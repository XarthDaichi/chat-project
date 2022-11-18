package chatClient.logic;

import chatClient.data.Data;
import chatClient.data.XmlPersister;
import chatProtocol.User;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private Data data;

    private Service() {
        data = new Data();
//        try {
//            data= XmlPersister.instance().load();
//        } catch(Exception e) {
//            data = new Data();
//        }
    }

    public List<User> contactSearch(User filter) throws Exception {
        return data.getContacts().stream().filter(element->element.toString().contains(filter.toString())).collect(toCollection(ArrayList::new));
    }

    public User getContact(User filter) throws Exception {
        User result = data.getContacts().stream().filter(e->e.getNombre().equals(filter.getNombre())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Contact does not exist");
    }
}
