import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ===============================
// Custom Exception
// ===============================
class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}


// ===============================
// Abstract Account Class
// ===============================
abstract class Account {

    private int accountNumber;
    private String holderName;
    private double balance;

    public Account(int accountNumber, String holderName, double balance) {

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // Getters

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    // Protected setter
    // Child classes can modify balance
    protected void setBalance(double balance) {
        this.balance = balance;
    }


    // Deposit money
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0.");
            return;
        }

        balance += amount;

        System.out.println(
                "₹" + amount + " deposited successfully."
        );

        System.out.println(
                "Current Balance: ₹" + balance
        );
    }


    // Abstract method
    // Each account type will implement withdrawal differently
    public abstract void withdraw(double amount)
            throws InsufficientBalanceException;


    // Display account details

    public void displayAccountDetails() {

        System.out.println("\n----------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + holderName);
        System.out.println("Account Type   : " + getAccountType());
        System.out.println("Balance        : ₹" + balance);
        System.out.println("----------------------------");
    }


    public abstract String getAccountType();
}


// ===============================
// Savings Account
// ===============================
class SavingsAccount extends Account {

    private double minimumBalance;


    public SavingsAccount(
            int accountNumber,
            String holderName,
            double balance,
            double minimumBalance) {

        super(accountNumber, holderName, balance);

        this.minimumBalance = minimumBalance;
    }


    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException {

        if (amount <= 0) {

            System.out.println(
                    "Withdrawal amount must be greater than 0."
            );

            return;
        }


        if (getBalance() - amount < minimumBalance) {

            throw new InsufficientBalanceException(
                    "Withdrawal failed! Savings Account must maintain minimum balance of ₹"
                            + minimumBalance
            );
        }


        setBalance(getBalance() - amount);


        System.out.println(
                "₹" + amount
                        + " withdrawn from Savings Account."
        );

        System.out.println(
                "Remaining Balance: ₹" + getBalance()
        );
    }


    @Override
    public String getAccountType() {

        return "Savings Account";
    }
}


// ===============================
// Current Account
// ===============================
class CurrentAccount extends Account {

    private double overdraftLimit;


    public CurrentAccount(
            int accountNumber,
            String holderName,
            double balance,
            double overdraftLimit) {

        super(accountNumber, holderName, balance);

        this.overdraftLimit = overdraftLimit;
    }


    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException {

        if (amount <= 0) {

            System.out.println(
                    "Withdrawal amount must be greater than 0."
            );

            return;
        }


        if (getBalance() - amount < -overdraftLimit) {

            throw new InsufficientBalanceException(
                    "Withdrawal failed! Overdraft limit of ₹"
                            + overdraftLimit
                            + " exceeded."
            );
        }


        setBalance(getBalance() - amount);


        System.out.println(
                "₹" + amount
                        + " withdrawn from Current Account."
        );

        System.out.println(
                "Remaining Balance: ₹" + getBalance()
        );
    }


    @Override
    public String getAccountType() {

        return "Current Account";
    }
}


// ===============================
// Bank Class
// ===============================
class Bank {

    /*
       Bank HAS accounts.

       This is a HAS-A relationship / Composition.
    */

    private List<Account> accounts;


    public Bank() {

        accounts = new ArrayList<>();
    }


    // Add account

    public void addAccount(Account account) {

        // Check duplicate account number

        if (findAccount(account.getAccountNumber()) != null) {

            System.out.println(
                    "Account number already exists!"
            );

            return;
        }


        accounts.add(account);


        System.out.println(
                "\nAccount created successfully!"
        );

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );
    }


    // Find account

    public Account findAccount(int accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber()
                    == accountNumber) {

                return account;
            }
        }

        return null;
    }


    // Display all accounts

    public void displayAllAccounts() {

        if (accounts.isEmpty()) {

            System.out.println(
                    "No accounts available."
            );

            return;
        }


        System.out.println(
                "\n========== ALL ACCOUNTS =========="
        );


        for (Account account : accounts) {

            account.displayAccountDetails();
        }
    }


    // Display number of accounts

    public void displayTotalAccounts() {

        System.out.println(
                "Total Accounts: " + accounts.size()
        );
    }
}


// ===============================
// Main Class
// ===============================
public class BankManagement {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank();


        while (true) {

            System.out.println(
                    "\n=========================================="
            );

            System.out.println(
                    "       BANK MANAGEMENT SYSTEM"
            );

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "1. Create Savings Account"
            );

            System.out.println(
                    "2. Create Current Account"
            );

