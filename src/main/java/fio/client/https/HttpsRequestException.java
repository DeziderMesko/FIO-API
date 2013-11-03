package fio.client.https;


public class HttpsRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	public HttpsRequestException(Exception e) {
		super(e);
	}
}
