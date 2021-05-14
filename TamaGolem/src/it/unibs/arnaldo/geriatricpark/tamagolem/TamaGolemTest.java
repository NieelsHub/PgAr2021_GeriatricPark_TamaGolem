package it.unibs.arnaldo.geriatricpark.tamagolem;

import java.util.ArrayList;
import java.util.Collections;

import it.unibs.fp.mylib.MyMenu;

//Classe eseguibile usata per i test, DA IGNORARE!
public class TamaGolemTest {
	public static void main(String[] args) {
		Scontro partita;
		
		Equilibrio.genera();
		//partita = Interfaccia.avviaPartita();
		
		//Interfaccia.terminaPartita(new Giocatore(""));
		
		
		
		
		
		
		
		
		System.out.println(Equilibrio.mostra());
		
		
		int sommaPotenze = 0;
		int potenza;
		for (Elementi elemento : Equilibrio.getElementi()) {
			sommaPotenze = 0;
			System.out.println(elemento+": ");
		
			for (Elementi elemento2 : Equilibrio.getElementi()) {
				if (!elemento.equals(elemento2)) {
					potenza = Equilibrio.trovaPotenza(new Elementi[]{elemento, elemento2});
					System.out.printf("%+d ", potenza);
					sommaPotenze += potenza;
				}
			}
			System.out.printf("= %+d%n%n", sommaPotenze);
		}		
		
		/*
		List sortedKeys=new ArrayList(yourMap.keySet());
		Collections.sort(sortedKeys);
	 	*/
		
		/*
		ArrayList <Elementi[]> prova = new ArrayList(Equilibrio.getGrafo().keySet());
		
		for (Elementi[] chiave : prova) {
			System.out.println(chiave[0]);
		}
		
		ArrayList <String> provaString = new ArrayList();
		for (Elementi[] chiave : prova) {
			provaString.add(chiave[0].name());
		}
		
		System.out.println("\n\n\n");
		
		for (String chiave : provaString) {
			System.out.println(chiave);
		}
		
		Collections.sort(provaString);
		System.out.println("\n\n\n");
		
		for (String chiave : provaString) {
			System.out.println(chiave);
		}
		*/
		/*
		System.out.println(Equilibrio.getGrafo().get(new Elementi[]{Elementi.ROSSO, Elementi.GIALLO}));
		System.out.println(Equilibrio.getGrafo().containsKey(new Elementi[]{Elementi.ROSSO, Elementi.GIALLO}));
		
		
		System.out.println("\nROSSO e GIALLO:");
		System.out.println(Equilibrio.getGrafo().get(Equilibrio.trovaChiave(new Elementi[]{Elementi.ROSSO, Elementi.GIALLO})));
		System.out.println(Equilibrio.getGrafo().containsKey(Equilibrio.trovaChiave(new Elementi[]{Elementi.ROSSO, Elementi.GIALLO})));
		System.out.println(Equilibrio.trovaPotenza(new Elementi[]{Elementi.ROSSO, Elementi.GIALLO}));
		
		System.out.println("\nGIALLO e ROSSO:");
		System.out.println(Equilibrio.getGrafo().get(Equilibrio.trovaChiave(new Elementi[]{Elementi.GIALLO, Elementi.ROSSO})));
		System.out.println(Equilibrio.getGrafo().containsKey(Equilibrio.trovaChiave(new Elementi[]{Elementi.GIALLO, Elementi.ROSSO})));
		System.out.println(Equilibrio.trovaPotenza(new Elementi[]{Elementi.GIALLO, Elementi.ROSSO}));
		
		System.out.println("\nGIALLO e GIALLO:");
		System.out.println(Equilibrio.getGrafo().get(Equilibrio.trovaChiave(new Elementi[]{Elementi.GIALLO, Elementi.GIALLO})));
		System.out.println(Equilibrio.getGrafo().containsKey(Equilibrio.trovaChiave(new Elementi[]{Elementi.GIALLO, Elementi.GIALLO})));
		System.out.println(Equilibrio.trovaPotenza(new Elementi[]{Elementi.GIALLO, Elementi.GIALLO}));
		*/

	}
}
