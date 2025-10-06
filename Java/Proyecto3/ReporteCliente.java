import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ReporteCliente {
     JFrame ventana;
     JPanel panelContenedor;
     JRadioButton rb_apellido, rb_cedula, rb_compraAnual;
     JButton btn_generar;
     ButtonGroup bg_orden;
     JRViewer visorReporte;

    public ReporteCliente(JFrame padre) {
        this.ventana = padre;
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        ventana.getContentPane().removeAll();
        ventana.setBounds(350, 150, 900, 650);
        ventana.setLayout(new BorderLayout());
        
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        ventana.add(panelContenedor, BorderLayout.CENTER);
    }

    private void crearComponentes() {
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelControles.setBorder(BorderFactory.createTitledBorder("Ordenamiento"));
        
        bg_orden = new ButtonGroup();
        
        rb_apellido = new JRadioButton("Apellido", true);
        rb_cedula = new JRadioButton("Cédula");
        rb_compraAnual = new JRadioButton("Compara Anual");
        
        Font radioFont = new Font("Arial", Font.PLAIN, 14);
        rb_apellido.setFont(radioFont);
        rb_cedula.setFont(radioFont);
        rb_compraAnual.setFont(radioFont);
        
        bg_orden.add(rb_apellido);
        bg_orden.add(rb_cedula);
        bg_orden.add(rb_compraAnual);
        
        btn_generar = new JButton("Generar Reporte");
        btn_generar.setFont(new Font("Arial", Font.BOLD, 14));
        btn_generar.addActionListener(e -> generarReporte());
        
        panelControles.add(new JLabel("Ordenar por: "));
        panelControles.add(rb_apellido);
        panelControles.add(rb_cedula);
        panelControles.add(rb_compraAnual);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(btn_generar);
        
        JPanel panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBorder(BorderFactory.createTitledBorder(" Reporte"));
        
        panelContenedor.add(panelControles, BorderLayout.NORTH);
        panelContenedor.add(panelReporte, BorderLayout.CENTER);
        
        ventana.revalidate();
        ventana.repaint();
    }

    private void generarReporte() {
        try {
            String orden = "apellido";
            if (rb_cedula.isSelected()) orden = "cedula";
            if (rb_compraAnual.isSelected()) orden = "compra_anual";

            DB db = new DB();
            db.abrir();
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("orden", orden);

            JasperPrint reporte = JasperFillManager.fillReport(
                "reporte_c_Blank_Letter.jasper",
                parametros, 
                db.con()
            );

            mostrarReporte(reporte);
            
            db.cerrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, 
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarReporte(JasperPrint reporte) {
        JPanel panelReporte = (JPanel) panelContenedor.getComponent(1);
        panelReporte.removeAll();
        
        visorReporte = new JRViewer(reporte);
        JScrollPane scrollPane = new JScrollPane(visorReporte);
        scrollPane.setPreferredSize(new Dimension(850, 500));
        
        panelReporte.add(scrollPane, BorderLayout.CENTER);
        
        panelReporte.revalidate();
        panelReporte.repaint();
    }
}