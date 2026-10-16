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

    
}
