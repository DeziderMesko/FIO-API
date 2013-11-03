package fio.client.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Zakladni implementace {@link HttpsConnector}
 * 
 * @author dezider.mesko
 * 
 */
public class BasicHttpsConnector implements HttpsConnector {

	/**
	 * @see fio.client.https.HttpsConnector#getData(java.lang.String)
	 */
	@Override
	public String getData(String url) throws HttpsRequestException {
		try {
			return getData(new URL(url));
		} catch (MalformedURLException e) {
			throw new HttpsRequestException(e);
		}
	}

	/**
	 * @see fio.client.https.HttpsConnector#getData(java.net.URL)
	 */
	@Override
	public String getData(URL url) throws HttpsRequestException {
		BufferedReader in = null;
		String result = "";
		try {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result += inputLine;
				result += '\n';
			}
		} catch (Exception e) {
			throw new HttpsRequestException(e);
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
