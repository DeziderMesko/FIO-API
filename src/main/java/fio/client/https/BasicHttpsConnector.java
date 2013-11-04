package fio.client.https;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	public byte[] getData(String url) throws HttpsRequestException {
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
	public byte[] getData(URL url) throws HttpsRequestException {
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			is = connection.getInputStream();
			byte value;
			while ((value = (byte) is.read()) != -1) {
				baos.write(value);
			}
		} catch (Exception e) {
			throw new HttpsRequestException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// who cares?
					e.printStackTrace();
				}
			}
		}
		return baos.toByteArray();
	}
}
