import org.jcsp.lang.*;
import org.jcsp.util.*;


public class GestorDeEventosCSP implements GestorDeEventos, CSProcess {
	
	public GestorDeEventosCSP() {
		
		

	}
 
	@Override
	public void emitir(int eid) {

		
	}

	@Override
	public void subscribir(int pid, int eid) {
		
		
	}

	@Override
	public void desubscribir(int pid, int eid) {
		
		
	}

	@Override
	public int escuchar(int pid) {
		
		return 0;
	}

	@Override
	public void run() {
		
		Any2AnyCallChannel o = new Any2AnyCallChannel();
		
	}

	

}
