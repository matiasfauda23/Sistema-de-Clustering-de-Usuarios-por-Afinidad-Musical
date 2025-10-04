package logica;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import interfaz.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
