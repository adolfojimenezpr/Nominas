package practica04;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class GeneracionXML {

	private Row currentRow;

	public GeneracionXML() {
		currentRow = null;
	}

	public void generarXMLDni(ArrayList<Row> listaBlancosDuplicados) {

		Iterator<Row> it = listaBlancosDuplicados.iterator();

		Element trabajadores = new Element("Trabajadores");
		Document doc = new Document(trabajadores);

		while (it.hasNext()) {

			currentRow = it.next();

			Element trabajador = new Element("Trabajador");
			trabajador.setAttribute(new Attribute("id", String.valueOf(currentRow.getRowNum() + 1)));

			trabajador.addContent(new Element("Nombre").setText(currentRow.getCell(3,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("PrimerApellido").setText(currentRow.getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("SegundoApellido").setText(currentRow.getCell(2,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("Categoria").setText(currentRow.getCell(5,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("Empresa").setText(currentRow.getCell(6,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));

			doc.getRootElement().addContent(trabajador);

		}
		XMLOutputter xmlOutput = new XMLOutputter();

		xmlOutput.setFormat(Format.getPrettyFormat());
		try {

			xmlOutput.output(doc, new FileWriter("resource/Errores.xml"));
			System.out.println("Archivo XML generado.");

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public void generarXMLCuentas(ArrayList<Row> listaCuentasErroneas) {
		Iterator<Row> it = listaCuentasErroneas.iterator();

		Element trabajadores = new Element("Trabajadores");
		Document doc = new Document(trabajadores);

		while (it.hasNext()) {

			currentRow = it.next();

			Element trabajador = new Element("Trabajador");
			trabajador.setAttribute(new Attribute("id", String.valueOf(currentRow.getRowNum() + 1)));

			trabajador.addContent(new Element("Nombre").setText(currentRow.getCell(3,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("PrimerApellido").setText(currentRow.getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("SegundoApellido").setText(currentRow.getCell(2,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("Empresa").setText(currentRow.getCell(6,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("CodigoCuentaErroneo").setText(currentRow.getCell(14,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			trabajador.addContent(new Element("IBANCorrecto").setText(currentRow.getCell(16,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
			
			doc.getRootElement().addContent(trabajador);

		}
		XMLOutputter xmlOutput = new XMLOutputter();

		xmlOutput.setFormat(Format.getPrettyFormat());
		try {

			xmlOutput.output(doc, new FileWriter("resource/cuentasErroneas.xml"));
			System.out.println("Archivo XML generado.");

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

}
