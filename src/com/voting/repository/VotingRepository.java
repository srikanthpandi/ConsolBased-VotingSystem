
package com.voting.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.voting.DBConnection;
import com.voting.model.Vote;

public class VotingRepository {

    public boolean castVote(Vote vote) throws SQLException {

        String electionSql =
                "SELECT status FROM elections WHERE election_id = ?";

        String voterSql =
                "SELECT voter_name FROM voters WHERE voter_id = ?";

        String candidateSql =
                "SELECT candidate_id FROM candidates WHERE candidate_id = ?";

        String duplicateSql =
                "SELECT vote_id FROM votes WHERE election_id = ? AND voter_id = ?";

        String insertSql =
                "INSERT INTO votes (election_id, voter_id, candidate_id) VALUES (?, ?, ?)";

        String voterName;

        try (Connection con = DBConnection.getConnection()) {

            // 1. Check whether the election exists and is open.
            try (PreparedStatement ps = con.prepareStatement(electionSql)) {
                ps.setInt(1, vote.getElectionId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Election not found.");
                        return false;
                    }

                    if (!"OPEN".equalsIgnoreCase(rs.getString("status"))) {
                        System.out.println("This election is closed.");
                        return false;
                    }
                }
            }

            // 2. Check whether the voter exists and get their name.
            try (PreparedStatement ps = con.prepareStatement(voterSql)) {
                ps.setInt(1, vote.getVoterId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println(
                                "Voter ID not found. Register first.");
                        return false;
                    }

                    voterName = rs.getString("voter_name");
                }
            }

            // 3. Check whether the candidate exists.
            try (PreparedStatement ps = con.prepareStatement(candidateSql)) {
                ps.setInt(1, vote.getCandidateId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Candidate ID not found.");
                        return false;
                    }
                }
            }

            // 4. Check whether the voter has already voted.
            try (PreparedStatement ps = con.prepareStatement(duplicateSql)) {
                ps.setInt(1, vote.getElectionId());
                ps.setInt(2, vote.getVoterId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                                "You have already voted in this election.");
                        return false;
                    }
                }
            }

            // 5. Insert the vote into the database.
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, vote.getElectionId());
                ps.setInt(2, vote.getVoterId());
                ps.setInt(3, vote.getCandidateId());

                int rows = ps.executeUpdate();

                if (rows == 1) {
                    System.out.println(
                            voterName + " successfully voted!");
                    return true;
                }
            }
        }

        return false;
    }
}
