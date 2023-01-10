//Confirm password matching

function matchPassword() {
    var pass = document.getElementById('password').value;
    var confirm_pass = document.getElementById('reppass').value;
    if (pass != confirm_pass) {
        document.getElementById('wrong_pass').style.color = 'red';
        document.getElementById('wrong_pass').innerHTML = '✖ Use same password';
    } else {
        document.getElementById('wrong_pass').style.color = 'green';
        document.getElementById('wrong_pass').innerHTML = '✔ Password Matched';
    }
}