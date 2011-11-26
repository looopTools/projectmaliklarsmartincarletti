package carletti;

import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;
import carletti.service.Service;
import carletti.service.jpa.ServiceJpa;

public class JpaTEST {
	
	public static void main(String[] args){
		Treatment t1 = Service.createTreatment("Red chocolate MMs");
		t1.createSubTreatment("1st drying", 1000*60*30, 1000*60*32, 1000*60*35);
		t1.createSubTreatment("2nd drying", 500, 750, 1000);
		t1.createSubTreatment("3rd drying", 1250, 1300, 1500);
		Product p1 = Service.createProduct("Red Chocolate MMs", "Info about red chocolate MMs", t1);
		SubProduct sp1 = Service.createSubProduct("Foobar", p1);
		ServiceJpa.getInstance().storeSubProduct(sp1);
	}

}

