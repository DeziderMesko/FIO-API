package fio.client;


import org.testng.Assert;
import org.testng.annotations.Test;

public class FioClientTest {
  @Test
  public void createFioClient() {
	  FioClient fc = new FioClient();
	  fc.setToken("xxx");
	  fc.setUrl("https://www.fio.cz");
	  fc.setDataFormat(DataFormat.XML);
	  Assert.assertEquals(fc.getToken(), "xxx");
	  Assert.assertEquals(fc.getUrl(), "https://www.fio.cz");
	  Assert.assertEquals(fc.getDataFormat(), DataFormat.XML);
  }
  @Test
  public void createFioClientConstructor(){
	  FioClient fc = new FioClient("xxx", DataFormat.XML);
	  Assert.assertEquals(fc.getToken(), "xxx");
	  Assert.assertEquals(fc.getDataFormat(), DataFormat.XML);
  }
  @Test
  public void createFioClientNullValues(){
	  FioClient fc = new FioClient();
	  Assert.assertNotNull(fc.getDataFormat());
	  Assert.assertNotNull(fc.getUrl());
	  Assert.assertNotNull(fc.getToken());
  }
}
