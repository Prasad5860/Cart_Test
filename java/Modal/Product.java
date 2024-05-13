package Modal;

public class Product {
	private int productId;
	private String productName;
	private double productPrice;
	private int productCategory;
	private String HSNCode;
	private String productImageId;
	private String quantity;
	private double gst;
	private double shippingCharge;
	private double discountPercent;
	private double finalPrice;
	
	public void setFinal(double finalPrice) {
		this.finalPrice=finalPrice;
	}
	
	public double getFinal() {
		return finalPrice;
	}
	
	public void setDiscount(double discountPercnt) {
		this.discountPercent=discountPercnt;
	}

	
	public double getDiscount() {
		return discountPercent;
	}
	
	public void setHSNCode(String HSNCode) {
		this.HSNCode = HSNCode;
	}

	public String getHSNCode() {
		return HSNCode;
	}

	public String getquanatity() {
		return quantity;
	}

	public void setquantity(String qnt) {
		this.quantity = qnt;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public double getGst() {
		return gst;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shipping) {
		this.shippingCharge = shipping;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public int getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(int productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(String productImageId) {
		this.productImageId = productImageId;
	}
}