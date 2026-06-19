# Internship Projects 🚀

This repository contains Java projects developed during the **CodSoft Software Development Internship**. The projects are categorized into **Major Project** (fully refactored with OOPs, Concurrency, and Exception Handling) and **Minor Projects** (core utility and game apps).

---

## 📂 Project Structure

```text
D:/Downloads/CodeSoft-main/
├── ATM/                                # [MAJOR PROJECT] ATM System
│   ├── ATMInterface.java               # Main entry point (and multithreading demo)
│   ├── model/
│   │   └── UserAccount.java            # Account entity with synchronized methods
│   ├── repository/
│   │   ├── AccountRepository.java      # Data access interface
│   │   └── InMemoryAccountRepository.java # Seeding multiple accounts using HashMap
│   ├── service/
│   │   ├── ATMService.java             # Business logic interface
│   │   └── ATMServiceImpl.java         # Service implementation (Loose coupling)
│   ├── exception/
│   │   ├── AccountNotFoundException.java   # Custom Exception
│   │   ├── InsufficientBalanceException.java # Custom Exception
│   │   └── InvalidPinException.java        # Custom Exception
│   └── ui/
│       └── ATMConsoleUI.java           # Try-catch validated interactive UI
│
├── CurrencyConverter.java             # [MINOR PROJECT] Live Exchange Rate Converter
├── NumberGame.java                    # [MINOR PROJECT] Number Guessing Game
└── README.md                          # Repository Documentation
```

---

## 🌟 Major Project: ATM Interface

An enterprise-grade console-based **Automated Teller Machine (ATM)** system built with focus on clean code design, robustness, and thread safety.

### 🔑 Key Features
1. **Loose Coupling (Dependency Injection)**: Components depend strictly on interfaces (e.g., `ATMService`, `AccountRepository`), making it modular and open to future enhancements (e.g., replacing the Console UI with a GUI or DB storage).
2. **Collection Framework**: Multiple accounts are managed dynamically using a `HashMap<Integer, UserAccount>`. Transaction logs (mini-statements) are recorded using an `ArrayList<String>`.
3. **Robust Exception Handling**: Custom checked business exceptions (`InsufficientBalanceException`, `InvalidPinException`, `AccountNotFoundException`) are handled using standard try-catch blocks to prevent crashes and validate inputs.
4. **Multithreading & Thread Safety**: Critical sections like `withdraw` and `deposit` are `synchronized` at the account level to prevent race conditions during concurrent transactions on joint accounts.

### 🚀 How to Run

#### Normal Interactive CLI Mode:
Run the compiler and execution commands from the root directory:
```powershell
# Compile the project
javac ATM/ATMInterface.java

# Run the ATM CLI
java ATM.ATMInterface
```
*Demo Seed Accounts:*
* Account `1001`, PIN `1111`, Balance `INR 5000.00`
* Account `1002`, PIN `2222`, Balance `INR 10000.00`
* Account `1003`, PIN `3333`, Balance `INR 15000.00`

#### Multithreading / Concurrency Demo Mode:
To simulate two concurrent withdrawals happening at the exact same moment on a joint account (demonstrating thread safety and synchronization):
```powershell
java ATM.ATMInterface --demo
```

---

## 🛠️ Minor Projects

### 1. Live Currency Converter
A network-connected utility that converts amounts between different currencies using a live exchange rate API.

* **Features**:
  * Calls the live conversion API using `OkHttpClient`.
  * Parses response parameters dynamically using the `JSONObject` parser library.
  * Inputs and calculations are processed with high precision using `BigDecimal`.
* **How to Run**:
  Ensure OkHttp and JSON jars are present in your classpath, then run:
  ```powershell
  javac -cp ".;lib/*" CurrencyConverter.java
  java -cp ".;lib/*" CurrencyConverter
  ```

### 2. Number Guessing Game
An interactive CLI game where players try to guess a randomly generated number.

* **Features**:
  * Generates random integers dynamically within a range (1 to 100).
  * Limits the attempts (5 guesses per round) to challenge the user.
  * Provides helpful relative hints ("Too high" or "Too low") after each guess.
  * Tracks total rounds played, rounds won, and logs the score calculation.
* **How to Run**:
  ```powershell
  javac NumberGame.java
  java NumberGame
  ```

---

## 🛠️ Technologies Used
* **Language**: Java (JDK 8+)
* **Libraries**: `okhttp3`, `org.json` (for Currency Converter)
* **Concepts**: OOPs (Interfaces, Encapsulation), Collections (Map, List), Multithreading (Synchronization, Threads), Custom Exception Handling, Input/Output parsing.
