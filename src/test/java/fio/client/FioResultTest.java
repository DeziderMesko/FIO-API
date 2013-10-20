package fio.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FioResultTest {
  @Test
  public void initalizationTest() throws URISyntaxException {
	  FioResult fr = new FioResult("Payload", DataFormat.JSON, new URI("http://fio.cz"));
	  Assert.assertNotNull(fr.getResponse());
	  Assert.assertNotNull(fr.getDataFormat());
	  Assert.assertNotNull(fr.getRequestURI());
  }
}
