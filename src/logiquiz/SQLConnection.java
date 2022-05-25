package logiquiz;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {

    private String usuario = "root";
    private String senha = "Megan200903$";
    private String host = "localhost";
    private String porta = "3306";
    private String bd = "quiz";

    public Connection obtemConexao() {
        Connection c2 = null;
        try {
            c2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz?useTimezone=true&serverTimezone=UTC&user=root&password=Megan200903$");
            System.out.println("Conexão estabelecida");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro");
        }
        return c2;
    }
    
}
