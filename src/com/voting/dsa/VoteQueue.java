
package com.voting.dsa;

import java.util.LinkedList;
import java.util.Queue;

import com.voting.model.Vote;

public class VoteQueue {

    private final Queue<Vote> queue = new LinkedList<>();

    // Add a vote to the queue
    public void addVote(Vote vote) {
        if (vote == null) {
            System.out.println("Invalid vote.");
            return;
        }

        queue.offer(vote);
        System.out.println("Vote added to processing queue.");
    }

    // Retrieve and remove the next vote
    public Vote processNextVote() {
        return queue.poll();
    }

    // Check whether the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get the number of pending votes
    public int size() {
        return queue.size();
    }
}
