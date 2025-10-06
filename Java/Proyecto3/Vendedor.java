import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class Vendedor extends Persona implements ActionListener
{
    private String codigo, nombre, apellido, departamento, cargo, sql; 
    private double ventaAnual, ventaMensual;

    JFrame ventana;
    JTextField txt_codigo,txt_nombre,txt_apellido, txt_departamento, txt_cargo, txt_ventaMensual, txt_ventaAnual;
    JLabel lbl_codigo, lbl_nombre, lbl_apellido, lbl_departamento, lbl_cargo, lbl_ventaMensual, lbl_ventaAnual; 
    JButton btn_buscar, btn_limpiar,btn_agregar,btn_modificar,btn_eliminar, btn_listar;
    JTable tabla;
    JScrollPane jsp_tabla;
    DefaultTableModel dtm_vendedor;

    public Vendedor(JFrame padre)
    {
        ventana = padre;
        ventana.getContentPane().removeAll();
        ventana.repaint();
        ventana.setLayout(null);
        
        crearComponentes();
        ventana.revalidate();
        ventana.repaint();
    }

    private void crearComponentes()
    {
        lbl_codigo = new JLabel("Codigo");
        lbl_codigo.setBounds(20,20,80,25);
        ventana.add(lbl_codigo);

        txt_codigo = new JTextField();
        txt_codigo.setBounds(140,20,150,25);
        ventana.add(txt_codigo);

        lbl_nombre = new JLabel("Nombre");
        lbl_nombre.setBounds(20,55,80,25);
        ventana.add(lbl_nombre);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(140,55,200,25);
        ventana.add(txt_nombre);

        lbl_apellido = new JLabel("Apellido");
        lbl_apellido.setBounds(20,90,80,25);
        ventana.add(lbl_apellido);

        txt_apellido = new JTextField();
        txt_apellido.setBounds(140,90,200,25);
        ventana.add(txt_apellido);

        lbl_departamento = new JLabel("Departamento");
        lbl_departamento.setBounds(20,125,100,25);
        ventana.add(lbl_departamento);

       txt_departamento = new JTextField();
       txt_departamento.setBounds(140,125,150,25);
       ventana.add(txt_departamento);

        lbl_cargo = new JLabel("Cargo");
        lbl_cargo.setBounds(20,160,80,25);
        ventana.add(lbl_cargo);

       txt_cargo = new JTextField();
       txt_cargo.setBounds(140,160,200,25);
       ventana.add(txt_cargo);

        lbl_ventaMensual = new JLabel("Venta Mensual");
        lbl_ventaMensual.setBounds(20,195,120,25);
        ventana.add(lbl_ventaMensual);
        
        txt_ventaMensual = new JTextField();
        txt_ventaMensual.setBounds(140,195,100,25);
        ventana.add(txt_ventaMensual);

        lbl_ventaAnual = new JLabel("Venta Anual");
        lbl_ventaAnual.setBounds(20,230,100,25);
        ventana.add(lbl_ventaAnual);
        
        txt_ventaAnual = new JTextField();
        txt_ventaAnual.setBounds(140,230,100,25);
        ventana.add(txt_ventaAnual);

        //btns
        btn_buscar = new JButton("Buscar");
        btn_buscar.setBounds(320,20,80,25);
        btn_buscar.addActionListener(this);
        ventana.add(btn_buscar);

        btn_limpiar = new JButton("Limpiar");
        btn_limpiar.setBounds(410,20,80,25);
        btn_limpiar.addActionListener(this);
        ventana.add(btn_limpiar);

        btn_agregar = new JButton("Agregar");
        btn_agregar.setBounds(500,20,80,25);
        btn_agregar.addActionListener(this);
        ventana.add(btn_agregar);

        btn_modificar = new JButton("Modificar");
        btn_modificar.setBounds(590,20,90,25);
        btn_modificar.addActionListener(this);
        ventana.add(btn_modificar);

        btn_eliminar = new JButton("Eliminar");
        btn_eliminar.setBounds(500,55,80,25);
        btn_eliminar.addActionListener(this);
        ventana.add(btn_eliminar);

        btn_listar = new JButton("Listar");
        btn_listar.setBounds(590,55,90,25);
        btn_listar.addActionListener(this);
        ventana.add(btn_listar);

        //Table
        dtm_vendedor = new DefaultTableModel();
        tabla = new JTable(dtm_vendedor);
        jsp_tabla = new JScrollPane(tabla);
        jsp_tabla.setBounds(20,270,740,180);
        ventana.add(jsp_tabla);

        limpiarCampos();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();

        if (src == btn_buscar)
        {
            buscar(txt_codigo.getText());
            if(!nombre.equals(""))
            {
                txt_nombre.setText(nombre);
                txt_apellido.setText(apellido);
                txt_departamento.setText(departamento);
                txt_cargo.setText(cargo);
                txt_ventaMensual.setText(String.valueOf(ventaMensual));
                txt_ventaAnual.setText(String.valueOf(ventaAnual));

                txt_codigo.setEnabled(false);
                btn_buscar.setEnabled(false);
                btn_agregar.setEnabled(false);
                btn_modificar.setEnabled(true);
                btn_eliminar.setEnabled(true);
                
            }else{
                //no se encuentra vendedor
                txt_nombre.setText("");
                txt_apellido.setText("");
                txt_cargo.setText("");
                txt_departamento.setText("");
                txt_ventaMensual.setText("0");
                txt_ventaAnual.setText("0");

                txt_codigo.setEnabled(false);
                btn_buscar.setEnabled(false);
                btn_agregar.setEnabled(true);
                btn_modificar.setEnabled(false);
                btn_eliminar.setEnabled(false);
            }
        }
        if(src == btn_limpiar)
        {
            limpiarCampos();
        } 
        if ( src == btn_agregar)
        {
            if(validarCampos())
            {
                asignarValores();
                agregar();
                JOptionPane.showMessageDialog(ventana,"Vendedor Agregado");
		cargarTabla(dtm_vendedor);
                limpiarCampos();
            }
        } 
        if(src == btn_modificar)
        {
            if (validarCampos())
            {
                asignarValores();
                modificar();
                JOptionPane.showMessageDialog(ventana, "Vendedor Modificado");
		cargarTabla(dtm_vendedor);
                limpiarCampos();
            }
        } 
        if (src == btn_eliminar)
        {
            int respuesta = JOptionPane.showConfirmDialog(ventana, "Desea eliminar el Vendedor", "Confirmar",JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION)
            {
                eliminar(txt_codigo.getText());
                JOptionPane.showMessageDialog(ventana,"Vendedor eliminado");
		cargarTabla(dtm_vendedor);
                limpiarCampos();
            }
        } 
       if (src == btn_listar)
        {
            cargarTabla(dtm_vendedor);
        }
    }

    private void limpiarCampos()
    {
        txt_nombre.setText("");
        txt_codigo.setText("");
        txt_cargo.setText("");
        txt_departamento.setText("");
        txt_apellido.setText("");
        txt_ventaMensual.setText("0");
        txt_ventaAnual.setText("0");

        txt_codigo.setEnabled(true);
        btn_buscar.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_modificar.setEnabled(false);
        btn_eliminar.setEnabled(false);
	btn_listar.setEnabled(true);
    }

    private boolean validarCampos()
    {
        if(txt_codigo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(ventana, "Ingrese el codigo");
            return false;
        }
        if(txt_nombre.getText().equals(""))
        {
            JOptionPane.showMessageDialog(ventana, "Ingrese el nombre");
            return false;
        }
        if(txt_departamento.getText().equals(""))
        {
            JOptionPane.showMessageDialog(ventana, "Ingrese el departamento");
            return false;
        }   
        return true;
    }
    private void asignarValores()
    {
        codigo = txt_codigo.getText();
        nombre = txt_nombre.getText();
        apellido = txt_apellido.getText();
        departamento = txt_departamento.getText();
        cargo = txt_cargo.getText();
        try{
            ventaMensual = Double.parseDouble(txt_ventaMensual.getText());
        
        }catch(NumberFormatException e){
            ventaMensual = 0;
        }
         try{
            ventaAnual = Double.parseDouble(txt_ventaAnual.getText());
        
        }catch(NumberFormatException e){
            ventaAnual = 0;
        }
        
    }
    private void setCodigo(String cod)
    {
        codigo = cod;
    }
    public String getCodigo()
    {
        return codigo;
    }
    private void setDepartamento(String d)
    {
        departamento = d;
    }
    public String getDepartamento()
    {
        return departamento;
    }
    public void setCargo(String c)
    {
        cargo = c;
    }
    public String getCargo()
    {
        return cargo;
    }
    public void setVentaMensual(double vm)
    {
        ventaMensual = vm;
    }
    public double getVentaMensual()
    {
        return ventaMensual;
    }
    public void setVentaAnual(double va)
    {
        ventaAnual = va;
    }
    public double getVentaAnual()
    {
        return ventaAnual;
    }

    //Base de datos
	public void buscar (String cod)
	{
		DB db = new DB();
		try
		{
			sql = "select * from vendedor where codigo = '" + cod + "'";
            ResultSet rs = db.executeQuery(sql);
            if (rs.next())
            {
                codigo = rs.getString("codigo");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                departamento = rs.getString("departamento");
                cargo = rs.getString("cargo");
                ventaMensual = rs.getDouble("venta_mensual");
                ventaAnual = rs.getDouble("venta_anual");
            }else{
                nombre = "";
                apellido = "";
                departamento = "";
                cargo = "";
                ventaMensual = 0;
                ventaAnual = 0;
            }    		
            db.cerrar();
        }catch(Exception e){
            System.out.println("error " + e.toString());
            db.cerrar();;
        }
    }
    public void agregar() 
    {
    DB db = new DB();
    try {
        sql = "insert into vendedor(codigo, nombre, apellido, departamento, cargo, venta_mensual, venta_anual) values('" +
              codigo + "','" + nombre + "','" + apellido + "','" + departamento + "','" + 
              cargo + "'," + ventaMensual + "," + ventaAnual + ")";
        db.executeUpdate(sql);
        db.cerrar();
    }catch(Exception e){
        System.out.println("error"+ e.toString());
        db.cerrar();
        }
    }

    public void modificar()
    {
        DB db = new DB();
        try{
            sql = "update vendedor set nombre = '" + nombre + "', apellido = '" + apellido +
              "', departamento = '" + departamento + "', cargo = '" + cargo + 
              "', venta_mensual = " + ventaMensual + ", venta_anual = " + ventaAnual + 
              " where codigo = '" + codigo + "'";
            db.executeUpdate(sql);
            db.cerrar();
        }catch (Exception e){
            System.out.println("error "+ e.toString());
            db.cerrar();
        }
    }

    public void eliminar(String cod)
    {
        DB db = new DB();
        try{
            sql = "delete from vendedor where codigo = '" + cod + "'";
            db.executeUpdate(sql);
            db.cerrar();
        }catch (Exception e ){
            System.out.println("Error" + e.toString());
            db.cerrar();
        }
    }
    public void cargarTabla(DefaultTableModel dtm_vendedor)
    {
        DB db = new DB();
        dtm_vendedor.setRowCount(0);
        dtm_vendedor.setColumnCount(0);

        dtm_vendedor.addColumn("Código");
        dtm_vendedor.addColumn("Nombre");
        dtm_vendedor.addColumn("Apellido");
        dtm_vendedor.addColumn("Departamento");
        dtm_vendedor.addColumn("Cargo");
        dtm_vendedor.addColumn("Venta Mensual");
        dtm_vendedor.addColumn("Venta Anual");
        
        Object[] fila = new Object[7];

        try{
            sql = "select codigo, nombre, apellido, departamento, cargo, venta_mensual, venta_anual from vendedor order by apellido";
            ResultSet rs = db.executeQuery(sql);

            while (rs.next())
            {
                fila[0] = rs.getString("codigo");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("departamento");
                fila[4] = rs.getString("cargo");
                fila[5] = rs.getDouble("venta_mensual");
                fila[6] = rs.getDouble("venta_anual");
                dtm_vendedor.addRow(fila);
            }
            db.cerrar();
        }catch (Exception e){
            System.out.println("error" + e.toString());
            db.cerrar();
        }
    }
            
}