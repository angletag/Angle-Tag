package in.angleservice.xsdview.processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;

import in.angleservice.xsdview.XsdHandler;
import in.angleservice.xsdview.svg.AbstractSymbol;
import in.angleservice.xsdview.svg.SvgForXsd;
import in.angleservice.xsdview.utils.TreeBuilder;
import in.angleservice.xsdview.utils.WriterHelper;
import in.angleservice.xsdview.utils.XsdErrorHandler;
import jakarta.annotation.PostConstruct;

@Component
public class XsdViProcessor {

	private static final Logger log = LoggerFactory.getLogger(XsdViProcessor.class);
	private XSLoader schemaLoader;
	
	public void process(String schema,OutputStream os) throws Exception {

		if(null==schema) {
			throw new IllegalArgumentException("Schema is null");
		}
		InputStream is=new ByteArrayInputStream(schema.getBytes());
		
		TreeBuilder builder = new TreeBuilder();
		XsdHandler xsdHandler = new XsdHandler(builder);
		WriterHelper writerHelper = new WriterHelper();
		SvgForXsd svg = new SvgForXsd(writerHelper);
				
		String output = "";
		
		XSModel model = schemaLoader.load(getLSInput(is));
		log.info("Processing XML Schema model...");
		xsdHandler.processModel(model);
		log.info("Drawing SVG " + output + "...");
		writerHelper.newWriter(os,"UTF-8");
		svg.draw((AbstractSymbol) builder.getRoot());
		log.info("Done.");
		is.close();
		
	}
		


	@PostConstruct
	public XSLoader getSchemaLoader() throws Exception {
		log.info("Loading the xercers schema");
		try {
			System.setProperty(DOMImplementationRegistry.PROPERTY, "org.apache.xerces.dom.DOMXSImplementationSourceImpl");
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			XSImplementation impl = (XSImplementation) registry.getDOMImplementation("XS-Loader");
			schemaLoader = impl.createXSLoader(null);
			DOMConfiguration config = schemaLoader.getConfig();
			DOMErrorHandler errorHandler = new XsdErrorHandler();
			config.setParameter("error-handler", errorHandler);
			config.setParameter("validate", Boolean.TRUE);
		} catch (ClassCastException|ClassNotFoundException|InstantiationException| IllegalAccessException e) {
			log.error(e.getLocalizedMessage(), e);
			throw new Exception(e);
		}
		return schemaLoader;
	}
	
	private  String outputUrl(String input) {
		String[] field = input.split("[/\\\\]");
		String in = field[field.length-1];
		if (in.toLowerCase().endsWith(".xsd")) {
			return in.substring(0, in.length()-4) + ".svg";
		}
		return in + ".svg";
	}
	
	private LSInput getLSInput(InputStream is) throws InstantiationException,IllegalAccessException, ClassNotFoundException {
		
	    final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
	    final DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
	    LSInput domInput = impl.createLSInput();
	    domInput.setByteStream(is);
	    return domInput;
	}
}
