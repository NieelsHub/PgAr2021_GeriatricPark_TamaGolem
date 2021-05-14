package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.HashMap;
import java.util.Random;

/**
 * Modellizza un sistema di elementi in equilibrio tra loro.
 * @author Nicol Stocchetti
 */
public class Equilibrio {
	private static Elementi[] elementi = Elementi.values();
	private static HashMap <Elementi[],Integer> grafo = new HashMap<>();
	private static Elementi[][] chiaviOrdinate = new Elementi[Equilibrio.calcoloCombinazioniElementi(Equilibrio.elementi)][2];
	
	
	public static HashMap<Elementi[],Integer> getGrafo() {
		return grafo;
	}
	
	public static Elementi[] getElementi() {
		return elementi;
	}

	public static void setElementi(Elementi[] elementi) {
		Equilibrio.elementi = elementi;
		Equilibrio.chiaviOrdinate = new Elementi[Equilibrio.calcoloCombinazioniElementi(Equilibrio.elementi)][2];
	}
	
	/**
	 * In base al numero di elementi calcola quante possibili combinazioni esisteranno tra loro.
	 * @param elementi il set di elementi da considerare per il calcolo, Elementi[].
	 * @return il numero di combinazioni possibili, int.
	 */
	private static int calcoloCombinazioniElementi(Elementi[] elementi) {
		int numeroCombinazioni = 0;
		int numeroElementi = elementi.length;
		
		for (int i = 1; i < numeroElementi; i++) {
			numeroCombinazioni += numeroElementi - i;
		}
		
		return numeroCombinazioni;
	}
	
	/**
	 * Calcola l'equilibrio tra tutti gli elementi.
	 * @return un grafo che rappresenta l'equilibrio, HashMap <Elementi[],Integer>.
	 */
	public static HashMap <Elementi[],Integer> genera () {
		Elementi[] coppia = new Elementi[2];
		int indice = 0;
		int potenza = 0;
		int sommaPotenze = 0;
		Random rand = new Random();
		
		Equilibrio.grafo.clear();
		Equilibrio.chiaviOrdinate = new Elementi[Equilibrio.calcoloCombinazioniElementi(Equilibrio.elementi)][2];
		
		//Creo le coppie che rappresentano le interazioni tra i vari elementi, inizializzate a 0.
		for (int j, i = 0; i < Equilibrio.elementi.length; i++) {
			coppia[0] = Equilibrio.elementi[i];
			for (j = i + 1; j < Equilibrio.elementi.length; j++) {
				coppia[1] = Equilibrio.elementi[j];
				
				Equilibrio.chiaviOrdinate[indice] = coppia.clone();
				Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[indice] , potenza);
				
				indice++;
			}	
		}
		
