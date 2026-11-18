
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

public class ArbolBinarioVisual extends JFrame {

    // COLORES DEL PROGRAMA
    private final Color FONDO = new Color(15, 23, 42);
    private final Color PANEL = new Color(30, 41, 59);
    private final Color AZUL = new Color(37, 99, 235);
    private final Color VERDE = new Color(22, 163, 74);
    private final Color MORADO = new Color(147, 51, 234);
    private final Color TEXTO = new Color(226, 232, 240);

    // ESTRUCTURA DEL ARBOL
    static class Nodo {
        int dato;
        Nodo izquierdo, derecho;

        Nodo(int dato) {
            this.dato = dato;
        }
    }

    private Nodo raiz;

    private JTextField campoDatos;
    private JTextArea resultado;
    private JLabel estado;
    private JPanel panelArbol;

    private List<Nodo> recorridoActual = new ArrayList<>();
    private int indiceAnimacion = 0;

    // CONSTRUCTOR
    public ArbolBinarioVisual() {

        setTitle("Sistema de Arbol Binario");
        setSize(1050, 720);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        crearInterfaz();
    }

    // CREAR INTERFAZ PRINCIPAL
    private void crearInterfaz() {

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(FONDO);
        encabezado.setBorder(new EmptyBorder(20, 25, 15, 25));

        JLabel titulo = new JLabel("ARBOL BINARIO VISUAL");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
            "Construccion y recorridos de estructuras binarias"
        );
        subtitulo.setForeground(new Color(148, 163, 184));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));
        textos.setBackground(FONDO);
        textos.add(titulo);
        textos.add(subtitulo);


        encabezado.add(textos, BorderLayout.WEST);

        // Nombre del desarrollador
        JLabel autor = new JLabel("Charles Huaman Villano");

        autor.setFont(new Font("SansSerif", Font.PLAIN, 12));
        autor.setForeground(new Color(148, 163, 184));
        autor.setHorizontalAlignment(SwingConstants.RIGHT);

        // Colocar nombre en la esquina superior derecha
        encabezado.add(autor, BorderLayout.EAST);

        add(encabezado, BorderLayout.NORTH);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("SansSerif", Font.BOLD, 14));
        pestañas.setBackground(PANEL);
        pestañas.setForeground(TEXTO);

        pestañas.addTab("  Constructor  ", crearConstructor());
        pestañas.addTab("  Recorridos  ", crearRecorridos());
        pestañas.addTab("  Información  ", crearInformacion());

        add(pestañas, BorderLayout.CENTER);

        estado = new JLabel("  Sistema listo. Cree un arbol para comenzar.");
        estado.setForeground(new Color(148, 163, 184));
        estado.setBackground(PANEL);
        estado.setOpaque(true);
        estado.setBorder(new EmptyBorder(12, 15, 12, 15));

        add(estado, BorderLayout.SOUTH);
    }

    // PESTAÑA CONSTRUCTOR
    private JPanel crearConstructor() {

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(FONDO);
        principal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel superior = new JPanel(new BorderLayout(10, 10));
        superior.setBackground(PANEL);
        superior.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel etiqueta = new JLabel("Ingrese los valores del arbol");
        etiqueta.setForeground(TEXTO);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 16));

        campoDatos = new JTextField("1, 2, 3, 4, 5, 6, 7");
        campoDatos.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campoDatos.setBackground(FONDO);
        campoDatos.setForeground(TEXTO);
        campoDatos.setCaretColor(Color.WHITE);
        campoDatos.setBorder(new CompoundBorder(
            new LineBorder(AZUL, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel ayuda = new JLabel(
            "Separe los numeros con comas. Ejemplo: 1, 2, 3, 4, 5"
        );
        ayuda.setForeground(new Color(148, 163, 184));

        JPanel entrada = new JPanel(new BorderLayout(0, 8));
        entrada.setBackground(PANEL);
        entrada.add(etiqueta, BorderLayout.NORTH);
        entrada.add(campoDatos, BorderLayout.CENTER);
        entrada.add(ayuda, BorderLayout.SOUTH);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setBackground(PANEL);

        JButton construir = crearBoton("Construir arbol", AZUL);
        JButton limpiar = crearBoton("Limpiar", new Color(71, 85, 105));

        construir.addActionListener(e -> construirArbol());
        limpiar.addActionListener(e -> limpiar());

        botones.add(construir);
        botones.add(limpiar);

        superior.setLayout(new BorderLayout(0, 15));
        superior.add(entrada, BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);

        panelArbol = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarArbol(g);
            }
        };

        panelArbol.setBackground(new Color(17, 29, 51));
        panelArbol.setPreferredSize(new Dimension(700, 360));
        panelArbol.setBorder(new LineBorder(new Color(51, 65, 85)));

        principal.add(superior, BorderLayout.NORTH);
        principal.add(panelArbol, BorderLayout.CENTER);

        return principal;
    }

    // PESTAÑA RECORRIDOS
    private JPanel crearRecorridos() {

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(FONDO);
        principal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Selecciona un recorrido");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel botones = new JPanel(new GridLayout(1, 3, 12, 0));
        botones.setBackground(FONDO);

        JButton pre = crearBoton("PREORDEN", AZUL);
        JButton in = crearBoton("INORDEN", VERDE);
        JButton post = crearBoton("POSTORDEN", MORADO);

        pre.addActionListener(e -> ejecutarRecorrido("PREORDEN"));
        in.addActionListener(e -> ejecutarRecorrido("INORDEN"));
        post.addActionListener(e -> ejecutarRecorrido("POSTORDEN"));

        botones.add(pre);
        botones.add(in);
        botones.add(post);

        resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setBackground(PANEL);
        resultado.setForeground(TEXTO);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultado.setBorder(new EmptyBorder(15, 15, 15, 15));
        resultado.setText("Los resultados apareceran aqui.");

        JScrollPane scroll = new JScrollPane(resultado);
        scroll.setBorder(new LineBorder(new Color(51, 65, 85)));

        JPanel superior = new JPanel(new BorderLayout(0, 15));
        superior.setBackground(FONDO);
        superior.add(titulo, BorderLayout.NORTH);
        superior.add(botones, BorderLayout.CENTER);

        principal.add(superior, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);

        return principal;
    }

    // PESTAÑA INFORMACION
    private JPanel crearInformacion() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JTextArea info = new JTextArea();

        info.setEditable(false);
        info.setBackground(PANEL);
        info.setForeground(TEXTO);
        info.setFont(new Font("SansSerif", Font.PLAIN, 16));
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setBorder(new EmptyBorder(20, 20, 20, 20));

        info.setText(
            "SISTEMA DE ARBOL BINARIO\n\n" +

            "Un arbol binario es una estructura de datos " +
            "en la que cada nodo puede tener como maximo " +
            "dos hijos: izquierdo y derecho.\n\n" +

            "1. PREORDEN\n" +
            "Raiz -> Izquierda -> Derecha\n" +
            "Primero visita la raiz, despues el hijo izquierdo " +
            "y finalmente el derecho.\n\n" +

            "2. INORDEN\n" +
            "Izquierda -> Raiz -> Derecha\n" +
            "Primero visita el hijo izquierdo, despues la raiz " +
            "y finalmente el derecho.\n\n" +

            "3. POSTORDEN\n" +
            "Izquierda -> Derecha -> Raiz\n" +
            "Primero visita ambos hijos y al final la raiz.\n\n" +

            "PROYECTO DESARROLLADO EN JAVA SWING."
        );

        panel.add(new JScrollPane(info), BorderLayout.CENTER);

        return panel;
    }

    // CREAR BOTONES
    private JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(12, 18, 12, 18));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    // CONSTRUIR ARBOL POR NIVELES
    private void construirArbol() {

        try {

            String texto = campoDatos.getText().trim();

            if (texto.isEmpty()) {
                throw new IllegalArgumentException(
                    "Debe ingresar los valores."
                );
            }

            String[] valores = texto.split(",");

            List<Integer> numeros = new ArrayList<>();

            for (String valor : valores) {
                numeros.add(Integer.parseInt(valor.trim()));
            }

            raiz = new Nodo(numeros.get(0));

            Queue<Nodo> cola = new LinkedList<>();
            cola.add(raiz);

            int i = 1;

            while (i < numeros.size()) {

                Nodo actual = cola.poll();

                if (actual == null) {
                    throw new IllegalArgumentException(
                        "La estructura del arbol no es valida."
                    );
                }

                actual.izquierdo = new Nodo(numeros.get(i++));
                cola.add(actual.izquierdo);

                if (i < numeros.size()) {
                    actual.derecho = new Nodo(numeros.get(i++));
                    cola.add(actual.derecho);
                }
            }

            recorridoActual.clear();
            indiceAnimacion = 0;

            panelArbol.repaint();

            estado.setText("  Arbol construido correctamente.");
            resultado.setText(
                "Arbol creado con " + numeros.size() + " nodos."
            );

            JOptionPane.showMessageDialog(
                this,
                "El arbol se construyo correctamente.",
                "Operacion exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Ingrese solamente numeros enteros separados por comas.",
                "Error de entrada",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // RECORRIDO PREORDEN
    private void preorden(Nodo nodo, List<Nodo> lista) {

        if (nodo != null) {
            lista.add(nodo);
            preorden(nodo.izquierdo, lista);
            preorden(nodo.derecho, lista);
        }
    }

    // RECORRIDO INORDEN
    private void inorden(Nodo nodo, List<Nodo> lista) {

        if (nodo != null) {
            inorden(nodo.izquierdo, lista);
            lista.add(nodo);
            inorden(nodo.derecho, lista);
        }
    }

    // RECORRIDO POSTORDEN
    private void postorden(Nodo nodo, List<Nodo> lista) {

        if (nodo != null) {
            postorden(nodo.izquierdo, lista);
            postorden(nodo.derecho, lista);
            lista.add(nodo);
        }
    }

    // EJECUTAR RECORRIDOS
    private void ejecutarRecorrido(String tipo) {

        if (raiz == null) {
            JOptionPane.showMessageDialog(
                this,
                "Primero debe construir un arbol.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        recorridoActual.clear();

        switch (tipo) {

            case "PREORDEN":
                preorden(raiz, recorridoActual);
                break;

            case "INORDEN":
                inorden(raiz, recorridoActual);
                break;

            case "POSTORDEN":
                postorden(raiz, recorridoActual);
                break;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("RECORRIDO ").append(tipo).append("\n\n");

        for (int i = 0; i < recorridoActual.size(); i++) {
            sb.append(recorridoActual.get(i).dato);

            if (i < recorridoActual.size() - 1) {
                sb.append(" -> ");
            }
        }

        sb.append("\n\nTotal de nodos: ");
        sb.append(recorridoActual.size());

        resultado.setText(sb.toString());

        indiceAnimacion = 0;

        Color color = tipo.equals("PREORDEN") ? AZUL :
                      tipo.equals("INORDEN") ? VERDE : MORADO;

        // Animacion de recorrido
        javax.swing.Timer timer = new javax.swing.Timer(550, null);

        timer.addActionListener(e -> {

            if (indiceAnimacion >= recorridoActual.size()) {
                timer.stop();
                return;
            }

            indiceAnimacion++;
            panelArbol.repaint();
        });

        timer.start();

        estado.setText("  Ejecutando recorrido: " + tipo);
    }

    // LIMPIAR
    private void limpiar() {

        raiz = null;
        recorridoActual.clear();
        indiceAnimacion = 0;

        campoDatos.setText("");
        resultado.setText("Los resultados apareceran aqui.");
        estado.setText("  Arbol eliminado. Puede crear uno nuevo.");

        panelArbol.repaint();
    }

    // DIBUJAR ARBOL
    private void dibujarArbol(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (raiz == null) {

            g2.setColor(new Color(148, 163, 184));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 17));

            String mensaje = "Ingrese los valores y construya su arbol";

            FontMetrics fm = g2.getFontMetrics();

            g2.drawString(
                mensaje,
                (panelArbol.getWidth() - fm.stringWidth(mensaje)) / 2,
                panelArbol.getHeight() / 2
            );

            g2.dispose();
            return;
        }

        dibujarNodo(
            g2,
            raiz,
            panelArbol.getWidth() / 2,
            55,
            panelArbol.getWidth() / 4,
            1
        );

        g2.dispose();
    }

    // DIBUJAR CADA NODO Y SUS CONEXIONES
    private void dibujarNodo(
        Graphics2D g,
        Nodo nodo,
        int x,
        int y,
        int separacion,
        int nivel
    ) {

        if (nodo == null) return;

        int radio = 22;
        int siguienteY = y + 90;

        // Dibujar conexiones
        g.setStroke(new BasicStroke(2));
        g.setColor(new Color(71, 85, 105));

        if (nodo.izquierdo != null) {

            g.drawLine(
                x, y + radio,
                x - separacion, siguienteY - radio
            );

            dibujarNodo(
                g, nodo.izquierdo,
                x - separacion,
                siguienteY,
                Math.max(25, separacion / 2),
                nivel + 1
            );
        }

        if (nodo.derecho != null) {

            g.drawLine(
                x, y + radio,
                x + separacion, siguienteY - radio
            );

            dibujarNodo(
                g, nodo.derecho,
                x + separacion,
                siguienteY,
                Math.max(25, separacion / 2),
                nivel + 1
            );
        }

        // Color del nodo segun recorrido
        Color colorNodo = AZUL;

        int posicion = recorridoActual.indexOf(nodo);

        if (posicion >= 0 && posicion < indiceAnimacion) {
            colorNodo = VERDE;
        }

        // Dibujar circulo
        g.setColor(colorNodo);
        g.fillOval(x - radio, y - radio, radio * 2, radio * 2);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawOval(x - radio, y - radio, radio * 2, radio * 2);

        // Mostrar valor
        g.setFont(new Font("SansSerif", Font.BOLD, 15));

        String valor = String.valueOf(nodo.dato);

        FontMetrics fm = g.getFontMetrics();

        g.drawString(
            valor,
            x - fm.stringWidth(valor) / 2,
            y + fm.getAscent() / 2 - 2
        );
    }

    // INICIAR APLICACION
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ArbolBinarioVisual ventana = new ArbolBinarioVisual();

            ventana.setVisible(true);
        });
    }
}
