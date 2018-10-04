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


    public class Moneychangers implements View , ActionListener {
        private HashMap<String, UiContainer> ui = new HashMap<>();
        BiConsumer<String, HashMap<String, String>> callBack;

        public Moneychangers(BiConsumer<String, HashMap<String, String>> callBack) {
            System.out.println("Der Knopf geht!");
            this.callBack = callBack;

            ui.put("id-label", new UiContainer(new JLabel("Kontostandt:"), 0, 1));
            JTextArea textArea2 =new JTextArea(Network.getBalance());
            textArea2.setEditable(false);
            ui.put("texterea-balance", new UiContainer(textArea2, 1, 1));


            JRadioButton radioButton1= new JRadioButton("Geld einzahlen:");
            ui.put("checkbox-in", new UiContainer(radioButton1, 0, 2));
            radioButton1.addActionListener(this);

            JRadioButton radioButton2= new JRadioButton("Geld abheben:");
            ui.put("checkbox-out", new UiContainer(radioButton2, 0, 3));
            radioButton2.addActionListener(this);

            ui.put("betrag1" , new UiContainer(new JTextField(),1,2));
            ui.put("betrag2" , new UiContainer(new JTextField(),1, 3));

            JButton payOutMoney = new JButton("Ausführen");
            payOutMoney.addActionListener(this);
            ui.put("toRun-button", new UiContainer(payOutMoney, 1, 4));

            JButton homescren = new JButton("Zurück in Hauptmenü");
            homescren.addActionListener(this);
            ui.put("homescren-button", new UiContainer(homescren, 2, 4));
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
            JTextField textField1 = (JTextField) this.ui.get("betrag1").getComponent();
            JTextField textField2 = (JTextField) this.ui.get("betrag2").getComponent();
            textField1.setVisible(false);
            textField2.setVisible(false);
            if(ae.getSource()== this.ui.get("checkbox-in").getComponent()){
                JRadioButton radioButtonin = (JRadioButton) ae.getSource();
                JRadioButton radioButtonout = (JRadioButton) this.ui.get("checkbox-out").getComponent();
                textField1.setVisible(true);



                if (radioButtonout.isSelected()){
                    radioButtonout.setSelected(false);


                }

            }if (ae.getSource()== this.ui.get("checkbox-out").getComponent()){
                JRadioButton radioButtonout = (JRadioButton) ae.getSource();
                JRadioButton radioButtonin = (JRadioButton) this.ui.get("checkbox-in").getComponent();
                textField2.setVisible(true);

                if (radioButtonin.isSelected()){
                    radioButtonin.setSelected(false);


                }
            }
            if (ae.getSource() == this.ui.get("homescren-button").getComponent()) {
                Window.getInstance().applyView(new Home(callBack));
            }
            if (ae.getSource() == this.ui.get("toRun-button").getComponent()){

                if(ae.getSource()== this.ui.get("checkbox-in").getComponent())
                Network.addMoney(textField1.getText());
                else{
                Network.getMoney(textField2.getText());
            }

            }
        }
    }