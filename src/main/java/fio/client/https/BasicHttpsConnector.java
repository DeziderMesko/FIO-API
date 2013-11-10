package fio.client.https;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Zakladni implementace {@link HttpsConnector}
 * 
 * @author dezider.mesko
 * 
 */
public class BasicHttpsConnector implements HttpsConnector {

	private SSLSocketFactory defaultSSLSocketFactory;

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
			if (defaultSSLSocketFactory != null) {
				HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSocketFactory);
			}
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

	/**
	 * Tuto metodu je mozne pouzit pro vypnuti kontroly validity SSL certifikatu
	 * 
	 * @param defaultSSLSocketFactory
	 */
	protected void setDefaultSSLSocketFactory(SSLSocketFactory defaultSSLSocketFactory) {
		this.defaultSSLSocketFactory = defaultSSLSocketFactory;
	}

	/**
	 * @see fio.client.https.HttpsConnector#getPostData(java.net.URL,
	 *      java.util.HashMap)
	 */
	@Override
	public byte[] getPostData(URL url, HashMap<String, String> parameters) throws HttpsRequestException {
		return null;
	}

	/**
	 * @see fio.client.https.HttpsConnector#getPostData(java.lang.String,
	 *      java.util.HashMap)
	 */
	@Override
	public byte[] getPostData(String url, HashMap<String, String> parameters) throws HttpsRequestException {
		try {
			MultipartUtility mu = new MultipartUtility(url, "UTF-8");
			// mu.addFilePart("file", uploadFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
