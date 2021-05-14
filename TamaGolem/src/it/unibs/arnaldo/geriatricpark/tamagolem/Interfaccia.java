package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

/**
 * Contiene i metodi da usare per comunicare con l'utente attraverso il terminale e le funzioni specifiche del programma principale.
 * @author Geriatric Park (Nicol Stocchetti, Wiam Nasr Allah, Mohamed Nassar).
 */
public class Interfaccia {
	public static final String TITOLO = " ______  ______   __    __   ______                                \r\n"
			+ "/\\__  _\\/\\  __ \\ /\\ \"-./  \\ /\\  __ \\                               \r\n"
			+ "\\/_/\\ \\/\\ \\  __ \\\\ \\ \\-./\\ \\\\ \\  __ \\                              \r\n"
			+ "   \\ \\_\\ \\ \\_\\ \\_\\\\ \\_\\ \\ \\_\\\\ \\_\\ \\_\\                             \r\n"
			+ "    \\/_/  \\/_/\\/_/ \\/_/  \\/_/ \\/_/\\/_/                             \r\n"
			+ "                                                                   \r\n"
			+ "                   ______   ______   __       ______   __    __    \r\n"
			+ "                  /\\  ___\\ /\\  __ \\ /\\ \\     /\\  ___\\ /\\ \"-./  \\   \r\n"
			+ "                  \\ \\ \\__ \\\\ \\ \\/\\ \\\\ \\ \\____\\ \\  __\\ \\ \\ \\-./\\ \\  \r\n"
			+ "                   \\ \\_____\\\\ \\_____\\\\ \\_____\\\\ \\_____\\\\ \\_\\ \\ \\_\\ \r\n"
			+ "                    \\/_____/ \\/_____/ \\/_____/ \\/_____/ \\/_/  \\/_/ \r\n";
	
	public static final String SOTTOTITOLO = "\t-_-_-_-_-_BATTAGLIE ELEMENTALI_-_-_-_-_-\n\n";
	public static final String CHIEDI_NOME = "Giocatore numero %d, inserisci il tuo nome: ";
	public static final String RIVELAZIONE_EQUILIBRIO = "\nL'equilibrio dell'universo in questa partita ha assunto la seguente configurazione: ";
	public static final String RIVELAZIONE_VINCITORE = "\nVince %s!!!\n";
	public static final String TITOLO_MENU_USCITA = "COSA VUOI FARE?";
	public static final String[] SCELTE_USCITA = {"Nuova partita"};
	public static final String INIZIO_EVOCAZIONE = "%n%s, evoca un Tamagolem!%n%n";
	public static final String FINE_EVOCAZIONE = "\nIl Tamagolem di %s scende in campo!\n";
	public static final String INIZIO_SCONTRO = "\nINIZIA LO SCONTRO!!!\n";
	public static final String PIETRE_DISPONIBILI = "Pietre disponibili: ";
	public static final String SELEZIONE_PIETRA = "Quale pietra desideri selezionare?";
	public static final String FINE_SCONTRO = "LO SCONTRO FINISCE QUI!\n";
	public static final String ATTACCO = "\n\nTAMAGOLEM, ATTACCATE!\n\n";
	public static final String USCITA = "\nArrivederci!";
	public static final String PROSSIMO_TURNO = "\tPremi invio per continuare... ";
	public static final String PIETRE_UGUALI = "\tATTENZIONE: non puoi scegliere le pietre nello stesso ordine del Tamagolem avversario, altrimenti lo scontro prosegue all'infinito!";
	
	/**
	 * Gestisce la creazione dei giocatori, la generazione dello scontro e la messa in campo dei primi tamagolem.
	 * @return la battaglia in corso, it.unibs.arnaldo.geriatricpark.tamagolem.Scontro.
	 */
	public static Scontro avviaPartita() {
		Giocatore[] giocatori = new Giocatore[Scontro.NUM_GIOCATORI];
		Scontro scontro;
		
		System.out.println(TITOLO);
		System.out.println();
		System.out.println(SOTTOTITOLO);
		System.out.println();
		
		for (int i = 0; i < Scontro.NUM_GIOCATORI; i++) {
			giocatori[i] = creazioneGiocatore(i+1);
		}
		
		scontro = new Scontro(giocatori);
		
		
		for (int i = 0; i < Scontro.NUM_GIOCATORI; i++) {
			evocazione(scontro, scontro.getGiocatori()[i]);
		}
		
		System.out.println(INIZIO_SCONTRO);
		
		return scontro;
	}
	
