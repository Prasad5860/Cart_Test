package Controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import BLL.calculate;
import DAO.DataOject;
import DAO.conatractOb;
import Modal.Product;

/**
 * Servlet implementation class chekOut
 */
@WebServlet("/chekOut")
public class chekOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public chekOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		List<Product> ag = cb.totalGST(Items);

		System.out.println(ag.get(0).getGst());

		double tot_price = cb.getPriceAll(ag);
		System.out.println(tot_price);
		double shippingCharge = calculate.cal(tot_price);

		HttpSession session = request.getSession();
		session.setAttribute("Products", ag);
		session.setAttribute("Price", tot_price);
		session.setAttribute("shipCharge", shippingCharge);

		// request.getRequestDispatcher("check.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
