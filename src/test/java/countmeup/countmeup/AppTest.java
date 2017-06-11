package countmeup.countmeup;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private String url = "http://localhost:8181/countmeup/";
	private static Map<String,String> headerMap = new HashMap<String,String>();
	private String voteServiceName = "vote";
	private String resultServiceName = "result";
	private TestPostClient client = new TestPostClient(); 


	@BeforeClass
	public static void setUp(){
		headerMap.put("Content-Type", "application/json");
		Thread th = new Thread( 
				new Runnable() {

					public void run() {
						AppServer.startServer();

					}
				});
		th.start();

	}
	@AfterClass
	public static void tearDown(){
		AppServer.shutDownServer();
	}
	@Test
	public void test() throws ParseException, IOException, InterruptedException{
		/*wait till we are sure that server has been started*/
		while(!AppServer.isRunning()){
			Thread.sleep(5000);
		}
		voteTests();
		resultTests();
		/*when running below test please comment out above test*/
		//concurrentTest();
	}

	public void voteTestOneThreadSample(){
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user1","A"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user2","A"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user3","A"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user4","A"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user5","A"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user6","A"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user7","A"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user8","B"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user9","B"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user10","B"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user11","B"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user12","B"));				
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user13","B"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user14","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user15","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user16","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user17","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user18","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user19","C"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user20","D"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user21","D"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user22","D"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user23","D"));
		client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user24","D"));
	}


	public void concurrentTest() throws InterruptedException, ParseException, IOException{
		Thread th_1 = new Thread(new Runnable() {	
			public void run() {
				voteTestOneThreadSample();
			}
		});				
		Thread th_2 = new Thread(new Runnable() {	
			public void run() {
				voteTestOneThreadSample();		
			}
		});
		Thread th_3 = new Thread(new Runnable() {	
			public void run() {
				voteTestOneThreadSample();	
			}
		});

		th_1.start();
		th_2.start();
		th_3.start();

		th_1.join();
		th_2.join();
		th_3.join();
		
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"A\",\"voteCount\":\"21\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"B\",\"voteCount\":\"18\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"C\",\"voteCount\":\"18\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"D\",\"voteCount\":\"15\""));
	}

	public void voteTests() {

		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user1","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user1","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user1","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user1","A")).getStatusLine().getStatusCode()==HttpStatus.SC_FORBIDDEN);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user2","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user2","B")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user2","C")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user2","D")).getStatusLine().getStatusCode()==HttpStatus.SC_FORBIDDEN);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user3","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user3","B")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user3","C")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user4","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user4","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user4","A")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user5","B")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user5","B")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user5","B")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user6","C")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);
		Assert.assertTrue(client.restPostExecute(url.concat(voteServiceName), headerMap, new Vote("user6","D")).getStatusLine().getStatusCode()==HttpStatus.SC_CREATED);


	}

	public void resultTests() throws ParseException, IOException{
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"A\",\"voteCount\":\"8\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"B\",\"voteCount\":\"5\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"C\",\"voteCount\":\"3\""));
		Assert.assertTrue(EntityUtils.toString(client.restPostExecute(url.concat(resultServiceName), headerMap, null).getEntity()).contains("\"candidateName\":\"D\",\"voteCount\":\"1\""));

	}
}
