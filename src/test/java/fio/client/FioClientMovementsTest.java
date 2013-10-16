package fio.client;

import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FioClientMovementsTest {
	FioClient fc;
	Calendar from;
	Calendar to;

	@BeforeMethod
	public void beforeMethod() {
		fc = new FioClient();
		from = Calendar.getInstance();
		to = Calendar.getInstance();
		to.add(Calendar.MONTH, 1);
	}

	@Test
	public void getDateRangeTransactions() {
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
	public void getDateRangeTransactionsInvalid(Calendar date1, Calendar date2) {
		FioResult fr = fc.getTransactions(date1, date2);
	}

}
