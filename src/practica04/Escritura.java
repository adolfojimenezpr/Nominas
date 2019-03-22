package practica04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Escritura {
	private static final String path = "resource/SistemasInformacionII.xlsx";

	public Escritura() {

	}

	public void modificarCelda(ArrayList<Row> listaModificar, int columna, int hoja) {

		String cadena = "";
		int fila = 0;
		Iterator<Row> it = listaModificar.iterator();
		Row currentRow = null;
		Cell cell = null;
		

		try {

			FileInputStream excelFile = new FileInputStream(new File(path));

			XSSFWorkbook wb = new XSSFWorkbook(excelFile);
			XSSFSheet worksheet = wb.getSheetAt(hoja);

			while (it.hasNext()) {

				currentRow = it.next();
				fila = currentRow.getRowNum();
				cadena = currentRow.getCell(columna,MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();



				cell = worksheet.getRow(fila).getCell(columna,MissingCellPolicy.CREATE_NULL_AS_BLANK);

				if (!cadena.isEmpty())
					cell.setCellValue(cadena);

			}

			FileOutputStream output = new FileOutputStream(new File(path));

			wb.write(output);
			output.flush();
			output.close();

			//wb.close();
			//excelFile.close();
			System.out.println("Hoja Excel actualizada con exito.");

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}

	}

}
