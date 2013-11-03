package fio.client;

import java.net.URISyntaxException;
import java.util.Calendar;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import fio.client.https.BasicHttpsConnector;
import fio.client.https.HttpsRequestException;
import fio.client.result.FioResultFormat;
import fio.client.result.FioResult;

public class FioClientApiFunctionsTest {
	FioClient fc = new FioClient("1234567890ABCDEF", FioResultFormat.json);;
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
	public void getDateRangeTransactionsInvalid(Calendar date1, Calendar date2) throws InvalidParametersException, URISyntaxException, HttpsRequestException {
		fc.getTransactions(date1, date2);
	}

	@Test
	public void getDateRangeTransactionsValid() throws InvalidParametersException, URISyntaxException, HttpsRequestException {
		FioResult fr = fc.getTransactions(from, to);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/periods/.+/[0-9-]+/[0-9-]+/transactions.json"));
	}
	
	
	@Test
	public void getStatement() throws HttpsRequestException{
		int year = 2013;
		int statementNumber = 1;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn("Some data");
		FioResult fr = fc.getStatement(year, statementNumber);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/by-id/.+/[0-9]{4}/[0-9]+/transactions.json"));
	}
	
	@Test
	public void getNewTransactions() throws HttpsRequestException{
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn("Some data");
		FioResult fr = fc.getNewTransactions();
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/last/.+/transactions.json"));
	}

	@Test
	public void setTransactionPointerById() throws HttpsRequestException{
		int pointer = 0;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn("Some data");
		FioResult fr = fc.setTransactionPointerById(pointer);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.matches(".*/set-last-id/.+/[0-9]+/"));
	}

	@Test(dataProvider = "dates")
	public void setTransactionPointerByDate(Calendar date1) throws HttpsRequestException, InvalidParametersException{
		Calendar date = date1;
		Mockito.when(connector.getData(Mockito.anyString())).thenReturn("Some data");
		FioResult fr = null;
		try {
			fr = fc.setTransactionPointerByDate(date);
			Assert.assertNotNull(fr);
			Mockito.verify(connector).getData(Mockito.matches(".*/set-last-date/.+/[0-9-]+/"));
		} catch (InvalidParametersException ipe){
			Assert.assertNull(fr);
			Assert.assertNull(date1);
		}
	}
}
