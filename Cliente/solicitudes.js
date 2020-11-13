var op="";
var json={idSolicitud:null,
        fechaRegistro:null,
        fechaAtencion:"",
        tituloProyecto:null,
        estatus:null,
        opcion:{
            idOpcion:null,
            nombre:null
        },
        administrativo:{
            idAdministrativo:null,
            nombre:null       
        },
        alumno:{ 
            idAlumno:null,
            noControl:null,
            nombre:null,
            carrera:{
                idCarrera:null,
                nombre:null
            }
        },
        tipoUsuario:null
    };

function inicializarDivs(){
    document.getElementById("listadoIndividual").style.display='none';
    document.getElementById("listadoGeneral").style.display='block';
    consultaGeneral();
}
function consultaGeneral(){
    var url="http://localhost:8090/TitulaTECREST/Solicitudes";
    var ajax=new XMLHttpRequest();

    ajax.open("get",url,true);
    ajax.onreadystatechange=function(){
        if(this.status=200 && this.readyState==4)
        generarTabla(this.responseText);
    };
    ajax.send();
}
function generarTabla(respuesta){
    limpiarTabla();
    var solicitudes=JSON.parse(respuesta);
    if(solicitudes.length>0){
        var table=document.getElementById("datos");
        for(i=0;i<solicitudes.length;i++){
            var s=solicitudes[i];
            var tr=document.createElement("tr");
            for(p in s){
                if(typeof s[p]=='object'){
                    var o=s[p];
                    for(p1 in o){
                        if(typeof o[p1]=='object'){
                            var oo=o[p1];
                            for(p2 in oo){
                            var td=document.createElement("td");
                            var texto=document.createTextNode(oo[p2]);
                            td.appendChild(texto);
                            tr.appendChild(td);
                            }
                        }
                        else{
                            var td=document.createElement("td");
                            var texto=document.createTextNode(o[p1]);
                            td.appendChild(texto);
                            tr.appendChild(td);
                        }
                    }
                }
            else{
                if(s[p]!=""){
                    var td=document.createElement("td");
                    var texto=document.createTextNode(s[p]);
                    td.appendChild(texto);
                    tr.appendChild(td);
                }
            }
            }
            var link=crearlink(solicitudes[i].idSolicitud,"editar");
            var td=document.createElement("td");
            td.appendChild(link);
            tr.appendChild(td);
            link=crearlink(solicitudes[i].idSolicitud,"eliminar");
            td=document.createElement("td");
            td.appendChild(link);
            tr.appendChild(td);
            table.appendChild(tr);
            
        }
   }
   else{
      alert(solicitudes.mensaje);
   }
}
function crearlink(id,operacion){
    var link=document.createElement("a");
    link.setAttribute("href","javascript:"+operacion+"("+id+")");
    var img=document.createElement("img");
    img.setAttribute("src","./img/"+operacion+".png");
    link.appendChild(img);
    return link;
}
function limpiarTabla(){
    var table=document.getElementById("datos");
    for(i=table.rows.length-1;i>0;i--){
        table.removeChild(table.rows[i]);
    }
}
function consultarOpciones(){
    var url="http://localhost:8090/TitulaTECREST/Opciones";
    var ajax=new XMLHttpRequest();
    ajax.open("get",url,true);
    ajax.onreadystatechange=function(){
        if(this.status=200 && this.readyState==4)
        llenarCombo(this.responseText);
    };
    ajax.send();
}
function llenarCombo(respuesta){
    limpiarCombo();
    var opciones=JSON.parse(respuesta);
    var combo=document.getElementById("opcion");
    for(i=0;i<opciones.length;i++){
        var option=document.createElement("option");
        var texto=document.createTextNode(opciones[i].nombre);
        option.appendChild(texto);
        option.setAttribute("value",opciones[i].idOpcion);
        combo.appendChild(option);
    }
}
function nuevo(){
    op="c";
    document.getElementById("listadoIndividual").style.display='block';
    reset();
    consultarOpciones();
    
}
function limpiarCombo(){
    var combo=document.getElementById("opcion");
    for(i=combo.options.length-1;i>0;i--){
        combo.removeChild(combo.options[i]);
    } 
}
function cancelar(){
    op="";
    document.getElementById("listadoIndividual").style.display='none';
}
function guardar(){
    switch(op){
        case "c":
            insertar();
            break;
        case "u":
            modificar();
            break;
    }
}
function insertar(){
    var url="http://localhost:8090/TitulaTECREST/Solicitudes";
    json.alumno.idAlumno=document.getElementById("idAlumno").value;
    json.tituloProyecto=document.getElementById("tituloProyecto").value;
    json.opcion.idOpcion=document.getElementById("opcion").options[document.getElementById("opcion").options.selectedIndex].value;
    //alert(JSON.stringify(json))
    var ajax=new XMLHttpRequest();
    ajax.open("post",url,true);
    ajax.onreadystatechange=function(){
        if(this.status=200 && this.readyState==4)
        var respuesta=JSON.parse(this.responseText);
        alert(respuesta.mensaje);
        inicializarDivs()
    };
    ajax.setRequestHeader("Content-Type","application/json");
    ajax.send(JSON.stringify(json)); 
}
function modificar(){
    json.tituloProyecto=document.getElementById("tituloProyecto").value;
    json.opcion.idOpcion=document.getElementById("opcion").options[document.getElementById("opcion").options.selectedIndex].value;
    //alert(JSON.stringify(json))
    var url="http://localhost:8090/TitulaTECREST/Solicitudes";
    var ajax=new XMLHttpRequest();
    ajax.open("put",url,true);
    ajax.onreadystatechange=function(){
        if(this.status=200 && this.readyState==4)
        var respuesta=JSON.parse(this.responseText);
        alert(respuesta.mensaje);
        inicializarDivs()
    };
    ajax.setRequestHeader("Content-Type","application/json");
    ajax.send(JSON.stringify(json)); 
}
function eliminar(id){
    if(confirm("¿Estas seguro de eliminar la solicitud con folio:"+id+"?")){
        var url="http://localhost:8090/TitulaTECREST/Solicitudes/"+id;
        var ajax=new XMLHttpRequest();
        ajax.open("delete",url,true);
        ajax.onreadystatechange=function(){
        if(this.status=200 && this.readyState==4)
            var respuesta=JSON.parse(this.responseText);
            alert(respuesta.mensaje);
            inicializarDivs()
        };
        ajax.send();         
    }
}
function reset(){
    document.getElementById("idAlumno").value="";
    document.getElementById("tituloProyecto").value="";
    document.getElementById("opcion").options[0].selected=true;
    mostrarDivs("none");
}
function mostrarDivs(valor){
    document.getElementById("folio").style.display=valor;
    document.getElementById("complemento").style.display=valor;
    document.getElementById("coordinador").style.display=valor;
}
function editar(id){
    consultarOpciones();
    op="u";
    var url="http://localhost:8090/TitulaTECREST/Solicitudes/"+id;
    var ajax=new XMLHttpRequest();
    ajax.open("get",url,true);
    ajax.onreadystatechange=function(){
    if(this.status=200 && this.readyState==4)
        //var respuesta=JSON.parse(this.responseText);
        cargarSolicitud(this.responseText);
    };
    ajax.send();
}
function cargarSolicitud(respuesta){
    //alert(respuesta);
    var obj=JSON.parse(respuesta);
    if(obj.mensaje){
        alert(respuesta.mensaje);
    }
    else{
        json=obj;
        document.getElementById("idSolicitud").value=json.idSolicitud;
        document.getElementById("tituloProyecto").value=json.tituloProyecto;
        document.getElementById("idSolicitud").value=json.idSolicitud;
        //var combo=document.getElementById("opcion");
        for(i=0;i<document.getElementById("opcion").options.length;i++){
            //alert(combo.options[i].value);
            if(document.getElementById("opcion").options[i].text==json.opcion.nombre){
                //alert("entro"+combo.options[i].text);
                document.getElementById("opcion").options[i].selected=true;
                break;
            }    
        }
        document.getElementById("fechaRegistro").value=json.fechaRegistro;
        document.getElementById("fechaAtencion").value=json.fechaAtencion;
        document.getElementById("estatus").value=json.estatus;
        document.getElementById("idAlumno").value=json.alumno.idAlumno;
        document.getElementById("nombreAlumno").value=json.alumno.nombre;
        document.getElementById("nocontrol").value=json.alumno.noControl;
        document.getElementById("idCarrera").value=json.alumno.carrera.idCarrera;
        document.getElementById("nombreCarrera").value=json.alumno.carrera.nombre;
        document.getElementById("idAdministrativo").value=json.administrativo.idAdministrativo;
        document.getElementById("nombreAdministrativo").value=json.administrativo.nombre;
        json.tipoUsuario="E";
        mostrarDivs("block");
        document.getElementById("listadoIndividual").style.display='block';
    }
}
