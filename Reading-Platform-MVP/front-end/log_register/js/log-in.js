function changeLoginPage() {
  const register_items = document.getElementsByClassName("hdn_form");
  var isLogin = true;
  
  document.getElementById("register_btn").addEventListener("click", () => {
    document.getElementById("title").firstChild.textContent = "REGISTER";
    if (isLogin) {
      for (let item of register_items) {
        item.style.display = "block";
      }
      isLogin = false;
      document.getElementById("label_login").textContent = "Login";
    }
  });
  document.getElementById("signin_btn").addEventListener("click", () => {
    document.getElementById("title").firstChild.textContent = "LOGIN";
    if (!isLogin) {
      for (let item of register_items) {
        item.style.display = "none";
      }
      isLogin = true;
      document.getElementById("label_login").textContent = "Login/Email";
    }
  });

  document.getElementById("register_form").addEventListener("submit", function(event) {
    if (!isForm(isLogin)) {
      event.preventDefault();
    }
  });
  document.getElementById("signin_form").addEventListener("submit", function(event) {
    if (!isForm(isLogin)) {
      event.preventDefault();
    }
  });
}

function isForm(isLogin) {
  const loginInput = document.getElementById('login');
  const emailInput = document.getElementById('email');
  const passwordInput = document.getElementById('password');
  const matchingPasswdInput = document.getElementById('matching_passwd');

  if (isLogin) {
    if (loginInput.value && passwordInput.value) {
      console.log("Login form is valid");
      return true;
    } else {
      console.log("Login form is invalid");
      return false;
    }
  } else {
    if ((loginInput.value && passwordInput.value && emailInput.value && matchingPasswdInput.value) && (passwordInput === matchingPasswdInput)) {
      console.log("Registration form is valid");
      return true;
    } else {
      console.log("Registration form is invalid");
      return false;
    }
  }
}

changeLoginPage();