package carletti;

public class LarsTest {

	public static void main(String[] args){
//		NextTreatment nt = new NextTreatment(26);
		Thread ntt = new Thread(new NewTreatmentThread());
		Thread ntt2 = new Thread(new NewTreatmentThread());
		
		ntt.start();
		ntt2.start();
	}
}
