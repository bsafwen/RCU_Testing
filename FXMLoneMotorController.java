/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author bsafwene
 */
public class FXMLoneMotorController implements Initializable {
    @FXML
    RadioButton leftMotor, rightMotor ;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    TextField nbAppuis, vitesseAppui, dureeAppui ;
    @FXML
    Button goButton ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftMotor.setSelected(true);
        leftMotor.setToggleGroup(group);
        rightMotor.setToggleGroup(group);

    } 
    @FXML
    private void back(ActionEvent event)throws Exception{
          Parent root = FXMLLoader.load(getClass().
                    getResource("FXMLFirst.fxml"));
        Scene scene = new Scene(root);
        RCU.stage.setScene(scene);
        RCU.stage.setResizable(false);
        RCU.stage.show();
    }
    @FXML
    private void queLaFeteCommence(ActionEvent event) throws Exception{
        int nb, vit, dur ;
        String whichM ;
        nb = Integer.parseInt(nbAppuis.getText());
        vit = Integer.parseInt(vitesseAppui.getText());
        dur = Integer.parseInt(dureeAppui.getText());
        if ( nb <= 0 || vit <= 0 || dur <= 0 ){
            Logger.getLogger(FXMLoneMotorController.class.getName()).
                    warning("Invalid input.");
            throw new Exception() ;
        }
        if (group.getSelectedToggle().equals(leftMotor)){
            whichM = "1";
        }
        else if (group.getSelectedToggle().equals(rightMotor) ){
            whichM = "2";
        }
        else {
            Logger.getLogger(FXMLoneMotorController.class.getName()).
                    warning("No motor selected.");
            throw new Exception();
        }
        if ( RCU.serialPort.isOpened() ){
            RCU.serialPort.writeString(whichM
                                        + " "
                                        + nbAppuis.getText()
                                        + " "
                                        + vitesseAppui.getText()
                                        + " "
                                        + dureeAppui.getText()
                                        + " #");
        }
        else {
            Logger.getLogger(FXMLoneMotorController.class.getName()).
                    warning("Serial port not open.");
        }
    }
    
}
