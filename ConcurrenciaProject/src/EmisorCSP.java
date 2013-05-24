import org.jcsp.lang.CSProcess;

public class EmisorCSP extends Emisor implements CSProcess {
  public EmisorCSP (GestorDeEventos ge, int eid) {
    super(ge,eid);
  }
}
