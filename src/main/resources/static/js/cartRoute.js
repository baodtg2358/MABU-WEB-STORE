$(document).ready(function(){

    $('#checkout-button').click(() => {
        var voucher = document.getElementById("voucher").value;
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
            var arrayItem = [];
            var table = document.getElementById('table-cart');
            var rowLength = table.rows.length;

            for(var i=1; i<rowLength; i++){
            var row = table.rows[i];
            var name = row.cells[0].querySelector('.name').innerText;
            var price = row.cells[1].querySelector('.price').innerText;
            var quantity = row.cells[2].querySelector('.js-qty-num').value
            var totalOfItem = row.cells[3].querySelector('.totalOfItem').innerText;
            var json = JSON.stringify({'nameItem': name, 'price': price, 'quantity':quantity, 'totalOfItem': totalOfItem});

		    arrayItem.push(json); 
            };
            var fullName = document.getElementById('fullName').value;
            var phone = document.getElementById('phoneNumber').value;
            var address = document.getElementById('address').value;
            var note = document.getElementById('note').value;
            var deliveryType = document.querySelector("input[name=radioZ]:checked").value;
            
            console.log(arrayItem); 
        }
    })
})