package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Vista de la pantalla para consultar el estado de las actividades.
 */
public class CloseEventView {

    private JFrame frmCloseEvent;
    private JTable tblEvents;
    private JButton btnCloseEvent;

    /**
     * Create the application.
     */
    public CloseEventView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmCloseEvent = new JFrame();
        frmCloseEvent.setTitle("Close Events");
        frmCloseEvent.setName("CloseEventView");
        frmCloseEvent.setBounds(100, 100, 858, 327);
        frmCloseEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmCloseEvent.getContentPane().setLayout(null);

        // Activities Table
        JLabel lblEvents = new JLabel("List of Events:");
        lblEvents.setBounds(7, 7, 457, 14);
        frmCloseEvent.getContentPane().add(lblEvents);
        tblEvents = new JTable();
        JScrollPane spEvents = new JScrollPane(tblEvents);
        spEvents.setBounds(7, 28, 825, 218);
        frmCloseEvent.getContentPane().add(spEvents);
                
                        // Exit Button
                        btnCloseEvent = new JButton("Close Selected Event");
                        btnCloseEvent.setBounds(348, 257, 181, 23);
                        frmCloseEvent.getContentPane().add(btnCloseEvent);
                        
                        frmCloseEvent.setResizable(false); 


    }

	public JFrame getFrmCloseEvent() {
		return frmCloseEvent;
	}

	public void setFrmCloseEvent(JFrame frmCloseEvent) {
		this.frmCloseEvent = frmCloseEvent;
	}

	public JTable getTblEvents() {
		return tblEvents;
	}

	public void setTblEvents(JTable tblEvents) {
		this.tblEvents = tblEvents;
	}

	public JButton getBtnCloseEvent() {
		return btnCloseEvent;
	}

	public void setBtnCloseEvent(JButton btnCloseEvent) {
		this.btnCloseEvent = btnCloseEvent;
	}

}
