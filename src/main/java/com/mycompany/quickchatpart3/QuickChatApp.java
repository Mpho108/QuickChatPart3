/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

/**
 *
 * @author maboy
 */
import java.util.Scanner;

public class QuickChatApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("===== REGISTRATION =====");

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Create username: ");
        String username = input.nextLine();

        System.out.print("Create password: ");
        String password = input.nextLine();

        System.out.print("Enter SA phone number (+27): ");
        String phoneNumber = input.nextLine();

        Login user = new Login(firstName, lastName, username, password, phoneNumber);

        System.out.println(user.registerUser());

        if (!user.checkUserName()
                || !user.checkPasswordComplexity()
                || !user.checkCellPhoneNumber()) {

            System.out.println("Registration failed.");
            input.close();
            return;
        }

        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();

        boolean loginSuccessful = user.loginUser(enteredUsername, enteredPassword);

        System.out.println(user.returnLoginStatus(loginSuccessful));

        if (!loginSuccessful) {
            input.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat");

        System.out.print("How many messages would you like to enter: ");
        int numberOfMessages = input.nextInt();
        input.nextLine();

        Message.createArrays(numberOfMessages);

        int messagesEntered = 0;
        boolean running = true;

        while (running) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show Sent Messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose: ");

            int menuChoice = input.nextInt();
            input.nextLine();

            switch (menuChoice) {

                case 1:

                    if (messagesEntered >= numberOfMessages) {
                        System.out.println("Maximum messages reached.");
                        break;
                    }

                    for (int i = messagesEntered; i < numberOfMessages; i++) {

                        System.out.println("\nMessage " + (i + 1));

                        System.out.print("Recipient number: ");
                        String recipient = input.nextLine();

                        System.out.print("Message: ");
                        String messageText = input.nextLine();

                        Message message = new Message(
                                i + 1,
                                username,
                                recipient,
                                messageText
                        );

                        if (message.checkRecipientCell() == 0) {
                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            System.out.println("This message was not created.");
                            continue;
                        } else {
                            System.out.println("Cell phone number successfully captured.");
                        }

                        System.out.println(message.checkMessageLength());

                        if (messageText.length() > 250) {
                            System.out.println("This message was not sent because it exceeds the character limit.");
                            continue;
                        }

                        System.out.println("\n1) Send");
                        System.out.println("2) Disregard");
                        System.out.println("3) Store");
                        System.out.print("Choose: ");

                        int option = input.nextInt();
                        input.nextLine();

                        System.out.println(message.sentMessage(option));

                        if (option == 1 || option == 3) {
                            System.out.println("\nMessage ID: " + message.getMessageID());
                            System.out.println("Message Hash: " + message.getMessageHash());
                            System.out.println("Recipient: " + message.getRecipient());
                            System.out.println("Message: " + message.getMessageText());
                        }

                        if (option == 1 || option == 2 || option == 3) {
                            messagesEntered++;
                        }
                    }

                    break;

                case 2:

                    Message sentMessages = new Message(
                            0,
                            username,
                            "+27000000000",
                            "Temp"
                    );

                    System.out.println(sentMessages.printMessages());

                    break;

                case 3:

                    Message finalMessage = new Message(
                            0,
                            username,
                            "+27000000000",
                            "Temp"
                    );

                    System.out.println("\nTotal messages sent: "
                            + finalMessage.returnTotalMessages());

                    running = false;
                    break;

                case 4:

                    boolean storedMenuRunning = true;

                    while (storedMenuRunning) {

                        System.out.println("\n===== STORED MESSAGES MENU =====");
                        System.out.println("1) Display sender and recipient of stored messages");
                        System.out.println("2) Display longest stored message");
                        System.out.println("3) Search for a message ID");
                        System.out.println("4) Search messages by recipient");
                        System.out.println("5) Delete a message using message hash");
                        System.out.println("6) Display stored message report");
                        System.out.println("7) Read JSON file and display stored messages");
                        System.out.println("8) Return to main menu");
                        System.out.print("Choose: ");

                        int storedChoice = input.nextInt();
                        input.nextLine();

                        Message storedMessages = new Message(
                                0,
                                username,
                                "+27000000000",
                                "Temp"
                        );

                        switch (storedChoice) {

                            case 1:
                                System.out.println(
                                        storedMessages.displayStoredSendersAndRecipients()
                                );
                                break;

                            case 2:
                                System.out.println(
                                        storedMessages.displayLongestStoredMessage()
                                );
                                break;

                            case 3:
                                System.out.print("Enter message ID: ");
                                String searchID = input.nextLine();

                                System.out.println(
                                        storedMessages.searchMessageByID(searchID)
                                );
                                break;

                            case 4:
                                System.out.print("Enter recipient number: ");
                                String searchRecipient = input.nextLine();

                                System.out.println(
                                        storedMessages.searchMessagesByRecipient(searchRecipient)
                                );
                                break;

                            case 5:
                                System.out.print("Enter message hash: ");
                                String searchHash = input.nextLine();

                                System.out.println(
                                        storedMessages.deleteMessageByHash(searchHash)
                                );
                                break;

                            case 6:
                                System.out.println(
                                        storedMessages.displayReport()
                                );
                                break;

                            case 7:
                                System.out.println(
                                        storedMessages.readStoredMessagesFromJSON()
                                );
                                break;

                            case 8:
                                storedMenuRunning = false;
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }
                    }

                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}