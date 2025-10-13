package interfaz;

import logica.*;
import javax.swing.*;

import controlador.ControladorAfinidad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


public class VentanaPrincipal extends JFrame {


	private static final long serialVersionUID = 1L;
	// Componentes de la UI
	private DefaultListModel<String> modeloUsuarios;
	private JList<String> listaUsuarios;
	private JButton btnAgregarUsuario;
	private JButton btnEjecutarAlgoritmo;
	private JPanel panelResultados;
	private JButton btnCargar;
	private JButton btnMostrarAGM;

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
		btnMostrarAGM.setEnabled(false);
	}


	private void configurarVentana() {
		setSize(800, 600); // ancho x alto inicial
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	//Interaccion entre usuario y controlador
	private void eventos() {
		eventoAgregarUsuario();
		eventoEjecutarAlgoritmo();
		eventoCargarJSON();
		eventoAGM();     		        
	}
	
	private void eventoAgregarUsuario() {
		// Agregar usuario y sus valores
		btnAgregarUsuario.addActionListener(e -> {
			String nombre = JOptionPane.showInputDialog(this, "Nombre:");
			if (nombre != null && !nombre.trim().isEmpty()) {

				int tango = pedirValor("Tango");
				int folklore = pedirValor("Folklore");
				int rock = pedirValor("Rock");
				int urbano = pedirValor("Urbano");

				controlador.agregarUsuario(nombre, tango, folklore, rock, urbano);

				// Solo actualizo la vista
				modeloUsuarios.addElement(nombre);
			}
		});
	}
		
	private void eventoEjecutarAlgoritmo() {
	    btnEjecutarAlgoritmo.addActionListener(e -> {
	        if (controlador.getUsuarios().size() < 2) {
	            JOptionPane.showMessageDialog(this, "Debe haber al menos 2 usuarios.");
	            return;
	        }

	        String input = JOptionPane.showInputDialog(this, "¿Cuántos grupos desea generar?");
	        try {
	            int k = controlador.convertirCantidadGrupos(input);

	            List<List<Usuario>> grupos = controlador.generarGrupos(k);

	            mostrarGrupos(grupos);

	            btnMostrarAGM.setEnabled(true);

	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.");
	        } catch (IllegalArgumentException ex) {
	            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });
	}


	
	private void eventoCargarJSON() {
		// Cargar usuario desde archivo JSON		
		btnCargar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        
		        javax.swing.filechooser.FileNameExtensionFilter filtro = 
		            new javax.swing.filechooser.FileNameExtensionFilter("Archivos JSON", "json");
		        fileChooser.setFileFilter(filtro);
		        
		        int seleccion = fileChooser.showOpenDialog(null);
		        if (seleccion == JFileChooser.APPROVE_OPTION) {
		            File archivo = fileChooser.getSelectedFile();
		            List<Usuario> usuariosCargados = controlador.cargarUsuariosDesdeArchivo(archivo.getAbsolutePath());
		            
		            if (usuariosCargados != null && !usuariosCargados.isEmpty()) {
		                for (Usuario usuario : usuariosCargados) {
		                    controlador.agregarUsuarioDesdeJSON(usuario);
		                    modeloUsuarios.addElement(usuario.getNombre());
		                }
		                JOptionPane.showMessageDialog(null, 
		                    "Se cargaron " + usuariosCargados.size() + " usuarios correctamente");
		            } else {
		                JOptionPane.showMessageDialog(null, 
		                    "Error al cargar usuarios", "Error", JOptionPane.ERROR_MESSAGE);
		            }

		        }
		    }
		});
	}
	
	private void eventoAGM() {
		btnMostrarAGM.addActionListener(e -> {
		    try {
		        GrafoVisual grafo = controlador.obtenerGrafoVisualDividido(controlador.getNumeroGrupos());
		        grafo.mostrar();
		    } catch (IllegalArgumentException ex) {
		        JOptionPane.showMessageDialog(this, ex.getMessage());
		    }
		});	
	}

	// Pedir valor entre 1 y 5
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
	    listaUsuarios = crearListaUsuarios();
	    JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);

	    JLabel lblUsuarios = crearLabelUsuarios();

	    // Crear botones con metodos auxiliares
	    btnAgregarUsuario = crearBoton("Agregar Usuario");
	    btnCargar = crearBoton("Cargar desde JSON");

	    // Panel para apilar botones (2 o mas filas)
	    JPanel panelBotones = crearPanelBotones(btnAgregarUsuario, btnCargar);

	    // Panel izquierdo principal
	    JPanel panelIzq = new JPanel(new BorderLayout());
	    panelIzq.setBackground(new Color(200, 230, 255));

	    panelIzq.add(lblUsuarios, BorderLayout.NORTH);
	    panelIzq.add(scrollUsuarios, BorderLayout.CENTER);
	    panelIzq.add(panelBotones, BorderLayout.SOUTH);

	    return panelIzq;
	}


	private JPanel crearPanelResultados() {
	    panelResultados = crearPanelResultadosInterno();
	    JScrollPane scrollResultado = new JScrollPane(panelResultados);

	    JLabel lblResultado = crearLabelResultado();
	    btnEjecutarAlgoritmo = crearBoton("Ejecutar Algoritmo");
	    btnMostrarAGM = crearBoton("Mostrar Grafo Animado");

	    // Panel para apilar botones
	    JPanel panelBotones = crearPanelBotones(btnEjecutarAlgoritmo, btnMostrarAGM);

	    // Panel derecho principal
	    JPanel panelDer = new JPanel(new BorderLayout());
	    panelDer.setBackground(new Color(200, 230, 255));

	    panelDer.add(lblResultado, BorderLayout.NORTH);
	    panelDer.add(scrollResultado, BorderLayout.CENTER);
	    panelDer.add(panelBotones, BorderLayout.SOUTH);

	    return panelDer;
	}


	//Metodos auxiliares 

	private JList<String> crearListaUsuarios() {
		JList<String> lista = new JList<>(modeloUsuarios);
	    lista.setFont(new Font("Arial", Font.PLAIN, 14));
	    return lista;
	}
	private JLabel crearLabelUsuarios() {
	    JLabel lbl = new JLabel("Usuarios cargados:");
	    lbl.setFont(new Font("Arial", Font.BOLD, 16));
	    lbl.setForeground(Color.DARK_GRAY);
	    return lbl;
	}

	private JButton crearBoton(String texto) {
	    JButton btn = new JButton(texto);
	    btn.setFont(new Font("Verdana", Font.BOLD, 14));
	    btn.setBackground(new Color(180, 210, 250));
	    btn.setFocusPainted(false);
	    btn.setBorder(BorderFactory.createLineBorder(new Color(120, 150, 200)));
	    return btn;
	}

	private JPanel crearPanelResultadosInterno() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.WHITE);
	    return panel;
	}
	
	private JLabel crearLabelResultado() {
	    JLabel lbl = new JLabel("Resultado:");
	    lbl.setFont(new Font("Arial", Font.BOLD, 16));
	    lbl.setForeground(Color.DARK_GRAY);
	    lbl.setHorizontalAlignment(SwingConstants.CENTER);
	    return lbl;
	}

	private JPanel crearPanelBotones(JButton... botones) {
	    JPanel panel = new JPanel(new GridLayout(botones.length, 1, 5, 5));
	    panel.setBackground(new Color(200, 230, 255));

	    for (JButton b : botones) {
	        panel.add(b);
	    }
	    return panel;
	}


	private void mostrarGrupos(List<List<Usuario>> grupos) {
	    panelResultados.removeAll();

	    List<String> resumenes = controlador.obtenerResumenGrupos(grupos);

	    for (String textoGrupo : resumenes) {
	        JTextArea areaTexto = new JTextArea(textoGrupo);
	        areaTexto.setEditable(false);
	        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14));
	        areaTexto.setBackground(new Color(240, 245, 255));
	        areaTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        JPanel panelGrupo = new JPanel(new BorderLayout());
	        panelGrupo.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 230)));
	        panelGrupo.add(areaTexto, BorderLayout.CENTER);

	        panelResultados.add(panelGrupo);
	    }

	    panelResultados.revalidate();
	    panelResultados.repaint();
	}



}

