package chatServer.data;

import chatProtocol.Message;
import chatProtocol.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    Database db;
    public MessageDao() {
        db = Database.instance();
    }

    public void create(Message m) throws Exception {
        String sql = "insert into " +
                "Message " +
                "(sender, receiver, note, orderEntered) " +
                "values(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getSender().getId());
        stm.setString(2, m.getReceiver().getId());
        stm.setString(3, m.getMessage());
        stm.setInt(4, this.maxCount()+1);

        db.executeUpdate(stm);
    }

    public int maxCount() throws Exception {
        String sql = "select max(orderEntered) " +
                "from " +
                "Message m";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void delete(Message m) throws Exception {
        String sql = "delete " +
                "from Message " +
                "where note=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getMessage());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Message does not exist");
        }
    }

    public List<Message> read(User receiver) throws Exception {
        UsuarioDao usuarioDao = new UsuarioDao();
        List<Message> result = new ArrayList<Message>();
        String sql = "select * " +
                "from " +
                "Message m " +
                " inner join Usuario u on m.sender=u.id " +
                " inner join Usuario s on m.receiver=s.id " +
                "where m.receiver=?" +
                "order by orderEntered asc";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, receiver.getId());
        ResultSet rs = db.executeQuery(stm);
        Message message;
        while (rs.next()) {
            message = from(rs, "m");
            message.setSender(usuarioDao.from(rs, "u"));
            message.setReceiver(usuarioDao.from(rs, "s"));
            result.add(message);
        }
        return result;
    }

    public Message from(ResultSet rs, String alias) throws Exception {
        Message m = new Message();
        m.setMessage(rs.getString(alias + ".note"));
        return m;
    }
}
