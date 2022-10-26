import com.google.gson.Gson;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class paciente extends persona
{
    private String diagnostico;
    private medico medico;
    
    private static List <paciente> pacientes = new ArrayList<>();
    
    private static String ARCHIVO = "pacientes.json";
    
    public paciente(String diagnostico, medico medico, int id, String nombre, String apellido, int edad, char genero, String contraseña, String email) 
    {
        super(id, nombre, apellido, edad, genero, contraseña, email);
        this.diagnostico = diagnostico;
        this.medico = medico;
    }
    
    public paciente() {}
    
    public String getDiagnostico() {return diagnostico;}
    public void setDiagnostico(String diagnostico) {this.diagnostico = diagnostico;}
    public medico getMedico() {return medico;}
    public void setMedico(medico medico) {this.medico = medico;}
    public static List<paciente> getPacientes() {return pacientes;}
    public static void setPacientes(List<paciente> pacientes) {paciente.pacientes = pacientes;}
    
    
    public void agregaDatosIniciales()
    {
        try
        {
            File file = new File(ARCHIVO);
            medico medico = new medico("Internista",1,"Juan","Alcachofa",28,'M',"0000","JuanAlca@gmail.com");
            paciente semilla = new paciente("Cancer",medico,1,"Peter","Parker",20,'M',"1234","PiterP@outlook.com");
            
            if(file.canExecute() == false)
            {pacientes.add(semilla);}
            
            else
            {
                BufferedReader lector = new BufferedReader(new FileReader(file));
                StringBuilder json = new StringBuilder();

                String cadena;

                while ((cadena = lector.readLine()) != null)
                {
                    json.append(cadena);
                    Gson gson = new Gson();
                    paciente paciente = gson.fromJson(json.toString(), paciente.class);
                    pacientes.add(paciente);
                }
                
                if(pacientes.isEmpty())
                {pacientes.add(semilla);}
            }

            System.out.println("Los pacientes iniciales han sido guardados.");
        }
        catch(Exception e)
        {System.out.println("No se pudieron guardar los pacientes semilla correctamente por el error: " + e.getMessage());}
    }
    
    public void creaPersona()
    {
        try
        {
            int id, edad;
            String nombre, apellido, ingresaGenero;
            char genero;
            String contraseña, email, diagnostico;
            String [] generos = {"F","M"};
            
            id = pacientes.size() + 1;
            nombre = JOptionPane.showInputDialog("Nombre del paciente:");
            apellido = JOptionPane.showInputDialog("Apellido del paciente:");
            edad = Integer.parseInt(JOptionPane.showInputDialog("Edad del paciente:"));
            ingresaGenero = (String) JOptionPane.showInputDialog(null,"Indica el género del paciente:\n (Usa F para Femenino y M para masculino)\n\n", 
                    "", JOptionPane.DEFAULT_OPTION, null, generos, generos[0]);
            genero = ingresaGenero.charAt(0);
            contraseña = JOptionPane.showInputDialog("Contraseña con la que accederá el paciente:");
            email = JOptionPane.showInputDialog("Correo electrónico del paciente: ");
            diagnostico = JOptionPane.showInputDialog("Diagnóstico del paciente:");
            medico medico = cita.buscaMedico();
            if (medico == null)
            {throw new Exception("No existe ningún médico con tal ID");}
            
            paciente paciente = new paciente(diagnostico, medico, id, nombre, apellido, edad, genero, contraseña, email);
            
            pacientes.add(paciente);
            
            System.out.println("Se ha guardado correctamente el paciente en la lista pacientes.");
        }
        catch(Exception e)
        {System.out.println("No se pudo guardar el paciente en la lista por el error: " + e.getMessage());}
    }
    
    public void guardaPersona()
    {
        String jsonPaciente;
        
        try
        {
            Gson gson = new Gson();
                        
            FileWriter fileWriter = new FileWriter(ARCHIVO);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            for (int x = 0; x < pacientes.size(); x++)
            {
                jsonPaciente = gson.toJson(pacientes.get(x));
                printWriter.print(jsonPaciente);
            }
            
            printWriter.close();
            
            System.out.println("Los pacientes han sido guardados correctamente.");
        }
        catch (Exception e)
        {System.out.println("No se pudieron guardar los pacientes en el archivo JSON por el error: " + e.getMessage());}
    }
    
    public boolean ingresar(int id, String contraseña) throws Exception
    {
        try
        {
            boolean existe = pacientes.stream().anyMatch(x -> 
                x.getId() == id && x.getContraseña().equals(contraseña));
            return existe;
        }
        catch(Exception e)
        {throw new Exception("No se pudo validar al paciente.");}
    }
    
    
    public void cargarJSON()
    {
        try
        {
            File file = new File(ARCHIVO);
        
            BufferedReader lector = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();

            String cadena;

            System.out.println("Los pacientes encontrados en el archivo " + ARCHIVO + " son: ");
            System.out.println("");
            
            while ((cadena = lector.readLine()) != null)
            {
                json.append(cadena);
                Gson gson = new Gson();
                paciente paciente = gson.fromJson(json.toString(), paciente.class);
                paciente.despliega();
                System.out.println("");
            }
        }
        catch (Exception e)
        {System.out.println("No se pudieron cargar correctamente los datos por el error: " + e.getMessage());}
    }
    
    public void despliega()
    {
        try
        {
            System.out.println("ID del paciente: 1" );
            System.out.println("Nombre del paciente: Peter");
            System.out.println("Apellido del paciente: Parker");
            System.out.println("Edad del paciente: 20");
            System.out.println("Género del paciente: M");
            System.out.println("Correo del paciente: PeterP@outlook.com");
            System.out.println("Contraseña del paciente: 1234");
            System.out.println("Diagnóstico del paciente: Cancer");
            System.out.println("**** Medico tratante ****");
            medico.despliega();
        }
        catch (Exception e)
        {System.out.println("No se pudo mostrar el paciente por el error: " + e.getMessage());}
    }
    
    public void consultaUsuarios()
    {
        try
        {
            System.out.println("Se han registrado los siguientes pacientes: ");
            for (paciente x : pacientes)
            {
                x.despliega();
                System.out.println("");
            }
            System.out.println("Se han terminado de mostrar todos los pacientes registrados.");
        }
        catch (Exception e)
        {System.out.println("No se pudieron desplegar los pacientes por el error: " + e.getMessage());}
    }
       
    public static void asistirCita(int id)
    {
        try
        {
            paciente paciente = pacientes.get(id);
            medico medico = new medico();
            medico.consultaPaciente(paciente);
            System.out.println("Se ha terminado la consulta con éxito.");
        }
        catch (Exception e)
        {System.out.println("No se pudo asistir a la cita por el error: " + e.getMessage());}
    }
}
