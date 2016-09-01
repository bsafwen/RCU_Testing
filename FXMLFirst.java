/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * FXML Controller class
 *
 * @author bsafwene
 */
public class FXMLFirst implements Initializable {
    @FXML
    private Button oneM ;
    @FXML
    private Button bothM ;
    private static boolean first_time = true ;
    @FXML
    private Label portError ;
    private TextField from ;
    private TextField to ;
    private TextField interval ;
    @FXML
    private ChoiceBox portcb ;

     private static ObservableList<String> portList = FXCollections
            .observableArrayList();

    private TextField number_of_buttons_tf ;
    @FXML
    private Button conf1next;
    @FXML
    private Button getdevButton ;
    private Label number_of_buttons_Label;
    String[] portNames ;
           
    @FXML
    private Insets x1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         portcb.setItems(portList);
         portNames = SerialPortList.getPortNames();
        if ( portNames.length == 0 ){
            portError.setVisible(true);
        }
        else {
                        portError.setVisible(false);
                for(int i = 0; i < portNames.length; i++){
                    portList.add((portNames[i]));
                }

                    portcb.setValue(portNames[0]);
        }
    }
    @FXML
    private void goToController(ActionEvent event) throws Exception{
        try{
                 portNames = SerialPortList.getPortNames();
                   if ( portNames.length == 0 ){
                      portError.setVisible(true);
                      first_time = true ;
                      portList.clear();
                      portcb.setItems(portList);
                      throw new Exception();
                   }      
                                   ;
            if ( RCU.serialPort == null || ! RCU.serialPort.isOpened() )
                RCU.serialPort = new SerialPort(portcb.getValue().toString());
            if ( ! RCU.serialPort.isOpened() ){
                RCU.serialPort.openPort();//Open serial port
            }
            Thread.sleep(500);
        
          while ( ! RCU.serialPort.isOpened() ) {
            RCU.serialPort.openPort();
           }
        RCU.serialPort.setParams(SerialPort.BAUDRATE_9600, 
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE, false, false);
        
        portError.setVisible(false);
        Button src  = (Button)event.getSource() ;
        if (src.equals(oneM)){
            Parent root = FXMLLoader.load(getClass().
                    getResource("FXMLoneMotor.fxml"));
        Scene scene = new Scene(root);
        RCU.stage.setScene(scene);
        RCU.stage.setResizable(false);
        RCU.stage.show();
        }
        else if ( src.equals(bothM)){
                 Parent root = FXMLLoader.load(getClass().
                    getResource("FXMLScenario.fxml"));
        Scene scene = new Scene(root);
        RCU.stage.setScene(scene);
        RCU.stage.setResizable(false);
        RCU.stage.show();
        }
        }
        catch ( NumberFormatException nfe ){
            number_of_buttons_Label.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void let_the_fun_begin(int n, int from, int to, int t)
            throws InterruptedException, SerialPortException{
        byte[] b = new byte[64];
        portNames = SerialPortList.getPortNames();
        if ( portNames.length == 0 ){
            portError.setVisible(true);
            portcb.setItems(portList);
        }
        else {
               String toSend = Integer.toString(n)
                             + " "
                             + Integer.toString(from)
                             + " "
                             + Integer.toString(to)
                             + " "
                             + Integer.toString(t)
                             + "#"
                             ;
              if ( RCU.serialPort == null || ! RCU.serialPort.isOpened() ){
                    RCU.serialPort = new SerialPort(portcb.getValue().toString());
              }
              
            if ( ! RCU.serialPort.getPortName().equals(portcb.getValue().toString())){
                RCU.serialPort.closePort();
                RCU.serialPort = new SerialPort(portcb.getValue().toString());
            }
         
    try {
        if ( ! RCU.serialPort.isOpened() ){
             RCU.serialPort.openPort();//Open serial port
        if ( first_time ){
            first_time = false ;
            Thread.sleep(3000);
        }
          while ( ! RCU.serialPort.isOpened() ) {
            RCU.serialPort.openPort();
        }
        RCU.serialPort.setParams(SerialPort.BAUDRATE_9600, 
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE, false, false);
        }
        RCU.serialPort.writeString(toSend);
            }
            catch (SerialPortException ex) {
                System.out.println(ex);
            }
        }
    }
    
    @FXML
    private void getDevices(ActionEvent event) throws Exception{
        portList.clear();
          portcb.setItems(portList);
          portNames = SerialPortList.getPortNames();
        if ( portNames.length == 0 ){
            portError.setVisible(true);
                        first_time = true ;
        }
        else {
                        portError.setVisible(false);

                for(int i = 0; i < portNames.length; i++){
                    portList.add((portNames[i]));
                }
                    portcb.setItems(portList);

                    portcb.setValue(portNames[0]);
        }
        
    }
    
}
