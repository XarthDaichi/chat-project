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
    }

    public List<User> contactSearch(User filter) throws Exception {
        return data.getContacts().stream().filter(element->element.getNombre().contains(filter.getNombre())).collect(toCollection(ArrayList::new));
    }

    public User getContact(User filter) throws Exception {
        User result = data.getContacts().stream().filter(e->e.getNombre().equals(filter.getNombre())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Contact does not exist");
    }

    public List<User> getContacts() {
        return data.getContacts();
    }

    public void load(User user) {
        try {
            data = XmlPersister.instance(user).load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(User user) {
        try {
            XmlPersister.instance(user).store(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
