package ParmecyManagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.*;

public class RegisterFrame extends JFrame {
	
	
	
	JLabel registerHeadinglabel,registerRegisterLabel,registerNameLabel,registerEmailLabel,registerPasswordLabel,registerConfirmPassWordLabel;
	JLabel registerAddressLabel, registerMobileLabel,registerRoleLabel;
	
	JTextField registerNameTextField,registerEmailTextField;
	JPasswordField registerConfirmPassWordTextField,registerPasswordTextField;
	JTextField registerAddressTextField, registerMobileTextField;
	
	JButton registerButton,registerLoginButton;
	JComboBox<String> RegisterRoleComboBox;
	JCheckBox checkBox,checkBox1;
	
	JPanel registerHeadPannel,registerBodyPanel;
	
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	public RegisterFrame() {
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy_manager","root","");
			st = con.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		setSize(450,780);
		setDefaultCloseOperation(3);
		setResizable(true);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Pharmacy Management System");
			
		Font my_font = new Font("Times New Roman", Font.PLAIN,20);
		
		
		registerHeadPannel = new JPanel();
		registerHeadPannel.setBounds(2,2,429,250);
		registerHeadPannel.setBackground(Color.white);
		registerHeadPannel.setLayout(null);
		add(registerHeadPannel);
		
		registerBodyPanel = new JPanel();
		registerBodyPanel.setBounds(2,252,429,486);
		registerBodyPanel.setBackground(new Color(51,153,255));
		registerBodyPanel.setLayout(null);
		add(registerBodyPanel);
		
		ImageIcon pharmacyImageIcon = new ImageIcon(getClass().getResource("pharaa.png"));
		JLabel pharmacyImageLabel = new JLabel(pharmacyImageIcon);
        pharmacyImageLabel.setBounds(0, 0, 429, 250);
        registerHeadPannel.add(pharmacyImageLabel);
		
		
		
		
		/*registerHeadinglabel = new JLabel("Pharmacy Manager");
		registerHeadinglabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		registerHeadinglabel.setBounds(160,460,280, 30);
		registerHeadinglabel.setForeground(Color.white);
		registerBodyPanel.add(registerHeadinglabel);*/
		
		
		registerRegisterLabel = new JLabel("Register");
		registerRegisterLabel.setFont(new Font("Times New Roman", Font.PLAIN, 38));
		registerRegisterLabel.setBounds(150,10,280, 40);
		registerRegisterLabel.setForeground(Color.white);
		registerBodyPanel.add(registerRegisterLabel);
		
		
		registerNameLabel = new JLabel("Name  ");
		registerNameLabel.setFont(my_font);
		registerNameLabel.setBounds(20,60,280, 30);
		registerNameLabel.setForeground(Color.white);
		registerBodyPanel.add(registerNameLabel);
		
		
		registerNameTextField = new JTextField();
		registerNameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerNameTextField.setBounds(180, 60,220,30);
		registerNameTextField.setBackground(Color.white);
		registerBodyPanel.add(registerNameTextField);
		
		registerEmailLabel = new JLabel("Email  ");
		registerEmailLabel.setFont(my_font);
		registerEmailLabel.setBounds(20,110,280, 30);
		registerEmailLabel.setForeground(Color.white);
		registerBodyPanel.add(registerEmailLabel);
		
		
		registerEmailTextField = new JTextField();
		registerEmailTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerEmailTextField.setBounds(180, 110,220,30);
		registerEmailTextField.setBackground(Color.white);
		registerBodyPanel.add(registerEmailTextField);
		
		
		registerPasswordLabel = new JLabel("Password  ");
		registerPasswordLabel.setFont(my_font);
		registerPasswordLabel.setBounds(20,160,280, 30);
		registerPasswordLabel.setForeground(Color.white);
		registerBodyPanel.add(registerPasswordLabel);
		
		
		registerPasswordTextField = new JPasswordField();
		registerPasswordTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerPasswordTextField.setBounds(180, 160,220,30);
		registerPasswordTextField.setBackground(Color.white);
		registerBodyPanel.add(registerPasswordTextField);
		
		
		
		//JPasswordField registerConfirmPassWordTextField,registerPasswordTextField;
		
		checkBox = new JCheckBox();
		checkBox.setFont(my_font);
		checkBox.setBounds(382, 190,20,20);
		checkBox.setBackground(new Color(51,153,255));
		checkBox.setForeground(Color.white);
		checkBox.setFocusable(false);
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					registerPasswordTextField.setEchoChar((char)0);
				}
				else {
					registerPasswordTextField.setEchoChar('*');
				}
			}
		});
		registerBodyPanel.add(checkBox);
		
		
		registerConfirmPassWordLabel = new JLabel("Confirm Password  ");
		registerConfirmPassWordLabel.setFont(my_font);
		registerConfirmPassWordLabel.setBounds(20,210,280, 30);
		registerConfirmPassWordLabel.setForeground(Color.white);
		registerBodyPanel.add(registerConfirmPassWordLabel);
		
		
		registerConfirmPassWordTextField = new JPasswordField();
		registerConfirmPassWordTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerConfirmPassWordTextField.setBounds(180, 210,220,30);
		registerConfirmPassWordTextField.setBackground(Color.white);
		registerBodyPanel.add(registerConfirmPassWordTextField);
		
		checkBox1 = new JCheckBox();
		checkBox1.setFont(my_font);
		checkBox1.setBounds(382, 240,20,20);
		checkBox1.setBackground(new Color(51,153,255));
		checkBox1.setForeground(Color.white);
		checkBox1.setFocusable(false);
		checkBox1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox1.isSelected()) {
					registerConfirmPassWordTextField.setEchoChar((char)0);
				}
				else {
					registerConfirmPassWordTextField.setEchoChar('*');
				}
			}
		});
		registerBodyPanel.add(checkBox1);
		
		
		registerAddressLabel = new JLabel("Address");
		registerAddressLabel.setFont(my_font);
		registerAddressLabel.setBounds(20,260,280, 30);
		registerAddressLabel.setForeground(Color.white);
		registerBodyPanel.add(registerAddressLabel);
		
		
		registerAddressTextField = new JTextField();
		registerAddressTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerAddressTextField.setBounds(180, 260,220,30);
		registerAddressTextField.setBackground(Color.white);
		registerBodyPanel.add(registerAddressTextField);
		
		registerMobileLabel = new JLabel("Mobile");
		registerMobileLabel.setFont(my_font);
		registerMobileLabel.setBounds(20,310,280, 30);
		registerMobileLabel.setForeground(Color.white);
		registerBodyPanel.add(registerMobileLabel);
		
		
		registerMobileTextField = new JTextField();
		registerMobileTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		registerMobileTextField.setBounds(180, 310,220,30);
		registerMobileTextField.setBackground(Color.white);
		registerBodyPanel.add(registerMobileTextField);
		
		registerRoleLabel = new JLabel("Role");
		registerRoleLabel.setFont(my_font);
		registerRoleLabel.setBounds(20, 360, 170, 30);
		registerRoleLabel.setForeground(Color.white);
		registerBodyPanel.add(registerRoleLabel);
		
		String[] Role = {"Admin","Salesman"};
		RegisterRoleComboBox = new JComboBox<String>(Role);
		RegisterRoleComboBox.setBounds(180, 360, 220, 30);
		registerBodyPanel.add(RegisterRoleComboBox);
	
		
		registerButton = new JButton("Register");
		registerButton.setFont(my_font);
		registerButton.setFocusable(false);
		registerButton.setBounds(180,430,100,40);
		registerButton.setBackground(new Color(0,102,0));
		registerButton.setForeground(Color.white);
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()== registerButton) {
					
					String name = registerNameTextField.getText();
					String email = registerEmailTextField.getText();
					String password = registerPasswordTextField.getText();
					String conpass = registerConfirmPassWordTextField.getText();
					String address = registerAddressTextField.getText();
					String mobile = registerMobileTextField.getText();		
					String selectRole = (String)  RegisterRoleComboBox.getSelectedItem();
					
					
					String username = registerNameTextField.getText();
					String useremail = registerEmailTextField.getText();
					String userpass = registerPasswordTextField.getText();
					String userconPass = registerConfirmPassWordTextField.getText();
					String usermobile = registerMobileTextField.getText();
					String useraddress = registerAddressTextField.getText();
					
					
					String userNameRegex = "^[a-zA-Z]{3,20}$";
					String emailRegex = "^[a-z0-9]+@[a-z]+.[a-z]+$";//a@c.c pattern
					String mobileRegex = "^(\\+88)?01[2-9]\\d{8}$";
					String passRegex = "^((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%&*_+=])).{6,20}$";
					String addressRegex = "^[a-zA-Z0-9,.-]{10,20}$";
					
					
					if(!Pattern.matches(userNameRegex, username)) {
						JOptionPane.showMessageDialog(null,"Invalid name");
					}
					else if(!Pattern.matches(emailRegex, email)) {
						JOptionPane.showMessageDialog(null,"Invalid email");
					}
					else if(!Pattern.matches(passRegex, userpass)) {
						JOptionPane.showMessageDialog(null,"Invalid password");
					}
					else if(!Pattern.matches(mobileRegex, mobile)) {
						JOptionPane.showMessageDialog(null,"Invalid mobile");
					}
					else if(!Pattern.matches(addressRegex, address)) {
						JOptionPane.showMessageDialog(null,"Invalid address");
					}		
					
					else if(name.equals("") || email.equals("") || password.equals("") || address.equals("") ||
							conpass.equals("")||mobile.equals("")||selectRole.equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter data in all the fields.");
						return;
					}
					else if(userpass!=conpass) {
						JOptionPane.showMessageDialog(null, "Please Check your Password and Confirm Password");
						return;
					}
					else {
						JOptionPane.showMessageDialog(null,"Successfully registered");
						/*System.out.println(username);
						System.out.println(email);
						System.out.println(userpass);
						System.out.println(userconPass);
						System.out.println(mobile);
						System.out.println(address);*/
					
					
					try {
						String sql = "INSERT INTO `employee`(`employee_name`, `employee_email`, `employee_password`, `employee_phone`, `employee_address`, `employee_role`) "
								+ "VALUES ('" + name + "','" + email + "','" + password+ "','" + mobile + "','" + address + "','" + selectRole + "')";
						int c = st.executeUpdate(sql);		
						
					}catch(Exception e1) {
						e1.printStackTrace();
					}
					dispose();
					new LoginFrame();
					
					}
				}
				
			
				
			}
		});
		registerBodyPanel.add(registerButton);
		
				
		
		/*registerLoginButton = new JButton("Login");
		registerLoginButton.setFont(my_font);
		registerLoginButton.setFocusable(false);
		registerLoginButton.setBounds(300,440,100,40);
		registerLoginButton.setBackground(Color.green);
		registerLoginButton.setForeground(Color.white);
		registerLoginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();
			}
		});
		registerBodyPanel.add(registerLoginButton);*/
		
		
		setVisible(true);
		
		
	}
}