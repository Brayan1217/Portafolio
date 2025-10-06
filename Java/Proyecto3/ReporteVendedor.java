import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ReporteVendedor {
    JFrame ventana;
    JPanel panelContenedor;
    JRadioButton rb_apellido, rb_codigo, rb_departamento;
    JButton btn_generar;
    ButtonGroup bg_orden;
    JRViewer visorReporte;
    JasperPrint jasperPrint;

    public ReporteVendedor(JFrame padre) {
        this.ventana = padre;
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        ventana.getContentPane().removeAll();
        ventana.setBounds(350, 150, 950, 650);
        ventana.setLayout(new BorderLayout());
        
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        ventana.add(panelContenedor, BorderLayout.CENTER);
        ventana.setVisible(true);
    }

    private void crearComponentes() {
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelControles.setBorder(BorderFactory.createTitledBorder("Ordenamiento de Vendedores"));
        
        bg_orden = new ButtonGroup();
        
        rb_apellido = new JRadioButton("Apellido", true);
        rb_codigo = new JRadioButton("Código");
        rb_departamento = new JRadioButton("Departamento");
        
        Font radioFont = new Font("Arial", Font.PLAIN, 14);
        rb_apellido.setFont(radioFont);
        rb_codigo.setFont(radioFont);
        rb_departamento.setFont(radioFont);
        
        bg_orden.add(rb_apellido);
        bg_orden.add(rb_codigo);
        bg_orden.add(rb_departamento);
        
        btn_generar = new JButton("Generar Reporte Vendedores");
        btn_generar.setFont(new Font("Arial", Font.BOLD, 14));
        btn_generar.addActionListener(e -> generarReporte());
        
        panelControles.add(new JLabel("Ordenar por: "));
        panelControles.add(rb_apellido);
        panelControles.add(rb_codigo);
        panelControles.add(rb_departamento);
        panelControles.add(Box.createHorizontalStrut(20));
        panelControles.add(btn_generar);
        
        JPanel panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBorder(BorderFactory.createTitledBorder("Vista del Reporte de Vendedores"));
        
        panelContenedor.add(panelControles, BorderLayout.NORTH);
        panelContenedor.add(panelReporte, BorderLayout.CENTER);
        
        ventana.revalidate();
        ventana.repaint();
    }

    private void generarReporte() {
        try {
            String orden = "apellido";
            if (rb_apellido.isSelected()) orden = "apellido";
            if (rb_codigo.isSelected()) orden = "codigo";
            if (rb_departamento.isSelected()) orden = "departamento";
            
            DB db = new DB();
            db.abrir();
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("orden", orden);

            jasperPrint = JasperFillManager.fillReport(
                "reporte_vende_Blank_Letter.jasper",
                parametros, 
                db.con()
            );

            mostrarReporte(jasperPrint);
            db.cerrar();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarReporte(JasperPrint reporte) {
        JPanel panelReporte = (JPanel) panelContenedor.getComponent(1);
        panelReporte.removeAll();
        
        visorReporte = new JRViewer(reporte);
        JScrollPane scrollPane = new JScrollPane(visorReporte);
        scrollPane.setPreferredSize(new Dimension(900, 500));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        
        panelReporte.add(scrollPane, BorderLayout.CENTER);
        panelReporte.revalidate();
        panelReporte.repaint();
    }
    
    public void exportarAPDF() {
        if (jasperPrint != null) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar Reporte de Vendedores");
                fileChooser.setSelectedFile(new java.io.File("reporte_vendedores.pdf"));
                
                if (fileChooser.showSaveDialog(ventana) == JFileChooser.APPROVE_OPTION) {
                    String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                    JasperExportManager.exportReportToPdfFile(jasperPrint, rutaArchivo);
                    
                    JOptionPane.showMessageDialog(ventana, 
                        "Reporte exportado a: " + rutaArchivo,
                        "Exportación exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, 
                    "Error al exportar: " + e.getMessage(),  
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}