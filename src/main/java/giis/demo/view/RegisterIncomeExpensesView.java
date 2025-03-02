package giis.demo.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegisterIncomeExpensesView {
	 private JFrame frame;
	    private JTextField conceptField;
	    private JTextField descriptionField;
	    private JTextField QEstimatedField;
	    private JTextField QPaidField;
	    private JTextField DatePaidField;
	    private JButton submitButton;

	    public RegisterIncomeExpensesView() {
	        frame = new JFrame("Register Income/Expense");
	        conceptField = new JTextField(20);
	        descriptionField = new JTextField(20);
	        QEstimatedField = new JTextField(20);
	        QPaidField = new JTextField(20);
	        DatePaidField = new JTextField(20);
	        submitButton = new JButton("Register");

	        frame.setLayout(new FlowLayout());
	        frame.add(new JLabel("Concept:"));
	        frame.add(conceptField);
	        frame.add(new JLabel("Description:"));
	        frame.add(descriptionField);
	        frame.add(new JLabel("Estimated Quantity:"));
	        frame.add(QEstimatedField);
	        frame.add(new JLabel("Paid Quantity:"));
	        frame.add(QPaidField);
	        frame.add(new JLabel("Date of Paid:"));
	        frame.add(DatePaidField);
	        frame.add(submitButton);

	        frame.setSize(400, 300);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	    }

	    // Getters para los componentes
	    public String getConcept() { return conceptField.getText(); }
	    public String getDescription() { return descriptionField.getText(); }
	    public double getQEstimated() { return Double.parseDouble(QEstimatedField.getText()); }
	    public double getQPaid() { return Double.parseDouble(QPaidField.getText()); }
	    public String getDateOfPaid() { return DatePaidField.getText(); }
	    public JButton getSubmitButton() { return submitButton; }
}
