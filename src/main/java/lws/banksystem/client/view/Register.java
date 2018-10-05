package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;
import lws.banksystem.client.network.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class Register implements View {
    private HashMap<String, UiContainer> ui = new HashMap<>();
    BiConsumer<String, HashMap<String, String>> callBack;

    public Register(BiConsumer<String, HashMap<String, String>> callBack) {
        this.callBack = callBack;

        ui.put("firstName-label", new UiContainer(new JLabel("Vorname:"), 0, 0));
        ui.put("firstName", new UiContainer(new JTextField(), 1, 0));

        ui.put("lastName-label", new UiContainer(new JLabel("Nachname:"), 0, 1));
        ui.put("lastName", new UiContainer(new JTextField(), 1, 1));


        ui.put("mail-label", new UiContainer(new JLabel("E-Mail:"), 0, 2));
        ui.put("mail", new UiContainer(new JTextField(), 1, 2));

        ui.put("birthdate-label", new UiContainer(new JLabel("Geburtstag:"), 0, 3));
        ui.put("birthdate", new UiContainer(new JTextField(), 1, 3));

        ui.put("password-label", new UiContainer(new JLabel("Passwort:"), 0, 4));
        ui.put("password", new UiContainer(new JPasswordField(), 1, 4));

        ui.put("city-label", new UiContainer(new JLabel("Ort:"), 2, 0));
        ui.put("city", new UiContainer(new JTextField(), 3, 0));

        ui.put("plz-label", new UiContainer(new JLabel("PLZ:"), 2, 1));
        ui.put("zipCode", new UiContainer(new JTextField(), 3, 1));

        ui.put("street-label", new UiContainer(new JLabel("Straße:"), 2, 2));
        ui.put("street", new UiContainer(new JTextField(), 3, 2));

        ui.put("housnumber-label", new UiContainer(new JLabel("Hausnummer:"), 2, 3));
        ui.put("housnumber", new UiContainer(new JTextField(), 3, 3));

        ui.put("passwordRepeat-label", new UiContainer(new JLabel("Passwort wiederholen:"), 2, 4));
        ui.put("passwordRepeat", new UiContainer(new JPasswordField(), 3, 4));


        JButton submit = new JButton("Registrieren");
        submit.addActionListener(this::handleRegistration);
        ui.put("submit-button", new UiContainer(submit, 3, 5));

        JButton backToLogin = new JButton("Zurück zum Login");
        backToLogin.addActionListener(this::actionPerformed);
        ui.put("login-button", new UiContainer(backToLogin, 1, 5));

    }

    private void handleRegistration(ActionEvent actionEvent) {
        if (!validateUserInput()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine valide Registrierung ein.");
            if (!((JTextField) ui.get("password").getComponent()).getText().equals(((JTextField) ui.get("passwordRepeat").getComponent()).getText())) {
                JOptionPane.showMessageDialog(null, "Passwort stimmt nicht überein");
                System.out.println();
            }
            return;
        } else { //------------------------------------------------------------------------------------------------------------------------------
            String firstName = ((JTextField) ui.get("firstName").getComponent()).getText(); //Bitte Variable definieren aus dem passendem TextFeld
            String lastName = ((JTextField) ui.get("lastName").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String mail = ((JTextField) ui.get("mail").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String birthdate = ((JTextField) ui.get("birthdate").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String street = ((JTextField) ui.get("street").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String houseNumber = ((JTextField) ui.get("housnumber").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String city = ((JTextField) ui.get("city").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String zipCode = ((JTextField) ui.get("zipCode").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            String password = ((JTextField) ui.get("password").getComponent()).getText(); //Bitte Variable definieren (aus dem passendem TextFeld
            int response = Network.register(firstName, lastName, mail, birthdate, street, houseNumber, city, zipCode, password);
            if (response > 0) {
                JOptionPane.showMessageDialog(null, "Sie haben sich erfolgreich Regestriert \n" +
                        "Ihre ID lautet:"+response);//Zeigt "Erfolgreich registriert Meldung. Die ID ist der String "response".

            } else {
                JOptionPane.showMessageDialog(null, "Serververbindungfehlgeschlagen");//Serverfehler: "InternalServerError"
            }
        } //--------------------------------------------------------------------------------------------------------------------------------------
        HashMap<String, String> result = new HashMap<>();
        ui.forEach((name, uiContainer) -> {
            if (name.split("-").length == 1)
                result.put(name, ((JTextField) uiContainer.getComponent()).getText());
        });
        callBack.accept("login", result);
    }

    private boolean validateUserInput() {
        AtomicBoolean returnValue = new AtomicBoolean(true);
        ui.forEach((name, uiContainer) -> {
            if (!name.contains("-") && ((JTextField) uiContainer.getComponent()).getText().equals(""))
                returnValue.set(false);
        });
        if (!((JPasswordField) ui.get("password").getComponent()).getText()
                .equals(
                        ((JPasswordField) ui.get("passwordRepeat").getComponent()).getText())
        )
            returnValue.set(false);
        return returnValue.get();
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
        if(ae.getSource() == this.ui.get("login-button").getComponent()) {
            Window.getInstance().applyView(new Login(callBack));

        }
    }
}