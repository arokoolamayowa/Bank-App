import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BankApplicationGUI extends JFrame {
    private BankGUI bank;
    private JTextField accountNumberField, holderNameField, accountTypeField, amountField;
    private JTextField sourceAccountField, targetAccountField, transferAmountField;
    private JTextArea outputArea;

    public BankApplicationGUI() {
        bank = new BankGUI();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Simple Bank Application - GUI Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panels
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel outputPanel = createOutputPanel();

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Account Operations"));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Account Operations"),
                new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Account creation fields
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Account Holder Name:"), gbc);
        gbc.gridx = 1;
        holderNameField = new JTextField(15);
        panel.add(holderNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Account Type:"), gbc);
        gbc.gridx = 1;
        accountTypeField = new JTextField(15);
        accountTypeField.setText("Savings");
        panel.add(accountTypeField, gbc);

        // General operation fields
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1;
        accountNumberField = new JTextField(15);
        panel.add(accountNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(15);
        panel.add(amountField, gbc);

        // Transfer specific fields
        gbc.gridx = 3; gbc.gridy = 0;
        panel.add(new JLabel("Source Account:"), gbc);
        gbc.gridx = 4;
        sourceAccountField = new JTextField(15);
        panel.add(sourceAccountField, gbc);

        gbc.gridx = 3; gbc.gridy = 1;
        panel.add(new JLabel("Target Account:"), gbc);
        gbc.gridx = 4;
        targetAccountField = new JTextField(15);
        panel.add(targetAccountField, gbc);

        gbc.gridx = 3; gbc.gridy = 2;
        panel.add(new JLabel("Transfer Amount:"), gbc);
        gbc.gridx = 4;
        transferAmountField = new JTextField(15);
        panel.add(transferAmountField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Operations"));

        JButton createAccountBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton checkBalanceBtn = new JButton("Check Balance");
        JButton transferBtn = new JButton("Transfer");
        JButton displayAllBtn = new JButton("Display All Accounts");
        JButton clearBtn = new JButton("Clear Output");

        // Add action listeners
        createAccountBtn.addActionListener(e -> createAccount());
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        checkBalanceBtn.addActionListener(e -> checkBalance());
        transferBtn.addActionListener(e -> transfer());
        displayAllBtn.addActionListener(e -> displayAllAccounts());
        clearBtn.addActionListener(e -> outputArea.setText(""));

        // Style buttons
        Color buttonColor = new Color(70, 130, 180);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        JButton[] buttons = {createAccountBtn, depositBtn, withdrawBtn,
                checkBalanceBtn, transferBtn, displayAllBtn, clearBtn};

        for (JButton btn : buttons) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(140, 35));
            panel.add(btn);
        }

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Output"));

        outputArea = new JTextArea(12, 70);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(248, 248, 255));
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void createAccount() {
        String name = holderNameField.getText().trim();
        String type = accountTypeField.getText().trim();

        if (name.isEmpty()) {
            appendOutput("❌ Error: Please enter account holder name!");
            return;
        }

        if (type.isEmpty()) {
            type = "Savings"; // Default type
        }

        String accountNumber = bank.createAccount(name, type);
        appendOutput("✅ Account created successfully!");
        appendOutput("📋 Account Number: " + accountNumber);
        appendOutput("👤 Holder: " + name + " | 🏦 Type: " + type);
        appendOutput("💰 Initial Balance: $0.00");
        appendOutput("─────────────────────────────────────");

        // Clear fields
        holderNameField.setText("");
        accountTypeField.setText("Savings");
    }

    private void deposit() {
        String accountNumber = accountNumberField.getText().trim();
        String amountText = amountField.getText().trim();

        if (accountNumber.isEmpty() || amountText.isEmpty()) {
            appendOutput("❌ Error: Please enter account number and amount!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            BankAccountGUI account = bank.findAccount(accountNumber);

            if (account == null) {
                appendOutput("❌ Error: Account not found!");
                return;
            }

            double oldBalance = account.getBalance();
            account.deposit(amount);
            appendOutput("✅ Deposit successful!");
            appendOutput("💳 Account: " + accountNumber);
            appendOutput("💰 Amount deposited: $" + String.format("%.2f", amount));
            appendOutput("📊 Balance: $" + String.format("%.2f", oldBalance) + " → $" + String.format("%.2f", account.getBalance()));
            appendOutput("─────────────────────────────────────");

            // Clear amount field
            amountField.setText("");

        } catch (NumberFormatException e) {
            appendOutput("❌ Error: Invalid amount format!");
        }
    }

    private void withdraw() {
        String accountNumber = accountNumberField.getText().trim();
        String amountText = amountField.getText().trim();

        if (accountNumber.isEmpty() || amountText.isEmpty()) {
            appendOutput("❌ Error: Please enter account number and amount!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            BankAccountGUI account = bank.findAccount(accountNumber);

            if (account == null) {
                appendOutput("❌ Error: Account not found!");
                return;
            }

            double oldBalance = account.getBalance();
            if (account.withdraw(amount)) {
                appendOutput("✅ Withdrawal successful!");
                appendOutput("💳 Account: " + accountNumber);
                appendOutput("💰 Amount withdrawn: $" + String.format("%.2f", amount));
                appendOutput("📊 Balance: $" + String.format("%.2f", oldBalance) + " → $" + String.format("%.2f", account.getBalance()));
                appendOutput("─────────────────────────────────────");

                // Clear amount field
                amountField.setText("");
            } else {
                appendOutput("❌ Error: Withdrawal failed - insufficient funds or invalid amount!");
                appendOutput("💰 Current balance: $" + String.format("%.2f", account.getBalance()));
            }

        } catch (NumberFormatException e) {
            appendOutput("❌ Error: Invalid amount format!");
        }
    }

    private void checkBalance() {
        String accountNumber = accountNumberField.getText().trim();

        if (accountNumber.isEmpty()) {
            appendOutput("❌ Error: Please enter account number!");
            return;
        }

        BankAccountGUI account = bank.findAccount(accountNumber);
        if (account == null) {
            appendOutput("❌ Error: Account not found!");
            return;
        }

        appendOutput("═══ Account Details ═══");
        appendOutput("📋 Account: " + account.getAccountNumber());
        appendOutput("👤 Holder: " + account.getAccountHolderName());
        appendOutput("🏦 Type: " + account.getAccountType());
        appendOutput("💰 Balance: $" + String.format("%.2f", account.getBalance()));
        appendOutput("─────────────────────────────────────");
    }

    private void transfer() {
        String sourceAccount = sourceAccountField.getText().trim();
        String targetAccount = targetAccountField.getText().trim();
        String amountText = transferAmountField.getText().trim();

        if (sourceAccount.isEmpty() || targetAccount.isEmpty() || amountText.isEmpty()) {
            appendOutput("❌ Error: Please fill all transfer fields!");
            return;
        }

        if (sourceAccount.equals(targetAccount)) {
            appendOutput("❌ Error: Source and target accounts cannot be the same!");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            BankAccountGUI source = bank.findAccount(sourceAccount);
            BankAccountGUI target = bank.findAccount(targetAccount);

            if (source == null) {
                appendOutput("❌ Error: Source account not found!");
                return;
            }

            if (target == null) {
                appendOutput("❌ Error: Target account not found!");
                return;
            }

            double sourceOldBalance = source.getBalance();
            double targetOldBalance = target.getBalance();

            if (source.transfer(target, amount)) {
                appendOutput("✅ Transfer successful!");
                appendOutput("📤 From " + sourceAccount + ": $" + String.format("%.2f", sourceOldBalance) + " → $" + String.format("%.2f", source.getBalance()));
                appendOutput("📥 To " + targetAccount + ": $" + String.format("%.2f", targetOldBalance) + " → $" + String.format("%.2f", target.getBalance()));
                appendOutput("💸 Amount transferred: $" + String.format("%.2f", amount));
                appendOutput("─────────────────────────────────────");

                // Clear transfer fields
                sourceAccountField.setText("");
                targetAccountField.setText("");
                transferAmountField.setText("");
            } else {
                appendOutput("❌ Error: Transfer failed - insufficient funds!");
                appendOutput("💰 Source account balance: $" + String.format("%.2f", source.getBalance()));
            }

        } catch (NumberFormatException e) {
            appendOutput("❌ Error: Invalid amount format!");
        }
    }

    private void displayAllAccounts() {
        if (bank.getAccountCount() == 0) {
            appendOutput("❌ No accounts found!");
            return;
        }

        appendOutput("═══ All Accounts ═══");
        bank.displayAllAccountsToGUI(this::appendOutput);
        appendOutput("📊 Total accounts: " + bank.getAccountCount());
        appendOutput("─────────────────────────────────────");
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankApplicationGUI().setVisible(true);
        });
    }
}

