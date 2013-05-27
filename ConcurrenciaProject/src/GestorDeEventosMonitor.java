import java.util.ArrayList;
import java.util.List;

import es.upm.babel.cclib.Monitor;
import es.upm.babel.cclib.Monitor.Cond;

public class GestorDeEventosMonitor implements GestorDeEventos {
	
	private final Monitor monitor;
	private final Cond[] observerConditions;
	private final List<ArrayList<Integer>> eventSubscriptors;
	
	private volatile int eventTrigger;
	
	public GestorDeEventosMonitor(){
		monitor = new Monitor();
		
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
			
			for( int pid : subscriptors ){
				eventTrigger = eid;
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
			subscriptors = new ArrayList<Integer>();
			eventSubscriptors.add(eid, (ArrayList<Integer>)subscriptors);
		}
		subscriptors.add(new Integer(pid));
		
		monitor.leave();
	}

	@Override
	public void desubscribir(int pid, int eid) {
		monitor.enter();
		
		try{
			List<Integer> subscriptors = eventSubscriptors.get(eid);
			subscriptors.remove(new Integer(pid));
		} catch(Exception e){}
		
		monitor.leave();
	}

	@Override
	public int escuchar(int pid) {
		try{
			monitor.enter();
			observerConditions[pid].await();
			return eventTrigger;
		} finally {
			monitor.leave();
		}
	}

}
