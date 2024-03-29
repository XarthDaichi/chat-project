
package chatServer;

import chatProtocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import chatProtocol.IService;
import chatProtocol.Message;
import chatProtocol.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class Server {
    ServerSocket srv;
    List<Worker> workers; 
    
    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers =  Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) {
        }
    }
    
    public void run(){
        IService service = new Service();

        boolean continuar = true;
        ObjectInputStream in=null;
        ObjectOutputStream out=null;
        Socket skt=null;
        int method;
        while (continuar) {
            try {
                skt = srv.accept();
                in = new ObjectInputStream(skt.getInputStream());
                out = new ObjectOutputStream(skt.getOutputStream() );
                System.out.println("Conexion Establecida...");
                method = in.readInt();
                switch(method) {
                    case Protocol.LOGIN:
                        try {
                            User user = service.login((User) in.readObject());
                            out.writeInt(Protocol.ERROR_NO_ERROR);
                            out.writeObject(user);
                            out.flush();
                            Worker worker = new Worker(this, in, out, user, service);
                            workers.add(worker);
                            worker.start();
                            for (Worker wk : workers) {
                                wk.contactLogin(user);
                                worker.contactLogin(wk.user);
                            }
                            worker.checkMessages();
                        } catch (Exception ex) {
                            out.writeInt(Protocol.ERROR_LOGIN);
                            out.flush();
                            skt.close();
                            System.out.println("Conexion Cerrada...");
                        }
                        break;
                    case Protocol.REGISTER:
                        try {
                            User user = service.register((User) in.readObject());
                            out.writeInt(Protocol.ERROR_NO_ERROR);
                            out.writeObject(user);
                            out.flush();

                        } catch(Exception ex) {
                            out.writeInt(Protocol.ERROR_REGISTER);
                            out.flush();
                            skt.close();
                            System.out.println("Conexion cerrrada...");
                        }
                        break;
                    default:
                        out.writeInt(Protocol.ERROR_LOGIN);
                        out.flush();
                        skt.close();
                        System.out.println("Conexion cerrada...");
                }

            }
            catch (Exception ex) {}
        }
    }
    
    private User login(ObjectInputStream in,ObjectOutputStream out,IService service) throws IOException, ClassNotFoundException, Exception{
        int method = in.readInt();
        if (method!=Protocol.LOGIN) throw new Exception("Should login first");
        User user=(User)in.readObject();                          
        user=service.login(user);
        out.writeInt(Protocol.ERROR_NO_ERROR);
        out.writeObject(user);
        out.flush();
        return user;
    }

    private void register(ObjectInputStream in, ObjectOutputStream out, IService service) throws IOException, ClassNotFoundException, Exception {
        int method = in.readInt();
        if (method!=Protocol.REGISTER) throw new Exception("Should register first");
        User user=(User)in.readObject();
        service.register(user);
        out.writeInt(Protocol.ERROR_NO_ERROR);
        out.flush();
    }
    
    public void deliver(Message message){
        for(Worker wk:workers){
            if (wk.user.equals(message.getReceiver()) || wk.user.equals(message.getSender())) {
                wk.deliver(message);
            }
        }        
    }

    public void logout(User user) {
        for (Worker wk : workers) {
            wk.contactLogout(user);
        }
    }
    
    public void remove(User u){
        for(Worker wk:workers) if(wk.user.equals(u)){workers.remove(wk);break;}
        System.out.println("Quedan: " + workers.size());
    }
    
}