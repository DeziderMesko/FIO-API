package fio.client;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class HttpConnectorTest {
	@Test(dataProvider = "dp", expectedExceptions = HttpRequestException.class, groups = { "integration" })
	public void getData(String address, String result) throws HttpRequestException {
		BasicHttpConnector hc = new BasicHttpConnector();
		String data = hc.getData(address);
		if (result != null) {
			Assert.assertEquals(data, result);
			throw new HttpRequestException(null); // all ok
		}
		Assert.fail("Exception expected");

	}

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
        
        ServerConnector https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory,"http/1.1"),
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
		        Pattern pattern = Pattern.compile("(?<=ib_api/rest/)[a-z-]*");
		        Matcher matcher = pattern.matcher(target);
		        String function = "";
		        if(matcher.matches()) {
		            function = matcher.group(1);
		        }
				switch (function) {
				case "periods":
				case "by-id":
				case "last":
				case "set-last-id":
				case "set-last-date":
					response.getWriter().println("Connected!");
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
		return new Object[][] { new Object[] { "http://localhost:60000", "Connected!" }, new Object[] { "invalid address", null }, };
	}
}
