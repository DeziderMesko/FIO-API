package fio.client.result;

/**
 * Mozne formaty vraceny serverm FIO tak jak jsou definovany v {@link http
 * ://www.fio.cz/docs/cz/API_Bankovnictvi.pdf} (verze 1.2.6)
 */
public enum FioFormat {
	xml, json, ofx, gpc("Windows-1250"), csv, html, sta, pdf(true);

	boolean binary = false;

	String encoding = "UTF-8";

	/**
	 * Zakladni konstruktor ktery ma predvolenou hodnotu pro atribut
	 * <code>binary</code> jako false a predvolenout hodnotu pro atribut
	 * <code>encoding</code> jako UTF-8
	 */
	FioFormat() {
	}

	/**
	 * Konstruktor umoznujici menit kodovani pro dany format
	 * 
	 * @param encoding
	 */
	FioFormat(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Konstruktor umoznujici volit mezi binarnim a textovym typem formatu
	 * 
	 * @param binary
	 *            true jestlize format je binarni
	 */
	FioFormat(boolean binary) {
		this.binary = binary;
	}

	/**
	 * @return kodovani pro tento typ
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @return true jestlize tento typ je binarni
	 */
	public Boolean isBinary() {
		return binary;
	}
}
