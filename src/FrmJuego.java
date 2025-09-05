import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    private Jugador jugador1, jugador2;
    private JTabbedPane tpJugadores;

    public FrmJuego() {

        setSize(600, 340);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        getContentPane().add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        getContentPane().add(btnVerificar);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(50, 255, 0));
        pnlJugador1.setLayout(null);
        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores = new JTabbedPane();
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);
        tpJugadores.setBounds(10, 40, 550, 200);
        getContentPane().add(tpJugadores);

        // Agregar los eventos
        btnRepartir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repartir();
            }

        });

        btnVerificar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                verificar();

            }

        });

        // crear las instancias para los dos jugadores
        jugador1 = new Jugador();
        jugador2 = new Jugador();

    }

    private void repartir() {
        jugador1.repartir();
        jugador2.repartir();

        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }
// imprimir puntaje total
    private void verificar() {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
            JOptionPane.showMessageDialog(null, jugador1.getGrupos());
            System.out.println("Puntaje jugador 1: " + jugador1.calcularPuntaje());
                break;

            case 1:
            JOptionPane.showMessageDialog(null, jugador2.getGrupos());
            System.out.println("Puntaje jugador 2: " + jugador2.calcularPuntaje());
                break;
        }
    }

}