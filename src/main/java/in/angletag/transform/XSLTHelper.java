package in.angletag.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import in.angletag.utilities.SaxonHandler;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.StaticError;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

@Component("xSLTHelper")
public class XSLTHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SaxonHandler errorHandler;

	private Processor proc;
	private StringWriter sw;
	private Serializer out;
	private XsltCompiler comp;

	@PostConstruct
	private void init() {
		proc = new Processor(false);
		sw = new StringWriter();
		out = proc.newSerializer(sw);
		comp = proc.newXsltCompiler();
		log.info("The Saxon processor object created");
	}

	public String transform(String inputXml, String xsltPayload, Map<String, String> parameters)
			throws SaxonApiException {
		List<StaticError> errors = new ArrayList<>();
		comp.setErrorList(errors);
		try {
			XsltExecutable exec = comp.compile(new StreamSource(new StringReader(xsltPayload)));
			XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(inputXml)));
			XsltTransformer transformer = exec.load();
			if (!CollectionUtils.isEmpty(parameters)) {
				parameters.forEach((k, v) -> {
					QName paramName = new QName(k);
					transformer.setParameter(paramName, new XdmAtomicValue(v));
				});
			}
			transformer.setInitialContextNode(source);
			transformer.setDestination(out);
			transformer.transform();
		} catch (SaxonApiException saxExcep) {
			String compilerError=errorHandler.processError(errors);
			throw new SaxonApiException(compilerError, saxExcep);
		}
		return sw.toString();
	}
}
