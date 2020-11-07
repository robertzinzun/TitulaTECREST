/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import modelo.DTO.Salida;
import modelo.DTO.Solicitud;

/**
 *
 * @author roberto
 */
public class SolicitudDAO {
    public Salida agregar(Solicitud solicitud){
        String sql="{call sp_registrar_solicitud(?,?,?,?,?)}";
        Salida salida=new Salida();
        try{
            CallableStatement cs=ConexionBD.getInstance().getConnection().prepareCall(sql);
            cs.setString(1,solicitud.getTituloProyecto());
            cs.setInt(2, solicitud.getOpcion().getIdOpcion());
            cs.setInt(3, solicitud.getAlumno().getIdAlumno());
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.execute();
            salida.setEstatus(cs.getString(4));
            salida.setMensaje(cs.getString(5));
            cs.close();
            ConexionBD.getInstance().cerrar();
            
        }
        catch(SQLException ex){
            System.out.println("Error al ejecutar el sp_registrar_solicitud:"+ex.getMessage());
        }
        return salida;
    }
    public Salida modificar(Solicitud solicitud){
        return null;
    }
    public Salida eliminar(int idSolicitud){
        return null;
    }
    public Object consultaGeneral(){
        return null;
    }
    public Object consultaIndividual(int idSolicitud){
        return null;
    }
    
}
