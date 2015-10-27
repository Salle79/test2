function ajaxFunction()
{

var xmlHttp;

try
  {
  // Firefox, Opera 8.0+, Safari
  xmlHttp=new XMLHttpRequest();
  }
catch (e)
  {
  // Internet Explorer
  try
    {
    xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
    }
  catch (e)
    {
    try
      {
      xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    catch (e)
      {
      alert("Your browser does not support AJAX!");
      return false;
      }
    } 
  }
  xmlHttp.onreadystatechange=function()
    {
    if(xmlHttp.readyState==4)
      {
      document.getElementById("txtHint").innerHTML=xmlHttp.responseText;    
     if( document.getElementById("txtHint").innerHTML.length >3) {
        document.getElementById("UserName").style.border="medium solid red";
         
     }
       
}
    }
    
    var url = 'AJAX.jsp?ID=' + document.getElementById("UserName").value;
    
    xmlHttp.open("GET",url,true);
  xmlHttp.send(null);
  }

function res()
{
  
          document.getElementById("UserName").style.border="thin solid black";
          document.getElementById("txtHint").innerHTML = "";
}