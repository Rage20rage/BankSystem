package lws.banksystem.client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;

public interface View {
    JPanel getJPanel();

    void actionPerformed(ActionEvent ae);
}
