package fio.client;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FioClientApiFunctionsTest {
	FioClient fc;
	Calendar from;
	Calendar to;

	@BeforeMethod
	public void beforeMethod() {
		fc = new FioClient("1234567890ABCDEF", DataFormat.json);
		fc.setUrl("https://localhost:8443/ib_api/rest/");

		from = Calendar.getInstance();
		to = Calendar.getInstance();
		to.add(Calendar.MONTH, 1);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDateRangeTransactions() throws InvalidParametersException, URISyntaxException, HttpRequestException {
		FioResult fr = fc.getTransactions(from, to);
		Assert.assertNotNull(fr);
		fr = fc.getTransactions(from, from);
		Assert.assertNotNull(fr);
	}

	@DataProvider(name = "invalidDates")
	public Object[][] invalidDates() {
		return new Object[][] { { to, from }, { null, to }, { from, null }, { null, null } };
	}

	@Test(expectedExceptions = InvalidParametersException.class, dataProvider = "invalidDates")
	public void getDateRangeTransactionsInvalid(Calendar date1, Calendar date2) throws InvalidParametersException, URISyntaxException, HttpRequestException {
		FioResult fr = fc.getTransactions(date1, date2);
	}
	
	@Mock
	BasicHttpConnector connector;
	
	@Test
	public void getStatement() throws HttpRequestException{
		int year = 2013;
		int statementNumber = 1;
		fc.setHttpConnector(connector);
		FioResult fr = fc.getStatement(year, statementNumber);
		Assert.assertNotNull(fr);
		Mockito.verify(connector).getData(Mockito.anyString());
		
	}
	
	@Test
	public void getNewTransactions() throws HttpRequestException{
		FioResult fr = fc.getNewTransactions();
	}

	@Test
	public void setTransactionPointerById() throws HttpRequestException{
		int pointer = 0;
		FioResult fr = fc.setTransactionPointerById(pointer);
	}

	@Test(expectedExceptions = InvalidParametersException.class)
	public void setTransactionPointerByDate() throws HttpRequestException, InvalidParametersException{
		Calendar date = null;
		FioResult fr = fc.setTransactionPointerByDate(date);
	}
}
