package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.BiConsumer;


    public class Moneytransfer implements View , ActionListener {
        private HashMap<String, UiContainer> ui = new HashMap<>();
        BiConsumer<String, HashMap<String, String>> callBack;

        public Moneytransfer(BiConsumer<String, HashMap<String, String>> callBack) {
            this.callBack = callBack;

            ui.put("account-label", new UiContainer(new JLabel("Kontostandt:"), 0, 1));
            JTextArea textArea2 =new JTextArea("1256,65");
            textArea2.setEditable(false);
            ui.put("texterea-balance", new UiContainer(textArea2, 1, 1));

            ui.put("amount-label", new UiContainer(new JLabel("Tragen sie hier den Betrag ein den sie Überwiesen möchten:"), 0, 2));
            ui.put("Überwieungsbetrag" , new UiContainer(new JTextField(),1,2));

            ui.put("anwen-label", new UiContainer(new JLabel("Tragen sie hier den begünstigten ein"), 0, 3));
            ui.put("Wemdasgelzusteht" , new UiContainer(new JTextField(),1,3));

            JButton payOutMoney = new JButton("Überwiesung Ausführen");
            payOutMoney.addActionListener(this::handelpayOutMoneyDeposit);
            ui.put("toRun-button", new UiContainer(payOutMoney, 1, 4));

            JButton homescren = new JButton("Zurück in Hauptmenü");
            homescren.addActionListener(this);
            ui.put("homescren-button", new UiContainer(homescren, 2, 4));
        }

        private void handelpayOutMoneyDeposit(ActionEvent actionEvent) {

        }



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
            if (ae.getSource() == this.ui.get("toRun-button").getComponent()){
                //dann geld über / einzahlen funktion
            }
        }
    }


