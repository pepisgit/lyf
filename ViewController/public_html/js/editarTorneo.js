// Inicia el controlador de las listas desplegables auto suggestion

function iniciar()
{
$('input').filter(function() { return /globalForm:tblRondas.+txtJug/.test(this.id) } ).autocomplete({
        serviceUrl : '/clublyf/jsonServlet?origen=torneo',
        transformResult : function(response) {
            return {
                // must convert json to javascript object before process
                suggestions : $.map($.parseJSON(response), function(item) {
                    return {
                        value: item.jugApellido + ' ' + item.jugNombre, data: item.jugId
                    };
                })
            };
        },
        showNoSuggestionNotice:false,
        
        onSelect: function (value) 
        {
            var hdnId = this.id.replace('txt','hdn');

            document.getElementById(hdnId).value = value.data;
        },
        
        onInvalidateSelection: function ()
        {
            var hdnId = this.id.replace('txt','hdn');
            
            document.getElementById(hdnId).value = '';
        }
    
    });
}

function iniciarAutosugestCR()
{
$('input').filter(function() { return /globalForm:tblJugadoresCR.+txtJugadorCR/.test(this.id) } ).autocomplete({
        serviceUrl : '/clublyf/jsonServlet?origen=todos',
        transformResult : function(response) {
            return {
                // must convert json to javascript object before process
                suggestions : $.map($.parseJSON(response), function(item) {
                    return {
                        value: item.jugApellido + ' ' + item.jugNombre, data: item.jugId
                    };
                })
            };
        },
        showNoSuggestionNotice:false,
        
        onSelect: function (value) 
        {
            var hdnId = this.id.replace('txt','hdn');
            var outId = this.id.replace('txt','ids');
            
            document.getElementById(hdnId).value = value.data;
            document.getElementById(outId).innerHTML = value.data;            
        },
        
        onInvalidateSelection: function ()
        {
            var hdnId = this.id.replace('txt','hdn');
            var outId = this.id.replace('txt','ids');
            
            document.getElementById(hdnId).value = '';
            document.getElementById(outId).innerHTML = '';
        }
    
    });
}

function handleKeyPressPuntaje(e, objeto)
{
    if(e.which == 46 || e.charCode == 46)
    {
        e.preventDefault();
        objeto.value = ',';
    }
}

function handleKeyPressResultado(e, objeto)
{
    if(e.which == 68 || e.charCode == 68 ||
       e.which == 100 || e.charCode == 100)
    { 
        e.preventDefault();
        objeto.value = '1/2';
    }
    else
    {
        // Evita valores inválidos
        // console.info('Valor : [' + e.which + ']');
        if (e.which != 43 && e.which != 45 && e.which != 48 && e.which != 49)
        {
            e.preventDefault();
            objeto.value = '';                
        }
    }
}

function cmdProcesar()
{
    var mensajes = validarPartidas();
    
    if (mensajes != '')
    {
        alert(mensajes);
        return false;
    }
    else
    {
        return true;
    }
}

function cmdGuardar()
{
    var mensajes = validarPartidas();
    
    if (mensajes != '')
    {
        alert(mensajes);
        return false;
    }
    else
    {
        return true;
    }
}

// Valida los datos ingresados en las partidas, y prepara los datos
// para que puedan ser leidos por el server

