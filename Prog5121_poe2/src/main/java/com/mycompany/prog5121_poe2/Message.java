package com.mycompany.prog5121_poe2;

/**
 *
 * @author leago
 */
public class Message {

    private static final List<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private final int messageNumber;

    
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Builds a ten-character numeric ID via string manipulation: a longer
    // random digit string is generated, then cut down to exactly ten
    // characters with substring(), rather than being hard-coded.
    
    private String generateMessageID() {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            digits.append((int) (Math.random() * 10));
        }
        return digits.substring(0, 10);
    }

    
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    
    public String announceMessageID() {
        return "Message ID generated: " + messageID;
    }

    
    public String checkRecipientCell() {
        if (recipient != null && Login.CELL_PHONE_PATTERN.matcher(recipient).matches()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    
    public static String checkMessageLength(String messageText) {
        if (messageText != null && messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    
    public String createMessageHash() {
        String idPrefix = (messageID != null && messageID.length() >= 2)
                ? messageID.substring(0, 2) : "";

        String[] words = messageText.trim().split("\\s+");
        String firstWord = stripPunctuation(words[0]);
        String lastWord = stripPunctuation(words[words.length - 1]);

        return (idPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    private String stripPunctuation(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

   
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.getFullMessageDetails()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    
    public String getFullMessageDetails() {
        return "Message ID: " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    
    public void storeMessage() {
        Path path = Path.of("storedMessages.json");
        try {
            String existing = Files.exists(path) ? Files.readString(path).trim() : "[]";
            String inner = existing.length() > 2
                    ? existing.substring(1, existing.length() - 1) + ","
                    : "";
            String updated = "[" + inner + toJson() + "]";
            Files.writeString(path, updated);
        } catch (IOException e) {
            System.out.println("Could not store message to JSON: " + e.getMessage());
        }
    }

    private String toJson() {
        return "{"
                + "\"messageID\":\"" + escapeJson(messageID) + "\","
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\","
                + "\"recipient\":\"" + escapeJson(recipient) + "\","
                + "\"message\":\"" + escapeJson(messageText) + "\""
                + "}";
    }

    private String escapeJson(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    
    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    /** Test-only: clear session state between tests. */
   public static void resetSession() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }
}
