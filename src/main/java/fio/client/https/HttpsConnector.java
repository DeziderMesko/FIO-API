package fio.client.https;

import java.net.URL;

import fio.client.FioClient;

/**
 * Rozhrani pouzivane tridou {@link FioClient} pro ziskani dat z HTTPS serveru
 * 
 * @author dezider.mesko
 * 
 */
public interface HttpsConnector {
	/**
	 * Wrapper pro funkci {@link #getData(URL)}
	 * 
	 * @param url
	 *            adresa pro ziskani dat
	 * @return ziskana data
	 * @throws HttpsRequestException
	 */
	public String getData(String url) throws HttpsRequestException;

	/**
	 * Ziska data z HTTPS serveru
	 * 
	 * @param url
	 *            adresa pro ziskani dat
	 * @return ziskana data
	 * @throws HttpsRequestException
	 */
	public String getData(URL url) throws HttpsRequestException;
}
