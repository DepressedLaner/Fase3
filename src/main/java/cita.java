import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class cita 
{
    private int id;
    private String nombre;
    private String fecha;
    private String hora;
    private String motivo;
    private medico medico;
    private paciente paciente;
    
    private static List <cita> citas = new ArrayList<>();;
    
    private static String ARCHIVO = "citas.json";

    public cita(int id, String nombre, String fecha, String hora, String motivo, medico medico, paciente paciente) 
    {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.medico = medico;
        this.paciente = paciente;
    }
    
    public cita() {}
    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getFecha() {return fecha;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public String getHora() {return hora;}
    public void setHora(String hora) {this.hora = hora;}
    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}
    public medico getMedico() {return medico;}
    public void setMedico(medico medico) {this.medico = medico;}
    public paciente getPaciente() {return paciente;}
    public void setPaciente(paciente paciente) {this.paciente = paciente;}
    public static List<cita> getCitas() {return citas;}
    public static void setCitas(List<cita> citas) {cita.citas = citas;}

    
    public void agregaDatosIniciales()
    {
        try
        {
            File file = new File(ARCHIVO);
            medico medico = new medico("Internista",1,"Juan","Alcachofa",28,'M',"0000","JuanAlca@gmail.com");
            paciente paciente = new paciente("Cancer",medico,1,"Peter","Parker",20,'M',"1234","PiterP@outlook.com");
            cita semilla = new cita(1,"Cita 1",LocalDate.now().toString(),LocalTime.now().toString(),"Seguimiento", medico, paciente);
            if(file.canExecute() == false)
            {citas.add(semilla);}
            
            else
            {
                BufferedReader lector = new BufferedReader(new FileReader(file));
                StringBuilder json = new StringBuilder();

                String cadena;

                while ((cadena = lector.readLine()) != null)
                {
                    //Se guarda la línea
                    json.append(cadena);
                    Gson gson = new Gson();
                    cita cita = gson.fromJson(json.toString(), cita.class);
                    citas.add(cita);
                }
                
                if(citas.isEmpty())
                {citas.add(semilla);}
            }

            System.out.println("Las citas iniciales han sido guardadas.");
        }
        catch(Exception e)
        {System.out.println("No se pudieron guardar las citas semilla correctamente por el error: " + e.getMessage());}
    }
    
    public void creaCita()
    {
        try
        {
            int id;
            String nombre,fecha, hora, motivo;

            id = citas.size() + 1;
            nombre = JOptionPane.showInputDialog("Nombre de la cita:");
            motivo = JOptionPane.showInputDialog("Motivo de la cita:");
            fecha = LocalDate.now().toString();
            hora = LocalTime.now().toString();
            medico medico = buscaMedico();
            if (medico == null)
            {throw new Exception("No existe ningún médico con tal ID");}
            paciente paciente = buscaPaciente();
            if (paciente == null)
            {throw new Exception("No existe ningún paciente con tal ID");}
            
            cita cita = new cita(id, nombre, fecha, hora, motivo, medico, paciente);
            
            citas.add(cita);
            
            System.out.println("Se ha guardado correctamente la cita en la lista citas.");
        }
        catch(Exception e)
        {System.out.println("No se pudo guardar la cita en la lista por el error: " + e.getMessage());}
    }
    
    public static medico buscaMedico() 
    {
        medico medico = null;
            
        try
        {
            int id_medico = Integer.parseInt(JOptionPane.showInputDialog("ID del médico tratante:"));
            medico metodos_medico = new medico();
            boolean existe_medico = metodos_medico.getMedicos().stream().anyMatch(x -> x.getId() == id_medico);
            if(existe_medico == true)
            {
                System.out.println("Médico encontrado en la lista de medicos.");
                medico = metodos_medico.getMedicos().get(id_medico);
            }
            else
            {System.out.println("Médico no encontrado en la lista de medicos.");}
        }
        catch (Exception e)
        {System.out.println("No se pudo referenciar al médico por el error: " + e.getMessage());}
        
        return medico;
    }
    
    public static paciente buscaPaciente()
    {
        paciente paciente = null;
            
        try
        {
            int id_paciente = Integer.parseInt(JOptionPane.showInputDialog("ID del paciente:"));
            paciente metodos_paciente = new paciente();
            boolean existe_paciente = metodos_paciente.getPacientes().stream().anyMatch(x -> x.getId() == id_paciente);
            if(existe_paciente == true)
            {
                System.out.println("Paciente encontrado en la lista de pacientes.");
                paciente = metodos_paciente.getPacientes().get(id_paciente);
            }
            else
            {System.out.println("Paciente no encontrado en la lista de pacientes.");}
        }
        catch (Exception e)
        {System.out.println("No se pudo referenciar al paciente por el error: " + e.getMessage());}

        return paciente;
    }
    
    public void guardaCita()
    {
        String jsonCita;
        
        try
        {
            Gson gson = new Gson();
                        
            FileWriter fileWriter = new FileWriter(ARCHIVO);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            for (int x = 0; x < citas.size(); x++)
            {
                jsonCita = gson.toJson(citas.get(x));
                printWriter.print(jsonCita);
            }
            
            printWriter.close();
            
            System.out.println("Las citas han sido guardadas correctamente.");
        }
        catch (Exception e)
        {System.out.println("No se pudieron guardar las citas en el archivo JSON por el error: " + e.getMessage());}
    }
    
    public void cargarJSON()
    {
        try
        {
            File file = new File(ARCHIVO);
        
            BufferedReader lector = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();

            String cadena;

            System.out.println("Las citas encontrados en el archivo " + ARCHIVO + " son: ");
            System.out.println("");
            
            while ((cadena = lector.readLine()) != null)
            {
                json.append(cadena);
                Gson gson = new Gson();
                cita cita = gson.fromJson(json.toString(), cita.class);
                cita.despliega();
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
            System.out.println("ID de la cita: " + id);
            System.out.println("Nombre de la cita: " + nombre);
            System.out.println("Motivo de la cita: " + motivo);
            System.out.println("Fecha de la cita: " + fecha);
            System.out.println("Hora de la cita: " + hora);
            System.out.println("**** Medico tratante ****");
            medico.despliega();
            System.out.println("**** Paciente a asistir ****");
            paciente.despliega();
        }
        catch (Exception e)
        {System.out.println("No se pudo mostrar la cita por el error: " + e.getMessage());}
    }
    
    public static void consultaCitas()
    {
        try
        {
            System.out.println("Se han registrado las siguientes citas: ");
            for (cita x : citas)
            {
                x.despliega();
                System.out.println("");
            }
            System.out.println("Se han terminado de mostrar todas las citas registradas.");
        }
        catch (Exception e)
        {System.out.println("No se pudieron desplegar las citas por el error: " + e.getMessage());}
    }
    
    public static void buscaCita(int id, String usuario)
    {
        try
        {
            System.out.println("Se encontraron las siguientes citas para el " + usuario.toLowerCase() + " #" + id);
            switch(usuario)
            {                
                case "Paciente":
                {
                    List <cita> citasPaciente = citas.stream().filter(paciente -> paciente.getId() == id).collect(Collectors.toList());
                    for (cita x : citasPaciente)
                    {
                        x.despliega();
                        System.out.println("");
                    }
                    break;
                }
                
                case "Médico":
                {
                    List <cita> citasMedico = citas.stream().filter(medico -> medico.getId() == id).collect(Collectors.toList());
                    for (cita x : citasMedico)
                    {
                        x.despliega();
                        System.out.println("");                        
                    }
                    break;
                }
            }
            
            System.out.println("Se han terminado de mostrar las citas del " + usuario.toLowerCase() + 
                    " #" + id + ".");
        }
        catch (Exception e)
        {System.out.println("No se pudieron encontrar las citas por el error: " + e.getMessage());}
    }
}