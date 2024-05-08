package DAO;

import java.util.List;

import Modal.Catogery;
import Modal.Product;

public interface conatractOb {
	List<Product> get();

	List<Product> getprd(int id);

	List<Catogery> cat_get();

	List<Product> priceGet(int l, int h);

	List<Product> validatePin(int cat, int pin);

	List<Product> totalGST(List<Product> p);

	double getPriceAll(List<Product> p);
}
