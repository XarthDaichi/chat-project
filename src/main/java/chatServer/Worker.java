package chatServer;

import chatProtocol.User;
import chatProtocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import chatProtocol.IService;
import chatProtocol.Message;

public class Worker {
    Server srv;
    ObjectInputStream in;
    ObjectOutputStream out;
    IService service;
    User user;

    public Worker(Server srv, ObjectInputStream in, ObjectOutputStream out, User user, IService service) {
        this.srv=srv;
        this.in=in;
        this.out=out;
        this.user=user;
        this.service=service;
    }

    boolean continuar;    
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) {  
        }
    }
    
    public void stop(){
        continuar=false;
        System.out.println("Conexion cerrada...");
    }
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = in.readInt();
                System.out.println("Operacion: "+method);
                switch(method){
                //case Protocol.LOGIN: done on accept
                case Protocol.LOGOUT:
                    try {
                        srv.logout(user);
                        srv.remove(user);
                        //service.logout(user); //nothing to do
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.POST:
                    Message message=null;
                    try {
                        message = (Message)in.readObject();
//                        message.setSender(user);
                        srv.deliver(message);
                        if (!receiverLoggedIn(message)) {
                            service.post(message); // if wants to save messages, ex. recivier no logged on
                        }
                        System.out.println(user.getNombre() + ": " + message.getMessage());
                    } catch (ClassNotFoundException ex) {} catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case Protocol.CONTACT:
                    try {
                        User checkingContact = (User) in.readObject();
                        System.out.println("Trying to add " + checkingContact.getNombre());
                        if (Service.instance().checkContact(checkingContact).equals(new User()))
                            out.writeInt(Protocol.ERROR_CONTACT);
                        else {
                            out.writeInt(Protocol.CONTACT_RESPONSE);
                        }
                        out.writeObject(checkingContact);
                        out.flush();
                        if (contactLoggedIn(checkingContact)) {
                            this.contactLogin(checkingContact);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                    break;
                }
                out.flush();
            } catch (IOException  ex) {
                System.out.println(ex);
                continuar = false;
            }                        
        }
    }
    
    public void deliver(Message message){
        try {
            out.writeInt(Protocol.DELIVER);
            out.writeObject(message);
            out.flush();
        } catch (IOException ex) {
        }
    }

    public boolean receiverLoggedIn(Message message) {
        for (Worker wk:srv.workers){
            if (wk.user.equals(message.getReceiver())) {
                return true;
            }
        }
        return false;
    }

    public boolean contactLoggedIn(User u) {
        for (Worker wk:srv.workers) {
            if (wk.user.equals(u)) {
                return true;
            }
        }
        return false;
    }

    public void contactLogin(User user) {
        try {
            out.writeInt(Protocol.CONTACT_LOGIN);
            out.writeObject(user);
            out.flush();
        } catch(IOException ex) {

        }
    }

    public void contactLogout(User user) {
        try {
            out.writeInt(Protocol.CONTACT_LOGOUT);
            out.writeObject(user);
            out.flush();
        } catch(IOException ex) {

        }
    }

    public void checkMessages() {
        try {
            List<Message> missedMessages = service.checkMessages(user);
            for (Message message : missedMessages) {
                this.deliver(message);
                service.readMessage(message);
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
