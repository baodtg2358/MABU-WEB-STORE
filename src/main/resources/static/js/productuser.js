var app = angular.module("myApp", []);
app.controller("proCtrl", function ($scope, $http, $rootScope) {
    //SHOW CATEGORY
    const getAllCategory = () => {
        $http.get("http://localhost:8080/api/v1/fa/category").then(
            function successCallback(reponse) {
                $scope.list_categories = reponse.data;
                console.log(reponse.data);
            }
            ,
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }

    //SHOW PRODUCT
    const getAllProduct = () => {
        $http.get("http://localhost:8080/api/v1/fa/products").then(
            function successCallback(reponse) {
                $scope.list_products = reponse.data;
            }
            ,
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }

    getAllCategory();
    getAllProduct();

    // //UPDATE USER
    $scope.updateProduct = function (id) {

        var form = $(`#updateFormProduct${id}`)[0];

        // Create an FormData object
        var formData = new FormData(form);

        for (var value of formData.values()) {
            console.log(value);
        }

        //$http POST function
        $http({
            method: "PUT",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/fa/products",
            data: formData,
        }).then(
            function successCallback(response) {
                alert("Successfully!");
                location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };

    $scope.statusProduct = true;

    $scope.changeDisplayStatus = statusProduct => {
        $scope.statusProduct = statusProduct
    };

    $scope.cart = [];

    $scope.addItemToCart = function (product) {
        if (localStorage.getItem("cart") == null) {
            $scope.cart.push(product);
            localStorage.setItem("cart", JSON.stringify($scope.cart));
        } else {
            $scope.cart = [];
            $scope.cart = JSON.parse(localStorage.getItem("cart"));
            var repeat = false;
            for (var i = 0; i < $scope.cart.length; i++) {
                if ($scope.cart[i].id === product.id) {
                    repeat = true;
                    $scope.cart[i].quantity += 1;
                }
            }
            if (!repeat) {
                $scope.cart.push(product)
                $scope.cart[i].quantity = 1;
            }
            localStorage.setItem("cart", JSON.stringify($scope.cart));
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Your work has been saved',
                text: 'You can change quantity add Cart',
                showConfirmButton: false,
                timer: 2000
              })
             

        }
    }

    $scope.addItemToCartNow = function (product) {
        if (localStorage.getItem("cart") == null) {
            $scope.cart.push(product);
            localStorage.setItem("cart", JSON.stringify($scope.cart));
        } else {
            $scope.cart = [];
            $scope.cart = JSON.parse(localStorage.getItem("cart"));
            var repeat = false;
            for (var i = 0; i < $scope.cart.length; i++) {
                if ($scope.cart[i].id === product.id) {
                    repeat = true;
                    $scope.cart[i].quantity += 1;
                }
            }
            if (!repeat) {
                $scope.cart.push(product)
                $scope.cart[i].quantity = 1;
            }
            localStorage.setItem("cart", JSON.stringify($scope.cart));
            window.location.href="/cart"
        }
    }

    $scope.minus = function (id) {
        var check = false;
        for (var i = 0; i < $scope.list_products.length; i++) {
            if ($scope.list_products[i].id == id) {
                if ($scope.list_products[i].quantity > 1) {
                    $scope.list_products[i].quantity--;
                }
            }
        }
    };

    $scope.add = function (id) {
        var check = false;
        for (var i = 0; i < $scope.list_products.length; i++) {
            if ($scope.list_products[i].id == id) {
                $scope.list_products[i].quantity++;
            }
        }
    };

    $scope.clickProduct = function () {
        for (var i = 0; i < $scope.list_products.length; i++) {
            $scope.list_products[i].quantity = 1;
        }
    }
});

