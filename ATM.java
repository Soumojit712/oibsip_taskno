import java.util.*;
import java.lang.*;
import java.security.*;
public class ATM{
    public static void main(String[]args){
        //init Scanner 
        Scanner sc = new Scanner(System.in);
        //init bank
        Bank theBank = new Bank("State Bank Of India");
        //add user ,whichalso creates a savings account
        User aUser = theBank.addUser("Oasis","Infobyte","1111");
        //add a checking account for our user 
        Account newAccount = new Account("Checking",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            //stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank,sc);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser,sc);
        }
    }
    public static User mainMenuPrompt(Bank theBank,Scanner sc){
        //init
        String userID;
        String pin;
        User authUser;
        //prompt the user for user id and pincombo until a correct one is reached
        do{
            System.out.println("\n\nWelcome to "+theBank.getName()+"\n\n");
            System.out.print("Enter User ID : ");
            userID = sc.nextLine();
            System.out.println();
            System.out.print("Enter Pin : ");
            pin = sc.nextLine();

            //try to get the user object corresponding to th ID and pin Combo 
            authUser = theBank.userLogin(userID,pin);
            if(authUser == null){
                System.out.println("incorrect User ID/Pin, Please try again");
            }
        }while(authUser==null);//continue login until successful login
        return authUser;
    }
    public static void printUserMenu(User theUser,Scanner sc){
        //print a summary of user's account
        theUser.printAccountSummary();

        //init choice
        int choice;
        //user menu
        do{
            System.out.println("Welcome "+theUser.getFirstName()+" What would you like to do");
            System.out.println("1>Show Transaction History");
            System.out.println("2>withdraw");
            System.out.println("3>Deposite");
            System.out.println("4>Transfer");
            System.out.println("5>Quit");
            System.out.println();
            System.out.println("Enter Your Choice : ");
            choice = sc.nextInt();
            if(choice<1 || choice>5){
                System.out.println("Invalid Choice"+" Please choose between 1-5 ");  
            } 
        }while(choice<1 || choice>5);
        //process the choice
        switch(choice){
            case 1:
                ATM.showTransactionHistory(theUser,sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser,sc);
                break;
            case 3:
                ATM.depositeFunds(theUser,sc);
                break;
            case 4:
                ATM.transferFunds(theUser,sc);
                break;
            case 5:
                 //gobble up rest of previous input
                 sc.nextLine();
                 break;
        }
        //redisplay this menu unless user quit
        if(choice != 5){
            ATM.printUserMenu(theUser,sc);
        }
    }
    public static void showTransactionHistory(User theUser,Scanner sc){
        int theAct;
        //get account whose history to look at
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+" whose transaction you waant to see : ",theUser.numAccounts());
            theAct = sc.nextInt()-1;
            if(theAct < 0 || theAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(theAct < 0 || theAct >= theUser.numAccounts());
        //Print transaction history
 
        theUser.printActTransHistory(theAct);
    }
    public static void transferFunds(User theUser,Scanner sc){
        //intits
        int fromAct;
        int toAct;
        double amount;
        double actBal;
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer from:",theUser.numAccounts());
            fromAct = sc.nextInt()-1;
            if(fromAct < 0 || fromAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(fromAct);

        //get the account to transfer to 
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer to:",theUser.numAccounts());
            toAct = sc.nextInt()-1;
            if(toAct < 0 || toAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(toAct < 0 || toAct >= theUser.numAccounts());
        //get the amount to traansfer
        do{
            System.out.println("Enter the amount to transfer (max than $"+actBal+") : $");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }else if(amount > actBal){
                System.out.println("Amount must not be greater than \n"+"balance of $"+actBal);
            }
        }while(amount < 0 || amount>actBal);
        //do the transfer
        theUser.addActTransaction(fromAct,-1*amount,String.format("Transfer to account "+theUser.getActUUID(toAct)));
        theUser.addActTransaction(toAct,amount,String.format("Transfer to account "+theUser.getActUUID(fromAct)));
    }
    public static void withdrawFunds(User theUser,Scanner sc){
        int fromAct;
        String memo;
        double amount;
        double actBal;
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"Where to withdraw :",theUser.numAccounts());
            fromAct = sc.nextInt()-1;
            if(fromAct < 0 || fromAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(fromAct);
        //get the amount to traansfer
        do{
            System.out.println("Enter the amount to withdraw (max $ "+actBal+"): $");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }else if(amount > actBal){
                System.out.println("Amount must not be greater than \n"+"balance of $"+actBal);
            }
        }while(amount < 0 || amount>actBal);
        //gobble up rest of previous input
        sc.nextLine();
        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        //do withdrawal
        theUser.addActTransaction(fromAct,-1*amount,memo);        
    }
    public static void depositeFunds(User theUser,Scanner sc){
        int toAct;
        String memo;
        double amount;
        double actBal;
        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"Where to Deposite:",theUser.numAccounts());
            toAct = sc.nextInt()-1;
            if(toAct < 0 || toAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(toAct < 0 || toAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(toAct);
        //get the amount to transfer
        do{
            System.out.print("Enter the amount to deposite (max than $"+actBal+") :$");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }
        }while(amount < 0 );
        //gobble up rest of previous input
        sc.nextLine();
        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        //do withdrawal
        theUser.addActTransaction(toAct,amount,memo);             
    }
}


