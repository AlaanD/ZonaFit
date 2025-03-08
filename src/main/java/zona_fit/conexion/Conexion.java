package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion(){
        Connection conexion = null;
        /*
        el nombre de la base de datos que creamos
        y a la cual nos vamos a conectar
         */
        var baseDatos = "zona_fit_db";
        //jdbc:mysql://nombre_del_servidor:numero_puerto/nombre_base_datos
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "quantex9";
        try{
            //clase de conexion, cada BD tiene una distinta
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        }catch (Exception e) {
            System.out.println("Error al conectarnos a la BD: " + e.getMessage());
        }
        return conexion;
    }

}
