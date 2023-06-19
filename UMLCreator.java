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
import java.awt.geom.Point2D;
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
        UMLCreator miVentana = new UMLCreator();
        miVentana.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Nuevo){
            miPanel.abrir(); /*Ojo aqui*/
        }
        if(e.getSource() == Abrir){
            miPanel.abrir();
        }
        if(e.getSource() == Guardar){
            miPanel.guardar();
        }
        if(e.getSource() == Salir){
            System.exit(0);
        }
        if(e.getSource() == Linea){
            miPanel.linea = true;
            miPanel.rectangulo = false;
        }
         if(e.getSource() == Rectangulo){
             miPanel.linea = false;
             miPanel.rectangulo = true;
         }
         if(e.getSource() == Elipse){
             miPanel.linea = false;
             miPanel.rectangulo = false;
         }
         if(e.getSource() == Relleno){
             if(miPanel.relleno){
                 miPanel.relleno = false;
             }else{
                 miPanel.relleno = true;
             }
         }
         if(e.getSource() == Color){ /*Ojo aca*/
             Color color = JColorChooser.showDialog(this, "Elija un color", this.miPanel.getColorActual());
             this.miPanel.getColorActual(Color);
         }
         if(e.getSource() == Acerca){
             JOptionPane.showMessageDialog(null,"Diego Ignacio Villablanca Gonzalez");
         }
    }

    public Graphics2D crearGraphics2D() {
        Graphics2D g2 = null;
        if (myImage == null || myImage.getWidth() != getSize().width ||
                myImage.getHeight() != getSize().height) {
            myImage = (BufferedImage) createImage(getSize().width, getSize().height);
        }

        if (myImage != null) {
            g2 = myImage.createGraphics();
            g2.setColor(coloractual);
            g2.setBackground(getBackground());
        }
        g2.clearRect(0, 0, getSize().width, getSize().height);
        return g2;
    }
    public void paintComponents(Graphics g){ /* O paintComponent en 160 y 161*/
        super.paintComponents(g);
        if(myImage == null){
            g2D = crearGraphics2D();
        }
        if(figura != null){
            if(relleno){
                g2D.setColor(coloractual);
                g2D.draw(figura);
                g2D.fill(figura);
            }else{
                g2D.setColor(coloractual);
                g2D.draw(figura);
            }
            if (myImage != null && isShowing()){
                g.drawImage(myImage, 0, 0, this);
            }
            figura = null;
        }
    }
    public void resetAll(){
        myImage = null;

        repaint();
    }
}

class MiPanel extends JPanel{
    Point p1;
    Point p2;
    Shape figura;
    Random R = new Random();
    public Color coloractual = Color.WHITE;
    BufferedImage myImage;
    Graphics2D g2D;
    boolean rectangulo = false; /*Cambiar nombres segun corresponda*/
    boolean linea = true;
    boolean relleno = false;

    public Shape crearFigura(Point p1, Point p2){
        double xInicio = Math.min(p1.getX(), p2.getX());
        double yInicio = Math.min(p1.getY(), p2.getY());
        double ancho = Math.abs(p2.getX() - p1.getX());
        double altura = Math.abs(p2.getY() - p1.getY());
        Shape nuevaFigura = new Rectangulo2D.Double(xInicio, yInicio, ancho, altura);
        return nuevaFigura;
    }
    public Shape crearLinea(Point p1, Point p2){
        Shape nuevaFigura = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        return nuevaFigura;
    }
    public Shape crearElpise(Point p1, Point p2){
        double xInicio = Math.min(p1.getX(), p2.getX());
        double yInicio = Math.min(p1.getY(), p2.getY());
        double ancho = Math.abs(p2.getX() - p1.getX());
        double altura = Math.abs(p2.getY() - p1.getY());
        Shape nuevaFigura = new Ellipse2D.Double(xInicio, yInicio, ancho, altura);
        return nuevaFigura;
    }

