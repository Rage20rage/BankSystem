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

public class home implements View, ActionListener {

    private HashMap<String, UiContainer> ui = new HashMap<>();
    BiConsumer<String, HashMap<String, String>> callBack;

    public home(BiConsumer<String, HashMap<String, String>> callBack) {
        this.callBack = callBack;
        //ui.put("titel",new UiContainer(new JTextPane(Test1.t));
        ui.put("Balence-label", new UiContainer(new JLabel("Kontostandt:"), 0, 1));

        JTextArea textArea1 =new JTextArea("1265.56");
        textArea1.setEditable(false);
        ui.put("texterea-", new UiContainer(textArea1, 1, 1));

        ui.put("username-label", new UiContainer(new JLabel("Nutzername:"), 0, 2));
        JTextArea textArea2 =new JTextArea("Dein Name");
        textArea2.setEditable(false);
        ui.put("username", new UiContainer(textArea2, 1, 2));


        JButton history = new JButton("Verlauf");
        history.addActionListener(this);
        ui.put("history-button", new UiContainer(history, 1, 3));

        JButton depositMoney = new JButton("Geld ein/auszahlen");
        depositMoney.addActionListener(this);
        ui.put("depositMoney-button", new UiContainer(depositMoney, 1, 4));

        JButton transfer = new JButton("Überweisen");
        transfer.addActionListener(this);
        ui.put("transfer-button", new UiContainer(transfer, 2, 3));

        JButton logout = new JButton("Auslogen");
        logout.addActionListener(this);
        ui.put("payOutMoney-button", new UiContainer(logout, 2, 4));


    }

    /*private void handeltransfer(ActionEvent actionEvent) {
        System.out.println("Das ist der Handle Transfer!");

    }

    private void handelpayOutMoneyDeposit(ActionEvent actionEvent) {


    }
    private void handelhistory(ActionEvent actionEvent) {


    }*/

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

        if (ae.getSource() == this.ui.get("depositMoney-button").getComponent()) {
            Window.getInstance().applyView(new moneychangers(callBack));
        }
        if (ae.getSource() == this.ui.get("transfer-button").getComponent()) {
            Window.getInstance().applyView(new moneytransfer(callBack));
        }
        if(ae.getSource()==this.ui.get("logout").getComponent()){
            Network.logout();
            Window.getInstance().applyView(new Login(callBack));
        }

    }

}