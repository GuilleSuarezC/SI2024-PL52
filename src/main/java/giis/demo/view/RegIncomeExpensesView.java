package giis.demo.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class RegIncomeExpensesView {

	private JFrame frame;
    private JTextField sourceField;  
    private JTextField descriptionField;
    private JComboBox<Object> eventComboBox;  
    private JTextField amountField;  
    private JTextField datePaidField;
    private JButton submitButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegIncomeExpensesView window = new RegIncomeExpensesView();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public RegIncomeExpensesView() {
        frame = new JFrame("Register Income/Expense");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sourceField = new JTextField(20);
        eventComboBox = new JComboBox<>();
        amountField = new JTextField(20);
        descriptionField = new JTextField(20);
        datePaidField = new JTextField(20);
        submitButton = new JButton("Register");
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel label = new JLabel("Source:");
        frame.getContentPane().add(label);
        frame.getContentPane().add(sourceField);
        
        JLabel label_1 = new JLabel("Event:");
        frame.getContentPane().add(label_1); 
        frame.getContentPane().add(eventComboBox);
        
        JLabel label_2 = new JLabel("Amount:");
        frame.getContentPane().add(label_2);    
        frame.getContentPane().add(amountField);
        
        JLabel label_3 = new JLabel("Description:");
        frame.getContentPane().add(label_3);
        frame.getContentPane().add(descriptionField);
        
        JLabel label_4 = new JLabel("Date of Paid:");
        frame.getContentPane().add(label_4);
        frame.getContentPane().add(datePaidField);
        
        frame.getContentPane().add(submitButton);

        frame.setSize(500, 300);  
        frame.setVisible(true);
    }

    
    public String getSource() { return sourceField.getText(); } 
    public String getDescription() { return descriptionField.getText(); }
    public String getDateOfPaid() { return datePaidField.getText(); }
    public Object getSelectedEvent() { return eventComboBox.getSelectedItem(); }
    public double getAmount() { return Double.parseDouble(amountField.getText()); }
    public JButton getSubmitButton() { return submitButton; }
    
    public void setSubmitAction(ActionListener actionListener) {
        submitButton.addActionListener(actionListener);
    }

    
    public void setEventOptions(Object[] events) {
        for (Object event : events) {
            eventComboBox.addItem(event);
        }
    }
}
