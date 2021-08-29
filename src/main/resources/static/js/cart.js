//   ANGULAR
var app = angular.module("myApp", []);
app.controller("proCtrl", function ($scope) {
  $scope.carts = [
    {
      id: 1,
      name: "Baebody Eye Cream Corporis",
      photo: "pro1.jpg",
      price: 68.0,
    },
  ];
  $scope.cart = [];
  $scope.cart = JSON.parse(localStorage.getItem("cart"));

  $scope.total = 0;

  for (var i = 0; i < $scope.cart.length; i++) {
    $scope.total += $scope.cart[i].price * $scope.cart[i].quantity;
  }

  $scope.minus = function (id) {
    var check = false;

    for (var i = 0; i < $scope.cart.length; i++) {
      if ($scope.cart[i].id == id) {
        if ($scope.cart[i].quantity > 1) {
          $scope.cart[i].quantity--;
          localStorage.setItem("cart", JSON.stringify($scope.cart));
        } else {
          $scope.cart.splice(i, 1);
          localStorage.setItem("cart", JSON.stringify($scope.cart));
        }
      }
    }
    $scope.total = 0;
    for (var i = 0; i < $scope.cart.length; i++) {
      $scope.total += $scope.cart[i].price * $scope.cart[i].quantity;
    }

  };

  $scope.add = function (id) {
    var check = false;

    for (var i = 0; i < $scope.cart.length; i++) {
      if ($scope.cart[i].id == id) {
        $scope.cart[i].quantity++;
        localStorage.setItem("cart", JSON.stringify($scope.cart));
      }
    }
    $scope.total = 0;
    for (var i = 0; i < $scope.cart.length; i++) {
      $scope.total += $scope.cart[i].price * $scope.cart[i].quantity;
    }
  };

});

// for Button Toggle COD or Credit Card
// 'use strict';


window.onload = function(){

var switchBtnRight = document.querySelector(".switch-button-case.right");
var switchBtnLeft = document.querySelector(".switch-button-case.left");
var activeSwitch = document.querySelector(".active");

// document.getElementById("COD").style.display = "none";
document.getElementById("PAYPAL").style.display = "none";
document.getElementById('voucher-row').style.display='none';


function switchLeft() {
    switchBtnRight.classList.remove("active-case");
    switchBtnLeft.classList.add("active-case");
    activeSwitch.style.left = "0%";
}

function switchRight() {
    switchBtnRight.classList.add("active-case");
    switchBtnLeft.classList.remove("active-case");
    activeSwitch.style.left = "50%";
}
switchBtnLeft.addEventListener(
    "click",
    function () {
        switchLeft();
        document.getElementById("COD").style.display = "block";
        document.getElementById("PAYPAL").style.display = "none";
    },
    false
);
switchBtnRight.addEventListener(
    "click",
    function () {
        switchRight();
        document.getElementById("COD").style.display = "none";
        document.getElementById("PAYPAL").style.display = "block";
    },
    false
);

$('#check-voucher').click(()=> {
  var voucher = document.getElementById('voucher').value;
  if(voucher == '' || voucher.length < 5){
    document.getElementById('errorMsg').innerHTML='<h6 class="text-danger">Invalid Voucher !</h6> '; 
  }else{
    
    document.getElementById('errorMsg').innerHTML='<h6 class="text-secondary">Checking...</h6> '; 
   $.ajax({
        url: "http://localhost:8080/api/v1/fa/voucher/search?code="+voucher,
        type:"GET",
        contentType:"application/json; charset=utf-8",
        success : function(response){
            var total = parseFloat(document.getElementById('amountTotal').innerText);
            var discount = total * (parseInt(response.discount)/100);
            var afterDiscount = total - discount;
            total = afterDiscount.toString();
            document.getElementById('discount').innerText = response.discount;
            document.getElementById('amountTotal').innerText = afterDiscount.toString();
            document.getElementById('checkout-button').disabled = false;
            document.getElementById('errorMsg').innerHTML='<h5 class="text-uppercase text-success">'+response.content+'</h5>'
        },
        error: function(request, status, error){
          var responseText = request.responseText;
            if(responseText == '') document.getElementById('errorMsg').innerHTML='<h6 class="text-danger">Invalid Voucher !</h6> ';

            else document.getElementById('errorMsg').innerHTML='<h6 class="text-danger">'+responseText+'</h6> ';

           
        }
        
    })
  }
})

$('#checkout-button').click(() => {
  if(document.getElementById('user-login') == null){
      Swal.fire({
          imageUrl: '../img/logoblack.jpg',
          imageHeight: 120,
          imageWidth: 120,
          title: 'LOGIN FOR CHECKOUT',
          text: 'You need login for checkout',
          showDenyButton: true,
          confirmButtonText: `Let's Login`,
          denyButtonText: `Continue Shopping`,
          confirmButtonColor: '#f9af47',
          denyButtonColor: '#f9af47'
        }).then((result) => {
          /* Read more about isConfirmed, isDenied below */
          if (result.isConfirmed) {
              window.location.href="/login";
          } else if (result.isDenied) {
              window.location.href="/product";
          }
        })
    }else{
          var jsonObj = jsonObjectCheckout();
          $.ajax({
            url: "http://localhost:8080/api/v1/order/checkout",
            type:"POST",
            contentType:"application/json; charset=utf-8",
            data: jsonObj,
            success : function(response){
              Swal.fire({
                imageUrl: '../img/logoblack.jpg',
                imageHeight: 120,
                imageWidth: 120,
                title: 'SUCCESSFULLY!',
                text: 'You can check order at Member Dashboard',
                showDenyButton: true,
                confirmButtonText: `Member Dashboard`,
                denyButtonText: `Continue Shopping`,
                confirmButtonColor: '#f9af47',
                denyButtonColor: '#f9af47'
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    window.location.href="/mDashboard";
                } else if (result.isDenied) {
                    window.location.href="/product";
                }
              })
            }
            
        })
    
    }
})

}


function jsonObjectCheckout(){
      var arrayItem = [];
      var table = document.getElementById('table-cart');
      var rowLength = table.rows.length;

      for(var i=1; i<rowLength; i++){
      var row = table.rows[i];
      var name = row.cells[0].querySelector('.name').innerText;
      var quantity = row.cells[2].querySelector('.js-qty-num').value
      var totalOfItem = row.cells[3].querySelector('.totalOfItem').innerText;
      var json =  {'nameItem': name, 'quantity':quantity, 'totalOfItem': totalOfItem};
      arrayItem.push(json);
      };
      var email = document.getElementById('user-login').innerText;
      var fullName = document.getElementById('fullName').value;
      var phone = document.getElementById('phoneNumber').value;
      var address = document.getElementById('address').value;
      var note = document.getElementById('note').value;
      var deliveryType = document.querySelector("input[name=radioZ]:checked").value;
      var totalAmount = document.getElementById('amountTotal').innerText;

      var voucher ="";
      if($('#no-voucher').is(':checked')){
        voucher = document.getElementById('voucher').value;
      }else voucher = "";
      
      var jsonObj = JSON.stringify({'email':email,'name' : fullName, 'phone' : phone, 'address': address,'note':note,'delivery':deliveryType,'voucher':voucher,"total": totalAmount,'items': arrayItem });
      console.log(jsonObj);
      return jsonObj;
}

 


function haveVoucher(obj){
  if($(obj).is(':checked')){
    document.getElementById('voucher-row').style.display='block';
    document.getElementById('checkout-button').disabled = true
  }else{
    document.getElementById('voucher-row').style.display='none';
    document.getElementById('checkout-button').disabled = false;
  }
}// End window.onload