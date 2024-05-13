package BLL;

import java.text.DecimalFormat;
import java.util.List;

import Modal.Product;

public class calculate {

	public static double cal(double amt) {

		if (amt <= 500) {
			return 10.0;
		} else if (amt > 500 && amt <= 1000) {
			return 60;
		} else if (amt > 1000 && amt <= 2000) {
			return 100;
		} else if (amt > 2000 && amt <= 5000) {
			return 150;
		} else if (amt > 5000 && amt <= 100000) {
			return 300;
		} else if (amt > 10000) {
			return 500;
		}

		return 0.0;
	}

	public static double getPriceAll(List<Product> p) {
		// TODO Auto-generated method stub

		double price = 0.0;
		for (Product product : p) {
			double discountvalue = (product.getDiscount())/100.00;
			double prc = ( product.getProductPrice()-(discountvalue*product.getProductPrice()));
			double finalPrice = Integer.parseInt(product.getquanatity())*(prc + (product.getGst()/100.00)*prc);
			product.setFinal(finalPrice);
			price += finalPrice;
			product.setGst(Integer.parseInt(product.getquanatity()) * product.getGst());
		}
		return price;
	}

	public static List<Product> getCharges(List<Product> ag, double shippAmount, double price) {

		for (Product pd : ag) {
			double prc = pd.getFinal();
			double ind = Display(prc, price, shippAmount, pd.getGst());
			pd.setShippingCharge(ind);
		}

		return ag;

	}

	public static double Display(double pdPrice, double price, double shipp, double d) {

		double per = (pdPrice / price) * 100;
		double pdship = (per / 100) * shipp;
		pdship += pdship * (d / 100);
		DecimalFormat dFormat = new DecimalFormat("#.##");
		return Double.parseDouble(dFormat.format(pdship));
	}

}