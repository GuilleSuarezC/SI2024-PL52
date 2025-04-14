package giis.demo.controller;

import giis.demo.model.*;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.view.LTAView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LTAController {
    private LTAModel model;
    private LTAView view;
    private SwingMain loquesea;

    public LTAController(LTAModel model, LTAView view) {
        this.model = model;
        this.view = view;
        this.initView();
    }

    public void initView() {
        view.getFrmCloseEvent().setVisible(true);
    }

    public void initController(SwingMain swingMain) {
        // Cargar eventos al iniciar
        loquesea = swingMain;
    }

}