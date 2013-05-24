import es.upm.babel.cclib.ConcIO;

public class Emisor extends Thread
{
  private GestorDeEventos ge;
  private int eid;

  public Emisor(GestorDeEventos ge,
                int eid) {
    this.ge = ge;
    this.eid = eid;
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(1000 * (eid + 1));
      } catch (Exception ex) {
        ConcIO.printfnl("excepción capturada: " + ex);
        ex.printStackTrace();
      }
      ConcIO.printfnl("Emitiendo evento " + eid);
      ge.emitir(eid);
    }
  }
}
