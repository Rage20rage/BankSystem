package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class home implements View, ActionListener {

    private HashMap<String, UiContainer> ui = new HashMap<>();
    BiConsumer<String, HashMap<String, String>> callBack;

    public home(BiConsumer<String, HashMap<String, String>> callBack) {
        this.callBack = callBack;

        ui.put("id-label", new UiContainer(new JLabel("Kontostandt:"), 0, 1));
        ui.put("id", new UiContainer(new JTextArea(), 1, 1));

        ui.put("username-label", new UiContainer(new JLabel("Nutzername:"), 0, 2));
        ui.put("Kontoauszug-", new UiContainer(new JTextArea(), 1, 2));


        JButton history = new JButton("Verlauf");
        history.addActionListener(this::handelhistory);
        ui.put("history-button", new UiContainer(history, 1, 3));

        JButton depositMoney = new JButton("Geld ein/auszahlen");
        depositMoney.addActionListener(this::handelpayOutMoneyDeposit);
        ui.put("depositMoney-button", new UiContainer(depositMoney, 1, 4));

        JButton transfer = new JButton("Überweisen");
        transfer.addActionListener(this);
        ui.put("transfer-button", new UiContainer(transfer, 2, 3));

        JButton payOutMoney = new JButton("Auslogen");
        payOutMoney.addActionListener(this::handelpayOutMoneyDeposit);
        ui.put("payOutMoney-button", new UiContainer(payOutMoney, 2, 4));


    }

    /*private void handeltransfer(ActionEvent actionEvent) {
        System.out.println("Das ist der Handle Transfer!");

    }*/

    private void handelpayOutMoneyDeposit(ActionEvent actionEvent) {


    }
    private void handelhistory(ActionEvent actionEvent) {


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

        if (ae.getSource() == this.ui.get("transfer-button").getComponent()) {
            Window.getInstance().applyView(new moneychangers(callBack));
        }

    }

}