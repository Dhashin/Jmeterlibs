import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import sun.security.util.Length;


// This program was created to iterate through results in SAFAL and display a summary report of them
public class CreateReport {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidFormatException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")

	public void createReport(String myDirectoryPath,	String resultsDirectoryPath, String TemplateLocation, String resultsDirectoryPath2)throws InvalidFormatException, FileNotFoundException, IOException, ParseException
	{






		//Initialization of global variables


		Sheet CurrentSheet;
		String date="";
		String FileName="";
		String Total_Runtime = null;
		String TimeCell = null;
		String FailedTest="";
		String DefectDescription="";
		String StepDescription="";
		String Browser = "";

		int Total_Tests=0;
		int Total_Tests_Passed=0;
		int Total_Tests_Failed=0;
		int Passed_Steps = 0;
		int Failed_Steps = 0;
		int Total_Steps = 0;
		int LastRow=0;
		int LastRow2= 0;
		int count = 16;
		int count2 = 0;

		double PassPercentage=0;

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date time;

		long sum = 0;










		//Creating a connection to the template workbook

		Workbook workbook_template = new XSSFWorkbook(OPCPackage.open(new FileInputStream(TemplateLocation)));// Import the template workbook
		Sheet Main_Sheet = workbook_template.getSheetAt(0);		
		Sheet Analysis_Sheet = workbook_template.getSheetAt(1);	


		File dir = new File(myDirectoryPath); 					//Create a directory path of the directory the results are
		File[] directoryListing = dir.listFiles();  			// get a list of files from the directory and store it in the array
		if (directoryListing != null) { 						//A loop that runs while theres is still a file available in the directory


			// Get the first file in the directory and check the file extension (xls or xlsx)
			System.out.println("1");
			System.out.println(directoryListing[0]);
			File CheckChild = directoryListing[0];				
			System.out.println(CheckChild);
			String b = CheckChild.toString();
			int x=	b.lastIndexOf(".")+1;
			int c = b.length();
			String CheckWhatFile = b.substring(x,c);
			//System.out.println(a);
			//System.out.println(c);
			System.out.println(CheckWhatFile);




			// If the file is an xlsx file - use this set of code to import the workbook

			if(CheckWhatFile.equalsIgnoreCase("xlsx"))

			{

				Workbook workbook;
				Sheet summary;
				for (File child : directoryListing) 
				{ 


					workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(child)));   // For xlsx
					summary = workbook.getSheetAt(0);
					Browser=(summary.getRow(3).getCell(3).toString());
					date=(summary.getRow(2).getCell(3).toString()).substring(0,15);
					LastRow=summary.getLastRowNum();

					Passed_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(5).toString());
					Failed_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(6).toString());
					Total_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(7).toString());

					TimeCell = summary.getRow(LastRow).getCell(10).toString();;
					//time = timeFormat.parse(TimeCell);
					//sum = sum + time.getTime();


					for (int i=6;i<(LastRow);i++)
					{
						Total_Tests++;


						if(Integer.parseInt((summary.getRow(i).getCell(6).toString()).substring(0,1))>0)
						{
							Total_Tests_Failed++;
							FailedTest=summary.getRow(i).getCell(2).toString();
							if(FailedTest.lastIndexOf("\\")>1)
							{
								FailedTest = FailedTest.substring(1+(FailedTest.lastIndexOf("\\")),FailedTest.lastIndexOf('.'));
							}
							else
							{

							}





							//	System.out.println(FailedTest);
							int spreadsheetindex = workbook.getSheetIndex(FailedTest);
							CurrentSheet = workbook.getSheetAt(spreadsheetindex); 
							LastRow2=CurrentSheet.getLastRowNum();
							// System.out.println(LastRow2);
							for (int a=1 ;a<=(LastRow2);a++)
							{

								String CheckIfFail=CurrentSheet.getRow(a).getCell(3).toString();

								if(CheckIfFail.equals("FAIL"))
								{

									DefectDescription=CurrentSheet.getRow(a).getCell(4).toString();
									StepDescription=CurrentSheet.getRow(a).getCell(1).toString();
									System.out.println(FailedTest);
									System.out.println(summary.getRow(1).getCell(3).toString());



									FileName=summary.getRow(1).getCell(3).toString();
									if(FileName.lastIndexOf("\\")>1)
									{
										FileName = FileName.substring(1+(FileName.lastIndexOf("\\")),FileName.lastIndexOf('.'));
									}
									else
									{

									}
									count2++;



									Main_Sheet.getRow(count).getCell(2).setCellValue(FileName);
									Main_Sheet.getRow(count).getCell(3).setCellValue(FailedTest);
									Main_Sheet.getRow(count).getCell(4).setCellValue(DefectDescription);
									Main_Sheet.getRow(count).getCell(5).setCellValue(StepDescription);


									Analysis_Sheet.createRow(count2).createCell(0).setCellValue(count2);
									Analysis_Sheet.getRow(count2).createCell(5).setCellValue(Browser);
									Analysis_Sheet.getRow(count2).createCell(6).setCellValue(FileName);
									Analysis_Sheet.getRow(count2).createCell(7).setCellValue(FailedTest);
									Analysis_Sheet.getRow(count2).createCell(8).setCellValue(StepDescription);

									//Analysis_Sheet.getRow(count2).createCell(2).setCellValue("SCRIPT ERROR");
									System.out.println(count2);

									/*
									Row row = Analysis_Sheet.getRow(count2);
									if(row == null)
									{
										row = Analysis_Sheet.createRow(count2);
									}

									Cell cell = row.getCell(8);
									cell.setCellValue(StepDescription);
									 */
									count++;
								}

							}

						}
					}

				}
			}

			// If the file is an xls file - use this set of code to import the workbook
			else
			{			

				HSSFWorkbook workbook;
				HSSFSheet summary;
				for (File child : directoryListing) 
				{ 	
					workbook = new HSSFWorkbook(new FileInputStream(child));


					summary = workbook.getSheetAt(0);
					Browser=(summary.getRow(3).getCell(3).toString());
					date=(summary.getRow(2).getCell(3).toString()).substring(0,15);
					LastRow=summary.getLastRowNum();

					Passed_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(5).toString());
					Failed_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(6).toString());
					Total_Steps += Integer.parseInt(summary.getRow(LastRow).getCell(7).toString());

					TimeCell = summary.getRow(LastRow).getCell(10).toString();;
					time = timeFormat.parse(TimeCell);
					sum = sum + time.getTime();


					for (int i=6;i<(LastRow);i++)
					{
						Total_Tests++;


						if(Integer.parseInt((summary.getRow(i).getCell(6).toString()).substring(0,1))>0)
						{
							Total_Tests_Failed++;
							FailedTest=summary.getRow(i).getCell(2).toString();
							if(FailedTest.lastIndexOf("\\")>1)
							{
								FailedTest = FailedTest.substring(1+(FailedTest.lastIndexOf("\\")),FailedTest.lastIndexOf('.'));
							}
							else
							{

							}
							//	System.out.println(FailedTest);
							int spreadsheetindex = workbook.getSheetIndex(FailedTest);
							CurrentSheet = workbook.getSheetAt(spreadsheetindex); 

							LastRow2=CurrentSheet.getLastRowNum();
							// System.out.println(LastRow2);
							for (int a=1 ;a<=(LastRow2);a++)
							{

								String CheckIfFail=CurrentSheet.getRow(a).getCell(3).toString();// set a variable to chechk if a step has failed or not

								if(CheckIfFail.equals("FAIL")) //if the step has failed then go into the workbook and get the required data
								{

									DefectDescription=CurrentSheet.getRow(a).getCell(4).toString();
									StepDescription=CurrentSheet.getRow(a).getCell(1).toString();
									System.out.println(FailedTest);

									FileName=summary.getRow(1).getCell(3).toString();
									if(FileName.lastIndexOf("\\")>1)
									{
										FileName = FileName.substring(1+(FileName.lastIndexOf("\\")),FileName.lastIndexOf('.'));
									}
									else
									{

									}
									count2++;



									Main_Sheet.getRow(count).getCell(2).setCellValue(FileName);
									Main_Sheet.getRow(count).getCell(3).setCellValue(FailedTest);
									Main_Sheet.getRow(count).getCell(4).setCellValue(DefectDescription);
									Main_Sheet.getRow(count).getCell(5).setCellValue(StepDescription);
									Analysis_Sheet.createRow(count2).createCell(6).setCellValue(FileName);
									Analysis_Sheet.createRow(count2).createCell(7).setCellValue(FailedTest);
									Analysis_Sheet.createRow(count2).createCell(8).setCellValue(StepDescription);
									System.out.println(count2);
									count++;
								}

							}

						}
					}

				}

			}












		} 
		else
		{

		}

		Total_Runtime = timeFormat.format(new Date(sum)); 													//calculating the total execution time
		Total_Tests_Passed = Total_Tests-Total_Tests_Failed;												//calculating the total tests passed
		PassPercentage = ((double)Total_Tests_Passed/Total_Tests)*100;										// calculating the pass percentage




		Main_Sheet.getRow(1).getCell(3).setCellValue(Browser);																//appending cells with relevant data
		Main_Sheet.getRow(2).getCell(3).setCellValue(date);
		Main_Sheet.getRow(5).getCell(3).setCellValue(Total_Tests);
		Main_Sheet.getRow(6).getCell(3).setCellValue(Total_Tests_Failed);
		Main_Sheet.getRow(7).getCell(3).setCellValue(Total_Tests_Passed); 
		Main_Sheet.getRow(8).getCell(3).setCellValue(Total_Steps);
		Main_Sheet.getRow(9).getCell(3).setCellValue(Passed_Steps);
		Main_Sheet.getRow(10).getCell(3).setCellValue(Failed_Steps);
		Main_Sheet.getRow(11).getCell(3).setCellValue(PassPercentage);
		Main_Sheet.getRow(12).getCell(3).setCellValue(Total_Runtime);


		for (int i = 0; i < 15; i++) {
			Main_Sheet.autoSizeColumn(i);																					// loop to autosize the excel columns 
		}


		//SimpleDateFormat ft = new SimpleDateFormat ("HH-MM"); 															// setting a time format 
		//Date dNow = new Date();																							//created a date object

		//String time1= ft.format(dNow);																					// getting a time and sotring in a string to inclue in the file name

		FileOutputStream f = new FileOutputStream(resultsDirectoryPath +Browser +"_"+ date+".xlsx"); // create an outputstream to write the file-- the parameter in brackets is the path and name of the file
		FileOutputStream g = new FileOutputStream(resultsDirectoryPath2 +Browser +"_"+ date +".xlsx"); // create an outputstream to write the file-- the parameter in brackets is the path and name of the file //this is for the second location

		workbook_template.write(f); // do the actual writing of the file
		workbook_template.write(g); // do the actual writing of the file
		f.close(); //close the first output stream
		g.close(); //close the second output stream




		AnalysisSheet analysisSheet = new AnalysisSheet();// create an object in reference to class AnalysisSheet
		analysisSheet.checkRepeatedFailures(resultsDirectoryPath +Browser +"_"+ date +".xlsx");// this calls the method check repeated failures from the class Analysis Sheet with the parameter as the full path to file




	}











	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException, ParseException
	{





		System.out.println("fdgdgdfg");

		String myDirectoryPath = "C:\\Temp\\";
		String resultsDirectoryPath = "C:\\Users\\Dhashin.moodley\\Desktop\\Random\\";
		String TemplateLocation = "C:\\Users\\Dhashin.moodley\\Desktop\\Random\\ReportTemplate.xlsx";
		String resultsDirectoryPath2 = "C:\\Reports\\";





		CreateReport obj = new CreateReport();
		obj.createReport(myDirectoryPath, resultsDirectoryPath, TemplateLocation, resultsDirectoryPath2 ) ;




	}
}