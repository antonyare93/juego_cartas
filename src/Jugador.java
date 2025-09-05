import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;


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

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        boolean hayGrupo = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupo = true;
                break;
            }
        }

        if (hayGrupo) {
            resultado = "Se encontraron los siguientes grupos";
            int p = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    resultado += "\n" + Grupo.values()[contador] + " de " + NombreCarta.values()[p];
                }
                p++;
            }
        }

        return resultado;
    }
    public int calcularPuntaje() {
        // resetear estado antes de calcular
        for (Carta carta : cartas) {
            carta.setUtilizada(false);
        }

        // detectar grupos (pares, ternas, cuartetos…)
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            int count = 0;
            for (int j = 0; j < TOTAL_CARTAS; j++) {
                if (cartas[i].getNombre() == cartas[j].getNombre()) {
                    count++;
                }
            }
            if (count >= 2) { // ya elimina pares también
                for (int j = 0; j < TOTAL_CARTAS; j++) {
                    if (cartas[i].getNombre() == cartas[j].getNombre()) {
                        cartas[j].setUtilizada(true);
                    }
                }
            }
        }

        // detectar escalas (3+ consecutivas de la misma pinta)
        for (Pinta pinta : Pinta.values()) {
            List<Carta> mismasPinta = new ArrayList<>();
            for (Carta carta : cartas) {
                if (carta.getPinta() == pinta) {
                    mismasPinta.add(carta);
                }
            }
            mismasPinta.sort((a, b) -> a.getNombre().ordinal() - b.getNombre().ordinal());

            int start = 0;
            for (int k = 1; k <= mismasPinta.size(); k++) {
                boolean endSeq = (k == mismasPinta.size()) ||
                        (mismasPinta.get(k).getNombre().ordinal() !=
                                mismasPinta.get(k - 1).getNombre().ordinal() + 1);

                if (endSeq) {
                    int length = k - start;
                    if (length >= 3) {
                        for (int m = start; m < k; m++) {
                            mismasPinta.get(m).setUtilizada(true);
                        }
                    }
                    start = k;
                }
            }
        }

        // sumar cartas que NO fueron utilizadas
        int total = 0;
        StringBuilder sb = new StringBuilder("Calculando puntaje: ");
        
        boolean first = true;
        for (Carta carta : cartas) {
            if (!carta.isUtilizada()) {
                int valor = carta.obtenerValor();
                if (!first) {
                    sb.append(" + ");
                }
                //mostrar el como se calcula el puntaje total
                sb.append(valor).append(" (").append(carta.getNombre()).append(" de ").append(carta.getPinta()).append(")");
                total += valor;
                first = false;
            }
        }
        
        sb.append(" = ").append(total);
        System.out.println(sb.toString());
        
        return total;
    }
}