package fio.client.https;

import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BasicHttpsConnectorTest {
	Server server = null;

	@BeforeClass
	public void beforeClass() throws Exception {

		server = new Server(0);

		HttpConfiguration https_config = new HttpConfiguration();
		https_config.setSecureScheme("https");
		https_config.setSecurePort(8443);
		https_config.setOutputBufferSize(32768);
		https_config.addCustomizer(new SecureRequestCustomizer());

		SslContextFactory sslContextFactory = new SslContextFactory();
		URL kstore = this.getClass().getResource("/keystore");
		sslContextFactory.setKeyStorePath(kstore.getPath());
		sslContextFactory.setKeyStorePassword("httptest");

		ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(https_config));
		https.setPort(8443);
		https.setIdleTimeout(500000);

		server.setConnectors(new Connector[] { https });

		server.setHandler(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				response.setContentType("text/html;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				baseRequest.setHandled(true);
				Pattern pattern = Pattern.compile("(/ib_api/rest/)([a-z-]*)(/.*)");
				String function = "";
				Matcher matcher = pattern.matcher(target);
				if (matcher.matches()) {
					function = matcher.group(2);
				}
				switch (function) {
				case "periods":
				case "by-id":
				case "last":
				case "set-last-id":
				case "set-last-date":
					response.getWriter().print("Connected!");
					break;
				default:
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}

			}
		});
		server.start();
	}

	@AfterClass
	public void afterClass() throws Exception {
		if (server != null) {
			server.stop();
			server.destroy();
		}
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] {
				new Object[] { "https://localhost:8443/ib_api/rest/last/TOKEN/transactions.json", "Connected!".getBytes(), false },
				new Object[] { "https://localhost:8443/INVALID/transactions.json", null, true },
				new Object[] { "invalid address", null, true } };
	}

	@Test(dataProvider = "dp", groups = { "Jetty" })
	public void getData(String address, byte[] result, boolean exceptionExpected) throws Exception {
		BasicHttpsConnector hc = new BasicHttpsConnector();
		hc.setDefaultSSLSocketFactory(getDefaultSSLSocketFactory());
		try {
			byte[] data = hc.getData(address);
			if (result != null) {
				Assert.assertEquals(data, result);
			}
		} catch (HttpsRequestException e) {
			if (!exceptionExpected) {
				Assert.fail("Exception not expected");
			}
		}
	}

	/**
	 * Disable SSL cert checking as described on <br>
	 * {@link http://www.nakov.com/blog/2009/07/16/
	 * disable-certificate-validation-in-java-ssl-connections/}
	 * 
	 * @return
	 */
	private SSLSocketFactory getDefaultSSLSocketFactory() throws Exception {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());

		return sc.getSocketFactory();
	}
}
