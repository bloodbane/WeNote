var username = localStorage.getItem('user_name');
/*
chrome.browserAction.onClicked.addListener(function(tab){
    chrome.tabs.query({
        active: true,
        currentWindow: true
    }, function(tab) {
        localStorage.ctab=tab[0].url;
        console.log(localStorage.getItem('ctab'));
    })
});
*/
function info(){
    $('document').ready(function(){
        chrome.tabs.query({
            active: true,
            currentWindow: true
        }, function(tab) {
            localStorage.ctab=tab[0].url;
            getComment();
        });
    });
}


function getComment()
{
    
            var ctab=localStorage.getItem('ctab');
            console.log(localStorage.getItem('ctab'));
            $( "<p>" ).html(ctab).appendTo( "#comments" );
            var request = "http://54.227.7.141:8080/wenote/notes?";
            $.getJSON( request, {
                user_name: username,
                url: ctab
            })
            .done(function( data ) {
                console.log( "JSON Data: " + data );
                $.each( data, function( i, note ) {
                    $( "<p>" ).html(note.userName+ ": [" + note.title + "] " + note.content)
                .appendTo( "#comments" );
                        
            });
                  
            });

    
}


function ch(obj){
return document.getElementById(obj);
}

function sp(){
var tex = ch('te').value;
var nun =tex.length;
var spa = ch('span');
$('#span').text(1000-nun);
}

var ss;

function submitComment()
{
    $('document').ready(function(){
        var ctab=localStorage.getItem('ctab');
        var encoded=ctab.value;
        var request = "http://54.227.7.141:8080/wenote/post?";
        var comment = document.getElementById("te").value;
        var ti = document.getElementById("ti").value;
        $.getJSON( request, {
            user_name: username,
            url: ctab,
            title: ti,
            content: comment
        })
        .done(function( data ) {
            $( "<p>" ).html("Success!").appendTo( "#form" );
            $( "<p>" ).html(username+ ": [" + ti + "] " + comment)
              .appendTo( "#comments" );
        });
              
    });
           
}

//localhost:8080/wenote/post?user_name=admin&url=nyu.edu&title=hello&content=nothing

document.addEventListener('DOMContentLoaded', function ()
{

    info();
    document.getElementById('st').addEventListener('click', submitComment);
    document.getElementById('logout').addEventListener('click', logout);
    document.getElementById('te').addEventListener('focus', ss=setInterval(sp,25));
    document.getElementById('te').addEventListener('blur' , ss);
    
});

function logout(){
    localStorage.user_name=null;
    chrome.browserAction.setPopup({popup: 'login.html'});
    window.close();
}
