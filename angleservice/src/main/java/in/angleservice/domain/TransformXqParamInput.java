package in.angleservice.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TransformXqParamInput {

	@JacksonXmlCData
	@JacksonXmlProperty
	String xquery;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	String xqInput1;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	String xqXmlInputName1;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	String xqInput2;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	String xqXmlInputName2;
	
	@JacksonXmlCData
	@JacksonXmlProperty
	String param;
	
	
	private Map<String,String> parameters=new HashMap<>();

	

	public String getXquery() {
		return xquery;
	}

	public void setXquery(String xquery) {
		this.xquery = xquery;
	}

	public String getXqInput1() {
		return xqInput1;
	}

	public void setXqInput1(String xqInput1) {
		this.xqInput1 = xqInput1;
	}

	public String getXqInput2() {
		return xqInput2;
	}

	public void setXqInput2(String xqInput2) {
		this.xqInput2 = xqInput2;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getXqXmlInputName1() {
		return xqXmlInputName1;
	}

	public void setXqXmlInputName1(String xqXmlInputName1) {
		this.xqXmlInputName1 = xqXmlInputName1;
	}

	public String getXqXmlInputName2() {
		return xqXmlInputName2;
	}

	public void setXqXmlInputName2(String xqXmlInputName2) {
		this.xqXmlInputName2 = xqXmlInputName2;
	}

	@Override
	public String toString() {
		return "TransformXqParamInput [xquery=" + xquery + ", xqInput1=" + xqInput1 + ", xqXmlInputName1=" + xqXmlInputName1
				+ ", xqInput2=" + xqInput2 + ", xqXmlInputName2=" + xqXmlInputName2 + ", param=" + param
				+ ", parameters=" + parameters + "]";
	}
}
