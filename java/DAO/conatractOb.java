package DAO;
import java.util.List;

import Modal.Catogery;
import Modal.Product;

public interface conatractOb {

	List<Product> get();

	List<Product> getprd(String ct);

	List<Catogery> cat_get();

	List<Product> priceGet(String cat, String srt);

	List<Product> validatePin(int cat, int pin);

	List<Product> totalGST(List<Product> p);
}
