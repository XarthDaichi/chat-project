package chatProtocol;

import java.util.List;

public interface IService {
    public User login(User u) throws Exception;

    public void register(User u) throws Exception;
    public void logout(User u) throws Exception; 
    public void post(Message m) throws Exception;

    public User checkContact(User u) throws Exception;

    public List<Message> checkMessages(User user) throws Exception;

    public void readMessage(Message message) throws Exception;
}
