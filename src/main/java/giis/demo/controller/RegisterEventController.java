package giis.demo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import giis.demo.model.ConsultEventDTO;
import giis.demo.model.ConsultEventStatusModel;
import giis.demo.model.ExistingEventDTO;
import giis.demo.model.IncomeEntryDTO;
import giis.demo.model.PaymentDTO;
import giis.demo.model.RegisterEventDTO;
import giis.demo.model.RegisterEventModel;
import giis.demo.model.SponsorshipInfoDTO;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.view.ConsultEventStatusView;
import giis.demo.view.RegisterEventView;
import giis.demo.util.SwingMain;

//En el Controller (RegisterEventController.java):
public class RegisterEventController {
 private RegisterEventModel model;
 private RegisterEventView view;

 private List<ConsultEventDTO> eventList;
 private List<SponsorshipInfoDTO> sponsorList;
 private List<PaymentDTO> paymentList;
 private List<IncomeEntryDTO> balanceList;
 private List<ExistingEventDTO> existingEvents;
 private SwingMain loquesea;

 public RegisterEventController(RegisterEventModel model, RegisterEventView view) {
 	this.eventList = new ArrayList<ConsultEventDTO>();
 	this.sponsorList = new ArrayList<SponsorshipInfoDTO>();
 	this.paymentList = new ArrayList<PaymentDTO>();
 	this.balanceList = new ArrayList<IncomeEntryDTO>();
 	this.existingEvents = new ArrayList<ExistingEventDTO>();
    this.view = view;
 	this.model = model;
    this.initView();
 }

 /**
  * Inicializa la vista y carga los eventos en el comboBox.
  */
 public void initView() { 	
     view.getFrmRegisterEvents().setVisible(true);
 }

 /**
  * Inicializa los manejadores de eventos.
  */
 public void initController(SwingMain sm) {
 	loquesea = sm;

   // Agregar un ActionListener al botón Exit
    view.getBtnExit().addActionListener(e -> SwingUtil.exceptionWrapper(() -> view.getFrmRegisterEvents().dispose()));
    // Cerrar la ventana actual
    view.getRegisterEvent().addActionListener(e -> SwingUtil.exceptionWrapper(() -> onRegisterButtonClick()));
 
  

 }

 
 private RegisterEventDTO getLastRegisteredEvent() {
	    // Assuming the model has a method or that we store the last event somewhere
	    // It can be stored after registering the event in onRegisterButtonClick
	    return model.getLastRegisteredEvent();
	}
 
 public void onRegisterButtonClick() {
	    // Obtener datos de la vista
	    String eventName = view.gettFeventName().getText();
	    String eventEdition = view.gettFeventEdition().getText();
	    String startDate = view.gettFstartDate().getText();
	    String endDate = view.gettFendDate().getText();

	    // Comprobar si algún campo está vacío
	    if (eventName.isEmpty() || startDate.isEmpty() || endDate.isEmpty() ) {
	        JOptionPane.showMessageDialog(view.getFrmRegisterEvents(), "All fields must be filled.", "Missing information", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Validar formato de fecha ISO (YYYY-MM-DD)
	    if (!Util.isValidISODate(startDate) || !Util.isValidISODate(endDate)) {
	        SwingUtil.showMessage("Wrong date format, please use ISO YYYY-MM-DD.", "Wrong date format.", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Validar que la fecha de inicio no sea posterior a la de fin
	    if (startDate.compareTo(endDate) > 0) {
	        SwingUtil.showMessage("The event can't end before it starts.", "Wrong date usage.", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	 // Obtener la fecha del sistema desde SwingMain
	    String systemDates = loquesea.getFechaISO(); // Se asume que devuelve un Date

	    // NUEVA VALIDACIÓN: Si la fecha de inicio es anterior a la fecha del sistema
	    if ((startDate.compareTo(systemDates) < 0)) {
	        int response = JOptionPane.showConfirmDialog(
	            view.getFrmRegisterEvents(),
	            "Dates of the new event are from the past. Do you want to register the event anyway?",
	            "Confirm Registration",
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.WARNING_MESSAGE
	        );

	        if (response != JOptionPane.YES_OPTION) {
	            return; // Cancelar el registro si el usuario elige "No"
	        }
	    }
	    
	    if ((startDate.compareTo(systemDates) == 0)) {
	        int response = JOptionPane.showConfirmDialog(
	            view.getFrmRegisterEvents(),
	            "Dates of the new event are for today. Do you want to register the event anyway?",
	            "Confirm Registration",
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.WARNING_MESSAGE
	        );

	        if (response != JOptionPane.YES_OPTION) {
	            return; // Cancelar el registro si el usuario elige "No"
	        }
	    }

	    

	    // 🔹 Comprobación de existencia del evento con la misma edición
	    List<ExistingEventDTO> existingEvents = model.getExistingEventsByName(eventName);
	    
	    for (ExistingEventDTO event : existingEvents) {
	        if (event.getEvent_edition().equals(eventEdition)) { 
	            SwingUtil.showMessage("An event with the same name and edition already exists.", "Duplicate Event", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }
	    
	    // Obtener la fecha del sistema desde SwingMain
	    Date systemDate = loquesea.getFecha();

	    // Convertir las fechas del evento a Date
	    Date startDateObj = Util.isoStringToDate(startDate);
	    Date endDateObj = Util.isoStringToDate(endDate);

	    // Determinar el estado del evento
	    String eventStatus;
	    if (systemDate.before(startDateObj)) {
	        eventStatus = "Planned";
	    } else if (!systemDate.after(endDateObj)) {
	        eventStatus = "Ongoing";
	    } else {
	        eventStatus = "Completed";
	    }

	    // Registrar el evento con el estado calculado
	    RegisterEventDTO newEvent = new RegisterEventDTO();
	    newEvent.setEvent_name(eventName);
	    newEvent.setEvent_edition(eventEdition);
	    newEvent.setEvent_date(startDate);
	    newEvent.setEvent_endDate(endDate);
	    newEvent.setEvent_status(eventStatus); // Estado basado en la fecha del sistema

	    model.registerEvent(newEvent);
	    
	    

	    
	    SwingUtil.showMessage("Event successfully registered with status: " + eventStatus, "Success", JOptionPane.INFORMATION_MESSAGE);
	    view.clearFields();
	}
 


 public void onSearchEventName(String name) {
     // Buscar eventos existentes
     existingEvents = model.getExistingEventsByName(name);
 }
 
 private void abrirFormularioNivelesDePatrocinio(int eventId) {
	    boolean continuar = true;
	    while (continuar) {
	        JTextField nombreField = new JTextField();
	        JTextField precioField = new JTextField();

	        Object[] message = {
	            "Nombre del nivel:", nombreField,
	            "Precio:", precioField
	        };

	        int option = JOptionPane.showConfirmDialog(null, message, "Nuevo nivel de patrocinio", JOptionPane.OK_CANCEL_OPTION);
	        if (option == JOptionPane.OK_OPTION) {
	            String nombre = nombreField.getText();
	            double precio;
	            try {
	                precio = Double.parseDouble(precioField.getText());
	                model.addSponsorshipLevel(nombre, precio, eventId);
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Precio no válido", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            continuar = false;
	        }
	    }
	}

}

