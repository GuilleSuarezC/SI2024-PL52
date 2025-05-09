package giis.demo.view;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import giis.demo.util.Util;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

/**
 * Vista de la pantalla para registrar acuerdos de patrocinio.
 * Se ha generado siguiendo el patrón MVC, eliminando manejadores de eventos
 * y asegurando que la UI se pueda manipular desde el controlador.
 */
public class EditEventView {

    private JFrame frmEditEvent;
    private JTable lstActivities;
    private JLabel lblFilters;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JLabel lblEndDate_1;
    private JButton btnApplyFilter;
    private JButton btnClearFields;
    private JButton btnEditEvent;
    private JTextField startDateChooser, endDateChooser;
    private JPanel SelectedPanel;
    private JLabel lblNewLabel;
    private JLabel lblName;
    private JLabel lblEdititon;
    private JLabel lblSDate;
    private JLabel lblEDate;
    private JLabel lblStatus;
    private JTextField NameField;
    private JTextField EditionField;
    private JTextField StartDateField;
    private JTextField EndDateField;
    private JComboBox<Object> StatusCB;
    private JButton btnRegisterNewSL;
    private JScrollPane sponsorshipLevelslPanel;
    private JTable lstSponsorshipLevel;
    private JLabel lblClosed;
    private JCheckBox chckbxClosed;

    /**
     * Create the application.
     */
    public EditEventView() {
        initialize();
    }
    

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmEditEvent = new JFrame();
        frmEditEvent.setTitle("Edit Events");
        frmEditEvent.setName("SponsorshipView");
        frmEditEvent.setBounds(0, 0, 785, 789);
        frmEditEvent.getContentPane().setLayout(new MigLayout("", "[744.00px,grow]", "[21px][88.00px][15px][235.00px][223.00px,grow]"));
        
        
        JLabel lblList = new JLabel("List of Events");
        lblList.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmEditEvent.getContentPane().add(lblList, "cell 0 2,grow");
        
        JScrollPane ListPanel = new JScrollPane();
        frmEditEvent.getContentPane().add(ListPanel, "cell 0 3,grow");
        
        lstActivities = new JTable();
        ListPanel.setViewportView(lstActivities);
        
        JPanel DetailsPanel = new JPanel();
        frmEditEvent.getContentPane().add(DetailsPanel, "cell 0 1,grow");
        DetailsPanel.setLayout(null);
        
        startDateChooser = new JTextField();
        startDateChooser.setBounds(74, 14, 131, 19);
        DetailsPanel.add(startDateChooser);
        startDateChooser.setColumns(10);          
        
        lblStartDate = new JLabel("Start Date: ");
        lblStartDate.setBounds(10, 10, 86, 23);
        DetailsPanel.add(lblStartDate);        
        
        endDateChooser = new JTextField();
        endDateChooser.setColumns(10);
        endDateChooser.setBounds(332, 14, 131, 19);
        DetailsPanel.add(endDateChooser);        
        
        btnApplyFilter = new JButton("Apply Filter");
        btnApplyFilter.setBounds(10, 55, 117, 21);
        DetailsPanel.add(btnApplyFilter);
        
        lblEndDate_1 = new JLabel("End Date: ");
        lblEndDate_1.setBounds(274, 10, 86, 23);
        DetailsPanel.add(lblEndDate_1);
        
        lblStatus = new JLabel("Status: ");
        lblStatus.setBounds(508, 12, 198, 22);
        DetailsPanel.add(lblStatus);
        
        String[] status = {"All","Planned", "Ongoing", "Completed"};
        StatusCB = new JComboBox<>(status);
        StatusCB.setBounds(564, 12, 158, 21);
        DetailsPanel.add(StatusCB);
        
        lblFilters = new JLabel("Filters");
        lblFilters.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmEditEvent.getContentPane().add(lblFilters, "cell 0 0,grow");
        
        SelectedPanel = new JPanel();
        frmEditEvent.getContentPane().add(SelectedPanel, "cell 0 4,grow");
        SelectedPanel.setLayout(null);
        
