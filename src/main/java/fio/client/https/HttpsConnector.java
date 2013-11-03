package fio.client.https;

import java.net.URL;


public interface HttpsConnector {
	public String getData(String url) throws HttpsRequestException;
	public String getData(URL url) throws HttpsRequestException;
}
