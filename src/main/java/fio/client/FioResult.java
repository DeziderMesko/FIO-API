package fio.client;

import java.net.URI;

public class FioResult {

	private String requestUri;
	private DataFormat dataFormat;
	private String response;
	
	public FioResult(String response, DataFormat dataFormat, String requestUri) {
		this.response = response;
		this.dataFormat = dataFormat;
		this.requestUri = requestUri;
	}

	public String getResponse() {
		return this.response;
	}

	public DataFormat getDataFormat() {
		return this.dataFormat;
	}

	public String getRequestURI() {
		return this.requestUri;
	}

}
