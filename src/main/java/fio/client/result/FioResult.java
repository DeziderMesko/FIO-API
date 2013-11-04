package fio.client.result;

import java.io.UnsupportedEncodingException;

/**
 * Objekt reprezentujici odpoved serveru na dotaz. Obsahuje URL ze ktere byla
 * data ziskana, format ve kterem byla ziskana a samotna data
 * 
 * @author dezider.mesko
 * 
 */
public class FioResult {

	private String requestUrl;
	private FioResultFormat dataFormat;
	private byte[] response;

	/**
	 * 
	 * @param response
	 *            data tak, jak byla zaslana serverem
	 * @param dataFormat
	 *            format dat {@link FioResultFormat}
	 * @param requestUrl
	 *            adresa ze ktere byla data ziskana
	 */
	public FioResult(byte[] response, FioResultFormat dataFormat, String requestUrl) {
		this.response = response;
		this.dataFormat = dataFormat;
		this.requestUrl = requestUrl;
	}

	/**
	 * 
	 * @return data tak, jak byla zaslana serverem
	 */
	public byte[] getResponse() {
		return response;
	}

	/**
	 * 
	 * @return format dat {@link FioResultFormat}
	 */
	public FioResultFormat getDataFormat() {
		return dataFormat;
	}

	/**
	 * 
	 * @return adresa ze ktere byla data ziskana
	 */
	public String getRequestURL() {
		return requestUrl;
	}

	/**
	 * @see fio.client.result.FioResult#getResponseAsText()
	 */
	public String getResponseAsText() {
		try {
			return new String(response, dataFormat.getEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
