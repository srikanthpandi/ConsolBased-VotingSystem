
package com.voting.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.voting.model.Voter;
import com.voting.repository.VoterRepository;
import com.voting.util.InputValidator;

public class VoterService {

    private final VoterRepository repository;
    private final Map<Integer, Voter> voters;

    public VoterService() {
        repository = new VoterRepository();
        voters = new HashMap<>();

        loadExistingVoters();
    }

    // Load voters from MySQL when the application starts
    private void loadExistingVoters() {
        try {
            voters.putAll(repository.getAllVoters());

            System.out.println(
                    "Loaded " + voters.size() + " existing voters.");

        } catch (SQLException e) {
            System.out.println(
                    "Error loading voters: " + e.getMessage());
        }
    }

    // Register a new voter
    public void registerVoter(int voterId, String voterName) {

        // Validate voter ID
        if (!InputValidator.isValidId(voterId)) {
            System.out.println("Voter ID must be positive.");
            return;
        }

        // Validate voter name
        if (!InputValidator.isValidName(voterName)) {
            System.out.println("Voter name cannot be empty.");
            return;
        }

        voterName = voterName.trim();

        // Check for duplicate voter ID in the local cache
        if (voters.containsKey(voterId)) {
            System.out.println("Voter ID already exists.");
            return;
        }

        Voter voter = new Voter(voterId, voterName);

        try {
            boolean saved = repository.saveVoter(voter);

            if (saved) {
                voters.put(voterId, voter);
                System.out.println("Voter registered successfully!");
                System.out.println("Voter ID: " + voter.getVoterId());
                System.out.println("Voter Name: " + voter.getVoterName());
            } else {
                System.out.println("Voter registration failed.");
                
            }

        } catch (SQLException e) {
            System.out.println(
                    "Database error while registering voter: "
                            + e.getMessage());
        }
    }

    // Find a voter using voter ID
    public Voter findVoter(int voterId) {
        return voters.get(voterId);
    }

    // Display all registered voters
    public void displayAllVoters() {

        if (voters.isEmpty()) {
            System.out.println("No voters registered.");
            return;
        }

        System.out.println("\n----- REGISTERED VOTERS -----");

        for (Voter voter : voters.values()) {
            System.out.println(voter);
        }
    }

    // Return the number of registered voters
    public int getVoterCount() {
        return voters.size();
    }
}
