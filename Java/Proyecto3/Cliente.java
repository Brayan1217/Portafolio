import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente extends Persona implements ActionListener {
    private String cedula, nombre, apellido, telefono, provincia, sql;
    private int compraAnual;
    
    // Variables para la interfaz
    JFrame ventana;
    JTextField txt_cedula, txt_nombre, txt_apellido, txt_telefono, txt_compra;
    JComboBox<String> cmb_provincia;
    JButton btn_buscar, btn_limpiar, btn_agregar, btn_modificar, btn_eliminar, btn_listar;
    JTable tabla;
    JScrollPane jsp_tabla;
    DefaultTableModel dtm_cliente;
    
    Provincia oprovincia = new Provincia();
    
    // recibe JFrame 
    public Cliente(JFrame padre) 
	{
        ventana = padre;
	ventana.getContentPane().removeAll();
        ventana.setBounds(350, 150, 800, 600);
        ventana.setLayout(null);
        
        crearComponentes();
        cargarProvincias();
	ventana.revalidate();
	ventana.repaint();
	}
    
    private void crearComponentes() 
	{
        // Labels
        JLabel lbl_cedula = new JLabel("Cédula:");
        lbl_cedula.setBounds(20, 20, 80, 25);
        ventana.add(lbl_cedula);
        
        JLabel lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setBounds(20, 55, 80, 25);
        ventana.add(lbl_nombre);
        
        JLabel lbl_apellido = new JLabel("Apellido:");
        lbl_apellido.setBounds(20, 90, 80, 25);
        ventana.add(lbl_apellido);
        
        JLabel lbl_telefono = new JLabel("Teléfono:");
        lbl_telefono.setBounds(20, 125, 80, 25);
        ventana.add(lbl_telefono);
        
        JLabel lbl_provincia = new JLabel("Provincia:");
        lbl_provincia.setBounds(20, 160, 80, 25);
        ventana.add(lbl_provincia);
        
        JLabel lbl_compra = new JLabel("Compra Anual:");
        lbl_compra.setBounds(20, 195, 100, 25);
        ventana.add(lbl_compra);
        
        // JtextField
        txt_cedula = new JTextField();
        txt_cedula.setBounds(120, 20, 150, 25);
        ventana.add(txt_cedula);
        
        txt_nombre = new JTextField();
        txt_nombre.setBounds(120, 55, 200, 25);
        ventana.add(txt_nombre);
        
        txt_apellido = new JTextField();
        txt_apellido.setBounds(120, 90, 200, 25);
        ventana.add(txt_apellido);
        
        txt_telefono = new JTextField();
        txt_telefono.setBounds(120, 125, 150, 25);
        ventana.add(txt_telefono);
        
        txt_compra = new JTextField();
        txt_compra.setBounds(120, 195, 100, 25);
        ventana.add(txt_compra);
        
        // ComboBox provincia
        cmb_provincia = new JComboBox<>();
        cmb_provincia.setBounds(120, 160, 200, 25);
        ventana.add(cmb_provincia);
        
        // Botones
        btn_buscar = new JButton("Buscar");
        btn_buscar.setBounds(300, 20, 80, 25);
        btn_buscar.addActionListener(this);
        ventana.add(btn_buscar);
        
        btn_limpiar = new JButton("Limpiar");
        btn_limpiar.setBounds(390, 20, 80, 25);
        btn_limpiar.addActionListener(this);
        ventana.add(btn_limpiar);
        
        btn_agregar = new JButton("Agregar");
        btn_agregar.setBounds(480, 20, 80, 25);
        btn_agregar.addActionListener(this);
        ventana.add(btn_agregar);
        
        btn_modificar = new JButton("Modificar");
        btn_modificar.setBounds(570, 20, 90, 25);
        btn_modificar.addActionListener(this);
        ventana.add(btn_modificar);
        
        btn_eliminar = new JButton("Eliminar");
        btn_eliminar.setBounds(480, 55, 80, 25);
        btn_eliminar.addActionListener(this);
        ventana.add(btn_eliminar);
        
        btn_listar = new JButton("Listar");
        btn_listar.setBounds(570, 55, 90, 25);
        btn_listar.addActionListener(this);
        ventana.add(btn_listar);
        
        // Tabla
        dtm_cliente = new DefaultTableModel();
        tabla = new JTable(dtm_cliente);
        jsp_tabla = new JScrollPane(tabla);
        jsp_tabla.setBounds(20, 240, 740, 300);
        ventana.add(jsp_tabla);
        
        // Estado inicial
        limpiarCampos();
    }
    
    private void cargarProvincias() {
        cmb_provincia.removeAllItems();
        cmb_provincia.addItem("Seleccionar");
        
        DB db = new DB();
        try {
            sql = "select * from provincia order by descripcion";
            ResultSet rs = db.executeQuery(sql);
            while (rs.next()) {
                cmb_provincia.addItem(rs.getString("descripcion"));
            }
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error cargando provincias: " + e.toString());
            db.cerrar();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        
        if (src == btn_buscar) {
            buscar(txt_cedula.getText());
            if (!nombre.equals(""))
		 {
                // Cliente encontrado
                txt_nombre.setText(nombre);
                txt_apellido.setText(apellido);
                txt_telefono.setText(telefono);
                txt_compra.setText(String.valueOf(compraAnual));
                cmb_provincia.setSelectedItem(provincia);
                
                txt_cedula.setEnabled(false);
                btn_buscar.setEnabled(false);
                btn_agregar.setEnabled(false);
                btn_modificar.setEnabled(true);
                btn_eliminar.setEnabled(true);
		
		btn_buscar.setEnabled(false);
            	} else {
                // Cliente no encontrado - preparar para agregar
                txt_nombre.setText("");
                txt_apellido.setText("");
                txt_telefono.setText("");
                txt_compra.setText("0");
                cmb_provincia.setSelectedItem("Seleccionar");
                
                txt_cedula.setEnabled(false);
                btn_buscar.setEnabled(false);
                btn_agregar.setEnabled(true);
                btn_modificar.setEnabled(false);
                btn_eliminar.setEnabled(false);
        	    }
       		}
        
        if (src == btn_limpiar) 
	{
            limpiarCampos();
        }
        
        if (src == btn_agregar)
	 {
            if (validarCampos()) 
		{
                asignarValores();
                agregar();
                JOptionPane.showMessageDialog(ventana, "Cliente agregado");
                limpiarCampos();
            	}
        }
        
        if (src == btn_modificar) 
	{
            if (validarCampos()) 
		{
                asignarValores();
                modificar();
                JOptionPane.showMessageDialog(ventana, "Cliente modificado");
                limpiarCampos();
            	}
        }
        
        if (src == btn_eliminar) 
	{
            int respuesta = JOptionPane.showConfirmDialog(ventana, 
                "Desea eliminar el cliente?", "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) 
		{
                eliminar(txt_cedula.getText());
                JOptionPane.showMessageDialog(ventana, "Cliente eliminado");
                limpiarCampos();
            	}
        }
        
        if (src == btn_listar) 
	{
            cargarTabla(dtm_cliente);
        }
    }
    
    private void limpiarCampos() 
	{
        txt_cedula.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_telefono.setText("");
        txt_compra.setText("0");
        cmb_provincia.setSelectedItem("Seleccionar");
        
        txt_cedula.setEnabled(true);
        btn_buscar.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_modificar.setEnabled(false);
        btn_eliminar.setEnabled(false);
	btn_listar.setEnabled(true);
   	}
    
    private boolean validarCampos() 
	{
        if (txt_cedula.getText().trim().equals("")) 
		{
           	 JOptionPane.showMessageDialog(ventana, "Ingrese la cédula");
            	return false;
        	}
        if (txt_nombre.getText().trim().equals("")) 
		{
           	 JOptionPane.showMessageDialog(ventana, "Ingrese el nombre");
           	 return false;
        	}
        if (txt_apellido.getText().trim().equals("")) 
		{
            	JOptionPane.showMessageDialog(ventana, "Ingrese el apellido");
           	 return false;
        	}
        if (cmb_provincia.getSelectedItem().toString().equals("Seleccionar")) 
		{
            JOptionPane.showMessageDialog(ventana, "Seleccione una provincia");
            return false;
        	}
        return true;
    	}
    
    private void asignarValores() 
	{
        cedula = txt_cedula.getText();
        nombre = txt_nombre.getText();
        apellido = txt_apellido.getText();
        telefono = txt_telefono.getText();
        provincia = cmb_provincia.getSelectedItem().toString();
        try {
            compraAnual = Integer.parseInt(txt_compra.getText());
        } catch (NumberFormatException e) {
            compraAnual = 0;
        }
    }
    
    // Getters y Setters
    public void setCedula(String ced) 
	{
        cedula = ced;
   	}
    
    public String getCedula() 
	{
        return cedula;
    	}
    
    public void setTelefono(String t) 
	{
        telefono = t;
    	}
    
    public String getTelefono() 
	{
        return telefono;
    	}
    
    public void setProvincia(String p) 
	{
        provincia = p;
    	}
    
    public String getProvincia() 
	{
        return provincia;
    	}
    
    public void setCompraAnual(int c) 
	{
        compraAnual = c;
    	}
    
    public int getCompraAnual() 
	{
        return compraAnual;
    	}
    
    // Métodos base de datos
    public void buscar(String ced) 
	{
        DB db = new DB();
        try {
            sql = "select cliente.*, provincia.descripcion from cliente, provincia where cliente.provincia = provincia.codigo and cliente.cedula = '" + ced + "'";
            ResultSet rs = db.executeQuery(sql);
            if (rs.next()) 
		{
                cedula = rs.getString("cedula");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                telefono = rs.getString("telefono");
                provincia = rs.getString("descripcion");
                compraAnual = rs.getInt("compra_anual");
            	} else {
                nombre = "";
                apellido = "";
                telefono = "";
                provincia = "Seleccionar";
                compraAnual = 0;
            }
            db.cerrar();
        } catch (Exception e) {
            System.out.println("error Cliente buscar " + e.toString());
            db.cerrar();
        }
    }
    
    public void agregar() 
	{
        DB db = new DB();
        try {
            String provinciaCodigo = oprovincia.buscarCodigo(provincia);
            sql = "insert into cliente(cedula, nombre, apellido, telefono, provincia, compra_anual) values('" +
                  cedula + "','" + nombre + "','" + apellido + "','" + telefono + "','" + 
                  provinciaCodigo + "'," + compraAnual + ")";
            db.executeUpdate(sql);
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.toString());
            db.cerrar();
        }
    }
    
    public void modificar() {
        DB db = new DB();
        try {
            String provinciaCodigo = oprovincia.buscarCodigo(provincia);
            sql = "update cliente set nombre = '" + nombre + "', apellido = '" + apellido +
                  "', telefono = '" + telefono + "', provincia = '" + provinciaCodigo + 
                  "', compra_anual = " + compraAnual + " where cedula = '" + cedula + "'";
            db.executeUpdate(sql);
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.toString());
            db.cerrar();
        }
    }
    
    public void eliminar(String ced) 
	{
        DB db = new DB();
        try {
            sql = "delete from cliente where cedula = '" + ced + "'";
            db.executeUpdate(sql);
            db.cerrar();
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.toString());
            db.cerrar();
        }
    }
    
    public void listar(DefaultListModel<String> dlm_cliente) 
	{
        DB db = new DB();
        try {
            sql = "select cliente.cedula, cliente.nombre, cliente.apellido, provincia.descripcion from cliente, provincia where cliente.provincia = provincia.codigo order by apellido";
            ResultSet rs = db.executeQuery(sql);
            dlm_cliente.clear();
            while (rs.next()) 
		{
                dlm_cliente.addElement(rs.getString("cedula") + " " + rs.getString("nombre") + 
                                    " " + rs.getString("apellido") + " " + rs.getString("descripcion"));
            	}
            db.cerrar();
        } catch (Exception e) {
            System.out.println("error listar: " + e.toString());
            db.cerrar();
        }
    }
    
    public void cargarTabla(DefaultTableModel dtm_cliente)
	 {
        DB db = new DB();
        dtm_cliente.setRowCount(0);
        dtm_cliente.setColumnCount(0);
        
        dtm_cliente.addColumn("Cedula");
        dtm_cliente.addColumn("Nombre");
        dtm_cliente.addColumn("Apellido");
        dtm_cliente.addColumn("Telefono");
        dtm_cliente.addColumn("Provincia");
        dtm_cliente.addColumn("Compra Anual");
        
        Object[] fila = new Object[6];
        
        try {
            sql = "select cliente.cedula, cliente.nombre, cliente.apellido, cliente.telefono, provincia.descripcion, cliente.compra_anual from cliente, provincia where cliente.provincia = provincia.codigo order by apellido";
            ResultSet rs = db.executeQuery(sql);
            
            while (rs.next()) {
                fila[0] = rs.getString("cedula");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getString("descripcion");
                fila[5] = rs.getInt("compra_anual");
                dtm_cliente.addRow(fila);
            }
            db.cerrar();
        } catch (Exception e) {
            System.out.println("error cargarTabla: " + e.toString());
            db.cerrar();
        }
		ventana.revalidate();
       		ventana.repaint();
    }

}