            System.out.println(
                    "3. Deposit Money"
            );

            System.out.println(
                    "4. Withdraw Money"
            );

            System.out.println(
                    "5. Check Account Details"
            );

            System.out.println(
                    "6. Display All Accounts"
            );

            System.out.println(
                    "7. Show Total Accounts"
            );

            System.out.println(
                    "8. Exit"
            );

            System.out.println(
                    "=========================================="
            );


            System.out.print(
                    "Enter your choice: "
            );


            int choice = scanner.nextInt();


            switch (choice) {


                // =================================
                // Create Savings Account
                // =================================

                case 1:

                    System.out.print(
                            "Enter Account Number: "
                    );

                    int savingsNumber =
                            scanner.nextInt();


                    scanner.nextLine();


                    System.out.print(
                            "Enter Holder Name: "
                    );

                    String savingsName =
                            scanner.nextLine();


                    System.out.print(
                            "Enter Initial Balance: ₹"
                    );

                    double savingsBalance =
                            scanner.nextDouble();


                    SavingsAccount savingsAccount =
                            new SavingsAccount(
                                    savingsNumber,
                                    savingsName,
                                    savingsBalance,
                                    1000
                            );


                    bank.addAccount(
                            savingsAccount
                    );

                    break;


                // =================================
                // Create Current Account
                // =================================

                case 2:

                    System.out.print(
                            "Enter Account Number: "
                    );

                    int currentNumber =
                            scanner.nextInt();


                    scanner.nextLine();


                    System.out.print(
                            "Enter Holder Name: "
                    );

                    String currentName =
                            scanner.nextLine();


                    System.out.print(
                            "Enter Initial Balance: ₹"
                    );

                    double currentBalance =
                            scanner.nextDouble();


                    CurrentAccount currentAccount =
                            new CurrentAccount(
                                    currentNumber,
                                    currentName,
                                    currentBalance,
                                    5000
                            );


                    bank.addAccount(
                            currentAccount
                    );

                    break;


                // =================================
                // Deposit
                // =================================

                case 3:

                    System.out.print(
                            "Enter Account Number: "
                    );

                    int depositAccountNumber =
                            scanner.nextInt();


                    Account depositAccount =
                            bank.findAccount(
                                    depositAccountNumber
                            );


                    if (depositAccount == null) {

                        System.out.println(
                                "Account not found!"
                        );

                        break;
                    }


                    System.out.print(
                            "Enter Deposit Amount: ₹"
                    );

                    double depositAmount =
                            scanner.nextDouble();


                    depositAccount.deposit(
                            depositAmount
                    );

                    break;


                // =================================
                // Withdraw
                // =================================

                case 4:

                    System.out.print(
                            "Enter Account Number: "
                    );

                    int withdrawAccountNumber =
                            scanner.nextInt();


                    Account withdrawAccount =
                            bank.findAccount(
                                    withdrawAccountNumber
                            );


                    if (withdrawAccount == null) {

                        System.out.println(
                                "Account not found!"
                        );

                        break;
                    }


                    System.out.print(
                            "Enter Withdrawal Amount: ₹"
                    );

                    double withdrawAmount =
                            scanner.nextDouble();


                    try {

                        withdrawAccount.withdraw(
                                withdrawAmount
                        );

                    }

                    catch (
                            InsufficientBalanceException e
                    ) {

                        System.out.println(
                                "ERROR: "
                                        + e.getMessage()
                        );
                    }

                    break;


                // =================================
                // Check Account
                // =================================

                case 5:

                    System.out.print(
                            "Enter Account Number: "
                    );

                    int searchNumber =
                            scanner.nextInt();


                    Account account =
                            bank.findAccount(
                                    searchNumber
                            );


                    if (account == null) {

                        System.out.println(
                                "Account not found!"
                        );

                    }

                    else {

                        account.displayAccountDetails();
                    }

                    break;


                // =================================
                // Display All Accounts
                // =================================

                case 6:

                    bank.displayAllAccounts();

                    break;


                // =================================
                // Total Accounts
                // =================================

                case 7:

                    bank.displayTotalAccounts();

                    break;


                // =================================
                // Exit
                // =================================

                case 8:

                    System.out.println(
                            "\nThank you for using "
                                    + "Bank Management System!"
                    );

                    scanner.close();

                    return;


                // =================================
                // Invalid Choice
                // =================================

                default:

                    System.out.println(
                            "Invalid choice! "
                                    + "Please try again."
                    );
            }
        }
    }
}