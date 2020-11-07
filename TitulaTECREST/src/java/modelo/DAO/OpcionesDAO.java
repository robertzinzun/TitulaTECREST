/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.DTO.Opcion;
import modelo.DTO.Salida;

/**
 *
 * @author roberto
 */
public class OpcionesDAO {
    public Object consultaGeneral(){
        String sql="select idOpcion,nombre from opciones where estatus='A'";
        Salida s=new Salida();
        ArrayList<Opcion> opciones=new ArrayList<Opcion>();
        try{
            Statement st=ConexionBD.getInstance().getConnection().createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                Opcion o=new Opcion();
                o.setIdOpcion(rs.getInt("idOpcion"));
                o.setNombre(rs.getString("nombre"));
                opciones.add(o);
            }
            rs.close();
            st.close();
            ConexionBD.getInstance().cerrar();
        }
        catch(SQLException ex){
            System.out.println("Error al ejecutar:"+sql+", "+ex.getMessage());
            s.setEstatus("Error:");
            s.setMensaje("Error al ejecutar:"+sql);
        }
        if(opciones.size()>0)
            return opciones;
        else{
            s.setEstatus("OK");
            s.setMensaje("No hay opciones registradas");
            return s;
        }            
    }
}
