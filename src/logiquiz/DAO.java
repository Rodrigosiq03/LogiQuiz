package logiquiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {

    private String result;

    public boolean existe(Usuario usuario) throws Exception {
        String sqlusuario = "SELECT * FROM usuario WHERE nome = ? AND senha = ?";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();

            }
        }

    }

    public boolean permissao(Usuario admin) throws Exception {
        String sqladmin = "SELECT permissoes FROM usuario WHERE nome = ? AND senha = ?";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqladmin);
            ps.setString(1, admin.getNome());
            ps.setString(2, admin.getSenha());
            try ( ResultSet rs = ps.executeQuery()) {
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

    public String enunciado(int id) throws Exception {
        String sqlusuario = "SELECT enunciado FROM exercicio WHERE id_questao = ?";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("enunciado");
                } else {
                    return "erro";
                }

            }
        }

    }

    public String alternativas(int id) throws Exception {
        String aux = "";
        String sqlusuario = "SELECT alternativa FROM alternativa WHERE idAlternativa = ?";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String alternativa = rs.getString(1);
                aux = String.format(aux + alternativa);
            } else {
                String alternativa = "a";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;

    }

    public int pontos(String nome) {
        int pontos = 0;
        String sqlpontos = "SELECT pontos FROM usuario WHERE nome = ?";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlpontos);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pontos = rs.getInt(1);
            } else {
                pontos = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pontos;
    }

    public void pontuar(Usuario usuario) {
        DAO dao = new DAO();
        SQLConnection con = new SQLConnection();
        String nome = usuario.getNome();
        int pontos = dao.pontos(nome);
        String sqlalternativacerta = "UPDATE `db_logquiz`.`usuario` SET `pontos` = ? WHERE nome = ?;";
        pontos = pontos + 10;
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlalternativacerta);
            ps.setInt(1, pontos);
            ps.setString(2, nome);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public int isRight(String alternativa, int idQuestao) {
        SQLConnection con = new SQLConnection();
        int isRight = 0;
        String selectIsRight = "SELECT correta from alternativa where alternativa = ? and idQuestao = ?";
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(selectIsRight);
            ps.setString(1, alternativa);
            ps.setInt(2, idQuestao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isRight = rs.getInt("correta");
                System.out.println(isRight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isRight);
        return isRight;
    }

    public String rank() throws Exception {
        String aux = "";
        String sqlusuario = "SELECT nome, pontos, RANK() OVER (ORDER BY pontos desc ) AS 'Rank' FROM usuario;";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String alternativa = rs.getString(1);
                aux = String.format(aux + alternativa + "\n");
            } else {
                String alternativa = "a";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;

    }

    public String gabarito(int idQuestao) {
        String aux = "";
        String sqlusuario = "SELECT gabarito from exercicio where id_questao = ?;";
        SQLConnection con = new SQLConnection();
        try ( Connection c2 = con.obtemConexao()) {
            PreparedStatement ps = c2.prepareStatement(sqlusuario);
            ps.setInt(1, idQuestao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String gabarito = rs.getString(1);
                aux = String.format(aux + gabarito);
            } else {
                String gabarito = "a";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return aux;

    }

    public Usuario[] obterUsuarios() throws Exception {
        String sql = "SELECT * FROM usuario";
        SQLConnection con = new SQLConnection();
        try ( Connection conn = con.obtemConexao();  
                PreparedStatement ps = conn.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);  
                ResultSet rs = ps.executeQuery()) {

            int totalDeUsuarios = rs.last() ? rs.getRow() : 0;
           Usuario[] usuarios = new Usuario[totalDeUsuarios];
            rs.beforeFirst();
            int contador = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                usuarios[contador++] = new Usuario(id, nome, senha);
            }
            return usuarios;
        }
    }

}
