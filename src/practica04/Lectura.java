package practica04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Lectura {

	private static final String path = "resource/SistemasInformacionII.xlsx";
	private ArrayList<Row> listaDefinitiva;
	private ArrayList<Row> listaDefinitivaSegun;


	public Lectura() {
		listaDefinitiva = new ArrayList<Row>();
		listaDefinitivaSegun = new ArrayList<Row>();
	}

	public void leerFicheroPorFilas(int hoja) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(path));

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(hoja);

			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				if (hoja == 0) {

					listaDefinitiva.add(currentRow);
				}else if(hoja == 1)
				{
					listaDefinitivaSegun.add(currentRow);
				}
				workbook.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Row> getListaDefinitiva() {
		return listaDefinitiva;
	}

	public void setListaDefinitiva(ArrayList<Row> listaDefinitiva) {
		this.listaDefinitiva = listaDefinitiva;
	}

	public ArrayList<Row> getListaDefinitivaSegun() {
		return listaDefinitivaSegun;
	}

	public void setListaDefinitivaSegun(ArrayList<Row> listaDefinitivaSegun) {
		this.listaDefinitivaSegun = listaDefinitivaSegun;
	}


}
