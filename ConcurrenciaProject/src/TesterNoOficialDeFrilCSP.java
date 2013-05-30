import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;


public class TesterNoOficialDeFrilCSP {
	
	public static void main(String args[]){
		
		GestorDeEventosCSP gem = new GestorDeEventosCSP();
		
		ObservadorCSP obs[] = new ObservadorCSP[GestorDeEventos.N_OBSERVADORES];
		for(int i=0; i<obs.length; i++){
			obs[i] = new ObservadorCSP(gem, i);
			obs[i].start();
		}
		
		EmisorCSP ems[] = new EmisorCSP[GestorDeEventos.N_OBSERVADORES];
		for(int i=0; i<obs.length; i++){
			ems[i] = new EmisorCSP(gem, i);
			ems[i].start();
		}
		
	}

}
