package fio.client.result;

import java.net.URI;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import fio.client.result.FioResultFormat;
import fio.client.result.FioResult;

public class FioResultTest {
  @Test
  public void initalizationTest() throws URISyntaxException {
	  FioResult fr = new FioResult("Payload", FioResultFormat.json, "http://fio.cz");
	  Assert.assertNotNull(fr.getResponse());
	  Assert.assertNotNull(fr.getDataFormat());
	  Assert.assertNotNull(fr.getRequestURI());
  }
}
