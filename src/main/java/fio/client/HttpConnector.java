package fio.client;

import java.net.URL;

public interface HttpConnector {
	public String getData(String url) throws HttpRequestException;
	public String getData(URL url) throws HttpRequestException;
}
