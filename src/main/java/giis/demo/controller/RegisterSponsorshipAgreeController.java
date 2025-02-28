package giis.demo.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableModel;

import giis.demo.model.RegisterSponsorshipAgreeModel;
import giis.demo.view.RegisterSponsorshipAgreeView;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;

/**
 * Controlador para la funcionalidad de registrar acuerdos de patrocinio.
 * - Se inicializa con el modelo y la vista.
 * - Instala los manejadores de eventos en initController().
 */
public class RegisterSponsorshipAgreeController {
    private RegisterSponsorshipAgreeModel model;
    private RegisterSponsorshipAgreeView view;
    private String lastSelectedKey = "";

    public RegisterSponsorshipAgreeController(RegisterSponsorshipAgreeModel m, RegisterSponsorshipAgreeView v) {
        this.model = m;
        this.view = v;
        this.initView();
    }

    /**
     * Inicializa el controlador agregando los manejadores de eventos.
     */
    public void initController() {
        view.getBtnRegistrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> registerSponsorship()));
        view.getTablaAcuerdos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SwingUtil.exceptionWrapper(() -> updateDetail());
            }
        });
    }

    /**
     * Inicializa la vista y carga los datos iniciales.
     */
    public void initView() {
        this.loadCompanies();
        this.loadEvents();
        this.loadGoverningBoardMembers();
        this.loadSponsorships();
        view.getFrame().setVisible(true);
    }

    /**
     * Carga la lista de empresas en el comboBox.
     */
    private void loadCompanies() {
        List<Object[]> companies = model.getCompanies();
        view.getListaCompany().setModel(SwingUtil.getComboModelFromList(companies));
    }

    /**
     * Carga la lista de eventos en el comboBox.
     */
    private void loadEvents() {
        List<Object[]> events = model.getEvents();
        view.getListaEvent().setModel(SwingUtil.getComboModelFromList(events));
    }

    /**
     * Carga la lista de miembros del consejo de administración.
     */
    private void loadGoverningBoardMembers() {
        List<Object[]> members = model.getGoverningBoardMembers();
        view.getListaMiembrosGB().setModel(SwingUtil.getComboModelFromList(members));
    }

    /**
     * Registra un nuevo acuerdo de patrocinio y actualiza el balance del evento.
     */
    private void registerSponsorship() {
        String company = view.getListaCompany().getSelectedItem().toString();
        String event = view.getListaEvent().getSelectedItem().toString();
        String date = view.getAgreementDate();
        String member = view.getListaMiembrosGB().getSelectedItem().toString();

        // Registra el sponsorship
        int eventId = model.registerSponsorship(company, event, date, member);

        // Inserta un nuevo registro en la tabla Balance relacionado al evento
        model.registerBalance(eventId, "Sponsorship", 0, 0, 0, 0);

        this.loadSponsorships(); // Actualiza la tabla después de registrar
    }

    /**
     * Carga la lista de acuerdos de patrocinio en la tabla.
     */
    private void loadSponsorships() {
        List<Object[]> sponsorships = model.getSponsorships();
        TableModel tmodel = SwingUtil.getTableModelFromList(sponsorships, new String[]{"ID", "Name", "Date"});
        view.getTablaAcuerdos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTablaAcuerdos());
    }

    /**
     * Muestra el detalle del acuerdo de patrocinio seleccionado y su balance.
     */
    private void updateDetail() {
        this.lastSelectedKey = SwingUtil.getSelectedKey(view.getTablaAcuerdos());
        if ("".equals(this.lastSelectedKey)) {
            view.setAgreementDate("");
            view.clearBalanceTable();
        } else {
            String[] details = model.getSponsorshipDetails(Integer.parseInt(lastSelectedKey));
            view.setAgreementDate(details[2]); // Asigna la fecha al campo de la vista
            this.loadBalance(Integer.parseInt(details[0])); // Carga balance asociado
        }
    }

    /**
     * Carga el balance de un evento en la tabla.
     */
    private void loadBalance(int eventId) {
        List<Object[]> balances = model.getEventBalance(eventId);
        TableModel tmodel = SwingUtil.getTableModelFromList(balances, new String[]{"ID", "Source", "Est. Income", "Paid Income", "Est. Expenses", "Paid Expenses"});
        view.getTablaBalance().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTablaBalance());
    }
}
