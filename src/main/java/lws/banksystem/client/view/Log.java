package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;
import lws.banksystem.client.network.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.BiConsumer;


    public class Log implements View , ActionListener {
        private HashMap<String, UiContainer> ui = new HashMap<>();
        BiConsumer<String, HashMap<String, String>> callBack;

        public Log(BiConsumer<String, HashMap<String, String>> callBack) {
            this.callBack = callBack;

            JTextArea textArea = new JTextArea();
            textArea.setText(Network.getLog());
            textArea.setEditable(false);
            ui.put("log", new UiContainer(textArea,1,4));

            JButton homescren = new JButton("Zurück in Hauptmenü");
            homescren.addActionListener(this);
            ui.put("homescren-button", new UiContainer(homescren, 2, 5));
        }

        @Override
        public JPanel getJPanel() {
            final JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = 1;
        c.insets = new Insets(10, 10, 10, 10);
            ui.values().forEach(uiContainer -> {
            c.gridx = uiContainer.getX();
            c.gridy = uiContainer.getY();
            jPanel.add(uiContainer.getComponent(), c);

        });
            return jPanel;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == this.ui.get("homescren-button").getComponent()) {
                Window.getInstance().applyView(new Home(callBack));
            }
        }
    }

