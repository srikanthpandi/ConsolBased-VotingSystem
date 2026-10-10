
package com.voting.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.voting.model.Candidate;
import com.voting.repository.CandidateRepository;

public class CandidateService {

    private final CandidateRepository repository =
            new CandidateRepository();

    private final Map<Integer, Candidate> candidateMap =
            new HashMap<>();

    public boolean registerCandidate(String name) {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Candidate name cannot be empty.");
            return false;
        }

        Candidate candidate = new Candidate(0, name.trim());

        try {
            int generatedId = repository.saveCandidate(candidate);

            if (generatedId > 0) {
                Candidate savedCandidate =
                        new Candidate(generatedId, name.trim());

                candidateMap.put(generatedId, savedCandidate);

                System.out.println(
                        "Candidate registered successfully!");
                System.out.println(savedCandidate);

                return true;
            }

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.out.println(
                        "This candidate name already exists.");
            } else {
                System.out.println(
                        "Database error: " + e.getMessage());
            }
        }

        return false;
    }

    public Candidate findCandidate(int candidateId) {
        return candidateMap.get(candidateId);
    }
}