function validarPartidas()
{
    var cRonda = 0;   // Contador de rondas
    var cPartida = 0; // Contador de partidas
    var corte = false;
    
    var txtJugadorBlancas, txtJugadorNegras;
    var hdnJugadorBlancas, hdnJugadorNegras;
    
    var txtResultadoBlancas, txtResultadoNegras;
    var hdnResultadoBlancas, hdnResultadoNegras;
    var hdnTipoResultadoBlancas, hdnTipoResultadoNegras;
    
    var mensajes = ''; // Mensajes de error, en caso de haberlos.
    var strRonda, strPartida;
    
    do // Recorre las rondas
    {
        console.log('RONDA ' + cRonda + '\n');
        
        cPartida = 0;
        
        while (existePartida(cRonda, cPartida))  // Recorre las partidas
        {
            txtJugadorBlancas = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':txtJugBlancas');
            txtJugadorNegras = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':txtJugNegras');
            
            hdnJugadorBlancas = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnJugBlancas');
            hdnJugadorNegras = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnJugNegras');
            
            txtResultadoBlancas = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':txtResultBlancas');
            txtResultadoNegras = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':txtResultNegras');
            
            hdnResultadoBlancas = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnResultBlancas');
            hdnResultadoNegras = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnResultNegras');
            
            hdnTipoResultadoBlancas = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnTipoResultBlancas');
            hdnTipoResultadoNegras = document.getElementById('globalForm:tblRondas:' + cRonda + ':sbtbl:' + cPartida + ':hdnTipoResultNegras');
            
            strRonda = cRonda + 1;
            strPartida = cPartida + 1;
            
            console.log(txtJugadorBlancas.value + '(' + hdnJugadorBlancas.value + ') vs ' + txtJugadorNegras.value + '(' + hdnJugadorNegras.value + ') : ' + 
                        txtResultadoBlancas.value + '-' + txtResultadoNegras.value + '[' +
                        hdnResultadoBlancas.value + '-' + hdnResultadoNegras.value + '] [' +
                        hdnTipoResultadoBlancas.value + '-' + hdnTipoResultadoNegras.value + ']');
            
            // Valida que estén todos los datos cargados en las cajas de texto
            if (!(txtJugadorBlancas != null && txtJugadorBlancas.value.trim() != '' && txtJugadorBlancas.value.trim() != '?' && hdnJugadorBlancas.value != '') ||
                !(txtJugadorNegras != null && txtJugadorNegras.value.trim() != '' && txtJugadorNegras.value.trim() != '?' && hdnJugadorNegras.value != '')
               )
            {
                //mensajes += 'Faltan cargar jugadores en una o más partidas.';
                mensajes += 'Falta jugador en Ronda ' + strRonda + ' Partida ' + strPartida + '\n';
            }
            
            if (!(txtResultadoBlancas != null && txtResultadoBlancas.value.trim() != '' && resultadoValido(txtResultadoBlancas.value.trim())) ||
                !(txtResultadoNegras != null && txtResultadoNegras.value.trim() != '' && resultadoValido(txtResultadoNegras.value.trim()))
               )
           {
                mensajes += 'Resultado inválido en Ronda ' + strRonda + ' Partida ' + strPartida + '\n';
           }
           
           // Valida inconsistencias
           
           if (hdnJugadorBlancas.value == hdnJugadorNegras.value)
           {
               mensajes += 'Partida con el mismo jugador en Blancas y Negras, en Ronda ' + strRonda + ' Partida ' + strPartida + '\n';
           }
           else
           {
               if (hdnJugadorBlancas.value != '0' && jugadorEnRonda(hdnJugadorBlancas.value, cRonda) > 1)
               {
                   mensajes += 'El jugador [' + txtJugadorBlancas.value + '] se repite en la Ronda ' + strRonda + '\n';
               }
               if (hdnJugadorNegras.value != '0' && jugadorEnRonda(hdnJugadorNegras.value, cRonda) > 1)
               {
                   mensajes += 'El jugador [' + txtJugadorNegras.value + '] se repite en la Ronda ' + strRonda + '\n';
               }
           }
           
           if (mensajes != '')
           {
               corte = true;
               break;
           }
           
           // Carga las variables hdn con el resultado y tipo de resultado.
           
           if (txtResultadoBlancas.value == '1' || txtResultadoBlancas.value == '+')
               hdnResultadoBlancas.value = '1';
           else if (txtResultadoBlancas.value == '0' || txtResultadoBlancas.value == '-')
               hdnResultadoBlancas.value = '0';
           else if (txtResultadoBlancas.value == '1/2')
               hdnResultadoBlancas.value = '2';
               
           if (txtResultadoNegras.value == '1' || txtResultadoNegras.value == '+')
               hdnResultadoNegras.value = '1';
           else if (txtResultadoNegras.value == '0' || txtResultadoNegras.value == '-')
               hdnResultadoNegras.value = '0';
           else if (txtResultadoNegras.value == '1/2')
               hdnResultadoNegras.value = '2';
           
           // Carga el BYE (id del jugador = 0)
           if (hdnJugadorBlancas.value == '0' || hdnJugadorNegras.value == '0')
           {
               hdnTipoResultadoBlancas.value = '3';
               hdnTipoResultadoNegras.value = '3';
           }
           else
           {
               if (txtResultadoBlancas.value == '+' || txtResultadoBlancas.value == '-')
                   hdnTipoResultadoBlancas.value = '2'; // Ausencia
               else
                   hdnTipoResultadoBlancas.value = '1'; // Normal

               if (txtResultadoNegras.value == '+' || txtResultadoNegras.value == '-')
                   hdnTipoResultadoNegras.value = '2'; // Ausencia
               else
                   hdnTipoResultadoNegras.value = '1'; // Normal
           }

           console.log(txtJugadorBlancas.value + '(' + hdnJugadorBlancas.value + ') vs ' + txtJugadorNegras.value + '(' + hdnJugadorNegras.value + ') : ' + 
            txtResultadoBlancas.value + '-' + txtResultadoNegras.value + '[' +
            hdnResultadoBlancas.value + '-' + hdnResultadoNegras.value + '] [' +
            hdnTipoResultadoBlancas.value + '-' + hdnTipoResultadoNegras.value + ']');

           cPartida++;
           
        } // Fin bucle PARTIDAS
        
        if (!existePartida(cRonda, 0))
            corte = true;
            
        // aumentar ronda
        cRonda++;
    }
    while (!corte) // Fin bucle RONDAS
    
    return mensajes;
}

// Evalua si existe la partida para la ronda y numero de partida dados.
function existePartida(ronda, partida)
{
    return document.getElementById('globalForm:tblRondas:' + ronda + ':sbtbl:' + partida + ':txtJugBlancas') != null;
}

function resultadoValido(valorResultado)
{
    return valorResultado == '1' ||
           valorResultado == '0' ||
           valorResultado == '1/2' ||
           valorResultado == '+' ||
           valorResultado == '-';
}

// Devuelve la cantidad de veces que se encontró un jugador en una Ronda.

function jugadorEnRonda(idJugador, ronda)
{
    var hdnJugadorBlancas, hdnJugadorNegras;
    var cPartida = 0; // Contador de partidas
    var ret = 0;      // Retorno 
    
    while (existePartida(ronda, cPartida))  // Recorre las partidas
    {
        hdnJugadorBlancas = document.getElementById('globalForm:tblRondas:' + ronda + ':sbtbl:' + cPartida + ':hdnJugBlancas');
        hdnJugadorNegras = document.getElementById('globalForm:tblRondas:' + ronda + ':sbtbl:' + cPartida + ':hdnJugNegras');
        
        if (hdnJugadorBlancas.value == idJugador || hdnJugadorNegras.value == idJugador)
        {
            ret++;
        }
        cPartida++;
    }
    return ret;
}
