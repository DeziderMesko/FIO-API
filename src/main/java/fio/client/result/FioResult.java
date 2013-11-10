package fio.client.result;

import java.io.UnsupportedEncodingException;

import fio.client.FioConstants;

/**
 * Objekt reprezentujici odpoved serveru na dotaz. Obsahuje URL ze ktere byla
 * data ziskana, format ve kterem byla ziskana a samotna data
 * 
 * @author dezider.mesko
 * 
 */
public class FioResult {

	private String requestUrl;
	private FioConstants.AnswerFormat answerFormat;
	private byte[] response;

	/**
	 * 
	 * @param response
	 *            data tak, jak byla zaslana serverem
	 * @param answerFormat
	 *            format dat {@link AnswerFormat}
	 * @param requestUrl
	 *            adresa ze ktere byla data ziskana
	 */
	public FioResult(byte[] response, FioConstants.AnswerFormat answerFormat, String requestUrl) {
		this.response = response;
		this.answerFormat = answerFormat;
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
	 * @return format dat {@link AnswerFormat}
	 */
	public FioConstants.AnswerFormat getDataFormat() {
		return answerFormat;
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
		if (answerFormat.isBinary()) {
			return null;
		}
		try {
			return new String(response, answerFormat.getEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
