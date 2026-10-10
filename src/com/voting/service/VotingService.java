
package com.voting.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.voting.model.Vote;
import com.voting.repository.VotingRepository;

public class VotingService {

    private final VotingRepository repository = new VotingRepository();

    // DSA concept: HashSet provides fast membership checks.
    private final Set<String> votedVoters = new HashSet<>();

    public boolean castVote(int electionId, int voterId, int candidateId) {

        if (electionId <= 0 || voterId <= 0 || candidateId <= 0) {
            System.out.println("All IDs must be positive.");
            return false;
        }

        String voterKey = electionId + "-" + voterId;

        // Fast duplicate check within this application session.
        if (votedVoters.contains(voterKey)) {
            System.out.println("You have already voted in this election.");
            return false;
        }

        Vote vote = new Vote(electionId, voterId, candidateId);

        try {
            boolean success = repository.castVote(vote);

            if (success) {
                votedVoters.add(voterKey);
            }

            return success;

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println("Duplicate vote or database constraint violation.");
            } else {
                System.out.println("Database error: " + e.getMessage());
            }

            return false;
        }
    }
}
