import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Martinez467 implements ActionListener 
{
    JFrame ventana;

    JMenuBar menuBar;
    JMenu mnu_inicio, mnu_mantenimiento, mnu_reportes;
    JMenuItem mni_presentacion, mni_salir;
    JMenuItem mni_cliente, mni_vendedor;
    JMenuItem mni_reporteCliente, mni_reporteVendedor;

    DefaultListModel<String> dlm_log;
    JList<String> lst_log;
    JScrollPane jsp_log;

    public static void main(String[] args)
	 {
        new Martinez467();
  	 }

    Martinez467() 
{
        ventana = new JFrame("PROYECTO 3");
        ventana.setBounds(300, 100, 700, 500);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Barra de menú
        menuBar = new JMenuBar();

        // Menú Inicio
        mnu_inicio = new JMenu("Inicio");
        mni_presentacion = new JMenuItem("Presentación");
        mni_presentacion.addActionListener(this);
        mni_salir = new JMenuItem("Salir");
        mni_salir.addActionListener(this);
        mnu_inicio.add(mni_presentacion);
        mnu_inicio.add(mni_salir);
        menuBar.add(mnu_inicio);

        // Menú Mantenimiento
        mnu_mantenimiento = new JMenu("Mantenimiento");
        mni_cliente = new JMenuItem("Cliente");
        mni_cliente.addActionListener(this);
        mni_vendedor = new JMenuItem("Vendedor");
        mni_vendedor.addActionListener(this);
        mnu_mantenimiento.add(mni_cliente);
        mnu_mantenimiento.add(mni_vendedor);
        menuBar.add(mnu_mantenimiento);

        // Menú Reportes
        mnu_reportes = new JMenu("Reportes");
        mni_reporteCliente = new JMenuItem("Reporte Cliente");
        mni_reporteCliente.addActionListener(this);
        mni_reporteVendedor = new JMenuItem("Reporte Vendedor");
        mni_reporteVendedor.addActionListener(this);
        mnu_reportes.add(mni_reporteCliente);
        mnu_reportes.add(mni_reporteVendedor);
        menuBar.add(mnu_reportes);

        ventana.setJMenuBar(menuBar);

        // Log con JList
        dlm_log = new DefaultListModel<>();
        lst_log = new JList<>(dlm_log);
        jsp_log = new JScrollPane(lst_log);
        jsp_log.setBounds(20, 20, 640, 200);
        ventana.add(jsp_log);

        ventana.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == mni_salir) System.exit(0);

        if (src == mni_presentacion) {
            new Presentacion(ventana);
            dlm_log.addElement("Presentación abierta");
        }

        if (src == mni_cliente) {
            new FormCliente(ventana);
            dlm_log.addElement("Módulo Cliente abierto");
        }

        if (src == mni_vendedor) {
            new FormVendedor(ventana);
            dlm_log.addElement("Módulo Vendedor abierto");
        }

        if (src == mni_reporteCliente) {
            new ReporteCliente(ventana);
            dlm_log.addElement("Reporte Cliente generado");
        }

        if (src == mni_reporteVendedor) {
            new ReporteVendedor(ventana);
            dlm_log.addElement("Reporte Vendedor generado");
        }
    }
}
