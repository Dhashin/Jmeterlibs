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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


// This program was created to allow the user to select a number of scripts to create a regression script with
public class CreateRegressionScript extends JPanel implements ItemListener, ActionListener{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidFormatException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")

	
	

    private HashMap<JCheckBox, ArrayList<Integer>> map = new HashMap<>();
    private JLabel _label;
    JButton redButton;

    private static final int MAX_CHECKS = 30;

    public CreateRegressionScript() {
        super(new BorderLayout());

        JCheckBox checkBox;
        Random r = new Random();
      
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        
              
        _label = new JLabel("You selected nothing");
        checkPanel.add(_label);
		
		String testDir="";
		int dirLength=0;
		String currentFile="";
		
		
System.out.println("Please enter the path of the tests directory");
testDir = rose.next();


File dir = new File(testDir); 					//Create a directory path of the directory the results are
File[] directoryListing = dir.listFiles();  			// get a list of files from the directory and store it in the array
if (directoryListing != null) { 						//A loop that runs while theres is still a file available in the directory


	dirLength = directoryListing.length;	
	System.out.println(dirLength);
	for (int a=0;a <dirLength;a++)
	{
	
		  checkBox = new JCheckBox(directoryListing[a].toString());
          checkBox.setName("CheckBox" + a);
          checkBox.addItemListener(this);
          checkPanel.add(checkBox);
         
		System.out.println(directoryListing[a]);
	}
}
     

      //  add(checkPanel);

JScrollPane area = new JScrollPane(checkPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
area.setPreferredSize(new Dimension(100, 100));

// and add it to the GUI.
add(area);
setOpaque(true);
redButton= new JButton("Create Script");
//redButton.setLocation(0, 0);
redButton.setSize(30, 5);
redButton.addActionListener(this);
checkPanel.add(redButton);

        
    }

    public void itemStateChanged(ItemEvent e) {

        JCheckBox source = (JCheckBox) e.getItemSelectable();

        if (e.getStateChange() == ItemEvent.SELECTED) {

            ArrayList<Integer> list = map.get(source);

            _label.setText("You've just selected " + list);

        }

     
    }

    
    
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == redButton)
        {
        	 _label.setText("You've just clicked this button");
        }
     
       
    }

    
    
    
    
    
    
    
    
    
    private static void createAndShowGUI() {

        JFrame _frame = new JFrame("Create Regression Script");
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(800, 600);

        JComponent newContentPane = new CreateRegressionScript();
        newContentPane.setOpaque(true);
        _frame.setContentPane(newContentPane);

        _frame.setVisible(true);
        
    }

 
	
	
	public static Scanner rose = new Scanner(System.in);
public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException, ParseException {

	
	   javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    });
	
	

}
}
