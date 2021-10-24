
$(document).ready(function (){

    let loginForm = document.getElementById('loginDetails');
    window.onclick = function (event) {
        if (event.target == loginForm) {
            loginForm.style.display = "none";
        }
    }
});


