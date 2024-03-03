# Loan Calculator

Welcome to the Loan Calculator project!

## Overview

This project is a simple loan calculator application that helps users estimate loan offers based on their financial information.

## Features

- Read loan configuration from a YAML file.
- Take user input for employment and financial details.
- Calculate and display loan offers based on the provided information.

## Getting Started

Follow these steps to set up and run the project locally:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/loan-calculator.git
   cd loan-calculator

2. **Build and run**
   ```bash
    ./gradlew build
    ./gradlew run

3. **Input Details**
Open the application and follow the prompts to input your employment and financial details.

## Configuration

The loan calculator uses a YAML configuration file (`application.yml`) to set various parameters. Here's an example of the configuration file and how you can customize it:

```yaml
maxCreditPeriod: 100
minCreditPeriod: 6
minCreditAmount: 5000.0
maxCreditAmount: 150000.0
maxEngagement: 200000.0
interestRates:
  period6_12: 0.02
  period13_36: 0.03
  period37_60: 0.03
  period61_100: 0.03
dti:
  period6_12: 0.6
  period13_36: 0.6
  period37_60: 0.5
  period61_100: 0.55
```

Configuration Parameters
- maxCreditPeriod: The maximum allowed credit period.
- minCreditPeriod: The minimum required credit period.
- minCreditAmount: The minimum loan amount.
- maxCreditAmount: The maximum loan amount.
- maxEngagement: The maximum engagement.
- interestRates: Interest rates for different credit periods.
- dti: Debt-to-Income ratio for different credit periods.
  
Feel free to adjust these values to fit your specific loan calculation requirements.

## Testing

The Loan Calculator project includes a suite of tests to ensure the correctness of its functionalities. Below are instructions on how to run the tests:

### Prerequisites

Make sure you have the following set up before running the tests:

- Java Development Kit (JDK) installed
- Gradle build tool installed (optional, you can use the provided Gradle wrapper)

### Running Tests

1. Open a terminal window and navigate to the project root directory.

2. Run the following command to execute the tests:

   ```bash
   ./gradlew test


## Dependencies
- Java 17
- Gradle
