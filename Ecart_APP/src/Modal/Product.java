package Modal;

public class Product {
	private int productId;
	private String productName;
	private double productPrice;
	private String productCategory;
	private String HSNCode;
	private String productImageId;
	private String quantity;
	private double gst;

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

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(String productImageId) {
		this.productImageId = productImageId;
	}
}
