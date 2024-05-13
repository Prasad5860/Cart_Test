package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Modal.*;
import com.google.gson.reflect.TypeToken;

import BLL.calculate;

import com.google.gson.Gson;

import DAO.DataOject;
import DAO.conatractOb;

/**
 * Servlet implementation class chekOut
 */
public class chekOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Gson gson = new Gson();
		conatractOb cb = null;
		DataOject d = new DataOject();
		cb = d.getobject();
		String arr = request.getParameter("arr");
		List<Product> Items = gson.fromJson(arr, new TypeToken<List<Product>>() {
		}.getType());
		System.out.println(Items);
		List<Product> ag = cb.totalGST(Items);

		System.out.println(ag.get(0).getGst());
		System.out.println(ag.get(0).getDiscount());

		double tot_price = calculate.getPriceAll(ag);
		System.out.println(tot_price);
		double shippingCharge = calculate.cal(tot_price);

		List<Product> products = calculate.getCharges(ag, shippingCharge, tot_price);

		HttpSession session = request.getSession();
		session.setAttribute("Products", products);
		session.setAttribute("Price", tot_price);
		session.setAttribute("shipCharge", shippingCharge);
	}

}
