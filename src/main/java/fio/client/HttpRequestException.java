package fio.client;


public class HttpRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	public HttpRequestException(Exception e) {
		super(e);
	}
}
