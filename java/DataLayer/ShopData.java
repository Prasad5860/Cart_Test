package DataLayer;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
	public List<Product> getprd(String ct) {
		try {
			if ("all".equals(ct)) {
				return get();
			}
			int id = Integer.parseInt(ct);
			Connection con = dbCon.initialize();
			PreparedStatement ps = con.prepareCall("select * from H_products where pcat_id = ?");
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

	@Override
	public List<Product> priceGet(String cat, String srt) {
		// TODO Auto-generated method stub

		Connection cn = dbCon.initialize();
		try {
			PreparedStatement ps = null;
			String sql = "select * from H_products order by price";
			System.out.println(cat + " " + srt);
			if (!"all".equals(cat)) {
				sql = "select * from H_products where pcat_id=? order by price";
			}
			if ("hight".equals(srt)) {
				sql += " DESC";
			}
			System.out.println("Query  " + sql);
			ps = cn.prepareStatement(sql);
			if (!"all".equals(cat)) {
				ps.setInt(1, Integer.parseInt(cat));
			}
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
			PreparedStatement ps = con.prepareCall("select pcat_id from  ProductCategoryWiseServiceableregions\r\n"
					+ "inner join ServiceableRegions on ProductCategoryWiseServiceableRegions.reg_id = ServiceableRegions.reg_id\r\n"
					+ "where ServiceableRegions.pin_from<=? and ServiceableRegions.pin_to>=?");
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
				ps = con.prepareCall("select * from H_products where pcat_id = ?");
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
		try {
			Connection cn = dbCon.initialize();
			CallableStatement cs = null;
			CallableStatement ct = null;
			for (Product product : p) {
				int id = product.getProductId();
				System.out.println("Product id "+id);
				int pact = product.getProductCategory();
				System.out.println(pact);
				cs = cn.prepareCall("{? = call GetGSTForProduct(?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setInt(2, id);
				cs.execute();
				ct = cn.prepareCall("{? = call GetDiscount(?)}");
				ct.registerOutParameter(1,Types.NUMERIC);
				ct.setInt(2, pact);
				ct.execute();
				int gst = cs.getInt(1);
				BigDecimal discount = ct.getBigDecimal(1);
				System.out.println("Discount "+discount);
				product.setGst(gst);
				product.setDiscount(discount.doubleValue());
			}
			return p;
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
			p.setProductCategory(rs.getInt(5));
			p.setProductImageId(rs.getString(6));
			//System.out.println(p.getProductCategory());
			lp.add(p);
		}
		return lp;
	}

}