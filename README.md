This Java bank application includes the following features:

Classes:

BankAccount - Represents individual bank accounts with basic operations
Bank - Manages multiple accounts and provides account creation/lookup
BankApplication - Main class with user interface and menu system
Key Features:

Account Creation - Create new accounts with unique account numbers
Deposit - Add money to accounts with validation
Withdrawal - Remove money with insufficient funds checking
Balance Inquiry - Display account details and current balance
Money Transfer - Transfer funds between accounts
Account Management - View all accounts in the system
Security Features:

Input validation for amounts and account numbers
Error handling for invalid inputs
Automatic account number generation
Balance verification before withdrawals/transfers
How to run:

Compile: javac BankApplication.java
Run: java BankApplication
The application uses a simple console interface where users can navigate through menu options to perform banking operations. Each account is automatically assigned a unique account number starting from ACC1001.
