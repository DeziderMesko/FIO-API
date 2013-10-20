package fio.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

public class FioClient {
	private static final String HTTPS_WWW_FIO_CZ = "https://www.fio.cz/ib_api/rest/";
	private String token = "";
	private DataFormat dataFormat = DataFormat.XML;
	private String url = HTTPS_WWW_FIO_CZ;
	
	private HttpConnector connector;

	public FioClient(String token, DataFormat dataFormat) {
		this.token = token;
		this.dataFormat = dataFormat;
		connector = new BasicHttpConnector();
	}

	public FioResult getTransactions(Calendar from, Calendar to) throws InvalidParametersException, URISyntaxException {
		if (from == null || to == null || from.after(to)) {
			throw new InvalidParametersException();
		}
		return new FioResult("Payload", DataFormat.JSON, new URI(HTTPS_WWW_FIO_CZ));
	}
	
	public String getUrl() {
		return url;
	}

	public DataFormat getDataFormat() {
		return dataFormat;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDataFormat(DataFormat dataFormat) {
		this.dataFormat = dataFormat;

	}

	public FioResult setTransactionPointerByDate(Calendar date) {
		return null;
	}

	public FioResult setTransactionPointerById(int pointer) {
		return null;
	}

	public FioResult getNewTransactions() {
		return null;
	}

	public FioResult getStatement(int year, int statementNumber) {
		return null;
	}
}
