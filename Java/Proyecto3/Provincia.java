import javax.swing.*;
import java.sql.*;

public class Provincia
{
    private String codigo, descripcion, sql;

    public void setCodigo(String c)
    {
        codigo = c;
    }
    public String getCodigo()
    {
        return codigo;
    }

    public void setDescripcion(String d)
    {
        descripcion = d;
    }
    public String getDescripcion()
    {
        return descripcion;
    }

    public void cargar(JComboBox<String> jcb)
    {

        DB db = new DB();

        try
        {
            sql = "select * from provincia order by descripcion";
            ResultSet rs = db.executeQuery(sql);
            
            while (rs.next())
            {
                codigo = rs.getString("codigo");
                descripcion = rs.getString("descripcion");
                jcb.addItem(descripcion);
            }

            db.cerrar();
        }
        catch(Exception e)
        {
            System.out.println("error " + e.toString());
        }

    }

    public String buscarCodigo(String p)
    {

        DB db = new DB();

        try
        {
            //sql = "select * from cliente where cedula = '8-2'";
            //sql = "select * from cliente where cedula = '" + ced + "'";
            sql = "select * from provincia where descripcion = '" + p + "'";
            ResultSet rs = db.executeQuery(sql);
            
            if (rs.next())
            {
                codigo = rs.getString("codigo");
                descripcion = rs.getString("descripcion");
            }
            else
            {
                codigo = "";
                descripcion = "";
            }
            db.cerrar();
        }
        catch(Exception e)
        {
            System.out.println("error Cliente buscar " + e.toString());
        }

        return codigo;

    }



}