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
                  window.location.assign("notelist.html");
                  }
                  else{

                    document.getElementById("banner").innerHTML='<font color="red">Wrong user or password!</font>'
                  }
                });
          });
}

function register(){
    
    $('document').ready(function(){
                        
        var regun=document.getElementById("regun").value;
        var regpw=document.getElementById("regpw").value;
        var passre=document.getElementById("regpwrep").value;
        if  (regpw===passre){
            var request = "http:///54.227.7.141:8080/wenote/newuser?";
            $.getJSON( request, {
                user_name: regun,
                password: regpw
            })
            .done(function( data ) {
                console.log( "JSON Data: " + data );
                var isOK=data;
                if(isOK)
                {
                  localStorage.user_name=regun;
                  window.location.href="notelist.html";
                }
                else{
                    document.getElementById("rbanner").innerHTML='<font color="red">Username cannot be used!</font>';}
                  });
            }
            else{
                  document.getElementById("rbanner").innerHTML='<font color="red">Password are not the same!</font>';}
            });
        
}

document.addEventListener('DOMContentLoaded', function ()
    {
        document.getElementById('login').addEventListener('click', getComment);
        document.getElementById('register').addEventListener('click',register);
    
    });
