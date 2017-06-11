package countmeup.countmeup;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 * This class is responsible for all of the Poll logic operations like registering a vote, applying necessary checking, calculating the results etc.
 * */
public class Poll {
	private static List<ConcurrentLinkedQueue<String>> pollCollector = new ArrayList<ConcurrentLinkedQueue<String>>();
	private static HashMap<String,Integer> candidateNameIndexMap = new HashMap<String,Integer>();
	private static HashMap<String, Integer> voterVoteCountMap = new HashMap<String, Integer>(); 
	private static int candiateIndex;
	private final static int MAX_VOTE_COUNT_PER_USER = 3;

	static{
		Poll.registerNewCandidate("A");
		Poll.registerNewCandidate("B");
		Poll.registerNewCandidate("C");
		Poll.registerNewCandidate("D");
	}
	public static void registerNewCandidate(String candidateName){
		if(candidateNameIndexMap.containsKey(candidateName)){
			return;
		}
		ConcurrentLinkedQueue<String> thisCandidatesPoll = new ConcurrentLinkedQueue<String>();
		pollCollector.add(thisCandidatesPoll);
		candidateNameIndexMap.put(candidateName, candiateIndex++);
	}
	
	/**
	 * Method responsible for a single vote operation logic. Designed to be synchronized as there may be concurrent vote request. 
	 * In such a condition we must avoid miscalculations on for example incrementing vote count per user. 
	 * */
	public synchronized static boolean voteForCandidate(Vote vote){
		Integer candidateListIndex = candidateNameIndexMap.get(vote.getCandidateName());
		if(candidateListIndex == null){
			return false;
		}
		Integer voterVoteCount = voterVoteCountMap.get(vote.getVoterID());
		if(voterVoteCount == null){
			voterVoteCount = new Integer(0);
		}
		else if(voterVoteCount >= MAX_VOTE_COUNT_PER_USER){
			return false;
		}
		pollCollector.get(candidateListIndex).add(vote.getVoterID());
		voterVoteCountMap.put(vote.getVoterID(), ++voterVoteCount);
		
		return true; 
	}
	
	/**
	 * In order to present a reasonably clean result format, we return a ResultObject array, within each element of the array a mapping of the candidate name and vote count of that candidate.
	 * This array will be converted to the JSon Array at outer level where this getResults method is called.
	 * */
	public static ResultObject [] getResults(){
		ResultObject [] result =  new ResultObject[pollCollector.size()];
		Iterator<String> candidateNameIndexKeysIter = candidateNameIndexMap.keySet().iterator();
		while(candidateNameIndexKeysIter.hasNext()){
			String candidateName = candidateNameIndexKeysIter.next();
			int index = candidateNameIndexMap.get(candidateName);
			ResultObject resultObject = new ResultObject();
			resultObject.setCandidateName(candidateName);
			resultObject.setVoteCount(Integer.toString(pollCollector.get(index).size()));
			result[index] = resultObject;
		}
		return result;
	}
	
}
