package chatClient.presentation;

import chatClient.data.Data;
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

    }

    public void search(User filter) throws Exception {
        List<User> rows = null;
        rows = data.getContacts().stream().filter(e->e.getNombre().equals(filter.getNombre())).findFirst().orElse(null);
        if (rows!=null && !filter.getNombre().isEmpty()) return rows;
        else if (rows==null && !filter.getNombre().isEmpty()) throw new Exception("Contact does not exits");

    }
}
