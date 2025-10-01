package interfaz;

import logica.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class VentanaPrincipal extends JFrame {
    
    // Componentes de la UI
    private DefaultListModel<String> modeloUsuarios;
    private JList<String> listaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnEjecutarAlgoritmo;
    private JTextArea areaResultado;
    
    // Datos de negocio
    private java.util.List<Usuario> usuarios;
    
    public VentanaPrincipal() {
        super("Afinidad Musical - TP2");
        this.usuarios = new ArrayList<>();
        inicializarComponentes();
        configurarVentana();
        eventos();
    }
    
    private void inicializarComponentes() {
        // Panel usuarios
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        
        btnAgregarUsuario = new JButton("Agregar Usuario");
        btnEjecutarAlgoritmo = new JButton("Ejecutar Algoritmo");
        
        // Panel resultado
        areaResultado = new JTextArea(15, 30);
        areaResultado.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        
        // Layout principal
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.add(new JLabel("Usuarios cargados:"), BorderLayout.NORTH);
        panelIzq.add(scrollUsuarios, BorderLayout.CENTER);
        panelIzq.add(btnAgregarUsuario, BorderLayout.SOUTH);
        
        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.add(new JLabel("Resultado:"), BorderLayout.NORTH);
        panelDer.add(scrollResultado, BorderLayout.CENTER);
        panelDer.add(btnEjecutarAlgoritmo, BorderLayout.SOUTH);
        
        getContentPane().setLayout(new GridLayout(1, 2));
        getContentPane().add(panelIzq);
        getContentPane().add(panelDer);
    }
    
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void eventos() {
        // Cargar usuario con diálogo simple
        btnAgregarUsuario.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                // Valores de intereses (se pueden mejorar con sliders o comboBox)
                int tango = pedirValor("Tango");
                int folklore = pedirValor("Folklore");
                int rock = pedirValor("Rock");
                int urbano = pedirValor("Urbano");
                
                Usuario u = new Usuario(nombre, tango, folklore, rock, urbano);
                usuarios.add(u);
                modeloUsuarios.addElement(nombre);
            }
        });
        
        // Ejecutar algoritmo y mostrar grupos
        btnEjecutarAlgoritmo.addActionListener(e -> {
            if (usuarios.size() < 2) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos 2 usuarios.");
                return;
            }
            Grafo grafo = new Grafo(usuarios);
            String resultado = grafo.mostrarInfoGruposConEstadisticas();
            areaResultado.setText(resultado);
        });
    }
    
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
    
    // Main para probar la UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}

