package interfaz;

import logica.*;
import javax.swing.*;

import controlador.ControladorAfinidad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import interfaz.GrafoVisual;


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
				int k = controlador.convertirCantidadGrupos(input);
				controlador.setNumeroGrupos(k); // Valor predeterminado

				if (!controlador.esNumeroDeGruposValido(k)) {
				    JOptionPane.showMessageDialog(this, "Número de grupos inválido.");
				    return;
				}


				List<List<Usuario>> grupos = controlador.calcularGrupos(k);

				// Delego la visualizacion a metodo externo
				mostrarGrupos(grupos);
				
				// Habilitar boton para mostrar AGM
				btnMostrarAGM.setEnabled(true);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.");
			}
		});
			
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
		            ArchivoJSON manejador = new ArchivoJSON();
		            List<Usuario> usuariosCargados = manejador.leerUsuarios(archivo.getAbsolutePath());
		            
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
		
		
			btnMostrarAGM.addActionListener(e -> {
			    try {
			        GrafoVisual grafo = controlador.obtenerGrafoVisualDividido(controlador.getNumeroGrupos());
			        grafo.mostrar();
			    } catch (IllegalArgumentException ex) {
			        JOptionPane.showMessageDialog(this, ex.getMessage());
			    }
			});
//			        
//			            
//			            System.setProperty("org.graphstream.ui", "swing");
//			            
//			            GrafoVisual grafoVisual = new GrafoVisual();
//			            Grafo grafo = new Grafo(controlador.getUsuarios());
//			            
//			            // Agregar nodos
//			            for (Usuario usuario : controlador.getUsuarios()) {
//			                grafoVisual.agregarNodo(usuario.getNombre(), usuario.getNombre());
//			            }
//			            
//			            // Obtener AGM y dividirlo en k grupos
//			            List<Arista> agm = grafo.kruskal();
//			            
//			            // Eliminar las (k-1) aristas más pesadas
//			            for (int i = 0; i < grupos - 1; i++) {
//			                grafo.eliminarAristaMayorPeso(agm);
//			            }
//			            
//			            // Agregar las aristas restantes
//			            int contador = 0;
//			            for (Arista arista : agm) {
//			                String nodo1 = arista.getUsuario1().getNombre();
//			                String nodo2 = arista.getUsuario2().getNombre();
//			                double peso = arista.getPeso();
//			                
//			                grafoVisual.agregarAristaConPeso("arista" + contador, nodo1, nodo2, peso);
//			                contador++;
//			            }
//			            grafoVisual.mostrar();		            		         
			    }
			
	
    //crea el grafo visual y lo muestra (revisar si se puede cambiar el metodo)


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
	    listaUsuarios.setFont(new Font("Arial", Font.PLAIN, 14)); 
	    JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);

	    // Boton con fuente personalizada
	    btnAgregarUsuario = new JButton("Agregar Usuario");
	    btnAgregarUsuario.setFont(new Font("Verdana", Font.BOLD, 14));
	    btnAgregarUsuario.setBackground(new Color(180, 210, 250));
	    btnAgregarUsuario.setFocusPainted(false);
	    
	    // Boton para cargar usuario desde archivo JSON
	    btnCargar = new JButton("Cargar desde JSON");
	    btnCargar.setFont(new Font("Verdana", Font.BOLD, 14));
	    btnCargar.setBackground(new Color(180, 210, 250));
	    btnCargar.setFocusPainted(false);

	    // Label con fuente y color personalizados
	    JLabel lblUsuarios = new JLabel("Usuarios cargados:");
	    lblUsuarios.setFont(new Font("Arial", Font.BOLD, 16));
	    lblUsuarios.setForeground(Color.DARK_GRAY);

	    // Panel izquierdo con fondo azul claro
	    JPanel panelIzq = new JPanel(new BorderLayout());
	    panelIzq.setBackground(new Color(200, 230, 255));

	    panelIzq.add(lblUsuarios, BorderLayout.NORTH);
	    panelIzq.add(scrollUsuarios, BorderLayout.CENTER);
	    panelIzq.add(btnAgregarUsuario, BorderLayout.SOUTH);
	    panelIzq.add(btnCargar, BorderLayout.NORTH);

	    return panelIzq;
	}


	private JPanel crearPanelResultados() {
	    panelResultados = new JPanel();
	    panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
	    JScrollPane scrollResultado = new JScrollPane(panelResultados);

	    // Boton con fuente personalizada
	    btnEjecutarAlgoritmo = new JButton("Ejecutar Algoritmo");
	    btnEjecutarAlgoritmo.setFont(new Font("Verdana", Font.BOLD, 14)); 
	    btnEjecutarAlgoritmo.setBackground(new Color(180, 210, 250)); 
	    btnEjecutarAlgoritmo.setFocusPainted(false); // quita el borde al hacer click	    

	    // Label con fuente y color personalizados
	    JLabel lblResultado = new JLabel("Resultado:");
	    lblResultado.setFont(new Font("Arial", Font.BOLD, 16)); 
	    lblResultado.setForeground(Color.DARK_GRAY); // texto gris oscuro
	    
	    // Boton para mostrar AGM
	    btnMostrarAGM = new JButton("Mostrar AGM");
	    btnMostrarAGM.setFont(new Font("Verdana", Font.BOLD, 14));
	    btnMostrarAGM.setBackground(new Color(180, 210, 250));
	    btnMostrarAGM.setFocusPainted(false); // quita el borde al hacer click

	    // Panel derecho con fondo azul claro
	    JPanel panelDer = new JPanel(new BorderLayout());
	    panelDer.setBackground(new Color(200, 230, 255));

	    panelDer.add(lblResultado, BorderLayout.NORTH);
	    panelDer.add(scrollResultado, BorderLayout.CENTER);
	    panelDer.add(btnEjecutarAlgoritmo, BorderLayout.SOUTH);
	    panelDer.add(btnMostrarAGM, BorderLayout.EAST);

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
			Estadistica agrupador = new Estadistica(grupo);
			
			// Mostrar estadisticas debajo
			grupoPanel.add(new JLabel("Promedio Tango: " + agrupador.getPromedioTango()));
			grupoPanel.add(new JLabel("Promedio Folklore: " + agrupador.getPromedioFolklore()));
			grupoPanel.add(new JLabel("Promedio Rock: " + agrupador.getPromedioRock()));
			grupoPanel.add(new JLabel("Promedio Urbano: " + agrupador.getPromedioUrbano()));

			// Envolver en contenedor que ocupa todo el ancho
			JPanel contenedor = new JPanel(new BorderLayout());
			contenedor.add(grupoPanel, BorderLayout.CENTER);

			panelResultados.add(contenedor);
			g++;
		}

		panelResultados.revalidate();
		panelResultados.repaint();
	}


}

