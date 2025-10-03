package interfaz;

import logica.*;
import javax.swing.*;

import controlador.ControladorAfinidad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;



public class VentanaPrincipal extends JFrame {
    
    
	private static final long serialVersionUID = 1L;
	// Componentes de la UI
    private DefaultListModel<String> modeloUsuarios;
    private JList<String> listaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnEjecutarAlgoritmo;
    private JPanel panelResultados;
    // Controlador de la lógica
    private ControladorAfinidad controlador;

    //Metodo constructor
    public VentanaPrincipal() {
        super("Afinidad Musical - TP2");
        this.controlador = new ControladorAfinidad();
        inicializarComponentes();
        configurarVentana();
        eventos();
    }
    
    
    private void inicializarComponentes() {
        JPanel panelIzq = crearPanelUsuarios();
        JPanel panelDer = crearPanelResultados();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzq, panelDer);
        split.setDividerLocation(200);
        getContentPane().add(split);
    }

    
    private void configurarVentana() {
    	setSize(800, 600); // ancho x alto inicial
    	setLocationRelativeTo(null);
    	setVisible(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    //Interaccion entre usuario y controlador
    private void eventos() {
        // Agregar usuario y sus valores
        btnAgregarUsuario.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre != null && !nombre.trim().isEmpty()) {
               
                int tango = pedirValor("Tango");
                int folklore = pedirValor("Folklore");
                int rock = pedirValor("Rock");
                int urbano = pedirValor("Urbano");
                
                // Agregrego usuarios con sus valores al controlador
                controlador.agregarUsuario(nombre, tango, folklore, rock, urbano);

                // Solo actualizo la vista
                modeloUsuarios.addElement(nombre);
            }
        });
        
        // Ejecutar algoritmo y mostrar grupos
        btnEjecutarAlgoritmo.addActionListener(e -> {
            if (controlador.getUsuarios().size() < 2) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos 2 usuarios.");
                return;
            }

            String input = JOptionPane.showInputDialog(this, "¿Cuántos grupos desea generar?");
            try {
                int k = Integer.parseInt(input);

                if (k < 1 || k > controlador.getUsuarios().size()) {
                    JOptionPane.showMessageDialog(this, "Número de grupos inválido.");
                    return;
                }

                List<List<Usuario>> grupos = controlador.calcularGrupos(k);

                // Delego la visualizacion a metodo externo
                mostrarGrupos(grupos);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.");
            }
        });

    }


    //Metodos auxiliares
    private int pedirValor(String genero) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Interés en " + genero + " (1-5):");
            try {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 5) return val;
            } catch (Exception ignored) {}
            JOptionPane.showMessageDialog(this, "Debe ser un número entre 1 y 5.");
        }
    }
    
    private JPanel crearPanelUsuarios() {
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);

        btnAgregarUsuario = new JButton("Agregar Usuario");

        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.add(new JLabel("Usuarios cargados:"), BorderLayout.NORTH);
        panelIzq.add(scrollUsuarios, BorderLayout.CENTER);
        panelIzq.add(btnAgregarUsuario, BorderLayout.SOUTH);

        return panelIzq;
    }

    private JPanel crearPanelResultados() {
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollResultado = new JScrollPane(panelResultados);

        btnEjecutarAlgoritmo = new JButton("Ejecutar Algoritmo");

        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.add(new JLabel("Resultado:"), BorderLayout.NORTH);
        panelDer.add(scrollResultado, BorderLayout.CENTER);
        panelDer.add(btnEjecutarAlgoritmo, BorderLayout.SOUTH);

        return panelDer;
    }
    
    private void mostrarGrupos(List<List<Usuario>> grupos) {
        panelResultados.removeAll();

        int g = 1;
        for (List<Usuario> grupo : grupos) {
            JPanel grupoPanel = new JPanel();
            grupoPanel.setLayout(new BoxLayout(grupoPanel, BoxLayout.Y_AXIS));
            grupoPanel.setBorder(BorderFactory.createTitledBorder("Grupo " + g));
            grupoPanel.setBackground(new Color(230, 240, 255));

            // Mostrar usuarios
            for (Usuario u : grupo) {
                grupoPanel.add(new JLabel("• " + u.getNombre()));
            }

            // Calcular promedios
            double sumT = 0, sumF = 0, sumR = 0, sumU = 0;
            for (Usuario u : grupo) {
                sumT += u.getValorTango();
                sumF += u.getValorFolklore();
                sumR += u.getValorRock();
                sumU += u.getValorUrbano();
            }
            int n = grupo.size();

            // Mostrar estadísticas debajo
            grupoPanel.add(new JLabel("Promedio Tango: " + (sumT / n)));
            grupoPanel.add(new JLabel("Promedio Folklore: " + (sumF / n)));
            grupoPanel.add(new JLabel("Promedio Rock: " + (sumR / n)));
            grupoPanel.add(new JLabel("Promedio Urbano: " + (sumU / n)));

            // Envolver en contenedor que ocupa todo el ancho
            JPanel contenedor = new JPanel(new BorderLayout());
            contenedor.add(grupoPanel, BorderLayout.CENTER);

            panelResultados.add(contenedor);
            g++;
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }



    
    // Metodo main para iniciar la aplicación
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }

}

