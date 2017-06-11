package countmeup.countmeup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class TestPostClient {

	public static void main(String[] args) {
		String url = "http://localhost:8181/countmeup/";
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		String voteServiceName = "vote";
		String resultServiceName = "result";
		TestPostClient client = new TestPostClient(); 
		//client.restPostExecute(url.concat(resultServiceName), null, null);
		HttpResponse response=null;
		Vote vote = new Vote("erdosh", "A");
		response = client.restPostExecute(url.concat(voteServiceName), headerMap, vote);
		response = client.restPostExecute(url.concat(voteServiceName), headerMap, vote);
		response = client.restPostExecute(url.concat(voteServiceName), headerMap, vote);
		response = client.restPostExecute(url.concat(voteServiceName), headerMap, vote);
		
		response = client.restPostExecute(url.concat(resultServiceName), headerMap, null);
		try {
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public HttpResponse restPostExecute(String url,Map<String,String> headers, Object postInput){
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		Gson gson = new Gson();
		StringEntity entity=null;
		if(postInput != null){
			try {
				entity = new StringEntity( gson.toJson(postInput));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		post.setEntity(entity);
		if(headers != null){
			Iterator<Entry<String,String>> iter = headers.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String,String> entry = iter.next();
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpResponse response =null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
