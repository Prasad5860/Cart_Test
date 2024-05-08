<%@ page import="java.util.List" %>
<%@ page import="Modal.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Display Products</title>
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
        <h2>Total Price: <%= (Double)session.getAttribute("Price")+(Double)session.getAttribute("shipCharge") %></h2>
        
        
    </div>
</body>
</html>
