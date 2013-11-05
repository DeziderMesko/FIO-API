package fio.client.result;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FioResultTest {
	private static final String DARK_STRING = "\u011B\u0161\u010D\u0159\u017E\u00FD\u00E1\u00ED\u00E9\u016F";
	private static final String HTTPS_FIO_CZ = "https://fio.cz";

	@Test
	public void initalizationTest() throws URISyntaxException {
		FioResult fr = new FioResult("Payload".getBytes(), FioResultFormat.json, HTTPS_FIO_CZ);
		Assert.assertNotNull(fr.getResponse());
		Assert.assertNotNull(fr.getDataFormat());
		Assert.assertNotNull(fr.getRequestURL());
	}

	@DataProvider(name = "encoding")
	public Object[][] dp() throws UnsupportedEncodingException {
		return new Object[][] { new Object[] { DARK_STRING.getBytes("Windows-1250"), FioResultFormat.json, false, "JSON + WIN1250" },
				new Object[] { DARK_STRING.getBytes("Windows-1250"), FioResultFormat.gpc, true, "GPC + WIN1250" },
				new Object[] { DARK_STRING.getBytes("UTF-8"), FioResultFormat.json, true, "JSON + UTF8" },
				new Object[] { DARK_STRING.getBytes("UTF-8"), FioResultFormat.gpc, false, "GPC + UTF8" } };
	}

	@Test(dataProvider = "encoding")
	public void encodingTest(byte[] payload, FioResultFormat format, boolean isOk, String msg) {
		FioResult fr = new FioResult(payload, format, HTTPS_FIO_CZ);
		Assert.assertEquals(fr.getResponseAsText().equals(DARK_STRING), isOk, msg);
	}

	@Test
	public void testBinary() throws UnsupportedEncodingException {
		FioResult fr = new FioResult(DARK_STRING.getBytes(), FioResultFormat.pdf, HTTPS_FIO_CZ);
		Assert.assertNull(fr.getResponseAsText());
		Assert.assertEquals(fr.getResponse(), DARK_STRING.getBytes());

		fr = new FioResult(DARK_STRING.getBytes("UTF-8"), FioResultFormat.xml, HTTPS_FIO_CZ);
		Assert.assertEquals(fr.getResponseAsText(), DARK_STRING);
		Assert.assertEquals(fr.getResponse(), DARK_STRING.getBytes("UTF-8"));

	}
}