public class Account {
    private String name;  // The name of the account (e.g., saving, current, etc.)
    private String uuid;  // Account ID number
    private User holder;  // The user object that owns this account
    private List<Transaction> transactions;  // List of transactions for this account

    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<>();
    }

    public String getUUID() {
        return uuid;
    }

    public String getSummaryLine() {
        double balance = getBalance();
        return String.format("%s : $%.2f : %s", uuid, balance, name);
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.println("Transaction history for account: " + uuid);
        for (int t = transactions.size() - 1; t >= 0; t--) {
            System.out.println(transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        transactions.add(newTrans);
    }
}



public class Bank{
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID(){
        //inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        //continue looping until we get a unique ID
        do{
            //genrate number
            uuid="";
            for(int i = 0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //check  to make sure it's unique
            nonUnique = false;
            for(User u:this.users){
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        return uuid;
    }
    public String getNewAccountUUID(){
      //inits
      String uuid;
      Random rng = new Random();
      int len = 10;
      boolean nonUnique;
      //continue looping until we get a unique ID
      do{
          //genrate number
          uuid="";
          for(int i = 0;i<len;i++){
              uuid += ((Integer)rng.nextInt(10)).toString();
          }
          //check  to make sure it's unique
          nonUnique = false;
          for(Account a:this.accounts){
              if(uuid.compareTo(a.getUUID())==0){
                  nonUnique = true;
                  break;
              }
          }
      }while(nonUnique);
      return uuid;  

    }
    public void addAccount(Account anAct){
        this.accounts.add(anAct);
    }

    public User addUser(String firstName,String lastName,String pin){
        //create a new user object and add it to our list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        //create a saving account for the user and add user to User and Bank account lists
        Account newAccount = new Account("Savings",newUser,this);
     
         newUser.addAccount(newAccount);
         this.accounts.add(newAccount);

         return newUser;
    }
    public User userLogin(String userID,String pin){
        //search through lists of user
        for(User u:this.users){
            //check usser ID is correct
            if(u.getUUID().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;//either pin or user invalid or both invalid

    }
    public String getName(){
        return this.name;
    }
}




public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this(amount, "", inAccount);
    }

    public Transaction(double amount, String memo, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = memo;
    }

    public double getAmount() {
        return amount;
    }

    public String getSummaryLine() {
        String amountString = this.amount >= 0 ? String.format("$%.2f", this.amount) : String.format("($%.2f)", -this.amount);
        return String.format("%s : %s : %s", this.timestamp, amountString, this.memo);
    }
}




public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte[] pinHash;
    private List<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pinHash = hashPin(pin);

        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<>();

        System.out.println("New User: " + firstName + " " + lastName + " with ID: " + this.uuid + " created");
    }

    public void addAccount(Account anAct) {
        this.accounts.add(anAct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) {
        byte[] inputPinHash = hashPin(aPin);
        return MessageDigest.isEqual(inputPinHash, this.pinHash);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountSummary() {
        System.out.println(this.firstName + "'s account Summary:");
        int accountNumber = 1;
        for (Account account : this.accounts) {
            System.out.println(accountNumber + " " + account.getSummaryLine());
            accountNumber++;
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printActTransHistory(int actIdx) {
        Account account = this.accounts.get(actIdx);
        account.printTransHistory();
    }

    public double getAccountBalance(int actIdx) {
        Account account = this.accounts.get(actIdx);
        return account.getBalance();
    }

    public String getActUUID(int actIdx) {
        Account account = this.accounts.get(actIdx);
        return account.getUUID();
    }

    public void addActTransaction(int actIdx, double amount, String memo) {
        Account account = this.accounts.get(actIdx);
        account.addTransaction(amount, memo);
    }

    private byte[] hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
            return null; // This line is unreachable, added to satisfy the compiler.
        }
    }
}