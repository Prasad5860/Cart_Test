<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="Modal.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Display Products</title>
    <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    
    <style>
  body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        header {
            background-color: #333;
            color: white;
            padding: 10px 20px;
            text-align: center;
        }
        nav {
		  display: inline-block;
		}
		nav button {
		  background-color: #333;
		  color: white;
		  border: none;
		  padding: 10px 20px;
		  margin-right: 20px;
		  cursor: pointer;
		}
		nav button:hover {
		  background-color: #555;
		  border-radius: 10px;
		}
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            border-radius:10px;
            border:3px;
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-color: 2px solid #96D4D4;
    
            text-align:center;
           
        }
        th {
            background-color: #f2f2f2;
        }
        .pymt{
        margin-left:70%;
        }

        #razorpayBtn {
            padding: 10px;
            cursor: pointer;
            border-radius: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            display: block;
            width: 70%;
            text-align: center;
            font-size:20px;
        }
        #razorpayBtn:hover {
            background-color: #0056b3;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* CSS for image size */
        .product-image {
            width: 100px; /* Set the width as needed */
            height: 100px; /* Set the height as needed */
        }



        /* Form styling */
       
    </style>
</head>
<body>
	<header>
        <h1>Shopping Cart</h1>
        <nav>
	      <button onclick="openHomePage()">Home</button>
	      <button onclick="openCart()">Cart</button>
        </nav>
      </header>
    <div class="container">
        <h2>Order Summary</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Product Image</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Original Price</th>
                    <th>Discount(%)</th>
                    <th>FinalPrice</th>
                    <th>GST Added</th>
                    <th>ShippingCharge(inc GST)</th>
                </tr>
            </thead>
            <tbody>
            <% 
            List<Product> products = (List<Product>) session.getAttribute("Products");
            double ship = 0.0;
            for (Product product : products) { %>
                <tr>
                    <td><img src="<%= product.getProductImageId() %>" alt="Product Image" class="product-image"></td>
                    <td><%= product.getProductName() %></td>
                    <td><%= product.getquanatity() %></td>
                    <td><%= product.getProductPrice() %></td>
                    <td><%= product.getDiscount() %></td>
                    <td><%= product.getFinal() %></td>
                    <td><%= product.getGst() %>%</td>
                    <td><%= product.getShippingCharge() %></td>
                </tr>
            <% 
            ship+=product.getShippingCharge();
            }
            DecimalFormat df = new DecimalFormat("#.##");
            %>
            </tbody>
        </table>
        
        <div class="pymt">
            <p id ="prc"><b>Total Products Price: <%= (Double)session.getAttribute("Price")%> </b></p>
	        <p><b>Shipping Charge: <%= df.format(ship)%></b></p>
	        <p><b>Total Amount : <%= (Double)session.getAttribute("Price") + Double.parseDouble(df.format(ship)) %> </b></p>
	        <div class="btn"><button id="razorpayBtn">Pay Now</button></div>
        </div>
   
    </div>
    
    <script>
    var razorpayBtn = document.getElementById('razorpayBtn');
    var amt = <%=  (Double)session.getAttribute("Price") + Double.parseDouble(df.format(ship)) %>
    razorpayBtn.addEventListener('click', function () {
        var options = {
            "key": "rzp_test_OQhYYrloWXvOJ5",
            "amount": amt*100, // amount in paise
            "currency": "INR",
            "name": "Ekart Shopping",
            "description": "Shopping App",
            "image": "https://your-website.com/logo.png",
            "handler": function (response) {
                // Handle success response
                alert('Payment successful: ' + response.razorpay_payment_id);
                
                
            },
            "prefill": {
                "name": "M harish",
                "email": "customer@example.com",
                "contact": "9346237652"
            },
            "theme": {
                "color": "#007bff"
            }
        };

        var rzp = new Razorpay(options);
        rzp.open();
    });
    
    function openCart(){
  	  window.open("cart.html", "_self");
  	}
  	function openHomePage() {
  	  window.open("home.html", "_self");
  	}
</script>
    
</body>
</html>