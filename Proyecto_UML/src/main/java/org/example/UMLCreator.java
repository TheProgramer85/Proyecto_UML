package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class UMLCreator extends JFrame implements ActionListener {
    JMenu archivo, dibujar, ayuda;
    JMenuItem acerca, salir, nuevo, guardar, abrir, color, agregarTexto;
    JRadioButtonMenuItem dibujo, rectangulo, elipse, monigote, estado, generalizacion, asociacion,
            composicion, agregacion, dependencia;
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
        agregarTexto.addActionListener(this);
        dibujo.addActionListener(this);
        generalizacion.addActionListener(this);
        composicion.addActionListener(this);
        asociacion.addActionListener(this);
        agregacion.addActionListener(this);
        dependencia.addActionListener(this);
        estado.addActionListener(this);
        rectangulo.addActionListener(this);
        elipse.addActionListener(this);
        monigote.addActionListener(this);
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
        dibujar = new JMenu("Elementos");
        btn = new ButtonGroup();
        dibujo = new JRadioButtonMenuItem("Dibujo");
        generalizacion = new JRadioButtonMenuItem("Generalización");
        asociacion = new JRadioButtonMenuItem("Asociación");
        composicion = new JRadioButtonMenuItem("Composición");
        dependencia = new JRadioButtonMenuItem("Dependencia");
        agregacion = new JRadioButtonMenuItem("Agregación");
        rectangulo = new JRadioButtonMenuItem("Clase");
        elipse = new JRadioButtonMenuItem("Caso de Uso");
        monigote = new JRadioButtonMenuItem("Monigote");
        estado = new JRadioButtonMenuItem("Estado");
        btn.add(estado);
        btn.add(generalizacion);
        btn.add(elipse);
        btn.add(rectangulo);
        btn.add(asociacion);
        btn.add(composicion);
        btn.add(agregacion);
        btn.add(dependencia);
        btn.add(dibujo);
        btn.add(monigote);
        btn.setSelected(dibujo.getModel(), true);
        relleno = new JCheckBoxMenuItem("Relleno");
        color = new JMenuItem("Color");
        agregarTexto = new JMenuItem("Agregar Texto");
        dibujar.add(dibujo);
        dibujar.add(generalizacion);
        dibujar.add(estado);
        dibujar.add(rectangulo);
        dibujar.add(elipse);
        dibujar.add(asociacion);
        dibujar.add(composicion);
        dibujar.add(agregacion);
        dibujar.add(dependencia);
        dibujar.add(monigote);
        dibujar.add(relleno);
        dibujar.add(color);
        dibujar.add(agregarTexto);
        menu.add(dibujar);
        ayuda = new JMenu("Más");
        acerca = new JMenuItem("Hecho por Diego Villablanca / TheProgramer85");
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
        if (e.getSource() == dibujo) {
            miPanel.setHerramienta("dibujo");
        }
        if (e.getSource() == rectangulo) {
            miPanel.setHerramienta("rectangulo");
        }
        if (e.getSource() == elipse) {
            miPanel.setHerramienta("elipse");
        }
        if (e.getSource() == monigote) {
            miPanel.setHerramienta("monigote");
        }
        if (e.getSource() == estado) {
            miPanel.setHerramienta("estado");
        }
        if (e.getSource() == asociacion) {
            miPanel.setHerramienta("asociacion");
        }
        if (e.getSource() == composicion) {
            miPanel.setHerramienta("composicion");
        }
        if (e.getSource() == generalizacion) {
            miPanel.setHerramienta("generalizacion");
        }
        if (e.getSource() == agregacion) {
            miPanel.setHerramienta("agregacion");
        }
        if (e.getSource() == dependencia) {
            miPanel.setHerramienta("dependencia");
        }
        if (e.getSource() == relleno) {
            miPanel.setRelleno(relleno.getState());
        }
        if (e.getSource() == color) {
            miPanel.setColor(colorChooser.showDialog(this, "Seleccione un color", Color.BLACK));
        }
        if (e.getSource() == agregarTexto) {
            miPanel.agregarTexto();
        }
    }

    class MiPanel extends JPanel {
        BufferedImage buffer;
        Graphics2D g2D;
        int currentX, currentY, oldX, oldY;
        boolean relleno = false;
        String herramienta = "dibujo";
        Color color = Color.BLACK;
        String texto = "";

        public MiPanel() {
            this.setBackground(Color.WHITE);
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    oldX = e.getX();
                    oldY = e.getY();
                }

                public void mouseReleased(MouseEvent e) {
                    if (herramienta.equals("dibujo")) {
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
                    } else if (herramienta.equals("monigote")) {
                        int width = 25;
                        int height = 50;
                        int x = e.getX() - width / 2;
                        int y = e.getY() - height / 2;
                        g2D.drawOval(x, y, width, height / 2);  // Cabeza
                        g2D.drawLine(x + width / 2, y + height / 2, x + width / 2, y + height);  // Cuerpo
                        g2D.drawLine(x, y + height / 2, x + width, y + height / 2);  // Brazos
                        g2D.drawLine(x + width / 2, y + height, x, y + height + 25);  // Pierna izquierda
                        g2D.drawLine(x + width / 2, y + height, x + width, y + height + 25);  // Pierna derecha
                    } else if (herramienta.equals("estado")) {
                        int width = Math.abs(e.getX() - oldX);
                        int height = Math.abs(e.getY() - oldY);
                        int x = Math.min(oldX, e.getX());
                        int y = Math.min(oldY, e.getY());
                        int arcWidth = Math.min(width, height);
                        int arcHeight = Math.min(width, height);
                        if (relleno) {
                            g2D.fill(new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight));
                        } else {
                            g2D.draw(new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight));
                        }
                    } else if (herramienta.equals("generalizacion")) {
                        Arrow arrow = new Arrow();
                        arrow.drawArrow(g2D, oldX, oldY, e.getX(), e.getY());
                    } else if (herramienta.equals("asociacion" +
                            "")) {
                        g2D.drawLine(oldX, oldY, e.getX(), e.getY());
                    }else if (herramienta.equals("composicion")) {
                        int width = Math.abs(e.getX() - oldX);
                        int height = Math.abs(e.getY() - oldY);
                        int x = Math.min(oldX, e.getX());
                        int y = Math.min(oldY, e.getY());
                        int startX = x;
                        int startY = y + height / 2;
                        int endX = x + width - height / 2;
                        int endY = startY;
                        int diamondX = endX - height / 2;
                        int diamondY = endY - height / 4;
                        int diamondWidth = height;
                        int diamondHeight = height / 2;

                        g2D.draw(new Line2D.Float(startX, startY, endX, endY));
                        g2D.setColor(Color.BLACK);
                        g2D.fillPolygon(
                                new int[]{diamondX, diamondX + diamondWidth / 2, diamondX + diamondWidth, diamondX + diamondWidth / 2},
                                new int[]{diamondY, diamondY - diamondHeight / 2, diamondY, diamondY + diamondHeight / 2},
                                4
                        );
                        g2D.setColor(color);
                    }else if (herramienta.equals("agregacion")) {
                        int width = Math.abs(e.getX() - oldX);
                        int height = Math.abs(e.getY() - oldY);
                        int x = Math.min(oldX, e.getX());
                        int y = Math.min(oldY, e.getY());
                        int startX = x;
                        int startY = y + height / 2;
                        int endX = x + width - height / 2;
                        int endY = startY;
                        int diamondX = endX - height / 2;
                        int diamondY = endY - height / 4;
                        int diamondWidth = height;
                        int diamondHeight = height / 2;

                        g2D.draw(new Line2D.Float(startX, startY, endX, endY));
                        g2D.setColor(Color.BLACK);
                        g2D.setStroke(new BasicStroke(2));
                        g2D.drawPolygon(
                                new int[]{diamondX, diamondX + diamondWidth / 2, diamondX + diamondWidth, diamondX + diamondWidth / 2},
                                new int[]{diamondY, diamondY - diamondHeight / 2, diamondY, diamondY + diamondHeight / 2},
                                4
                        );
                        g2D.setColor(color);
                    }else if (herramienta.equals("dependencia")) {
                        int startX = oldX;
                        int startY = oldY;
                        int endX = e.getX();
                        int endY = e.getY();

                        int arrowLength = 10;  
                        int arrowSpacing = 5;  

                        double angle = Math.atan2(endY - startY, endX - startX);
                        double dx = Math.cos(angle) * (arrowLength + arrowSpacing);
                        double dy = Math.sin(angle) * (arrowLength + arrowSpacing);

                        // Dibuja las secciones de la flecha
                        boolean isWhiteSection = true;
                        for (double i = 0; i < Math.sqrt((endX - startX) * (endX - startX) + (endY - startY) * (endY - startY)); i += arrowLength + arrowSpacing) {
                            int sectionStartX = startX + (int) (i * Math.cos(angle));
                            int sectionStartY = startY + (int) (i * Math.sin(angle));
                            int sectionEndX = sectionStartX + (int) (arrowLength * Math.cos(angle));
                            int sectionEndY = sectionStartY + (int) (arrowLength * Math.sin(angle));

                            if (isWhiteSection) {
                                g2D.setColor(Color.WHITE);
                            } else {
                                g2D.setColor(Color.BLACK);
                            }

                            g2D.drawLine(sectionStartX, sectionStartY, sectionEndX, sectionEndY);
                            isWhiteSection = !isWhiteSection;
                        }

                
                        int arrowEndX = (int) (endX - arrowLength * Math.cos(angle));
                        int arrowEndY = (int) (endY - arrowLength * Math.sin(angle));
                        int[] arrowX = {endX, arrowEndX - 8, arrowEndX - 8, endX};
                        int[] arrowY = {endY, arrowEndY - 4, arrowEndY + 4, endY};
                        g2D.setColor(Color.BLACK);
                        g2D.fillPolygon(arrowX, arrowY, 4);
                    }
                    if (!texto.isEmpty()) {
                        g2D.drawString(texto, oldX, oldY);
                    }
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    currentX = e.getX();
                    currentY = e.getY();
                    if (herramienta.equals("dibujo")) {
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

        public void agregarTexto() {
            texto = JOptionPane.showInputDialog(null, "Ingrese el texto:");
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
                    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = image.createGraphics();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    paint(g);
                    String filePath = archivo.getPath();
                    if (!filePath.toLowerCase().endsWith(".png")) {
                        filePath += ".png";
                    }
                    ImageIO.write(image, "png", new File(filePath));
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
class Arrow {
    private static final int ARROW_SIZE = 10;

    public void drawArrow(Graphics2D g2D, int x1, int y1, int x2, int y2) {
        g2D.drawLine(x1, y1, x2, y2);

        double angle = Math.atan2(y2 - y1, x2 - x1);
        int x = (int) (x2 - ARROW_SIZE * Math.cos(angle - Math.PI / 6));
        int y = (int) (y2 - ARROW_SIZE * Math.sin(angle - Math.PI / 6));
        g2D.drawLine(x2, y2, x, y);

        x = (int) (x2 - ARROW_SIZE * Math.cos(angle + Math.PI / 6));
        y = (int) (y2 - ARROW_SIZE * Math.sin(angle + Math.PI / 6));
        g2D.drawLine(x2, y2, x, y);
    }
}
