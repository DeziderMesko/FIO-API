package fio.client.https;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
	public byte[] getData(String url) throws HttpsRequestException;

	/**
	 * Ziska data z HTTPS serveru
	 * 
	 * @param url
	 *            adresa pro ziskani dat
	 * @return ziskana data
	 * @throws HttpsRequestException
	 */
	public byte[] getData(URL url) throws HttpsRequestException;

	/**
	 * Ziska data z HTTPS serveru POST metodou
	 * 
	 * @param url
	 *            adresa pro ziskani dat
	 * @param parameters
	 *            POST parameters
	 * @return ziskana data
	 * @throws HttpsRequestException
	 */
	public byte[] getPostData(URL url, Map<String, String> parameters) throws HttpsRequestException;

	/**
	 * Wrapper pro funkci {@link #getPostData(URL, HashMap)}
	 * 
	 * @param url
	 *            adresa pro ziskani dat
	 * @param parameters
	 *            POST parameters
	 * @return ziskana data
	 * @throws HttpsRequestException
	 */
	public byte[] getPostData(String url, Map<String, String> parameters) throws HttpsRequestException;
}
