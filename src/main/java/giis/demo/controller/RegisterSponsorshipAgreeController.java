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
    	this.loquesea=s;
        // Manejador para el botón "Registrar"
        view.getBtnRegistrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> registerSponsorship()));
        
        view.getListaCompany().addActionListener(e -> SwingUtil.exceptionWrapper(() -> loadCompanyMembers()));

        // Manejador para el botón "Cancelar"
        view.getBtnCancelar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearForm()));
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
            comboModel.addElement(event.getEvent_name()); // Agregar EventDTO como Object
        }
        view.getListaEvent().setModel(comboModel); // Asignar el modelo al JComboBox<Object>
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
    }
    
    /**
     * Registra un nuevo acuerdo de patrocinio.
     */
    private void registerSponsorship() {
        // Obtener los valores seleccionados
        int selectedCompanyIndex = view.getListaCompany().getSelectedIndex();
        if (selectedCompanyIndex < 0) { SwingUtil.showMessage("You need to select a company first.", "Select a Company", JOptionPane.ERROR_MESSAGE); return;}
        int selectedEventIndex = view.getListaEvent().getSelectedIndex();
        if (selectedEventIndex < 0) { SwingUtil.showMessage("You need to select an event first.", "Select an Event", JOptionPane.ERROR_MESSAGE); return;}
    	int selectedGBIndex = view.getListaMiembrosGB().getSelectedIndex();
        if (selectedGBIndex < 0) { SwingUtil.showMessage("You need to select a Governing Board member first.", "Select a GB", JOptionPane.ERROR_MESSAGE); return;}
        

        CompanyDTO selectedCompany = this.listaGlobal.get(selectedCompanyIndex);
        EventDTO selectedEvent = this.listaEvent.get(selectedEventIndex);
        GBMemberDTO selectedMember = this.listaGB.get(selectedGBIndex);
        String agreementDate = view.getAgreementDate();
        if(view.getListaContacts().getSelectedRow() < 0) { SwingUtil.showMessage("Missing company member selection, please select a contact member from the company.", "Select company contact", JOptionPane.ERROR_MESSAGE); return;}
        
        if (!Util.isValidISODate(agreementDate)) { SwingUtil.showMessage("Incorrect date format, please use ISO format: YYYY-MM-DD.", "Use ISO for date", JOptionPane.ERROR_MESSAGE); return;}
        else {
        	// Validar que todos los campos estén completos
            if (selectedCompany == null || selectedEvent == null || selectedMember == null || agreementDate.isEmpty()) {
                throw new ApplicationException("Todos los campos son obligatorios.");
            }
            
            // Agreement <= System
            Date isoagreementDate = Util.isoStringToDate(agreementDate);
            String systemDate = "2025-02-02";
            if(loquesea.getFecha() != null) {
            	systemDate = Util.dateToIsoString(loquesea.getFecha());
            }
            Date systemDate1 = Util.isoStringToDate(systemDate);
            if(systemDate1.compareTo(isoagreementDate) >= 0) {
            	// Agreement < CelebrationEventDate
                if(isoagreementDate.compareTo(Util.isoStringToDate(selectedEvent.getEvent_date())) < 0) {
                	// Registrar el acuerdo de patrocinio
                    
                    int eventId = model.registerSponsorship(
                        selectedCompany.getCompany_name(),
                        selectedEvent.getEvent_name(),
                        agreementDate,
                        selectedMember.getGb_name()
                    );

                    // Registrar el balance asociado al evento
                    model.registerBalance(eventId, "Sponsorship", selectedEvent.getEvent_fee());

                    // Mostrar mensaje de éxito
                    SwingUtil.showMessage("Acuerdo de patrocinio registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar el formulario después del registro
                    clearForm();
                } else {
                	SwingUtil.showMessage("Incorrect date, the event is being celebrated or finished.", "Use correct date", JOptionPane.ERROR_MESSAGE); 
                	return;
                }
            } else {
            	SwingUtil.showMessage("Incorrect date, please register a date either from the past or today.", "Use correct date", JOptionPane.ERROR_MESSAGE); 
            	return;
            }
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
            comboModel.addElement(member.getGb_name()); // Agregar GBMemberDTO como Object
        }
        view.getListaMiembrosGB().setModel(comboModel); // Asignar el modelo al JComboBox<Object>
    }

    
    /**
     * Limpia el formulario.
     */
    private void clearForm() {
        view.getListaCompany().setSelectedIndex(-1); // Deseleccionar empresa
        view.getListaEvent().setSelectedIndex(-1);   // Deseleccionar evento
        view.getListaMiembrosGB().setSelectedIndex(-1); // Deseleccionar miembro del consejo
        view.setAgreementDate(""); // Limpiar la fecha
        view.setComboBoxIndexes(-1);
        view.getListaContacts().setModel(new DefaultTableModel(new String[] {},0));
    }
}