/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.DAO.SolicitudDAO;
import modelo.DTO.Salida;
import modelo.DTO.Solicitud;

/**
 * REST Web Service
 *
 * @author roberto
 */
@Path("/Solicitudes")
public class SolicitudREST {

    @Context
    private UriInfo context;
    Gson gson;
    SolicitudDAO sdao;
    Salida salida;
    /**
     * Creates a new instance of SolicitudREST
     */
    public SolicitudREST() {
        gson=new Gson();
        sdao=new SolicitudDAO();
        salida=new Salida();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registrar(String json){
        Solicitud solicitud=gson.fromJson(json, Solicitud.class);
        salida=sdao.agregar(solicitud);
        return gson.toJson(salida);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarSolicitudes(){
        /*Salida s=new Salida();
        s.setEstatus("OK");
        s.setMensaje("Hola");*/
        Object objeto=sdao.consultaGeneral();
        return gson.toJson(objeto);
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarSolicitud(@PathParam("id") int idSolicitud){
       Object objeto=sdao.consultaIndividual(idSolicitud);
       return gson.toJson(objeto);
    } 
    /*
    @GET
    @Path("/Alumno/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarSolicitudPorAlumno(@PathParam("id") int idAlumno){
       Object objeto=sdao.consultarPorAlumno(idAlumno);
       return gson.toJson(objeto);
    } 
    */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminarSolicitud(@PathParam("id") int idSolicitud){
        salida=sdao.eliminar(idSolicitud);
        return gson.toJson(salida);
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String modificarSolicitud(String json){
        Solicitud solicitud=gson.fromJson(json, Solicitud.class);
        salida=sdao.modificar(solicitud);
        return gson.toJson(salida);
    }
    
}
