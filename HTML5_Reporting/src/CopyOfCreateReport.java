

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

public class CopyOfCreateReport extends JPanel implements ItemListener {

    private HashMap<JCheckBox, ArrayList<Integer>> map = new HashMap<>();
    private JLabel _label;

    private static final int MAX_CHECKS = 30;

    public CopyOfCreateReport() {
        super(new BorderLayout());

        JCheckBox checkBox;
        Random r = new Random();

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        _label = new JLabel("You selected nothing");
        checkPanel.add(_label);

        for (int i = 0; i < MAX_CHECKS; i++) {
        //    StringBuilder sb = new StringBuilder();
          //  ArrayList<Integer> a = new ArrayList<>();
           // for (int j = 0; j < 3; j++) {
            //    Integer temp = (r.nextInt()) % 100;
              //  a.add(temp);
                //sb.append(temp).append(" ");
         //   }

            checkBox = new JCheckBox("1"+i);
            checkBox.setName("CheckBox" + i);
            checkBox.addItemListener(this);
          //  map.put(checkBox, a);
            checkPanel.add(checkBox);
        }

        add(checkPanel);

    }

    public void itemStateChanged(ItemEvent e) {

        JCheckBox source = (JCheckBox) e.getItemSelectable();

        if (e.getStateChange() == ItemEvent.SELECTED) {

            ArrayList<Integer> list = map.get(source);

            _label.setText("You've just selected " + list);

        }

    }

    private static void createAndShowGUI() {

        JFrame _frame = new JFrame("Check box loop");
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(800, 600);

        JComponent newContentPane = new CopyOfCreateReport();
        newContentPane.setOpaque(true);
        _frame.setContentPane(newContentPane);

        _frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}