let email = document.querySelector('[name="email"]');
let emailFailMessage = document.querySelector('#email_fail_message');
let username = document.querySelector('[name="username"]');
let usernameFailMessage = document.querySelector('#username_fail_message');
let password = document.querySelector('[name="password"]');
let passwordFailMessage = document.querySelector('#password_fail_message');
let confirmPassword = document.querySelector('[name="confirm_password"]');
let confirmPasswordFailMessage = document.querySelector('#confirm_password_fail_message');
let signUpForm = document.querySelector('#sighUpForm');
let submitButton = document.querySelector('#submit');

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

function allPass() {
    if(emailFailMessage.classList.contains('hide') &&
        usernameFailMessage.classList.contains('hide') &&
        passwordFailMessage.classList.contains('hide') &&
        confirmPasswordFailMessage.classList.contains('hide') &&
        email.value.length > 0 &&
        username.value.length > 0 &&
        password.value.length > 0 &&
        confirmPassword.value.length > 0
    ) return true;
    return false;
}

function signUpCheck(){
    if(allPass()){
        submitButton.style.backgroundColor = '#4285F4';
    }else{
        submitButton.style.backgroundColor = 'darkgray';
    }
}

signUpForm.addEventListener('submit', function (event) {
    event.preventDefault();
    if(allPass()){
        let formData = {
            "email":email.value
            ,"username":username.value
            ,"password":password.value
        };

        fetch('http://localhost:8080/signUp', {
            method: 'POST',
            body: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Network response was not ok.');
            })
            .then(data => {
                console.log(data); // 서버 응답 출력
                // 원하는 처리 수행
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }


})