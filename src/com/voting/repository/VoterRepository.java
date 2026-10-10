
package com.voting.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.voting.DBConnection;
import com.voting.model.Voter;

public class VoterRepository {

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
}
