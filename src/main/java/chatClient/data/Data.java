package chatClient.data;

import chatProtocol.Message;
import chatProtocol.User;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.util.stream.Collectors.toCollection;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    protected List<User> contacts;

    protected List<Message> messages;

    public Data() {
//        contacts = new ArrayList<User>();
        contacts = new ArrayList<User>(Arrays.asList(new User("001", "000", "Diego", false),new User("002", "000", "Jorge", false), new User("003", "000", "Sofia", false)));
        messages = new ArrayList<Message>();
    }

    public Data(List<User> contacts, List<Message> messages) {
        this.contacts = contacts;
        this.messages = messages;
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
