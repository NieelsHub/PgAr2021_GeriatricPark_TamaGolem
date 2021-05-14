package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Definisce un giocatore, identificato da un username, che può avere un certo numero massimo di Tamagolem.
 * @author Geriatric Park (Nicol Stocchetti, Wiam Nasr Allah, Mohamed Nassar).
 *
 */
public class Giocatore {

    public static final int MAX_TAMAGOLEM = calcMaxTamagolem();///Da sistemare
    
    private String username;
    private Queue <Tamagolem> tamagolem  = new ArrayDeque <Tamagolem>(); //tamagolem ancora disponibili da evocare
    private Tamagolem tamaAttivo;

    /**
     * Costruttore generale, inizializza il giocatore con il massimo numero di tamagolem.
     * @param username il nome del giocatore, String.
     */
    public Giocatore(String username) {
		this.username = username;
		
		for(int i = 0; i < MAX_TAMAGOLEM; i++) {
			tamagolem.offer(new Tamagolem());
		}
    }
    
    /**
     * Calcola il numero massimo di tamagolem per il giocatore.
     * @return il numero massimo di tamagolem, int.
     */
    private static int calcMaxTamagolem() {
    	if(((Elementi.values().length - 1) * (Elementi.values().length - 2) % (2 * Tamagolem.MAX_PIETRE)) == 0)
    		return (Elementi.values().length - 1) * (Elementi.values().length - 2) / (2 * Tamagolem.MAX_PIETRE);

    	return ((Elementi.values().length - 1) * (Elementi.values().length - 2) / (2 * Tamagolem.MAX_PIETRE) + 1);
    }
    
    /**
     * Evoca un tamagolem.
     * @param pietre le pietre da fornire al tamagolem al momento dell'evocazione, it.unibs.arnaldo.geriatricpark.tamagolem.Elementi.
     * @return true se l'evocazione è andata a buon fine (perché c'erano ancora tamagolem disponibili da evocare), altrimenti false, boolean.
     */
    public boolean evocaTamagolem(Queue <Elementi> pietre) {
    	this.tamaAttivo = tamagolem.poll();
    	
    	if (this.tamaAttivo == null) //ho finito i tamagolem da evocare!
    		return false;
    	
    	tamaAttivo.setPietre(pietre);
    	
    	return true;
    }

    public String getUsername() {
        return username;
    }

    public Queue<Tamagolem> getTamagolem() {
        return tamagolem;
    }

	public Tamagolem getTamaAttivo() {
		return tamaAttivo;
	}
	
	public void setTamaAttivo(Tamagolem tamagolem) {
		this.tamaAttivo = tamagolem;
	}

}

