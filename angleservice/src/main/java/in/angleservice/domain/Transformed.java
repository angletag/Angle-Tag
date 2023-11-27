package in.angleservice.domain;

public class Transformed {

	private Integer status;

	private String content;
	
	private String executionTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}
	
	@Override
	public String toString() {
		return "Transformed [status=" + status + ", content=**Larger to print**, executionTime=" + executionTime + "]";
	}
}
