package chatServer.data;

import chatProtocol.User;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<User> users;

    public Data() {
        users = new ArrayList<>();
        users.add(new User("001","001","Juan", false));
        users.add(new User("002","002","Maria", false));
        users.add(new User("003","003","Pedro", false));
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
