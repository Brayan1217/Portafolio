import java.sql.*;

public class DB {
    String URL, user, pass;
    Connection con;
    Statement stmt;
    ResultSet rs;
    
    public DB() {
        URL = "jdbc:mysql://127.0.0.1/proyecto3";
        user = "root";
        pass = "root";
    }
    
    public Connection con() 
	{
        return this.con;
    	}
    

    public void abrir() {
        try {
            con = DriverManager.getConnection(URL, user, pass);
            stmt = con.createStatement();
        } catch(Exception e) {
            System.out.println("error abrir" + e.toString());
        }
    }
    
    public ResultSet executeQuery(String sql) {
        try {
            abrir();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch(Exception e) {
            System.out.println("error query" + e.toString());
            return null;
        }
    }
    
    public void executeUpdate(String sql) {
        try {
            abrir();
            stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println("error update" + e.toString());
        }
    }
    
    public void cerrar() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch(Exception e) {
            System.out.println("error cerrar" + e.toString());
        }
    }
}