package countmeup.countmeup;
/**
 * This class is the class representing any vote.
 * A vote request coming as a rest request can be parsed as a json String and when parsed, the resulting objects is the Vote object defined here.
 * */
public class Vote {
	public Vote(String voterID, String candidateName) {
		super();
		this.voterID = voterID;
		this.candidateName = candidateName;
	}
	private String voterID;
	private String candidateName;
	public String getVoterID() {
		return voterID;
	}
	public void setVoterID(String voterID) {
		this.voterID = voterID;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
}