// GUI-specific Bank class
class BankGUI {
    private Map<String, BankAccountGUI> accounts;
    private int accountCounter;

    public BankGUI() {
        accounts = new HashMap<>();
        accountCounter = 1000;
    }

    public String createAccount(String holderName, String accountType) {
        String accountNumber = "ACC" + (++accountCounter);
        BankAccountGUI newAccount = new BankAccountGUI(accountNumber, holderName, accountType);
        accounts.put(accountNumber, newAccount);
        return accountNumber;
    }

    public BankAccountGUI findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public void displayAllAccountsToGUI(java.util.function.Consumer<String> outputFunction) {
        int count = 1;
        for (BankAccountGUI account : accounts.values()) {
            outputFunction.accept("Account #" + count + ":");
            outputFunction.accept("📋 Account: " + account.getAccountNumber());
            outputFunction.accept("👤 Holder: " + account.getAccountHolderName());
            outputFunction.accept("🏦 Type: " + account.getAccountType());
            outputFunction.accept("💰 Balance: $" + String.format("%.2f", account.getBalance()));
            outputFunction.accept("---");
            count++;
        }
    }
}

// GUI-specific BankAccount class
class BankAccountGUI {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;

    public BankAccountGUI(String accountNumber, String accountHolderName, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = 0.0;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccountGUI targetAccount, double amount) {
        if (this.withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        }
        return false;
    }
}