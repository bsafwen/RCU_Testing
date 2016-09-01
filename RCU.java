/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcu;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author bsafwene
 */
public class RCU extends Application {

    private static RCU me;
    private String port;
    public static Stage stage;
    public static SerialPort serialPort ;
    public static RCU getInstance() {
        return me;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String s) {
        port = s;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLFirst.fxml"));
        RCU.stage = stage;
        Scene scene = new Scene(root);
        RCU.me = this;
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        launch(args);
        try {
           if (  serialPort != null ){
               serialPort.closePort();
           }
           
        } catch (SerialPortException ex) {
            Logger.getLogger(RCU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
