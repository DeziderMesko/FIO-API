package fio.client;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fio.client.FioConstants.AnswerFormat;
import fio.client.FioConstants.OrderFormat;
import fio.client.https.BasicHttpsConnector;
import fio.client.https.HttpsRequestException;
import fio.client.result.FioResult;

public class FioClientApiFunctionsTest {
	/**
	 * 
	 */
	private static final String SOME_DATA = "Some data";
	FioClient fc = new FioClient("1234567890ABCDEF", AnswerFormat.json);;
	Calendar from;
	Calendar to;

	@Mock
	BasicHttpsConnector connector;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		fc.setUrl("https://localhost:8443/ib_api/rest/");
		fc.setHttpConnector(connector);
		from = Calendar.getInstance();
		to = Calendar.getInstance();
		to.add(Calendar.MONTH, 1);
	}

	@Test
	public void getDateRangeTransactions() throws InvalidParametersException, URISyntaxException, HttpsRequestException {
		FioResult fr = fc.getTransactions(from, to);
		Assert.assertNotNull(fr);
		fr = fc.getTransactions(from, from);
		Assert.assertNotNull(fr);
	}

	@DataProvider(name = "invalidDates")
	public Object[][] invalidDates() {
		return new Object[][] { { to, from }, { null, to }, { from, null }, { null, null } };
	}

	@DataProvider(name = "dates")
	public Object[][] dates() {
		return new Object[][] { { to }, { null } };
	}

	@Test(expectedExceptions = InvalidParametersException.class, dataProvider = "invalidDates")
	public void getDateRangeTransactionsInvalid(Calendar date1, Calendar date2) throws InvalidParametersException, URISyntaxException,
			HttpsRequestException {
		fc.getTransactions(date1, date2);
	}

	@Test
	public void getDateRangeTransactionsValid() throws InvalidParametersException, URISyntaxException, HttpsRequestException {
		FioResult fr = fc.getTransactions(from, to);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/periods/.+/[0-9-]+/[0-9-]+/transactions.json"));
	}

	@Test
	public void getStatement() throws HttpsRequestException {
		int year = 2013;
		int statementNumber = 1;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn(SOME_DATA.getBytes());
		FioResult fr = fc.getStatement(year, statementNumber);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/by-id/.+/[0-9]{4}/[0-9]+/transactions.json"));
	}

	@Test
	public void getNewTransactions() throws HttpsRequestException {
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn(SOME_DATA.getBytes());
		FioResult fr = fc.getNewTransactions();
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/last/.+/transactions.json"));
	}

	@Test
	public void setTransactionPointerById() throws HttpsRequestException {
		int pointer = 0;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn(SOME_DATA.getBytes());
		FioResult fr = fc.setTransactionPointerById(pointer);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/set-last-id/.+/[0-9]+/"));
	}

	@Test(dataProvider = "dates")
	public void setTransactionPointerByDate(Calendar date1) throws HttpsRequestException, InvalidParametersException {
		Calendar date = date1;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn(SOME_DATA.getBytes());
		FioResult fr = null;
		try {
			fr = fc.setTransactionPointerByDate(date);
			Assert.assertNotNull(fr);
			Mockito.verify(connector).getData(Mockito.matches(".*/set-last-date/.+/[0-9-]+/"));
		} catch (InvalidParametersException ipe) {
			Assert.assertNull(fr);
			Assert.assertNull(date1);
		}
	}

	@DataProvider(name = "sendOrder")
	public Object[][] sendOrder() {
		return new Object[][] { { "some Order", OrderFormat.abo, false }, { null, null, true }, { "some Order", null, true },
				{ null, OrderFormat.xml, true }, { "some Order", OrderFormat.xml, false } };
	}

	@Test(dataProvider = "sendOrder")
	public void sendRequest(String order, OrderFormat format, Boolean exceptionExpected) throws HttpsRequestException {
		Mockito.when(connector.getPostData(Mockito.anyString(), Mockito.anyMapOf(String.class, String.class))).thenReturn(
				SOME_DATA.getBytes());
		FioResult fr = null;
		try {
			fr = fc.sendOrder(order, format);
			Assert.assertNotNull(fr);
			Mockito.verify(connector).getPostData(Mockito.matches(".*/import/"), Mockito.argThat(new isCorrectHashMap()));
		} catch (InvalidParametersException e) {
			if (!exceptionExpected) {
				Assert.fail();
			}
		}
	}

	class isCorrectHashMap extends ArgumentMatcher<Map<String, String>> {
		@Override
		public boolean matches(Object argument) {
			HashMap<String, String> map = (HashMap<String, String>) argument;
			return map.containsKey("lng") && map.containsKey("token") && map.get("token") != null && map.containsKey("type")
					&& map.get("type") != null && map.containsKey("file") && map.get("file") != null && map.containsKey("filename");
		}
	}
}
