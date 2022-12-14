package org.source;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.source.service.LoaderSettings;
import org.source.views.ControlPanel;

public class Main {

    private static final String CONFIG = "res/config.conf";

    public static void main(String[] args) {

        ApplicationUI.getInstance().push(new ControlPanel());
        ApplicationUI.getInstance().validate();
        try {
            LoaderSettings.getInstance().initConfigurations(CONFIG);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(ApplicationUI.getInstance(), e.getMessage(),
                    "Ошибка загрузки настроек", JOptionPane.ERROR_MESSAGE);
        }
    }
}
