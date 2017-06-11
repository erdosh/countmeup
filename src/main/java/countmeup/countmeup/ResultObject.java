package countmeup.countmeup;

/**
 * We return result of the poll as a JSon String which is a JSon Array type of ResultObjects,
 * namely each element of the result array will consist of the candidate's name and vote count.
 * */
public class ResultObject {
private String candidateName;
private String voteCount;

public String getCandidateName() {
	return candidateName;
}
public void setCandidateName(String candidateName) {
	this.candidateName = candidateName;
}
public String getVoteCount() {
	return voteCount;
}
public void setVoteCount(String voteCount) {
	this.voteCount = voteCount;
}
}
