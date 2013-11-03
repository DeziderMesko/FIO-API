package fio.client;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fio.client.https.BasicHttpsConnector;
import fio.client.https.HttpsConnector;
import fio.client.https.HttpsRequestException;
import fio.client.result.FioResultFormat;
import fio.client.result.FioResult;

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
	private FioResultFormat dataFormat = FioResultFormat.xml;
	private String fioUrl = HTTPS_WWW_FIO_CZ;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private HttpsConnector httpConnector;

	public FioClient(String token, FioResultFormat dataFormat) {
		this.token = token;
		this.dataFormat = dataFormat;
		setHttpConnector(new BasicHttpsConnector());
	}

	public FioResult getTransactions(Calendar from, Calendar to) throws InvalidParametersException, URISyntaxException,
			HttpsRequestException {
		if (from == null || to == null || from.after(to)) {
			throw new InvalidParametersException();
		}

		String uri = String.format(GET_TRANSACTIONS, fioUrl, getToken(), dateFormatter.format(from.getTime()),
				dateFormatter.format(to.getTime()), getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult getNewTransactions() throws HttpsRequestException {
		String uri = String.format(GET_NEW_TRANSACTIONS, fioUrl, getToken(), getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult getStatement(int year, int statementNumber) throws HttpsRequestException {
		String uri = String.format(GET_STATEMENTS, fioUrl, getToken(), String.valueOf(year), String.valueOf(statementNumber),
				getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult setTransactionPointerByDate(Calendar date) throws HttpsRequestException, InvalidParametersException {
		if (date == null) {
			throw new InvalidParametersException();
		}
		String uri = String.format(SET_LAST_BY_DATE, fioUrl, getToken(), dateFormatter.format(date.getTime()), getDataFormat()
				.toString());
		return executeRequest(uri);
	}

	public FioResult setTransactionPointerById(int pointer) throws HttpsRequestException {
		String uri = String.format(SET_LAST_BY_ID, fioUrl, getToken(), String.valueOf(pointer), getDataFormat().toString());
		return executeRequest(uri);
	}

	private FioResult executeRequest(String uri) throws HttpsRequestException {
		String result = httpConnector.getData(uri);
		return new FioResult(result, getDataFormat(), uri);
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
		return dataFormat;
	}

	public void setDataFormat(FioResultFormat dataFormat) {
		this.dataFormat = dataFormat;

	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
