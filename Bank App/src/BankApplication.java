import java.util.*;

// Bank Account class to represent individual accounts
class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;

    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = 0.0;
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
            System.out.println("New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw money
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            System.out.println("New balance: $" + balance);
            return true;
        } else if (amount > balance) {
            System.out.println("Insufficient funds!");
            return false;
        } else {
            System.out.println("Invalid withdrawal amount!");
            return false;
        }
    }

    // Check balance
    public void checkBalance() {
        System.out.println("Account: " + accountNumber);
        System.out.println("Holder: " + accountHolderName);
        System.out.println("Type: " + accountType);
        System.out.println("Balance: $" + balance);
    }

    // Transfer money to another account
    public boolean transfer(BankAccount targetAccount, double amount) {
        if (this.withdraw(amount)) {
            targetAccount.deposit(amount);
            System.out.println("Transfer successful to account: " + targetAccount.getAccountNumber());
            return true;
        } else {
            System.out.println("Transfer failed!");
            return false;
        }
    }
}

// Bank class to manage multiple accounts
class Bank {
    private Map<String, BankAccount> accounts;
    private int accountCounter;

    public Bank() {
        accounts = new HashMap<>();
        accountCounter = 1000;
    }

    // Create new account
    public String createAccount(String holderName, String accountType) {
        String accountNumber = "ACC" + (++accountCounter);
        BankAccount newAccount = new BankAccount(accountNumber, holderName, accountType);
        accounts.put(accountNumber, newAccount);
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        return accountNumber;
    }

    // Find account by account number
    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Get all accounts
    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        System.out.println("\n=== All Accounts ===");
        for (BankAccount account : accounts.values()) {
            account.checkBalance();
            System.out.println("-------------------");
        }
    }
}

// Main Bank Application class
public class BankApplication {
    private static Bank bank = new Bank();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Welcome to Simple Bank Application ===");

        while (true) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    transfer();
                    break;
                case 6:
                    bank.displayAllAccounts();
                    break;
                case 7:
                    System.out.println("Thank you for using Simple Bank Application!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Bank Menu ===");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. Transfer Money");
        System.out.println("6. Display All Accounts");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // consume invalid input
            return -1;
        }
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter account type (Savings/Checking): ");
        String type = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Invalid name!");
            return;
        }

        bank.createAccount(name, type);
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = bank.findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter deposit amount: $");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            account.deposit(amount);
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount!");
            scanner.nextLine(); // consume invalid input
        }
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = bank.findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter withdrawal amount: $");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            account.withdraw(amount);
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount!");
            scanner.nextLine(); // consume invalid input
        }
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = bank.findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        account.checkBalance();
    }

    private static void transfer() {
        System.out.print("Enter source account number: ");
        String sourceAccountNumber = scanner.nextLine();

        BankAccount sourceAccount = bank.findAccount(sourceAccountNumber);
        if (sourceAccount == null) {
            System.out.println("Source account not found!");
            return;
        }

        System.out.print("Enter target account number: ");
        String targetAccountNumber = scanner.nextLine();

        BankAccount targetAccount = bank.findAccount(targetAccountNumber);
        if (targetAccount == null) {
            System.out.println("Target account not found!");
            return;
        }

        System.out.print("Enter transfer amount: $");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            sourceAccount.transfer(targetAccount, amount);
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount!");
            scanner.nextLine(); // consume invalid input
        }
    }
}