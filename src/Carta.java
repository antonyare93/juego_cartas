import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public JLabel mostrar(JPanel pnl, int x, int y) {
        String archivoImagen = "imagenes/CARTA" + indice + ".jpg";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(archivoImagen));
        JLabel lblCarta = new JLabel();
        lblCarta.setIcon(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());
        pnl.add(lblCarta);
        return lblCarta;
    }

}