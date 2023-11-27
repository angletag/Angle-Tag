package in.angleservice.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import in.angleservice.domain.MultiXsdValidationInput;
import in.angleservice.domain.TranformerInput;
import in.angleservice.domain.TransformXqParamInput;
import in.angleservice.domain.XPathInput;
import in.angleservice.domain.XsdValidationInput;
import in.angleservice.service.TransformerService;
import in.angleservice.service.XMLGenerationService;
import in.angleservice.utilities.XmlFormatter;
import net.sf.saxon.s9api.SaxonApiException;

public class AngleController {

	private static final Logger log = LoggerFactory.getLogger(AngleController.class);

	@Autowired
	TransformerService service;

	@Autowired
	XmlFormatter xmlFormatter;

	@Autowired
	XMLGenerationService xmlGeneration;

	public String transformXQuery(TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (StringUtils.hasText(transformer.getParam()))
			transformer.setParameters(service.stringtoMapParam(transformer.getParam()));

		String result = "Processing.,..";
		try {
			result = service.transformXQuery(transformer.getXqueryValue(), transformer.getXml(),
					transformer.getParameters());
		} catch (SaxonApiException | IOException e) {
			log.error("Error in xquery transform {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();

		}

		return result;
	}

	public String evaluateXPath(XPathInput xPathInput) {
		String result = null;
		try {
			if (xPathInput != null) {
				if ("1.0".equals(xPathInput.getVersion())) {

					result = service.evaluateXpathExpressionForVersionOnePointZero(xPathInput.getXpathValue(),
							xPathInput.getXml());

				} else if ("2.0".equals(xPathInput.getVersion())) {
					result = service.evaluateXpathExpressionForVersionTwoPointZero(xPathInput.getXpathValue(),
							xPathInput.getXml());
				} else {
					log.error("unsupported version {}", xPathInput.getVersion());
					return "Unsupported xpath Version";
				}
			}
		} catch (Exception e) {
			log.error("Error in evaluateXPath {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}

		return result;
	}

	public String validateXmlSchema(XsdValidationInput input) {
		String result;
		try {
			result = service.validateSchema(input.getXml(), input.getXsd());
		} catch (SAXException | IOException e) {
			log.error("Error while evaluating against xsd", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}
		return result;
	}

	public String validateXmlSchema(String xml)
			throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		MultiXsdValidationInput input = xmlMapper.readValue(xml, MultiXsdValidationInput.class);
		String result;
		try {
			result = service.validateSchema(input.getXml(), input.getXsd());
		} catch (SAXException | IOException e) {
			log.error("Error while evaluating against xsd", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}
		return result;
	}

	public String transformXSLT(TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (StringUtils.hasText(transformer.getParam())) {
			transformer.setParameters(service.stringtoMapParam(transformer.getParam()));
		}
		String result = "Processing...";
		try {
			result = service.transformXSLT(transformer.getXqueryValue(), transformer.getXml(),
					transformer.getParameters());
		} catch (Exception e) {
			log.error("Error in xslt transform", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}

		return result;
	}

	public String transformXQueryWithParams(String xml)
			throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		TransformXqParamInput transformXqParamInput = xmlMapper.readValue(xml, TransformXqParamInput.class);
		String result = "";
		log.debug("TransformXQueryWithParams input:{}", transformXqParamInput);

		try {
			if (StringUtils.hasText(transformXqParamInput.getParam())) {
				transformXqParamInput.setParameters(service.stringtoMapParam(transformXqParamInput.getParam()));
			}
			result = service.transformXQueryWithParam(transformXqParamInput);
		} catch (IOException | SaxonApiException e) {
			log.error("Error transform XQuery with params", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}
		return result;
	}

	public String formatXML(TranformerInput input) {
		log.trace("Entry formatXML input xml \n {}", input.getXml());
		String result = "Processing.,..";
		try {
			result = xmlFormatter.prettyFormat(input.getXml(), "2");
		} catch (Exception e) {
			log.error("Error in format xml {}", e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result = sw.toString();
		}
		log.trace("formatXML output xml \n {}", result);
		return result;
	}

	public String generateXML(String xsd) throws TransformerConfigurationException {
		return xmlGeneration.generateSampleXml(xsd);
	}

}
