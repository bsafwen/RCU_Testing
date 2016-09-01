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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author bsafwene
 */
public class FXMLScenarioController implements Initializable {
    @FXML
    TextField repos, appui ;
    @FXML
    TextArea txtArea ;
    @FXML
    Label alerteLabel ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void lancerScenario(ActionEvent event) throws Exception{
        String txt = txtArea.getText().trim();
        if ( txt == null || txt.trim().length() <= 0
                || repos == null || repos.getText().length()==0
                || appui == null || appui.getText().length()==0 ){
            alerteLabel.setVisible(true);
            throw new Exception() ;
        }
        if ( ! RCU.serialPort.isOpened() ){
              Parent root = FXMLLoader.load(getClass().
                    getResource("FXMLFirst.fxml"));
        Scene scene = new Scene(root);
        RCU.stage.setScene(scene);
        RCU.stage.setResizable(false);
        RCU.stage.show();
        }
        alerteLabel.setVisible(false);
        String[] toSend = txt.split(" ");
        for (int i=0; i<toSend.length;++i){
            if ( (i+1)%2 == 0){
                 RCU.serialPort.writeString(  "2 "
                                     + toSend[i]
                                     + " "
                                     + repos.getText()
                                     + " "
                                     + appui.getText()
                                     + " #"  );
            }
            else {
                     RCU.serialPort.writeString(  "1 "
                                     + toSend[i]
                                     + " "
                                     + repos.getText()
                                     + " "
                                     + appui.getText()
                                     + " #"  );
            }
            Thread.sleep(Integer.parseInt(toSend[i]) * 
                          ( Integer.parseInt(repos.getText()) +
                                  Integer.parseInt(appui.getText())));
        }
       /* RCU.serialPort.writeString(  "S " 
                                     + repos.getText()
                                     + " "
                                     + appui.getText()
                                     + " "
                                     + txt 
                                     + " #"  );*/
    }
    
}
