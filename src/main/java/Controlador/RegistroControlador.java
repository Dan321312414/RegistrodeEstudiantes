package Controlador;

import Modelo.Estudiante;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RegistroControlador {
    private ArrayList<Estudiante> listaEstudiantes;

    public RegistroControlador(DefaultTableModel modeloTabla) {
        listaEstudiantes = new ArrayList<>();
        cargarEstudiantesDesdeArchivo(modeloTabla); 
    }

     public void agregarEstudiante(Estudiante estudiante) {
    
    for (Estudiante e : listaEstudiantes) {
        if (e.getCodigo().equals(estudiante.getCodigo())) {
            JOptionPane.showMessageDialog(null, "El estudiante con el código " + estudiante.getCodigo() + " ya existe.");
            return; // No agregar si ya existe
        }
    }
    listaEstudiantes.add(estudiante);
    guardarEstudiantesEnArchivo();
}
    public void buscarEstudiantes(String texto, DefaultTableModel modeloTabla) {
        limpiarTabla(modeloTabla);
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                estudiante.getApellidoPaterno().toLowerCase().contains(texto.toLowerCase()) ||
                estudiante.getApellidoMaterno().toLowerCase().contains(texto.toLowerCase()) ||
                estudiante.getCodigo().toLowerCase().contains(texto.toLowerCase()) ||
                estudiante.getFacultad().toLowerCase().contains(texto.toLowerCase())) {
                
                modeloTabla.addRow(new Object[]{
                    estudiante.getCodigo(),
                    estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno(),
                    estudiante.getFacultad(),
                    estudiante.getFechaNacimiento()
                });
            }
        }
    }

    public void mostrarEstudiantes(DefaultTableModel modeloTabla) {
    limpiarTabla(modeloTabla); 

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 

    for (Estudiante estudiante : listaEstudiantes) {
        String fechaFormateada = dateFormat.format(estudiante.getFechaNacimiento()); 

        modeloTabla.addRow(new Object[]{
            estudiante.getCodigo(),
            estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno(),
            estudiante.getFacultad(),
            fechaFormateada  
        });
    }
}

    private void limpiarTabla(DefaultTableModel modeloTabla) {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }

    // Método para guardar un estudiante en el archivo de texto
public void guardarEstudiantesEnArchivo() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("estudiantes.txt"))) {
        writer.write("+--------+----------------------+-------------------+--------------------+----------------------+---------------------+");
        writer.newLine();
        writer.write("| Código | Nombres y Apellidos  | Apellido Paterno  | Apellido Materno    | Facultad             | Fecha de Nacimiento |");
        writer.newLine();
        writer.write("+--------+----------------------+-------------------+--------------------+----------------------+---------------------+");
        writer.newLine();

        for (Estudiante estudiante : listaEstudiantes) {
            String fechaNacimiento = String.format("%02d/%02d/%04d",
                estudiante.getFechaNacimiento().getDate(),
                estudiante.getFechaNacimiento().getMonth() + 1,
                estudiante.getFechaNacimiento().getYear() + 1900);

            writer.write(String.format("| %-6s | %-20s | %-17s | %-18s | %-20s | %-17s |",
                estudiante.getCodigo(),
                estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno(),
                estudiante.getApellidoPaterno(),
                estudiante.getApellidoMaterno(),
                estudiante.getFacultad(),
                fechaNacimiento));
            writer.newLine();
        }
        writer.write("+--------+----------------------+-------------------+--------------------+----------------------+---------------------+");
        writer.newLine();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


   
   public void cargarEstudiantesDesdeArchivo(DefaultTableModel modeloTabla) {
    String archivoEstudiantes = "estudiantes.txt"; 
    File archivo = new File(archivoEstudiantes);
    
    if (!archivo.exists()) {
        System.out.println("El archivo no se encontró en la ruta especificada.");
        return;
    }
    
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        boolean esPrimeraLinea = true; 

        while ((linea = br.readLine()) != null) {
            // Saltar encabezados
            if (esPrimeraLinea) {
                esPrimeraLinea = false; 
                continue;
            }
            
          
            String[] datos = linea.split("\\|"); 
            if (datos.length >= 6) { 
                String codigo = datos[1].trim();
                String nombres = datos[2].trim();
                String apellidoPaterno = datos[3].trim();
                String apellidoMaterno = datos[4].trim();
                String facultad = datos[5].trim();
                String fechaNacimientoString = datos[6].trim();
                
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaNacimiento = dateFormat.parse(fechaNacimientoString);

           
                Estudiante estudiante = new Estudiante(codigo, nombres, apellidoPaterno, apellidoMaterno, facultad, fechaNacimiento);
                listaEstudiantes.add(estudiante); 

               
                modeloTabla.addRow(new Object[]{
                    codigo,
                    nombres,
                    facultad,
                    fechaNacimientoString
                });
            }
        }
    } catch (IOException | ParseException e) {
        System.out.println("Error al leer el archivo: " + e.getMessage());
    }
}
    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }
}
