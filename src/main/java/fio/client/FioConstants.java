/**
 * 
 */
package fio.client;

/**
 * Trida obsahujici konstanty pouzite napric FIO API Tak jak jsou definovany v
 * {@link http://www.fio.cz/docs/cz/API_Bankovnictvi.pdf} (verze 1.2.6)
 * 
 * @author dezider.mesko
 * 
 */
public class FioConstants {
	/**
	 * Mozne formaty vraceny serverm FIO
	 */
	public enum AnswerFormat {
		xml, json, ofx, gpc("Windows-1250"), csv, html, sta, pdf(true);

		boolean binary = false;

		String encoding = "UTF-8";

		/**
		 * Zakladni konstruktor ktery ma predvolenou hodnotu pro atribut
		 * <code>binary</code> jako false a predvolenout hodnotu pro atribut
		 * <code>encoding</code> jako UTF-8
		 */
		AnswerFormat() {
		}

		/**
		 * Konstruktor umoznujici menit kodovani pro dany format
		 * 
		 * @param encoding
		 */
		AnswerFormat(String encoding) {
			this.encoding = encoding;
		}

		/**
		 * Konstruktor umoznujici volit mezi binarnim a textovym typem formatu
		 * 
		 * @param binary
		 *            true jestlize format je binarni
		 */
		AnswerFormat(boolean binary) {
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

	/**
	 * Format prikazu k uhrade
	 * 
	 * @author dezider.mesko
	 * 
	 */
	public enum OrderFormat {
		xml, abo;
	}

	/**
	 * Jazyky pouzite v odpovedi na platebni prikaz
	 * 
	 * @author dezider.mesko
	 * 
	 */
	public enum Languages {
		cs, sk, en;
	}
}
