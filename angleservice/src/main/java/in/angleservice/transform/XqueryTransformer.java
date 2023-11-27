package in.angleservice.transform;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import in.angleservice.domain.TransformXqParamInput;
import in.angleservice.utilities.SaxonHandler;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XQueryCompiler;
import net.sf.saxon.s9api.XQueryEvaluator;
import net.sf.saxon.s9api.XQueryExecutable;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.XmlProcessingError;

@Component("xqueryTransformer")
public class XqueryTransformer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	Processor saxon;
	
	XQueryCompiler compiler;
	
	XQueryExecutable exec;
	
	DocumentBuilder builder;
	
	Configuration config;
	
	@Autowired
	SaxonHandler errorHandler;

	public String transform(String xml, String xquery, Map<String, String> parameters)
			throws SaxonApiException, IOException {
		List<XmlProcessingError> errors = new ArrayList<>();

		XdmValue result = null;
		saxon = new Processor(false);
		compiler = saxon.newXQueryCompiler();
		// compiler.setErrorListener(errorListerner);
		builder = saxon.newDocumentBuilder();
		config = new Configuration();
		log.info("The Saxon processor object created");
		compiler.setErrorList(errors);
		try {
			XQueryExecutable exec = compiler.compile(xquery);
			log.debug("Xquery Compiled");
			Source src = new StreamSource(new StringReader(xml));
			XdmNode doc = builder.build(src);
			log.debug("XdmNode is created using the input XML ");
			// instantiate the query, bind the input and evaluate
			XQueryEvaluator query = exec.load();
			query.setContextItem(doc);
			if (!CollectionUtils.isEmpty(parameters)) {
				parameters.forEach((k, v) -> {
					query.setExternalVariable(new QName(k), new XdmAtomicValue(v));
				});

			}
			result = query.evaluate();
		} catch (SaxonApiException saxExcep) {
			String compilerError = errorHandler.processError(errors);
			throw new SaxonApiException(compilerError, saxExcep);
		}

		log.debug("Xquery is evaluated and result is generated ");
		return result.toString();
	}

	public String transformWithParam(TransformXqParamInput transformXqParamInput)
			throws SaxonApiException, IOException {
		XQueryExecutable exec = compiler.compile(transformXqParamInput.getXquery());
		log.debug("TransformWithParam Xquery Compiled");

		XQueryEvaluator query = exec.load();

		if (!(StringUtils.isEmpty(transformXqParamInput.getXqInput1())
				&& (StringUtils.isEmpty(transformXqParamInput.getXqXmlInputName1())))) {

			query.setExternalVariable(new QName(transformXqParamInput.getXqXmlInputName1()),
					new XdmAtomicValue(transformXqParamInput.getXqInput1()));
		}

		if (!(StringUtils.isEmpty(transformXqParamInput.getXqInput2())
				&& (StringUtils.isEmpty(transformXqParamInput.getXqXmlInputName2())))) {

			query.setExternalVariable(new QName(transformXqParamInput.getXqXmlInputName2()),
					new XdmAtomicValue(transformXqParamInput.getXqInput2()));
		}

		if (!CollectionUtils.isEmpty(transformXqParamInput.getParameters())) {
			transformXqParamInput.getParameters().forEach((k, v) -> {
				query.setExternalVariable(new QName(k), new XdmAtomicValue(v));
			});

		}
		XdmValue result = query.evaluate();
		log.debug("TransformWithParam Xquery is evaluated and result is generated ");
		return result.toString();
	}
}
