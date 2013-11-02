package fio.client;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FioClientTest {
	@Test
	public void createFioClient() {
		FioClient fc = new FioClient("xxx", DataFormat.xml);
		fc.setToken("yyy");
		fc.setUrl("https://www.fio.cz");
		fc.setDataFormat(DataFormat.xml);
		Assert.assertEquals(fc.getToken(), "yyy");
		Assert.assertEquals(fc.getUrl(), "https://www.fio.cz");
		Assert.assertEquals(fc.getDataFormat(), DataFormat.xml);
	}

	@Test
	public void createFioClientConstructor() {
		FioClient fc = new FioClient("xxx", DataFormat.xml);
		Assert.assertEquals(fc.getToken(), "xxx");
		Assert.assertEquals(fc.getDataFormat(), DataFormat.xml);
	}

	@Test
	public void createFioClientNullValues() {
		FioClient fc = new FioClient("xxx", DataFormat.xml);
		Assert.assertNotNull(fc.getDataFormat());
		Assert.assertNotNull(fc.getUrl());
		Assert.assertNotNull(fc.getToken());
	}
}
