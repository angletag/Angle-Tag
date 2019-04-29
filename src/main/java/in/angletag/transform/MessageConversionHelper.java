package in.angletag.transform;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component("messageConversionHelper")
public class MessageConversionHelper {

	private static final Logger log = LoggerFactory.getLogger(MessageConversionHelper.class);

	public Document convertStringToDocument(String xmlStr) {
		log.debug("Inside convertStringToDocument():MessageConvertor");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document doc = null;
		try (StringReader stringReader = new StringReader(xmlStr)) {
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(stringReader));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			log.error("Error convertStringToDocument():MessageConvertor ->", e);
		}
		return doc;
	}

	public String convertDocumentToString(Document doc) {

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		String output = null;
		try (StringWriter writer = new StringWriter()) {
			transformer = factory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();
		} catch (TransformerException | IOException e) {
			log.error("Error convertDocumentToString():MessageConvertor ->", e);
		}
		return output;
	}
}
