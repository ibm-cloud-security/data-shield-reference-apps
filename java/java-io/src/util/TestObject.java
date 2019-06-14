package util;

import java.io.Serializable;

public class TestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestObject(String content) {
		this.content = content;
	}

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return getContent();
	}

}
