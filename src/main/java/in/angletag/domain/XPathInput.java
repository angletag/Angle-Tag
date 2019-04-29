package in.angletag.domain;

public class XPathInput {

	private String xpathValue;
	private String version;
	private String xml;

	

	public String getXpathValue() {
		return xpathValue;
	}
	public void setXpathValue(String xpathValue) {
		this.xpathValue = xpathValue;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	@Override
	public String toString() {
		return "XPathInput [xpathValue=" + xpathValue + ", version=" + version + ", xml=" + xml + "]";
	}
}
