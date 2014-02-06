document.addEventListener('DOMContentLoaded', function () {
    var links = document.getElementsByTagName("a");
    for (var i = 0; i < links.length; i++) {
        (function () {
            var ln = links[i];
            var location = ln.href;
            ln.onclick = function () {
                chrome.tabs.create({active: true, url: location});
            };
         })();
    }
});

function getComment()
{
    var isUser;
    $('document').ready(function(){
                        
                        
        var username=document.getElementById("login-username").value;
        var pass=document.getElementById("login-password").value;
        var request = "http:///54.227.7.141:8080/wenote/login?";
        $.getJSON( request, {
            user_name: username,
            password: pass
        })
            .done(function( data ) {
                  console.log( "JSON Data: " + data );
                  isUser=data;
                  if(isUser)
                  {
                  localStorage.user_name=username;
                  chrome.browserAction.setPopup({popup: 'input.html'});
                  /*
                   chrome.tabs.insertCSS( null, {file: "sidetogglemenu.css"});
                   chrome.tabs.executeScript(null, {file: "jquery-1.10.2.min.js"});
                   chrome.tabs.executeScript(null, {file: "content_script.js"});
                   */
                  window.close();
                  }
                  else{ document.getElementById("banner").innerHTML='<font color="red">Wrong username or password!</font>';}
        });
                        });



}


function register(){
    chrome.tabs.create({active: true, url: "http://wenote.s3-website-us-east-1.amazonaws.com/"})
}
document.addEventListener('DOMContentLoaded', function ()
    {
    document.getElementById('login').addEventListener('click', getComment);
    document.getElementById('reg').addEventListener('click', register);
    });



