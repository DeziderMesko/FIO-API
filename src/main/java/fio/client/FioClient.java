package fio.client;

import java.util.Calendar;
import java.util.Date;

public class FioClient {
	private static final String HTTPS_WWW_FIO_CZ = "https://www.fio.cz/ib_api/rest/";
	private String token = "";
	private DataFormat dataFormat = DataFormat.XML;
	private String url = HTTPS_WWW_FIO_CZ;

	public FioClient(String token, DataFormat dataFormat) {
		this.token = token;
		this.dataFormat = dataFormat;
	}

	public FioClient() {

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

	public FioResult getTransactions(Calendar from, Calendar to) throws InvalidParametersException {
		if (from == null || to == null || from.after(to)) {
			throw new InvalidParametersException();
		}
		return null;
	}
}
