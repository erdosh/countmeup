package countmeup.countmeup;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
/**
 * This class is responsible for meeting rest requests and handling each request with the matching @Path object.
 * For example relative path for a vote action is simple "vote" and for result action "result"
 * this make our endpoint for a vote request as http://localhost:8181/countmeup/vote. Any logic related to Poll operations is not included here, for that purpose Poll class is used instead.
 * */
@Path("countmeup")
public class RestHandler {
	/**
	 * Get tester method for the Rest servlet.
	 * */
	@GET
	@Path("getservice")
	@Produces(MediaType.TEXT_PLAIN)
	public String restLocal() {
		return "GET Test method for \"countmeup\" Called!";
	}

	/**
	 * Rest handler method for handling voteRequest. Just parsing the JSonString and converting it to the relevant object, it delegates the vote logic to the voteForCandidate method of the Poll class.
	 * */
	@POST
	@Path("vote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response voteRequest(String json) {
		Gson gson = new Gson();
		Vote vote = gson.fromJson(json, Vote.class);
		boolean accepted = Poll.voteForCandidate(vote);
		return accepted ? Response.status(Status.CREATED).build() : Response.status(Status.FORBIDDEN).build();
	}

	/**
	 * Rest handler method for handling getResult requests. It delegates the result calculation logic to the getResults method of the Poll class and converts the ResultObject array to a JSonString and returns.
	 * */
	@POST
	@Path("result")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String resultRequest() {
		ResultObject [] result = Poll.getResults();
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
