package ParmecyManagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import com.mysql.cj.xdevapi.Result;
 
public class LoginFrame extends JFrame {
 
 
	JLabel loginHeadinglabel,loginLoginLabel,loginEmailLabel,loginPasswordLabel;
	JPasswordField loginPasswordTextField;
	JTextField loginEmailTextField;
	JButton loginButton,loginRegisterButton;
	JPanel loginHeadPannel,loginBodyPanel;
	JCheckBox checkBox;
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
 
	public LoginFrame() {
		
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy_manager","root","");
			st = con.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		setSize(450,680);
		setDefaultCloseOperation(3);
		setResizable(true);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Pharmacy Management System");
 
		Font my_font = new Font("Times New Roman", Font.PLAIN,20);
 
		loginHeadPannel = new JPanel();
		loginHeadPannel.setBounds(2,2,429,250);
		loginHeadPannel.setBackground(Color.white);
		loginHeadPannel.setLayout(null);
		add(loginHeadPannel);
 
		loginBodyPanel = new JPanel();
		loginBodyPanel.setBounds(2,252,429,385);
		loginBodyPanel.setBackground(new Color(51,153,255));
		loginBodyPanel.setLayout(null);
		add(loginBodyPanel);
 
 
		loginHeadinglabel = new JLabel("Always There For Care!");
		loginHeadinglabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		loginHeadinglabel.setBounds(60,290,320, 50);
		loginHeadinglabel.setForeground(new Color(255,255,255));
		loginBodyPanel.add(loginHeadinglabel);
		
		
		ImageIcon pharmacyImageIcon = new ImageIcon(getClass().getResource("pharaa.png"));
		Image pharaa = pharmacyImageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(pharaa);
		JLabel pharmacyImageLabel = new JLabel(scaledIcon);
		pharmacyImageLabel.setBounds(0, 0, 429, 250);
		loginHeadPannel.add(pharmacyImageLabel);
 
		loginLoginLabel = new JLabel("Login");
		loginLoginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		loginLoginLabel.setBounds(170,10,280, 40);
		loginLoginLabel.setForeground(Color.white);
		loginBodyPanel.add(loginLoginLabel);
 
		loginEmailLabel = new JLabel("Email");
		loginEmailLabel.setFont(my_font);
		loginEmailLabel.setBounds(20,80,280, 30);
		loginEmailLabel.setForeground(Color.white);
		loginBodyPanel.add(loginEmailLabel);
 
 
		loginEmailTextField = new JTextField();
		loginEmailTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		loginEmailTextField.setBounds(180, 80,220,30);
		loginEmailTextField.setBackground(Color.white);
		loginBodyPanel.add(loginEmailTextField);
 
		loginPasswordLabel = new JLabel("Password  ");
		loginPasswordLabel.setFont(my_font);
		loginPasswordLabel.setBounds(20,140,280, 30);
		loginPasswordLabel.setForeground(Color.white);
		loginBodyPanel.add(loginPasswordLabel);
		
		
		
		checkBox = new JCheckBox();
		checkBox.setFont(my_font);
		checkBox.setBounds(382,170,20, 20);
		checkBox.setBackground(new Color(51,153,255));
		checkBox.setForeground(Color.white);
		checkBox.setFocusable(false);
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					loginPasswordTextField.setEchoChar((char)0);
				}
				else {
					loginPasswordTextField.setEchoChar('*');
				}
			}
		});
		loginBodyPanel.add(checkBox);
 
 
		loginPasswordTextField = new JPasswordField();
		loginPasswordTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		loginPasswordTextField.setBounds(180, 140,220,30);
		loginPasswordTextField.setBackground(Color.white);
		loginBodyPanel.add(loginPasswordTextField);
 
		loginButton = new JButton("Login");
		loginButton.setFont(my_font);
		loginButton.setFocusable(false);
		loginButton.setBounds(180,200,100,40);
		loginButton.setBackground(new Color(0,0,153));
		loginButton.setForeground(Color.white);
		loginButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        String email = loginEmailTextField.getText();
		        String pass = loginPasswordTextField.getText();
		        
		       // System.out.println(email);
		        //System.out.println(pass);

		        if (email.equals("")) {
		            JOptionPane.showMessageDialog(null, "Please enter Email");
		            return;
		        } else if (pass.equals("")) {
		            JOptionPane.showMessageDialog(null, "Please enter Password");
		            return;
		        } else {
		            try {

		                String sql1 = "SELECT * FROM `employee` WHERE employee_email = '" + email + "'";
		                String sql2 = "SELECT * FROM `employee` WHERE employee_password = '" + pass + "'";

		              //  System.out.println(sql1);
		               // System.out.println(sql2);

		                String conmail = "";
		                String conpass = "";
		                String role ="";

		                ResultSet rs1 = st.executeQuery(sql1);
		                while (rs1.next()) {
		                    conmail = rs1.getString("employee_email");
		                    role = rs1.getString("employee_role");
		                    //System.out.println(conmail);
		                    //System.out.println(role);
		                }

		                ResultSet rs2 = st.executeQuery(sql2);
		                while (rs2.next()) {
		                	
		                    conpass = rs2.getString("employee_password");
		                    //System.out.println(conpass);
		                }
	                

		                if (!conmail.isEmpty() && !conpass.isEmpty()) {
		                	if(pass.equals(conpass)&& email.equals(conmail)) {
		  
			                    dispose();
			                    //new MainFrame();  
			                    
			                    if(role.equals("Admin")) {
			                    	new AdminView();
			                    }
			                    else if(role.equals("Salesman")) {
			                    	new SalesmanView();
			                    }
		                	}
		                	else {
			                    JOptionPane.showMessageDialog(null, "You are not a registered user. Please register!");
			                    loginEmailTextField.setText("");
			                    loginPasswordTextField.setText("");
			                    return;
			                }

		                } else {
		                    JOptionPane.showMessageDialog(null, "You are not a registered user. Please register!");
		                    loginEmailTextField.setText("");
		                    loginPasswordTextField.setText("");
		                    return;
		                }
		            } catch (SQLException s1) {
		                s1.printStackTrace();
		            }

		        }
		    }
		});

		loginBodyPanel.add(loginButton);
 
 
		loginRegisterButton = new JButton("Register");
		loginRegisterButton.setFont(my_font);
		loginRegisterButton.setFocusable(false);
		loginRegisterButton.setBounds(300,200,100,40);
		loginRegisterButton.setBackground(new Color(0,0,153));
		loginRegisterButton.setForeground(Color.white);
		loginRegisterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				new RegisterFrame();
			}
		});
		loginBodyPanel.add(loginRegisterButton);
 
 
		setVisible(true);
	}
}