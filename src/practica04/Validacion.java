package practica04;

import java.math.BigInteger;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import nominas.entity.Trabajadorbbdd;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class Validacion {

	Row currentRow;
	private Lectura lectura;
	private Escritura escritura;
	private GeneracionXML generador;
	private ArrayList<Row> listaDefinitiva;
	ArrayList<Row> listaBlancosDuplicados;
	ArrayList<Row> listaModificar;

	private int fila;
	private String dniValidar;
	private String[] tablaLetras = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
			"Q", "V", "H", "L", "C", "K", "E" };

	/*-----------------PRACTICA 02----------------*/

	private String CCC;
	private int[] factores = { 1, 2, 4, 8, 5, 10, 9, 7, 3, 6 };
	private ArrayList<Row> listaModificarCuentas;
	private ArrayList<Row> listaIban;
        

	public Validacion() {

		lectura = new Lectura();
		lectura.leerFicheroPorFilas(0);

		escritura = new Escritura();

		listaDefinitiva = lectura.getListaDefinitiva();
		listaDefinitiva.remove(0);

		generador = new GeneracionXML();

		dniValidar = "";
		fila = 0;
		currentRow = null;
		listaBlancosDuplicados = new ArrayList<Row>();
		listaModificar = new ArrayList<Row>();

		listaModificarCuentas = new ArrayList<Row>();
		listaIban = new ArrayList<Row>();

	}

	public void validarDNI() {

		String dniSinLetra = "";
		String letra = "";
		char[] tmp;
		String letraCorrecta = "";
		String antiguo = "";
		boolean flagNIE = false;

		Iterator<Row> itDef = listaDefinitiva.iterator();

		while (itDef.hasNext()) {

			currentRow = itDef.next();
			dniValidar = currentRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();

			fila = currentRow.getRowNum();

			if (!dniValidar.isEmpty()) {
				if (dniValidar.length() != 9 || Character.isLetter(dniValidar.charAt(8)) == false) {
					System.out.println("El DNI no contiene 9 caracteres o no contiene letra.");
				} else {

					letra = dniValidar.substring(8).toUpperCase();

					if (dniValidar.startsWith("X")) {

						tmp = dniValidar.toCharArray();
						tmp[0] = '0';
						dniValidar = new String(tmp);
						flagNIE = true;

					} else if (dniValidar.startsWith("Y")) {

						tmp = dniValidar.toCharArray();
						tmp[0] = '1';
						dniValidar = new String(tmp);
						flagNIE = true;

					} else if (dniValidar.startsWith("Z")) {

						tmp = dniValidar.toCharArray();
						tmp[0] = '2';
						dniValidar = new String(tmp);
						flagNIE = true;

					}

					dniSinLetra = dniValidar.substring(0, 8);

					if (esNumero(dniSinLetra) == true && calcularLetraDni(dniValidar).equals(letra)) {
						System.out.println(
								"DNI CORRECTO, no se actualiza hoja Excel: " + dniValidar + " fila: " + (fila + 1));
					} else {

						antiguo = dniValidar;

						letraCorrecta = calcularLetraDni(dniValidar);

						tmp = dniValidar.toCharArray();
						tmp[8] = letraCorrecta.charAt(0);

						dniValidar = new String(tmp);
						System.out.println("DNI INCORRECTO " + antiguo + " se actualiza hoja Excel con DNI: "
								+ dniValidar + " fila: " + (fila + 1));

						if (flagNIE == true) {
							if (dniValidar.startsWith("0")) {

								tmp = dniValidar.toCharArray();
								tmp[0] = 'X';
								dniValidar = new String(tmp);
								flagNIE = false;

							} else if (dniValidar.startsWith("1")) {

								tmp = dniValidar.toCharArray();
								tmp[0] = 'Y';
								dniValidar = new String(tmp);
								flagNIE = false;
							} else if (dniValidar.startsWith("2")) {

								tmp = dniValidar.toCharArray();
								tmp[0] = 'Z';
								dniValidar = new String(tmp);
								flagNIE = false;
							}

						}
						if (!dniValidar.isEmpty())
							currentRow.getCell(0).setCellValue(dniValidar);

						listaModificar.add(currentRow);

					}
				}

			}

		}

		if (!listaModificar.isEmpty()) {
			escritura.modificarCelda(listaModificar, 0, 0); 
		}

	}

	public void comprobarBlancosDuplicados() {

		Iterator<Row> it = listaDefinitiva.iterator();
		Set<Object> set = new HashSet<>();

		while (it.hasNext()) {
			currentRow = it.next();
			dniValidar = currentRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			fila = currentRow.getRowNum();

			if (!set.add(dniValidar) && !dniValidar.isEmpty()) {
				System.out.println("DNIs DUPLICADOS: " + dniValidar + " fila " + (fila + 1));
				listaBlancosDuplicados.add(currentRow);

			}

			if (dniValidar.isEmpty()) {

				System.out.println("NIF/NIE en blanco en la fila: " + (fila + 1));
				listaBlancosDuplicados.add(currentRow);

			}
		}

		if (!listaBlancosDuplicados.isEmpty()) {
			generador.generarXMLDni(listaBlancosDuplicados);
		}
	}

	private String calcularLetraDni(String dni) {

		String letra = "";
		int dniCalculo = Integer.parseInt(dni.substring(0, 8));
		int resto = 0;

		resto = dniCalculo % 23;

		letra = tablaLetras[resto];

		return letra;

	}

	private boolean esNumero(String cadena) {

		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/*----------------------------PRACTICA 02--------------------------*/





	public void validarCCC() {

		Iterator<Row> itDef = listaDefinitiva.iterator();
		String entidadYOficina = "";
		String codigoCuenta = "";
		String controlEntidadOficinaCalculado = "";
		String controlCodigoCuentaCalculado = "";
		String digitosControlCalculado = "";
		String digitosControlOriginal = "";
		String cuentaCorrecta = "";
	
		@SuppressWarnings("unused")
		String codigoPais = "";

		int contador = 0;
		while (itDef.hasNext()) {

			currentRow = itDef.next();
			CCC = currentRow.getCell(14, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			codigoPais = currentRow.getCell(15, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			fila = currentRow.getRowNum();



			digitosControlOriginal = CCC.substring(8, 10);

			entidadYOficina = CCC.substring(0, 8);
			entidadYOficina = "00" + entidadYOficina;
			codigoCuenta = CCC.substring(10, 20);

			controlEntidadOficinaCalculado = obtenerDigitoControl(entidadYOficina);
			controlCodigoCuentaCalculado = obtenerDigitoControl(codigoCuenta);

			digitosControlCalculado = controlEntidadOficinaCalculado + controlCodigoCuentaCalculado;

			cuentaCorrecta = entidadYOficina.substring(2) + digitosControlCalculado + codigoCuenta;


			if (!digitosControlOriginal.equalsIgnoreCase(digitosControlCalculado)) {

				if (!cuentaCorrecta.isEmpty())
					currentRow.getCell(14).setCellValue(cuentaCorrecta);

				listaModificarCuentas.add(currentRow);



			} else {
				System.out.println("CORRECTO:---------> FILA: " + fila + " Nombre: "
						+ currentRow.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK));
				contador++;
			}
		}

		if (!listaModificarCuentas.isEmpty()) {
			escritura.modificarCelda(listaModificarCuentas, 14, 0); 
		}

		System.out.println("Cuentas correctas: " + contador);
		
		
		calcularIBAN();
		
		
		if (!listaModificarCuentas.isEmpty()) {
			generador.generarXMLCuentas(listaModificarCuentas);

		}

	}

	public void generarEmail() {

		String correoElectronico = "";
		String nombre = "";
		String primerApellido = "";
		String segundoApellido = "";
		int numeroRepeticion = 0;
		String numeroRepeticionFormateado = String.format("%02d", numeroRepeticion);
		String nombreEmpresa = "";
		ArrayList<Row> listaCorreos = new ArrayList<Row>();
		ArrayList<Row> listaRepetidos = new ArrayList<Row>();
		Set<Object> set = new HashSet<>();
		String foo = "";
		String newEmail = "";
		String primeraParte = "";
		String ultimaParte = "";
		Iterator<Row> itDef = listaDefinitiva.iterator();

		while (itDef.hasNext()) {

			currentRow = itDef.next();
			fila = currentRow.getRowNum();

			nombre = currentRow.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			primerApellido = currentRow.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			segundoApellido = currentRow.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			nombreEmpresa = currentRow.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();


			nombre = nombre.substring(0, 3);
			primerApellido = primerApellido.substring(0, 2);

			if (!segundoApellido.isEmpty()) {
				segundoApellido = segundoApellido.substring(0, 2);
			}

			nombreEmpresa = "@" + nombreEmpresa + ".es";

			correoElectronico = (nombre + primerApellido + segundoApellido + numeroRepeticionFormateado + nombreEmpresa)
					.toLowerCase();
			
			correoElectronico = cleanString(correoElectronico); //Limpia el String de acentos.

			if (!correoElectronico.isEmpty()) {
				currentRow.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(correoElectronico);
				listaCorreos.add(currentRow);
			}

			// REPETIDOS
			if (!set.add(correoElectronico) && !correoElectronico.isEmpty()) {

				listaRepetidos.add(currentRow);
			}

		}

		for (int i = 0; i < listaRepetidos.size(); i++) {
			foo = listaRepetidos.get(i).getCell(4).toString();
			numeroRepeticion = Integer.parseInt(foo.substring(7, 9));
			primeraParte = foo.substring(0, 7);
			ultimaParte = foo.substring(9);
			numeroRepeticion++;
			numeroRepeticionFormateado = String.format("%02d", numeroRepeticion);
			newEmail = primeraParte + numeroRepeticionFormateado + ultimaParte;
			listaRepetidos.get(i).getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(newEmail);

		}

		if (!listaCorreos.isEmpty()) {
			escritura.modificarCelda(listaCorreos, 4, 0); 

		}

	}
        
	
	private void calcularIBAN() {

		String cuentaCorrecta = "";
		String codigoPais = "";
		String ibanSinDigito = "";
		String dcIban = "";
		String ibanCorrecto = "";

		Iterator<Row> itDef = listaDefinitiva.iterator();


		while (itDef.hasNext()) {

			currentRow = itDef.next();
			cuentaCorrecta = currentRow.getCell(14, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			codigoPais = currentRow.getCell(15, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			fila = currentRow.getRowNum();

			ibanSinDigito = cuentaCorrecta + getPesoIBAN(codigoPais.charAt(0)) + getPesoIBAN(codigoPais.charAt(1))
					+ "00";

			BigInteger cuentaIban = new BigInteger(ibanSinDigito);
			BigInteger moduloNoventaYSiete = new BigInteger("97");
			cuentaIban = cuentaIban.mod(moduloNoventaYSiete);
			int resto = cuentaIban.intValue();
			resto = 98 - resto;

			dcIban = String.format("%02d", resto);

			ibanCorrecto = codigoPais + dcIban + cuentaCorrecta;

			if (validarIBAN(codigoPais, dcIban, cuentaCorrecta) == true) {

				System.out.println("El IBAN generado es correcto. Fila: " + fila);

				if (!ibanCorrecto.isEmpty()) {
					currentRow.getCell(16, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(ibanCorrecto);

					listaIban.add(currentRow);
				}

			} else {
				System.out.println("El IBAN generado es INCORRECTO. Fila: " + fila);

			}
		}

		if (!listaIban.isEmpty()) {
			escritura.modificarCelda(listaIban, 16, 0); 
		}

	}

	private String getPesoIBAN(char letra) {
		String peso = "";
		letra = Character.toUpperCase(letra);
		switch (letra) {
		case 'A':
			peso = "10";
			break;
		case 'B':
			peso = "11";
			break;
		case 'C':
			peso = "12";
			break;
		case 'D':
			peso = "13";
			break;
		case 'E':
			peso = "14";
			break;
		case 'F':
			peso = "15";
			break;
		case 'G':
			peso = "16";
			break;
		case 'H':
			peso = "17";
			break;
		case 'I':
			peso = "18";
			break;
		case 'J':
			peso = "19";
			break;
		case 'K':
			peso = "20";
			break;
		case 'L':
			peso = "21";
			break;
		case 'M':
			peso = "22";
			break;
		case 'N':
			peso = "23";
			break;
		case 'O':
			peso = "24";
			break;
		case 'P':
			peso = "25";
			break;
		case 'Q':
			peso = "26";
			break;
		case 'R':
			peso = "27";
			break;
		case 'S':
			peso = "28";
			break;
		case 'T':
			peso = "29";
			break;
		case 'U':
			peso = "30";
			break;
		case 'V':
			peso = "31";
			break;
		case 'W':
			peso = "32";
			break;
		case 'X':
			peso = "33";
			break;
		case 'Y':
			peso = "34";
			break;
		case 'Z':
			peso = "35";
			break;
		}
		return peso;
	}

	private boolean validarIBAN(String codigoPais, String dcIban, String cuentaCorrecta) {

		String def = cuentaCorrecta + getPesoIBAN(codigoPais.charAt(0)) + getPesoIBAN(codigoPais.charAt(1)) + dcIban;

		BigInteger moduloNoventaYSiete = new BigInteger("97");
		BigInteger cuentaAValidar = new BigInteger(def);
		cuentaAValidar = cuentaAValidar.mod(moduloNoventaYSiete);
		int resto = cuentaAValidar.intValue();

		if (resto == 1)
			return true;

		return false;
	}

	

	private String obtenerDigitoControl(String codigo) {

		int control = 0;

		for (int i = 0; i < factores.length; i++) {

			control += factores[i] * codigo.charAt(i);
		}

		control = (11 - (control % 11));

		if (control == 11)
			control = 0;
		else if (control == 10)
			control = 1;

		return String.valueOf(control);

	}
	
    private static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

}
