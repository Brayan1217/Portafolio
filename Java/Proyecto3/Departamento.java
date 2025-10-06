import java.sql.*;

public class Departamento {
    private String codigo, descripcion, sql;
    
    public void setCodigo(String cod) {
        codigo = cod;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setDescripcion(String desc) {
        descripcion = desc;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String buscarCodigo(String desc) {
        DB db = new DB();
        String codigoEncontrado = "";
        try {
            sql = "select codigo from departamento where descripcion = '" + desc + "'";
            ResultSet rs = db.executeQuery(sql);
            if (rs.next()) {
                codigoEncontrado = rs.getString("codigo");
            }
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error buscando código departamento: " + e.toString());
            db.cerrar();
        }
        return codigoEncontrado;
    }
    
    public String buscarDescripcion(String cod) {
        DB db = new DB();
        String descripcionEncontrada = "";
        try {
            sql = "select descripcion from departamento where codigo = '" + cod + "'";
            ResultSet rs = db.executeQuery(sql);
            if (rs.next()) {
                descripcionEncontrada = rs.getString("descripcion");
            }
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error buscando descripción departamento: " + e.toString());
            db.cerrar();
        }
        return descripcionEncontrada;
    }
}