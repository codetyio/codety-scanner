package io.codety.scanner.analyzer.scalastyle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "checkstyle")
public class ScalastyleCheckstyle {

	@JacksonXmlProperty(localName = "file")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<ScalastyleFile> file;

	private String version;

	public List<ScalastyleFile> getFile() {
		return file;
	}

	public void setFile(List<ScalastyleFile> file) {
		this.file = file;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
