package io.codety.scanner.analyzer.scalastyle.dto;

import java.util.List;

public class ScalastyleFile {
	private List<ScalastyleError> error;
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
