$(document).ready(function() {

    // CATEGORY OPTIONS

    $.ajax({
      url:"http://localhost:8080/Ecart_APP/Catogeries",
      type: "GET",
      dataType:"json",
      success: function(data){
        $.each(data,function(ind,ele){
          let el = '<option value ="'+ele.id+'">'+ele.name+'</option>'
          $('#category-select').append(el)
        })
      }
    })

    // ALL PRODUCTS

    $.ajax({
      url: "http://localhost:8080/Ecart_APP/allProducts",
      type: "GET",
      dataType: "json",
      success: function(data) {
        if ($('#category-select').is(":hidden")) {
            $('#category-select').show()
        }
        $.each(data, function(index, product) {
          console.log( product.productName);
          var productDiv = '<div class="product">' +
                              '<div class="product-info">' +
                                  '<img src="' + product.productImageId + '" alt="' + product.productName + '">' +
                                '<h2>' + product.productName + '</h2>' +
                                '<p>Price: ' + product.productPrice + '</p>' +
                                '<button onclick="addToCart(\'' + product.productName + '\', ' + product.productPrice + ', \'' + product.productImageId + '\', ' + product.productId + ', \'' + product.productCategory + '\')">Add to Cart</button>' +
                              '</div>' +
                            '</div>';
          $('.Product_list').append(productDiv);
        });
      },
      error: function(xhr, status, error) {
        console.error("Error fetching products: " + error);
      }
    });

      //CATEGORY WISE PRODUCTS

  
    $('#category-select').change(function(){
      $('.Product_list').empty();
      let cat_id =  $('#category-select').val()
      $.ajax({
        url:"http://localhost:8080/Ecart_APP/CateWise",
        type: "POST",
        data:{
          id: cat_id
        },
        success: function(data) {
                $.each(data, function(index, product) {
                console.log( product.productName);
                var productDiv = '<div class="product">' +
                                    '<div class="product-info">' +
                                        '<img src="' + product.productImageId + '" alt="' + product.productName + '">' +
                                        '<h2>' + product.productName + '</h2>' +
                                        '<p>Price: $' + product.productPrice + '</p>' +
                                        '<button onclick="addToCart(\'' + product.productName + '\', ' + product.productPrice + ', \'' + product.productImageId + '\', ' + product.productId + ', \'' + product.productCategory + '\')">Add to Cart</button>' +
                                    '</div>' +
                                    '</div>';
                $('.Product_list').append(productDiv);
                });
            },
        error: function(xhr, status, error) {
            console.error("Error fetching products: " + error);
        }
      })
    });
  
})



function addToCart(name, price, img, id,categoryId) {
    let pincode = prompt("Enter Pincode:");
    if (!pincode) {
        return; // User canceled or didn't enter a pincode
    }

    // Check if the item category is available for the entered pincode
    $.ajax({
        url: "http://localhost:8080/Ecart_APP/PinValidate",
        type: 'GET',
        data: { categoryId: categoryId, pincode: pincode },
        success: function(response) {
            if (response) {
                // Item is available, proceed to add to cart
                let arr = JSON.parse(localStorage.getItem("arr")) || [];
                let found = false;

                arr.forEach(item => {
                    if (item.productId === id) {
                        item.quantity = (item.quantity || 1) + 1;
                        found = true;
                    }
                });

                if (!found) {
                    arr.push({ productId: id, productName: name, productPrice: price, productImageId: img, quantity: 1 });
                }
                localStorage.setItem("arr", JSON.stringify(arr));
                alert("Item Added to cart")
            } else {
                alert("Item not available for the entered pincode and category.");
            }
        },
        error: function(xhr, status, error) {
            console.error('Error checking availability:', error);
            alert("An error occurred. Please try again.");
        }
    });
}

function openCart(){
    window.open("cart.html", "_self");
}
function openHomePage() {
    window.open("home.html", "_self");
}


