package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.function.BiConsumer;


    public class moneychangers implements View , ActionListener {
        private HashMap<String, UiContainer> ui = new HashMap<>();
        BiConsumer<String, HashMap<String, String>> callBack;

        public moneychangers(BiConsumer<String, HashMap<String, String>> callBack) {
            System.out.println("Der Knopf geht!");
            this.callBack = callBack;
            ui.put("id-label", new UiContainer(new JLabel("Kontostandt:"), 0, 1));
            ui.put("Kontostand" , new UiContainer(new JTextArea(),1, 1));
            JRadioButton radioButton1= new JRadioButton("Geld einzahlen:");
            ui.put("checkbox-in", new UiContainer(radioButton1, 0, 2));
            radioButton1.addActionListener(this::actionPerformed);

            JRadioButton radioButton2= new JRadioButton("Geld abheben:");
            ui.put("checkbox-out", new UiContainer(radioButton2, 0, 3));
            radioButton2.addActionListener(this::actionPerformed);

            ui.put("betrag1" , new UiContainer(new JTextField(),1,2));
            ui.put("betrag2" , new UiContainer(new JTextField(),1, 3));

            JButton payOutMoney = new JButton("Ausführen");
            payOutMoney.addActionListener(this::handelpayOutMoneyDeposit);
            ui.put("toRun-button", new UiContainer(payOutMoney, 1, 4));


            JButton homescren = new JButton("Zurück in Hauptmenü");
            homescren.addActionListener(this);
            ui.put("homescren-button", new UiContainer(homescren, 2, 4));
        }

        private void handelpayOutMoneyDeposit(ActionEvent actionEvent) {

//wenn marko das gemacht hääte wäre er down
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
            if(ae.getSource()== this.ui.get("checkbox-in").getComponent()){
                JRadioButton radioButtonin = (JRadioButton) ae.getSource();
                JRadioButton radioButtonout = (JRadioButton) this.ui.get("checkbox-out").getComponent();
                if (radioButtonout.isSelected()){
                    radioButtonout.setSelected(false);
                }

            }if (ae.getSource()== this.ui.get("checkbox-out").getComponent()){
                JRadioButton radioButtonout = (JRadioButton) ae.getSource();
                JRadioButton radioButtonin = (JRadioButton) this.ui.get("checkbox-in").getComponent();
                if (radioButtonin.isSelected()){
                    radioButtonin.setSelected(false);
                }
            }
            if (ae.getSource() == this.ui.get("homescren-button").getComponent()) {
                Window.getInstance().applyView(new home(callBack));
            }
        }
    }