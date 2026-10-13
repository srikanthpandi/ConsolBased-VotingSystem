
package com.voting.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.voting.model.Candidate;
import com.voting.repository.CandidateRepository;
import com.voting.util.InputValidator;

public class CandidateService {

    private final CandidateRepository repository;
    private final Map<Integer, Candidate> candidates;

    public CandidateService() {
        repository = new CandidateRepository();
        candidates = new HashMap<>();

        loadExistingCandidates();
    }

    // Load candidates from MySQL at startup
    private void loadExistingCandidates() {
        try {
            candidates.putAll(repository.getAllCandidates());

            System.out.println(
                    "Loaded " + candidates.size()
                            + " existing candidates.");

        } catch (SQLException e) {
            System.out.println(
                    "Error loading candidates: " + e.getMessage());
        }
    }

    // Register a new candidate
    public void registerCandidate(String candidateName) {

        if (!InputValidator.isValidName(candidateName)) {
            System.out.println("Candidate name cannot be empty.");
            return;
        }

        candidateName = candidateName.trim();

        // Check for duplicate candidate names
        for (Candidate candidate : candidates.values()) {
            if (candidate.getCandidateName()
                    .equalsIgnoreCase(candidateName)) {

                System.out.println("Candidate already exists.");
                return;
            }
        }

        Candidate candidate = new Candidate(0, candidateName);

        try {
            int candidateId = repository.saveCandidate(candidate);

            if (candidateId > 0) {
                Candidate savedCandidate =
                        new Candidate(candidateId, candidateName);

                candidates.put(candidateId, savedCandidate);

                System.out.println(
                        "Candidate registered successfully!");
                System.out.println(
                        "Candidate ID: " + candidateId);

            } else {
                System.out.println(
                        "Candidate registration failed.");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Database error while registering candidate: "
                            + e.getMessage());
        }
    }

    // Find candidate by ID
    public Candidate findCandidate(int candidateId) {
        return candidates.get(candidateId);
    }

    // Display all registered candidates
    public void displayAllCandidates() {

        if (candidates.isEmpty()) {
            System.out.println("No candidates registered.");
            return;
        }

        System.out.println("\n----- REGISTERED CANDIDATES -----");

        for (Candidate candidate : candidates.values()) {
            System.out.println(candidate);
        }
    }

    // Return the number of registered candidates
    public int getCandidateCount() {
        return candidates.size();
    }
}
