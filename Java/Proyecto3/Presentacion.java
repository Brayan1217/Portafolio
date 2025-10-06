import javax.swing.*;
import java.awt.*;

public class Presentacion {
    JFrame ventanaPrincipal;
    JLabel lbl_a,lbl_b,lbl_c,lbl_d,lbl_e,lbl_f,lbl_g,lbl_i,lbl_j;

    public Presentacion(JFrame ventana) 
	{
        this.ventanaPrincipal = ventana;
        ventanaPrincipal.getContentPane().removeAll();
        ventanaPrincipal.repaint();
        ventanaPrincipal.setLayout(null);

        lbl_a = new JLabel("Universidad Tecnológica de Panamá");
        lbl_a.setBounds(30, 25, 300, 30);
        ventanaPrincipal.add(lbl_a);

        lbl_b = new JLabel("Facultad de Ingeniería en Sistemas Computacionales");
        lbl_b.setBounds(30, 55, 400, 30);
        ventanaPrincipal.add(lbl_b);

        lbl_c = new JLabel("Lic. Desarrollo y Gestión de Software");
        lbl_c.setBounds(30, 85, 300, 30);
        ventanaPrincipal.add(lbl_c);

        lbl_d = new JLabel("Desarrollo de Software 3");
        lbl_d.setBounds(30, 115, 300, 30);
        ventanaPrincipal.add(lbl_d);

        lbl_e = new JLabel("Prof. Ricardo Chan");
        lbl_e.setBounds(30, 145, 250, 30);
        ventanaPrincipal.add(lbl_e);

        lbl_f = new JLabel("Brayan Martinez");
        lbl_f.setBounds(30, 175, 300, 30);
        ventanaPrincipal.add(lbl_f);

        lbl_g = new JLabel("8-977-467");
        lbl_g.setBounds(30, 205, 200, 30);
        ventanaPrincipal.add(lbl_g);

        lbl_i = new JLabel("GS222");
        lbl_i.setBounds(30, 235, 100, 30);
        ventanaPrincipal.add(lbl_i);

        lbl_j = new JLabel("7-6-2025");
        lbl_j.setBounds(30, 265, 100, 30);
        ventanaPrincipal.add(lbl_j);

        ventanaPrincipal.revalidate();
        ventanaPrincipal.repaint();
    }
}

