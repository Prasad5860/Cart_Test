package DataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.conatractOb;
import Modal.Catogery;
import Modal.Product;

public class ShopData implements conatractOb {

	@Override
	public List<Product> get() {
		// TODO Auto-generated method stub

		try {
			Connection con = dbCon.initialize();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from h_products");
			return getdata(rs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Product> getprd(int id) {
		try {
			Connection con = dbCon.initialize();
			PreparedStatement ps = con.prepareCall("select * from H_products where pcatid = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			return getdata(rs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Catogery> cat_get() {
		// TODO Auto-generated method stub

		Connection cn = dbCon.initialize();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from H_pcategory");
			List<Catogery> lc = new ArrayList<>();
			while (rs.next()) {
				Catogery c = new Catogery();
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				lc.add(c);
			}

			return lc;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static List<Product> getdata(ResultSet rs) throws SQLException {
		List<Product> lp = new ArrayList<>();
		while (rs.next()) {
			Product p = new Product();
			p.setProductId(rs.getInt(1));
			p.setProductName(rs.getString(2));
			p.setProductPrice(rs.getDouble(3));
			p.setHSNCode(rs.getString(4));
			p.setProductCategory(rs.getString(5));
			p.setProductImageId(rs.getString(6));
			lp.add(p);
		}
		return lp;
	}

	@Override
	public List<Product> priceGet(int l, int h) {
		// TODO Auto-generated method stub

		Connection cn = dbCon.initialize();
		try {
			Connection con = dbCon.initialize();
			PreparedStatement ps = con.prepareCall("select * from H_products where price>? and price<=?");
			ps.setInt(1, l);
			ps.setInt(2, h);
			ResultSet rs = ps.executeQuery();
			return getdata(rs);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public List<Product> validatePin(int cat, int pin) {
		// TODO Auto-generated method stub
		try {
			int f = 0;
			Connection con = dbCon.initialize();
			PreparedStatement ps = con.prepareCall("select prct_id from  ProductCategoryWiseServiceableRegions\r\n"
					+ "inner join ServiceableRegions_192 on ProductCategoryWiseServiceableRegions.srrg_id = ServiceableRegions_192.srrg_id\r\n"
					+ "where ServiceableRegions_192.srrg_pinfrom<=? and ServiceableRegions_192.srrg_pinto>=?");
			ps.setInt(1, pin);
			ps.setInt(2, pin);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (cat == rs.getInt(1)) {
					System.out.println("inner" + cat);
					f = 1;
					break;
				}
			}
			if (f == 1) {
				ps = con.prepareCall("select * from H_products where pcatid = ?");
				ps.setInt(1, cat);
				rs = ps.executeQuery();
				return getdata(rs);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Product> totalGST(List<Product> p) {
		// TODO Auto-generated method stub
		try {
			Connection cn = dbCon.initialize();
			PreparedStatement ps = null;
			ResultSet rs = null;
			for (Product product : p) {
				int gst;
				int id = product.getProductId();
				ps = cn.prepareStatement("select hsn_code from H_products where pid = ?");
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					String code = rs.getString(1);
					ps = cn.prepareStatement("select gst from H_hsncode where hsn_code = ?");
					ps.setString(1, code);
					rs = ps.executeQuery();
					if (rs.next()) {
						gst = rs.getInt(1);
						product.setGst(gst);
					}
				}
			}
			return p;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public double getPriceAll(List<Product> p) {
		// TODO Auto-generated method stub

		double price = 0.0;
		for (Product product : p) {
			price += product.getProductPrice() + product.getGst();
		}
		return price;
	}

}
