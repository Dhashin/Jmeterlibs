import java.io.Console;
import java.io.File;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;

import sun.security.util.Length;


// This program was created to iterate through results in SAFAL and display a summary report of them
public class AnalysisSheet {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")



	public static void checkRepeatedFailures( String pathToFileToday)throws InvalidFormatException, FileNotFoundException, IOException, ParseException
	{



		String temp = 	pathToFileToday.substring(pathToFileToday.indexOf('_')+1,pathToFileToday.lastIndexOf('.'));

		SimpleDateFormat safal = new SimpleDateFormat("E_MMM_dd_yyyy");
		Date startDate = safal.parse(temp);
		Date	oneDayBefore = new Date(startDate.getTime() - 2);
		temp = safal.format(oneDayBefore);


		String pathToFileYesterday = "";
		System.out.println(pathToFileToday);
		pathToFileYesterday = pathToFileToday.substring(0,pathToFileToday.indexOf('_')+1)+temp +pathToFileToday.substring(pathToFileToday.lastIndexOf('.'),pathToFileToday.length());
		System.out.println(pathToFileToday);
		
		System.out.println(pathToFileYesterday);




		int lastRowNumToday=0;
		int lastRowNumYday=0;
		String fileNameToday ="";
		String fileNameYday ="";
		String testNameToday="";
		String testNameYday="";
		String stepDescriptionToday ="";
		String stepDescriptionYday ="";
		String reasonForFailureToday="";
		String reasonForFailureYday="";
		String jiraId="";
		String failureDescription="";
		int count=0;



		Workbook today= new XSSFWorkbook(OPCPackage.open(new FileInputStream(pathToFileToday)));
		Sheet mainSheetToday = today.getSheetAt(0);
		Sheet analysisSheetToday = today.getSheetAt(1);

		Workbook yday= new XSSFWorkbook(OPCPackage.open(new FileInputStream(pathToFileYesterday)));
		Sheet mainSheetYday = yday.getSheetAt(0);
		Sheet analysisSheetYday = yday.getSheetAt(1);



		lastRowNumToday=analysisSheetToday.getPhysicalNumberOfRows()-1;
		lastRowNumYday=analysisSheetYday.getPhysicalNumberOfRows()-1;
		System.out.println("the total number of rows in todays analysis sheet is " +lastRowNumToday);
		System.out.println("the total number of rows in yesterdays analysis sheet is " +lastRowNumYday);


		for (int a =0; a<lastRowNumToday; a++)
		{
			if(analysisSheetToday.getRow(a)!=null)
			{

				if(analysisSheetToday.getRow(a).getCell(6)!=null)
				{
					fileNameToday=analysisSheetToday.getRow(a).getCell(6).toString();
					//		System.out.println(fileNameToday);


					for (int b =0; b<lastRowNumYday; b++)
					{
						if(analysisSheetYday.getRow(b)!=null)
						{

							if(analysisSheetYday.getRow(b).getCell(6)!=null)
							{
								fileNameYday=analysisSheetYday.getRow(b).getCell(6).toString();
								//			System.out.println(fileNameYday);
								if(fileNameToday.equals(fileNameYday))
								{

									if(analysisSheetToday.getRow(a).getCell(7)!=null)
									{
										testNameToday=analysisSheetToday.getRow(a).getCell(7).toString();
									}
									if(analysisSheetYday.getRow(b).getCell(7)!=null)
									{
										testNameYday=analysisSheetYday.getRow(b).getCell(7).toString();
									}
									if (testNameToday.equals(testNameYday))
									{
										if(analysisSheetToday.getRow(a).getCell(8)!=null)
										{
											stepDescriptionToday =analysisSheetToday.getRow(a).getCell(8).toString();

										}
										if(analysisSheetYday.getRow(b).getCell(8)!=null)
										{
											stepDescriptionYday =analysisSheetYday.getRow(b).getCell(8).toString();
										}
										if (stepDescriptionToday.equals(stepDescriptionYday))
										{
											if(analysisSheetYday.getRow(b).getCell(2)!=null)
											{
												reasonForFailureYday=analysisSheetYday.getRow(b).getCell(2).toString();
											}
											else
											{
												reasonForFailureYday="";
											}
											reasonForFailureToday=	reasonForFailureYday;

											if(analysisSheetYday.getRow(b).getCell(1)!=null)
											{
												jiraId=analysisSheetYday.getRow(b).getCell(1).toString();
											}
											else
											{
												jiraId="";
											}

											if(analysisSheetYday.getRow(b).getCell(3)!=null)
											{
												failureDescription=analysisSheetYday.getRow(b).getCell(3).toString();
											}
											else
											{
												failureDescription="";
											}





											System.out.println("");
											System.out.println("File is "							+fileNameToday);
											System.out.println("Test is " 						+testNameToday);
											System.out.println("Step is " 						+stepDescriptionToday );
											System.out.println("Failure reason is " 	+reasonForFailureToday);
											System.out.println("JiraID is " 					+jiraId);
											System.out.println("Failure description " +failureDescription);
											System.out.println("");

											if(analysisSheetToday.getRow(a).getCell(1)!=null)
											{
												analysisSheetToday.getRow(a).getCell(1).setCellValue(jiraId);
											}
											else
											{
												analysisSheetToday.getRow(a).createCell(1).setCellValue(jiraId);

											}

											if(analysisSheetToday.getRow(a).getCell(2)!=null)
											{
												analysisSheetToday.getRow(a).getCell(2).setCellValue(reasonForFailureYday);
											}
											else
											{
												analysisSheetToday.getRow(a).createCell(2).setCellValue(reasonForFailureYday);
											}

											if(analysisSheetToday.getRow(a).getCell(3)!=null)
											{
												analysisSheetToday.getRow(a).getCell(3).setCellValue(failureDescription);
											}
											else
											{
												analysisSheetToday.getRow(a).createCell(3).setCellValue(failureDescription);
											}



											count++;
										}
									}










									//	System.out.println(fileNameToday+" Equals "+fileNameYday);

								}



							}
							else
							{
								System.out.println("there is a null value here");
							}

						}
						else
						{
							System.out.println("there is a null row here");

						}

					}




				}
				else
				{
					System.out.println("there is a null value here");
				}
			}
			else
			{
				System.out.println("there is a null row here");

			}

		}



		System.out.println("there are " + count+" similarfiles");


		FileOutputStream f = new FileOutputStream(pathToFileToday);
		today.write(f);
		f.close();
	}
	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException, ParseException
	{

	}

}
