# Java DSA Voting System

A console-based voting application developed using **Java, MySQL, JDBC, and Data Structures and Algorithms (DSA)**. The application allows users to register voters and candidates, cast votes, prevent duplicate voting, manage election status, and display election results ranked by vote count.

## Features

- **Voter Registration:** Register voters using a unique voter ID and name.
- **Candidate Registration:** Register candidates with automatically generated candidate IDs.
- **Vote Casting:** Allow registered voters to vote for registered candidates.
- **Duplicate Vote Prevention:** Prevent the same voter from voting more than once in the same election.
- **Election Management:** Open or close an election through the console menu.
- **Result Display:** Display candidates ranked by their total vote counts.
- **Merge Sort:** Sort election results in descending order of votes.
- **Queue Processing:** Use a queue to add and process vote requests.
- **MySQL Integration:** Store voters, candidates, elections, and voting records in a MySQL database.
- **Input Validation:** Validate IDs, names, and election status.
- **Modular Architecture:** Separate application logic into model, repository, service, DSA, and utility packages.

## Technologies Used

- Java
- MySQL
- JDBC (Java Database Connectivity)
- Data Structures and Algorithms
- Eclipse IDE
- MySQL Connector/J

## Project Structure

The project is organized into separate packages to improve readability, maintainability, and separation of responsibilities.

```text
VotingSystemDSA/
└── src/
    ├── com.voting/
    │   ├── Main.java
    │   └── DBConnection.java
    ├── com.voting.model/
    │   ├── Voter.java
    │   ├── Candidate.java
    │   └── Vote.java
    ├── com.voting.repository/
    │   ├── VoterRepository.java
    │   ├── CandidateRepository.java
    │   └── VotingRepository.java
    ├── com.voting.service/
    │   ├── VoterService.java
    │   ├── CandidateService.java
    │   ├── VotingService.java
    │   ├── ResultService.java
    │   └── ElectionService.java
    ├── com.voting.dsa/
    │   ├── VoteQueue.java
    │   └── ResultSorter.java
    └── com.voting.util/
        └── InputValidator.java
```

*Note: Adjust the structure if your actual Eclipse project uses different package or class names.*

## Requirements

Before running the project, ensure you have:

- Java JDK installed
- MySQL Server installed and running
- Eclipse IDE or another Java IDE
- MySQL Connector/J added to the project's build path
- A configured MySQL database

## Database Setup

The application uses a MySQL database named `Elections`.

### 1. Create the database

Run the following SQL in MySQL Workbench or the MySQL command-line client:

```sql
CREATE DATABASE IF NOT EXISTS Elections;
USE Elections;
```

### 2. Create the required tables

Create the `voters`, `candidates`, `elections`, and `votes` tables using the schema configured for this project.

The voting records should have foreign keys referencing the relevant election, voter, and candidate records. A unique constraint on `(election_id, voter_id)` prevents duplicate votes within the same election at the database level.

### 3. Create an election

If the database does not already contain an election, create one:

```sql
INSERT INTO elections (election_name, status)
VALUES ('College Election 2026', 'OPEN');
```

Check the generated election ID before running the application. The current `Main.java` configuration uses election ID `1`.

### 4. Configure the database connection

Update `DBConnection.java` with your local MySQL connection details:

- Database: `Elections`
- Host: `localhost`
- Port: `3306`
- Username: your MySQL username
- Password: your MySQL password

**Security:** Do not commit real database passwords or other credentials to a public GitHub repository.

## How to Run

1. Clone or download the project repository.
2. Open Eclipse and import the project.
3. Add MySQL Connector/J to the Java build path if it is not already configured.
4. Create the `Elections` database and required tables.
5. Configure the database credentials in `DBConnection.java`.
6. Verify that the election ID used by `Main.java` exists.
7. Open `Main.java`.
8. Select **Run As → Java Application**.

## Console Menu

The current application provides the following menu:

```text
==================================
          VOTING SYSTEM
==================================
1. Register Voter
2. Register Candidate
3. Cast Vote
4. View Election Results
5. Close Election
6. Open Election
7. Exit
==================================
Enter your choice:
```

## How to Use

1. **Register Voter:** Enter a positive voter ID and a valid voter name. The application saves the voter in MySQL and displays the registration confirmation.
2. **Register Candidate:** Enter a candidate name. The application saves the candidate and displays the generated candidate ID.
3. **Cast Vote:** Enter a registered voter ID and a valid candidate ID. The application checks election status and processes the vote.
4. **View Election Results:** View the candidates and their vote counts, ranked using Merge Sort.
5. **Close Election:** Close the election to reject further voting.
6. **Open Election:** Reopen the election when voting is permitted.
7. **Exit:** Close the console application.

The application rejects duplicate votes for the same voter in the same election. Voting also depends on the election being open and the voter and candidate existing in the database.

## DSA Concepts Used

### 1. HashMap

The application uses `HashMap` to maintain voter and candidate records in memory for efficient lookup by ID.

- Average lookup time: **O(1)**
- Useful for checking registered voters and candidates.

### 2. Queue

The `VoteQueue` class uses a queue backed by `LinkedList` to process vote requests in First-In, First-Out (FIFO) order.

- Adding a vote: typically **O(1)**
- Removing the next vote: typically **O(1)**

In the current implementation, votes are added to the queue and processed immediately. This demonstrates queue operations but does not implement asynchronous or background processing.

### 3. Merge Sort

The `ResultSorter` class sorts candidate results by total votes in descending order.

- Time complexity: **O(n log n)**
- Commonly used for efficient sorting.
- Candidates with equal vote counts retain their relative order when the merge implementation preserves left-side elements on ties.

## Database and Data Integrity

The application uses JDBC and prepared statements to communicate with MySQL.

Data integrity is supported by:

- Primary keys for records
- Foreign keys between votes, voters, candidates, and elections
- Unique voter IDs
- Unique candidate names
- A unique `(election_id, voter_id)` constraint to prevent duplicate voting
- Election-status validation before accepting votes

The database constraint is important because in-memory collections alone cannot reliably prevent duplicate voting after application restarts or across concurrent sessions.

## Current Limitations

- The main menu currently uses a configured election ID rather than allowing users to select from multiple elections.
- The queue processes each vote immediately instead of running as a background worker.
- The application is console-based and does not provide a graphical or web interface.
- The project has not been described as having automated unit or integration tests.
- Database credentials are configured locally and should be managed securely.

## Future Enhancements

- Support selecting and managing multiple elections.
- Add automated unit and integration tests.
- Improve exception handling and user-friendly error messages.
- Add election reports and export results to CSV or PDF.
- Introduce a web interface using Spring Boot.
- Add role-based access control for election administrators.
- Improve vote-processing architecture if asynchronous processing becomes necessary.

## Author

**Srikanth Pandi**

## License

No license has been specified. Add a `LICENSE` file if you intend to publish the project and define how others may use or modify it.
