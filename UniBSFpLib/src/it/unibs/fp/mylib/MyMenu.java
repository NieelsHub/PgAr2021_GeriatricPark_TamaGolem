package it.unibs.fp.mylib;

/*
 * Rappresenta un menù testuale generico, a più voci, selezionabili tramite la digitazione di un intero.
 * La scelta per uscire è sempre associata al valore 0, se presente.
 */
public class MyMenu {
	
	final private static int CORNICE = 3;
	final private static String VOCE_USCITA = "0 - Esci";
	final private static String RICHIESTA_INSERIMENTO = "\nDigita il numero dell'opzione desiderata\n> ";
	
	private String titolo;
	private String [] voci;
	private boolean uscita;
	
	/**
	 * Il metodo costurttore del menù.	
	 * @param titolo il titolo del menù, String.
	 * @param voci le varie scelte che il menù dovrà presentare a video, String[].
	 */
	public MyMenu(String titolo, String [] voci, boolean uscita) {
		this.titolo = titolo;
		this.voci = voci;
		this.uscita = uscita;
	}
	
	/**
	 * Presenta a video il menù.
	 */
	public void stampaMenu(boolean numeri) {
		System.out.print("\t");
		for (int i = 0; i < titolo.length()+2*CORNICE; i++) {
			System.out.print("-");
		}
		System.out.println();
	
		System.out.print("\t");
		for (int i = 0; i < CORNICE; i++) {
			System.out.print(" ");
		}
		System.out.println(titolo);
		
		System.out.print("\t");
		for (int i = 0; i < titolo.length()+2*CORNICE; i++) {
			System.out.print("-");
		}
		System.out.println();
		
		for (int i=0; i<voci.length; i++) {
			System.out.print("\t ");
			if(numeri) {
				System.out.print((i+1) + " - ");
			}
			System.out.println(voci[i]);
		}
		
		if (uscita) {
			System.out.println();
			System.out.println("\t " + VOCE_USCITA);
			System.out.println();
		}
	}
	
	/**
	 * Visualizza il menù e gestisce il corretto inserimento dell'opzione scelta da parte dell'utente, controllando che
	 * essa sia ammissibile per valore e formato.
	 * @return l'intero associato alla scelta effettuata dall'utente, int.
	 */
	public int scegli() {
		int min = 0;
		
		if(uscita)
			min = 0;
		else
			min = 1;
		
		stampaMenu(true);
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, min, voci.length);	 
	}
	  
  
  //////////////ROBA MIA
  /*
  private static Scanner scan = new Scanner(System.in);
  /**
	 * Rappresenta un menù (con delle alternative selezionabili tramite la digitazione di un intero) e gestisce il corretto
	 * inserimento dell'opzione scelta da parte dell'utente.
	 * @param messaggioMenu il menù di scelta che si vuole presentare a video, String.
	 * @param minValore il minimo intero corrispondente a una delle opzioni selezionabili, int.
	 * @param maxValore il massimo intero corrispondente a una delle opzioni selezionabili, int.
	 * @return l'intero corrispondente all'opzione scelta, int.
	 */
  /*
	public static int menuAzioniMio(String messaggioMenu, int minValore, int maxValore) {
	//Il controllo sul dato corretto è ispirato a quello usato in InputDati di UniBSFpLib.
		boolean uscita = false;
		int scelta = 0;
		
		System.out.println(messaggioMenu);
		
		do {
		    try
		    {
				scelta = scan.nextInt();
		       
				while(scelta < minValore || scelta > maxValore) {
					System.err.println("\nScelta non disponibile! Reinserire: ");
					scelta = scan.nextInt();
				}
		       
				uscita = true;
		    
			}
			catch (InputMismatchException e) {
				System.err.println("\nScelta non disponibile! Reinserire: ");
				scan.next();
			}
			
		} while (!uscita);

		return scelta;
	}
	*/
	
}

