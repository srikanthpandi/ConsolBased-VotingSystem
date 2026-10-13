
package com.voting.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.voting.DBConnection;
import com.voting.dsa.VoteQueue;
import com.voting.model.Vote;
import com.voting.repository.VotingRepository;

public class VotingService {

    private final VotingRepository repository =
            new VotingRepository();

    private final VoteQueue voteQueue = new VoteQueue();

    private final Set<String> votedVoters = new HashSet<>();

    // Check election status directly in MySQL
    private boolean isElectionOpen(int electionId) throws SQLException {

        String sql =
                "SELECT status FROM elections WHERE election_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, electionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "OPEN".equalsIgnoreCase(
                            rs.getString("status"));
                }
            }
        }

        return false;
    }

    public boolean castVote(int electionId, int voterId, int candidateId) {

        if (electionId <= 0 || voterId <= 0 || candidateId <= 0) {
            System.out.println("All IDs must be positive.");
            return false;
        }

        String voterKey = electionId + "-" + voterId;

        if (votedVoters.contains(voterKey)) {
            System.out.println(
                    "You have already voted in this election.");
            return false;
        }

        try {
            // Check the election BEFORE adding the vote to the queue.
            if (!isElectionOpen(electionId)) {
                System.out.println(
                        "Election is closed or does not exist. Vote rejected.");
                return false;
            }

            Vote vote = new Vote(electionId, voterId, candidateId);

            voteQueue.addVote(vote);

            Vote nextVote = voteQueue.processNextVote();

            if (nextVote == null) {
                System.out.println("No vote available for processing.");
                return false;
            }

            boolean success = repository.castVote(nextVote);

            if (success) {
                votedVoters.add(voterKey);
            }

            return success;

        } catch (SQLException e) {
            System.out.println(
                    "Database error: " + e.getMessage());
            return false;
        }
    }
}
