# Banking Application Test Suite

This project involves the creation of a JUnit 5 automated test suite to test a Java-based banking application. The application was refactored to ensure robust and meaningful test cases, aligning with modern testing best practices.

## Features
1. Refactored Banking Application
 - Simplified and modularised code for better testability and maintainability.
 - Enhanced structure using object-oriented principles.
2. JUnit 5 Test Suite
  - Comprehensive test coverage for all methods in the application.
  - Utilizes JUnit 5 annotations:
  - @BeforeAll
  - @BeforeEach
  - @Test
  - @ParameterizedTest
  - @Timeout
  - @AfterEach
  - @AfterAll
  - Includes exception handling tests for at least two distinct exception types.
3. Parameterization and Timeout
  - Parameterized tests ensure varied inputs are tested efficiently.
  - Timeout tests verify method performance within defined time constraints.

Installation and Usage
1. Clone the repository:
 ```bash
git clone https://github.com/jamesmcdonald112/BankingApp.git
```

2. Navigate to the project directory:
```bash
cd BankingApp
```

3. Run the test suite:
  - Ensure JUnit 5 is configured in your environment.
	- Execute the tests via the IDE’s test runner or CLI.