    public MiPanel(){
        OyenteDeRaton = MiOyente = new OyenteDeRaton();
        OyenteDeMovimiento = MiOyente2 = new OyenteDeMovimiento();
        addMouseListener( MiOyente );
        addMouseMotionListener( MiOyente2 );
    }
    class OyenteDeRaton extends MouseAdapter{
        public void mousePressed(MouseEvent evento){
            MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            p1 = evento.getPoint();
        }
        public void mouseReleased(MouseEvent evento){
            MiPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if(rectangulo){
                p2 = evento.getPoint();
                figura = crearLinea(p1, p2);
                repaint();
            }else{
                if(linea){
                    p2 = evento.getPoint();
                    figura = crearLinea(p1, p2);
                    repaint();
                }else{
                    p2 = evento.getPoint();
                    figura = crearElpise(p1, p2);
                    repaint();
                }
            }
        }
    }
    class OyenteDeMovimiento extends MouseMotionAdapter{
        public void mouseDragged(MouseEvent evento){
            Graphics2D g2D;
            if(rectangulo){
                if(figura != null){
                    g2D = (Graphics2D) MiPanel.this.getGraphics();
                    g2D.setXORMode(MiPanel.this.getBackground());
                    g2D.setColor(coloractual);
                    g2D.draw(figura);
                }
                p2 = evento.getPoint();
                figura = crearFigura(p1, p2);
                g2D = (Graphics2D) MiPanel.this.getGraphics();
                g2D.setXORMode(MiPanel.this.getBackground());
                g2D.setColor(coloractual);
                g2D.draw(figura);
            }else if(linea){
                if(figura != null){
                    g2D = (Graphics2D) MiPanel.this.getGraphics();
                    g2D.setXORMode(MiPanel.this.getBackground());
                    g2D.setColor(coloractual);
                    g2D.draw(figura);
                }
                p2 = evento.getPoint();
                figura = crearLinea(p1, p2);
                g2D = (Graphics2D) MiPanel.this.getGraphics();
                g2D.setXORMode(MiPanel.this.getBackground());
                g2D.setColor(coloractual);
                g2D.draw(figura);
            }else{
                if(figura != null){
                    g2D = (Graphics2D) MiPanel.this.getGraphics();
                    g2D.setXORMode(MiPanel.this.getBackground());
                    g2D.setColor(coloractual);
                    g2D.draw(figura);
                }
                p2 = evento.getPoint();
                figura = crearElpise(p1, p2);
                g2D = (Graphics2D) MiPanel.this.getGraphics();
                g2D.setXORMode(MiPanel.this.getBackground());
                g2D.setColor(coloractual);
                g2D.draw(figura);
            }
        }
    }
    public void abrir(){
        try{
            JFileChooser jfc = createJFileChooser();
            jfc.showOpenDialog(this);
            File file = jfc.getSelectedFile();
            if (file == null){
                return;
            }
            myImage = javax.imageio.ImageIO.read(file);
            int w = myImage.getWidth(null);
            int h = myImage.getHeight(null);
            if(myImage.getType() != BufferedImage.TYPE_INT_RGB){
                BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(myImage, 0 , 0, null);
            }
            g2D = (Graphics2D) myImage.getGraphics();
            repaint();
        }catch (IOException e){
            System.exit(1);
        }
    }
    public void guardar(){
        try {
            JFileChooser jfc = createJFileChooser();
            jfc.showSaveDialog(this);
            File file = jfc.getSelectedFile();
            if(file == null){
                return;
            }
            javax.swing.filechooser.FileFilter ff = jfc.getFileFilter();
            String fileName = file.getName();
            String extension = "jpg";
            if(ff instanceof MyFileFilter){
                extension =((MyFileFilter)ff).getExtension();
            }
            fileName = fileName + "." + extension;
            file = new File(file.getParent(), fileName);
            javax.imageio.ImageIO.write(myImage, extension, file);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public JFileChooser createJFileChooser(){
        JFileChooser jfc = new JFileChooser();
        jfc.setAcceptAllFileFilterUsed(false);
        String [] fileTypes = getFormats();
        for(int i=0; i<fileTypes.length; i++){
            jfc.addChoosableFileFilter(new MyFileFilter(fileTypes[i], fileTypes[i] + "file"));
        }
        return jfc;
    }
    public String[] getFormats(){
        String[] formats = javax.imageio.ImageIO.getWriterFormatNames();
        java.util.TreeSet<String> formatSet = new java.util.TreeSet<String>();
        for(String s: formats){
            formatSet.add(s.toLowerCase());
        }
        return formatSet.toArray(new String[0]);
    }
    class MyFileFilter extends javax.swing.filechooser.FileFilter{
        private String extension;
        private String description;
        public MyFileFilter(String extension, String description){
            this.extension = extension;
            this.description = description;
        }
        public boolean accept (File f){
            return f.getName().toLowerCase().endsWith("." + extension) || f.isDirectory();
        }
        public String getDescription(){
            return description;
        }
        public String getExtension(){
            return extension;
        }
    }
}




