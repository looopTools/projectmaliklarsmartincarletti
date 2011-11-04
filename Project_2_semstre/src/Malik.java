
public class Malik {
	private String navn;
	
	public Malik(String navn){
		this.navn = navn;
	}
	
	public void printName(){
		System.out.println(navn);
	}
	
	public static void main(String[] args){
		Malik m = new Malik("Malik");
		m.printName();
	}
}
