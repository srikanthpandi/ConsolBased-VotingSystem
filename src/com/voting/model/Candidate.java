
package com.voting.model;

public class Candidate {

    private int candidateId;
    private String candidateName;

    public Candidate(int candidateId, String candidateName) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    @Override
    public String toString() {
        return "Candidate ID: " + candidateId
                + ", Name: " + candidateName;
    }
}
