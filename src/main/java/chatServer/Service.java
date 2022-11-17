package chatServer;

import chatProtocol.User;
import chatProtocol.IService;
import chatProtocol.Message;
import chatServer.data.Data;
import chatServer.data.UsuarioDao;
import java.util.List;

public class Service implements IService{
    private static Service theInstance;
//    private Data data;
    private static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private UsuarioDao usuarioDao;

    public Service() {
//        data =  new Data();
        usuarioDao = new UsuarioDao();
    }
    
    public void post(Message m){
        // if wants to save messages, ex. recivier no logged on
    }
    
    public User login(User p) throws Exception{
        //for(User u:data.getUsers()) if(p.equals(u)) return u;
        //throw new Exception("User does not exist");

//        p.setNombre(p.getId());
        return usuarioDao.read(p.getId(), p.getClave());
    }

    public void register(User p) throws Exception {
        usuarioDao.create(p);
    }

    public void logout(User p) throws Exception{
        //nothing to do
    }

    public boolean checkContact(User u) throws Exception {
        return false;
    }
}
