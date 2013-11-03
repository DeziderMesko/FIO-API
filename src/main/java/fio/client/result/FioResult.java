package fio.client.result;

import java.net.URI;


public class FioResult {

	private String requestUri;
	private FioResultFormat dataFormat;
	private String response;
	
	public FioResult(String response, FioResultFormat dataFormat, String requestUri) {
		this.response = response;
		this.dataFormat = dataFormat;
		this.requestUri = requestUri;
	}

	public String getResponse() {
		return this.response;
	}

	public FioResultFormat getDataFormat() {
		return this.dataFormat;
	}

	public String getRequestURI() {
		return this.requestUri;
	}

}
