package interfaz;

import logica.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.GridLayout;
import java.util.List;
import java.util.ArrayList;



public class VentanaPrincipal extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Componentes de la UI
    private DefaultListModel<String> modeloUsuarios;
    private JList<String> listaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnEjecutarAlgoritmo;
    private JPanel panelResultados;

    
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
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollResultado = new JScrollPane(panelResultados);

        
        // Layout principal
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.add(new JLabel("Usuarios cargados:"), BorderLayout.NORTH);
        panelIzq.add(scrollUsuarios, BorderLayout.CENTER);
        panelIzq.add(btnAgregarUsuario, BorderLayout.SOUTH);
        
        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.add(new JLabel("Resultado:"), BorderLayout.NORTH);
        panelDer.add(scrollResultado, BorderLayout.CENTER);
        panelDer.add(btnEjecutarAlgoritmo, BorderLayout.SOUTH);
        
        //Usar JSplitPane para dividir la ventana
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
    
    private void eventos() {
        // Cargar usuario con diálogo simple
        btnAgregarUsuario.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre != null && !nombre.trim().isEmpty()) {
               
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

            // Preguntar cuántos grupos quiere generar
            String input = JOptionPane.showInputDialog(this, "¿Cuántos grupos desea generar?");
            try {
                int k = Integer.parseInt(input);

                if (k < 1 || k > usuarios.size()) {
                    JOptionPane.showMessageDialog(this, "Número de grupos inválido.");
                    return;
                }

                List<List<Usuario>> grupos = grafo.obtenerGrupos(k);

                // limpiar resultados anteriores
                panelResultados.removeAll();

                int g = 1;
                for (List<Usuario> grupo : grupos) {
                    JPanel grupoPanel = new JPanel();
                    grupoPanel.setLayout(new BoxLayout(grupoPanel, BoxLayout.Y_AXIS));
                    grupoPanel.setBorder(BorderFactory.createTitledBorder("Grupo " + g));
                    grupoPanel.setBackground(new Color(230, 240, 255));

                    for (Usuario u : grupo) {
                        grupoPanel.add(new JLabel(u.getNombre()));
                    }

                    // guardo el grupo en un contenedor que ocupa todo el ancho
                    JPanel contenedor = new JPanel(new BorderLayout());
                    contenedor.add(grupoPanel, BorderLayout.CENTER);

                    panelResultados.add(contenedor);
                    g++;
                }


                // refrescar UI
                panelResultados.revalidate();
                panelResultados.repaint();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.");
            }
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

