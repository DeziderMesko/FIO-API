package fio.client.result;

/**
 * Objekt obalujici odpoved serveru na dotaz. Obsahuje URL ze ktere byla data
 * ziskana, format ve kterem byla ziskana a samotna data
 * 
 * @author dezider.mesko
 * 
 */
public class FioResult {

	private String requestUrl;
	private FioResultFormat dataFormat;
	private String response;

	/**
	 * 
	 * @param response
	 *            data tak, jak byla zaslana serverem
	 * @param dataFormat
	 *            format dat {@link FioResultFormat}
	 * @param requestUrl
	 *            adresa ze ktere byla data ziskana
	 */
	public FioResult(String response, FioResultFormat dataFormat, String requestUrl) {
		this.response = response;
		this.dataFormat = dataFormat;
		this.requestUrl = requestUrl;
	}

	/**
	 * 
	 * @return data tak, jak byla zaslana serverem
	 */
	public String getResponse() {
		return this.response;
	}

	/**
	 * 
	 * @return format dat {@link FioResultFormat}
	 */
	public FioResultFormat getDataFormat() {
		return this.dataFormat;
	}

	/**
	 * 
	 * @return adresa ze ktere byla data ziskana
	 */
	public String getRequestURL() {
		return this.requestUrl;
	}

}
