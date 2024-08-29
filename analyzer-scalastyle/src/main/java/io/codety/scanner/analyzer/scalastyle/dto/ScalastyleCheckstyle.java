package io.codety.scanner.analyzer.scalastyle.dto;

import java.util.List;

public class ScalastyleCheckstyle {
	private List<ScalastyleFile> file;
	private double version;

	public List<ScalastyleFile> getFile() {
		return file;
	}

	public void setFile(List<ScalastyleFile> file) {
		this.file = file;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}
}
