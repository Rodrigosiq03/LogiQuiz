package logiquiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class DAO {
 public boolean existe (Usuario usuario) throws Exception{
 String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
 SQLConnection con = new SQLConnection();
 try (Connection c2 = con.obtemConexao()){
 PreparedStatement ps = c2.prepareStatement(sql);
 ps.setString(1, usuario.getNome());
 ps.setString(2, usuario.getSenha());
 try (ResultSet rs = ps.executeQuery()){
 return rs.next();
 
 }
 }
}
}
