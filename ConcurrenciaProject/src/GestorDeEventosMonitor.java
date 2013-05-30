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
	
	/**
	 * <b>void</b> emitir (<b>int</b> eid)
	 * 
	 * <p>Emite el evento con id eid.
	 * Los procesos suscritos a este evento pasarán a escucharlo.</p>
	 */
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

	
	/**
	 * <b>void</b> suscribir (<b>int</b> pid, <b>int</b> eid)
	 * 
	 * <p>Pone a true el elemento en la matriz en la posición (eid, pid).
	 * Monitor asegura la exclusión mutua. </p>
	 */
	@Override
	public void subscribir(int pid, int eid) {
		monitor.enter();
		
		subscriptions[eid][pid] = true;
		
		monitor.leave();
	}

	
	/**
	 * <b>void</b> desuscribir (<b>int</b> pid, <b>int</b> eid)
	 * 
	 * <p>Pone a false el elemento en la matriz en la posición (eid, pid)
	 * Monitor asegura la exclusión mutua</p>
	 */
	@Override
	public void desubscribir(int pid, int eid) {
		monitor.enter();
		
		subscriptions[eid][pid] = false;
		porEscuchar[eid][pid] = false;
		
		monitor.leave();
	}

	/**
	 * <b>int</b> escuchar (<b>int</b> pid).
	 * 
	 * <p>Se devuelve la id del evento escuchado. El evento puede haber sido emitido antes 
	 * de ejecutar este método. En caso contrario, el proceso se queda esperando hasta que
	 * lo sea.</p>  
	 */
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
