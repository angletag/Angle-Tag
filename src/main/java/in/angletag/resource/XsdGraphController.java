package in.angletag.resource;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.angletag.xsdview.processor.XsdViProcessor;

@RestController
@RequestMapping("/api/xml")
public class XsdGraphController {

	@Autowired
	XsdViProcessor processor;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, path = "/viewXsd.svg",consumes="text/plain")
	public byte[] serveFile(@RequestBody String xsd,HttpServletResponse response) throws Exception {
		
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
