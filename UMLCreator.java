package org.example;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser; /* Opcional */
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Shape;
import java.awt.geom.Ellipse2D; /* Buscar librerias con mas formas */
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color; /* Opcional */
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Cursor;
import java.util.Random;

public class UMLCreator extends JFrame implements ActionListener{
    JMenu Archivo, Dibujar, Ayuda;
    JMenuItem Acerca, Salir, Nuevo, Guardar, Abrir, Color;
    JRadioButtonMenuItem Linea, Rectangulo, Elipse; /* Renombrar segun corresponda */
    JCheckBoxMenuItem Relleno; /*Opcional*/
    JColorChooser colorChooser = new JColorChooser();
    ButtonGroup btn;
    MiPanel miPanel;

    public UMLCreator(){
        crearmenu();
        addListeners();
        miPanel = new MiPanel();
        this.add( miPanel );
        this.setSize(800,600);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("UML-Creator: ");
    }
    private void addListeners(){
        Acerca.addActionListener(this);
        Salir.addActionListener(this);
        Nuevo.addActionListener(this);
        Guardar.addActionListener(this);
        Abrir.addActionListener(this);
        Color.addActionListener(this); /* Opcional */
        Linea.addActionListener(this);
        Rectangulo.addActionListener(this);
        Elipse.addActionListener(this);
        Relleno.addActionListener(this); /* Opcional */
    }
    public void crearmenu() {     /* Cambiar nombres egun corresponda */
        JMenuBar menu = new JMenuBar();
        Archivo = new JMenu("Archivo");
        Nuevo = new JMenuItem("Nuevo");
        Abrir = new JMenuItem("Abrir archivo existente");
        Guardar = new JMenuItem("Guardar como: ");
        Salir = new JMenuItem("Salir");
        Archivo.add(Nuevo);
        Archivo.add(Abrir);
        Archivo.add(Guardar);
        Archivo.add(Salir);
        Archivo.add(Archivo);
        Dibujar = new JMenu("Dibujar");
        btn = new ButtonGroup();
        Linea = new JRadioButtonMenuItem("Linea");
        Rectangulo = new JRadioButtonMenuItem("Rectangulo");
        Elipse = new JRadioButtonMenuItem("Elipse");
        btn.add(Elipse);
        btn.add(Rectangulo);
        btn.add(Linea);
        btn.setSelected(Linea.getModel(), true);
        Relleno = new JCheckBoxMenuItem("Relleno");
        Color = new JMenuItem("Color");
        Dibujar.add(Linea);
        Dibujar.add(Rectangulo);
        Dibujar.add(Elipse);
        Dibujar.add(Relleno);
        Dibujar.add(Color);
        menu.add(Dibujar);
        Ayuda = new JMenu("Más");
        Acerca = new JMenuItem("Hecho por TheProgramer85");
        Ayuda.add(Acerca);
        menu.add(Ayuda);
        this.setJMenuBar(menu);
    }
    public static void main ( String[] args ){
        UML miVentana = new UML();
        miVentana.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Nuevo){
            miPanel.resetAll();
        }
        if(e.getSource() == Abrir){
            miPanel.Abrir();
        }
        if(e.getSource() == Guardar){
            miPanel.Guardar();
        }
        if(e.getSource() == Salir){
            System.exit(0);
        }
        if(e.getSource() == Linea){
            miPanel.Linea = true;
            miPanel.Rectangulo = false;
        }
         if(e.getSource() == Rectangulo){
             miPanel.Linea = false;
             miPanel.Rectangulo = true;
         }
         if(e.getSource() == Elipse){
             miPanel.Linea = false;
             miPanel.Rectangulo = false;
         }
         if(e.getSource() == Relleno){
             if(miPanel.Relleno){
                 miPanel.Relleno = false;
             }else{
                 miPanel.Relleno = true;
             }
         }
         if(e.getSource() == Color){
             Color color = JColorChooser.showDialog(this, "Elija un color", this.miPanel.getColorActual());
             this.miPanel.setColorActual(color);
         }
         if(e.getSource() == Acerca){
             JOptionPane.showMessageDialog(null,"Diego Ignacio Villablanca Gonzalez");
         }

    }
}
