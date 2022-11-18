package chatServer;

import chatProtocol.User;
import chatProtocol.IService;
import chatProtocol.Message;
import chatServer.data.MessageDao;
import chatServer.data.UsuarioDao;

import java.util.List;

public class Service implements IService{
    private static Service theInstance;
//    private Data data;
    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private UsuarioDao usuarioDao;
    private MessageDao messageDao;

    public Service() {
//        data =  new Data();
        usuarioDao = new UsuarioDao();
        messageDao = new MessageDao();
    }
    
    public void post(Message m) throws Exception{
        messageDao.create(m); // if wants to save messages, ex. recivier no logged on
    }
    
    public User login(User p) throws Exception{
        //for(User u:data.getUsers()) if(p.equals(u)) return u;
        //throw new Exception("User does not exist");

//        p.setNombre(p.getId());
        return usuarioDao.read(p.getId(), p.getClave());
    }

    public User register(User p) throws Exception {
        usuarioDao.create(p);
        return p;
    }

    public void logout(User p) throws Exception{
        //nothing to do
    }

    public User checkContact(User u) throws Exception {
        return usuarioDao.read(u.getId());
    }

    public List<Message> checkMessages(User user) throws Exception {
        return messageDao.read(user);
    }

    public void readMessage(Message read) throws Exception {
        messageDao.delete(read);
    }
}
