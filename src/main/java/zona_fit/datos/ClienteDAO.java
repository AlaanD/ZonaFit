package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{
    @Override
    public List<Cliente> listarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        //permite preparar la sentencia sql
        PreparedStatement ps;
        //para almacenar las consulta sql
        ResultSet rs;
        Connection conexion = Conexion.getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            //preparamos la sentencia sql para ser ejecutada
            ps = conexion.prepareStatement(sql);
            //ejecutamos la sentencia y la almacenamos
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }finally {
            try {
                conexion.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection conexion = Conexion.getConexion();
        //el signo de pregunta indica que va a recibir un parametro
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try{
            ps = conexion.prepareStatement(sql);
            //vamos a pasar un parametro por eso primero el 1 y luego pasamos el parametro
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }finally {
            try {
                conexion.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        //no creamos un objeto ResultSet porque solo se usa cuando recuperamos informacion
        PreparedStatement ps;
        Connection conexion = Conexion.getConexion();
        var sql = "INSERT INTO cliente(nombre, apellido, membresia) VALUES(?, ?, ?)";
        try{
            ps = conexion.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }finally {
            try{
                conexion.close();
            }catch (Exception e){
                System.out.println("Error al cerrar coneccion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection conexion = Conexion.getConexion();
        var sql = "UPDATE cliente SET nombre = ?, apellido = ?, membresia = ? WHERE id = ?";
        try{
            ps = conexion.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            // executeUpdate verificar el número de filas afectadas
            var filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }finally {
            try{
                conexion.close();
            }catch (Exception e){
                System.out.println("Error al cerrar coneccion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection conexion = Conexion.getConexion();
        var sql = "DELETE FROM cliente WHERE id = ?";
        try{
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            var filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }finally {
            try{
                conexion.close();
            }catch (Exception e){
                System.out.println("Error al cerrar coneccion: " + e.getMessage());
            }
        }
        return false;
    }

}
