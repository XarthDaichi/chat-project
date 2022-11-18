package chatClient.presentation;

import chatClient.data.Data;
import chatClient.logic.Service;
import chatClient.logic.ServiceProxy;
import chatProtocol.Message;
import chatProtocol.User;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;
    
    ServiceProxy localService;
    
    public Controller(View view, Model model) {
        try {
            model.setContacts(Service.instance().getContacts());
            model.setMessages(Service.instance().getMessages());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.view = view;
        this.model = model;
        localService = (ServiceProxy)ServiceProxy.instance();
        localService.setController(this);
        view.setController(this);
        view.setModel(model);
    }

    public void login(User u) throws Exception{
        User logged=ServiceProxy.instance().login(u);
        model.setCurrentUser(logged);
        model.setCurrentReceiver(null);
        Service.instance().load(model.getCurrentUser());
        model.setContacts(Service.instance().getContacts());
        model.setMessages(Service.instance().getMessages());
        model.commit(Model.USER+Model.CHAT);
    }

    public void register(User u) throws Exception {
        ServiceProxy.instance().register(u);
    }

    public void post(String text){
        Message message = new Message();
        message.setMessage(text);
        message.setSender(model.getCurrentUser());
        message.setReceiver(model.getCurrentReceiver());
        try {
            ServiceProxy.instance().post(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.commit(Model.CHAT);
    }

    public void logout(){
        Service.instance().save(model.getCurrentUser());
        try {
            ServiceProxy.instance().logout(model.getCurrentUser());
            model.setMessages(new ArrayList<>());
            model.commit(Model.CHAT);
        } catch (Exception ex) {
        }
        model.setCurrentUser(null);
        model.commit(Model.USER+Model.CHAT);
    }
        
    public void deliver(Message message){
        Service.instance().getMessages().add(message);
        model.setMessages(Service.instance().getMessages());
        model.commit(Model.CHAT);       
    }

    public void addContact(User u) throws Exception {
        for (User checking : Service.instance().getContacts())
            if (checking.equals(u))
                throw new Exception("Contact already exists");
        ServiceProxy.instance().checkContact(u);
    }

    public void addContactToList(User u) throws Exception {
        if (u == null) throw new Exception("User not found");
        else {
            Service.instance().getContacts().add(u);
            model.setContacts(Service.instance().getContacts());
            model.commit(Model.CHAT);
        }
    }

    public void contactLoggedIn(User u) {
        for (User contact : Service.instance().getContacts()) {
            if (contact.equals(u)) {
                contact.setOnline(true);
                model.setContacts(Service.instance().getContacts());
                model.commit(Model.CHAT);
            }
        }
    }

    public void search(User filter) throws Exception {
        List<User> rows = null;
        try {
            rows = Service.instance().contactSearch(filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setContacts(rows);
        model.commit(Model.CHAT);
    }

    public void selectContact(int row) {
        User user = model.getContacts().get(row);
        model.setCurrentReceiver(user);
        model.commit(Model.CHAT);
    }

    public void contactLoggedOut(User loggedOut) {
        for (User contact : Service.instance().getContacts()) {
            if (contact.equals(loggedOut)) {
                contact.setOnline(false);
                model.setContacts(Service.instance().getContacts());
                model.commit(Model.CHAT);
            }
        }
    }
}
