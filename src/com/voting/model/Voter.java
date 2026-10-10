
package com.voting.model;

public class Voter {

    private int voterId;
    private String voterName;

    public Voter(int voterId, String voterName) {
        this.voterId = voterId;
        this.voterName = voterName;
    }

    public int getVoterId() {
        return voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    @Override
    public String toString() {
        return "Voter ID: " + voterId
                + ", Name: " + voterName;
    }
}
