/*global $, document, window, setTimeout, navigator, console, location*/
$(document).ready(function () {

    'use strict';

    var usernameError = true,
        emailError    = true,
        passwordError = true,
        passConfirm   = true;

    // Detect browser for css purpose
    if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
        $('.form form label').addClass('fontSwitch');
    }

    // Label effect
    $('input').focus(function () {

        $(this).siblings('label').addClass('active');
    });

    // Form validation
    $('input').blur(function () {

        // User Name
        if ($(this).hasClass('name')) {
            if ($(this).val().length === 0) {
                $(this).siblings('span.error').text('Please type your full name').fadeIn().parent('.form-group').addClass('hasError');
                usernameError = true;
            } else if ($(this).val().length > 1 && $(this).val().length <= 6) {
                $(this).siblings('span.error').text('Please type at least 6 characters').fadeIn().parent('.form-group').addClass('hasError');
                usernameError = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent('.form-group').removeClass('hasError');
                usernameError = false;
            }
        }
        // Email
        if ($(this).hasClass('email')) {
            if ($(this).val().length == '') {
                $(this).siblings('span.error').text('Please type your email address').fadeIn().parent('.form-group').addClass('hasError');
                emailError = true;
            } else {
                $(this).siblings('.error').text('').fadeOut().parent('.form-group').removeClass('hasError');
                emailError = false;
            }
        }
        // label effect
        if ($(this).val().length > 0) {
            $(this).siblings('label').addClass('active');
        } else {
            $(this).siblings('label').removeClass('active');
        }



    });


    // form switch
    $('a.switch').click(function (e) {
        $(this).toggleClass('active');
        e.preventDefault();

        if ($('a.switch').hasClass('active')) {
            $(this).parents('.form-peice').addClass('switched').siblings('.form-peice').removeClass('switched');
        } else {
            $(this).parents('.form-peice').removeClass('switched').siblings('.form-peice').addClass('switched');
        }
    });


    // Form submit
    $('form.signup-form').submit(function (event) {
        event.preventDefault();
        if (usernameError == true || emailError == true ) {
            $('.name, .email').blur();
        } else {
            var form = $(this).serializeFormJSON();
        
            $.ajax({
                url: "http://localhost:8080/api/v2/fa/regis",
                type:"POST",
                contentType:"application/json; charset=utf-8",
                data:  JSON.stringify(form),
                success : function(){
                    $('.container').addClass('modalBlur');
                    swal({
                        title: "WELCOME NEW MABER!",
                        text: "Please check your email and login for first time!",
                        icon: "../img/bear.png",
                        button: false,
                        timer: 3000,
                      }).then(() => {


                          $('a.switch').toggleClass('active');
                      if ($('a.switch').hasClass('active')) {
                          $('a.switch').parents('.form-peice').addClass('switched').siblings('.form-peice').removeClass('switched');
                      } else {
                          $('a.switch').parents('.form-peice').removeClass('switched').siblings('.form-peice').addClass('switched');
                      }
                        $('.container').removeClass('modalBlur');
                      });
                      
                }
                
            })

            
        }
    });

    $('#btn-forgot').click(() => {
        if(document.getElementById("loginemail").value == null || document.getElementById("loginemail").value == '' ){
            document.getElementById("err-msg").innerText = 'Please type your email'
        }else{
            var email = document.getElementById("loginemail").value;
            $.ajax({
                url: "http://localhost:8080/api/v2/fa/rspw?email="+ email,
                type:"GET",
                contentType:"application/json; charset=utf-8",
                success : function(){
            $('.container').addClass('modalBlur');
            swal({
                title: "OTP WAS SEND!",
                text: "Please check your email and login with OTP!",
                icon: "../img/bear.png",
                buttons: false,
                timer: 3000
              }).then(() => {
                  $('.container').removeClass('modalBlur');
              });
           },
           error: function(request, status, error){
            document.getElementById("err-msg").innerText = 'Email not exist!'; 
          }  
            }) 
        } // else
        
    })


});

(function ($) {
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
})(jQuery);