        lblNewLabel = new JLabel("Actual");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 0, 125, 22);
        SelectedPanel.add(lblNewLabel);
        
        lblName = new JLabel("Event Name: ");
        lblName.setBounds(10, 32, 267, 22);
        SelectedPanel.add(lblName);
        
        lblEdititon = new JLabel("Event edition: ");
        lblEdititon.setBounds(10, 66, 267, 22);
        SelectedPanel.add(lblEdititon);
        
        lblSDate = new JLabel("Start Date: ");
        lblSDate.setBounds(10, 98, 267, 22);
        SelectedPanel.add(lblSDate);
        
        lblEDate = new JLabel("End Date: ");
        lblEDate.setBounds(10, 130, 267, 22);
        SelectedPanel.add(lblEDate);
        
        btnClearFields = new JButton("Clear Fields");
        btnClearFields.setBounds(414, 304, 117, 21);
        SelectedPanel.add(btnClearFields);
        
        btnEditEvent = new JButton("Edit Event");
        btnEditEvent.setBounds(541, 304, 117, 21);
        SelectedPanel.add(btnEditEvent);
        
        NameField = new JTextField();
        NameField.setBounds(90, 34, 209, 19);
        SelectedPanel.add(NameField);
        NameField.setColumns(10);
        
        EditionField = new JTextField();
        EditionField.setColumns(10);
        EditionField.setBounds(90, 68, 209, 19);
        SelectedPanel.add(EditionField);
        
        StartDateField = new JTextField();
        StartDateField.setColumns(10);
        StartDateField.setBounds(90, 100, 209, 19);
        SelectedPanel.add(StartDateField);
        
        EndDateField = new JTextField();
        EndDateField.setColumns(10);
        EndDateField.setBounds(90, 132, 209, 19);
        SelectedPanel.add(EndDateField);
        
        JLabel lblSponsorLevel = new JLabel("Sponsorship Levels: ");
        lblSponsorLevel.setBounds(487, 10, 232, 22);
        SelectedPanel.add(lblSponsorLevel);
        
        btnRegisterNewSL = new JButton("Register New Sponsorship Level");
        btnRegisterNewSL.setBounds(414, 234, 244, 21);
        SelectedPanel.add(btnRegisterNewSL);
        
        sponsorshipLevelslPanel = new JScrollPane();
        sponsorshipLevelslPanel.setBounds(431, 42, 216, 169);
        SelectedPanel.add(sponsorshipLevelslPanel);
        
        lstSponsorshipLevel = new JTable();
        sponsorshipLevelslPanel.setViewportView(lstSponsorshipLevel);
        
        lblClosed = new JLabel("Is it Closed?: ");
        lblClosed.setBounds(10, 162, 141, 22);
        SelectedPanel.add(lblClosed);
        
        chckbxClosed = new JCheckBox("");
        chckbxClosed.setBounds(111, 163, 93, 21);
        SelectedPanel.add(chckbxClosed);
    }

    // Getters para acceso desde el controlador
    public JButton getBtnApplyFilter() { return this.btnApplyFilter; }
    public JButton getBtnClearFields() { return this.btnClearFields; }
    public JButton getBtnEditEvent() { return this.btnEditEvent; }
    public JButton getBtnRegisterNewSL() { return this.btnRegisterNewSL; }
    public JTable getLstActivities() {return this.lstActivities;}
    public JTable getLstSponsorshipLevels() {return this.lstSponsorshipLevel;}
    public JFrame getFrame() { return this.frmEditEvent; }
    public String getStartDate() {return this.startDateChooser.getText();}
    public String getEndDate() {return this.endDateChooser.getText();}
    public void setStartDate(String dateStr) {this.startDateChooser.setText(dateStr);}
    public void setEndDate(String dateStr) {this.endDateChooser.setText(dateStr);}     
    
    public void setName(String name) {this.NameField.setText(name);}
    public void setEdition(String edition) {this.EditionField.setText(edition);}
    public void setStartDateField(String date) {this.StartDateField.setText(date);}
    public void setEndDateField(String date) {this.EndDateField.setText(date);}
    public String getName() {return this.NameField.getText();}
    public String getEdition() {return this.EditionField.getText();}
    public String getStartDateField() {return this.StartDateField.getText();}
    public String getEndDateField() {return this.EndDateField.getText();}
    public JComboBox<Object> getStatus() { return this.StatusCB; }
    public void setStatus(String status) { this.StatusCB.setSelectedItem(status); }
	public JCheckBox getChckbxClosed() {return chckbxClosed;}
	public void setChckbxClosed(JCheckBox chckbxClosed) {this.chckbxClosed = chckbxClosed;}
    
    
}

