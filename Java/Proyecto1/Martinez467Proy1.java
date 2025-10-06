import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Martinez467Proy1 implements ActionListener {

    JFrame ventana;
    JTextField tf_valor, tf_intentos, tf_x, tf_y, tf_mensaje;
    JButton btn_reiniciar, btn_iniciar, btn_ayuda, btn_boton;
    JLabel lbl_Norte, lbl_Sur, lbl_Este, lbl_Oeste, lbl_intentos, lbl_x, lbl_y, lbl_hoz, lbl_vert,lbl_a,lbl_b,lbl_c,lbl_d,lbl_e,lbl_f,lbl_g,lbl_i,lbl_j;
    Random rnd;
    int enjambrePos;

    public static void main(String[] args) 
    {
        new Martinez467Proy1();
    }

    Martinez467Proy1() {
        ventana = new JFrame("Martinez467Proy1");
        ventana.setBounds(300, 100, 1150, 850);  
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rnd = new Random();

        //Presentacion ziq
        lbl_a = new JLabel("Universidad Tecnologica de Panama");
		lbl_a.setBounds(1100,25,300,100);
		ventana.add(lbl_a);
		
		lbl_b = new JLabel("Facultad de Ingenieria en Sistemas Computacionales");
		lbl_b.setBounds(1100,40,500,100);
		ventana.add(lbl_b);
		
		lbl_c = new JLabel("Lic. Desarrollo y Gestion de Software");
		lbl_c.setBounds(1100,80,500,100);
		ventana.add(lbl_c);

		lbl_d = new JLabel("Desarrollo de Software 3");
		lbl_d.setBounds(1100,120,300,100);
		ventana.add(lbl_d);
		
		lbl_e = new JLabel("prof. Ricardo chan");
		lbl_e.setBounds(1125,140,250,100);
		ventana.add(lbl_e);
		
		lbl_f = new JLabel("Brayan Martinez");
		lbl_f.setBounds(1125,200,300,100);
		ventana.add(lbl_f);

		lbl_g = new JLabel("8-977-467");
		lbl_g.setBounds(1150,220,200,100);
		ventana.add(lbl_g);

		lbl_i = new JLabel("GS222");
		lbl_i.setBounds(1150,250,500,100);
		ventana.add(lbl_i);

		lbl_j = new JLabel("7-6-2025");
		lbl_j.setBounds(1150,280,500,100);
		ventana.add(lbl_j);

        lbl_Norte = new JLabel("Norte");
        lbl_Norte.setBounds(500, 20, 100, 20);
        lbl_Norte.setVisible(false);
        ventana.add(lbl_Norte);

        lbl_Sur = new JLabel("Sur");
        lbl_Sur.setBounds(500, 430, 100, 20);
        lbl_Sur.setVisible(false);
        ventana.add(lbl_Sur);

        lbl_Este = new JLabel("Este");
        lbl_Este.setBounds(855, 250, 100, 20);
        lbl_Este.setVisible(false);
        ventana.add(lbl_Este);

        lbl_Oeste = new JLabel("Oeste");
        lbl_Oeste.setBounds(100, 250, 100, 20);
        lbl_Oeste.setVisible(false);
        ventana.add(lbl_Oeste);

        lbl_x = new JLabel("X:");
        lbl_x.setBounds(900, 30, 20, 20);
        lbl_x.setVisible(false);
        ventana.add(lbl_x);

        tf_x = new JTextField();
        tf_x.setBounds(930, 30, 40, 20);
        tf_x.setVisible(false);
        ventana.add(tf_x);

        lbl_y = new JLabel("Y:");
        lbl_y.setBounds(980, 30, 20, 20);
        tf_y = new JTextField();
        tf_y.setBounds(1010, 30, 40, 20);
        tf_y.setVisible(false);
        ventana.add(tf_y);
        lbl_y.setVisible(false);
        ventana.add(lbl_y);

        tf_valor = new JTextField();
        tf_valor.setBounds(930, 60, 120, 20);
        tf_valor.setVisible(false);
        ventana.add(tf_valor);

        lbl_intentos = new JLabel("Intentos:");
        lbl_intentos.setBounds(930, 95, 80, 25);
        lbl_intentos.setVisible(false);
        ventana.add(lbl_intentos);

        tf_intentos = new JTextField("0");
        tf_intentos.setBounds(980, 95, 120, 25);
        tf_intentos.setVisible(false);
        ventana.add(tf_intentos);

        tf_mensaje = new JTextField();
        tf_mensaje.setBounds(200, 480, 450, 30);
        tf_mensaje.setVisible(false);
        ventana.add(tf_mensaje);

        btn_reiniciar = new JButton("Reiniciar");
        btn_reiniciar.setBounds(930, 130, 120, 25);
        btn_reiniciar.addActionListener(this);
        ventana.add(btn_reiniciar);

        btn_iniciar = new JButton("Iniciar");
        btn_iniciar.setBounds(930, 165, 120, 25);
        btn_iniciar.addActionListener(this);
        ventana.add(btn_iniciar);

        btn_ayuda = new JButton("Ayuda");
        btn_ayuda.setBounds(930, 200, 120, 25);
        btn_ayuda.addActionListener(this);
        btn_ayuda.setVisible(false);
        ventana.add(btn_ayuda);

        ventana.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_reiniciar) {
            System.out.println("en actionPerformed");
            reiniciarJuego();
        } else if (e.getSource() == btn_iniciar) {
            System.out.println("en actionPerformed");
            iniciarJuego();
        } else if (e.getSource() == btn_ayuda) {
            System.out.println("en actinPerformed");
            mostrarUbicacion();
        } else {
            manejarClicBoton((JButton) e.getSource());
            System.out.println("en actionPerformed");
        }
    }

    private void iniciarJuego() {
        btn_iniciar.setVisible(false);
        lbl_Norte.setVisible(true);
        lbl_Sur.setVisible(true);
        lbl_Este.setVisible(true);
        lbl_Oeste.setVisible(true);
        lbl_intentos.setVisible(true);
        tf_intentos.setVisible(true);
        btn_reiniciar.setVisible(true);
        btn_ayuda.setVisible(true);
        tf_mensaje.setVisible(true);

        int filas = 12;
        int columnas = 13;
        int ancho = 50;
        int alto = 30;
        int margenX = 200;
        int margenY = 50;

        for (int i = 0; i < filas * columnas; i++) {
            int col = i / filas;
            int fila = i % filas;

            int x = margenX + (columnas - 1 - col) * ancho;
            int y = margenY + (filas - 1 - fila) * alto;

            btn_boton = new JButton(String.valueOf(i));
            btn_boton.setBounds(x, y, ancho, alto);
            btn_boton.setMargin(new Insets(0, 0, 0, 0));
            btn_boton.setFont(new Font("Arial", Font.PLAIN, 12));
            btn_boton.addActionListener(this);
            ventana.add(btn_boton);
        }

        ventana.repaint();

       for (int i = 0; i < filas; i++) {
        lbl_hoz = new JLabel(String.valueOf(filas - i));
        lbl_hoz.setBounds(150, margenY + i * alto, 30, 30);
        ventana.add(lbl_hoz);
    }

    // Números debajo de la cuadrícula)
    for (int i = 0; i < columnas; i++) {
        lbl_vert = new JLabel(String.valueOf(i + 1));
        lbl_vert.setBounds(margenX + i * ancho + 10, margenY + filas * alto, 30, 30);
        ventana.add(lbl_vert);
        }

        enjambrePos = rnd.nextInt(156);
        tf_valor.setText("Enjambre en botón: " + enjambrePos);
    }

    private void reiniciarJuego() {
        System.out.println("en actionPerformed");
        tf_intentos.setText("0");
        tf_valor.setText("");
        tf_x.setText("");
        tf_y.setText("");
        tf_mensaje.setText("");
        enjambrePos = rnd.nextInt(156);
        tf_valor.setText("Enjambre en botón: " + enjambrePos);
    }

    private void mostrarUbicacion() {
        boolean visible = !lbl_x.isVisible();
        lbl_x.setVisible(visible);
        lbl_y.setVisible(visible);
        tf_x.setVisible(visible);
        tf_y.setVisible(visible);
        tf_valor.setVisible(visible);
    }

    private void manejarClicBoton(JButton btn) {
    int botonNum = Integer.parseInt(btn.getText());
    tf_valor.setText(String.valueOf(botonNum));

    int filas = 12;
    int columnas = 13;

    int botonCol = botonNum / filas;
    int botonFila = botonNum % filas;

    int x = columnas - botonCol;
    int y = filas - botonFila;

    tf_x.setText(String.valueOf(x));
    tf_y.setText(String.valueOf(y));

    int intentos = Integer.parseInt(tf_intentos.getText()) + 1;
    tf_intentos.setText(String.valueOf(intentos));

    if (botonNum == enjambrePos) {
        tf_mensaje.setText("¡Enjambre encontrado!");
        return;
    }

   int enjambreCol = enjambrePos / filas;
int enjambreFila = enjambrePos % filas;

int diffCol;
if (botonCol > enjambreCol) {
    diffCol = botonCol - enjambreCol;
} else {
    diffCol = enjambreCol - botonCol;
}

int diffFila;
if (botonFila > enjambreFila) {
    diffFila = botonFila - enjambreFila;
} else {
    diffFila = enjambreFila - botonFila;
}

if (diffCol <= 1 && diffFila <= 1) {
    tf_mensaje.setText("Enjambre encontrado");
} else {
    String direccion = "";
    if (botonFila < enjambreFila) {
        direccion = "Norte";
    } else if (botonFila > enjambreFila) {
        direccion = "Sur";
    }

    if (botonCol < enjambreCol) {
        if (direccion.length() > 0) {
            direccion += "-Este";
        } else {
            direccion = "Este";
        }
    } else if (botonCol > enjambreCol) {
        if (direccion.length() > 0) {
            direccion += "-Oeste";
        } else {
            direccion = "Oeste";
        }
    }

    tf_mensaje.setText("El enjambre está al " + direccion);
}
    }
}



