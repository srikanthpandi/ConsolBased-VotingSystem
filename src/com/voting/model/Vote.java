
package com.voting.model;

public class Vote {
    private int electionId;
    private int voterId;
    private int candidateId;

    public Vote(int electionId, int voterId, int candidateId) {
        this.electionId = electionId;
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public int getElectionId() {
        return electionId;
    }

    public int getVoterId() {
        return voterId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    @Override
    public String toString() {
        return "Election ID: " + electionId
                + ", Voter ID: " + voterId
                + ", Candidate ID: " + candidateId;
    }
}
