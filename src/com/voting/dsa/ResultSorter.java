
package com.voting.dsa;

import java.util.ArrayList;
import java.util.List;

public class ResultSorter {

    public static class CandidateResult {
        private final int candidateId;
        private final String candidateName;
        private final int totalVotes;

        public CandidateResult(int candidateId, String candidateName,
                               int totalVotes) {
            this.candidateId = candidateId;
            this.candidateName = candidateName;
            this.totalVotes = totalVotes;
        }

        public int getCandidateId() {
            return candidateId;
        }

        public String getCandidateName() {
            return candidateName;
        }

        public int getTotalVotes() {
            return totalVotes;
        }

        @Override
        public String toString() {
            return candidateName + " (ID: " + candidateId
                    + ") - Votes: " + totalVotes;
        }
    }

    public void mergeSort(List<CandidateResult> results) {
        if (results == null || results.size() <= 1) {
            return;
        }

        List<CandidateResult> sorted = sort(results);
        results.clear();
        results.addAll(sorted);
    }

    private List<CandidateResult> sort(List<CandidateResult> list) {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }

        int middle = list.size() / 2;

        List<CandidateResult> left =
                sort(new ArrayList<>(list.subList(0, middle)));

        List<CandidateResult> right =
                sort(new ArrayList<>(list.subList(middle, list.size())));

        return merge(left, right);
    }

    private List<CandidateResult> merge(
            List<CandidateResult> left,
            List<CandidateResult> right) {

        List<CandidateResult> merged = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            // Highest vote count first.
            if (left.get(i).getTotalVotes()
                    >= right.get(j).getTotalVotes()) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }
}
