package chatServer.data;

import chatProtocol.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDao {
    Database db;

    public UsuarioDao() {
        db = Database.instance();
    }

    public void create(User u) throws Exception {
        String sql = "insert into " +
                "Usuario " +
                "(id, clave, nombre) " +
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getId());
        stm.setString(2,u.getClave());
        stm.setString(3, u.getNombre());

        db.executeUpdate(stm);
    }

    public User read(String id, String clave) throws Exception {
        String sql = "select " +
                "* " +
                "from Usuario u " +
                "where u.id=? and u.clave=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        stm.setString(2, clave);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "u");
        } else {
            throw new Exception("USUARIO NO EXISTE");
        }
    }

    public void update(User u) throws Exception {
        String sql = "update " +
                "Usuario " +
                "set clave=?, nombre=? " +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getClave());
        stm.setString(2, u.getNombre());
        stm.setString(3, u.getId());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("USUARIO NO EXISTE");
        }
    }

    public void delete(User u) throws Exception {
        String sql = "delete " +
                "from Usuario " +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("USUARIO NO EXISTE");
        }
    }

    public List<User> findByNombre(String nombre) throws Exception {
        List<User> resultado = new ArrayList<User>();
        String sql = "select * " +
                "from " +
                "usuario u " +
                "where u.nombre like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + nombre + "%");
        ResultSet rs = db.executeQuery(stm);
        while(rs.next()) {
            resultado.add(from(rs, "u"));
        }
        return resultado;
    }

    public User from(ResultSet rs, String alias) throws Exception {
        User u = new User();
        u.setId(rs.getString(alias + ".id"));
        u.setClave(rs.getString(alias + ".clave"));
        u.setNombre(rs.getString(alias + ".nombre"));
        return u;
    }
}
