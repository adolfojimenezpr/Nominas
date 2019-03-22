package practica04;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneracionPDF {

	public void generar(Map<String, Double> mapDatosNomina, Map<String, String> mapDatosEmpresa, boolean prorrateoFlag)
			throws DocumentException {

		Document documento = new Document();
		String cantidad = "30 dias";
		String nombreEmpresa = mapDatosEmpresa.get("nombreEmpresa");
		String CIFEmpresa = mapDatosEmpresa.get("CIFEmpresa");
		String nombreTrabajador = mapDatosEmpresa.get("nombreTrabajador");
		String primerApellidoTrabajador = mapDatosEmpresa.get("primerApellidoTrabajador");
		String segundoApellidoTrabajador = mapDatosEmpresa.get("segundoApellidoTrabajador");
		String DNITrabajador = mapDatosEmpresa.get("DNITrabajador");
		String mesSolicitudStr = mapDatosEmpresa.get("mesSolicitudStr");
		String añoSolicitudStr = mapDatosEmpresa.get("añoSolicitudStr");
		
		double salarioBaseDevengo = mapDatosNomina.get("salarioBaseDevengo") / 30;
		double unidadesRedondeo = Math.round(salarioBaseDevengo * 100.0) / 100.0;
		String unidadesSalario = String.valueOf(unidadesRedondeo);
		double prorrataDevengo = mapDatosNomina.get("prorrata") / 30;
		double unidadesProrrata = Math.round(prorrataDevengo * 100.0) / 100.0;
		String unidadesProrrataStr = String.valueOf(unidadesProrrata);
		double complementoDevengo = mapDatosNomina.get("complementoDevengo") / 30;
		double complementoRedondeo = Math.round(complementoDevengo * 100.0) / 100.0;
		String complementoStr = String.valueOf(complementoRedondeo);
		double antiguedadDevengo = mapDatosNomina.get("antiguedadDevengo") / 30;
		double antiguedadRedondeo = Math.round(antiguedadDevengo * 100.0) / 100.0;
		String antiguedadStr = String.valueOf(antiguedadRedondeo);

		String path = "resource/" + DNITrabajador + "_" + nombreTrabajador + primerApellidoTrabajador+ segundoApellidoTrabajador + "_" + mesSolicitudStr + añoSolicitudStr + ".pdf";

		try {
			PdfWriter.getInstance(documento, new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		documento.open();

		Paragraph empty = new Paragraph("");
		PdfPTable tableDatosEmpresa = new PdfPTable(2);
		Paragraph nom = new Paragraph(nombreEmpresa);
		Paragraph cif = new Paragraph(CIFEmpresa);
		Paragraph dir1 = new Paragraph("Avenida de la facultad - 6");
		Paragraph dir2 = new Paragraph("24001 Leon");
		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(nom);
		cell1.addElement(cif);
		cell1.addElement(empty);
		cell1.addElement(dir1);
		cell1.addElement(dir2);
		cell1.addElement(empty);
		cell1.setPadding(1);
		PdfPCell cell2 = new PdfPCell(new Paragraph(""));
		cell2.setBorder(Rectangle.NO_BORDER);
		Paragraph IBAN = new Paragraph("IBAN: "+mapDatosEmpresa.get("IBAN"));
		Paragraph categoria = new Paragraph("Categoria: "+mapDatosEmpresa.get("categoria"));
		Paragraph brutoAnual = new Paragraph("Bruto Anual: "+mapDatosNomina.get("brutoAnual"));
		Paragraph fechaAltaEmpresa = new Paragraph("Fecha de alta: "+mapDatosEmpresa.get("fechaAltaEmpresa"));
		cell2.addElement(IBAN);
		cell2.addElement(categoria);
		cell2.addElement(brutoAnual);
		cell2.addElement(fechaAltaEmpresa);

		tableDatosEmpresa.addCell(cell1);
		tableDatosEmpresa.addCell(cell2);
		tableDatosEmpresa.setSpacingAfter(10f);
		documento.add(tableDatosEmpresa);
		PdfPTable tableDatosTrabajador = new PdfPTable(2);
		Paragraph destinatario = new Paragraph("Destinatario: ");
		Paragraph nomTrabajador = new Paragraph(
				nombreTrabajador + " " + primerApellidoTrabajador + " " + segundoApellidoTrabajador);
		nomTrabajador.setAlignment(Element.ALIGN_RIGHT);
		Paragraph niftrab = new Paragraph("DNI: " + DNITrabajador);
		niftrab.setAlignment(Element.ALIGN_RIGHT);
		Paragraph dir1trab = new Paragraph("Avenida de la facultad");
		dir1trab.setAlignment(Element.ALIGN_RIGHT);
		Paragraph dir2trab = new Paragraph("24001 Leon");
		dir2trab.setAlignment(Element.ALIGN_RIGHT);
		Image img = null;
		try {
			img = Image.getInstance("resource/img.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PdfPCell cellImagen = new PdfPCell(img, true);
		cellImagen.setBorder(Rectangle.NO_BORDER);
		cellImagen.setPadding(10);
		cellImagen.setPaddingTop(30);
		cellImagen.setFixedHeight(90);
		PdfPCell celltrabajador = new PdfPCell();
		celltrabajador.addElement(destinatario);
		celltrabajador.addElement(nomTrabajador);
		celltrabajador.addElement(niftrab);
		celltrabajador.addElement(empty);
		celltrabajador.addElement(dir1trab);
		celltrabajador.addElement(dir2trab);
		celltrabajador.addElement(empty);
		celltrabajador.setIndent(10);
		celltrabajador.setPadding(10);
		tableDatosTrabajador.addCell(cellImagen);
		tableDatosTrabajador.addCell(celltrabajador);
		tableDatosTrabajador.setSpacingAfter(10f);
		documento.add(tableDatosTrabajador);
		PdfPTable tablaFecha = new PdfPTable(1);
		Font fontTit = new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.BLACK);
		Paragraph fechLit = new Paragraph("Nomina: " + mesSolicitudStr + " de " + añoSolicitudStr, fontTit);
		fechLit.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellfecha2 = new PdfPCell();
		cellfecha2.addElement(fechLit);
		cellfecha2.setBorder(Rectangle.NO_BORDER);
		cellfecha2.setPadding(10);
		tablaFecha.addCell(cellfecha2);
		PdfPCell cellfecha3 = new PdfPCell(new Paragraph(""));
		cellfecha3.setBorder(Rectangle.NO_BORDER);
		tablaFecha.addCell(cellfecha3);
		tablaFecha.setSpacingAfter(10f);

		PdfPTable table = new PdfPTable(6);

		PdfPCell celdaGrande = new PdfPCell(new Paragraph(""));
		celdaGrande.setColspan(2);
		celdaGrande.setBorder(Rectangle.NO_BORDER);
		PdfPCell celdaSalario = new PdfPCell(new Paragraph("Salario Base"));
		celdaSalario.setColspan(2);
		PdfPCell celdaProrrata = new PdfPCell(new Paragraph("Prorrata"));
		celdaProrrata.setColspan(2);
		PdfPCell celdaComplemento = new PdfPCell(new Paragraph("Complemento"));
		celdaComplemento.setColspan(2);
		PdfPCell celdaAntiguedad = new PdfPCell(new Paragraph("Antigüedad"));
		celdaAntiguedad.setColspan(2);
		
		PdfPCell celdaContingencias = new PdfPCell(new Paragraph("Contigencias Generales"));
		celdaContingencias.setColspan(2);
		
		PdfPCell celdaDesempleo = new PdfPCell(new Paragraph("Desempleo"));
		celdaDesempleo.setColspan(2);
		
		PdfPCell celdaFormacion = new PdfPCell(new Paragraph("Formacion"));
		celdaFormacion.setColspan(2);
		
		PdfPCell celdaIRPF = new PdfPCell(new Paragraph("IRPF"));
		celdaIRPF.setColspan(2);
		
		PdfPCell celdaTotalDeducciones = new PdfPCell(new Paragraph("Total Deducciones"));
		celdaTotalDeducciones.setColspan(2);

		PdfPCell celdaTotalDevengos = new PdfPCell(new Paragraph("Total Devengos"));
		celdaTotalDevengos.setColspan(2);
		
		PdfPCell celdaLiquidoAPercibir = new PdfPCell(new Paragraph("Liquido a percibir", new Font(FontFamily.HELVETICA, 12, Font.UNDERLINE, GrayColor.BLACK)));
		celdaLiquidoAPercibir.setColspan(2);
		
		
		table.addCell(celdaGrande);
		table.addCell("cant.");
		table.addCell("Imp. Unit.");
		table.addCell("Dev.");
		table.addCell("Deducc.");

		table.addCell(celdaSalario);
		table.addCell(cantidad);
		table.addCell(unidadesSalario);
		table.addCell(String.valueOf(mapDatosNomina.get("salarioBaseDevengo")));
		table.addCell("");

		
		if (prorrateoFlag == true) {
			
			table.addCell(celdaProrrata);
			table.addCell(cantidad);
			table.addCell(unidadesProrrataStr);
			table.addCell(String.valueOf(mapDatosNomina.get("prorrata")));
			table.addCell("");

		}else {
			
			table.addCell(celdaProrrata);
			table.addCell(cantidad);
			table.addCell("00.00");
			table.addCell("00.00");
			table.addCell("");
			
		}
		
		table.addCell(celdaComplemento);
		table.addCell(cantidad);
		table.addCell(complementoStr);
		table.addCell(String.valueOf(mapDatosNomina.get("complementoDevengo")));
		table.addCell("");
		
		table.addCell(celdaAntiguedad);
		table.addCell(String.valueOf(mapDatosNomina.get("numeroTrienios").intValue())+" Trienios");
		table.addCell(antiguedadStr);
		table.addCell(String.valueOf(mapDatosNomina.get("antiguedadDevengo")));
		table.addCell("");
		
		PdfPCell cellEspacio = new PdfPCell();
		cellEspacio.addElement(new Paragraph(""));
		cellEspacio.setBorder(Rectangle.NO_BORDER);
		cellEspacio.setPadding(10);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);


		
		
		table.addCell(celdaContingencias);
		table.addCell(String.valueOf(mapDatosNomina.get("contingenciasGenerales"))+"%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeSeguridadSocial")));
		
		
		table.addCell(celdaDesempleo);
		table.addCell(String.valueOf(mapDatosNomina.get("desempleo"))+"%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeDesempleo")));

		table.addCell(celdaFormacion);
		table.addCell(String.valueOf(mapDatosNomina.get("cuotaFormacion"))+"%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeFormacion")));
		
		table.addCell(celdaIRPF);
		table.addCell(String.valueOf(mapDatosNomina.get("IRPF"))+"%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("brutoMensual")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeIRPF")));
		
		

		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		
		table.addCell(celdaTotalDeducciones);
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("totalDeduccionesTrabajador")));

		table.addCell(celdaTotalDevengos);
		table.addCell("");
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("brutoMensual")));
		table.addCell("");


		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		celdaLiquidoAPercibir.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaLiquidoAPercibir);
		PdfPCell celdaLiquido = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("liquidoAPercibir"))));
		celdaLiquido.setBorder(Rectangle.NO_BORDER);		
		table.addCell(celdaLiquido);
		
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		PdfPCell lineaDebajo = new PdfPCell(new Paragraph(""));
		lineaDebajo.setBorder(Rectangle.BOTTOM);
		PdfPCell celdaBaseReguladora = new PdfPCell(new Paragraph("Calculo empresario: BASE",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteBase = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("baseReguladora")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaBaseReguladora.setColspan(3);
		celdaBaseReguladora.setBorder(Rectangle.BOTTOM);
		celdaImporteBase.setBorder(Rectangle.BOTTOM);
		table.addCell(celdaBaseReguladora);
		table.addCell(lineaDebajo);
		table.addCell(lineaDebajo);
		table.addCell(celdaImporteBase);
		
		PdfPCell celdaContingenciasComunes = new PdfPCell(new Paragraph("Contingencias comunes "+mapDatosNomina.get("contigenciasComunesEmp")+"%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteCon = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeContigenciasComunesEmp")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaContingenciasComunes.setColspan(3);
		celdaContingenciasComunes.setBorder(Rectangle.NO_BORDER);
		celdaImporteCon.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaContingenciasComunes);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteCon);
		
		PdfPCell celdaDesempleoEm = new PdfPCell(new Paragraph("Desempleo "+mapDatosNomina.get("desempleoEmpresario")+"%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteDesEm = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeDesempleoEmpr")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaDesempleoEm.setColspan(3);
		celdaDesempleoEm.setBorder(Rectangle.NO_BORDER);
		celdaImporteDesEm.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaDesempleoEm);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteDesEm);
		
		
		PdfPCell celdaFormacionEmp = new PdfPCell(new Paragraph("Formacion "+mapDatosNomina.get("formacionEmpresario")+"%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteForEm = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeFormacionEmpr")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaFormacionEmp.setColspan(3);
		celdaFormacionEmp.setBorder(Rectangle.NO_BORDER);
		celdaImporteForEm.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaFormacionEmp);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteForEm);
		
		PdfPCell celdaAccidentes = new PdfPCell(new Paragraph("Accidentes de trabajo "+mapDatosNomina.get("accidentesTrabajo")+"%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteAccidentes = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeAccidentes")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaAccidentes.setColspan(3);
		celdaAccidentes.setBorder(Rectangle.NO_BORDER);
		celdaImporteAccidentes.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaAccidentes);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteAccidentes);
		
		PdfPCell celdaFOGASA = new PdfPCell(new Paragraph("FOGASA "+mapDatosNomina.get("FOGASA")+"%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteFOGASA = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeFOGASA")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaFOGASA.setColspan(3);
		celdaFOGASA.setBorder(Rectangle.BOTTOM);
		celdaImporteFOGASA.setBorder(Rectangle.BOTTOM);
		table.addCell(celdaFOGASA);
		table.addCell(lineaDebajo);
		table.addCell(lineaDebajo);
		table.addCell(celdaImporteFOGASA);
		
		PdfPCell celdaTotalEmpr = new PdfPCell(new Paragraph("TOTAL empresario ",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteTotalEmp = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("totalDeduccionesEmpresario")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaTotalEmpr.setColspan(3);
		celdaTotalEmpr.setBorder(Rectangle.NO_BORDER);
		celdaImporteTotalEmp.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaTotalEmpr);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteTotalEmp);
		
		PdfPCell celdaCoste = new PdfPCell(new Paragraph("Coste total trabajador ",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteCoste = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("costeTotalTrabajador")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaCoste.setColspan(3);
		celdaCoste.setBorder(Rectangle.NO_BORDER);
		celdaImporteCoste.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaCoste);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteCoste);
		
		documento.add(tablaFecha);
		documento.add(table);

		System.out.println("Nomina generada correctamente.");

		
		documento.close();

	}

	public void generarExtra(Map<String, Double> mapDatosNomina, Map<String, String> mapDatosEmpresa, boolean prorrateoFlag)
			throws DocumentException {

		Document documento = new Document();
		String cantidad = "30 dias";
		String nombreEmpresa = mapDatosEmpresa.get("nombreEmpresa");
		String CIFEmpresa = mapDatosEmpresa.get("CIFEmpresa");
		String nombreTrabajador = mapDatosEmpresa.get("nombreTrabajador");
		String primerApellidoTrabajador = mapDatosEmpresa.get("primerApellidoTrabajador");
		String segundoApellidoTrabajador = mapDatosEmpresa.get("segundoApellidoTrabajador");
		String DNITrabajador = mapDatosEmpresa.get("DNITrabajador");
		String mesSolicitudStr = mapDatosEmpresa.get("mesSolicitudStr");
		String añoSolicitudStr = mapDatosEmpresa.get("añoSolicitudStr");
		
		double salarioBaseDevengo = mapDatosNomina.get("salarioBaseDevengo") / 30;
		double unidadesRedondeo = Math.round(salarioBaseDevengo * 100.0) / 100.0;
		String unidadesSalario = String.valueOf(unidadesRedondeo);
		double prorrataDevengo = mapDatosNomina.get("prorrata") / 30;
		double unidadesProrrata = Math.round(prorrataDevengo * 100.0) / 100.0;
		String unidadesProrrataStr = String.valueOf(unidadesProrrata);
		double complementoDevengo = mapDatosNomina.get("complementoDevengo") / 30;
		double complementoRedondeo = Math.round(complementoDevengo * 100.0) / 100.0;
		String complementoStr = String.valueOf(complementoRedondeo);
		double antiguedadDevengo = mapDatosNomina.get("antiguedadDevengo") / 30;
		double antiguedadRedondeo = Math.round(antiguedadDevengo * 100.0) / 100.0;
		String antiguedadStr = String.valueOf(antiguedadRedondeo);

		String path = "resource/" + DNITrabajador + "_" + nombreTrabajador + primerApellidoTrabajador
				+ segundoApellidoTrabajador + "_" + mesSolicitudStr + añoSolicitudStr + "Extra.pdf";

		try {
			PdfWriter.getInstance(documento, new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		documento.open();

		Paragraph empty = new Paragraph("");
		PdfPTable tableDatosEmpresa = new PdfPTable(2);
		Paragraph nom = new Paragraph(nombreEmpresa);
		Paragraph cif = new Paragraph(CIFEmpresa);
		Paragraph dir1 = new Paragraph("Avenida de la facultad - 6");
		Paragraph dir2 = new Paragraph("24001 Leon");
		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(nom);
		cell1.addElement(cif);
		cell1.addElement(empty);
		cell1.addElement(dir1);
		cell1.addElement(dir2);
		cell1.addElement(empty);
		cell1.setPadding(1);
		PdfPCell cell2 = new PdfPCell(new Paragraph(""));
		cell2.setBorder(Rectangle.NO_BORDER);
		Paragraph IBAN = new Paragraph("IBAN: "+mapDatosEmpresa.get("IBAN"));
		Paragraph categoria = new Paragraph("Categoria: "+mapDatosEmpresa.get("categoria"));
		Paragraph brutoAnual = new Paragraph("Bruto Anual: "+mapDatosNomina.get("brutoAnual"));
		Paragraph fechaAltaEmpresa = new Paragraph("Fecha de alta: "+mapDatosEmpresa.get("fechaAltaEmpresa"));
		cell2.addElement(IBAN);
		cell2.addElement(categoria);
		cell2.addElement(brutoAnual);
		cell2.addElement(fechaAltaEmpresa);

		tableDatosEmpresa.addCell(cell1);
		tableDatosEmpresa.addCell(cell2);
		tableDatosEmpresa.setSpacingAfter(10f);
		documento.add(tableDatosEmpresa);
		PdfPTable tableDatosTrabajador = new PdfPTable(2);
		Paragraph destinatario = new Paragraph("Destinatario: ");
		Paragraph nomTrabajador = new Paragraph(
				nombreTrabajador + " " + primerApellidoTrabajador + " " + segundoApellidoTrabajador);
		nomTrabajador.setAlignment(Element.ALIGN_RIGHT);
		Paragraph niftrab = new Paragraph("DNI: " + DNITrabajador);
		niftrab.setAlignment(Element.ALIGN_RIGHT);
		Paragraph dir1trab = new Paragraph("Avenida de la facultad");
		dir1trab.setAlignment(Element.ALIGN_RIGHT);
		Paragraph dir2trab = new Paragraph("24001 Leon");
		dir2trab.setAlignment(Element.ALIGN_RIGHT);
		Image img = null;
		try {
			img = Image.getInstance("resource/img.jpg");
		} catch (MalformedURLException e) {
                    // TODO Auto-generated catch block

		} catch (IOException e) {
                    // TODO Auto-generated catch block

		}
		PdfPCell cellImagen = new PdfPCell(img, true);
		cellImagen.setBorder(Rectangle.NO_BORDER);
		cellImagen.setPadding(10);
		cellImagen.setPaddingTop(30);
		cellImagen.setFixedHeight(90);
		PdfPCell celltrabajador = new PdfPCell();
		celltrabajador.addElement(destinatario);
		celltrabajador.addElement(nomTrabajador);
		celltrabajador.addElement(niftrab);
		celltrabajador.addElement(empty);
		celltrabajador.addElement(dir1trab);
		celltrabajador.addElement(dir2trab);
		celltrabajador.addElement(empty);
		celltrabajador.setIndent(10);
		celltrabajador.setPadding(10);
		tableDatosTrabajador.addCell(cellImagen);
		tableDatosTrabajador.addCell(celltrabajador);
		tableDatosTrabajador.setSpacingAfter(10f);
		documento.add(tableDatosTrabajador);
		PdfPTable tablaFecha = new PdfPTable(1);
		Font fontTit = new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.BLACK);
		Paragraph fechLit = new Paragraph("Nomina: " + mesSolicitudStr + " de " + añoSolicitudStr, fontTit);
		fechLit.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cellfecha2 = new PdfPCell();
		cellfecha2.addElement(fechLit);
		cellfecha2.setBorder(Rectangle.NO_BORDER);
		cellfecha2.setPadding(10);
		tablaFecha.addCell(cellfecha2);
		PdfPCell cellfecha3 = new PdfPCell(new Paragraph(""));
		cellfecha3.setBorder(Rectangle.NO_BORDER);
		tablaFecha.addCell(cellfecha3);
		tablaFecha.setSpacingAfter(10f);

		PdfPTable table = new PdfPTable(6);

		PdfPCell celdaGrande = new PdfPCell(new Paragraph(""));
		celdaGrande.setColspan(2);
		celdaGrande.setBorder(Rectangle.NO_BORDER);
		PdfPCell celdaSalario = new PdfPCell(new Paragraph("Salario Base"));
		celdaSalario.setColspan(2);
		PdfPCell celdaProrrata = new PdfPCell(new Paragraph("Prorrata"));
		celdaProrrata.setColspan(2);
		PdfPCell celdaComplemento = new PdfPCell(new Paragraph("Complemento"));
		celdaComplemento.setColspan(2);
		PdfPCell celdaAntiguedad = new PdfPCell(new Paragraph("Antigüedad"));
		celdaAntiguedad.setColspan(2);
		
		PdfPCell celdaContingencias = new PdfPCell(new Paragraph("Contigencias Generales"));
		celdaContingencias.setColspan(2);
		
		PdfPCell celdaDesempleo = new PdfPCell(new Paragraph("Desempleo"));
		celdaDesempleo.setColspan(2);
		
		PdfPCell celdaFormacion = new PdfPCell(new Paragraph("Formacion"));
		celdaFormacion.setColspan(2);
		
		PdfPCell celdaIRPF = new PdfPCell(new Paragraph("IRPF"));
		celdaIRPF.setColspan(2);
		
		PdfPCell celdaTotalDeducciones = new PdfPCell(new Paragraph("Total Deducciones"));
		celdaTotalDeducciones.setColspan(2);

		PdfPCell celdaTotalDevengos = new PdfPCell(new Paragraph("Total Devengos"));
		celdaTotalDevengos.setColspan(2);
		
		PdfPCell celdaLiquidoAPercibir = new PdfPCell(new Paragraph("Liquido a percibir", new Font(FontFamily.HELVETICA, 12, Font.UNDERLINE, GrayColor.BLACK)));
		celdaLiquidoAPercibir.setColspan(2);
		
		
		table.addCell(celdaGrande);
		table.addCell("cant.");
		table.addCell("Imp. Unit.");
		table.addCell("Dev.");
		table.addCell("Deducc.");

		table.addCell(celdaSalario);
		table.addCell(cantidad);
		table.addCell(unidadesSalario);
		table.addCell(String.valueOf(mapDatosNomina.get("salarioBaseDevengo")));
		table.addCell("");

		
		if (prorrateoFlag == true) {
			
			table.addCell(celdaProrrata);
			table.addCell(cantidad);
			table.addCell(unidadesProrrataStr);
			table.addCell(String.valueOf(mapDatosNomina.get("prorrata")));
			table.addCell("");

		}else {
			
			table.addCell(celdaProrrata);
			table.addCell(cantidad);
			table.addCell("00.00");
			table.addCell("00.00");
			table.addCell("");
			
		}
		
		table.addCell(celdaComplemento);
		table.addCell(cantidad);
		table.addCell(complementoStr);
		table.addCell(String.valueOf(mapDatosNomina.get("complementoDevengo")));
		table.addCell("");
		
		table.addCell(celdaAntiguedad);
		table.addCell(String.valueOf(mapDatosNomina.get("numeroTrienios").intValue())+" Trienios");
		table.addCell(antiguedadStr);
		table.addCell(String.valueOf(mapDatosNomina.get("antiguedadDevengo")));
		table.addCell("");
		
		PdfPCell cellEspacio = new PdfPCell();
		cellEspacio.addElement(new Paragraph(""));
		cellEspacio.setBorder(Rectangle.NO_BORDER);
		cellEspacio.setPadding(10);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);


		
		
		table.addCell(celdaContingencias);
		table.addCell("00.00%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeSeguridadSocial")));
		
		
		table.addCell(celdaDesempleo);
		table.addCell("00.00%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeDesempleo")));

		table.addCell(celdaFormacion);
		table.addCell("00.00%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("baseReguladora")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeFormacion")));
		
		table.addCell(celdaIRPF);
		table.addCell("00.00%");
		table.addCell("de "+String.valueOf(mapDatosNomina.get("brutoMensual")));
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("importeIRPF")));
		
		

		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		
		table.addCell(celdaTotalDeducciones);
		table.addCell("");
		table.addCell("");
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("totalDeduccionesTrabajador")));

		table.addCell(celdaTotalDevengos);
		table.addCell("");
		table.addCell("");
		table.addCell(String.valueOf(mapDatosNomina.get("brutoMensual")));
		table.addCell("");


		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		celdaLiquidoAPercibir.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaLiquidoAPercibir);
		PdfPCell celdaLiquido = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("liquidoAPercibir"))));
		celdaLiquido.setBorder(Rectangle.NO_BORDER);		
		table.addCell(celdaLiquido);
		
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		
		PdfPCell lineaDebajo = new PdfPCell(new Paragraph(""));
		lineaDebajo.setBorder(Rectangle.BOTTOM);
		PdfPCell celdaBaseReguladora = new PdfPCell(new Paragraph("Calculo empresario: BASE",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteBase = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("baseReguladora")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaBaseReguladora.setColspan(3);
		celdaBaseReguladora.setBorder(Rectangle.BOTTOM);
		celdaImporteBase.setBorder(Rectangle.BOTTOM);
		table.addCell(celdaBaseReguladora);
		table.addCell(lineaDebajo);
		table.addCell(lineaDebajo);
		table.addCell(celdaImporteBase);
		
		PdfPCell celdaContingenciasComunes = new PdfPCell(new Paragraph("Contingencias comunes "+"00.00%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteCon = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeContigenciasComunesEmp")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaContingenciasComunes.setColspan(3);
		celdaContingenciasComunes.setBorder(Rectangle.NO_BORDER);
		celdaImporteCon.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaContingenciasComunes);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteCon);
		
		PdfPCell celdaDesempleoEm = new PdfPCell(new Paragraph("Desempleo "+"00.00%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteDesEm = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeDesempleoEmpr")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaDesempleoEm.setColspan(3);
		celdaDesempleoEm.setBorder(Rectangle.NO_BORDER);
		celdaImporteDesEm.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaDesempleoEm);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteDesEm);
		
		
		PdfPCell celdaFormacionEmp = new PdfPCell(new Paragraph("Formacion "+"00.00%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteForEm = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeFormacionEmpr")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaFormacionEmp.setColspan(3);
		celdaFormacionEmp.setBorder(Rectangle.NO_BORDER);
		celdaImporteForEm.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaFormacionEmp);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteForEm);
		
		PdfPCell celdaAccidentes = new PdfPCell(new Paragraph("Accidentes de trabajo "+"00.00%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteAccidentes = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeAccidentes")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaAccidentes.setColspan(3);
		celdaAccidentes.setBorder(Rectangle.NO_BORDER);
		celdaImporteAccidentes.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaAccidentes);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteAccidentes);
		
		PdfPCell celdaFOGASA = new PdfPCell(new Paragraph("FOGASA "+"00.00%",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteFOGASA = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("importeFOGASA")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaFOGASA.setColspan(3);
		celdaFOGASA.setBorder(Rectangle.BOTTOM);
		celdaImporteFOGASA.setBorder(Rectangle.BOTTOM);
		table.addCell(celdaFOGASA);
		table.addCell(lineaDebajo);
		table.addCell(lineaDebajo);
		table.addCell(celdaImporteFOGASA);
		
		PdfPCell celdaTotalEmpr = new PdfPCell(new Paragraph("TOTAL empresario ",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteTotalEmp = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("totalDeduccionesEmpresario")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaTotalEmpr.setColspan(3);
		celdaTotalEmpr.setBorder(Rectangle.NO_BORDER);
		celdaImporteTotalEmp.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaTotalEmpr);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteTotalEmp);
		
		PdfPCell celdaCoste = new PdfPCell(new Paragraph("Coste total trabajador ",new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		PdfPCell celdaImporteCoste = new PdfPCell(new Paragraph(String.valueOf(mapDatosNomina.get("costeTotalTrabajador")),new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, GrayColor.GRAY)));
		celdaCoste.setColspan(3);
		celdaCoste.setBorder(Rectangle.NO_BORDER);
		celdaImporteCoste.setBorder(Rectangle.NO_BORDER);
		table.addCell(celdaCoste);
		table.addCell(cellEspacio);
		table.addCell(cellEspacio);
		table.addCell(celdaImporteCoste);
		
		documento.add(tablaFecha);
		documento.add(table);
		
		System.out.println("Nomina extra generada correctamente.");

		documento.close();

	}

	
}