	/**
	 * Avvia un'evocazione e gestisce la scelta da parte dell'utente delle pietre da fornire al nuovo tamagolem.
	 * @param scontro la battaglia all'interno della quale viene compiuta l'evocazione, it.unibs.arnaldo.geriatricpark.tamagolem.Scontro.
	 * @param giocatore il giocatore che effettua l'evocazione, it.unibs.arnaldo.geriatricpark.tamagolem.Giocatore.
	 */
	private static void evocazione(Scontro scontro, Giocatore giocatore) {
		Queue <Elementi> pietre = new ArrayDeque <Elementi>();
		ArrayList <Elementi> listaPietre; //lo uso per copiarci dentro la deque in modo da poter accedere ai suoi elementi senza per forza toglierli dalla deque originale. 
		ArrayList <Elementi> listaPietreAltriGiocatori;
		boolean flagPietreUguali = false;
		
		Elementi pietraScelta;
		do {
			System.out.printf(INIZIO_EVOCAZIONE, giocatore.getUsername());
			for(int j = 0; j < Tamagolem.MAX_PIETRE; j++) {
				pietraScelta = sceltaPietre(scontro);
				pietre.offer(pietraScelta);
				scontro.getPietreAttuali().remove(pietraScelta);
			}
			listaPietre = new ArrayList <Elementi> (pietre);
			for (Giocatore altroGiocatore : scontro.getGiocatori()) {
				if (!giocatore.equals(altroGiocatore) && altroGiocatore.getTamaAttivo() != null) {
					flagPietreUguali = true;
					listaPietreAltriGiocatori = new ArrayList <Elementi>(altroGiocatore.getTamaAttivo().getPietre());
					for (int i = 0; i < listaPietre.size(); i++) {
						if (!listaPietre.get(i).equals(listaPietreAltriGiocatori.get(i))) {
							flagPietreUguali = false;
							break;
						}
					}
					if (flagPietreUguali) {
						while (!pietre.isEmpty()) {
							scontro.getPietreAttuali().add(pietre.poll());
						}
						System.err.println(PIETRE_UGUALI);
						break;
					}
				}
			}
		} while(flagPietreUguali);
		
		giocatore.evocaTamagolem(pietre);
		System.out.printf(FINE_EVOCAZIONE, giocatore.getUsername());
		System.out.println(Tamagolem.NORMAL_SPRITE);
	}
	
	/**
	 * Gestisce l'inserimento dei dati da parte dell'utente per la creazione di un giocatore.
	 * @param numero il numero del giocatore, int.
	 * @return il giocatore creato, it.unibs.arnaldo.geriatricpark.tamagolem.Giocatore.
	 */
	private static Giocatore creazioneGiocatore(int numero) {
		String username = "";
		
		username = InputDati.leggiStringaNonVuota(String.format(CHIEDI_NOME, numero));
		System.out.println();
		
		return new Giocatore(username);
	}
	
	/**
	 * Mostra a video il vincitore della partita.
	 * @param vincitore il vincitore, it.unibs.arnaldo.geriatricpark.tamagolem.Giocatore.
	 */
	private static void mostraVincitore(Giocatore vincitore) {
		System.out.println(String.format(RIVELAZIONE_VINCITORE, vincitore.getUsername()));
	}
	
	/**
	 * Mostra a video l'equilibrio di elementi vigente durante la partita.
	 */
	private static void mostraEquilibrio() {
		System.out.println(RIVELAZIONE_EQUILIBRIO);
		System.out.println();
		System.out.println(Equilibrio.mostra());
	}
	
