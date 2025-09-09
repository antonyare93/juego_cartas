import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int SEPARACION = 40;
    private final int MARGEN = 10;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
        ordenarCartas(cartas);
    }

    public void ordenarCartas(Carta[] cartas) {
        Carta cartaAux;
        for (int i = 0; i < TOTAL_CARTAS - 1; i++) {
            for (int j = i + 1; j < TOTAL_CARTAS; j++) {
                if (cartas[i].getNombre().ordinal() > cartas[j].getNombre().ordinal()) {
                    cartaAux = cartas[i];
                    cartas[i] = cartas[j];
                    cartas[j] = cartaAux;
                }
            }
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN;
        JLabel[] lblCartas = new JLabel[TOTAL_CARTAS];
        int z = 0;
        for (Carta carta : cartas) {
            lblCartas[z] = carta.mostrar(pnl, posicion, MARGEN);
            posicion += SEPARACION;
            z++;
        }

        z = lblCartas.length - 1;
        for (JLabel lbl : lblCartas) {
            pnl.setComponentZOrder(lbl, z);
            z--;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";

        int[] escaleras = new int[NombreCarta.values().length];
        for (int i = 0; i < cartas.length - 1; i++) {
            int conteo = 1;
            for (int j = i + 1; j < cartas.length; j++) {
                if (!cartas[i].isUtilizada()) {
                    if (cartas[j].getNombre().ordinal() - cartas[i].getNombre().ordinal() == conteo && cartas[j].getPinta() == cartas[i].getPinta() && !cartas[j].isUtilizada()) {
                        conteo++;
                        escaleras[i] = conteo;
                        cartas[j].setUtilizada(true);
                    }
                }
            }
            if (conteo > 1) {
                cartas[i].setUtilizada(true);
                continue;
            }
            escaleras[i] = conteo;
        }

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }
        for (Carta carta : cartas) {
            if (contadores[carta.getNombre().ordinal()] >= 2) {
                carta.setUtilizada(true);
            }
        }

        boolean hayGrupo = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupo = true;
                break;
            }
        }
        for (int escalera : escaleras) {
            if (escalera >= 2) {
                hayGrupo = true;
                break;
            }
        }

        if (hayGrupo) {
            resultado = "Se encontraron los siguientes grupos";
            int p = 0;
            for (int escalera : escaleras) {
                if (escalera >= 2) {
                    resultado += "\nEscalera desde " + cartas[p].getNombre() + " hasta " + NombreCarta.values()[cartas[p].getNombre().ordinal() + escalera - 1] + " de " + cartas[p].getPinta() ; 
                }
                p++;
            }
            p = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    resultado += "\n" + Grupo.values()[contador] + " de " + NombreCarta.values()[p];
                }
                p++;
            }
        }

        return resultado;
    }

}