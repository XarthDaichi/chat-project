/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatClient.presentation;

import chatProtocol.Message;
import chatProtocol.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model extends java.util.Observable {
    User currentUser;
    List<Message> messages;
    List<User> contacts;

    public Model() {
       currentUser = null;
       messages= new ArrayList<>();
//       contacts = new ArrayList<User>(Arrays.asList(new User("001", "000", "Diego", false),new User("002", "000", "Jorge", false), new User("003", "000", "Sofia", false)));
        this.setContacts(new ArrayList<User>());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit(Model.USER+Model.CHAT+Model.CONTACT);
    }
    
    public void commit(int properties){
        this.setChanged();
        this.notifyObservers(properties);        
    } 
    
    public static int USER=1;
    public static int CHAT=2;
    public static int CONTACT=3;
}
