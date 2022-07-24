package interfaz;

import javax.swing.*;

public class Ventana extends JFrame {

    private Panel_principal panel_principal;

    public Ventana() {
        init();
    }

    private void init() {
        setTitle("SecurityDoc");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        panel_principal = new Panel_principal();
        add(panel_principal);
        setVisible(true);
    }
}
