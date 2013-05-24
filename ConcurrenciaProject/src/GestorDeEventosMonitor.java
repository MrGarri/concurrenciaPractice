import java.util.List;
import java.util.ArrayList;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	List<List<Integer>> processListeners = new ArrayList<List<Integer>>(N_OBSERVADORES);
	@Override
	public void emitir(int eid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribir(int pid, int eid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desubscribir(int pid, int eid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int escuchar(int pid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
