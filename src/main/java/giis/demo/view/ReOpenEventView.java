package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReOpenEventView {

    private JFrame frmReopenEvent;
    private JTable tblClosedEvents;
    private JButton btnReopenEvent;

    public ReOpenEventView() {
        initialize();
    }

    private void initialize() {
        frmReopenEvent = new JFrame();
        frmReopenEvent.setTitle("Reopen Closed Events");
        frmReopenEvent.setName("ReopenEventView");
        frmReopenEvent.setBounds(100, 100, 858, 327);
        frmReopenEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmReopenEvent.getContentPane().setLayout(null);

        JLabel lblEvents = new JLabel("List of Closed Events:");
        lblEvents.setBounds(7, 7, 457, 14);
        frmReopenEvent.getContentPane().add(lblEvents);
        
        tblClosedEvents = new JTable();
        JScrollPane spEvents = new JScrollPane(tblClosedEvents);
        spEvents.setBounds(7, 28, 825, 218);
        frmReopenEvent.getContentPane().add(spEvents);

        btnReopenEvent = new JButton("Reopen Selected Event");
        btnReopenEvent.setBounds(348, 257, 181, 23);
        frmReopenEvent.getContentPane().add(btnReopenEvent);

        frmReopenEvent.setResizable(false);
    }

    public JFrame getFrmReopenEvent() {
        return frmReopenEvent;
    }

    public JTable getTblClosedEvents() {
        return tblClosedEvents;
    }

    public JButton getBtnReopenEvent() {
        return btnReopenEvent;
    }
}
