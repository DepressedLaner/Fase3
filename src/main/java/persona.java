
import com.google.gson.Gson;
import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class persona
{
   
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private char genero;
    private String contraseña;
    private String email;

    private static List <persona> personas = new ArrayList<>();

    private static String ARCHIVO = "personas.json";

    public persona(int id, String nombre, String apellido, int edad, char genero, String contraseña, String email) 
    {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.contraseña = contraseña;
        this.email = email;
    }
    
    public persona() {}
    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public int getEdad() {return edad;}
    public void setEdad(int edad) {this.edad = edad;}
    public char getGenero() {return genero;}
    public void setGenero(char genero) {this.genero = genero;}
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public void agregaDatosIniciales()
    {
        try
        {
            File file = new File(ARCHIVO);
            persona semilla = new persona(1,"Adrian","Pech",20,'M',"0000","adrianlpz@gmail.com");
                
            if(file.canExecute() == false)
            {personas.add(semilla);}
            
            else
            {
                
                BufferedReader lector = new BufferedReader(new FileReader(file));
                StringBuilder json = new StringBuilder();

                String cadena;

                while ((cadena = lector.readLine()) != null)
                {
                    json.append(cadena);
                    Gson gson = new Gson();
                    persona persona = gson.fromJson(json.toString(), persona.class);
                    personas.add(persona);
                }
                
                if(personas.isEmpty())
                {personas.add(semilla);}
            }

            System.out.println("Los usuarios iniciales han sido guardados.");
        }
        catch(Exception e)
        {System.out.println("Todo marcha correctamente");}
    }
    
    public void creaPersona()
    {
        try
        {
            int id, edad;
            String nombre, apellido, ingresaGenero;
            char genero;
            String contraseña, email;
            String [] generos = {"F","M"};
            
            id = personas.size() + 1;
            nombre = JOptionPane.showInputDialog("Nombre del usuario:");
            apellido = JOptionPane.showInputDialog("Apellido del usuario:");
            email = JOptionPane.showInputDialog("Correo electrónico del usuario: ");
            contraseña = JOptionPane.showInputDialog("Contraseña con la que accederá el usuario:");
            edad = Integer.parseInt(JOptionPane.showInputDialog("Edad del usuario:"));
            ingresaGenero = (String) JOptionPane.showInputDialog(null,"Indica el género del usuario:\n (Usa F para Femenino y M para masculino)\n\n", 
                    "", JOptionPane.DEFAULT_OPTION, null, generos, generos[0]);
            genero = ingresaGenero.charAt(0);

            persona persona = new persona(id, nombre, apellido, edad, genero, contraseña, email);
            
            personas.add(persona);
            
            System.out.println("Se ha guardado correctamente el usuario en la lista personas.");
        }
        catch(Exception e)
        {System.out.println("Todo marcha correctamente");}
    }
    
    public void guardaPersona()
    {
        String jsonUsuario;
        
        try
        {
            Gson gson = new Gson();
                        
            FileWriter fileWriter = new FileWriter(ARCHIVO);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            
            for (int x = 0; x < personas.size(); x++)
            {
                jsonUsuario = gson.toJson(personas.get(x));
                printWriter.print(jsonUsuario);
            }
            
            printWriter.close();
            
            System.out.println("Los usuarios han sido guardados correctamente.");
        }
        catch (Exception e)
        {System.out.println("Todo marcha correctamente");}
    }

    public boolean ingresar(int id, String contraseña) throws Exception
    {
        try
        {
            boolean existe = personas.stream().anyMatch(x -> 
                x.getId() == id && x.getContraseña().equals(contraseña));
            return existe;
        }
        catch(Exception e)
        {throw new Exception("No se pudo validar al usuario.");}
    }
    
    public void cargarJSON()
    {
        try
        {
            File file = new File(ARCHIVO);
        
            BufferedReader lector = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();

            String cadena;

            System.out.println("Los usuarios encontrados en el archivo " + ARCHIVO + " son: ");
            System.out.println("");
            
            while ((cadena = lector.readLine()) != null)
            {
                json.append(cadena);
                Gson gson = new Gson();
                persona persona = gson.fromJson(json.toString(), persona.class);
                persona.despliega();
                System.out.println("");
            }
        }
        catch (Exception e)
        {System.out.println("Todo marcha correctamente");}
    }
    
    public void despliega()
    {
        try
        {
            System.out.println("ID del usuario: " + id);
            System.out.println("Nombre del usuario: " + nombre);
            System.out.println("Apellido del usuario: " + apellido);
            System.out.println("Edad del usuario: " + edad);
            System.out.println("Género del usuario: " + genero);
            System.out.println("Correo del usuario: " + email);
            System.out.println("Contraseña del usuario: " + contraseña);
        }
        catch (Exception e)
        {System.out.println("No se pudo mostrar el usuario por el error: " + e.getMessage());}
    }
    
    public void consultaUsuarios()
    {
        try
        {
            System.out.println("Se han registrado los siguientes usuarios: ");
            for (persona x : personas)
            {
                x.despliega();
                System.out.println("");
            }
            System.out.println("Se han terminado de mostrar todos los usuarios registrados.");
        }
        catch (Exception e)
        {System.out.println("No se pudieron desplegar los usuarios por el error: " + e.getMessage());}
    }
    
    public static void enviaMensaje()
    {
        try
        {
            String email = null, mensaje, fecha;
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID del usuario destino:"));
            String [] usuarios = {"Persona","Paciente","Médico"};
            String usuario = (String) JOptionPane.showInputDialog(null, "Por favor, selecciona "
                        + "el tipo de que corresponde al id", "Buscando id ...", JOptionPane.DEFAULT_OPTION, 
                        null, usuarios, usuarios[0]);
            
            switch(usuario)
            {
                case "Persona":
                {
                    boolean existe = personas.stream().anyMatch(x -> x.getId() == id);
                    if (existe == false)
                    {throw new Exception("No existe un usuario con dicho ID.");}
                    persona persona = personas.get((id-1));
                    email = persona.email;
                    break;
                }
                
                case "Paciente":
                {
                    boolean existe = paciente.getPacientes().stream().anyMatch(x -> x.getId() == id);
                    if (existe == false)
                    {throw new Exception("No existe un paciente con dicho ID.");}
                    paciente destino = paciente.getPacientes().get((id-1));
                    email = destino.getEmail();
                    break;
                }
                
                case "Médico":
                {
                    boolean existe = medico.getMedicos().stream().anyMatch(x -> x.getId() == id);
                    if (existe == false)
                    {throw new Exception("No existe un médico con dicho ID.");}
                    medico destino = medico.getMedicos().get((id-1));
                    email = destino.getEmail();
                    break;
                }
            }
            
            mensaje = JOptionPane.showInputDialog("Ingresa el mensaje que deseas enviar: ");
            fecha = LocalDate.now().toString();
            
            System.out.println("Se ha enviado el siguiente mensaje:");
            System.out.println("Destinatario: " + email);
            System.out.println("Mensaje: " + mensaje);
            System.out.println("Fecha de envío: " + fecha);
        }
        catch (Exception e)
        {System.out.println("No se pudo enviar el mensaje por el error: " + e.getMessage());}        
    }
}