package giis.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import giis.demo.view.RegisterSponsorshipAgreeView;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;
import giis.demo.util.SwingMain;
import giis.demo.model.*;

public class RegisterSponsorshipAgreeController {
    private RegisterSponsorshipAgreeModel model;
    private RegisterSponsorshipAgreeView view;
    public List<CompanyDTO> listaGlobal;
    public List<EventDTO> listaEvent;
    public List<GBMemberDTO> listaGB;
    private SwingMain loquesea;
    private DefaultTableModel tableModel = new DefaultTableModel();


    public RegisterSponsorshipAgreeController(RegisterSponsorshipAgreeModel m, RegisterSponsorshipAgreeView v) {
    	this.listaGlobal =  new ArrayList<CompanyDTO>();
    	this.listaEvent = new ArrayList<EventDTO>();
    	this.listaGB = new ArrayList<GBMemberDTO>();
        this.model = m;
        this.view = v;        
        this.initView();
    }

    /**
     * Inicializa el controlador agregando los manejadores de eventos.
     */
    public void initController(SwingMain s) {
        this.loquesea = s;

        view.getBtnRegistrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> registerSponsorship()));

        view.getListaCompany().addActionListener(e -> SwingUtil.exceptionWrapper(() -> loadCompanyMembers()));

        view.getListaEvent().addActionListener(e -> {
            SwingUtil.exceptionWrapper(() -> {
                int selectedEventIndex = view.getListaEvent().getSelectedIndex();
                if (selectedEventIndex >= 0)
                    loadSponsorshipLevels(listaEvent.get(selectedEventIndex).getEvent_id());
            });
        });

        view.getBtnCancelar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearForm()));

        view.getBtnAddLevel().addActionListener(e -> SwingUtil.exceptionWrapper(() -> addSponsorshipLevel()));

        view.getListSponsorshipLevels().setModel(tableModel);
    }


    /**
     * Inicializa la vista y carga los datos iniciales.
     */
    public void initView() {
        this.loadCompanies();
        this.loadEvents();
        this.loadGoverningBoardMembers();
        view.setComboBoxIndexes(-1);
        view.getFrame().setVisible(true);
    }

   /* public void loadFee() {
    	int selectedEvent = view.getListaEvent().getSelectedIndex();
    	if(selectedEvent == -1) return;
    	//view.settFeventFee(""+listaEvent.get(selectedEvent).getEvent_fee());
    }*/
    /**
     * Carga la lista de empresas en el comboBox.
     */
    private void loadCompanies() {
        List<CompanyDTO> companies = model.getCompanies();
        DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
        for (CompanyDTO company : companies) {
        	
        	this.listaGlobal.add(company);
        	System.out.println("Company added to listaglobal: "+company.getCompany_name());
        	System.out.println("Company added to listaglobal: "+ this.listaGlobal);
        	
            comboModel.addElement(company.getCompany_name()); // Agregar CompanyDTO como Object
            System.out.println("Company: "+company.getCompany_name());
        }
        view.getListaCompany().setModel(comboModel); // Asignar el modelo al JComboBox<Object>
    }

    /**
     * Carga la lista de eventos en el comboBox.
     */
    private void loadEvents() {
        List<EventDTO> events = model.getEvents();
        DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
        for (EventDTO event : events) {
        	this.listaEvent.add(event);
            comboModel.addElement(event.getEvent_name()); 
        }
        view.getListaEvent().setModel(comboModel); 
    }
    
   
    
    
    private void loadCompanyMembers() {
        int selectedCompanyIndex = view.getListaCompany().getSelectedIndex();
        
        if (selectedCompanyIndex < 0) return;
        
        int companyId = this.listaGlobal.get(selectedCompanyIndex).getCompany_id();
        System.out.println("Selected Company ID: " + companyId);

        List<CMemberDTO> members = model.getCompanyMembers(companyId);

        // Create a table model from the list of members
        TableModel tableModel = SwingUtil.getTableModelFromPojos(members,new String[] {"member_id", "member_name", "member_email", "member_phone", "company_id"});

        // Set the model to the JTable
        view.getListaContacts().setModel(tableModel);
        SwingUtil.autoAdjustColumns(view.getListaContacts());
        
        // Ocultar las columnas ids
        view.getListaContacts().getColumnModel().getColumn(0).setMinWidth(0);
        view.getListaContacts().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getListaContacts().getColumnModel().getColumn(0).setWidth(0);
        
        view.getListaContacts().getColumnModel().getColumn(4).setMinWidth(0);
        view.getListaContacts().getColumnModel().getColumn(4).setMaxWidth(0);
        view.getListaContacts().getColumnModel().getColumn(4).setWidth(0);
    }
    
    /**
     * Registra un nuevo acuerdo de patrocinio.
     */
    private void registerSponsorship() {
        int selectedCompanyIndex = view.getListaCompany().getSelectedIndex();
        if (selectedCompanyIndex < 0) {
            SwingUtil.showMessage("You need to select a company first.", "Select a Company", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedEventIndex = view.getListaEvent().getSelectedIndex();
        if (selectedEventIndex < 0) {
            SwingUtil.showMessage("You need to select an event first.", "Select an Event", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedGBIndex = view.getListaMiembrosGB().getSelectedIndex();
        if (selectedGBIndex < 0) {
            SwingUtil.showMessage("You need to select a Governing Board member first.", "Select a GB", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedLevelRow = view.getListSponsorshipLevels().getSelectedRow();
        if (selectedLevelRow < 0) {
            SwingUtil.showMessage("You need to select a sponsorship level.", "Select Level", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (view.getListaContacts().getSelectedRow() < 0) {
            SwingUtil.showMessage("Missing company member selection, please select a contact member from the company.", "Select company contact", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String agreementDate = view.getAgreementDate();
        if (!Util.isValidISODate(agreementDate)) {
            SwingUtil.showMessage("Incorrect date format, please use ISO format: YYYY-MM-DD.", "Use ISO for date", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EventDTO selectedEvent = listaEvent.get(selectedEventIndex);
        CompanyDTO selectedCompany = listaGlobal.get(selectedCompanyIndex);
        GBMemberDTO selectedMember = listaGB.get(selectedGBIndex);

        Date isoAgreementDate = Util.isoStringToDate(agreementDate);
        String systemDateStr = loquesea.getFecha() != null ? Util.dateToIsoString(loquesea.getFecha()) : "2025-02-02";
        Date systemDate = Util.isoStringToDate(systemDateStr);

        if (systemDate.compareTo(isoAgreementDate) >= 0 &&
            isoAgreementDate.compareTo(Util.isoStringToDate(selectedEvent.getEvent_date())) < 0) {

            // Obtener el precio del Sponsorship Level seleccionado
            TableModel levelModel = view.getListSponsorshipLevels().getModel();
            String levelName = levelModel.getValueAt(selectedLevelRow, 0).toString();
            double levelPrice = Double.parseDouble(levelModel.getValueAt(selectedLevelRow, 1).toString());

            String sponsorshipName = selectedCompany.getCompany_name() + " " + selectedEvent.getEvent_name() + " " + selectedEvent.getEvent_edition();
            String sponsorshipLevelName = view.getListSponsorshipLevels().getValueAt(selectedLevelRow, 0).toString() + " Sponsorship";

            int balanceId = model.registerBalance(
            	    selectedEvent.getEvent_id(), 
            	    sponsorshipName,  // este es el concept
            	    levelPrice,       // este es el amount
            	    sponsorshipLevelName // este es la descripción
            	);

            model.registerSponsorship(
                    selectedCompany.getCompany_name(),
                    selectedEvent.getEvent_name(),
                    selectedEvent.getEvent_edition(),
                    agreementDate,
                    selectedMember.getGb_name(),
                    selectedMember.getGb_id(),
                    balanceId
            );
            


            SwingUtil.showMessage("Sponsorship agreement successfully registered.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            SwingUtil.showMessage("Incorrect date: must be today or earlier, and before the event celebration.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        }
    }


	/**
     * Carga la lista de miembros del consejo de administración.
     */
    private void loadGoverningBoardMembers() {
        List<GBMemberDTO> members = model.getGoverningBoardMembers();
        DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
        for (GBMemberDTO member : members) {
        	this.listaGB.add(member);
            comboModel.addElement(member.getGb_rank()); // Agregar GBMemberDTO como Object
        }
        view.getListaMiembrosGB().setModel(comboModel); // Asignar el modelo al JComboBox<Object>
    }

    
    /**
     * Limpia el formulario.
     */
    private void clearForm() {
        view.getListaCompany().setSelectedIndex(-1); 
        view.getListaEvent().setSelectedIndex(-1);  
        view.getListaMiembrosGB().setSelectedIndex(-1); 
        view.setAgreementDate(""); 
        view.setComboBoxIndexes(-1);
        view.getListaContacts().setModel(new DefaultTableModel(new String[] {},0));
        view.settFeventFee("");
        view.getListSponsorshipLevels().setModel(new DefaultTableModel(new String[] {},0));
        
    }
    
    private void loadSponsorshipLevels(int eventId) {
        List<SponsorshipLevelDTO> levels = model.getSponsorshipLevelsByEvent(eventId); 
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Level Name");
        tableModel.addColumn("Price");

        for (SponsorshipLevelDTO level : levels) {
            tableModel.addRow(new Object[]{level.getLevel_name(), level.getLevel_price()});
        }

        view.getListSponsorshipLevels().setModel(tableModel);
    }


    private void addSponsorshipLevel() {
        int selectedEventIndex = view.getListaEvent().getSelectedIndex();
        if (selectedEventIndex < 0) {
            SwingUtil.showMessage("Please select an event first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String levelName = JOptionPane.showInputDialog("Enter level name:");
        if (levelName == null || levelName.trim().isEmpty()) {
            SwingUtil.showMessage("Level name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String priceStr = JOptionPane.showInputDialog("Enter level price:");
        if (priceStr == null || priceStr.trim().isEmpty()) {
            SwingUtil.showMessage("Price cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);

            if (price < 0) {
                SwingUtil.showMessage("Price cannot be negative.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Math.round(price * 100.0) != price * 100.0) {
                SwingUtil.showMessage("Price must have at most two decimal places.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            SwingUtil.showMessage("Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int eventId = listaEvent.get(selectedEventIndex).getEvent_id();
        model.addSponsorshipLevel(eventId, levelName, price);

        loadSponsorshipLevels(eventId);
    }

}