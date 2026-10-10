
package com.voting;

import java.util.List;
import java.util.Scanner;

import com.voting.model.Voter;
import com.voting.service.CandidateService;
import com.voting.service.ResultService;
import com.voting.service.VoterService;
import com.voting.service.VotingService;
import com.voting.dsa.ResultSorter.CandidateResult;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        VoterService voterService = new VoterService();
        CandidateService candidateService = new CandidateService();
        VotingService votingService = new VotingService();
        ResultService resultService = new ResultService();

        final int ELECTION_ID = 1;
        int choice=0;

        do {
            System.out.println("\n==================================");
            System.out.println("       VOTING SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Register Voter");
            System.out.println("2. Register Candidate");
            System.out.println("3. Cast Vote");
            System.out.println("4. View Election Results");
            System.out.println("5. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Voter ID: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid voter ID.");
                        sc.nextLine();
                        break;
                    }
                    int voterId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Voter Name: ");
                    String voterName = sc.nextLine();

                    voterService.registerVoter(voterId, voterName);
                    break;

                case 2:
                    System.out.print("Enter Candidate Name: ");
                    String candidateName = sc.nextLine();

                    candidateService.registerCandidate(candidateName);
                    break;

                case 3:
                    System.out.print("Enter Voter ID: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid voter ID.");
                        sc.nextLine();
                        break;
                    }
                    int votingVoterId = sc.nextInt();

                    System.out.print("Enter Candidate ID: ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid candidate ID.");
                        sc.nextLine();
                        break;
                    }
                    int candidateId = sc.nextInt();
                    sc.nextLine();

                    votingService.castVote(
                            ELECTION_ID, votingVoterId, candidateId);
                    break;

                case 4:
                    System.out.print("Enter Election ID (1): ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid election ID.");
                        sc.nextLine();
                        break;
                    }
                    int electionId = sc.nextInt();
                    sc.nextLine();

                    resultService.displayResults(electionId);
                    break;

                case 5:
                    System.out.println("Thank you for using the Voting System!");
                    break;

                default:
                    System.out.println("Invalid choice. Select 1 to 5.");
            }

        } while (choice != 5);

        sc.close();
    }
}
