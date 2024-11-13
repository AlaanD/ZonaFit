package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;
import java.util.Scanner;

public class AppZonaFit {

    public static void main(String[] args) {
        AppZonaFit.appZonaFit();
    }

    private static void appZonaFit(){
        var salir = false;
        var consola = new Scanner(System.in);
        IClienteDAO clienteDAO = new ClienteDAO();
        while(!salir){
            try{
                AppZonaFit.mostrarMenu();
                var opcion = Integer.parseInt(consola.nextLine().strip());
                salir = AppZonaFit.ejecutarMenu(opcion, consola, clienteDAO);
            }catch (Exception e){
                System.out.println("Error, ingrese un numero entero: " + e.getMessage() + "\n");
            }
        }
        consola.close();
    }

    private static void mostrarMenu(){
        System.out.println("*** Aplicacion Zona Fit ***");
        System.out.print("""
                Menu:
                1. Listar Clientes.
                2. Buscar Cliente.
                3. Agregar Cliente.
                4. Modificar Cliente.
                5. Eliminar Cliente.
                6. Salir.
                Ingrese una opcion:\s""");
    }

    private static boolean ejecutarMenu(int opcion, Scanner consola, IClienteDAO clienteDAO){
        System.out.println();
        switch (opcion){
            case 1 -> AppZonaFit.listarCliente(clienteDAO);
            case 2 -> AppZonaFit.buscarCliente(consola, clienteDAO);
            case 3 -> AppZonaFit.agregarCliente(consola, clienteDAO);
            case 4 -> AppZonaFit.modificarCliente(consola, clienteDAO);
            case 5 -> AppZonaFit.eliminarCliente(consola, clienteDAO);
            case 6 -> {
                System.out.println("Hasta Luego!!!");
                return true;
            }
            default -> System.out.println("Opcion invalida: " + opcion);
        }
        System.out.println();
        return false;
    }

    private static void listarCliente(IClienteDAO clienteDAO){
        System.out.println("*** Lista Clientes ***");
        clienteDAO.listarCliente().forEach(System.out::println);
    }

    private static void buscarCliente(Scanner consola, IClienteDAO clienteDAO){
        System.out.print("Introduce el id del Cliente a buscar: ");
        try{
            var id = Integer.parseInt(consola.nextLine().strip());
            var cliente = new Cliente(id);
            var encontrado = clienteDAO.buscarClientePorId(cliente);
            if (encontrado){
                System.out.println("\nCliente encontrado: " + cliente);
            }else
                System.out.println("\nNo se encontro el cliente");
        }catch (Exception e){
            System.out.println("\nError, ingrese un numero entero: " + e.getMessage());
        }
    }

    private static void agregarCliente(Scanner consola, IClienteDAO clienteDAO) {
        var cliente = new Cliente();
        var exito = false;
        try{
            System.out.print("Ingrese el nombre del nuevo Cliente: ");
            var nombre = consola.nextLine().strip();
            System.out.print("Ingrese el apellido del nuevo Cliente: ");
            var apellido = consola.nextLine().strip();
            System.out.print("Ingrese la membresia del nuevo Cliente: ");
            var membresia = Integer.parseInt(consola.nextLine().strip());
            cliente = new Cliente(nombre, apellido, membresia);
            exito = clienteDAO.agregarCliente(cliente);
            if (exito){
                System.out.println("\nCliente agregado exitosamente");
            }else
                System.out.println("\nNo se puedo agregar el nuevo cliente");
        }catch (Exception e){
            System.out.println("\nError, ingrese un numero entero: " + e.getMessage());
        }
    }

    private static void modificarCliente(Scanner consola, IClienteDAO clienteDAO) {
        var cliente = new Cliente();
        var exito = false;
        try{
            System.out.print("Ingrese el id del Cliente a modificar: ");
            var id = Integer.parseInt(consola.nextLine().strip());
            System.out.print("Ingrese el nombre del Cliente a modificar: ");
            var nombre = consola.nextLine().strip();
            System.out.print("Ingrese el apellido del Cliente a modificar: ");
            var apellido = consola.nextLine().strip();
            System.out.print("Ingrese la membresia del Cliente a modificar: ");
            var membresia = Integer.parseInt(consola.nextLine().strip());
            cliente = new Cliente(id, nombre, apellido, membresia);
            exito = clienteDAO.modificarCliente(cliente);
            if (exito){
                System.out.println("\nCliente modificado exitosamente: " + cliente);
            }else
                System.out.println("\nNo existe cliente con ese id: " + cliente.getId());
        }catch (Exception e){
            System.out.println("\nError, ingrese un numero entero: " + e.getMessage());
        }
    }

    private static void eliminarCliente(Scanner consola, IClienteDAO clienteDAO) {
        var exito = false;
        var cliente = new Cliente();
        try{
            System.out.print("Ingrese el id del Cliente a eliminar: ");
            var id = Integer.parseInt(consola.nextLine().strip());
            cliente = new Cliente(id);
            exito = clienteDAO.eliminarCliente(cliente);
            if (exito){
                System.out.println("\nCliente eliminado");
            }else
                System.out.println("\nNo existe cliente con ese id: " + cliente.getId());
        }catch (Exception e){
            System.out.println("\nError, ingrese un numero entero: " + e.getMessage());
        }
    }
}
