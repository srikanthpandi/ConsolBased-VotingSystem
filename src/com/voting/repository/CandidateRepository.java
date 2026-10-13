
package com.voting.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.voting.DBConnection;
import com.voting.model.Candidate;

public class CandidateRepository {

    // Save a candidate and return the generated ID
    public int saveCandidate(Candidate candidate) throws SQLException {

        String sql =
                "INSERT INTO candidates (candidate_name) VALUES (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, candidate.getCandidateName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // Load all candidates from MySQL
    public Map<Integer, Candidate> getAllCandidates()
            throws SQLException {

        Map<Integer, Candidate> candidates = new HashMap<>();

        String sql =
                "SELECT candidate_id, candidate_name FROM candidates";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("candidate_id");
                String name = rs.getString("candidate_name");

                candidates.put(id, new Candidate(id, name));
            }
        }

        return candidates;
    }
}
