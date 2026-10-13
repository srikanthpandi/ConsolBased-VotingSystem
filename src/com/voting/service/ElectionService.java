
package com.voting.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.voting.DBConnection;

public class ElectionService {

    public boolean updateElectionStatus(int electionId, String status) {

        if (electionId <= 0) {
            System.out.println("Invalid election ID.");
            return false;
        }

        if (status == null ||
                (!status.equalsIgnoreCase("OPEN")
                && !status.equalsIgnoreCase("CLOSED"))) {
            System.out.println("Status must be OPEN or CLOSED.");
            return false;
        }

        String sql =
                "UPDATE elections SET status = ? WHERE election_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toUpperCase());
            ps.setInt(2, electionId);

            int rows = ps.executeUpdate();

            if (rows == 1) {
                System.out.println(
                        "Election " + electionId + " status updated to "
                        + status.toUpperCase() + ".");
                return true;
            }

            System.out.println("Election not found.");
            return false;

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}
