package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.ArrayList;

/**
 * Modellizza una battaglia tra tamagolem.
 * @author Geriatric Park (Nicol Stocchetti, Wiam Nasr Allah, Mohamed Nassar).
 *
 */
public class Scontro {

	public static final int PIETRE_INIZIALI = calcPietreIniziali();
	public static final int NUM_GIOCATORI = 2;
	
	private Giocatore[] giocatori;
	private ArrayList <Elementi> pietreAttuali = new ArrayList <Elementi>();
	
	/**
	 * Metodo costruttore di Scontro.
	 * @param giocatori i giocatori che si sfideranno, it.unibs.arnaldo.geriatricpark.tamagolem.Giocatore[].
	 * */
	public Scontro(Giocatore[] giocatori) {
		this.giocatori = giocatori;
		///creazione pietre iniziali
		for (Elementi elemento : Equilibrio.getElementi()) {
			for (int i = 0; i < PIETRE_INIZIALI; i++) {
				pietreAttuali.add(elemento);
			}
		}
	}
	
	/**
	 * Calcola il numero di pietre iniziali.
	 * @return il numero di pietre iniziali, int.
	 */
	 private static int calcPietreIniziali() {
	    	if(((2 * Giocatore.MAX_TAMAGOLEM * Tamagolem.MAX_PIETRE) % Equilibrio.getElementi().length) == 0)
	    		return ((2 * Giocatore.MAX_TAMAGOLEM * Tamagolem.MAX_PIETRE) / Equilibrio.getElementi().length);

	    	return (((2 * Giocatore.MAX_TAMAGOLEM * Tamagolem.MAX_PIETRE) / Equilibrio.getElementi().length) + 1);
	    }

	
	/**
	 * Esegue l'attacco e in base al suo esito ricalcola il valore di vita dei Tamagolem.
	 * @return le informazioni su quanto è accaduto durante l'attacco, String.
	 */
	 public String avviaAttacco() {
		Elementi[] attacchi = new Elementi[NUM_GIOCATORI];
		Integer potenza;
		String feedback = "";
        
        for (int i = 0; i < NUM_GIOCATORI; i++) {
        	attacchi[i] = this.giocatori[i].getTamaAttivo().attacca();
        	feedback += "Il tamagolem di " + this.giocatori[i].getUsername() + " scaglia un attacco di tipo " + attacchi[i] + "!\n\n";
        }
        potenza = Equilibrio.trovaPotenza(attacchi);
        
        if(potenza == null || potenza == 0) {
        	 feedback += "Nessun danno!\n";
        	 return feedback;
        }
        
        if(potenza > 0 ) {
        	this.giocatori[1].getTamaAttivo().setVita(this.giocatori[1].getTamaAttivo().getVita() - potenza);
        	feedback += "Il Tamagolem di " + this.giocatori[1].getUsername() + " ha subito " + potenza + " danni!\n";
        	
        	if (this.giocatori[1].getTamaAttivo().isMorto()) {
        		feedback += "Il Tamagolem di " + this.giocatori[1].getUsername() + " è sconfitto!\n\n";
        	}
        	return feedback;
        }
        potenza = -potenza;
        
    	this.giocatori[0].getTamaAttivo().setVita(this.giocatori[0].getTamaAttivo().getVita() - potenza);
    	feedback += "Il Tamagolem di " + this.giocatori[0].getUsername() + " ha subito " + potenza + " danni!\n";
    	
    	if (this.giocatori[0].getTamaAttivo().isMorto()) {
    		feedback += "Il Tamagolem di " + this.giocatori[0].getUsername() + " è sconfitto!\n\n";
    	}
    	return feedback;
	}

	public ArrayList <Elementi> getPietreAttuali() {
		return this.pietreAttuali;
	}
	
	public Giocatore[] getGiocatori() {
		return this.giocatori;
	}
}




	

