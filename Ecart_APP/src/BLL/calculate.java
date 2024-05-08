package BLL;

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

}
