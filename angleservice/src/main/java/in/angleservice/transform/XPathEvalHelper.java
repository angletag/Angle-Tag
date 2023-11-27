package in.angleservice.transform;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.WhitespaceStrippingPolicy;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;

@Component("xPathEvalHelper")
public class XPathEvalHelper {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public String evaluateXpathExpressionForVersionOnePointZero(Document doc, String expression)
			throws XPathExpressionException {

		String strippedRemoved = removePrefixInXpath(expression);

		NodeList list = doc.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			removeNameSpace(list.item(i), "");
		}
		String result = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			xPath.setNamespaceContext(new NamespaceResolver(doc));
			result = (String) xPath.compile(strippedRemoved).evaluate(doc);
		} catch (XPathExpressionException e) {
			String errorMessage = MessageFormat.format(
					"evaluateXpathExpressionForVersionOnePointZero while evaluating the xpath expression={1}",
					expression);
			log.error(errorMessage, e);
			throw e;
		}
		return result;
	}

	public String evaluateXpathExpressionForVersionTwoPointZero(String xml, String expression)
			throws SaxonApiException {
		String result = null;
		try {
			Processor proc = new Processor(false);
			XPathCompiler xPath = proc.newXPathCompiler();
			DocumentBuilder builder = proc.newDocumentBuilder();
			builder.setLineNumbering(true);
			builder.setWhitespaceStrippingPolicy(WhitespaceStrippingPolicy.ALL);
			Source src = new StreamSource(new StringReader(xml));
			XdmNode doc = builder.build(src);

			XPathSelector selector = xPath.compile(expression).load();
			selector.setContextItem(doc);
			XdmItem xdmItem = selector.evaluateSingle();
			result = xdmItem == null ? null : xdmItem.getStringValue();

		} catch (SaxonApiException e) {
			String errorMessage = MessageFormat.format(
					"evaluateXpathExpressionForVersionTwoPointZero while evaluating the xpath expression={1}",
					expression);
			log.error(errorMessage, e);
			throw e;
		}
		return result;
	}

	private void removeNameSpace(Node node, String nameSpaceURI) {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Document ownerDoc = node.getOwnerDocument();
			NamedNodeMap map = node.getAttributes();
			Node n;
			while (!(0 == map.getLength())) {
				n = map.item(0);
				map.removeNamedItemNS(n.getNamespaceURI(), n.getLocalName());
			}
			ownerDoc.renameNode(node, nameSpaceURI, node.getLocalName());
		}
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			removeNameSpace(list.item(i), nameSpaceURI);
		}
	}

	
	private String removePrefixInXpath(String expression) {
		String strippedRemoved=expression;
		String prefix = StringUtils.substringBetween(expression, "/", ":");
		while(null!=prefix) {
			strippedRemoved = expression.replaceAll(prefix + ":", "");
			prefix = StringUtils.substringBetween(strippedRemoved, "/", ":");
		}
		
		return strippedRemoved;
	}
}

class NamespaceResolver implements NamespaceContext {

	private Document sourceDocument;

	public NamespaceResolver(Document document) {
		sourceDocument = document;
	}


	public String getNamespaceURI(String prefix) {
		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
			return sourceDocument.lookupNamespaceURI(null);
		} else {
			return sourceDocument.lookupNamespaceURI(prefix);
		}
	}

	public String getPrefix(String namespaceURI) {
		return sourceDocument.lookupPrefix(namespaceURI);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String namespaceURI) {
		return null;
	}
}
