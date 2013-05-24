import java.util.List;
import java.util.ArrayList;
import es.upm.babel.cclib.*;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	List<List<Integer>> eventListeners;
	
	public GestorDeEventosMonitor(){
		eventListeners = new ArrayList<List<Integer>>(N_OBSERVADORES);
		
		for( int i=0; i<N_OBSERVADORES; i++ ){
			eventListeners.add(i, new ArrayList<Integer>());
		}
	}
	
	@Override
	public void emitir(int eid){
		List<Integer> eventList = eventListeners.get(eid);
		
		for( int pid : eventList ){
			// Depsertar a pid.
			
		}
	}

	@Override
	public void subscribir(int pid, int eid) {
		List<Integer> eventList = eventListeners.get(eid);
		eventList.add(pid);
	}

	@Override
	public void desubscribir(int pid, int eid) {
		List<Integer> eventList = eventListeners.get(eid);
		eventList.remove(pid);
	}

	@Override
	public int escuchar(int pid) {
		
		return 0;
	}

}
