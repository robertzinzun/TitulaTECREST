/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import ClienteWSSIE.Alumno;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.DTO.Salida;

/**
 * REST Web Service
 *
 * @author roberto
 */
@Path("/Alumnos")
public class AlumnosRest {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AlumnosRest
     */
    public AlumnosRest() {
    }

    /**
     * Retrieves representation of an instance of Servicios.AlumnosRest
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{nocontrol}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarAlumno(@PathParam("nocontrol")String nocontrol) {
        
        ClienteWSSIE.SIEService_Service service = new ClienteWSSIE.SIEService_Service();
        ClienteWSSIE.SIEService port = service.getSIEServicePort();
        Gson gson=new Gson();
        return gson.toJson(port.consultarAlumno(nocontrol));
        
        
    }

    /**
     * PUT method for updating or creating an instance of AlumnosRest
     * @param <error>
     * @param content representation for the resource
     */
    @PUT
    @Path("{nocontrol}")
    @Produces(MediaType.APPLICATION_JSON)
    public String putJson(@PathParam("nocontrol") String nocontrol){
        Salida s=new Salida();
        if(actualizarEstatusAlumno(nocontrol)){
            s.setEstatus("OK");
            s.setMensaje("Estatus modificado con exito");
        }
        else{
            s.setEstatus("Error");
            s.setMensaje("Error al modificar el estatus");
        }
        Gson gson=new Gson();
        return gson.toJson(s);
    }

    private static boolean actualizarEstatusAlumno(java.lang.String noControl) {
        ClienteWSSIE.SIEService_Service service = new ClienteWSSIE.SIEService_Service();
        ClienteWSSIE.SIEService port = service.getSIEServicePort();
        return port.actualizarEstatusAlumno(noControl);
    }


    
}
