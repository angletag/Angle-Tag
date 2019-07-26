package in.angletag.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import in.angletag.domain.MultiXsdValidationInput;
import in.angletag.domain.TranformerInput;
import in.angletag.domain.TransformXqParamInput;
import in.angletag.domain.XPathInput;
import in.angletag.domain.XsdValidationInput;
import in.angletag.service.TransformerService;
import in.angletag.utilities.XmlFormatter;
import net.sf.saxon.s9api.SaxonApiException;

@RestController
@RequestMapping("/api/xml")
public class AngleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransformerService service;
	
	@Autowired
	XmlFormatter xmlFormatter;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xquery")
	public String transformXQuery(@RequestBody TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (!StringUtils.isEmpty(transformer.getParam()))
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

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xpath")
	public String evaluateXPath(@RequestBody XPathInput xPathInput) {
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

	@PostMapping(produces = MediaType.TEXT_XML_VALUE, path = "/xsd")
	public String validateXmlSchema(@RequestBody XsdValidationInput input) {
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

	@PostMapping(produces = MediaType.TEXT_XML_VALUE, path = "/multixsd", consumes = MediaType.TEXT_XML_VALUE)
	public String validateXmlSchema(@RequestBody String xml)
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

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/xslt")
	public String transformXSLT(@RequestBody TranformerInput transformer) {

		log.debug("Input :{}", transformer);
		log.trace("input xml \n {}", transformer.getXml());
		if (!StringUtils.isEmpty(transformer.getParam())) {
			transformer.setParameters(service.stringtoMapParam(transformer.getParam()));
		}
		String result = "Processing.,..";
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

	@PostMapping(produces = MediaType.TEXT_XML_VALUE, path = "/xqueryparam", consumes = MediaType.TEXT_XML_VALUE)
	public String transformXQueryWithParams(@RequestBody String xml)
			throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		TransformXqParamInput transformXqParamInput = xmlMapper.readValue(xml, TransformXqParamInput.class);
		String result = "";
		log.debug("TransformXQueryWithParams input:{}", transformXqParamInput);

		try {
			if (!StringUtils.isEmpty(transformXqParamInput.getParam())) {
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
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE, path = "/formatXML")
	public String formatXML(@RequestBody TranformerInput input) {

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

	@GetMapping("/test")
	public String test() {
		return "<Test>From server </Test>";
	}

}
