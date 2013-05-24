import org.jcsp.lang.CSProcess;

public class ObservadorCSP extends Observador implements CSProcess {
  public ObservadorCSP(GestorDeEventos ge, int pid) {
    super(ge,pid);
  }
}
