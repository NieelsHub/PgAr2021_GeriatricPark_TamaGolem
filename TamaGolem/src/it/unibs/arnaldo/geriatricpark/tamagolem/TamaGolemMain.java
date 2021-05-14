package it.unibs.arnaldo.geriatricpark.tamagolem;

public class TamaGolemMain {

	public static void main(String[] args) {
		Scontro partita;
		boolean vittoria;
		
		do {
			Equilibrio.genera();
			
			vittoria = false;
			
			partita = Interfaccia.avviaPartita();
			
			do {
				vittoria = Interfaccia.mostraTurno(partita);
				
			} while(!vittoria);
			
		} while(!Interfaccia.menuUscita());
	}
}

