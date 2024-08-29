package io.codety.scanner.analyzer.scalastyle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScalastyleFile {

	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ScalastyleError> error;
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	public List<ScalastyleError> getError() {
		return error;
	}

	public void setError(List<ScalastyleError> error) {
		this.error = error;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
