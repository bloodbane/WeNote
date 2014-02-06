var username = localStorage.getItem('user_name');
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
    //$( "<p>" ).html(ctab).appendTo( "#comments" );
    $('document').ready(function(){
        var username=localStorage.getItem('user_name');
        var request = "http://54.227.7.141:8080/wenote/notes?";
        $.getJSON( request, {
            user_name: username
        })
        .done(function( data ) {
            console.log( "JSON Data: " + data );
              $.each( data, function( i, note ) {
                     $wnot=$( "<li>" );
                     $wnot.attr("data-corners", "false").attr("data-shadow","false").attr("data-iconshadow","true").attr("data-wrapperels","div").attr( "data-icon","false").attr("data-iconpos","right").attr( "data-theme","none").attr( "class","ui-btn ui-btn-icon-right ui-li ui-li-has-alt ui-corner-bottom ui-li-last ui-btn-up-none");
                     $d1=$("<div>").attr("class","ui-btn-inner ui-li ui-li-has-alt");
                     $d2=$("<div>").attr("class","ui-btn-text");
                     $a=$("<a>").attr("href","#").attr("class","ui-link-inherit");
                     $("<h2>").html("["+note.title+"]").attr("class","ui-li-heading").appendTo($a);
                     $("<a>").html(note.url).attr("href",note.url).appendTo($a);
                     $("<p>").html(note.content).appendTo($a);
                     $("<p>").html(note.changeDate).attr("class","ui-li-desc").appendTo($a);

                     $a.appendTo($d2);
                     $d2.appendTo($d1);
                     $d1.appendTo($wnot);
                     $wnot.appendTo( "#comments" );
            });
                  
        });
    });
}

function getFriend()
{
    //$( "<p>" ).html(ctab).appendTo( "#comments" );
    $('document').ready(function(){
            var username=localStorage.getItem('user_name');
            var request = "http://54.227.7.141:8080/wenote/listfriend?";
                $.getJSON( request, {
                    user_name: username
                })
                .done(function( data ) {
                    console.log( "JSON Data: " + data );
                    $.each( data, function( i, friend ) {
                        $wnot=$( "<li>" );
                        $wnot.attr("data-corners", "false").attr("data-shadow","false").attr("data-iconshadow","true").attr("data-wrapperels","div").attr( "data-icon","arrow-r").attr("data-iconpos","right").attr( "data-theme","c").attr( "class","ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-btn-up-c");
                        $d1=$("<div>").attr("class","ui-btn-inner ui-li");
                        $d2=$("<div>").attr("class","ui-btn-text");
                        $a=$("<a>").html(friend).attr("href","#").attr( "data-rel","dialog").attr( "class","ui-link-inherit");
                        $a.appendTo($d2);

                        $d2.appendTo($d1);
                        $("<span>").html("&nbsp;").attr("class","ui-icon ui-icon-arrow-r ui-icon-shadow").appendTo($d1);
                        $d1.appendTo($wnot);
                        $wnot.appendTo( "#friendlist" );
                                     });
                              
                              });
                        });
}


function find()
{
    $('document').ready(function(){
        var username=localStorage.getItem('user_name')
        var friend=document.getElementById("basic").value;
        var request = "http:///54.227.7.141:8080/wenote/addfriend?";
        $.getJSON( request, {
            user_name: username,
            friend: friend
        })
        .done(function( data ) {
            console.log( "JSON Data: " + data );
                isOK=data;
                if(isOK)
                {
                    alert("You and "+friend+" are friend now!");
                }
                else{
                              
                    alert("You can't become a friend with "+friend+" !");
                }
            });
        });
}


document.addEventListener('DOMContentLoaded', function ()
{
    getComment();
    getFriend();
    document.getElementById('find').addEventListener('click', find);
});


