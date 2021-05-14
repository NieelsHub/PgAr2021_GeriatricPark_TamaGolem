package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Determina un tamagolem definito da un numero massimo di vita e un numero massimo di pietre.
 * @author Geriatric Park (Nicol Stocchetti, Wiam Nasr Allah, Mohamed Nassar).
 *
 */
public class Tamagolem{
	public static final int MAX_PIETRE = calcMaxPietre();
	public static final int MAX_VITA = 30;
	public static final String NORMAL_SPRITE = "\n    ??       ??\r\n"
			+ "   ????     ????\r\n"
			+ "    ????===????\r\n"
			+ "   /           \\\r\n"
			+ "  |  O  ___  O  |\r\n"
			+ "???             ???\r\n"
			+ "  |             |\r\n"
			+ "   \\  _______  /\r\n"
			+ "   =??       ??=\n";
	
    private int vita;
    private Queue <Elementi> pietre = new ArrayDeque <Elementi>();

    /**
     * Costruttore generale, inizializza un tamagolem al massimo della sua vita.
     */
    public Tamagolem () {
        this.vita = MAX_VITA;
    }
    
    /**
     * Calcola il numero di pietre che può ingoiare il tamagolem.
     * @return il numero di pietre che può ingoiare il tamagolem, int.
     */
    private static int calcMaxPietre() {
    	if(((Elementi.values().length + 1) % 3) == 0)
    		return ((Elementi.values().length + 1) / 3) + 1;

    	return (((Elementi.values().length + 1) / 3) + 2);
    }
    
   /**
	* Fa mangiare delle pietre al Tamagolem.
	* @param pietre le pietre da dare al Tamagolem, it.unibs.arnaldo.geriatricpark.tamagolem.Elementi.
    */
    public void setPietre(Queue <Elementi> pietre) {
        this.pietre = pietre;
    }

    /**
     * Esegue l'attacco di un tamagolem.
     * @return l'elemento sprigionato dall'attacco, it.unibs.arnaldo.geriatricpark.tamagolem.Elementi.
     */
    public Elementi attacca() {
        Elementi temp;
        
        temp = pietre.poll();

        pietre.offer(temp);

        return temp;
    }

    public int getVita() {
        return vita;
    }


    public void setVita(int vita) {
        this.vita = vita;
    }
    
    /**
     * Controlla se un tamagolem è morto.
     * @return true se è morto, false se è ancora vivo, boolean.
     */
    public boolean isMorto() {
    	if (this.vita > 0)
    		return false;
    	return true;
    }

    public Queue <Elementi> getPietre() {
        return pietre;
    }

}