/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciolemario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class EjercicioLemario {
    
//    ArrayList<String> listaPalabras = new ArrayList<String>();
    HashMap <String, Boolean> listaPalabras = new HashMap();
    
    public static String normalizeString(String cadena){
	String limpio =null;
	if (cadena !=null) {
        String valor = cadena;
        valor = valor.toUpperCase();
        // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
        limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
        // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
        limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
        // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
    }
    return limpio;
    }
    
    public void cargaFicheroLemario(){
	File fichero = new File("src/ejerciciolemario/lemario-20101017.txt");
	try {
	    FileReader fr = new FileReader(fichero);
	    BufferedReader br = new BufferedReader(fr);
	    String linea;
	    while((linea= br.readLine()) != null){
		listaPalabras.put(normalizeString(linea.toUpperCase()),true);
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(EjercicioLemario.class.getName()).log(Level.SEVERE, null, ex);
	} catch(IOException ex){
	    Logger.getLogger(EjercicioLemario.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public boolean existe(String palabra){
	try {
	    if(listaPalabras.get(normalizeString(palabra.toUpperCase()))){
	    return true;
	}
	else return false;
	} catch (Exception e) {
	    return false;
	}
    }
    
    public boolean escalera(String[] palabras){
	boolean esEscalera = true;
	int contadorDiferentes;
	for(int i = 0; i<palabras.length-1&&esEscalera; i++){
	    contadorDiferentes = 0;
	    if(palabras[i].length()==palabras[i+1].length()){
		if(existe(normalizeString(palabras[i].toUpperCase()))&&existe(normalizeString(palabras[i+1].toUpperCase()))){
		    for(int j = 0; j<palabras[i].length(); j++){
			if(normalizeString(palabras[i].toUpperCase()).charAt(j)!= normalizeString(palabras[i+1].toUpperCase()).charAt(j)){
			contadorDiferentes++;
			}
		    }   
		}
		else{
		    return false;
		}
		if(contadorDiferentes!=1){
		   esEscalera = false;
		}
	    }
	    else{
		esEscalera = false;
		return esEscalera;
	    }
	}
	return esEscalera;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	EjercicioLemario test = new EjercicioLemario();
	test.cargaFicheroLemario();
	
	//Test busqueda palabras
	System.out.println("Test busqueda palabras");
	System.out.println(test.existe("molu"));
	System.out.println("");
	
	//Test escalera
	System.out.println("Test escalera");
	String[] prueba1 = {"dedo", "dado", "daño", "maño", "mano"};
	System.out.println(test.escalera(prueba1));
	System.out.println("");
    }
    
}
