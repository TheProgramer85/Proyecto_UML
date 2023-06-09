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

}
