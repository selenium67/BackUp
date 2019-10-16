/**
* Company		:	Accenture
* Project		:	Aviall
* FileName		:	Excel Reading
* Author		:	Suresh Kurry
* Description	:	Get the objects from Excel sheet and execute the methods defined in this file 
**/
package com.testng.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.aviall.utilities.GlobalConstants;



/**
 * @author Suresh Kurry
 *
 */
public class ReadTestData {
	
	public static String sPreviousTestCaseName;
	public static int iRowNo;
	public static File file;
	public static FileInputStream fin;
	public static XSSFWorkbook wb;
	public static XSSFSheet sh;
	public static int rows;
	public static int cols;
	public static String sCurTestCaseName;
	public static String sKey;
	public static String sValue;
	public static Map.Entry<String, Map<String, String>> entry;
	public static Map<String, String> values;
	

	/* Below Method is Used to get multiple sets of data from excel.
	 * Parameters: File Path, Sheet Name, TestCase Name
	*/
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static Map<String, Map<String, String>> readMultipleTestData(String sFilePath, String sSheetName, String sTestCaseName)
			throws Exception {
		
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> objTestData = new HashMap();
		
		try {

			sPreviousTestCaseName = "";
			iRowNo = 1;
			// Go To File Path
			file = new File(sFilePath);			
			fin = new FileInputStream(file);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sSheetName);
			rows = sh.getLastRowNum() + 1;
			cols = sh.getRow(0).getLastCellNum();
			/*
			 * iRowCounter is used to get the Test Case Name
			 * */
			for (int iRowCounter = 1; iRowCounter < rows; iRowCounter++) {
				
				// Below Map Interface is used to find Single Set of Data
				@SuppressWarnings("unchecked")
				Map<String, String> objRowData = new HashMap();
				sCurTestCaseName = sh.getRow(iRowCounter).getCell(0).getStringCellValue().trim();
				if ((sPreviousTestCaseName.length() != 0)
						&& (sCurTestCaseName != sPreviousTestCaseName)
						&& (sTestCaseName.trim().length() > 0)) {
					break;
				}
				if (sCurTestCaseName.equalsIgnoreCase(sTestCaseName)) {
					sPreviousTestCaseName = sCurTestCaseName;
					// Below Loop is used to get all the headers names in the excel
					for (int iColCounter = 0; iColCounter < cols; iColCounter++) {
						sKey = sh.getRow(0).getCell(iColCounter).getStringCellValue().trim();
						sValue = "";
						if (sh.getRow(iRowCounter).getCell(iColCounter) != null) {
							if (sh.getRow(iRowCounter).getCell(iColCounter).getCellType() == Cell.CELL_TYPE_STRING) {
								sValue = sh.getRow(iRowCounter)
										.getCell(iColCounter)
										.getStringCellValue().trim();
							} else {
								sValue = sh.getRow(iRowCounter)
										.getCell(iColCounter).getRawValue();
							}
						}

							objRowData.put(sKey, sValue);						
					}
					objTestData.put("Row" + iRowNo, objRowData);

					objRowData = null;

					iRowNo++;
				}

			}

		} catch (Exception e) {

		}
		return objTestData;
	}
	
	public static Map<String, String> retrieveData(String sheetName, String testCase_Name){
		
		
		Map<String, Map<String, String>> testData = null;
		
		try {
			testData = readMultipleTestData(GlobalConstants.TESTDATAFILE, sheetName, testCase_Name);
			
		} catch (Exception e) {
			
			Assert.fail(e.getMessage());
		}
		
		Iterator<Entry<String, Map<String, String>>> entries = testData.entrySet().iterator();
		
		while (entries.hasNext()) {
			
			entry = (Entry<String, Map<String, String>>)entries.next();
			values = (Map<String, String>) entry.getValue();	
			
		}
		return values;
				
	}
	
	
	public static Map<String, String> getTestData(String sheetName, String testCase_Name){
		
		Map<String, Map<String, String>> testData = null;
		try {
			testData = readMultipleTestData(GlobalConstants.TESTDATAFILE, sheetName, testCase_Name);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Iterator<Entry<String, Map<String, String>>> entries = testData.entrySet().iterator();
		while (entries.hasNext()) {
			entry = (Entry<String, Map<String, String>>)entries.next();
			values = (Map<String, String>) entry.getValue();	
		}
		return values;
	}
	

	public static void updateOrders(String fpath, HashMap<String,String>step) {
			FileInputStream fis;
			FileOutputStream fos;
			XSSFWorkbook wb;
			XSSFSheet sh = null;
			XSSFRow row;
			String ScriptName;
			try {
				deletesheet(fpath);
				fis = new FileInputStream(new File(fpath));
				try {
					wb = new XSSFWorkbook(fis); 
					sh = wb.getSheet("EndToEndOrders");
					row = sh.getRow(0);
					int fcid = row.getFirstCellNum();
					int lcid = row.getLastCellNum()-1;
					Iterator<String> KeySetIterator = step.keySet().iterator();
					while (KeySetIterator.hasNext()) {
						String key = KeySetIterator.next();
						String kv = step.get(key);
						String[] kvals = kv.split(" ");
						for (int r = 1; r<=sh.getLastRowNum(); r++) {
							row = sh.getRow(r);
							ScriptName = row.getCell(fcid).getStringCellValue();
							if (ScriptName.equalsIgnoreCase(kvals[0])) {
								row.createCell(lcid).setCellValue(kvals[1]);
							}					
						}												
					}
					
					fis.close();
					fos = new FileOutputStream(new File(fpath));
					wb.write(fos);
					fos.flush();
					fos.close();
					deleterows(fpath);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	

	public static void deletesheet(String fpath) throws FileNotFoundException {
		FileInputStream fis;
		FileOutputStream fos;
		XSSFWorkbook wb;
		fis = new FileInputStream(new File(fpath));		
		try {
			wb = new XSSFWorkbook(fis);
			if (wb.getNumberOfSheets()>=1) {
				for (int i=wb.getNumberOfSheets()-1; i>=0; i--) {
					if (!wb.getSheetAt(i).getSheetName().equalsIgnoreCase("EndToEndOrders")) {
						wb.removeSheetAt(i);
					}
				}
			}
			fis.close();
			fos = new FileOutputStream(new File(fpath));
			wb.write(fos);
			wb.close();
			fos.close();
		} catch (IOException e) {
			e.getMessage();		
		}
				
	}
	
public static void deleterows(String fpath) throws FileNotFoundException {
		
		FileInputStream fis;
		FileOutputStream fos;
		XSSFWorkbook wb;
		XSSFSheet sh = null;
		XSSFRow row;
		XSSFRow lrow;
		XSSFCell lcell;
		String ScriptName;
		fis = new FileInputStream(new File(fpath));	
		try {
			wb = new XSSFWorkbook(fis);
			sh = wb.getSheet("EndToEndOrders");
			row = sh.getRow(0);
			int fcid = row.getFirstCellNum();
			int lcid = row.getLastCellNum()-1;
			int rcnt = sh.getLastRowNum();
//			for (int r = 1; r<=sh.getLastRowNum()-1; r++) {
			for (int r = 1; r<rcnt+1; r++) {			
				lrow = sh.getRow(r);
				if (lrow.getCell(lcid) == null || lrow.getCell(lcid).getStringCellValue().equals("")) { 
					
					sh.shiftRows(r+1, rcnt+1, -1);
					rcnt = rcnt-1;
					r=r-2;

				}
			}
			fis.close();
			fos = new FileOutputStream(new File(fpath));
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	

	
}
