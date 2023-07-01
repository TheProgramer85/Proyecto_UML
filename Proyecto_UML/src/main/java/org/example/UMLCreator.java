package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class UMLCreator extends JFrame implements ActionListener {
    JMenu archivo, dibujar, ayuda;
    JMenuItem acerca, salir, nuevo, guardar, abrir, color;
    JRadioButtonMenuItem linea, rectangulo, elipse;
    JCheckBoxMenuItem relleno;
    JColorChooser colorChooser = new JColorChooser();
    ButtonGroup btn;
    MiPanel miPanel;

    public UMLCreator() {
        crearmenu();
        addListeners();
        miPanel = new MiPanel();
        this.add(miPanel);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("UML-Creator");
    }

    private void addListeners() {
        acerca.addActionListener(this);
        salir.addActionListener(this);
        nuevo.addActionListener(this);
        guardar.addActionListener(this);
        abrir.addActionListener(this);
        color.addActionListener(this);
        linea.addActionListener(this);
        rectangulo.addActionListener(this);
        elipse.addActionListener(this);
        relleno.addActionListener(this);
    }

    public void crearmenu() {
        JMenuBar menu = new JMenuBar();
        archivo = new JMenu("Archivo");
        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir archivo existente");
        guardar = new JMenuItem("Guardar como");
        salir = new JMenuItem("Salir");
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(salir);
        menu.add(archivo);
        dibujar = new JMenu("Dibujar");
        btn = new ButtonGroup();
        linea = new JRadioButtonMenuItem("Linea");
        rectangulo = new JRadioButtonMenuItem("Rectangulo");
        elipse = new JRadioButtonMenuItem("Elipse");
        btn.add(elipse);
        btn.add(rectangulo);
        btn.add(linea);
        btn.setSelected(linea.getModel(), true);
        relleno = new JCheckBoxMenuItem("Relleno");
        color = new JMenuItem("Color");
        dibujar.add(linea);
        dibujar.add(rectangulo);
        dibujar.add(elipse);
        dibujar.add(relleno);
        dibujar.add(color);
        menu.add(dibujar);
        ayuda = new JMenu("Más");
        acerca = new JMenuItem("Hecho por TheProgramer85");
        ayuda.add(acerca);
        menu.add(ayuda);
        this.setJMenuBar(menu);
    }

    public static void main(String[] args) {
        UMLCreator miVentana = new UMLCreator();
        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuevo) {
            miPanel.abrir();
        }
        if (e.getSource() == abrir) {
            miPanel.abrir();
        }
        if (e.getSource() == guardar) {
            miPanel.guardar();
        }
        if (e.getSource() == salir) {
            System.exit(0);
        }
        if (e.getSource() == acerca) {
            JOptionPane.showMessageDialog(null, "Este programa ha sido creado por TheProgramer85");
        }
        if (e.getSource() == linea) {
            miPanel.setHerramienta("linea");
        }
        if (e.getSource() == rectangulo) {
            miPanel.setHerramienta("rectangulo");
        }
        if (e.getSource() == elipse) {
            miPanel.setHerramienta("elipse");
        }
        if (e.getSource() == relleno) {
            miPanel.setRelleno(relleno.getState());
        }
        if (e.getSource() == color) {
            miPanel.setColor(colorChooser.showDialog(this, "Seleccione un color", Color.BLACK));
        }
    }

    class MiPanel extends JPanel {
        BufferedImage buffer;
        Graphics2D g2D;
        int currentX, currentY, oldX, oldY;
        boolean relleno = false;
        String herramienta = "linea";
        Color color = Color.BLACK;

        public MiPanel() {
            this.setBackground(Color.WHITE);
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    oldX = e.getX();
                    oldY = e.getY();
                }

                public void mouseReleased(MouseEvent e) {
                    if (herramienta.equals("linea")) {
                        g2D.draw(new Line2D.Float(oldX, oldY, e.getX(), e.getY()));
                    } else if (herramienta.equals("rectangulo")) {
                        int width = Math.abs(e.getX() - oldX);
                        int height = Math.abs(e.getY() - oldY);
                        int x = Math.min(oldX, e.getX());
                        int y = Math.min(oldY, e.getY());
                        if (relleno) {
                            g2D.fill(new Rectangle2D.Float(x, y, width, height));
                        } else {
                            g2D.draw(new Rectangle2D.Float(x, y, width, height));
                        }
                    } else if (herramienta.equals("elipse")) {
                        int width = Math.abs(e.getX() - oldX);
                        int height = Math.abs(e.getY() - oldY);
                        int x = Math.min(oldX, e.getX());
                        int y = Math.min(oldY, e.getY());
                        if (relleno) {
                            g2D.fill(new Ellipse2D.Float(x, y, width, height));
                        } else {
                            g2D.draw(new Ellipse2D.Float(x, y, width, height));
                        }
                    }
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    currentX = e.getX();
                    currentY = e.getY();
                    if (herramienta.equals("linea")) {
                        g2D.drawLine(oldX, oldY, currentX, currentY);
                        repaint();
                        oldX = currentX;
                        oldY = currentY;
                    }
                }
            });
        }

        public void setRelleno(boolean relleno) {
            this.relleno = relleno;
        }

        public void setHerramienta(String herramienta) {
            this.herramienta = herramienta;
        }

        public void setColor(Color color) {
            this.color = color;
            g2D.setColor(color);
        }

        public void abrir() {
            JFileChooser fc = new JFileChooser();
            int seleccion = fc.showOpenDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fc.getSelectedFile();
                try {
                    buffer = ImageIO.read(archivo);
                    g2D = buffer.createGraphics();
                    this.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void guardar() {
            JFileChooser fc = new JFileChooser();
            int seleccion = fc.showSaveDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fc.getSelectedFile();
                try {
                    ImageIO.write(buffer, "png", archivo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (buffer == null) {
                buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
                g2D = buffer.createGraphics();
                g2D.setColor(color);
            }
            g.drawImage(buffer, 0, 0, null);
        }
    }
}