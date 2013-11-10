package fio.client;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FioClientTest {
	@Test
	public void createFioClient() {
		FioClient fc = new FioClient("xxx", FioConstants.AnswerFormat.xml);
		fc.setToken("yyy");
		fc.setUrl("https://www.fio.cz");
		fc.setAnswerFormat(FioConstants.AnswerFormat.xml);
		Assert.assertEquals(fc.getToken(), "yyy");
		Assert.assertEquals(fc.getUrl(), "https://www.fio.cz");
		Assert.assertEquals(fc.getAnswerFormat(), FioConstants.AnswerFormat.xml);
	}

	@Test
	public void createFioClientConstructor() {
		FioClient fc = new FioClient("xxx", FioConstants.AnswerFormat.xml);
		Assert.assertEquals(fc.getToken(), "xxx");
		Assert.assertEquals(fc.getAnswerFormat(), FioConstants.AnswerFormat.xml);
	}

	@Test
	public void createFioClientNullValues() {
		FioClient fc = new FioClient("xxx", FioConstants.AnswerFormat.xml);
		Assert.assertNotNull(fc.getAnswerFormat());
		Assert.assertNotNull(fc.getUrl());
		Assert.assertNotNull(fc.getToken());
	}
}
