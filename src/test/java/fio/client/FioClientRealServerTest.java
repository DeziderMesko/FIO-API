package fio.client;

import java.util.Calendar;

import org.testng.annotations.Test;

import fio.client.https.HttpsRequestException;
import fio.client.result.FioResult;

public class FioClientRealServerTest {

	@Test(groups = { "Real" })
	public void getDateRangeTransactions() throws InvalidParametersException, HttpsRequestException {
		String token = System.getenv("TOKEN");
		FioClient fc = new FioClient(token, FioConstants.AnswerFormat.json);
		Calendar from = Calendar.getInstance();
		from.set(2013, 6, 15);
		Calendar to = Calendar.getInstance();
		to.set(2013, 6, 18);
		FioResult fr = fc.getTransactions(from, to);

		System.out.println(fr.getResponseAsText());
	}
}
