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
import java.util.*;
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
public class CollateResults {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")

	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException, ParseException
	{

		String directory= "C:\\Reports\\";
		String startDate ="01/01/2016";//use format mm-dd-yyyy
		String endDate= "12/31/2016";//use format mm-dd-yyyy
		String file ="";
		String fileBrowser="";
		String fileDate="";
		String fileMonth="";
		String fileYear="";
		String fileTime="";
		int rowCount=0;
		int scriptErrorCount=0;
		int validBugCount=0;
		int applicationChangeCount=0;
		int safalBugCount=0;
		int fisIdChangeCount=0;
		int passedCount=0;
		int feedbackReqCount=0;
		double scriptErrorHours=0;
		double validBugHours=0;
		double applicationChangeHours=0;
		double safalBugHours=0;
		double fisIdChangeHours=0;
		double passedHours=0;
		double feedbackReqHours=0;
		Workbook workbook;
		Sheet analysisSheet;





		File dir = new File(directory); 					//Create a directory path of the directory the results are
		File[] directoryListing = dir.listFiles();  			// get a list of files from the directory and store it in the array
		if (directoryListing != null)


		{ 						//A loop that runs while theres is still a file available in the directory



			for (File child : directoryListing)
			{

				System.out.println(file);
				file =child.toString();
				file = file.substring(file.lastIndexOf('\\')+1, file.length());
				fileBrowser=(file).substring(file.lastIndexOf('\\')+1, file.indexOf('_'));
				fileTime =file.substring(file.lastIndexOf('_')+1,file.indexOf('.'));
			
				fileDate = file.substring(file.indexOf('_')+1,file.lastIndexOf('_'));
				 fileYear =fileDate.substring(fileDate.lastIndexOf('_')+1,fileDate.length());
				 fileMonth = fileDate.substring(fileDate.indexOf('_')+1, fileDate.indexOf('_')+4 );
				 fileDate = fileDate.substring(fileDate.lastIndexOf('_')-2, fileDate.lastIndexOf('_'));
			
			

					System.out.println(file);
					System.out.println(fileBrowser);
					System.out.println(fileDate);
					System.out.println(fileMonth);
					System.out.println(fileYear);




				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat sdf2 = new SimpleDateFormat("MMM/dd/yyyy");
				Calendar cal = Calendar.getInstance();


				Date startDate1 = sdf.parse(startDate);
				Date endDate1 = sdf.parse(endDate);
				Date fileDate1 = sdf2.parse(fileMonth+"/" + fileDate+"/"+fileYear);



				if((fileDate1.after(startDate1) && (fileDate1.before(endDate1))) || fileDate1.equals(startDate1) || fileDate1.equals(endDate1))
				{




					workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(child)));   // For xlsx
					analysisSheet = workbook.getSheetAt(1);
					rowCount=analysisSheet.getPhysicalNumberOfRows()-1;

					for (int a =0; a<rowCount; a++)
					{
						if(analysisSheet.getRow(a)!=null)
						{

							if(analysisSheet.getRow(a).getCell(2)!=null)
							{
								if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("SCRIPT ERROR"))
								{
									scriptErrorCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										scriptErrorHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}


								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("VALID BUG"))
								{
									validBugCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										validBugHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("SAFAL BUG"))
								{
									safalBugCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										safalBugHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("APPLICATION CHANGE"))
								{
									applicationChangeCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										applicationChangeHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("SGID CHANGING"))
								{
									fisIdChangeCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										fisIdChangeHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("PASSED"))
								{
									passedCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										passedHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}
								else if(analysisSheet.getRow(a).getCell(2).toString().equalsIgnoreCase("FEEDBACK REQUIRED"))
								{
									feedbackReqCount++;
									if(analysisSheet.getRow(a).getCell(9)!=null)
									{
										feedbackReqHours += Double.parseDouble(analysisSheet.getRow(a).getCell(9).toString());
									}
								}

							}
						}
					}



				}
				else
				{
					System.out.println(file +"Date is not between ");
				}

			}





		}

		System.out.println(" There are a total of "+scriptErrorCount+" script errors in the range. The total time spent on scripterrors is : " +scriptErrorHours+ " Hours");
		System.out.println(" There are a total of "+validBugCount+" valid bugs in the range. The total time spent on validbugs is : " +validBugHours+ " Hours");
		System.out.println(" There are a total of "+fisIdChangeCount+" fisIDchanges in the range. The total time spent on fisIDchanges is : " +fisIdChangeHours+ " Hours");
		System.out.println(" There are a total of "+applicationChangeCount+" applicationChanges in the range. The total time spent on applicationChanges is : " +applicationChangeHours+ " Hours");
		System.out.println(" There are a total of "+safalBugCount+"  SafalBugs in the range. The total time spent on SafalBugs is : " +safalBugHours+ " Hours");
		System.out.println(" There are a total of "+passedCount+" passedSteps in the range. The total time spent on passedSteps is : " +passedHours+ " Hours");
		System.out.println(" There are a total of "+feedbackReqCount+" feedbackRequired in the range. The total time spent on feedbackRequired is : " +feedbackReqHours+ " Hours");

	}
}
