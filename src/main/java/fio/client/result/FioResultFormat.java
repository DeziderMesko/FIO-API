package fio.client.result;

/**
 * Mozne formaty vraceny serverm FIO tak jak jsou definovany v {@link http
 * ://www.fio.cz/docs/cz/API_Bankovnictvi.pdf} (verze 1.2.6)
 */
public enum FioResultFormat {
	xml, json, ofx, gpc, csv, html, sta, pdf
}