	/**
	 * Gestisce tramite un menù la possibilità di uscire dal gioco o iniziare una nuova partita.
	 * @return la scelta effettuata, int.
	 */
	public static boolean menuUscita() {
		MyMenu uscita = new MyMenu(TITOLO_MENU_USCITA, SCELTE_USCITA, true);
		
		if(uscita.scegli() == 0) {
			System.out.println(USCITA);
			return true;
		}
		return false;
	}
	
	/**
	 * Presenta un menù che permette al giocatore di scegliere una pietra tra quelle ancora a disposizione per la
	 * partita corrente.
	 * @param partita la partita con cui ci si vuole interfacciare, it.unibs.arnaldo.geriatricpark.tamagolem.Scontro.
	 * @return la pietra selezionata, it.unibs.arnaldo.geriatricpark.tamagolem.Elementi.
	 */
	private static Elementi sceltaPietre(Scontro partita) {
		int numeroPietre = 0;
		int scelta;
		ArrayList <Elementi> pietreSelezionabili = new ArrayList <Elementi>();
		String[] stringaPietre = new String[Elementi.values().length];
		String opzione;
		
		System.out.println(/*PIETRE_DISPONIBILI*/);
		
		for (int i = 0; i < Elementi.values().length; i++) {
			opzione = Elementi.values()[i].name() + ":";
			numeroPietre = 0;
			
			for (Elementi pietra : partita.getPietreAttuali()) {
				if (pietra.equals(Elementi.values()[i])) {
					numeroPietre++;
				}
			}
			
			opzione += " x"+numeroPietre;
			stringaPietre[i] = opzione;
			
			if (numeroPietre > 0) {
				pietreSelezionabili.add(Elementi.values()[i]);
			}
		}
		
		MyMenu menuz = new MyMenu(PIETRE_DISPONIBILI, stringaPietre, false);
		menuz.stampaMenu(false);
		
		System.out.println();
		
		stringaPietre = new String[pietreSelezionabili.size()];
		
		for (int i = 0; i < pietreSelezionabili.size(); i++) {
			stringaPietre[i] = pietreSelezionabili.get(i).name();
		}
		
		MyMenu menu = new MyMenu(SELEZIONE_PIETRA, stringaPietre, false);
		
		scelta = menu.scegli();
		
		return pietreSelezionabili.get(scelta-1);
	}
	
	/**
	 * Mostra a video il flusso di gioco per un turno.
	 * @param partita la battaglia di cui devo richiamare lo svolgimento, it.unibs.arnaldo.geriatricpark.tamagolem.Scontro.
	 * @return true se la partita è terminata in questo turno, altrimenti false, boolean.
	 */
	public static boolean mostraTurno(Scontro partita) {
		String attacco = "";
		
		System.out.println(ATTACCO);
		
		attacco = partita.avviaAttacco();
		
		System.out.println(attacco);
		
		if (partita.getGiocatori()[0].getTamaAttivo().isMorto()) {
			if (partita.getGiocatori()[0].getTamagolem().size() <= 0) {
				terminaPartita(partita.getGiocatori()[1]);
				return true;
			}
			evocazione(partita, partita.getGiocatori()[0]);
		}
		
		if (partita.getGiocatori()[1].getTamaAttivo().isMorto()) {
			if (partita.getGiocatori()[1].getTamagolem().size() <= 0) {
				terminaPartita(partita.getGiocatori()[0]);
				return true;
			}
			evocazione(partita, partita.getGiocatori()[1]);
		}
		InputDati.leggiStringa(PROSSIMO_TURNO);
		
		return false;
	}
	
	/**
	 * Mostra a video le informazioni di interesse per la conclusione della partita corrente.
	 * @param vincitore il vincitore di questa partita, it.unibs.arnaldo.geriatricpark.tamagolem.Giocatore.
	 */
	public static void terminaPartita(Giocatore vincitore) {
		System.out.println(FINE_SCONTRO);
		mostraVincitore(vincitore);
		mostraEquilibrio();
	}

}
