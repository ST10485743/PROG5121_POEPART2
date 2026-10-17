package com.mycompany.prog5121_poe2;

import java.util.Scanner;

/**
 *
 * @author leago
 */
public class Main {
    
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        registerUser(scanner, login);
        logInUser(scanner, login);

        // Part 2: users may only reach QuickChat once they've logged in
        // successfully.
        runQuickChat(scanner);

        scanner.close();
    }

    // ---------- Part 1: Registration and login ----------

    private static void registerUser(Scanner scanner, Login login) {
        System.out.println("=== Chat App - Registration ===");

        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhoneNumber;
        String registrationResult;

        do {
            System.out.print("Enter first name: ");
            firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            lastName = scanner.nextLine();

            System.out.print("Enter username (must contain '_' and be no more than 5 characters): ");
            username = scanner.nextLine();

            System.out.print("Enter password (min 8 characters, a capital letter, a number, a special character): ");
            password = scanner.nextLine();

            System.out.print("Enter South African cell phone number (e.g. +27838968976): ");
            cellPhoneNumber = scanner.nextLine();

            registrationResult = login.registerUser(firstName, lastName, username, password, cellPhoneNumber);
            System.out.println(registrationResult);
            System.out.println();

        } while (!registrationResult.startsWith("Username successfully captured"));
    }

    private static void logInUser(Scanner scanner, Login login) {
        System.out.println("=== Chat App - Login ===");

        String status;
        do {
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            status = login.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(status);

        } while (!status.startsWith("Welcome"));
    }

    // ---------- Part 2: Sending messages ----------

    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        int numMessages = readInt(scanner, "How many messages would you like to send? ");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            int choice = readInt(scanner, "Choose an option: ");

            switch (choice) {
                case 1:
                    sendMessages(scanner, numMessages);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Please choose 1, 2, or 3.");
            }
        }
    }

    private static void sendMessages(Scanner scanner, int numMessages) {
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("--- Message " + (i + 1) + " of " + numMessages + " ---");

            System.out.print("Enter recipient cell number (e.g. +27838968976): ");
            String recipient = scanner.nextLine();

            System.out.print("Enter message (max 250 characters): ");
            String text = scanner.nextLine();

            Message message = new Message(recipient, text, i);
            System.out.println(message.announceMessageID());

            // Validation results are reported, an invalid recipient or over-length message is
            // still allowed rather than blocking it.
            
            System.out.println(message.checkRecipientCell());
            System.out.println(Message.checkMessageLength(text));

            System.out.println("1) Send Message  2) Disregard Message  3) Store Message to send later");
            int choice = readInt(scanner, "Choose an option: ");
            System.out.println(message.sentMessage(choice));

            if (choice == 1) {
                System.out.println(message.getFullMessageDetails());
            }
        }

        System.out.println();
        System.out.println("Total messages sent so far: " + Message.returnTotalMessages());
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
