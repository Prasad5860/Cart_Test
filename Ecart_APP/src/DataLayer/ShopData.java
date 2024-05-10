package DataLayer;

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
	public List<Product> priceGet(String cat, String srt) {
		// TODO Auto-generated method stub

		Connection cn = dbCon.initialize();
		try {
			PreparedStatement ps = null;
			String sql = "select * from H_products order by price";
			System.out.println(cat + " " + srt);
			if (!"all".equals(cat)) {
				sql = "select * from H_products where pcatid=? order by price";
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
		try {
			Connection cn = dbCon.initialize();
			CallableStatement cs = null;
			for (Product product : p) {
				int id = product.getProductId();
				cs = cn.prepareCall("{? = call GetGSTForProductt_203(?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setInt(2, id);
				cs.execute();
				int gst = cs.getInt(1);
				product.setGst(gst);
			}
			return p;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
