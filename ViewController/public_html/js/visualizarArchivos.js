
function hacerClick (nombreComponente) {
    if (navigator.appName.indexOf("Explorer") != -1) {
        // Internet Explorer
        document.getElementById(nombreComponente).click();
    } else {
        // Mozilla Firefox
        document.getElementById(nombreComponente).onclick();
    }
}

function iniciar() {
    if ((document.getElementById('frmArchivos:hdnInit').value == null) || 
        (document.getElementById('frmArchivos:hdnInit').value == '')) {
        hacerClick('frmArchivos:lnkBuscar');
    } else {
        document.getElementById('frmArchivos:txtRuta').focus(); 
        document.getElementById('frmArchivos:txtRuta').select();
    }
}

function sePresionoEnter(event, nombreComponente) 
{
    var com=document.getElementById(nombreComponente); 
    
    if (event.which != null) {
        // Mozilla FireFox
        if (event.which == 13) {
            com.onclick();
        }
     } else{
        // Internet Explorer
        if (window.event.keyCode==13) 
        {
            com.click();
        }            
     } 
}

