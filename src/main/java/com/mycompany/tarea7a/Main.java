/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tarea7a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author ismae
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static LocalDate fechaHoy = LocalDate.now();

    public static void main(String[] args) {

        // Fichero a leer con datos de ejemplo
        String idFichero = "./src/main/java/com/mycompany/tarea7a/RelPerCen.csv";
        String idFichero2 = "./src/main/java/com/mycompany/tarea7a/veinteAños.csv";//No se porque no me crea la ruta
        // Variables para guardar los datos que se van leyendo
        ArrayList<Empleado> lista = new ArrayList<>();
        ArrayList<Empleado> lista2 = new ArrayList<>();
        leerFichero(idFichero, lista);
        lista2 = lista;
        lista = comprobarAños(lista);
        escribirFichero(idFichero2, lista);
        ejercicioA(lista2);
        ejercicioB(lista2);
    }

    public static boolean comprobacion(String token) {//Para hacer booleans del fichero original
        if (token.equals("Sí")) {
            return true;
        } else {
            return false;
        }

    }

    public static String comprobacion(boolean token) {//Para pasar los booleans al nuevo fichero
        if (token) {
            return "Sí";
        } else {
            return "No";
        }

    }

    public static LocalDate comprobarVacio(String token) { //para los campos de fecha vacios
        String tokens[];
        if (token.equals("")) {
            return fechaHoy;
        } else {
            tokens = token.split("/");

            return LocalDate.parse(tokens[2] + '-' + tokens[1] + '-' + tokens[0]);
        }
    }

    public static String quitarComillas(String token) {//para quitar las comillas del archivo
        String aux = "";
        if (token.equals("")) {
            return token;
        }
        for (int i = 1; i < token.length() - 1; i++) {
            aux = aux + token.charAt(i);

        }
        return aux;
    }

    private static void leerFichero(String idFichero, ArrayList<Empleado> lista) {
        String[] tokens;
        String linea;
        System.out.println("Leyendo el fichero: " + idFichero);

        // Inicialización del flujo "datosFichero" en función del archivo llamado "idFichero"
        // Estructura try-with-resources. Permite cerrar los recursos una vez finalizadas
        // las operaciones con el archivo
        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {
            // hasNextLine devuelve true mientras haya líneas por leer
            datosFichero.nextLine();
            while (datosFichero.hasNextLine()) {
                // Guarda la línea completa en un String
                linea = datosFichero.nextLine();
                // Se guarda en el array de String cada elemento de la
                // línea en función del carácter separador de campos del fichero CSV
                tokens = linea.split(",");
                Empleado tmp = new Empleado();
                tmp.setApellidos(quitarComillas(tokens[0]));
                tmp.setNombre(quitarComillas(tokens[1]));
                tmp.setDni(quitarComillas(tokens[2]));
                tmp.setPuesto(quitarComillas(tokens[3]));
                tokens[4] = quitarComillas(tokens[4]);
                tmp.setFechaInicio(comprobarVacio(tokens[4]));
                tokens[5] = quitarComillas(tokens[5]);
                tmp.setFechaFin(comprobarVacio(tokens[5]));
                tmp.setTeléfono(quitarComillas(tokens[6]));
                tokens[7] = quitarComillas(tokens[7]);
                tmp.setEvaluador(comprobacion(tokens[7]));
                tokens[8] = quitarComillas(tokens[8]);
                tmp.setCoordinador(comprobacion(tokens[8]));
                lista.add(tmp);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void escribirFichero(String idFichero2, ArrayList<Empleado> lista) {
        String tmp;

        // Si se utiliza el constructor FileWriter(idFichero, true) entonces se anexa información
        // al final del fichero idFichero
        // Estructura try-with-resources. Instancia el objeto con el fichero a escribir
        // y se encarga de cerrar el recurso "flujo" una vez finalizadas las operaciones
        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(idFichero2))) {
            for (Empleado empleado : lista) {
                tmp = empleado.getApellidos();
                flujo.write(tmp + ",");
                tmp = empleado.getNombre();
                flujo.write(tmp + ",");
                tmp = empleado.getDni();
                flujo.write(tmp + ",");
                tmp = empleado.getPuesto();
                flujo.write(tmp + ",");
                tmp = empleado.getFechaInicio().toString();
                flujo.write(tmp + ",");
                tmp = empleado.getFechaFin().toString();
                flujo.write(tmp + ",");
                tmp = comprobacion(empleado.isEvaluador());
                flujo.write(tmp + ",");
                tmp = comprobacion(empleado.isCoordinador());
                flujo.write(tmp);
                flujo.newLine();
            }

            // Metodo fluh() guarda cambios en disco 
            flujo.flush();
            System.out.println("Fichero " + idFichero2 + " creado correctamente.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList comprobarAños(ArrayList<Empleado> lista) {//comprueba que los trabajadores lleven mas de 20 años
        long diferenciaDias;
        ArrayList<Empleado> lista2 = new ArrayList<>();
        for (Empleado empleado : lista) {
            diferenciaDias = ChronoUnit.DAYS.between(empleado.getFechaInicio(), fechaHoy);
            if (diferenciaDias > 7300) {
                lista2.add(empleado);

            }
        }
        return lista2;
    }

    private static void ejercicioA(ArrayList<Empleado> lista) {
        int profesoresInformatica = 0;
        ArrayList<Empleado> lista2 = new ArrayList<>();
        ArrayList<Empleado> lista1 = new ArrayList<>();
        ArrayList<Empleado> lista3 = new ArrayList<>();
        boolean noJohns;
        for (Empleado empleado : lista) {
            if (empleado.getPuesto().equals("Informática P.E.S.")) {
                profesoresInformatica++;
                lista1.add(empleado);
            }

            if (empleado.getPuesto().equals("Biología y Geología P.E.S.") && empleado.isCoordinador() == true) {
                lista2.add(empleado);
            }
            if (empleado.getDni().contains("N")) {
                lista3.add(empleado);
            }
        }

        noJohns = noJohns(lista);
        Collections.sort(lista3, (v1, v2) -> v1.getApellidos().compareTo(v2.getApellidos()));
        System.out.println("numero de profesores informatica= " + profesoresInformatica);
        for (Empleado empleado : lista1) {
            System.out.println(empleado.toString());
        }
        System.out.println("");
        System.out.println("Lista de empleados de biologia que son coordinadores");
        for (Empleado empleado : lista2) {
            System.out.println(empleado.toString());
        }
        System.out.println("");
        System.out.println("Lista de profesores que tienen una letra N en su DNI");
        for (Empleado empleado : lista3) {
            System.out.println(empleado.getApellidos());
        }
        System.out.println("");
        if (noJohns) {
            System.out.println("No hay ningun profesor llamado John");
        } else {
            System.out.println("Hay alguien llamado John");
        }
        System.out.println("");
    }

    private static void ejercicioB(ArrayList<Empleado> lista) {
        long profInformatica = lista.stream()
                .filter(p -> p.getPuesto().equals("Informática P.E.S."))
                .count();
        List<Empleado> listaB2 = lista.stream()
                .filter(p -> p.getPuesto().equals("Biología y Geología P.E.S.") && p.isCoordinador() == true)
                .collect(Collectors.toList());
        List<String> listaB3 = lista.stream()
                .filter(p -> p.getDni().contains("N"))
                .map(p -> p.getApellidos())
                .sorted()
                .collect(Collectors.toList());
        List<Empleado> listaB4 = lista.stream()
                .filter(p -> p.getNombre().equals("John"))
                .collect(Collectors.toList());
        System.out.println("Hay " + profInformatica + " profesores de informatica");

        System.out.println("");
        System.out.println("Este imprime la lista de los biologos coordinadores");
        for (Empleado empleado : listaB2) {
            System.out.println(empleado.toString());
        }
        System.out.println("");
        System.out.println("Este imprime la lista de gente que tiene N en sus DNI");
        for (String string : listaB3) {
            System.out.println(string);
        }
        System.out.println("");
        if (listaB4.isEmpty()) {
            System.out.println("No hay johns en la lista");
        } else {
            System.out.println("Hay algun john en la lista");
        }
    }

    private static boolean noJohns(ArrayList<Empleado> lista) {
        for (Empleado empleado : lista) {
            if (empleado.getNombre().equalsIgnoreCase("John")) {
                return false;
            }
        }
        return true;
    }
    
        public static List<LocalDate> fechas(ArrayList<Empleado> lista) {
        List<LocalDate> fecha = lista.stream()
                .map(p -> p.getFechaFin())//Stream de LocalDates
                .filter(p -> p != null)//Todos los que no sean null
                .collect(Collectors.toList());

        return fecha;

    }

    public static List<String> jubilados(ArrayList<Empleado> lista, LocalDate fechaJ) {

        List<String> jubi = lista.stream()
                .filter(p -> p.getFechaFin().equals(fechaJ))
                .map(p -> p.getApellidos())
                .collect(Collectors.toList());
        return jubi;

    }
    
}
