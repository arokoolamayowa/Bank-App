/**
 * Input validation utility class for the Bank Application
 * Provides enhanced validation for user inputs
 */
public class InputValidator {

    /**
     * Validates account holder name
     * @param name The name to validate
     * @return ValidationResult with success status and message
     */
    public static ValidationResult validateAccountHolderName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ValidationResult(false, "Account holder name cannot be empty!");
        }

        if (name.trim().length() < 2) {
            return new ValidationResult(false, "Account holder name must be at least 2 characters long!");
        }

        if (name.trim().length() > 50) {
            return new ValidationResult(false, "Account holder name cannot exceed 50 characters!");
        }

        // Check for invalid characters (only letters, spaces, hyphens, apostrophes)
        if (!name.matches("^[a-zA-Z\\s\\-']+$")) {
            return new ValidationResult(false, "Account holder name can only contain letters, spaces, hyphens, and apostrophes!");
        }

        return new ValidationResult(true, "Valid name");
    }

    /**
     * Validates account type
     * @param accountType The account type to validate
     * @return ValidationResult with success status and message
     */
    public static ValidationResult validateAccountType(String accountType) {
        if (accountType == null || accountType.trim().isEmpty()) {
            return new ValidationResult(false, "Account type cannot be empty!");
        }

        String type = accountType.trim().toLowerCase();
        if (!type.equals("savings") && !type.equals("checking") && !type.equals("current")) {
            return new ValidationResult(false, "Account type must be 'Savings', 'Checking', or 'Current'!");
        }

        return new ValidationResult(true, "Valid account type");
    }

    /**
     * Validates monetary amount
     * @param amount The amount to validate
     * @return ValidationResult with success status and message
     */
    public static ValidationResult validateAmount(double amount) {
        if (amount < 0) {
            return new ValidationResult(false, "Amount cannot be negative!");
        }

        if (amount == 0) {
            return new ValidationResult(false, "Amount must be greater than zero!");
        }

        if (amount > 1000000) {
            return new ValidationResult(false, "Amount cannot exceed $1,000,000 per transaction!");
        }

        // Check for reasonable decimal places (max 2)
        if (Math.round(amount * 100.0) / 100.0 != amount) {
            return new ValidationResult(false, "Amount can have maximum 2 decimal places!");
        }

        return new ValidationResult(true, "Valid amount");
    }

    /**
     * Validates account number format
     * @param accountNumber The account number to validate
     * @return ValidationResult with success status and message
     */
    public static ValidationResult validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return new ValidationResult(false, "Account number cannot be empty!");
        }

        // Check if it follows the ACC#### format
        if (!accountNumber.matches("^ACC\\d{4}$")) {
            return new ValidationResult(false, "Account number must be in format ACC#### (e.g., ACC1001)!");
        }

        return new ValidationResult(true, "Valid account number");
    }

    /**
     * Validates transfer to ensure source and target are different
     * @param sourceAccount Source account number
     * @param targetAccount Target account number
     * @return ValidationResult with success status and message
     */
    public static ValidationResult validateTransfer(String sourceAccount, String targetAccount) {
        ValidationResult sourceValidation = validateAccountNumber(sourceAccount);
        if (!sourceValidation.isValid()) {
            return new ValidationResult(false, "Source account: " + sourceValidation.getMessage());
        }

        ValidationResult targetValidation = validateAccountNumber(targetAccount);
        if (!targetValidation.isValid()) {
            return new ValidationResult(false, "Target account: " + targetValidation.getMessage());
        }

        if (sourceAccount.equals(targetAccount)) {
            return new ValidationResult(false, "Source and target accounts cannot be the same!");
        }

        return new ValidationResult(true, "Valid transfer accounts");
    }
}

/**
 * Result class for validation operations
 */
class ValidationResult {
    private boolean valid;
    private String message;

    public ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}