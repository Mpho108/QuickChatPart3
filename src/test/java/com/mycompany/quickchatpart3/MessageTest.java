/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MessageTest {

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                "Did you get the cake?"
        );

        assertEquals(1, message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                1,
                "kyl_1",
                "0838884567",
                "It is dinner time!"
        );

        assertEquals(0, message.checkRecipientCell());
    }

    @Test
    public void testMessageLengthUnder250Characters() {
        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                "This message is short enough."
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthOver250Characters() {
        String longMessage = "";

        for (int i = 0; i < 251; i++) {
            longMessage = longMessage + "a";
        }

        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                longMessage
        );

        assertTrue(
                message.checkMessageLength().startsWith(
                        "Message exceeds 250 characters"
                )
        );
    }

    @Test
    public void testMessageIDLength() {
        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                "Did you get the cake?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testMessageHashFormat() {
        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                "Did you get the cake?"
        );

        String hash = message.getMessageHash();

        assertTrue(hash.contains(":1:"));
        assertTrue(hash.endsWith("DIDCAKE?"));
    }

    @Test
    public void testSentMessageAddsToSentMessages() {
        Message.createArrays(2);

        Message message = new Message(
                1,
                "kyl_1",
                "+27834557896",
                "Did you get the cake?"
        );

        assertEquals(
                "Message successfully sent.",
                message.sentMessage(1)
        );

        assertTrue(
                message.printMessages().contains(
                        "Did you get the cake?"
                )
        );
    }

    @Test
    public void testStoredMessageReport() {
        Message.createArrays(2);

        Message message = new Message(
                1,
                "kyl_1",
                "+27838884567",
                "Where are you? You are late!"
        );

        assertEquals(
                "Message successfully stored.",
                message.sentMessage(3)
        );

        assertTrue(
                message.displayReport().contains(
                        "Where are you? You are late!"
                )
        );
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message.createArrays(2);

        Message message = new Message(
                1,
                "kyl_1",
                "+27838884567",
                "Where are you?"
        );

        message.sentMessage(3);

        assertTrue(
                message.searchMessagesByRecipient(
                        "+27838884567"
                ).contains("Where are you?")
        );
    }

    @Test
    public void testDeleteMessageByHash() {
        Message.createArrays(2);

        Message message = new Message(
                1,
                "kyl_1",
                "+27838884567",
                "Where are you?"
        );

        message.sentMessage(3);

        String result = message.deleteMessageByHash(
                message.getMessageHash()
        );

        assertTrue(result.contains("successfully deleted"));
        assertEquals(
                "No stored messages found.",
                message.displayReport()
        );
    }
}
