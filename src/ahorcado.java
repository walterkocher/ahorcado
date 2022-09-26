import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ahorcado {

    public static ArrayList<String> leer(String nombreArchivo) throws FileNotFoundException, IOException {
        String auxs = "";

        ArrayList<String> palabrasLeidas = new ArrayList<String>();

        FileReader fr =  new FileReader(nombreArchivo);

        BufferedReader buff_in = new BufferedReader( fr );

        while(( auxs = buff_in.readLine()) != null) {
            if (auxs.length() > 0)
                palabrasLeidas.add(auxs);
        }

        buff_in.close();

        return palabrasLeidas;
    }

    public static void main(String[] args) throws IOException {
        String palabraSecreta;
        String palabraAdivinada;

        final int intentosMaximos = 10;
        int intentos = 0;
        int resultado = 0;

        boolean letraBien = false;

        ArrayList<String> palabras = new ArrayList<String>();
        try {
            palabras = leer("src\\palabras.txt");
        } catch(FileNotFoundException e) {
            System.out.println("El archivo de palabras no existe");
            System.out.println("Imposible continuar");
            return;
        }

        Random randGen = new Random();

        palabraSecreta = palabras.get(randGen.nextInt(palabras.size()));

        palabraAdivinada = String.format("%0" + palabraSecreta.length() + "d", 0).replace("0", "_");

        Scanner consInp = new Scanner(System.in);

        for (char c : palabraAdivinada.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();

        while(resultado == 0) {
            System.out.println();
            System.out.println();
            System.out.print("Ingrese una letra: ");

            String letraIngresada = consInp.next();
            letraIngresada = letraIngresada.trim().substring(0, 1);

            int posicion;
            posicion = palabraSecreta.indexOf(letraIngresada);

            letraBien = false;
            while (posicion != -1) {
                palabraAdivinada = palabraAdivinada.substring(0,posicion) + letraIngresada + palabraAdivinada.substring(posicion + 1);


                posicion = palabraSecreta.indexOf(letraIngresada, posicion + 1);

                letraBien = true;
            }

            System.out.println();
            for (char c : palabraAdivinada.toCharArray()) {
                System.out.print(c + " ");
            }
            System.out.println();

            if (!palabraAdivinada.contains("_")) {
                resultado = 1;
            } else {
                if (!letraBien) {
                    intentos++;
                    if (intentos == intentosMaximos) {
                        resultado = -1;
                    }
                }
            }

            System.out.println();
            System.out.println("Usaste " + intentos + " intentos de los " + intentosMaximos);
        }

        if (resultado == 1) {
            System.out.println();
            System.out.println("Ganaste!");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("La palabra es: " + palabraSecreta);
            System.out.println();
        }
    }
}
