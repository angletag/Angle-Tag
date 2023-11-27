package in.angleservice.domain;

import java.util.HashMap;
import java.util.Map;

public class TranformerInput {

	private String xqueryValue;
	private String xml;
	private String param;
	private Map<String,String> parameters=new HashMap<>();
	
	public String getXqueryValue() {
		return xqueryValue;
	}
	public void setXqueryValue(String xqueryValue) {
		this.xqueryValue = xqueryValue;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "TranformerInput [xqueryValue=" + xqueryValue + ", param=" + param + ", parameters=" + parameters + "]";
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
