package lws.banksystem.server.network;

import lws.banksystem.server.log.Logger;

public class ConnectionSheduler extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NetworkHandler.disconnctInActiveConnections();
            Logger.log("Ermögliche erneuten Login...");
            AntiDos.getInstance().ipAddresses.clear();
            Logger.log("Erneuter Login wurde ermöglicht!");
        }
    }

}
