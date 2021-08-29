var app = angular.module("myApp", []);
app.controller("proCtrl", function ($scope) {
    $scope.carts = [
        {
          id: 1,
          name: "Shoes test 1",
          photo: "pro1.jpg",
          price: 68.0,
          quantity: 1
        },
        {
            id: 2,
            name: "Shoes test 2",
            photo: "pro1.jpg",
            price: 70.0,
            quantity: 1
          },
          {
            id: 3,
            name: "Shoes test 3",
            photo: "pro1.jpg",
            price: 70.0,
            quantity: 1
          },
          {
            id: 4,
            name: "Shoes test 4",
            photo: "pro1.jpg",
            price: 70.0,
            quantity: 1
          },
          {
            id: 5,
            name: "Shoes test 5",
            photo: "pro1.jpg",
            price: 70.0,
            quantity: 1
          },
      ];
      $scope.cart = [];
      $scope.cart = JSON.parse(localStorage.getItem("cart"));

      $scope.postProduct = function(){
        localStorage.setItem("cart", JSON.stringify($scope.carts));
      }
});