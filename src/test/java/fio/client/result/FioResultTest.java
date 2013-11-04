package fio.client.result;

import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FioResultTest {
	@Test
	public void initalizationTest() throws URISyntaxException {
		FioResult fr = new FioResult("Payload".getBytes(), FioResultFormat.json, "http://fio.cz");
		Assert.assertNotNull(fr.getResponse());
		Assert.assertNotNull(fr.getDataFormat());
		Assert.assertNotNull(fr.getRequestURL());
	}
}
