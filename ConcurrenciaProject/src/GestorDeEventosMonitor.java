import java.util.ArrayList;
import java.util.List;

import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.Monitor.Cond;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	Monitor monitor;
	Cond[] observerConditions;
	List<ArrayList<Integer>> eventSubscriptors;
	
	int eventTrigger;
	
	public GestorDeEventosMonitor(){
		monitor = new Monitor();
		observerConditions = new Cond[ N_OBSERVADORES + 1 ];
		eventSubscriptors = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<=observerConditions.length; i++){
			observerConditions[i] = monitor.newCond();
		}
		
		for(int i=0; i<=N_EVENTOS; i++){
			eventSubscriptors.add( i, new ArrayList<Integer>() );
		}
	}
	
	@Override
	public void emitir(int eid){
		monitor.enter();
		
		eventTrigger = eid;
		List<Integer> subscriptors = eventSubscriptors.get(eid);
		
		for( int pid : subscriptors ){
			observerConditions[pid].signal();
		}
		
		monitor.leave();
	}

	@Override
	public void subscribir(int pid, int eid) {
		monitor.enter();
		
		List<Integer> subscriptors = eventSubscriptors.get(eid);
		subscriptors.add(pid);
		
		monitor.leave();
	}

	@Override
	public void desubscribir(int pid, int eid) {
		monitor.enter();
		
		List<Integer> subscriptors = eventSubscriptors.get(eid);
		subscriptors.remove(pid);
		
		monitor.leave();
	}

	@Override
	public int escuchar(int pid) {
		observerConditions[pid].await();
		
		monitor.enter();
		int res = eventTrigger;
		monitor.leave();
		
		return res;
	}

}
