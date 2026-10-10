
package com.voting.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.voting.model.Voter;
import com.voting.repository.VoterRepository;

public class VoterService {

    private final VoterRepository repository =
            new VoterRepository();

    private final Map<Integer, Voter> voterMap =
            new HashMap<>();

    public boolean registerVoter(int voterId, String voterName) {

        if (voterId <= 0) {
            System.out.println("Voter ID must be positive.");
            return false;
        }

        if (voterName == null || voterName.trim().isEmpty()) {
            System.out.println("Voter name cannot be empty.");
            return false;
        }

        if (voterMap.containsKey(voterId)) {
            System.out.println("Voter ID already exists.");
            return false;
        }

        Voter voter = new Voter(voterId, voterName.trim());

        try {
            boolean saved = repository.saveVoter(voter);

            if (saved) {
                voterMap.put(voterId, voter);
                System.out.println("Voter registered successfully!");
                return true;
            }

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println(
                        "Voter ID already exists in the database.");
            } else {
                System.out.println(
                        "Database error: " + e.getMessage());
            }
        }

        return false;
    }

    public Voter findVoter(int voterId) {
        return voterMap.get(voterId);
    }
}
