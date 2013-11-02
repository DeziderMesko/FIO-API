package fio.client;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FioClient {
	private static final String HTTPS_WWW_FIO_CZ = "https://www.fio.cz/ib_api/rest/";
	// by-id/{token}/{year}/{id}/transactions.{format}
	private static final String GET_STATEMENTS = "%sby-id/%s/%s/%s/transactions.%s";
	// periods/{token}/{datum od}/{datum do}/transactions.{format}
	private static final String GET_TRANSACTIONS = "%speriods/%s/%s/%s/transactions.%s";
	// last/{token}/transactions.{format}
	private static final String GET_NEW_TRANSACTIONS = "%slast/%s/transactions.%s";
	// set-last-id/{token}/{id}/
	private static final String SET_LAST_BY_ID = "%sset-last-id/%s/%s";
	// set-last-date/{token}/{rrrr-mm-dd}/
	private static final String SET_LAST_BY_DATE = "%sset-last-date/%s/%s";

	private String token = "";
	private DataFormat dataFormat = DataFormat.xml;
	private String url = HTTPS_WWW_FIO_CZ;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private HttpConnector httpConnector;

	public FioClient(String token, DataFormat dataFormat) {
		this.token = token;
		this.dataFormat = dataFormat;
		setHttpConnector(new BasicHttpConnector());
	}

	public FioResult getTransactions(Calendar from, Calendar to) throws InvalidParametersException, URISyntaxException,
			HttpRequestException {
		if (from == null || to == null || from.after(to)) {
			throw new InvalidParametersException();
		}

		String uri = String.format(GET_TRANSACTIONS, url, getToken(), dateFormatter.format(from.getTime()),
				dateFormatter.format(to.getTime()), getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult getNewTransactions() throws HttpRequestException {
		String uri = String.format(GET_NEW_TRANSACTIONS, url, getToken(), getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult getStatement(int year, int statementNumber) throws HttpRequestException {
		String uri = String.format(GET_STATEMENTS, url, getToken(), String.valueOf(year), String.valueOf(statementNumber),
				getDataFormat().toString());
		return executeRequest(uri);
	}

	public FioResult setTransactionPointerByDate(Calendar date) throws HttpRequestException, InvalidParametersException {
		if (date == null) {
			throw new InvalidParametersException();
		}
		String uri = String.format(SET_LAST_BY_DATE, url, getToken(), dateFormatter.format(date.getTime()), getDataFormat()
				.toString());
		return executeRequest(uri);
	}

	public FioResult setTransactionPointerById(int pointer) throws HttpRequestException {
		String uri = String.format(SET_LAST_BY_ID, url, getToken(), String.valueOf(pointer), getDataFormat().toString());
		return executeRequest(uri);
	}

	private FioResult executeRequest(String uri) throws HttpRequestException {
		String result = httpConnector.getData(uri);
		return new FioResult(result, getDataFormat(), uri);
	}

	public HttpConnector getHttpConnector() {
		return httpConnector;
	}

	public void setHttpConnector(HttpConnector connector) {
		this.httpConnector = connector;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DataFormat getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(DataFormat dataFormat) {
		this.dataFormat = dataFormat;

	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
