/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mediator;

import Client.WSClient;
import gui.GUI;
import java.util.Vector;
import network.Network;

/**
 *
 * @author LucianDobre
 */
public class Mediator {
    private Vector<String> downloadColumnNames;
    private GUI gui;
    private Network net;
    private WSClient client;

    public Mediator() {
        downloadColumnNames = new Vector<>();
        downloadColumnNames.add("Source");
        downloadColumnNames.add("Destination");
        downloadColumnNames.add("File name");
        downloadColumnNames.add("Progress");
        downloadColumnNames.add("Status");
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public Network getNet() {
        return net;
    }

    public void setNet(Network net) {
        this.net = net;
    }

    public WSClient getClient() {
        return client;
    }

    public void setClient(WSClient client) {
        this.client = client;
    }

    public Vector<String> getDownloadColumnNames() {
        return downloadColumnNames;
    }

    public void setDownloadColumnNames(Vector<String> downloadColumnNames) {
        this.downloadColumnNames = downloadColumnNames;
    }
}
