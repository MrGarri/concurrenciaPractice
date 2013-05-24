import es.upm.babel.cclib.ConcIO;

public class Observador extends Thread
{
  private GestorDeEventos ge;
  private int pid;

  public Observador(GestorDeEventos ge,
                    int pid) {
    this.ge = ge;
    this.pid = pid;
  }

  public void run() {
    while (true) {
      int n = 2, i, eid = 0;
      for (i = 0; i < n; i++) {
        eid = (pid + i) % GestorDeEventos.N_EVENTOS;
        ConcIO.printfnl("Subscrito a evento " + eid);
        ge.subscribir(pid, eid);
      }
      for (i = 0; i < n; i++) {
        ConcIO.printfnl("Escuchando..." + eid);
        eid = ge.escuchar(pid);
        ConcIO.printfnl("Evento " + eid + " escuchado!");
        ge.desubscribir(pid,eid);
        ConcIO.printfnl("Desubscrito de evento " + eid);
      }
      try {
        Thread.sleep(499);
      } catch (Exception ex) {
        ConcIO.printfnl("excepción capturada: " + ex);
        ex.printStackTrace();
      }
    }
  }
}
