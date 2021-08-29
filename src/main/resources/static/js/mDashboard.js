

$(document).ready(function () {
    document.getElementById("hidden-email").style.display = "none";
    var email = document.getElementById('hidden-email').innerText;
    var switchBtnOrder = document.getElementById("orderLi");
    var switchBtnAccount = document.getElementById("accountLi");
    document.getElementById("ORDERDIV").style.display = "block";
    document.getElementById("ACCOUNTDIV").style.display = "none";
    document.getElementById("btnSave").style.display = "none";
    document.getElementById("logoutDiv").style.display = "none";
    document.getElementById("btnOrder").style.display = "none";
    

    $.ajax({
        url: "api/v2/member/p/getAllOrder/"+email,
        type:"GET",
        contentType:"application/json; charset=utf-8",
        success : function(response){
            
            if(response == ""){
                
                var tableorderDiv = document.getElementById('tableorder');
                $('tableorder').empty();
                
                tableorderDiv.innerHTML='<h1 class="text-center text-secondary">NOTHING ORDER AT THIS TIME</h1>'

            }else{
                var dataZ = JSON.parse(response);
               append_json(dataZ); 
            } 
        }
    })
    
    $(document).ready(function(){
        $("#search").on("keyup", function() {
          var value = $(this).val().toLowerCase();
          $("#tableorder tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
        });
      });


function append_json(data){
    var table = document.getElementById('orderTb');
    data.forEach(function(object) {
        idOrder = String(object.idOrder);
        var tr = document.createElement('tr');
       var td = '<td><h5 class=" text-success" style="font-weight: bold;" onclick="modalZ('+String(object.idOrder)+')" id="idOrder-text">' + object.idOrder + '</h5></td>' +
        '<td>' + object.dateCreated + '</td>';
        if(object.state == "pending" || object.state == "shipping"){
            td+=   '<td><h5 class="text-warning" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
        }else if(object.state == "cancel"){
            td+=   '<td><h5 class="text-danger" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
        }else if(object.state == "success"){
            td+=   '<td><h5 class="text-success" style="font-weight: bold;" id="state-order">' + object.state + '</h5></td>';
        }
            td+=   '<td>' + object.deliveryType + '</td>' +
        '<td>' + object.totalAmount + '</td>'+
        `<td><input type="button" class="btn btn-secondary" onclick="modalZ('`+idOrder+`')" value="DETAIL"> </td>` ;
        tr.innerHTML = td;
        table.appendChild(tr);
    });
}

    $('#updateOrder-btn').click(() => {
        var idCode = document.getElementById('idOrder-text').innerText;
        var fullName = document.getElementById('username-details').value;
        var phoneNumber = document.getElementById('phone-details').value;
        var address = document.getElementById('address-details').value;
        var note = document.getElementById('note-details').value;
        var jsonCode = JSON.stringify({"idCode":idCode, "fullName":fullName,"phone":phoneNumber,"address":address,"note":note});

        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: "info",
            confirmButtonColor: '#f9af47',
            confirmButtonText: 'Update'
          }).then((result) => {
            if (result.isConfirmed) {
                console.log(jsonCode);
                $.ajax({
                    url: "api/v1/d/Order",
                    type:"POST",
                    contentType:"application/json; charset=utf-8",
                    data: jsonCode,
                    success : function(response){
                        Swal.fire({
                            icon: 'success',
                            title: 'Update succesfully!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        location.reload();
                    }
                })
            }
        })

    })
        
    $('#cancel-order-btn').click( () => {
            var idcode = document.getElementById('idOrder-text').innerText;
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                imageUrl: '../img/tenor.gif',
                imageHeight: 120,
                imageWidth: 120,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Cancel this order!'
              }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: "api/v1/c/Order?_id_code="+idcode,
                        type:"GET",
                        contentType:"application/json; charset=utf-8",
                        success : function(response){
                            Swal.fire({
                                icon: 'success',
                                title: 'Cancel succesfully!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            location.reload();
                        }
                    })
                }
            })
    });

    switchBtnOrder.addEventListener(
        "click",
        function () {
            document.getElementById("ORDERDIV").style.display = "block";
            document.getElementById("ACCOUNTDIV").style.display = "none";
            $(".orderLi").addClass("text-center")
            $(".accountLi").removeClass("text-center")
            document.getElementById("dashboardName").innerHTML = "Order Status";
        }
    );
       switchBtnAccount.addEventListener(
        "click",
        function () {
            $.ajax({
                url: "http://localhost:8080/api/v2/member/getInfo?email="+email,
                type:"GET",
                contentType:"application/json; charset=utf-8",
                success : function(response){
                    document.getElementById("full_name").value = response.fullName;
                    document.getElementById("email").value = response.email;
                    document.getElementById("birthday").value = response.birthday;
                    document.getElementById("sex").value = response.sex?1:0;
                    document.getElementById("phone_number").value = response.phoneNumber;
                    document.getElementById("address_private").value = response.address;
                    document.getElementById("address_company").value = response.addressCompany;

                    document.getElementById("ORDERDIV").style.display = "none";
                    document.getElementById("ACCOUNTDIV").style.display = "block";
                    $(".accountLi").addClass("text-center")
                    $(".orderLi").removeClass("text-center")
                    document.getElementById("dashboardName").innerHTML = "Member Details";  
              
                      
                }
            })
            
        }
    );



  

    $(".resetLi").click(function(){        
            Swal.fire({
                title: 'Are You Sure?',
                icon: 'warning',
                text: 'We will send a code to your mail, please type code for reset',
                showCancelButton: true,
                confirmButtonText: 'Confirm',
                showLoaderOnConfirm: true,
                preConfirm: () => {
                return fetch(`http://localhost:8080/api/v2/member/rspw/`+ email)
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
                        title: 'RESET PASSWORD',
                        html:
                        '<input id="code" class="swal2-input">' +
                        '<input type="password" id="password" class="swal2-input">' +
                        '<input type="password" id="re-password" class="swal2-input">',
                        focusConfirm: false,
                        
                        preConfirm: () => {
                        
                        var code =  document.getElementById('code').value.toString();
                        var password = document.getElementById('password').value.toString();
                        var jsonCode = {"code": code ,"password":  password,"email": email};
                        
                            if(document.getElementById('password').value !=  document.getElementById('re-password').value){
                                Swal.showValidationMessage(
                                    `Retype password not match`
                                )
                            }else return fetch(`http://localhost:8080/api/v2/member/rspw`,{
                           method: 'POST',
                           headers: {'Content-type':'application/json'},
                           beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                           body: JSON.stringify(jsonCode)
                            }).then(response => {
                                if(!response.ok){
                                    Swal.showValidationMessage(
                                        `Code NOT CORRECT`
                                    )
                                }else if(response.ok) {
                                        return response.ok;
                                    
                                }
                            }).catch(error => {
                                Swal.showValidationMessage(
                                    `Request failed, please contact supporter!`
                                )
                                })
                         } //pre confirm
                      }).then((result) => {
                        if(result.isConfirmed){
                            Swal.fire({
                                icon: 'success',
                                title: 'Password Has Been Reset!',
                                text:'Please login again with new password, we will auto redirecting in 4s',
                                showConfirmButton: false,
                                timer: 4000
                            }).then(() => {
                                $('#logoutClick').trigger('click');
                            })
                        }
                        })
                } // is Confirmed
            })
        })
    
    $('.logoutLi').click(function(){
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

    $('form.formAccount').submit(function(event){
        event.preventDefault();
        var form = $(this).serializeFormJSON();
        console.log(JSON.stringify(form));
        Swal.fire({
            title: 'Do you want to save the changes?',
            showDenyButton: true,
            confirmButtonText: `Save`,
            denyButtonText: `Don't save`,
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                 fetch(`http://localhost:8080/api/v2/member/update`,{
                        method: 'POST',
                        headers: {'Content-type':'application/json'},
                        body: JSON.stringify(form)
                         }).then(response => {
                             if(!response.ok){
                                 
                                    Swal.showValidationMessage(
                                    `Something error, pls try again`
                                    );
                               
                             }else if(response.ok){
                                 Swal.fire({
                                    icon: 'success',
                                    title: 'Your account has been saved',
                                    showConfirmButton: false,
                                    timer: 1500
                                  })
                                
                             }
                         }).catch(error => {
                            Swal.showValidationMessage(
                                `Request failed, we will check this!`
                            )
                            })
            } else if (result.isDenied) {
              Swal.fire('Changes are not saved', '', 'info')
              .then(() => {
                $.ajax({
                    url: "http://localhost:8080/api/v2/member/getInfo?email="+email,
                    type:"GET",
                    contentType:"application/json; charset=utf-8",
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success : function(response){
                        document.getElementById("full_name").value = response.fullName;
                        document.getElementById("email").value = response.email;
                        document.getElementById("birthday").value = response.birthday;
                        document.getElementById("sex").value = response.sex?1:0;
                        document.getElementById("phone_number").value = response.phoneNumber;
                        document.getElementById("address_private").value = response.address;
                        document.getElementById("address_company").value = response.addressCompany;
                    }
                }) // ajax
              })
            }
          })

    })

    $(".unlockDiv").click(function(){
        document.getElementById("unlockText").innerHTML = "<em class='fa fa-unlock-alt'>&nbsp;</em>UNLOCK";
        document.getElementById("unlockText").style.color = "GREEN";
        $("#full_name").removeAttr("disabled");
        $("#birthday").removeAttr("disabled");
        $("#sex").removeAttr("disabled");
        $("#phone_number").removeAttr("disabled");
        $("#address_private").removeAttr("disabled");
        $("#address_company").removeAttr("disabled");
        document.getElementById("btnSave").style.display = "block";
    })

    $(".unlockOrderDiv").click(function(){
        var state = document.getElementById("state-order").innerText;
        if(state == 'pending' || state == 'confirmed'){
            document.getElementById("unlockOrderText").innerHTML = "<em class='fa fa-unlock-alt'>&nbsp;</em>UNLOCK";
            document.getElementById("unlockOrderText").style.color = "GREEN";
            document.getElementById("btnOrder").style.display = "block";
            $("#username-details").removeAttr("disabled");
            $("#phone-details").removeAttr("disabled");
            $("#address-details").removeAttr("disabled");
            $("#note-details").removeAttr("disabled");
        }else if(state == 'success' || state == 'shipping' || state == 'cancel'){
            Swal.fire({
                            icon: 'warning',
                            title: 'Cannot update when state is '+state+'!',
                            showConfirmButton: false,
                            timer: 1500
                        })
        }
    })

  
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };


});

function modalZ(data){
    $.ajax({
        url: "api/v1/p/getDetails?_id="+data,
        type:"GET",
        contentType:"application/json; charset=utf-8",
        success : function(response){
            orderDetailsJson(response)
        }
    })
}

function orderDetailsJson(data){
    if(data.details.length >0){
    $("#item-ul").empty();
      data.details.forEach((object) => {
            var li = document.createElement("li");
        li.setAttribute("class","list-group-item d-flex justify-content-between lh-condensed");
        var div = document.createElement("div");
            div.innerHTML = '<span><b class="my-0">'+object.productName+'</b>&ensp;&ensp;&ensp; x<b class="my-0">'+object.quantity+'</b></span><br>'+
            '<span class="text-muted">Amount-Items-Total: '+object.totalAmountProduct+'$</span>'
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
        document.getElementById("credit-COD").checked = !data.payment?true:false;
        document.getElementById("credit-PP").checked = data.payment?true:false;
        $('#OrderModal').modal('show');
        
    }else{
        Swal.fire({
            icon: 'info',
            title: 'Nothing to show!',
            showConfirmButton: false,
            timer: 1500
          })
    } 
    

    
}



