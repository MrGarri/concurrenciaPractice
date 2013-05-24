import org.jcsp.lang.*;

public class PatronObserverCSP
{
  public static void main(String argv[]) {
    CSProcess[] threads =
      new CSProcess[GestorDeEventos.N_EVENTOS
                    + GestorDeEventos.N_OBSERVADORES
                    + 1];
    CSProcess sistema;
    int p = 0;
    
    GestorDeEventosCSP ge = new GestorDeEventosCSP();
    threads[p++] = ge;
    try {
      for (int eid = 0; eid < GestorDeEventos.N_EVENTOS; eid++) {
        threads[p] = new EmisorCSP(ge, eid);
        p++;
      }
      for (int pid = 0; pid < GestorDeEventos.N_OBSERVADORES; pid++) {
        threads[p] = new ObservadorCSP(ge,pid);
        p++;
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

    sistema = new Parallel(threads);

    sistema.run();
  }
}
