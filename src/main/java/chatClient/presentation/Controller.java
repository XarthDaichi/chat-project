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

    Data data;
    
    ServiceProxy localService;
    
    public Controller(View view, Model model) {
        try {
            model.setContacts(Service.instance().contactSearch(new User()));
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
        model.commit(Model.USER);
    }

    public void register(User u) throws Exception {
        ServiceProxy.instance().register(u);
    }

    public void post(String text, int receiver){
        User receiverObject = model.getContacts().get(receiver);
        Message message = new Message();
        message.setMessage(text);
        message.setSender(model.getCurrentUser());
        message.setReceiver(receiverObject);
        ServiceProxy.instance().post(message);
        model.commit(Model.CHAT);
    }

    public void logout(){
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
        model.messages.add(message);
        model.commit(Model.CHAT);       
    }

    public void addContact(User u) throws Exception {
        User newContact = ServiceProxy.instance().checkContact(u);
        model.
    }

    public void search(User filter) throws Exception {
        List<User> rows = null;
        try {
            rows = Service.instance().contactSearch(filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setContacts(rows);
        model.commit(Model.CONTACT);
    }
}
