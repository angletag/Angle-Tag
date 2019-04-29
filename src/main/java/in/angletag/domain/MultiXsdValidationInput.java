package in.angletag.domain;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MultiXsdValidationInput {
	@JacksonXmlCData
	@JacksonXmlProperty
	String xml;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	@JacksonXmlElementWrapper(useWrapping = false)
	List<String> xsd;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<String> getXsd() {
		return xsd;
	}
	public void setXsd(List<String> xsd) {
		this.xsd = xsd;
	}

}
