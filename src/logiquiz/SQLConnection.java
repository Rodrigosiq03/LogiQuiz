package logiquiz;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {

    private String usuario = "root";
    private String senha = "sardinha";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "db_pessoa";

    public Connection obtemConexao() {
        Connection c2 = null;
        try {
            c2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz?useTimezone=true&serverTimezone=UTC&user=root&password=sardinha");
            System.out.println("Conexão estabelecida");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro");
        }
        return c2;
    }
    
}
