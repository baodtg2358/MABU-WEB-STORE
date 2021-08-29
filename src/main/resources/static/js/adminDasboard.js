
var app = angular.module("myApp", []);




app.controller("proCtrlProduct", function ($scope, $http, $rootScope) {

    //SHOW CATEGORY
    const getAllCategory = () => {
        $http.get("http://localhost:8080/api/v1/fa/category").then(
            function successCallback(reponse) {
                $scope.list_categories = reponse.data;
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

    //CREATE PRODUCT
    $scope.createProduct = function () {
        var form = $("#addFormProduct")[0];

        // Create an FormData object
        var formData = new FormData(form);

        //$http POST function
        $http({
            method: "POST",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/fa/products",
            data: formData,
        }).then(
            function successCallback(response) {
                alert("Successfully!");
                window.location.hash = 'product';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };

    //UPDATE USER
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
                window.location.hash = 'product';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };

    //DELETE PRODUC
    $scope.deleteProduct = function (product) {
        //$http DELETE function
        $http({
            method: "DELETE",
            url: "http://localhost:8080/api/v1/fa/products/" + product.id,
        }).then(
            function successCallback(response) {
                alert("Successfully");
                window.location.hash = 'product';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    };

    $scope.statusProduct = true;

    $scope.changeDisplayStatus = statusProduct => {
        $scope.statusProduct = statusProduct
    };

    $scope.recoverProduct = (product) => {
        $http({
            method: "PUT",
            url: "http://localhost:8080/api/v1/fa/products/recover/" + product.id,
        }).then(
            function successCallback(response) {
                alert("Successfully");
                window.location.hash = 'product';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }
});



app.controller("proCtrlCategory", function ($scope, $http, $rootScope) {

    $scope.dataSelectCategory = [
        {
            name: "Active",
            value: "true",
            selected: "yes"
        },
        {
            name: "Inactive",
            value: "false",
            selected: "no"
        },
    ];

    //SHOW CATEGORY
    const getAllCategory = () => {
        $http.get("http://localhost:8080/api/v1/fa/category").then(
            function successCallback(reponse) {
                $scope.list_categories = reponse.data;
            }
            ,
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }

    getAllCategory();

    //CREATE CATEGORY
    $scope.createCategory = function () {
        var form = $("#addFormCategory")[0];

        // Create an FormData object
        var formData = new FormData(form);

        for (var value of formData.values()) {
            console.log(value);
        }

        //$http POST function
        $http({
            method: "POST",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/fa/category",
            data: formData,
        }).then(
            function successCallback(response) {
                alert("Successfully!");
                window.location.hash = 'category';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };
    $scope.updateCategory = function (category) {

        var form = $(`#updateFormCategory${category}`)[0];

        console.log("Category: " + category)

        // Create an FormData object
        var formData = new FormData(form);


        for (var value of formData.values()) {
            console.log(value);
        }

        //$http PUT function
        $http({
            method: "PUT",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/fa/category/update/" + category,
            data: formData,
        }).then(
            function successCallback(response) {
                alert("Successfully!");
                window.location.hash = 'category';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };



    $scope.deleteCategory = function (category) {
        //$http DELETE function
        $http({
            method: "DELETE",
            url: "http://localhost:8080/api/v1/fa/category/delete/" + category,
        }).then(
            function successCallback(response) {
                alert("Successfully");
                window.location.hash = 'category';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    };
});


app.controller("proCtrlProfile", function ($scope, $http, $rootScope) {

    $scope.isDisable = true;

    $scope.edit = function () {
        $scope.isDisable = false;
    }

    $scope.empolyee = null;

    $scope.fillProfile = function () {
        var email = document.getElementById("hidden-email").innerText;
        $http({
            method: "GET",
            url: "http://localhost:8080/api/v1/staff/search?email=" + email,
        }).then(
            function successCallback(reponse) {
                $scope.empolyee = reponse.data;
            },
            function errorCallBack(reponse) {
                alert("Error, Try Again!")
            }
        )
    };
});

app.controller("proCtrlVoucher", function ($scope, $http, $rootScope) {
    //SHOW VOUCHER
    const getAllVoucher = () => {
        $http.get("http://localhost:8080/api/v1/staff/voucher").then(
            function successCallback(reponse) {
                $scope.list_vouchers = reponse.data;
            }
            ,
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }

    const getUser = () => {
        var email = document.getElementById("hidden-email").innerText;

        $http.get("http://localhost:8080/api/v1/staff/voucher/search/" + email).then(
            function successCallback(reponse) {
                $scope.user = reponse.data;
            }
            ,
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    }

    getUser();
    getAllVoucher();

    //CREATE VOUCHER
    $scope.createVoucher = function () {
        var form = $("#addFormVoucher")[0];

        // Create an FormData object
        var formData = new FormData(form);

        //
        var email = document.getElementById("hidden-email").innerText;


        for (var value of formData.values()) {
            console.log(value);
        }

        //$http POST function
        $http({
            method: "POST",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/staff/voucher",
            data: formData,
        }).then(
            function successCallback(response) {
                alert("Successfully!");
                window.location.hash = 'voucher';
                window.location.reload();

            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };

    $scope.updateVoucher = function (voucher) {

        var form = $(`#updateFormVoucher${voucher}`)[0];

        console.log("Voucher: " + voucher)

        // Create an FormData object
        var formData = new FormData(form);


        for (var value of formData.values()) {
            console.log(value);
        }

        //$http PUT function
        $http({
            method: "PUT",
            enctype: 'multipart/form-data',
            headers: {
                'Content-Type': undefined
            },
            url: "http://localhost:8080/api/v1/staff/voucher/update/" + voucher,
            data: formData,
        }).then(
            function successCallback(response) {
                window.location.hash = 'voucher';
                window.location.reload();
            },
            function errorCallback(response) {
                alert("Error. Try again!");
            }
        );
    };


    $scope.deleteVoucher = function (voucher) {
        //$http DELETE function
        $http({
            method: "DELETE",
            url: "http://localhost:8080/api/v1/staff/voucher/delete/" + voucher,
        }).then(
            function successCallback(response) {
                alert("Successfully");
                window.location.hash = 'voucher';
                window.location.reload();

            },
            function errorCallback(response) {
                alert("Error. Try Again!");
            }
        );
    };

});




window.onload = function () {



    var chart1 = document.getElementById("line-chart").getContext("2d");
    window.myLine = new Chart(chart1).Line(lineChartData, {
        responsive: true,
        scaleLineColor: "rgba(0,0,0,.2)",
        scaleGridLineColor: "rgba(0,0,0,.05)",
        scaleFontColor: "#c5c7cc"
    });
    // Load all Order
    $.ajax({
        url: "api/v1/order/a/getAllOrder",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (response) {

            if (response == "") {

                var tableorderDiv = document.getElementById('tableorder');
                $('tableorder').empty();

                tableorderDiv.innerHTML = '<h1 class="text-center text-secondary">NOTHING ORDER AT THIS TIME</h1>'

            } else {
                var dataZ = JSON.parse(response);
                append_json(dataZ);
            }
        }
    })

    function append_json(data) {
        var table = document.getElementById('orderTb');
        data.forEach(function (object) {
            idOrder = String(object.idOrder);
            var tr = document.createElement('tr');
            var td = '<td><h5 class=" text-success" style="font-weight: bold;" onclick="modalZ(' + String(object.idOrder) + ')" id="idOrder-text">' + object.idOrder + '</h5></td>' +
                '<td>' + object.dateCreated + '</td>';
            if (object.state == "pending" || object.state == "shipping") {
                td += '<td><h5 class="text-warning" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
            } else if (object.state == "cancel") {
                td += '<td><h5 class="text-danger" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
            } else if (object.state == "success") {
                td += '<td><h5 class="text-success" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
            }
            td += '<td>' + object.deliveryType + '</td>' +
                '<td>' + object.totalAmount + '</td>' +
                `<td><input type="button" class="btn btn-secondary" onclick="modalZ('` + idOrder + `')" value="DETAIL"> </td>`;
            tr.innerHTML = td;
            table.appendChild(tr);
        });
    }

    // Load All Order


    var changeToDashBorad = document.getElementById("dashboard");
    var changeToProduct = document.getElementById("product");
    var changeToProfile = document.getElementById("profile");
    var changeToCategory = document.getElementById("category");
    var changeToOrder = document.getElementById("order");
    var changeToVoucher = document.getElementById("voucher");



    document.getElementById("hidden-email").style.display = "none";
    document.getElementById("logoutDiv").style.display = "none";
    document.getElementById("dashboardPage").style.display = "none";
    document.getElementById("productPage").style.display = "none";
    document.getElementById("profilePage").style.display = "none";
    document.getElementById("categoryPage").style.display = "none";
    document.getElementById("orderPage").style.display = "block";
    document.getElementById("voucherPage").style.display = "none";



    changeToDashBorad.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "block";
            document.getElementById("productPage").style.display = "none";
            document.getElementById("profilePage").style.display = "none";
            document.getElementById("categoryPage").style.display = "none";
            document.getElementById("orderPage").style.display = "none";
            document.getElementById("voucherPage").style.display = "none";


        },
        false
    );

    changeToProduct.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "none";
            document.getElementById("productPage").style.display = "block";
            document.getElementById("profilePage").style.display = "none";
            document.getElementById("categoryPage").style.display = "none";
            document.getElementById("orderPage").style.display = "none";
            document.getElementById("voucherPage").style.display = "none";


        },
        false
    );

    changeToProfile.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "none";
            document.getElementById("productPage").style.display = "none";
            document.getElementById("profilePage").style.display = "block";
            document.getElementById("categoryPage").style.display = "none";
            document.getElementById("orderPage").style.display = "none";
            document.getElementById("voucherPage").style.display = "none";


        },
        false
    );

    changeToCategory.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "none";
            document.getElementById("productPage").style.display = "none";
            document.getElementById("profilePage").style.display = "none";
            document.getElementById("categoryPage").style.display = "block";
            document.getElementById("orderPage").style.display = "none";
            document.getElementById("voucherPage").style.display = "none";


        },
        false
    );

    changeToOrder.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "none";
            document.getElementById("productPage").style.display = "none";
            document.getElementById("profilePage").style.display = "none";
            document.getElementById("categoryPage").style.display = "none";
            document.getElementById("orderPage").style.display = "block";
            document.getElementById("voucherPage").style.display = "none";


        },
        false
    );

    changeToVoucher.addEventListener(
        "click",
        function () {
            document.getElementById("dashboardPage").style.display = "none";
            document.getElementById("productPage").style.display = "none";
            document.getElementById("profilePage").style.display = "none";
            document.getElementById("categoryPage").style.display = "none";
            document.getElementById("orderPage").style.display = "none";
            document.getElementById("voucherPage").style.display = "block";

        },
        false
    );

    // Profile support

    $('#btn-cbp').click(() => {
        var phone = document.getElementById("textBoxSearch").value;
        $.ajax({
            url: "http://localhost:8080/api/v2/cbp/"+phone,
            type:"GET",
            contentType:"application/json; charset=utf-8",
            success : function(response){
                    Swal.fire({
                        imageUrl: '../img/logoblack.jpg',
                        imageHeight: 120,
                        imageWidth: 120,
                        title: response,
                        text: 'Member at Mabu Store'
                      });
            },
            error: function(request, status, error){
                var responseText = request.responseText;
                Swal.fire({
                    imageUrl: '../img/tenor.gif',
                    imageHeight: 120,
                    imageWidth: 120,
                    title: 'Oops...',
                    text: 'Something went wrong!',
                    footer: responseText
                  })
              }
        })
    });

    $('#btn-cbe').click(() => {
        var email = document.getElementById("textBoxSearch").value;
        $.ajax({
            url: "http://localhost:8080/api/v2/cbe/"+email,
            type:"GET",
            contentType:"application/json; charset=utf-8",
            success : function(response){
                    Swal.fire({
                        imageUrl: '../img/logoblack.jpg',
                        imageHeight: 120,
                        imageWidth: 120,
                        title: response,
                        text: 'Member at Mabu Store'
                      })
            },
            error: function(request, status, error){
                var responseText = request.responseText;
                Swal.fire({
                    imageUrl: '../img/tenor.gif',
                    imageHeight: 120,
                    imageWidth: 120,
                    title: 'Oops...',
                    text: 'Something went wrong!',
                    footer: responseText
                  })
              }
        })
    });

    $('#btn-sUser').click(() => {
        var email = document.getElementById('searchUser').value;
        $.ajax({
            url: "http://localhost:8080/api/v1/staff/search/?email="+email,
            type:"GET",
            contentType:"application/json; charset=utf-8",
            success : function(response){
                    document.getElementById('nameUser').value = response.fullName;
                    document.getElementById('emailUser').value = response.email;
                    document.getElementById('addressUser').value = response.address;
                    document.getElementById('phoneUser').value = response.phoneNumber;
                    document.getElementById('birthdayUser').value = response.birthday;
                    document.getElementById('roleUser').value = response.roleID;
                    document.getElementById('male-radio').checked = response.sex;
                    document.getElementById('female-radio').checked = !response.sex;
            },
            error: function(request, status, error){
                var responseText = request.responseText;
                Swal.fire({
                    imageUrl: '../img/tenor.gif',
                    imageHeight: 120,
                    imageWidth: 120,
                    title: 'Oops...',
                    text: 'Something went wrong!',
                    footer: responseText
                  })
              }
        })
    });

    $('#updateUser').click(() => {
                var email = document.getElementById('emailUser').value;
                var name =  document.getElementById('nameUser').value;
                var address =    document.getElementById('addressUser').value;
                var phone =   document.getElementById('phoneUser').value;
                var birthday =    document.getElementById('birthdayUser').value;
                var role =    document.getElementById('roleUser').value;
                var sex = document.querySelector("input[name=gender]:checked").value;
                var jsonObj = JSON.stringify({'email':email,'full_name':name,'address': address,'phone_number': phone,'sex':sex, 'birthday':birthday,'roleID':role });
        $.ajax({
            url: "http://localhost:8080/api/v1/staff/update",
            type:"POST",
            contentType:"application/json; charset=utf-8",
            data : jsonObj,
            success : function(response){      
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Update Successfully!',
                    text: response,
                    showConfirmButton: false,
                    timer: 2000
                })
            },
            error: function(request, status, error){
                var responseText = request.responseText;
                Swal.fire({
                    imageUrl: '../img/tenor.gif',
                    imageHeight: 120,
                    imageWidth: 120,
                    title: 'Oops...',
                    text: 'Something went wrong!',
                    footer: responseText
                  })
              }
        })
    });

    $('#rspw').click(() => {
        var email = document.getElementById('emailUser').value;
        
        Swal.fire({
            title: 'Are You Sure?',
            icon: 'warning',
            text: 'We will send an OTP to '+email+', please check email for reset',
            showCancelButton: true,
            confirmButtonText: 'Confirm',
            showLoaderOnConfirm: true,
            preConfirm: () => {
            return fetch(`http://localhost:8080/api/v1/staff/rspw?email=`+ email)
                .then(response => {
                if (!response.ok) {
                    Swal.showValidationMessage(
                        `Something wrong, please try again!`
                    )
                }
                return response.ok;
                })
                .catch(error => {
                Swal.showValidationMessage(
                    `Request failed, please contact supporter!`
                )
                })
            },
            allowOutsideClick: () => !Swal.isLoading()
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Succesfully!',
                    text:'Email already send!',
                    showConfirmButton: false,
                    timer: 2000
                });
            } // is Confirmed
        })
});




     // Profile support


    // send email for voucher
    $('#btnEmailVoucher').click(() => {
        Swal.fire({
            title: 'EVENT CODE',
            input: 'text',
            inputAttributes: {
                autocapitalize: 'off'
            },
            confirmButtonText: 'Send',
            showLoaderOnConfirm: true,
            preConfirm: (code) => {
                return fetch(`http://localhost:8080/api/v1/staff/email/${code}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(response.statusText)
                        }
                        return response.ok
                    })
                    .catch(error => {
                        Swal.showValidationMessage(
                            `Request failed: ${error}`
                        )
                    })
            },
            allowOutsideClick: true
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Email has been seen!',
                    showConfirmButton: false,
                    timer: 1500
                })
            }
        })
    });

        // send email for voucher

    $('.logoutLi').click(function () {
        Swal.fire({
            title: 'Are you sure?',
            text: "We will miss you so much :(",
            imageUrl: '../img/tenor.gif',
            imageHeight: 120,
            imageWidth: 120,
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'Logout'
        }).then((result) => {
            if (result.isConfirmed) {
                $('#logoutClick').trigger('click');
            }
        })
    });

    if (window.location.hash == "#voucher") {
        changeToVoucher.click();
    } else if (window.location.hash == "#product") {
        changeToProduct.click();
    } else if (window.location.hash == "#profile") {
        changeToProfile.click();
    } else if (window.location.hash == "#category") {
        changeToCategory.click();
    } else if (window.location.hash == "#order") {
        changeToOrder.click();
    }
    // }else  {
    //     changeToDashBorad.click();
    // }



}

function modalZ(data) {
    $.ajax({
        url: "api/v1/order/a/getDetails?_id=" + data,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            orderDetailsJson(response)
        }
    })
}

function orderDetailsJson(data) {
    if (data.details.length > 0) {
        $("#item-ul").empty();
        data.details.forEach((object) => {
            var li = document.createElement("li");
            li.setAttribute("class", "list-group-item d-flex justify-content-between lh-condensed");
            var div = document.createElement("div");
            div.innerHTML = '<span><b class="my-0">' + object.productName + '</b>&ensp;&ensp;&ensp; x<b class="my-0">' + object.quantity + '</b></span><br>' +
                '<span class="text-muted">Amount-Items-Total: ' + object.totalAmountProduct + '$</span>'
            li.appendChild(div);
            document.getElementById("item-ul").appendChild(li);
        })

        document.getElementById("voucher-code").value = data.voucher
        document.getElementById('Total-item').innerHTML = data.details.length;
        document.getElementById("total-amount").innerHTML = data.totalAmount;
        document.getElementById("username-details").value = data.fullName;
        document.getElementById("phone-details").value = data.phoneNumber;
        document.getElementById("address-details").value = data.address;
        document.getElementById("note-details").value = data.notes;
        document.getElementById("delivery-details").innerText = data.deliveryType;
        document.getElementById("credit-COD").checked = !data.payment ? true : false;
        document.getElementById("credit-PP").checked = data.payment ? true : false;
        $('#OrderModal').modal('show');

    } else {
        Swal.fire({
            icon: 'info',
            title: 'Nothing to show!',
            showConfirmButton: false,
            timer: 1500
        })
    }



}