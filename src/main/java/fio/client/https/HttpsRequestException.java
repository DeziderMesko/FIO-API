package fio.client.https;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Vyjimka pokryvajici {@link MalformedURLException} a {@link IOException}
 * 
 * @author dezider.mesko
 * 
 */
public class HttpsRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public HttpsRequestException(Exception e) {
		super(e);
	}
}
