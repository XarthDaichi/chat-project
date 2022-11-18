package chatClient.data;

import chatProtocol.Message;
import chatProtocol.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private List<User> contacts;

    private List<Message> messages;

    public Data() {
        contacts = new ArrayList<User>(Arrays.asList(new User("001", "000", "Diego", false),new User("002", "000", "Jorge", false), new User("003", "000", "Sofia", false)));
    }
    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
