package in.angleservice.resource;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import in.angleservice.xsdview.processor.XsdViProcessor;

public class XsdGraphController {

	@Autowired
	XsdViProcessor processor;
	
	public byte[] serveFile(String xsd) throws Exception {
		
		/*
		 * OutputStream outputStream = response.getOutputStream();
		 * processor.process(xsd, outputStream); response.flushBuffer();
		 */
		/* OutputStream outputStream = new FileOutputStream("test.svg"); */
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String formatedXsd=xsd.substring(4);
		processor.process(formatedXsd, outputStream); 
		return outputStream.toByteArray();
	} 
}