		//Scorro ogni elemento
		for (Elementi elemento : Equilibrio.elementi) {
			sommaPotenze = 0;
			//Calcolo la somma delle interazioni già assegnate precedentemente all'elemento corrente (sommo tutti i
			//valori relativi a quell'elemento, se un'interazione è ancora da decidere vale 0, come da inizializzazione,
			//e quindi non influisce sulla somma totale).
			for (Elementi elemento2 : Equilibrio.elementi) {
				if (!elemento.equals(elemento2)) {
					sommaPotenze += Equilibrio.trovaPotenza(new Elementi[]{elemento, elemento2});
				}
			}
			//Assegno un valore casuale alle interazioni di questo elemento con gli altri elementi, considerando solo
			//quelle coppie in cui questo elemento sta al primo posto (le interazioni in cui invece sta al secondo posto
			//nella coppia saranno già state assegnate precedentemente, per costruzione).
			for (int i = 0; i < Equilibrio.chiaviOrdinate.length; i++) {
				if (Equilibrio.chiaviOrdinate[i][0].equals(elemento)) {
					try {
						//se dopo la coppia attuale ce n'è un'altra sempre relativa a questo elemento, assegno il valore
						//di questa coppia in maniera casuale.
						if (Equilibrio.chiaviOrdinate[i+1][0].equals(elemento)) {
							do {
								potenza = rand.nextInt((2*Tamagolem.MAX_VITA + 1))-Tamagolem.MAX_VITA;
								potenza = potenza / (Equilibrio.elementi.length-(Equilibrio.elementi.length/2));
							}while (potenza == 0);//La potenza dell'interazione non può essere = 0!
							sommaPotenze += potenza;
							Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i], potenza);
						}
						else {
							//Arrivati all'ultima coppia, assegno il suo valore in modo da rendere la somma totale delle interazioni = 0.
							//Affinché questo si verifichi, l'interazione tra gli elementi dell'ultima coppia dovrà essere uguale e contraria
							//alla somma parziale delle interazioni precedenti, calcolata fino alla penultima coppia.
							//Se però questa somma parziale è già = 0, significa che per mantenere anche la somma totale = 0 dovremmo
							//mettere come ultimo valore di interazione per forza 0 e non va bene perché un'interazione tra elementi
							//diversi non può mai essere nulla!
							//In questo caso, allora, si ricalcola il valore della penultima coppia, fino a quando non si ha una somma
							//parziale !=0. A questo punto si potrà quindi equilibrarla nel modo corretto, per ottenere una somma totale = 0,
							//attribuendo all'ultima coppia un valore uguale e contrario alla somma parziale ottenuta fin'ora, che sarà
							//correttamente diverso da zero.
							while (sommaPotenze == 0) {
								sommaPotenze -= Equilibrio.grafo.get(Equilibrio.chiaviOrdinate[i-1]);
								do {
									potenza = rand.nextInt((2*Tamagolem.MAX_VITA + 1))-Tamagolem.MAX_VITA;
									potenza = potenza / (Equilibrio.elementi.length-(Equilibrio.elementi.length/2));
								}while (potenza == 0);
								sommaPotenze += potenza;
								Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i-1], potenza);
							}
							Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i], -sommaPotenze);
						}
					}
					//Se nel controllo dell'elemento successivo ottengo una IndexOutOfBoundsException, significa che ci si trova
					//attualmente sull'ultimo elemento dell'array di coppie.
					catch (ArrayIndexOutOfBoundsException e) { 
						
						while (sommaPotenze == 0) {
							//Anche in questo caso bisogna modificare l'ultimo valore casuale per evitare somme = 0, con un ragionamento
							//analogo al precedente... ma, dato che siamo sull'ultimo elemento, esso ha una sola coppia ancora assegnabile e 
							//quindi deve essere per forza usata per bilanciare i valori calcolati precedentemente. Il valore casuale ricalcolabile
							//più vicino è il penultimo valore assegnato all'elemento precedente. Dovrò quindi ricontrollare sia la somma sull'ultima
							//coppia dell'elemento precedente che su quello corrente fino ad ottenere valori soddisfacenti per entrambi.
							
							sommaPotenze -= -Equilibrio.grafo.get(Equilibrio.chiaviOrdinate[i-2]);//elimino dal conteggio il valore problematico che porta a zero la somma parziale
							
							do {
								do {
									potenza = rand.nextInt((2*Tamagolem.MAX_VITA + 1))-Tamagolem.MAX_VITA;
									potenza = potenza / (Equilibrio.elementi.length-(Equilibrio.elementi.length/2));
								}while (potenza == 0);
								Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i-2], potenza); //modifico il valore problematico
								
								
								//ricalcolo il valore della coppia finale del penultimo elemento (i-1).
								Elementi elementoPrecedente = Equilibrio.chiaviOrdinate[i-1][0];
								int sommaPotenzeElementoPrecedente = 0;
								
								Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i-1], 0);//Metto a zero il valore da ricalcolare
								
								for (Elementi elemento2 : Equilibrio.elementi) {
									if (!elementoPrecedente.equals(elemento2)) {
										sommaPotenzeElementoPrecedente += Equilibrio.trovaPotenza(new Elementi[]{elementoPrecedente, elemento2});
									}
								}
								Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i-1], -sommaPotenzeElementoPrecedente);//nuovo valore ricalcolato
								
							}while(Equilibrio.grafo.get(Equilibrio.chiaviOrdinate[i-1]) == 0); //(i-1) != 0
							
							sommaPotenze += -Equilibrio.grafo.get(Equilibrio.chiaviOrdinate[i-2]);//reinserisco il valore ricalcolato nella somma parziale
						}
						
						Equilibrio.grafo.put(Equilibrio.chiaviOrdinate[i], -sommaPotenze);
					}
				}
			}
		}	
		return Equilibrio.grafo;
	}
	
	/**
	 * Fornito un array contenente due elementi, ritorna la potenza dell'azione del primo elemento sul secondo; se nel grafo
	 * non c'è alcuna interazione corrispondente alla coppia di elementi fornita ritorna null. 
	 * @param valore la coppia di elementi, Elementi[].
	 * @return il valore dell'interazione, oppure null se l'interazione è inesistente, Integer.
	 */
	public static Integer trovaPotenza(Elementi[] valore) {
		Elementi[] chiave;
		
		chiave = Equilibrio.trovaChiave(valore);
		
		if (chiave != null)
			return Equilibrio.grafo.get(chiave);
		
		chiave = new Elementi[valore.length];
		
		for (int i = 0; i < valore.length; i++) {
			chiave[i] = valore[valore.length - 1 - i];
		}
		
		chiave = Equilibrio.trovaChiave(chiave);
		
		if (chiave != null)
			return -Equilibrio.grafo.get(chiave);
		
		return null;	
	}
	
	/**
	 * Fornito un array contenente due elementi, cerca e ritrona la chiave corrispondente a questa coppia (stessi elementi nello
	 * stesso ordine) all'interno della hash map che rappresenta il grafo delle interazioni tra elementi, se non trova alcuna
	 * corrispondenza ritorna null. 
	 * 
	 * @param valore la coppia di elementi, Elementi[].
	 * @return la chiave corrispondente oppure null, Elementi[].
	 */
	private static Elementi[] trovaChiave(Elementi[] valore) {
		//https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap
		for (Elementi[] chiave : Equilibrio.grafo.keySet()) {
		    if (chiave[0].equals(valore[0]) && chiave[1].equals(valore[1])) 
		    	return chiave;
		}
		return null;
	}
	
	/**
	 * Ritorna una stringa che illustra le attuali relazioni tra i vari elementi dell'equilibrio.
	 * @return la descrizione delle relazioni fra tutti gli elementi, String.
	 */
	public static String mostra() {
		int potenza;
		String grafico = "";
		
		for (Elementi[] chiave : Equilibrio.chiaviOrdinate) {
			potenza = Equilibrio.grafo.get(chiave);
			grafico += String.format("%s su %s => %d%n%n", chiave[0], chiave[1], potenza);
		}
		//N.B: in questo caso non si poteva usare semplicemente una TreeMap perché la chiave non è comparable!
		//https://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap
		//https://stackoverflow.com/questions/922528/how-to-sort-map-values-by-key-in-java
		
		return grafico;
	}
	
}
