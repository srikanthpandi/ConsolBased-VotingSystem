# Java DSA Voting System

A console-based voting application built with Java, MySQL, and Data Structures and Algorithms (DSA). It allows users to register voters and candidates, cast votes, prevent duplicate voting, and display election results ranked by vote count.

## Features

- Register voters with a voter ID and name
- Register election candidates
- Cast a vote for a registered candidate
- Prevent a voter from voting more than once in the same election
- Display election results ranked by vote count
- Use Merge Sort to rank candidates by their results
- Store voting records in MySQL
- Organize the application into model, repository, service, and DSA packages
- Console menu with basic input validation

## Technologies Used

- Java
- MySQL
- JDBC
- Data Structures and Algorithms (DSA)
- Eclipse IDE

## Project Structure

The project uses separate packages for its main responsibilities. The package names referenced in the current implementation include:

```text
src/
└── com/
    └── voting/
        ├── Main.java
        ├── model/
        │   └── Voter.java
        ├── repository/
        │   └── VoterRepository.java
        ├── service/
        │   ├── VoterService.java
        │   ├── CandidateService.java
        │   ├── VotingService.java
        │   └── ResultService.java
        └── dsa/
            └── ResultSorter.java
```

Add or adjust the files in this outline to match the complete package structure in your Eclipse project.

## Requirements

- Java JDK installed
- MySQL Server installed and running
- MySQL JDBC driver added to the project build path
- Eclipse IDE or another Java IDE

## Database Setup

The application uses a MySQL database named `Elections`.

1. Open MySQL Workbench or your MySQL command-line client.
2. Create the database if it does not already exist:

```sql
CREATE DATABASE IF NOT EXISTS Elections;
USE Elections;
```

3. Create the tables required by your repository classes, or run the SQL setup already used by your project.
4. Ensure that the database connection settings in `DBConnection.java` match your local MySQL username, password, and server configuration.
5. Ensure election ID `1` exists and is open before casting votes, as the current console menu uses election ID `1` for voting.

> **Note:** The exact table definitions and connection settings were not included in the supplied project notes. Use the schema already implemented in your project and do not commit database passwords to GitHub.

## How to Run

1. Clone or download the repository.
2. Open the project in Eclipse.
3. Add the MySQL Connector/J JAR to the project's build path if it is not already configured.
4. Update `DBConnection.java` with your local database connection settings.
5. Set up the `Elections` database and required tables.
6. Run `Main.java` as a Java Application.

## Console Menu

```text
==================================
       VOTING SYSTEM
==================================
1. Register Voter
2. Register Candidate
3. Cast Vote
4. View Election Results
5. Exit
==================================
Enter your choice:
```

## How to Use

1. Select **Register Voter** and enter a voter ID and name.
2. Select **Register Candidate** and enter a candidate name.
3. Select **Cast Vote** and enter the voter ID and candidate ID.
4. Select **View Election Results** and enter the election ID.
5. Select **Exit** to close the application.

The application should report a successful vote using the voter's name. Attempting to vote again with the same voter ID in the same election should be rejected.

## DSA Concepts

- **HashMap:** Supports average O(1) lookup by voter ID in the in-memory voter map.
- **Merge Sort:** Ranks candidate results by vote count.
- **Queue / LinkedList:** Listed as a possible future enhancement for FIFO vote processing, not a confirmed current feature.

## Current Limitations

- The voter and candidate service maps were initially stored in memory, so they reset when the application restarts. Database-loading support was being added as a follow-up improvement.
- The current menu uses election ID `1` when casting votes.
- Confirm that database constraints and repository validation prevent duplicate votes at the database level.

## Future Enhancements

- Load existing voters and candidates from MySQL when the application starts
- Add queue-based vote processing
- Add election administration to open or close elections
- Improve input validation and error handling
- Add automated tests and clearer setup scripts

## Author

**Srikanth Pandi**

## License

No license has been specified. Add a license file if you intend to publish or permit reuse of this project.
