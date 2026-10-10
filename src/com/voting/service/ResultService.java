
package com.voting.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voting.DBConnection;
import com.voting.dsa.ResultSorter;
import com.voting.dsa.ResultSorter.CandidateResult;

public class ResultService {

    private final ResultSorter sorter = new ResultSorter();

    public Map<Integer, Integer> getVoteCounts(int electionId)
            throws SQLException {

        Map<Integer, Integer> voteCounts = new HashMap<>();

        String sql =
                "SELECT c.candidate_id, COUNT(v.vote_id) AS total_votes " +
                "FROM candidates c " +
                "LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                "AND v.election_id = ? " +
                "GROUP BY c.candidate_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, electionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    voteCounts.put(
                            rs.getInt("candidate_id"),
                            rs.getInt("total_votes"));
                }
            }
        }

        return voteCounts;
    }

    public List<CandidateResult> getSortedResults(int electionId)
            throws SQLException {

        List<CandidateResult> results = new ArrayList<>();

        String sql =
                "SELECT c.candidate_id, c.candidate_name, " +
                "COUNT(v.vote_id) AS total_votes " +
                "FROM candidates c " +
                "LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                "AND v.election_id = ? " +
                "GROUP BY c.candidate_id, c.candidate_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, electionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new CandidateResult(
                            rs.getInt("candidate_id"),
                            rs.getString("candidate_name"),
                            rs.getInt("total_votes")));
                }
            }
        }

        sorter.mergeSort(results);

        return results;
    }

    public void displayResults(int electionId) {
        try {
            List<CandidateResult> results = getSortedResults(electionId);

            if (results.isEmpty()) {
                System.out.println("No candidates registered.");
                return;
            }

            System.out.println("\n========== ELECTION RESULTS ==========");

            int rank = 1;

            for (CandidateResult result : results) {
                System.out.println(rank + ". " + result);
                rank++;
            }

            System.out.println("======================================");

        } catch (SQLException e) {
            System.out.println("Unable to retrieve results: "
                    + e.getMessage());
        }
    }
}
