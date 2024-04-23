let email = document.querySelector('[name="email"]');
let emailFailMessage = document.querySelector('#email_fail_message');

email.onkeyup = function () {
    if(email.value.length !== 0){
        if(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value) === false) {
            emailFailMessage.classList.remove('hide');
        }else{
            emailFailMessage.classList.add('hide');
        }
    }else{
        emailFailMessage.classList.add('hide');
    }
    signUpCheck();
}


let username = document.querySelector('[name="username"]');
let usernameFailMessage = document.querySelector('#username_fail_message');

username.onkeyup = function () {
    if(username.value.length !== 0){
        if(/^[A-Za-z0-9][A-Za-z0-9]*$/.test(username.value) === false || (username.value.length > 12 || username.value.length < 4)) {
            usernameFailMessage.classList.remove('hide');
        }else{
            usernameFailMessage.classList.add('hide');
        }
    }else{
        usernameFailMessage.classList.add('hide');
    }
    signUpCheck();
}

let password = document.querySelector('[name="password"]');
let passwordFailMessage = document.querySelector('#password_fail_message');

password.onkeyup = function () {
    if(password.value.length !== 0){
        if(password.value.length < 8) {
            passwordFailMessage.classList.remove('hide');
        }else{
            passwordFailMessage.classList.add('hide');
        }
    }else{
        passwordFailMessage.classList.add('hide');
    }
    signUpCheck();
}

let confirmPassword = document.querySelector('[name="confirm_password"]');
let confirmPasswordFailMessage = document.querySelector('#confirm_password_fail_message');

confirmPassword.onkeyup = function () {
    if(confirmPassword.value.length !== 0){
        if(confirmPassword.value !== password.value) {
            confirmPasswordFailMessage.classList.remove('hide');
        }else{
            confirmPasswordFailMessage.classList.add('hide');
        }
    }else{
        confirmPasswordFailMessage.classList.add('hide');
    }
    signUpCheck();
}



function signUpCheck(){
    if(email.classList.contains('hide') &&
        usernameFailMessage.classList.contains('hide') &&
        passwordFailMessage.classList.contains('hide') &&
        confirmPasswordFailMessage.classList.contains('hide') &&
        email.value.length > 0 &&
        usernameFailMessage.value.length > 0 &&
        passwordFailMessage.value.length > 0 &&
        confirmPasswordFailMessage.value.length > 0
    ) document.querySelector('.submit').style.backgroundColor = '#4285F4'
    else{
        document.querySelector('.submit').style.backgroundColor = 'darkgray'
    }
}

function goToSignIn() {






}

function goToSignUp() {
    window.location.href="/pages/signUp.html";
}