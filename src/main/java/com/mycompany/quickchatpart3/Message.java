/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

/**
 *
 * @author maboy
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String messageID;
    private int messageNumber;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus;

    private static String[] messageIDs;
    private static String[] messageHashes;
    private static String[] senders;
    private static String[] recipients;
    private static String[] messages;
    private static String[] messageStatuses;

    private static int messageCount = 0;
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String sender, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = createMessageID();
        this.messageHash = createMessageHash();
        this.messageStatus = "Created";
    }

    public static void createArrays(int size) {
        messageIDs = new String[size];
        messageHashes = new String[size];
        senders = new String[size];
        recipients = new String[size];
        messages = new String[size];
        messageStatuses = new String[size];

        messageCount = 0;
        totalMessagesSent = 0;
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public int checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 12) {
            return 1;
        } else {
            return 0;
        }
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extraCharacters = messageText.length() - 250;
            return "Message exceeds 250 characters by " + extraCharacters + ", please reduce size.";
        }
    }

    private String createMessageID() {
        String number = "" + (long) (Math.random() * 10000000000L);

        while (number.length() < 10) {
            number = number + "0";
        }

        return number.substring(0, 10);
    }

    public String createMessageHash() {
        String cleanMessage = messageText.trim();

        while (cleanMessage.contains("  ")) {
            cleanMessage = cleanMessage.replace("  ", " ");
        }

        String firstWord;
        String lastWord;

        int firstSpace = cleanMessage.indexOf(" ");
        int lastSpace = cleanMessage.lastIndexOf(" ");

        if (firstSpace == -1) {
            firstWord = cleanMessage;
            lastWord = cleanMessage;
        } else {
            firstWord = cleanMessage.substring(0, firstSpace);
            lastWord = cleanMessage.substring(lastSpace + 1);
        }

        return (messageID.substring(0, 2) + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int userChoice) {
        if (messageIDs == null) {
            return "Message arrays have not been created.";
        }

        if (messageCount >= messageIDs.length) {
            return "Maximum messages reached.";
        }

        switch (userChoice) {
            case 1:
                messageStatus = "Sent";
                totalMessagesSent++;
                addMessageToArrays();
                return "Message successfully sent.";

            case 2:
                messageStatus = "Disregard";
                addMessageToArrays();
                return "Message disregarded.";

            case 3:
                messageStatus = "Stored";
                addMessageToArrays();
                storeMessage();
                return "Message successfully stored.";

            default:
                return "Invalid option selected.";
        }
    }

    private void addMessageToArrays() {
        messageIDs[messageCount] = messageID;
        messageHashes[messageCount] = messageHash;
        senders[messageCount] = sender;
        recipients[messageCount] = recipient;
        messages[messageCount] = messageText;
        messageStatuses[messageCount] = messageStatus;

        messageCount++;
    }

    public String printMessages() {
        String output = "";

        for (int i = 0; i < messageCount; i++) {
            if (messageStatuses[i].equals("Sent")) {
                output = output
                        + "\nMessage ID: " + messageIDs[i]
                        + "\nMessage Hash: " + messageHashes[i]
                        + "\nRecipient: " + recipients[i]
                        + "\nMessage: " + messages[i]
                        + "\n";
            }
        }

        if (output.equals("")) {
            return "No messages have been sent yet.";
        }

        return output;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String displayStoredSendersAndRecipients() {
        String output = "";

        for (int i = 0; i < messageCount; i++) {
            if (messageStatuses[i].equals("Stored")) {
                output = output
                        + "\nSender: " + senders[i]
                        + "\nRecipient: " + recipients[i]
                        + "\n";
            }
        }

        if (output.equals("")) {
            return "No stored messages found.";
        }

        return output;
    }

    public String displayLongestStoredMessage() {
        String longestMessage = "";
        boolean storedMessageFound = false;

        for (int i = 0; i < messageCount; i++) {
            if (messageStatuses[i].equals("Stored")) {
                if (!storedMessageFound || messages[i].length() > longestMessage.length()) {
                    longestMessage = messages[i];
                    storedMessageFound = true;
                }
            }
        }

        if (!storedMessageFound) {
            return "No stored messages found.";
        }

        return longestMessage;
    }

    public String searchMessageByID(String searchID) {
        for (int i = 0; i < messageCount; i++) {
            if (messageIDs[i].equals(searchID)) {
                return "Recipient: " + recipients[i]
                        + "\nMessage: " + messages[i];
            }
        }

        return "Message ID not found.";
    }

    public String searchMessagesByRecipient(String searchRecipient) {
        String output = "";

        for (int i = 0; i < messageCount; i++) {
            if (recipients[i].equals(searchRecipient)
                    && (messageStatuses[i].equals("Sent")
                    || messageStatuses[i].equals("Stored"))) {

                output = output
                        + "\nMessage: " + messages[i]
                        + "\n";
            }
        }

        if (output.equals("")) {
            return "No sent or stored messages found for this recipient.";
        }

        return output;
    }

    public String deleteMessageByHash(String searchHash) {
    for (int i = 0; i < messageCount; i++) {
        if (messageHashes[i].equals(searchHash)) {
            String deletedMessage = messages[i];

            for (int j = i; j < messageCount - 1; j++) {
                messageIDs[j] = messageIDs[j + 1];
                messageHashes[j] = messageHashes[j + 1];
                senders[j] = senders[j + 1];
                recipients[j] = recipients[j + 1];
                messages[j] = messages[j + 1];
                messageStatuses[j] = messageStatuses[j + 1];
            }

            messageIDs[messageCount - 1] = null;
            messageHashes[messageCount - 1] = null;
            senders[messageCount - 1] = null;
            recipients[messageCount - 1] = null;
            messages[messageCount - 1] = null;
            messageStatuses[messageCount - 1] = null;

            messageCount--;

            rewriteStoredMessagesFile();

            return "Message: \"" + deletedMessage + "\" successfully deleted.";
        }
    }

    return "Message hash not found.";
}

    public String displayReport() {
        String output = "STORED MESSAGE REPORT\n";

        boolean storedMessageFound = false;

        for (int i = 0; i < messageCount; i++) {
            if (messageStatuses[i].equals("Stored")) {
                storedMessageFound = true;

                output = output
                        + "\nMessage Hash: " + messageHashes[i]
                        + "\nRecipient: " + recipients[i]
                        + "\nMessage: " + messages[i]
                        + "\n";
            }
        }

        if (!storedMessageFound) {
            return "No stored messages found.";
        }

        return output;
    }

    public void storeMessage() {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);

            writer.write("{\n");
            writer.write("\"messageID\":\"" + messageID + "\",\n");
            writer.write("\"messageHash\":\"" + messageHash + "\",\n");
            writer.write("\"sender\":\"" + sender + "\",\n");
            writer.write("\"recipient\":\"" + recipient + "\",\n");
            writer.write("\"message\":\"" + messageText + "\",\n");
            writer.write("\"status\":\"" + messageStatus + "\"\n");
            writer.write("}\n");

            writer.close();

        } catch (IOException error) {
            System.out.println("The message could not be stored.");
        }
    }

    public String readStoredMessagesFromJSON() {
        File file = new File("storedMessages.json");

        if (!file.exists()) {
            return "No JSON file found.";
        }

        String output = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            String jsonID = "";
            String jsonHash = "";
            String jsonSender = "";
            String jsonRecipient = "";
            String jsonMessage = "";
            String jsonStatus = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("\"messageID\"")) {
                    jsonID = getJSONValue(line);
                } else if (line.startsWith("\"messageHash\"")) {
                    jsonHash = getJSONValue(line);
                } else if (line.startsWith("\"sender\"")) {
                    jsonSender = getJSONValue(line);
                } else if (line.startsWith("\"recipient\"")) {
                    jsonRecipient = getJSONValue(line);
                } else if (line.startsWith("\"message\"")) {
                    jsonMessage = getJSONValue(line);
                } else if (line.startsWith("\"status\"")) {
                    jsonStatus = getJSONValue(line);
                } else if (line.equals("}")) {
                    output = output
                            + "\nMessage ID: " + jsonID
                            + "\nMessage Hash: " + jsonHash
                            + "\nSender: " + jsonSender
                            + "\nRecipient: " + jsonRecipient
                            + "\nMessage: " + jsonMessage
                            + "\nStatus: " + jsonStatus
                            + "\n";
                }
            }

            reader.close();

        } catch (IOException error) {
            return "The JSON file could not be read.";
        }

        if (output.equals("")) {
            return "No stored messages found in the JSON file.";
        }

        return output;
    }

    private String getJSONValue(String line) {
        int firstQuote = line.indexOf("\"", line.indexOf(":") + 1);
        int lastQuote = line.lastIndexOf("\"");

        if (firstQuote == -1 || lastQuote == -1 || firstQuote == lastQuote) {
            return "";
        }

        return line.substring(firstQuote + 1, lastQuote);
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
    
    private void rewriteStoredMessagesFile() {
    try {
        FileWriter writer = new FileWriter("storedMessages.json", false);

        for (int i = 0; i < messageCount; i++) {
            if (messageStatuses[i].equals("Stored")) {
                writer.write("{\n");
                writer.write("\"messageID\":\"" + messageIDs[i] + "\",\n");
                writer.write("\"messageHash\":\"" + messageHashes[i] + "\",\n");
                writer.write("\"sender\":\"" + senders[i] + "\",\n");
                writer.write("\"recipient\":\"" + recipients[i] + "\",\n");
                writer.write("\"message\":\"" + messages[i] + "\",\n");
                writer.write("\"status\":\"" + messageStatuses[i] + "\"\n");
                writer.write("}\n");
            }
        }

        writer.close();

    } catch (IOException error) {
        System.out.println("The JSON file could not be updated.");
    }
}
}