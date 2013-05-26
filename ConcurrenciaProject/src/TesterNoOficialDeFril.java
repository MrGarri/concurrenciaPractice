public class TesterNoOficialDeFril {
	
	public static void main(String args[]){
		
		GestorDeEventosMonitor gem = new GestorDeEventosMonitor();
		
		Observador obs[] = new Observador[GestorDeEventos.N_OBSERVADORES];
		for(int i=0; i<obs.length; i++){
			obs[i] = new Observador(gem, i);
			obs[i].start();
		}
		
		Emisor ems[] = new Emisor[GestorDeEventos.N_OBSERVADORES];
		for(int i=0; i<obs.length; i++){
			ems[i] = new Emisor(gem, i);
			ems[i].start();
		}
		
	}

}
