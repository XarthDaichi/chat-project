/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatClient.presentation;

import chatProtocol.Message;
import chatProtocol.User;

import java.util.ArrayList;
import java.util.List;

public class Model extends java.util.Observable {
    User currentUser;
    User currentReceiver;
    List<Message> messages;
    List<User> contacts;

    public Model() {
       currentUser = null;
       messages= new ArrayList<>();
       this.setContacts(new ArrayList<User>());
    }

    public User getCurrentReceiver() {
        return currentReceiver;
    }

    public void setCurrentReceiver(User user) {
        this.currentReceiver = user;
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
        this.commit(Model.USER+Model.CHAT);
    }
    
    public void commit(int properties){
        this.setChanged();
        this.notifyObservers(properties);        
    } 
    
    public static int USER=1;
    public static int CHAT=2;
}
