package fio.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fio.client.https.BasicHttpsConnector;
import fio.client.https.HttpsConnector;
import fio.client.https.HttpsRequestException;
import fio.client.result.FioResult;
import fio.client.result.FioResultFormat;

public class FioClient {
	private static final String HTTPS_WWW_FIO_CZ = "https://www.fio.cz/ib_api/rest/";
	// by-id/{token}/{year}/{id}/transactions.{format}
	private static final String GET_STATEMENTS = "%sby-id/%s/%s/%s/transactions.%s";
	// periods/{token}/{datum od}/{datum do}/transactions.{format}
	private static final String GET_TRANSACTIONS = "%speriods/%s/%s/%s/transactions.%s";
	// last/{token}/transactions.{format}
	private static final String GET_NEW_TRANSACTIONS = "%slast/%s/transactions.%s";
	// set-last-id/{token}/{id}/
	private static final String SET_LAST_BY_ID = "%sset-last-id/%s/%s/";
	// set-last-date/{token}/{rrrr-mm-dd}/
	private static final String SET_LAST_BY_DATE = "%sset-last-date/%s/%s/";

	private String token = "";
	private FioResultFormat fioResultFormat = FioResultFormat.xml;
	private String fioUrl = HTTPS_WWW_FIO_CZ;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private HttpsConnector httpConnector;

	public FioClient(String token, FioResultFormat fioResultFormat) {
		this.token = token;
		this.fioResultFormat = fioResultFormat;
		setHttpConnector(new BasicHttpsConnector());
	}

	public FioResult getTransactions(Calendar from, Calendar to) throws InvalidParametersException, HttpsRequestException {
		if (from == null || to == null || from.after(to)) {
			throw new InvalidParametersException();
		}

		String url = String.format(GET_TRANSACTIONS, fioUrl, getToken(), dateFormatter.format(from.getTime()),
				dateFormatter.format(to.getTime()), getDataFormat().toString());
		return executeRequest(url);
	}

	public FioResult getNewTransactions() throws HttpsRequestException {
		String url = String.format(GET_NEW_TRANSACTIONS, fioUrl, getToken(), getDataFormat().toString());
		return executeRequest(url);
	}

	public FioResult getStatement(int year, int statementNumber) throws HttpsRequestException {
		String url = String.format(GET_STATEMENTS, fioUrl, getToken(), String.valueOf(year), String.valueOf(statementNumber),
				getDataFormat().toString());
		return executeRequest(url);
	}

	public FioResult setTransactionPointerByDate(Calendar date) throws HttpsRequestException, InvalidParametersException {
		if (date == null) {
			throw new InvalidParametersException();
		}
		String url = String.format(SET_LAST_BY_DATE, fioUrl, getToken(), dateFormatter.format(date.getTime()), getDataFormat().toString());
		return executeRequest(url);
	}

	public FioResult setTransactionPointerById(int pointer) throws HttpsRequestException {
		String url = String.format(SET_LAST_BY_ID, fioUrl, getToken(), String.valueOf(pointer), getDataFormat().toString());
		return executeRequest(url);
	}

	private FioResult executeRequest(String url) throws HttpsRequestException {
		String result = httpConnector.getData(url);
		return new FioResult(result, getDataFormat(), url);
	}

	public HttpsConnector getHttpConnector() {
		return httpConnector;
	}

	public void setHttpConnector(HttpsConnector connector) {
		this.httpConnector = connector;
	}

	public String getUrl() {
		return fioUrl;
	}

	public void setUrl(String url) {
		this.fioUrl = url;
	}

	public FioResultFormat getDataFormat() {
		return fioResultFormat;
	}

	public void setDataFormat(FioResultFormat dataFormat) {
		this.fioResultFormat = dataFormat;

	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
