public interface GestorDeEventos
{
  public static final int N_EVENTOS = 3;
  public static final int N_OBSERVADORES = 5;

  public void emitir(int eid);
  public void subscribir(int pid, int eid);
  public void desubscribir(int pid, int eid);
  public int escuchar(int pid);
}
