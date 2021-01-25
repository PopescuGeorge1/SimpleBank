package Home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utils.ConnectionUtil;

import java.awt.Component;
import javax.swing.JLabel;

public class LoginController implements Initializable 
{

	@FXML
	private TextField userField;

	@FXML
	private PasswordField passField;

	@FXML
	private Hyperlink forgotPass;

	@FXML
	private Button loginBtn;

	@FXML
	private Button closeBtn;

	@FXML 
	private Label errorLbl;

	Connection con=null;
	PreparedStatement prepStat = null;
	ResultSet resSet = null;

	public LoginController() {
		con=ConnectionUtil.conDB();
	}
	
	public void handleBtnAction(MouseEvent e) {
		if(e.getSource()==loginBtn)
		{
			//login
			if(logIn().equals("Success"))
				showDialog("Success", null, "LogIn Successfully");

		}
	}

	private String logIn() {
		String status = "Success";
		String email= userField.getText().toString();
		String pass = passField.getText().toString();

		String sql = "SELECT * FROM employees where email=? and password=?";

		try {
			prepStat = con.prepareStatement(sql);
			prepStat.setString(1, email);
			prepStat.setString(2, pass);
			resSet = prepStat.executeQuery();
			if(!resSet.next())
			{
				errorLbl.setText("Enter Correct Email/Password");
				status="Error";
			}
			else
			{
				errorLbl.setText("Login Successfull");
			}
		}catch(Exception e) {}
		return status;
	}


	
	public void showDialog(String info, String header, String title) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText(info);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}



