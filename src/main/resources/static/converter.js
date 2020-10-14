var sessionId;

function accept() {
  let url = "http://localhost:8080/login?";
  var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
           if (this.readyState == 4 && this.status == 200) {
               sessionId = this.responseText;
               console.log(this.responseText);
           }
      };
  url += "username="+ document.getElementById("username").value
  +"&password="+ document.getElementById("password").value
  +"&URL=" + document.getElementById("endpoint").value;
  xhttp.open("GET", url, true);
  xhttp.send();
}

function generate() {

}