import java.util.ArrayList;
import java.util.List;

import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.Monitor.Cond;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	private final Monitor monitor;
	private final Cond[] observerConditions;
	private final List<ArrayList<Integer>> eventSubscriptors;
	
	private int eventTrigger;
	private final Cond notify;
	
	public GestorDeEventosMonitor(){
		monitor = new Monitor();
		notify = monitor.newCond();
		
		observerConditions = new Cond[ N_OBSERVADORES + 1 ];
		eventSubscriptors = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<observerConditions.length; i++){
			observerConditions[i] = monitor.newCond();
		}
	}
	
	@Override
	public void emitir(int eid){
		monitor.enter();
		
		try{
			List<Integer> subscriptors = eventSubscriptors.get(eid);
			eventTrigger = eid;
			
			for( int pid : subscriptors ){
				observerConditions[pid].signal();
			}
		} catch(IndexOutOfBoundsException e){}
		
		monitor.leave();
	}

	@Override
	public void subscribir(int pid, int eid) {
		monitor.enter();
		
		List<Integer> subscriptors;
		
		try{
			subscriptors = eventSubscriptors.get(eid);
		} catch(IndexOutOfBoundsException e){
			eventSubscriptors.add(eid, new ArrayList<Integer>());
			subscriptors = eventSubscriptors.get(eid);
		}
		subscriptors.add(pid);
		
		monitor.leave();
	}

	@Override
	public void desubscribir(int pid, int eid) {
		monitor.enter();
		
		try{
			List<Integer> subscriptors = eventSubscriptors.get(eid);
			subscriptors.remove(pid);
		} catch(Exception e){}
		
		monitor.leave();
	}

	@Override
	public int escuchar(int pid) {
		monitor.enter();
		
		observerConditions[pid].await();
		int res = eventTrigger;

		return res;
	}

}
