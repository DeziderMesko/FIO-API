package fio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BasicHttpConnector implements HttpConnector {
	
	@Override
	public String getData(String url) throws HttpRequestException {
		try {
			return getData(new URL(url));
		} catch (MalformedURLException e) {
			throw new HttpRequestException(e);
		}
	}

	@Override
	public String getData(URL url) throws HttpRequestException {
		BufferedReader in = null;
		String result = "";
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
			}
		} catch (Exception e) {
			throw new HttpRequestException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// who cares?
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
