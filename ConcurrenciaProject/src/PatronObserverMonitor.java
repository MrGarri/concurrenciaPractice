public class PatronObserverMonitor
{
  public static void main(String argv[]) {
    Emisor[] emisor = new Emisor[GestorDeEventos.N_EVENTOS];
    Observador[] observador = new Observador[GestorDeEventos.N_OBSERVADORES];
    GestorDeEventos ge = new GestorDeEventosMonitor();
    for (int eid = 0; eid < GestorDeEventos.N_EVENTOS; eid++) {
      emisor[eid] = new Emisor(ge,eid);
      emisor[eid].start();
    }
    for (int pid = 0; pid < GestorDeEventos.N_OBSERVADORES; pid++) {
      observador[pid] = new Observador(ge,pid);
      observador[pid].start();
    }
    try {
      for (int eid = 0; eid < GestorDeEventos.N_EVENTOS; eid++)
        emisor[eid].join();
      for (int pid = 0; pid < GestorDeEventos.N_OBSERVADORES; pid++)
        observador[pid].join();
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
