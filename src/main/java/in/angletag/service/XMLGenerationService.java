package in.angletag.service;

import java.io.File;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSNamedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@Service
public class XMLGenerationService {

	
	private static final Logger log = LoggerFactory.getLogger(XMLGenerationService.class);

		public void generateSampleXml(String schema) throws TransformerConfigurationException {

			//Default options
			XSInstance instance = new XSInstance();
			instance.minimumElementsGenerated = 0;
			instance.maximumElementsGenerated = 0;
			instance.generateDefaultAttributes = false;
			instance.generateOptionalAttributes = false;
			instance.maximumRecursionDepth = 0;
			instance.generateOptionalElements = true;
			
			XSModel xsModel = new XSParser().parseString(schema, "");

		    XSNamedMap map = xsModel.getComponents(XSConstants.ELEMENT_DECLARATION);

		    QName rootElement = new QName(map.item(0).getNamespace(), map.item(0).getName(), XMLConstants.DEFAULT_NS_PREFIX);
		    StringWriter writer = new StringWriter();
		    XMLDocument sampleXml = new XMLDocument(new StreamResult(writer), true, 4, null);
		    instance.generate(xsModel, rootElement, sampleXml);

		    String xml = writer.toString();
				
			log.debug("Generated XML {}",xml);
		}
    
}
