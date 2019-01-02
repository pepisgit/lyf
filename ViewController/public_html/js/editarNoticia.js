var refreshIntervalId;

function testInsertImage()
{
    CKEDITOR.instances['frm01:editor1'].insertHtml('<p><img src=\x22imagenes/nosotros_archivos/foto01.jpg\x22 /></p>');
}

function insertarImagenTemp(data)
{
    var nombreArchivo;
    var idImagen;
    var optEp;
    var txtEpigrafe;
    var htmlImagen;

    if (data.status == "success")
    {
        nombreArchivo = document.getElementById('frm01:hdnImagen').value;
        idImagen = document.getElementById('frm01:hdnImagenId').value;
        optEp = document.getElementById('frm01:hdnOptEpigrafe').value;
        
        RichFaces.$('frm01:uploadImagen').__removeAllItems();
        RichFaces.$('frm01:insertImagenPopup').hide();
        
        if (document.getElementById('frm01:srEpigrafe:1').checked)
        {
            txtEpigrafe = document.getElementById('frm01:hdnEpigrafe').value;
            htmlImagen = '<div id="tbl' + idImagen +'">' + '<table align="center"><tr><td align="center"><table><tr><td id="' + idImagen +'">' +
                         '<img src=\x22' + nombreArchivo + '\x22 />' +
                         '</td></tr><tr><td align="center" style="font-size:11px;"><cite>' +
                         txtEpigrafe +
                         '</cite></td></tr></table></td></tr></table></div><br/>';
            
            CKEDITOR.instances['frm01:editor1'].insertHtml(htmlImagen, 'unfiltered_html');
        }
        else
        {
            CKEDITOR.instances['frm01:editor1'].insertHtml('<p><img src=\x22' + nombreArchivo + '\x22 /></p><br/>');
        }
    }
}

function keyListener(event)
{
    var tecla = event.data.keyCode;
    
    if (tecla == 46)
    {
        try
        {
            var sel = CKEDITOR.instances['frm01:editor1'].getSelection();
            var ranges = sel.getRanges();
            var range = ranges[0];
            var anc = range.getCommonAncestor();
    
            var idElemento = anc.getId();
            
            if (idElemento != null && idElemento.indexOf('not_img') != -1)
            {
                var idTabla = 'tbl' + idElemento;
                var elemTabla = CKEDITOR.instances['frm01:editor1'].document.getById(idTabla);
                if (elemTabla != null)
                    elemTabla.remove(false);
            }
        }
        catch (e)
        {}
    }
}

function clearFileList()
{
    RichFaces.$('frm01:uploadImagen').clear();
    return false;
}

function llamadaMantenerSesion()
{
    refreshIntervalId = setInterval ('mantenerSesion()', 30000);
}

function desactivarMantenerSesion()
{
    clearInterval(refreshIntervalId);
}