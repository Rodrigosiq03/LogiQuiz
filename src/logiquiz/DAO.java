package logiquiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class DAO {

    public boolean existe(Usuario usuario) throws Exception {
        String sqlusuario = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
        SQLConnection con = new SQLConnection();
        try (Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();

            }
        }

    }

    public boolean permissao(Usuario admin) throws Exception {
        String sqladmin = "SELECT permissoes FROM usuario WHERE nome = ? AND senha = ?";
        SQLConnection con = new SQLConnection();
        try (Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqladmin);
            ps.setString(1, admin.getNome());
            ps.setString(2, admin.getSenha());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String permissao = rs.getString("permissoes");
                    if (permissao.equals("admin")) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        }
        return false;
    }

}
