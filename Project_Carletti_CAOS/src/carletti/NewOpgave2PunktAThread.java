package carletti;

public class NewOpgave2PunktAThread implements Runnable{

	public NewOpgave2PunktAThread(){
		
	}

	@Override
	public void run() {
		Opgave2PunktA nt = new Opgave2PunktA(24);
	}
}
