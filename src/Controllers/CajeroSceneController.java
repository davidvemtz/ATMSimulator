/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.Cuenta;
import cajero.Cajero;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author David
 */
public class CajeroSceneController implements Initializable {
    
    @FXML
    private Button button0;
            
    @FXML
    private Button button1;
    
    @FXML
    private Button button2;
            
    @FXML
    private Button button3;

    @FXML
    private Button button4;
            
    @FXML
    private Button button5;
    
    @FXML
    private Button button6;
            
    @FXML
    private Button button7;         
            
    @FXML
    private Button button8;
            
    @FXML
    private Button button9;
    
    @FXML
    private Button buttonDeposit;
    
    @FXML
    private Button buttonBalance;
    
    
    @FXML
    private Button buttonWithdraw;
    
    @FXML
    private Button buttonCancel;
            
    @FXML
    private Button buttonEnter;     
    
    @FXML
    private TextField resultArea; 
    
    @FXML
    private TextArea infoArea;  
    
    private int flag = 1; // indica el estado de transacción del atm
    //flag = 0 ninguna transacción ha sido seleccionada
    //flag = 1 usuario incorrecto (cuenta incorrecta)
    //flag = 2 significa que deben ingresar una cantidad a depositar (se busca depósito bancario)
    //flag = 3 significa que deben ingresar una cantidad a retirar (se busca retiro bancario)
    private Cuenta cuentaActual = null; 
                
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        if(event.getSource() == button0)
        {
            resultArea.appendText("0");
        }
        else if(event.getSource() == button1)
        {
            resultArea.appendText("1");
        }
        else if(event.getSource() == button2)
        {
            resultArea.appendText("2");
        }
        else if(event.getSource() == button3)
        {
            resultArea.appendText("3");
        }
        else if(event.getSource() == button4)
        {
            resultArea.appendText("4");
        }
        else if(event.getSource() == button5)
        {
            resultArea.appendText("5");
        }
        else if(event.getSource() == button6)
        {
            resultArea.appendText("6");
        }
        else if(event.getSource() == button7)
        {
            resultArea.appendText("7");
        }
        else if(event.getSource() == button8)
        {
            resultArea.appendText("8");
        }
        else if(event.getSource() == button9)
        {
            resultArea.appendText("9");
        }
        else if(event.getSource() == buttonEnter) 
        {
            String entered = resultArea.getText(); 
            if(entered == null | entered == "")
            {
                infoArea.setText("Valor introducido incorrecto");
            }
            else if(flag == 0) 
            {
                infoArea.setText("Selecciona una transacción válida");
            }
            else if(flag == 1) // es posible hacer operaciones
            {
                boolean match = false; 
                int cuenta_no = Integer.parseInt(entered);
                for(Cuenta cuenta: Cajero.cuentas) // busca la cuenta de usuario introducida
                {
                    if(cuenta_no == cuenta.getId())
                    {
                        cuentaActual = cuenta;
                        match = true;
                    }
                }
                resultArea.clear(); 
                if(match == false)
                {
                    infoArea.setText("Numero de cuenta no válido. Vuelva a intentarlo.");
                }
                else 
                {
                   infoArea.setText("Numero de cuenta: "+ cuentaActual.getId()+"\n"+"Selecciona un movimiento.");
                   flag = 0;
                }
            }
            else if(flag == 2) 
            {
                double money = Double.parseDouble(entered);
                cuentaActual.deposit(money); // suma el saldo más el dinero depositado
                infoArea.setText("Saldo actual: "+cuentaActual.getBalance()+"\n"+"Seleccione otro movimiento.");
                resultArea.clear(); 
                flag = 0;
            }
            else if(flag == 3)
            {
                double money = Double.parseDouble(entered);
                
                if(cuentaActual.getBalance()<money) // verifica que el saldo sea suficiente
                {
                    infoArea.setText("Saldo insuficiente, tu saldo es: "+cuentaActual.getBalance()+"\n"+"Seleccione otro movimiento.");
                    resultArea.clear();
                }
                else
                {
                    cuentaActual.withdraw(money);  // Aplica el retiro (resta de cantidad retirada al saldo)
                    infoArea.setText("Tu saldo es: "+cuentaActual.getBalance()+"\n"+"Seleccione otro movimiento.");
                    resultArea.clear();
                }
                flag = 0;
            }
        }
        else if(event.getSource() == buttonBalance) // muestra saldo
        {
            if(cuentaActual != null)
            {
                infoArea.setText("Saldo insuficiente, tu saldo es: "+cuentaActual.getBalance()+"\n"+"Seleccione otro movimiento.");
            }
            else
            {
                infoArea.setText("Ingresa un número de cuenta válido.");
            }
        }
        else if(event.getSource() == buttonDeposit) 
        {
            if(cuentaActual != null)
            {
                infoArea.setText("Cantidad a depositar: ");
                flag = 2;
            }
            else
            {
                infoArea.setText("Ingresa un número de cuenta válido.");
            }
        }
        else if(event.getSource() == buttonWithdraw) 
        {
            if(cuentaActual != null)
            {
                infoArea.setText("Monto a retirar: ");
                flag = 3;
            }
            else
            {
                infoArea.setText("Ingresa un número de cuenta válido.");
            }
        }
        else if(event.getSource() == buttonCancel) 
        {
            if(cuentaActual != null)
            {
                infoArea.setText("Número de cuenta: "+cuentaActual.getId()+"\n"+"Seleccione otro movimiento.");
                flag = 0;
                resultArea.clear();
            }
            else
            {
                resultArea.clear();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
