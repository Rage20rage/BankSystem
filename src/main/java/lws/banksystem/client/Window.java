package lws.banksystem.client;

import lws.banksystem.client.network.Network;
import lws.banksystem.client.view.View;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame {

    private static Window window = null;
    public static Window getInstance(){
        if(window == null){
            window = new Window();
            WindowListener listener = new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Network.logout();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            };
            window.addWindowListener(listener);
        }
        return window;
    }
    String currentState = null;
    Window(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void applyView(View viewModel) {
        getContentPane().removeAll();
        add(viewModel.getJPanel());
        currentState = viewModel.getClass().getSimpleName();
        setVisible(true);
    }
}
