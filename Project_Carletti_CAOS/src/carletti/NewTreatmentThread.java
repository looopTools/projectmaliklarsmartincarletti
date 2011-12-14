package carletti;

public class NewTreatmentThread implements Runnable{

	public NewTreatmentThread(){
		
	}

	@Override
	public void run() {
		NextTreatment nt = new NextTreatment(24);
	}
}
