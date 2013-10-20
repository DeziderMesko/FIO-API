package fio.client;

import java.net.URISyntaxException;
import java.util.Calendar;

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
		fc = new FioClient("1234567890ABCDEF", DataFormat.JSON);
		from = Calendar.getInstance();
		to = Calendar.getInstance();
		to.add(Calendar.MONTH, 1);
	}

	@Test
	public void getDateRangeTransactions() throws InvalidParametersException, URISyntaxException {
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
	public void getDateRangeTransactionsInvalid(Calendar date1, Calendar date2) throws InvalidParametersException, URISyntaxException {
		FioResult fr = fc.getTransactions(date1, date2);
	}
	
	@Test
	public void getStatement(){
		int year = 2013;
		int statementNumber = 1;
		FioResult fr = fc.getStatement(year, statementNumber);
	}
	
	@Test
	public void getNewTransactions(){
		FioResult fr = fc.getNewTransactions();
	}

	@Test
	public void setTransactionPointerById(){
		int pointer = 0;
		FioResult fr = fc.setTransactionPointerById(pointer);
	}

	@Test
	public void setTransactionPointerByDate(){
		Calendar date = null;
		FioResult fr = fc.setTransactionPointerByDate(date);
	}
}
