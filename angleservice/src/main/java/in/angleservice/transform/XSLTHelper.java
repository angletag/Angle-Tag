package in.angleservice.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import in.angleservice.utilities.SaxonHandler;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XmlProcessingError;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

@Component("xSLTHelper")
public class XSLTHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SaxonHandler errorHandler;


	public String transform(String inputXml, String xsltPayload, Map<String, String> parameters)
			throws SaxonApiException {
		List<XmlProcessingError> errors = new ArrayList<>();
		Processor proc = null;
		StringWriter sw = new StringWriter();
		Serializer out = null;
		XsltCompiler comp = null;

		try {
			proc = new Processor(false);
			out = proc.newSerializer(sw);
			comp = proc.newXsltCompiler();
			comp.setErrorList(errors);
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
			String compilerError = errorHandler.processError(errors);
			throw new SaxonApiException(compilerError, saxExcep);
		}
		return sw.toString();
	}
}
