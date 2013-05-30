import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.Monitor.Cond;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	private volatile Monitor monitor;
	private volatile Cond[] observerConditions;
	private volatile boolean[][] subscriptions;
	private volatile boolean[][] porEscuchar;
	
	public GestorDeEventosMonitor(){
		monitor = new Monitor();
		
		observerConditions = new Cond[ N_OBSERVADORES ];
		subscriptions = new boolean[N_EVENTOS][N_OBSERVADORES];
		porEscuchar = new boolean[N_EVENTOS][N_OBSERVADORES];
		
		for(int i=0; i<observerConditions.length; i++){
			observerConditions[i] = monitor.newCond();
		}
	}
	
	@Override
	public void emitir(int eid){
		monitor.enter();
		
		for(int i=0; i<subscriptions[eid].length; i++){
			if(subscriptions[eid][i]){
				porEscuchar[eid][i] = true;
				observerConditions[i].signal();
			}
		}
		
		monitor.leave();
	}

	@Override
	public void subscribir(int pid, int eid) {
		monitor.enter();
		
		subscriptions[eid][pid] = true;
		
		monitor.leave();
	}

	@Override
	public void desubscribir(int pid, int eid) {
		monitor.enter();
		
		subscriptions[eid][pid] = false;
		porEscuchar[eid][pid] = false;
		
		monitor.leave();
	}

	@Override
	public int escuchar(int pid) {
		
		try{
			monitor.enter();
			
			for(int i=0; i<subscriptions.length; i++){
				if(subscriptions[i][pid] && porEscuchar[i][pid]){
					porEscuchar[i][pid] = false;
					return i;
				}
			}
			
			observerConditions[pid].await();

			int res=0;
			for(int i=0; i<subscriptions.length; i++){
				if(subscriptions[i][pid] && porEscuchar[i][pid]){
					porEscuchar[i][pid] = false;
					res = i;
				}
			}
			return res;
		} finally {
			monitor.leave();
		}
	}

}
