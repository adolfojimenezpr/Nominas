package practica04;

import java.util.Scanner;
import util.HibernateUtil;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		System.out.println("Corrigiendo DNIs...");
		Validacion validacion = new Validacion();

		validacion.validarDNI();
		System.out.println("Comprobando DNIs blancos y duplicados...");
		validacion.comprobarBlancosDuplicados();
		
		System.out.println("Generando IBAN...");
		validacion.validarCCC();
		System.out.println("Generando Email...");
		validacion.generarEmail();
	
		
		System.out.println("Introduzca el mes/a�o del que se desean generar las nominas. Formato: mm/aaaa");
		Scanner sc = new Scanner(System.in);
		String cadena = sc.nextLine();
		String mesStr = "", añoStr = "";
		int mes = 0, año = 0;
		
		mesStr = cadena.substring(0, 2);
		añoStr = cadena.substring(3);
		System.out.println(mesStr);
		System.out.println(añoStr);
		
		mes = Integer.valueOf(mesStr);
		año = Integer.valueOf(añoStr);
		
		Nomina_Sistema nomina = new Nomina_Sistema(mes,año);
		nomina.getCategoria();
                
                
                HibernateUtil.getSessionFactory().close();
		
	}

}
