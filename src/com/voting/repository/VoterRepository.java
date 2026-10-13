
package com.voting.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.voting.DBConnection;
import com.voting.model.Voter;

public class VoterRepository {

    // Save a new voter to MySQL
    public boolean saveVoter(Voter voter) throws SQLException {

        String sql =
                "INSERT INTO voters (voter_id, voter_name) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, voter.getVoterId());
            ps.setString(2, voter.getVoterName());

            return ps.executeUpdate() == 1;
        }
    }

    // Load all registered voters from MySQL
    public Map<Integer, Voter> getAllVoters() throws SQLException {

        Map<Integer, Voter> voters = new HashMap<>();

        String sql = "SELECT voter_id, voter_name FROM voters";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("voter_id");
                String name = rs.getString("voter_name");

                voters.put(id, new Voter(id, name));
            }
        }

        return voters;
    }
}
