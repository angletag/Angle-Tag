package in.angletag.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import in.angletag.domain.TransformXqParamInput;
import in.angletag.transform.MessageConversionHelper;
import in.angletag.transform.XPathEvalHelper;
import in.angletag.transform.XSLTHelper;
import in.angletag.transform.XqueryTransformer;
import net.sf.saxon.s9api.SaxonApiException;

@Service
public class TransformerService {

	
	private static final Logger log = LoggerFactory.getLogger(TransformerService.class);


	@Autowired
	XqueryTransformer xqueryTransformer;

	@Autowired
	XPathEvalHelper xPathEvalHelper;

	@Autowired
	MessageConversionHelper messageConversionHelper;

	@Autowired
	XSLTHelper xsltHelper;

	public String transformXQuery(String xqueryValue, String xml, Map<String, String> parameters)
			throws SaxonApiException, IOException {
		log.trace("Input {},{}", xqueryValue, xml);

		long startTime = System.currentTimeMillis();
		String result = xqueryTransformer.transform(xml, xqueryValue, parameters);
		long endTime = System.currentTimeMillis();
		log.debug("Transformed data is : {}", result);
		String exeTime = (endTime - startTime) + "ms";
		log.debug("Execution time : {} seconds", exeTime);
		return result;
	}

	public String evaluateXpathExpressionForVersionOnePointZero(String xqueryValue, String xml) throws Exception {
		log.trace("Input {},{}", xqueryValue, xml);

		Document docMessagePayload = messageConversionHelper.convertStringToDocument(xml);

		long startTime = System.currentTimeMillis();
		String result = xPathEvalHelper.evaluateXpathExpressionForVersionOnePointZero(docMessagePayload, xqueryValue);
		long endTime = System.currentTimeMillis();
		log.debug("evaluateXpathExpressionForVersionOnePointZero data is : {}", result);
		String exeTime = (endTime - startTime) + "ms";
		log.debug("Execution time : {} seconds", exeTime);
		return result;
	}

	public String evaluateXpathExpressionForVersionTwoPointZero(String xqueryValue, String xml)
			throws SaxonApiException, IOException, XPathExpressionException {
		log.trace("Input {},{}", xqueryValue, xml);

		long startTime = System.currentTimeMillis();
		String result = xPathEvalHelper.evaluateXpathExpressionForVersionTwoPointZero(xml, xqueryValue);
		long endTime = System.currentTimeMillis();
		log.debug("evaluateXpathExpressionForVersionTwoPointZero data is : {}", result);
		String exeTime = (endTime - startTime) + "ms";
		log.debug("Execution time : {} seconds", exeTime);

		return result;
	}

	public Map<String, String> stringtoMapParam(String param) {
		String[] keyValuePairs = param.split(";");
		Map<String, String> map = new HashMap<>();

		for (String pair : keyValuePairs) {
			if (!StringUtils.isEmpty(pair)) {
				String[] entry = pair.split("=");
				map.put(entry[0].trim(), entry[1].trim());
			}
		}
		return map;

	}

	public String validateSchema(String xml, String xsd) throws SAXException, IOException {
		Source source = new StreamSource(new StringReader(xml));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaSrc = new StreamSource(new StringReader(xsd));
		Schema schema = factory.newSchema(schemaSrc);
		Validator validator = schema.newValidator();
		validator.validate(source);
		return "Valid xml";
	}

	public String transformXSLT(String xqueryValue, String xml, Map<String, String> parameters) throws Exception {
		log.trace("Input {},{}", xqueryValue, xml);

		long startTime = System.currentTimeMillis();
		String result = xsltHelper.transform(xml, xqueryValue, parameters);
		long endTime = System.currentTimeMillis();
		log.debug("XSLT data is : {}", result);
		String exeTime = (endTime - startTime) + "ms";
		log.debug("Execution time : {} seconds", exeTime);

		return result;
	}

	public String validateSchema(String xml, List<String> xsds) throws SAXException, IOException {
		Source source = new StreamSource(new StringReader(StringEscapeUtils.unescapeXml(xml)));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Source[] sources = new Source[xsds.size()];
		int position = 0;
		for (String schema : xsds) {
			String xsd = StringEscapeUtils.unescapeXml(schema);
			sources[position++] = new StreamSource(new StringReader(xsd));
		}
		Schema schema = factory.newSchema(sources);
		Validator validator = schema.newValidator();
		validator.validate(source);
		return "Valid xml";
	}

	public String transformXQueryWithParam(TransformXqParamInput transformXqParamInput)
			throws SaxonApiException, IOException {
		log.trace("Input {}", transformXqParamInput);

		long startTime = System.currentTimeMillis();
		transformXqParamInput=unescapeTransformXqParamInputXmls(transformXqParamInput);
		String result = xqueryTransformer.transformWithParam(transformXqParamInput);
		long endTime = System.currentTimeMillis();
		log.debug("TransformXQueryWithParam data is : {}", result);
		String exeTime = (endTime - startTime) + "ms";
		log.debug("Execution time : {} seconds", exeTime);
		return result;
	}

	private TransformXqParamInput unescapeTransformXqParamInputXmls(TransformXqParamInput transformXqParamInput) {

		if (!StringUtils.isEmpty(transformXqParamInput.getXquery())) {
			String xquery = StringEscapeUtils.unescapeXml(transformXqParamInput.getXquery());
			transformXqParamInput.setXquery(xquery);
		}

		if (!StringUtils.isEmpty(transformXqParamInput.getXqInput1())) {
			String xqInput1 = StringEscapeUtils.unescapeXml(transformXqParamInput.getXqInput1());
			transformXqParamInput.setXquery(xqInput1);
		}
		if (!StringUtils.isEmpty(transformXqParamInput.getXqInput2())) {
			String xqInput2 = StringEscapeUtils.unescapeXml(transformXqParamInput.getXqInput2());
			transformXqParamInput.setXquery(xqInput2);
		}
		
		return transformXqParamInput;

	}
}
