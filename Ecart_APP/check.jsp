<%@ page import="java.util.List" %>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }

        #razorpayBtn {
            padding: 10px;
            cursor: pointer;
            border-radius: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            display: block;
            width: 30%;
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
      </header>
    <div class="container">
        <h2>Order Summary</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Product Image</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>GST Added</th>
                </tr>
            </thead>
            <tbody>
            <% 
            List<Product> products = (List<Product>) session.getAttribute("Products");
            for (Product product : products) { %>
                <tr>
                    <td><img src="<%= product.getProductImageId() %>" alt="Product Image" class="product-image"></td>
                    <td><%= product.getProductName() %></td>
                    <td><%= product.getProductPrice() %></td>
                    <td><%= product.getGst() %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <h2>Shipping Charge: <%= session.getAttribute("shipCharge") %></h2>
        <h2 id ="prc">Total Price: <%= (Double)session.getAttribute("Price")+(Double)session.getAttribute("shipCharge") %></h2>
        
        <button id="razorpayBtn">Pay Now</button>
        
    </div>
    
    <script>
    var razorpayBtn = document.getElementById('razorpayBtn');
    var amt = <%= (Double)session.getAttribute("Price")+(Double)session.getAttribute("shipCharge") %>
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
                "name": "RamBabu",
                "email": "customer@example.com",
                "contact": "9965847103"
            },
            "theme": {
                "color": "#007bff"
            }
        };

        var rzp = new Razorpay(options);
        rzp.open();
    });
</script>
    
</body>
</html>
