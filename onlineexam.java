import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class onlineexam {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean loggedIn = false;
    private static boolean signedUp = false;
    private static Timer timer;
    private static String[][] userCredentials = new String[100][2];
    private static String[][] questions = new String[10][5];
    private static String[][] answers = new String[10][2];
    private static int totalCorrectAnswers = 0;

    public static void main(String[] args) {
        initializeQuestionsAndAnswers();
        while (true) {
            if (!loggedIn && !signedUp) {
                System.out.println("===== Online Exam System =====");
                System.out.println("1. Login");
                System.out.println("2. Signup");
                System.out.println("3. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        signup();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else if (!loggedIn && signedUp) {
                System.out.println("Signup successful! Please login to continue.");
                login();
            } else {
                System.out.println("===== Online Exam System =====");
                System.out.println("1. Update Profile and Password");
                System.out.println("2. Start Exam");
                System.out.println("3. Close Session and Logout");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        updateProfileAndPassword();
                        break;
                    case 2:
                        startExam();
                        break;
                    case 3:
                        closeSessionAndLogout();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    private static void initializeQuestionsAndAnswers() {
        // Initialize the questions and answers
        questions[0][0] = "Which is used to find and fix bugs in the Java programs.?";
        questions[0][1] = "JDB";
        questions[0][2] = "JVM";
        questions[0][3] = "JDK";
        questions[0][4] = "JRE";

        questions[1][0] = "What is the return type of the hashCode() method in the Object class?";
        questions[1][1] = "int";
        questions[1][2] = "Object";
        questions[1][3] = "long";
        questions[1][4] = "void";

        questions[2][0] = "Which package contains the Random class?";
        questions[2][1] = "java.util package";
        questions[2][2] = "java.lang package";
        questions[2][3] = "java.awt package";
        questions[2][4] = "java.io package";

        questions[3][0] = "An interface with no fields or methods is known as?";
        questions[3][1] = "Runnable Interface";
        questions[3][2] = "Abstract Interface";
        questions[3][3] = "Marker Interface";
        questions[3][4] = "CharSequence Interface";

        questions[4][0] = "In which memory a String is stored, when we create a string using new operator?";
        questions[4][1] = "Stack";
        questions[4][2] = "String memory";
        questions[4][3] = "Random storage space";
        questions[4][4] = "Heap memory";

        questions[5][0] = "Which of the following is a marker interface?";
        questions[5][1] = "Runnable interface";
        questions[5][2] = "Remote interface";
        questions[5][3] = "Readable interface";
        questions[5][4] = "Result interface";

        questions[6][0] = "Which keyword is used for accessing the features of a package?";
        questions[6][1] = "import";
        questions[6][2] = "package";
        questions[6][3] = "extends";
        questions[6][4] = "export";

        questions[7][0] = "In java, jar stands for?";
        questions[7][1] = "Java Archive Runner";
        questions[7][2] = "Java Archive";
        questions[7][3] = "Java Application Resource";
        questions[7][4] = "Java Application Runner";

        questions[8][0] = "Which of the following is a mutable class in java?";
        questions[8][1] = "java.lang.StringBuilder";
        questions[8][2] = "java.lang.Short";
        questions[8][3] = "java.lang.Byte";
        questions[8][4] = "java.lang.String";

        questions[9][0] = "Which of the following option leads to the portability and security of Java?";
        questions[9][1] = "Bytecode is executed by JVM";
        questions[9][2] = "The applet makes the Java code secure and portable";
        questions[9][3] = "Use of exception handling";
        questions[9][4] = "Dynamic binding between objects";
        
        answers[0][1] = "JDB";
        answers[1][1] = "int";
        answers[2][1] = "java.util package";
        answers[3][1] = "Marker Interface";
        answers[4][1] = "Heap memory";
        answers[5][1] = "Remote interface";
        answers[6][1] = "import";
        answers[7][1] = "Java Archive";
        answers[8][1] = "java.lang.StringBuilder";
        answers[9][1] = "Bytecode is executed by JVM";
        // Initialize other questions and answers similarly...
        // Initialize answers for other questions similarly...
    }

    private static void login() {
        System.out.println("===== Login =====");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Perform authentication logic here
        // You can use a database or hardcoded values for demonstration purposes
        if (authenticateUser(username, password)) {
            loggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }

        System.out.println();
    }

    private static void signup() {
        System.out.println("===== Signup =====");
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Save the user's credentials in the array
        for (int i = 0; i < userCredentials.length; i++) {
            if (userCredentials[i][0] == null) {
                userCredentials[i][0] = username;
                userCredentials[i][1] = password;
                break;
            }
        }

        signedUp = true;
        System.out.println("Signup successful!");

        System.out.println();
    }

    private static boolean authenticateUser(String username, String password) {
        for (int i = 0; i < userCredentials.length; i++) {
            if (userCredentials[i][0] != null && userCredentials[i][0].equals(username) && userCredentials[i][1].equals(password)) {
                return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }

    private static void updateProfileAndPassword() {
        System.out.println("===== Update Profile and Password =====");
    
        // Prompt the user for the new username
        System.out.print("Enter your new username (leave empty to keep current): ");
        String newUsername = scanner.nextLine();
    
        // Prompt the user for the new password
        System.out.print("Enter your new password (leave empty to keep current): ");
        String newPassword = scanner.nextLine();
    
        // Find the user's current username (you would have a way to determine the logged-in user)
        String currentUsername = getCurrentUsername();
    
        // Check if the user wants to update the username
        if (!newUsername.isEmpty()) {
            boolean isUsernameAvailable = isUsernameAvailable(newUsername);
    
            if (isUsernameAvailable) {
                // Update the username in the userCredentials array
                for (int i = 0; i < userCredentials.length; i++) {
                    if (userCredentials[i][0] != null && userCredentials[i][0].equals(currentUsername)) {
                        userCredentials[i][0] = newUsername;
                    }
                }
                currentUsername = newUsername; // Update the current username
                System.out.println("Username updated successfully!");
            } else {
                System.out.println("Username is already taken. Please choose another username.");
            }
        }
    
        // Check if the user wants to update the password
        if (!newPassword.isEmpty()) {
            // Update the password in the userCredentials array
            for (int i = 0; i < userCredentials.length; i++) {
                if (userCredentials[i][0] != null && userCredentials[i][0].equals(currentUsername)) {
                    userCredentials[i][1] = newPassword;
                }
            }
            System.out.println("Password updated successfully!");
        }
    
        System.out.println();
    }
    
    private static String getCurrentUsername() {
        System.out.println("Type the current user name");
        String st = scanner.nextLine();
        return st;

    }
    
    private static boolean isUsernameAvailable(String username) {
        for (int i = 0; i < userCredentials.length; i++) {
            if (userCredentials[i][0] != null && userCredentials[i][0].equals(username)) {
                return false; // Username is already in use
            }
        }
        return true; // Username is available
    }
    

    private static void startExam() {
        System.out.println("===== Exam =====");
        long examDuration = 15000; // 15 seconds
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                submitExam();
            }
        }, examDuration);


        for (int i = 0; i < questions.length; i++) {
            System.out.println("Q" + (i + 1) + ": " + questions[i][0]);
            System.out.println("1) " + questions[i][1]);
            System.out.println("2) " + questions[i][2]);
            System.out.println("3) " + questions[i][3]);
            System.out.println("4) " + questions[i][4]);

            try {
                System.out.print("Your answer (enter the corresponding number): ");
                int answer = scanner.nextInt();
                if (answer >= 1 && answer <= 4) {
                if (questions[i][answer].equals(answers[i][1])) {
                    System.out.println("Correct answer!");
                    totalCorrectAnswers++;
                } else {
                    System.out.println("Incorrect answer!");
                }
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
                
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // ... (remaining exam logic)

        timer.cancel();
        System.out.println("Exam completed. Your score is : \n Submitting answers...");
        submitExam();
        System.out.println();
    }

    private static void submitExam() {
        // Implement logic to submit the answers automatically
        System.out.println("Submitting exam... \n Your score : " + totalCorrectAnswers);
        // Additional code to handle the submission process
        System.exit(0); // Exit the program after submission
    }

    private static void closeSessionAndLogout() {
        System.out.println("Closing session and logging out...");
        loggedIn = false;
    }
}
