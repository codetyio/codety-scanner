package io.codety.scanner.analyzer.scalastyle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScalastyleCheckstyleEntry {

	@JacksonXmlProperty(isAttribute = false)
	private ScalastyleCheckstyle checkstyle;

	public ScalastyleCheckstyle getCheckstyle() {
		return checkstyle;
	}

	public void setCheckstyle(ScalastyleCheckstyle checkstyle) {
		this.checkstyle = checkstyle;
	}

}
