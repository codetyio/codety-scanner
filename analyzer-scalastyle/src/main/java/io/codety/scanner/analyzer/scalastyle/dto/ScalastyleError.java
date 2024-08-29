package io.codety.scanner.analyzer.scalastyle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScalastyleError {
	@JacksonXmlProperty(isAttribute = true)
	private Integer line;
	@JacksonXmlProperty(isAttribute = true)
	private String source;
	@JacksonXmlProperty(isAttribute = true)
	private String severity;
	@JacksonXmlProperty(isAttribute = true)
	private String message;
	@JacksonXmlProperty(isAttribute = true)
	private Integer column;

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}
}

