package ParmecyManagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class SalesmanView extends JFrame {

	JTabbedPane managementPane;
	Font my_font = new Font("Times New Roman", Font.PLAIN, 20);

	// Receipt tab variables
	JPanel recieptPanel, stockPanel, historyPanel, ordersPanel, suggestionPanel;
	JPanel recepitTablePanel, recepitCustomerDataPanel, receiptPayPanel;
	JLabel discountLabel, totalAmountLabel, cashPaidLabel, returnLabel, counterNumberLabel, discountTotalAmountLabel;
	JLabel customerNameLabel, customerAddressLabel, customerMobileNumberLabel, customerDetailsLabel;
	JLabel itemNameLabel, quantityLabel, companyNameLabel;
	JButton updateButton, recsave, totalAmount, printButton, clearButton, addButton, deleteButton, applyDiscountButton,
			calculatReturnButton;
	JTextField discountTextField, totalAmountTextField, cashPaidTextField, returnTextField,
			discountTotalAmountTextField;
	JTextField customerNameTextField, customerAddressTextField, customerPhoneNumberTextField;
	JTextField counterNumberTextField, quantityTextField, companyNameTextField, itemNameTextField;
	JTable receiptTable;
	JDateChooser ReciptCurrentDate;
	JScrollPane receiptTableScrollPane;
	Object receiptData[][] = {};
	String receiptColums[] = { "Serial", "Name", "Rate", "Quantity", "Amount" };
	DefaultTableModel receiptTableModel = new DefaultTableModel(receiptData, receiptColums);

	// Stock tab variables
	JLabel stockCompanyNameLabel, stockDrugListLabel, stockSearchMedicineLNameLabel, stockSearchLabel,
			stockAddMedicineLabel;
	JTextField stockSearchCompanyNameTextField, stockSearchMedicineTextField;
	JTable stockTable;
	Object stockData[][] = {};
	String stockColumnNames[] = { "Name", "MRP", "TP", "Quantity", "Company", "Type" };
	DefaultTableModel stockModel = new DefaultTableModel(stockData, stockColumnNames);
	JLabel stockAddProductNameLabel, stockAddProductTPLabel, stockAddProductMRPLabel, stockAddQuantityLabel,
			stockAddCompanyLabel;
	JPanel stockTablePanel,stockOperationPanel,stockInformationPanel;
	JButton stockAddButton, stockSearchButton, stockPrintButton, stockDeleteButton, stockUpdateButton,stockClearButton;
	JTextField stockAddProductNameTextField, stockAddProductTPTextField, stockAddProductMRPTextField,
			stockAddQuantityTextField, stockAddCompanyTextField;
	boolean stockAddFlag = false, stockSearchFlag = false, stockUpdateFlag = false;
	JLabel stockTypeLabel;
	JTextField stockTypeTextField;

	// History tab variables
	JPanel historyDetailsPanel;
	JLabel historyTimePeriodLabel, historyCounterNumberLabel, historyTotalItemsLabel, historyTotalQuantityLabel,
			historyTotalDiscountLabel;
	JLabel historyTotalMRPLabel, historyTotalBuyingPriceLabel, historyTotalMarginLabel;
	JLabel historyFromLabel, historyToLabel;
	JTextField historyTimePeriodTextField, historyCounterNumberTextField, historyTotalItemsTextField,
			historyTotalQuantityTextField;
	JTextField historyTotalDiscountTextField, historyTotalMRPTextField, historyTotalBuyingPriceTextField,
			historyTotalMarginTextField;
	JDateChooser historyFrom, historyTo;
	JComboBox<Integer> historycounterNumberComboBox;
	JButton historySearch;

	// Orders tab variables
	JLabel ordersProductName, ordersQuantity, ordersProductCompany, ordersDateJlabel;
	JTextField ordersQuantityTextfield, ordersProductCompanytTextfield, ordersProductNameTextfield;
	JTable ordersTable;
	JPanel ordersOperationPanel;
	JButton ordersAddButton, ordersDeleteButton, ordersClearButton, ordersSaveButton, ordersPrintButton,
			ordersUpdateButton;
	Object ordersData[][] = {};
	String ordersColumnNames[] = { "Date", "Serial", "Names", "Company", "Quantity" };
	DefaultTableModel OrdersModel = new DefaultTableModel(ordersData, ordersColumnNames);
	JDateChooser currentDate;

	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	// Suggestion Tab

	JLabel suggestionDrugListLabel;
	JTable suggestionTable;
	Object suggestionData[][] = {};
	String suggestionColumnNames[] = { "Name", "MRP", "TP", "Quantity", "Company", "Type" };
	DefaultTableModel suggestionModel = new DefaultTableModel(suggestionData, suggestionColumnNames);

	JLabel suggestionSearchLabel, suggestionCompanyNameLabel, suggestionSearchTypeLNameLabel;
	JTextField suggestionCompanyNameTextField, suggestionSearchTypeLNameTextField;
	JPanel suggestionInformationPanel;
	JButton suggestionSearchButton, suggestionLogoutButton;

	public SalesmanView() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy_manager", "root", "");
			st = con.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		getContentPane().setBackground(Color.white);
		setSize(1100, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setTitle("Pharmacy Manager");

		// Tabbed Pane
		managementPane = new JTabbedPane();
		managementPane.setBackground(new Color(51,153,255)); // tabs color
		managementPane.setBounds(0, 0, 1100, 800);
		managementPane.setForeground(Color.black);
		add(managementPane);

		// // Receipt Tab
		recieptPanel = new JPanel();
		recieptPanel.setLayout(null);

		// Receipt Table Details
		recepitTablePanel = new JPanel();
		recepitTablePanel.setLayout(null);
		recepitTablePanel.setBounds(0, 0, 800, 500);
		recepitTablePanel.setBackground(Color.white);

		receiptTable = new JTable(receiptTableModel);
		receiptTable.setOpaque(true);
		receiptTable.setFillsViewportHeight(true);
		receiptTable.setBackground(Color.white);
		receiptTable.getTableHeader().setBackground(Color.blue);
		receiptTable.getTableHeader().setForeground(Color.white);

		receiptTableScrollPane = new JScrollPane(receiptTable);
		receiptTableScrollPane.setBounds(10, 10, 790, 490);
		recepitTablePanel.add(receiptTableScrollPane);
		receiptTable.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				int idx = receiptTable.getSelectedRow();

				String item = receiptTableModel.getValueAt(idx, 1).toString();
				String quantity = receiptTableModel.getValueAt(idx, 3).toString();

				itemNameTextField.setText(item);
				quantityTextField.setText(quantity);
			}
		});
		recieptPanel.add(recepitTablePanel);

		// Receipt Pay Details
		receiptPayPanel = new JPanel();
		receiptPayPanel.setLayout(null);
		receiptPayPanel.setBounds(10, 500, 790, 230);
		receiptPayPanel.setBackground(new Color(255,203,164));

		totalAmountLabel = new JLabel("Total Amount");
		totalAmountLabel.setBounds(20, 20, 200, 30);
		totalAmountLabel.setFont(my_font);
		receiptPayPanel.add(totalAmountLabel);

		totalAmountTextField = new JTextField();
		totalAmountTextField.setBounds(150, 20, 100, 30);
		totalAmountTextField.setFont(my_font);
		receiptPayPanel.add(totalAmountTextField);

		totalAmount = new JButton("Total");
		totalAmount.setBounds(260, 20, 90, 30);
		totalAmount.setFocusable(false);
		totalAmount.setBackground(new Color(51,153,255));
		totalAmount.setForeground(Color.white);
		totalAmount.setFont(my_font);
		totalAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double sum = 0.0;

				for (int i = 0; i < receiptTableModel.getRowCount(); i++) {

					Object amountValue = receiptTableModel.getValueAt(i, 4);

					if (amountValue instanceof Number) {
						sum += ((Number) amountValue).doubleValue();
					}
				}
				totalAmountTextField.setText(String.valueOf(sum));
			}
		});
		receiptPayPanel.add(totalAmount);

		discountLabel = new JLabel("Discount");
		discountLabel.setBounds(20, 90, 200, 30);
		discountLabel.setFont(my_font);
		receiptPayPanel.add(discountLabel);

		discountTextField = new JTextField();
		discountTextField.setBounds(150, 90, 100, 30);
		discountTextField.setFont(my_font);
		receiptPayPanel.add(discountTextField);

		discountTotalAmountLabel = new JLabel("Total Payable");
		discountTotalAmountLabel.setBounds(20, 160, 200, 30);
		discountTotalAmountLabel.setFont(my_font);
		receiptPayPanel.add(discountTotalAmountLabel);

		discountTotalAmountTextField = new JTextField();
		discountTotalAmountTextField.setBounds(150, 160, 100, 30);
		discountTotalAmountTextField.setFont(my_font);
		receiptPayPanel.add(discountTotalAmountTextField);

		applyDiscountButton = new JButton("Apply");
		applyDiscountButton.setBounds(260, 90, 90, 30);
		applyDiscountButton.setFocusable(false);
		applyDiscountButton.setBackground(new Color(51,153,255));
		applyDiscountButton.setForeground(Color.white);
		applyDiscountButton.setFont(my_font);
		applyDiscountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					double discountPercentage = Double.parseDouble(discountTextField.getText());

					double totalAmountValue = Double.parseDouble(totalAmountTextField.getText());

					double discountedAmount = totalAmountValue - (totalAmountValue * (discountPercentage / 100));

					discountTotalAmountTextField.setText(String.valueOf(discountedAmount));

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid discount percentage.");
				}

			}
		});

		receiptPayPanel.add(applyDiscountButton);

		cashPaidLabel = new JLabel("Cash Paid");
		cashPaidLabel.setBounds(380, 20, 200, 30);
		cashPaidLabel.setFont(my_font);
		receiptPayPanel.add(cashPaidLabel);

		cashPaidTextField = new JTextField();
		cashPaidTextField.setBounds(560, 20, 100, 30);
		cashPaidTextField.setFont(my_font);
		receiptPayPanel.add(cashPaidTextField);

		calculatReturnButton = new JButton("Count");
		calculatReturnButton.setBounds(670, 20, 90, 30);
		calculatReturnButton.setFocusable(false);
		calculatReturnButton.setBackground(new Color(51,153,255));
		calculatReturnButton.setForeground(Color.white);
		calculatReturnButton.setFont(my_font);
		calculatReturnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					double discountedAmount = Double.parseDouble(discountTotalAmountTextField.getText());

					double cashPaid = Double.parseDouble(cashPaidTextField.getText());

					double returnAmount = cashPaid - discountedAmount;

					returnTextField.setText(String.valueOf(returnAmount));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"Please enter valid numeric values for discount and cash paid.");
				}

			}
		});

		receiptPayPanel.add(calculatReturnButton);

		returnLabel = new JLabel("Return");
		returnLabel.setBounds(380, 90, 200, 30);
		returnLabel.setFont(my_font);
		receiptPayPanel.add(returnLabel);

		returnTextField = new JTextField();
		returnTextField.setBounds(560, 90, 200, 30);
		returnTextField.setFont(my_font);
		receiptPayPanel.add(returnTextField);

		recsave = new JButton("Save");
		recsave.setBounds(305, 160, 200, 30);
		recsave.setFocusable(false);
		recsave.setBackground(new Color(255,51,51));
		recsave.setForeground(Color.white);
		recsave.setFont(my_font);
		recsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String customerName = customerNameTextField.getText();
				String customerAddress = customerAddressTextField.getText();
				String customerPhoneNumber = customerPhoneNumberTextField.getText();
				double  totalPurchase=0.0,customerDiscount=0.0,totalPayable=0.0,totalCashPaid=0.0,totalReturn=0.0;
				try {
					totalPurchase = Double.parseDouble(totalAmountTextField.getText());
					
					customerDiscount = Double.parseDouble(totalAmountTextField.getText())
							- Double.parseDouble(discountTotalAmountTextField.getText());
					totalPayable = Double.parseDouble(discountTotalAmountTextField.getText());
					totalCashPaid = Double.parseDouble(cashPaidTextField.getText());
					totalReturn = Double.parseDouble(returnTextField.getText());
					
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Something Went Wrong!!");
					return;
				}	

				java.util.Date selectedDate = ReciptCurrentDate.getDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = dateFormat.format(selectedDate);

				if (customerName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Customer Name");
					return;
				}
				if (customerAddress.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Customer ADDRESS");
					return;
				}
				if (customerPhoneNumber.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter Customer Phone Number");
					return;
				}
				if (totalPurchase == 0) {
					JOptionPane.showMessageDialog(null, "There is No Purchase");
					return;
				}
				if (customerDiscount == 0) {
					JOptionPane.showMessageDialog(null, "Please input Discount");
					return;
				}
				if (totalPayable == 0) {
					JOptionPane.showMessageDialog(null, "Payable Textfield is empty");
					return;
				}
				if (totalCashPaid == 0) {
					JOptionPane.showMessageDialog(null, "TotalCashPaid Textfield is empty");
					return;
				}

				try {
					
					String insertCustomerSql = "INSERT INTO customer (customer_date,customer_name, customer_address, customer_phone, "
							+ "customer_tot_purchase, customer_discount, tot_payable, tot_cashpaid, tot_return) "
							+ "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
					try (PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomerSql)) {
						insertCustomerStatement.setString(1, formattedDate);
						insertCustomerStatement.setString(2, customerName);
						insertCustomerStatement.setString(3, customerAddress);
						insertCustomerStatement.setString(4, customerPhoneNumber);
						insertCustomerStatement.setDouble(5, totalPurchase);
						insertCustomerStatement.setDouble(6, customerDiscount);
						insertCustomerStatement.setDouble(7, totalPayable);
						insertCustomerStatement.setDouble(8, totalCashPaid);
						insertCustomerStatement.setDouble(9, totalReturn);

						insertCustomerStatement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Customer data saved successfully!");


						return;
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		receiptPayPanel.add(recsave);

		printButton = new JButton("Print");
		printButton.setBounds(560, 160, 200, 30);
		printButton.setFocusable(false);
		printButton.setBackground(new Color(0,0,255));
		printButton.setForeground(Color.white);
		printButton.setFont(my_font);
		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//JTextField customerNameTextField, customerAddressTextField, customerPhoneNumberTextField;
				String name = customerNameTextField.getText();
				String adress = customerAddressTextField.getText();
				String phone = customerPhoneNumberTextField.getText();
				
				if (receiptTableModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "No data found.");
					return;
				}

				MessageFormat header = new MessageFormat("Receipt : "+name+"   "+adress+"  "+phone);
				MessageFormat footer = new MessageFormat("Page{0, number, integer}");
				try {
					receiptTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
					
					customerNameTextField.setText("");
					customerAddressTextField.setText("");
					customerPhoneNumberTextField.setText("");
					totalAmountTextField.setText("");
					discountTextField.setText("");
					discountTotalAmountTextField.setText("");
					cashPaidTextField.setText("");
					returnTextField.setText("");

					DefaultTableModel tableModel = (DefaultTableModel) receiptTable.getModel();
					tableModel.setRowCount(0);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		receiptPayPanel.add(printButton);

		recieptPanel.add(receiptPayPanel);

		// Receipt Customer Details
		recepitCustomerDataPanel = new JPanel();
		recepitCustomerDataPanel.setLayout(null);
		recepitCustomerDataPanel.setBounds(800, 0, 280, 730);
		recepitCustomerDataPanel.setBackground(new Color(192,153,153));
		recieptPanel.add(recepitCustomerDataPanel);

		counterNumberLabel = new JLabel("Phre Pharmacy");
		counterNumberLabel.setBounds(10, 10, 300, 40);
		counterNumberLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		recepitCustomerDataPanel.add(counterNumberLabel);

		companyNameLabel = new JLabel("Date");
		companyNameLabel.setBounds(10, 50, 200, 30);
		companyNameLabel.setFont(my_font);
		recepitCustomerDataPanel.add(companyNameLabel);

		ReciptCurrentDate = new JDateChooser();
		ReciptCurrentDate.setBounds(10, 90, 250, 30);
		recepitCustomerDataPanel.add(ReciptCurrentDate);

		itemNameLabel = new JLabel("Item Name");
		itemNameLabel.setBounds(10, 130, 200, 30);
		itemNameLabel.setFont(my_font);
		recepitCustomerDataPanel.add(itemNameLabel);

		itemNameTextField = new JTextField();
		itemNameTextField.setBounds(10, 170, 250, 30);
		itemNameTextField.setFont(my_font);
		recepitCustomerDataPanel.add(itemNameTextField);

		quantityLabel = new JLabel("Quantity");
		quantityLabel.setBounds(10, 210, 200, 30);
		quantityLabel.setFont(my_font);
		recepitCustomerDataPanel.add(quantityLabel);

		quantityTextField = new JTextField();
		quantityTextField.setBounds(10, 250, 250, 30);
		quantityTextField.setFont(my_font);
		recepitCustomerDataPanel.add(quantityTextField);

		addButton = new JButton("Add");
		addButton.setBounds(10, 300, 120, 50);
		addButton.setFocusable(false);
		addButton.setBackground(new Color(116,195,101));
		addButton.setForeground(Color.white);
		addButton.setFont(my_font);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String productName = itemNameTextField.getText();
				String quantity = quantityTextField.getText();

				if (productName.equals("") || quantity.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter data in all the fields.");
					return;
				}
				if (ReciptCurrentDate.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date.");
					return;
				}

				java.util.Date selectedDate = ReciptCurrentDate.getDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = dateFormat.format(selectedDate);

				try {

					double rate = 0.0, tprate = 0.0, amount = 0.0, amount1 = 0.0, amount2 = 0.0;
					int serial = receiptTableModel.getRowCount() + 1, available = 0;

					String queryRateSql = "SELECT item_mrp,item_tp, item_quantity FROM `item` WHERE LOWER(item_name) = LOWER(?)";
					try (PreparedStatement queryRateStatement = con.prepareStatement(queryRateSql)) {
						queryRateStatement.setString(1, productName);

						ResultSet resultSet = queryRateStatement.executeQuery();

						if (resultSet.next()) {

							rate = resultSet.getDouble("item_mrp");
							tprate = resultSet.getDouble("item_tp");
							int currentQuantity = resultSet.getInt("item_quantity");

							if (currentQuantity < Integer.parseInt(quantity) || currentQuantity == 0) {

								JOptionPane.showMessageDialog(null, "Not enough stock for " + productName);
								itemNameTextField.setText("");
								quantityTextField.setText("");
								return;

							} else {

								Object newRow[] = { serial, productName, rate, quantity, amount, available };

								receiptTableModel.addRow(newRow);

								amount = Double.parseDouble(quantity) * rate;

								int lastRowIndex = receiptTableModel.getRowCount() - 1;
								receiptTableModel.setValueAt(rate, lastRowIndex, 2);
								receiptTableModel.setValueAt(amount, lastRowIndex, 4);

								int newQuantity = currentQuantity - Integer.parseInt(quantity);
								String updateQuantitySql = "UPDATE `item` SET `item_quantity` = ? WHERE LOWER(`item_name`) = LOWER(?)";
								try (PreparedStatement updateQuantityStatement = con
										.prepareStatement(updateQuantitySql)) {
									updateQuantityStatement.setInt(1, newQuantity);
									updateQuantityStatement.setString(2, productName);
									updateQuantityStatement.executeUpdate();
								}
								int maxSerial = getMaxSerialForDate(formattedDate);

								int newSerial = maxSerial + 1;

								String traproductName = itemNameTextField.getText();
								String traquantity = quantityTextField.getText();
								amount1 = Double.parseDouble(quantity) * rate;
								amount2 = Double.parseDouble(quantity) * tprate;

								String sql = "INSERT INTO `transaction`(`serial`,`date`, `rec_item_name`, `quantity`, `total_mrp`, `total_tp`)"
										+ "VALUES ('" + newSerial + "','" + formattedDate + "','" + traproductName
										+ "','" + traquantity + "','" + amount1 + "','" + amount2 + "')";

								try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

									st.executeUpdate(sql);

								} catch (SQLException e1) {
									JOptionPane.showMessageDialog(null, "Error retrieving data: " + e1.getMessage());
								}

							}
						} else {

							JOptionPane.showMessageDialog(null, "Item not found in the database.");

						}
					}
				} catch (SQLException s) {
					s.printStackTrace();
				}

				itemNameTextField.setText("");
				quantityTextField.setText("");
			}

			private int getMaxSerialForDate(String date) {
				try {
					String getMaxSerialQuery = "SELECT MAX(`serial`) AS maxSerial FROM `transaction` WHERE `date` = ?";
					try (PreparedStatement maxSerialStatement = con.prepareStatement(getMaxSerialQuery)) {
						maxSerialStatement.setString(1, date);

						ResultSet maxSerialResult = maxSerialStatement.executeQuery();
						if (maxSerialResult.next()) {
							return maxSerialResult.getInt("maxSerial");
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				return 0;
			}
		});
		recepitCustomerDataPanel.add(addButton);

		deleteButton = new JButton("Delete");
		deleteButton.setBounds(140, 300, 120, 50);
		deleteButton.setFocusable(false);
		deleteButton.setBackground(new Color(215,59,62));
		deleteButton.setForeground(Color.white);
		deleteButton.setFont(my_font);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (ReciptCurrentDate.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date.");
					return;
				}

				
				try {
					
					java.util.Date selectedDate = ReciptCurrentDate.getDate();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(selectedDate);
					
					int idx = receiptTable.getSelectedRow();
					if (idx == -1) {

						JOptionPane.showMessageDialog(null, "Please select a row to delete!");
						return;
					}

					String productName = receiptTableModel.getValueAt(idx, 1).toString();
					String quantityStr = receiptTableModel.getValueAt(idx, 3).toString();
					int quantity = Integer.parseInt(quantityStr);

					receiptTableModel.removeRow(idx);

					for (int i = 0; i < receiptTableModel.getRowCount(); i++) {
						receiptTableModel.setValueAt(i + 1, i, 0);
					}

					String updateQuantitySql = "UPDATE `item` SET `item_quantity` = `item_quantity` + ? WHERE LOWER(`item_name`) = LOWER(?)";
					try (PreparedStatement updateQuantityStatement = con.prepareStatement(updateQuantitySql)) {
						updateQuantityStatement.setInt(1, quantity);
						updateQuantityStatement.setString(2, productName);
						updateQuantityStatement.executeUpdate();
					}

					int maxSerial = getMaxSerialForDate(formattedDate);

					String deleteRowSql = "DELETE FROM `transaction` WHERE `date` = ? AND `serial` = ?";
					try (PreparedStatement deleteRowStatement = con.prepareStatement(deleteRowSql)) {
						deleteRowStatement.setString(1, formattedDate);
						deleteRowStatement.setInt(2, maxSerial);
						deleteRowStatement.executeUpdate();
					}

					itemNameTextField.setText("");
					quantityTextField.setText("");

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error while deleting the row!");
					e1.printStackTrace();
				}
			}

			private int getMaxSerialForDate(String date) {
				try {
					String getMaxSerialQuery = "SELECT MAX(`serial`) AS maxSerial FROM `transaction` WHERE `date` = ?";
					try (PreparedStatement maxSerialStatement = con.prepareStatement(getMaxSerialQuery)) {
						maxSerialStatement.setString(1, date);

						ResultSet maxSerialResult = maxSerialStatement.executeQuery();
						if (maxSerialResult.next()) {
							return maxSerialResult.getInt("maxSerial");
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				return 0;
			}

		});

		recepitCustomerDataPanel.add(deleteButton);

		updateButton = new JButton("Update");
		updateButton.setBounds(75, 360, 120, 50);
		updateButton.setFocusable(false);
		updateButton.setBackground(new Color(0,127,92));
		updateButton.setForeground(Color.white);
		updateButton.setFont(my_font);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (ReciptCurrentDate.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date.");
					return;
				}

				try {
					
					String productName = itemNameTextField.getText();
					String quantity = quantityTextField.getText();

					
					
					try {

						double rate = 0.0, tprate = 0.0, amount = 0.0, amount1 = 0.0, amount2 = 0.0;
						int serial = receiptTableModel.getRowCount() + 1, available = 0;

						String queryRateSql = "SELECT item_mrp,item_tp, item_quantity FROM `item` WHERE LOWER(item_name) = LOWER(?)";
						try (PreparedStatement queryRateStatement = con.prepareStatement(queryRateSql)) {
							queryRateStatement.setString(1, productName);

							ResultSet resultSet = queryRateStatement.executeQuery();

							if (resultSet.next()) {

								rate = resultSet.getDouble("item_mrp");
								tprate = resultSet.getDouble("item_tp");
								int currentQuantity = resultSet.getInt("item_quantity");

								if (currentQuantity < Integer.parseInt(quantity) || currentQuantity == 0) {

									JOptionPane.showMessageDialog(null, "Not enough stock for " + productName);
									itemNameTextField.setText("");
									quantityTextField.setText("");
									return;

								}
							}
						}
					}catch (SQLException ex1) {
						ex1.printStackTrace();
					}

					java.util.Date selectedDate = ReciptCurrentDate.getDate();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(selectedDate);

					int idx = receiptTable.getSelectedRow();
					if (idx == -1) {
						JOptionPane.showMessageDialog(null, "Please select a row to update!");
						return;
					}

					String item1 = itemNameTextField.getText();
					String quantityStr = quantityTextField.getText();
					int newQuantity = Integer.parseInt(quantityStr);
					int pastQuantity = Integer.parseInt(receiptTableModel.getValueAt(idx, 3).toString());
					double rate = Double.parseDouble(receiptTableModel.getValueAt(idx, 2).toString());
					double newAmount = newQuantity * rate;

					String updateQuantitySql1 = "UPDATE `item` SET `item_quantity` = `item_quantity` + ? WHERE LOWER(`item_name`) = LOWER(?)";
					try (PreparedStatement updateQuantityStatement1 = con.prepareStatement(updateQuantitySql1)) {
						updateQuantityStatement1.setInt(1, pastQuantity);
						updateQuantityStatement1.setString(2, item1);
						updateQuantityStatement1.executeUpdate();
					}

					String updateQuantitySql2 = "UPDATE `item` SET `item_quantity` = `item_quantity` - ? WHERE LOWER(`item_name`) = LOWER(?)";
					try (PreparedStatement updateQuantityStatement2 = con.prepareStatement(updateQuantitySql2)) {
						updateQuantityStatement2.setInt(1, newQuantity);
						updateQuantityStatement2.setString(2, item1);
						updateQuantityStatement2.executeUpdate();
					}

					receiptTableModel.setValueAt(item1, idx, 1);
					receiptTableModel.setValueAt(quantityStr, idx, 3);
					receiptTableModel.setValueAt(newAmount, idx, 4);

					int maxSerial = getMaxSerialForDate(formattedDate);
					double item_tpprice = 0.0;

					String selectTP = "SELECT `item_tp` FROM `item` WHERE LOWER(`item_name`) = LOWER(?)";
					try (PreparedStatement selectTPStatement = con.prepareStatement(selectTP)) {
						selectTPStatement.setString(1, item1);

						ResultSet selectTPResult = selectTPStatement.executeQuery();
						if (selectTPResult.next()) {
							item_tpprice = selectTPResult.getDouble("item_tp");
						}
					}

					double newTotalTpRate = newQuantity * item_tpprice;

					String updateTransactionSql = "UPDATE `transaction` SET `quantity` = ?, `total_mrp` = ?, `total_tp` = ? WHERE `serial` = ? AND `date` = ? AND `rec_item_name` = ?";
					try (PreparedStatement updateTransactionStatement = con.prepareStatement(updateTransactionSql)) {
						updateTransactionStatement.setInt(1, newQuantity);
						updateTransactionStatement.setDouble(2, newAmount);
						updateTransactionStatement.setDouble(3, newTotalTpRate);
						updateTransactionStatement.setInt(4, maxSerial);
						updateTransactionStatement.setString(5, formattedDate);
						updateTransactionStatement.setString(6, item1);
						updateTransactionStatement.executeUpdate();
					}

					itemNameTextField.setText("");
					quantityTextField.setText("");

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error while updating the row!");
					e1.printStackTrace();
				}
			}

			private int getMaxSerialForDate(String date) {
				try {
					String getMaxSerialQuery = "SELECT MAX(`serial`) AS maxSerial FROM `transaction` WHERE `date` = ?";
					try (PreparedStatement maxSerialStatement = con.prepareStatement(getMaxSerialQuery)) {
						maxSerialStatement.setString(1, date);

						ResultSet maxSerialResult = maxSerialStatement.executeQuery();
						if (maxSerialResult.next()) {
							return maxSerialResult.getInt("maxSerial");
						}
					}
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Error while updating the row!");
					e2.printStackTrace();
				}

				return 0;
			}

		});
		recepitCustomerDataPanel.add(updateButton);

		customerDetailsLabel = new JLabel("Customer Details:");
		customerDetailsLabel.setBounds(10, 430, 300, 20);
		customerDetailsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		recepitCustomerDataPanel.add(customerDetailsLabel);

		customerNameLabel = new JLabel("Name");
		customerNameLabel.setBounds(10, 470, 200, 20);
		customerNameLabel.setFont(my_font);
		recepitCustomerDataPanel.add(customerNameLabel);

		customerNameTextField = new JTextField();
		customerNameTextField.setBounds(10, 510, 250, 30);
		recepitCustomerDataPanel.add(customerNameTextField);

		customerAddressLabel = new JLabel("Address");
		customerAddressLabel.setBounds(10, 550, 200, 20);
		customerAddressLabel.setFont(my_font);
		recepitCustomerDataPanel.add(customerAddressLabel);

		customerAddressTextField = new JTextField();
		customerAddressTextField.setBounds(10, 590, 250, 30);
		recepitCustomerDataPanel.add(customerAddressTextField);

		customerMobileNumberLabel = new JLabel("Phone");
		customerMobileNumberLabel.setBounds(10, 630, 200, 20);
		customerMobileNumberLabel.setFont(my_font);
		recepitCustomerDataPanel.add(customerMobileNumberLabel);

		customerPhoneNumberTextField = new JTextField();
		customerPhoneNumberTextField.setBounds(10, 670, 250, 30);
		recepitCustomerDataPanel.add(customerPhoneNumberTextField);

		managementPane.add("Receipt", recieptPanel);

		// // Stock Tab
		stockPanel = new JPanel();
		stockPanel.setBackground(new Color(255,248,231));
		stockPanel.setLayout(null);
		
		stockTablePanel = new JPanel();
		stockTablePanel.setBackground(Color.white);
		stockTablePanel.setBounds(0, 0, 772, 500);
		stockTablePanel.setLayout(null);
		stockPanel.add(stockTablePanel);
		
		
		stockOperationPanel = new JPanel();
		stockOperationPanel.setBackground(Color.black);
		stockOperationPanel.setBounds(10, 800, 500, 200);
		stockPanel.setLayout(null);
		stockPanel.add(stockOperationPanel);
		

		// Drug List area
		stockDrugListLabel = new JLabel("Drug List ");
		stockDrugListLabel.setFont(my_font);
		stockDrugListLabel.setBounds(20, 10, 170, 30);
		stockDrugListLabel.setForeground(Color.black);
		stockTablePanel.add(stockDrugListLabel);

		stockTable = new JTable(stockModel);
		JScrollPane stockScrollPane = new JScrollPane(stockTable);
		stockScrollPane.setBounds(0, 50, 770, 450);
		stockTable.setOpaque(true);
		stockTable.setFillsViewportHeight(true);
		stockTable.setBackground(Color.white);
		stockTable.setBackground(Color.white);
		stockTable.getTableHeader().setBackground(Color.blue);
		stockTable.getTableHeader().setForeground(Color.white);
		
		
		stockTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int idx = stockTable.getSelectedRow();

				String name = stockModel.getValueAt(idx, 0).toString();
				String mrp = stockModel.getValueAt(idx, 1).toString();
				String tp = stockModel.getValueAt(idx, 2).toString();
				String quantity = stockModel.getValueAt(idx, 3).toString();
				String company = stockModel.getValueAt(idx, 4).toString();
				String type = stockModel.getValueAt(idx, 5).toString();

				stockAddProductNameTextField.setText(name);
				stockAddProductMRPTextField.setText(mrp);
				stockAddProductTPTextField.setText(tp);
				stockAddQuantityTextField.setText(quantity);
				stockAddCompanyTextField.setText(company);
				stockTypeTextField.setText(type);
			}
		});
		stockTablePanel.add(stockScrollPane);

		stockInformationPanel = new JPanel();
		stockInformationPanel.setBounds(773, 0, 310, 800);
		stockInformationPanel.setBackground(new Color(192,153,153));
		stockInformationPanel.setLayout(null);
		stockPanel.add(stockInformationPanel);

		// Search area
		stockSearchLabel = new JLabel("Search Here");
		stockSearchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		stockSearchLabel.setBounds(20, 50, 170, 30);
		stockSearchLabel.setForeground(Color.black);
		stockInformationPanel.add(stockSearchLabel);

		stockCompanyNameLabel = new JLabel("Company Name ");
		stockCompanyNameLabel.setFont(my_font);
		stockCompanyNameLabel.setBounds(20, 90, 170, 30);
		stockCompanyNameLabel.setForeground(Color.black);
		stockInformationPanel.add(stockCompanyNameLabel);

		stockSearchCompanyNameTextField = new JTextField();
		stockSearchCompanyNameTextField.setFont(my_font);
		stockSearchCompanyNameTextField.setBounds(20, 130, 270, 30);
		stockSearchCompanyNameTextField.setBackground(Color.white);
		stockInformationPanel.add(stockSearchCompanyNameTextField);

		stockSearchMedicineLNameLabel = new JLabel("Medicine Name");
		stockSearchMedicineLNameLabel.setFont(my_font);
		stockSearchMedicineLNameLabel.setBounds(20, 180, 170, 30);
		stockSearchMedicineLNameLabel.setForeground(Color.black);
		stockInformationPanel.add(stockSearchMedicineLNameLabel);

		stockSearchMedicineTextField = new JTextField();
		stockSearchMedicineTextField.setFont(my_font);
		stockSearchMedicineTextField.setBounds(20, 220, 270, 30);
		stockSearchMedicineTextField.setBackground(Color.white);
		stockInformationPanel.add(stockSearchMedicineTextField);

		// Add area
		stockAddMedicineLabel = new JLabel("Stock Management");
		stockAddMedicineLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		stockAddMedicineLabel.setBounds(20, 280, 250, 40);
		stockAddMedicineLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddMedicineLabel);

		stockAddProductNameLabel = new JLabel("Name");
		stockAddProductNameLabel.setFont(my_font);
		stockAddProductNameLabel.setBounds(20, 330, 170, 30);
		stockAddProductNameLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddProductNameLabel);

		stockAddProductNameTextField = new JTextField();
		stockAddProductNameTextField.setFont(my_font);
		stockAddProductNameTextField.setBounds(20, 370, 270, 30);
		stockAddProductNameTextField.setBackground(Color.white);
		stockInformationPanel.add(stockAddProductNameTextField);

		stockAddProductMRPLabel = new JLabel("MRP");
		stockAddProductMRPLabel.setFont(my_font);
		stockAddProductMRPLabel.setBounds(20, 410, 170, 30);
		stockAddProductMRPLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddProductMRPLabel);

		stockAddProductMRPTextField = new JTextField();
		stockAddProductMRPTextField.setFont(my_font);
		stockAddProductMRPTextField.setBounds(20, 450, 125, 30);
		stockAddProductMRPTextField.setBackground(Color.white);
		stockInformationPanel.add(stockAddProductMRPTextField);

		stockAddProductTPLabel = new JLabel("TP");
		stockAddProductTPLabel.setFont(my_font);
		stockAddProductTPLabel.setBounds(165, 410, 170, 30);
		stockAddProductTPLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddProductTPLabel);

		stockAddProductTPTextField = new JTextField();
		stockAddProductTPTextField.setFont(my_font);
		stockAddProductTPTextField.setBounds(155, 450, 135, 30);
		stockAddProductTPTextField.setBackground(Color.white);
		stockInformationPanel.add(stockAddProductTPTextField);

		stockAddQuantityLabel = new JLabel("Quantity");
		stockAddQuantityLabel.setFont(my_font);
		stockAddQuantityLabel.setBounds(20, 490, 170, 30);
		stockAddQuantityLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddQuantityLabel);

		stockAddQuantityTextField = new JTextField();
		stockAddQuantityTextField.setFont(my_font);
		stockAddQuantityTextField.setBounds(20, 530, 125, 30);
		stockAddQuantityTextField.setBackground(Color.white);
		stockInformationPanel.add(stockAddQuantityTextField);

		stockTypeLabel = new JLabel("Type");
		stockTypeLabel.setFont(my_font);
		stockTypeLabel.setBounds(165, 490, 170, 30);
		stockTypeLabel.setForeground(Color.black);
		stockInformationPanel.add(stockTypeLabel);

		stockTypeTextField = new JTextField();
		stockTypeTextField.setFont(my_font);
		stockTypeTextField.setBounds(155, 530, 135, 30);
		stockTypeTextField.setBackground(Color.white);
		stockInformationPanel.add(stockTypeTextField);

		stockAddCompanyLabel = new JLabel("Company");
		stockAddCompanyLabel.setFont(my_font);
		stockAddCompanyLabel.setBounds(20, 570, 170, 30);
		stockAddCompanyLabel.setForeground(Color.black);
		stockInformationPanel.add(stockAddCompanyLabel);

		stockAddCompanyTextField = new JTextField();
		stockAddCompanyTextField.setFont(my_font);
		stockAddCompanyTextField.setBounds(20, 610, 270, 30);
		stockAddCompanyTextField.setBackground(Color.white);
		stockInformationPanel.add(stockAddCompanyTextField);
		
		
		stockClearButton = new JButton("Clear");
		stockClearButton.setFont(my_font);
		stockClearButton.setFocusable(false);
		stockClearButton.setBounds(100, 680, 130, 40);
		stockClearButton.setBackground(new Color(128,0,0));
		stockClearButton.setForeground(Color.white);
		stockClearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				while (stockModel.getRowCount() != 0) {
					stockModel.removeRow(0);
				}
				
				stockAddProductNameTextField.setText("");
				stockAddProductTPTextField.setText("");
				stockAddProductMRPTextField.setText("");
				stockAddQuantityTextField.setText("");
				stockTypeTextField.setText("");
				stockAddCompanyTextField.setText("");
				stockSearchCompanyNameTextField.setText("");
				stockSearchMedicineTextField.setText("");
			}
		});
		stockInformationPanel.add(stockClearButton);
		
		
	
		stockPrintButton = new JButton("Print");
		stockPrintButton.setFont(my_font);
		stockPrintButton.setFocusable(false);
		stockPrintButton.setBounds(30, 600, 130, 40);
		stockPrintButton.setBackground(new Color(0,0,255));
		stockPrintButton.setForeground(Color.white);
		stockPrintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stockModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "No data found.");
					return;
				}
				MessageFormat header = new MessageFormat("Stock list");
				MessageFormat footer = new MessageFormat("Page{0, number, integer}");
				try {
					stockTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		stockPanel.add(stockPrintButton);

		stockAddButton = new JButton("Add");
		stockAddButton.setFont(my_font);
		stockAddButton.setFocusable(false);
		stockAddButton.setBounds(180, 600, 130, 40);
		stockAddButton.setBackground(new Color(116,195,101));
		stockAddButton.setForeground(Color.white);
		stockAddButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (stockAddFlag == false) {
					while (stockModel.getRowCount() != 0) {
						stockModel.removeRow(0);
					}
				}

				if (stockAddFlag == false)
					stockAddFlag = true;

				String stockproductName = stockAddProductNameTextField.getText();
				String stockMRP = stockAddProductMRPTextField.getText();
				String stockTP = stockAddProductTPTextField.getText();
				String stockQuantity = stockAddQuantityTextField.getText();
				String stockCompanyName = stockAddCompanyTextField.getText();
				String stockType = stockTypeTextField.getText();
				if (stockproductName.equals("") || stockAddProductMRPTextField.equals("") || stockQuantity.equals("")
						|| stockCompanyName.equals("") || stockAddProductTPTextField.equals("")
						|| stockTypeTextField.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter data in all the fields.");
					return;
				}
				double stock_Rate = 0.0, stock_Quantity = 0.0;
				int serial = receiptTableModel.getRowCount() + 1, available = 0;

				Object newRow[] = { stockproductName, stockMRP, stockTP, stockQuantity, stockCompanyName, stockType };

				stockModel.addRow(newRow);

				stockAddProductNameTextField.setText("");
				stockAddProductMRPTextField.setText("");
				stockAddProductTPTextField.setText("");
				stockAddQuantityTextField.setText("");
				stockAddCompanyTextField.setText("");
				stockTypeTextField.setText("");

				try {

					ResultSet check = st.executeQuery("SELECT * FROM `item` WHERE LOWER(item_name) = LOWER('"
							+ stockproductName + "') and LOWER(item_company) = LOWER('" + stockCompanyName + "')");
					int checkCnt = 0;
					while (check.next()) {
						checkCnt++;
					}

					if (checkCnt != 0) {
						JOptionPane.showMessageDialog(null, "Item already exists");
						stockModel.removeRow(stockModel.getRowCount() - 1);
						return;
					}

					int id = 0;
					ResultSet rowCount = st.executeQuery("SELECT * FROM item");
					int cnt = 0;
					while (rowCount.next()) {
						cnt++;
					}
					if (cnt == 0) {
						id = 1;
					} else {
						int nextId = 0;
						ResultSet rs = st.executeQuery("SELECT * FROM item");
						int prevMx = -1;
						while (rs.next()) {
							prevMx = Math.max(prevMx, rs.getInt(1));
						}
						id = prevMx + 1;
					}
					String sql = "INSERT INTO `item`(`item_id`, `item_name`, `item_company`, `item_mrp`, `item_tp`, `item_quantity`,`item_type`) "
							+ "VALUES ('" + id + "','" + stockproductName + "','" + stockCompanyName + "','" + stockMRP
							+ "','" + stockTP + "','" + stockQuantity + "','" + stockType + "')";
					int c = st.executeUpdate(sql);
					//System.out.println(cnt);
					//System.out.println(id);
				} catch (SQLException s) {
					s.printStackTrace();
				}

			}
		});
		stockPanel.add(stockAddButton);

		stockDeleteButton = new JButton("Delete");
		stockDeleteButton.setFont(my_font);
		stockDeleteButton.setFocusable(false);
		stockDeleteButton.setBounds(330, 600, 130, 40);
		stockDeleteButton.setBackground(new Color(215,59,62));
		stockDeleteButton.setForeground(Color.white);
		stockDeleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int stockDeleteIndex = stockTable.getSelectedRow();

					String name = stockAddProductNameTextField.getText();
					String mrp = stockAddProductMRPTextField.getText();
					String tp = stockAddProductTPTextField.getText();
					String quantity = stockAddQuantityTextField.getText();
					String company = stockAddCompanyTextField.getText();
					String type = stockTypeTextField.getText();
					if (name.equals("") || company.equals("")) {
						JOptionPane.showMessageDialog(null, "Please add both 'Name' and 'Company'");
						return;
					}

					String sql = "DELETE FROM `item` WHERE LOWER(item_name) = LOWER('" + name
							+ "') and LOWER(item_company) = LOWER('" + company + "')";

					st.executeUpdate(sql);

					String stockName = stockModel.getValueAt(stockDeleteIndex, 0).toString();
					String stockMRP = stockModel.getValueAt(stockDeleteIndex, 1).toString();
					String stockTP = stockModel.getValueAt(stockDeleteIndex, 2).toString();
					String stockQuantity = stockModel.getValueAt(stockDeleteIndex, 3).toString();
					String stockCompany = stockModel.getValueAt(stockDeleteIndex, 4).toString();
					String stockTypes = stockModel.getValueAt(stockDeleteIndex, 5).toString();

					stockModel.removeRow(stockTable.getSelectedRow());
				} catch (Exception e1) {
					if (stockModel.getRowCount() == 0)
						JOptionPane.showMessageDialog(null, "No data found!");
					else
						JOptionPane.showMessageDialog(null, "Please select a row!");
				}
				stockAddProductNameTextField.setText("");
				stockAddProductMRPTextField.setText("");
				stockAddProductTPTextField.setText("");
				stockAddQuantityTextField.setText("");
				stockAddCompanyTextField.setText("");
				stockTypeTextField.setText("");
			}

		});

		stockPanel.add(stockDeleteButton);

		stockUpdateButton = new JButton("Update");
		stockUpdateButton.setFont(my_font);
		stockUpdateButton.setFocusable(false);
		stockUpdateButton.setBounds(480, 600, 130, 40);
		stockUpdateButton.setBackground(new Color(0,127,92));
		stockUpdateButton.setForeground(Color.white);
		stockUpdateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String stockproductName = stockAddProductNameTextField.getText();
				String stockMRP = stockAddProductMRPTextField.getText();
				String stockTP = stockAddProductTPTextField.getText();
				String stockQuantity = stockAddQuantityTextField.getText();
				String stockCompanyName = stockAddCompanyTextField.getText();
				String stockType = stockTypeTextField.getText();

				if (stockproductName.equals("") || stockMRP.equals("") || stockQuantity.equals("")
						|| stockCompanyName.equals("") || stockTP.equals("") || stockType.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter data in all the fields.");
					return;
				}
				double stock_Rate = 0.0, stock_Quantity = 0.0;

				Object newRow[] = { stockproductName, stockMRP, stockTP, stockQuantity, stockCompanyName, stockType };

				int idx = stockTable.getSelectedRow();

				stockModel.setValueAt(stockproductName, idx, 0);
				stockModel.setValueAt(stockMRP, idx, 1);
				stockModel.setValueAt(stockTP, idx, 2);
				stockModel.setValueAt(stockQuantity, idx, 3);
				stockModel.setValueAt(stockCompanyName, idx, 4);
				stockModel.setValueAt(stockType, idx, 5);

				try {
					String checkItemQuery = "SELECT item_id FROM `item` WHERE LOWER(item_name) = LOWER(?)";
					try (PreparedStatement checkItemStatement = con.prepareStatement(checkItemQuery)) {
						checkItemStatement.setString(1, stockproductName);

						ResultSet resultSet = checkItemStatement.executeQuery();

						if (resultSet.next()) {

							int itemID = resultSet.getInt("item_id");

							String updateSql = "UPDATE `item` SET `item_name` = ?, `item_company` = ?, `item_mrp` = ?, `item_tp` = ?, `item_quantity` = ?, `item_type` = ? WHERE `item_id` = ?";
							try (PreparedStatement updateStatement = con.prepareStatement(updateSql)) {
								updateStatement.setString(1, stockproductName);
								updateStatement.setString(2, stockCompanyName);
								updateStatement.setString(3, stockMRP);
								updateStatement.setString(4, stockTP);
								updateStatement.setString(5, stockQuantity);
								updateStatement.setString(6, stockType);
								updateStatement.setInt(7, itemID);

								int updatedRows = updateStatement.executeUpdate();

								if (updatedRows > 0) {
									//System.out.println("Update successful!");
									JOptionPane.showMessageDialog(null, "Update successful!");
									stockAddProductNameTextField.setText("");
									stockAddProductMRPTextField.setText("");
									stockAddProductTPTextField.setText("");
									stockAddQuantityTextField.setText("");
									stockAddCompanyTextField.setText("");
									stockTypeTextField.setText("");
									return;
								} else {
									//System.out.println("Update failed. No rows updated.");
									JOptionPane.showMessageDialog(null, "Update failed. No rows updated.");
									stockAddProductNameTextField.setText("");
									stockAddProductMRPTextField.setText("");
									stockAddProductTPTextField.setText("");
									stockAddQuantityTextField.setText("");
									stockAddCompanyTextField.setText("");
									stockTypeTextField.setText("");
									return;
								}
							}
						} else {
							//System.out.println("Item not found for " + stockproductName);
							JOptionPane.showMessageDialog(null, "Item not found for " + stockproductName);

							stockAddProductNameTextField.setText("");
							stockAddProductMRPTextField.setText("");
							stockAddProductTPTextField.setText("");
							stockAddQuantityTextField.setText("");
							stockAddCompanyTextField.setText("");
							stockTypeTextField.setText("");

							return;
						}
					}
				} catch (SQLException s) {
					s.printStackTrace();
				}
			}
		});
		stockPanel.add(stockUpdateButton);

		stockSearchButton = new JButton("Search");
		stockSearchButton.setFont(my_font);
		stockSearchButton.setFocusable(false);
		stockSearchButton.setBounds(630, 600, 130, 40);
		stockSearchButton.setBackground(new Color(255,36,0));
		stockSearchButton.setForeground(Color.white);
		stockSearchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				while (stockModel.getRowCount() != 0) {
					stockModel.removeRow(0);
				}

				String companyName = stockSearchCompanyNameTextField.getText();
				String itemName = stockSearchMedicineTextField.getText();

				try {
					String sql = "";
					if (itemName.equals("") && !companyName.equals("")) {
						sql = "SELECT * FROM `item` WHERE LOWER(item_company) = LOWER('" + companyName + "')";
					} else if (!itemName.equals("") && companyName.equals("")) {
						sql = "SELECT * FROM `item` WHERE LOWER(item_name) = LOWER('" + itemName + "')";
					} else if (!itemName.equals("") && !companyName.equals("")) {
						sql = "SELECT * FROM item WHERE LOWER(item_name) = LOWER('" + itemName
								+ "') and LOWER(item_company) = LOWER('" + companyName + "')";
					} else {
						JOptionPane.showMessageDialog(null, "Please enter data");
						return;
					}
					ResultSet rs = st.executeQuery(sql);
					int idx = 0;
					while (rs.next()) {
						String dbItemId = String.valueOf(rs.getInt(1));
						String dbItemName = rs.getString(2);
						String dbcompanyName = rs.getString(3);
						String dbItemMRP = String.valueOf(rs.getDouble(4));
						String dbItemTP = String.valueOf(rs.getDouble(5));
						String dbItemQuantity = String.valueOf(rs.getInt(6));
						String dbItemType = String.valueOf(rs.getString(7));

						//System.out.println(dbItemId);
						//System.out.println(dbcompanyName);

						Object newRow[] = { dbItemName, dbItemMRP, dbItemTP, dbItemQuantity, dbcompanyName,
								dbItemType };

						stockModel.addRow(newRow);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		stockPanel.add(stockSearchButton);

		managementPane.add("Stock", stockPanel);

		// // Orders Tab
		ordersPanel = new JPanel();
		ordersPanel.setBackground(new Color(255,228,196));
		ordersPanel.setLayout(null);
		ordersTable = new JTable(OrdersModel);
		ordersTable.setOpaque(true);
		ordersTable.setFillsViewportHeight(true);
		ordersTable.setBackground(Color.white);
		ordersTable.setBounds(20, 110, 962, 220);
		ordersTable.getTableHeader().setBackground(Color.blue);
		ordersTable.getTableHeader().setForeground(Color.white);
		ordersTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int idx = ordersTable.getSelectedRow();

				String product = OrdersModel.getValueAt(idx, 2).toString();
				String quantity = OrdersModel.getValueAt(idx, 4).toString();
				String company = OrdersModel.getValueAt(idx, 3).toString();

				ordersProductNameTextfield.setText(product);
				ordersQuantityTextfield.setText(quantity);
				ordersProductCompanytTextfield.setText(company);

			}

		});
		JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
		ordersScrollPane.setBounds(3, 0, 1077, 460);
		ordersPanel.add(ordersScrollPane);

		// Date
		ordersDateJlabel = new JLabel("Date");
		ordersDateJlabel.setFont(my_font);
		ordersDateJlabel.setBounds(10, 500, 170, 30);
		ordersDateJlabel.setForeground(Color.black);
		ordersPanel.add(ordersDateJlabel);

		// Date Choosser

		currentDate = new JDateChooser();
		currentDate.setBounds(150, 500, 200, 30);
		ordersPanel.add(currentDate);

		// Product Name
		ordersProductName = new JLabel("Product Name :");
		ordersProductName.setFont(my_font);
		ordersProductName.setBounds(10, 550, 170, 30);
		ordersProductName.setForeground(Color.black);
		ordersPanel.add(ordersProductName);

		ordersProductNameTextfield = new JTextField();
		ordersProductNameTextfield.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ordersProductNameTextfield.setBounds(150, 550, 200, 30);
		ordersProductNameTextfield.setBackground(Color.white);
		ordersPanel.add(ordersProductNameTextfield);

		// Quantity
		ordersQuantity = new JLabel("Quantity :");
		ordersQuantity.setFont(my_font);
		ordersQuantity.setBounds(360, 550, 170, 30);
		ordersQuantity.setForeground(Color.black);
		ordersPanel.add(ordersQuantity);

		ordersQuantityTextfield = new JTextField();
		ordersQuantityTextfield.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ordersQuantityTextfield.setBounds(460, 550, 200, 30);
		ordersQuantityTextfield.setBackground(Color.white);
		ordersPanel.add(ordersQuantityTextfield);

		// Company Name
		ordersProductCompany = new JLabel("Product Company :");
		ordersProductCompany.setFont(my_font);
		ordersProductCompany.setBounds(680, 550, 170, 30);
		ordersProductCompany.setForeground(Color.black);
		ordersPanel.add(ordersProductCompany);

		ordersProductCompanytTextfield = new JTextField();
		ordersProductCompanytTextfield.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ordersProductCompanytTextfield.setBounds(850, 550, 220, 30);
		ordersProductCompanytTextfield.setBackground(Color.white);
		ordersPanel.add(ordersProductCompanytTextfield);

		// Buttons
		ordersAddButton = new JButton("Add");
		ordersAddButton.setFont(my_font);
		ordersAddButton.setFocusable(false);
		ordersAddButton.setBounds(10, 610, 220, 40);
		ordersAddButton.setBackground(new Color(116,195,101));
		ordersAddButton.setForeground(Color.white);
		ordersAddButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String productName = ordersProductNameTextfield.getText();
					String quantity = ordersQuantityTextfield.getText();
					String companyName = ordersProductCompanytTextfield.getText();

					java.util.Date selectedDate = currentDate.getDate();

					if (selectedDate == null || productName.equals("") || quantity.equals("")
							|| companyName.equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter data in all the fields.");
						return;
					}

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(selectedDate);

					int maxSerial = getMaxSerialForDate(formattedDate);

					int newSerial = maxSerial + 1;

					Object newRow[] = { formattedDate, newSerial, productName, companyName, quantity };

					OrdersModel.addRow(newRow);

					ResultSet check = st.executeQuery("SELECT * FROM `orders` WHERE `orders_name` = '" + productName
							+ "' AND `orders_company` = '" + companyName + "' AND `orders_date` = '" + formattedDate
							+ "'");
					if (check.next()) {
						JOptionPane.showMessageDialog(null, "Order already exists for this item on the selected date.");

						OrdersModel.removeRow(OrdersModel.getRowCount() - 1);
						return;
					}

					String sql = "INSERT INTO `orders`(`orders_date`, `orders_serial`, `orders_name`, `orders_company`, `orders_quantity`)"
							+ "VALUES (?, ?, ?, ?, ?)";
					try (PreparedStatement insertStatement = con.prepareStatement(sql)) {
						insertStatement.setString(1, formattedDate);
						insertStatement.setInt(2, newSerial);
						insertStatement.setString(3, productName);
						insertStatement.setString(4, companyName);
						insertStatement.setString(5, quantity);

						int rowsInserted = insertStatement.executeUpdate();

						if (rowsInserted > 0) {
							//System.out.println("Order added successfully!");
						} else {
							//System.out.println("Failed to add order.");
						}
					}
				} catch (SQLException s) {
					s.printStackTrace();
				}
			}

			private int getMaxSerialForDate(String date) {
				try {
					String getMaxSerialQuery = "SELECT MAX(`orders_serial`) AS maxSerial FROM `orders` WHERE `orders_date` = ?";
					try (PreparedStatement maxSerialStatement = con.prepareStatement(getMaxSerialQuery)) {
						maxSerialStatement.setString(1, date);

						ResultSet maxSerialResult = maxSerialStatement.executeQuery();
						if (maxSerialResult.next()) {
							return maxSerialResult.getInt("maxSerial");
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				return 0;
			}
		});

		// ordersPanel.add(ordersAddButton);

		ordersPanel.add(ordersAddButton);

		ordersDeleteButton = new JButton("Delete");
		ordersDeleteButton.setFont(my_font);
		ordersDeleteButton.setFocusable(false);
		ordersDeleteButton.setBounds(290, 610, 220, 40);
		ordersDeleteButton.setBackground(new Color(215,59,62));
		ordersDeleteButton.setForeground(Color.white);
		ordersDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					int orderIndex = ordersTable.getSelectedRow();
					if (orderIndex == -1) {
						JOptionPane.showMessageDialog(null, "Please select a row!");
						return;
					}

					String productName = OrdersModel.getValueAt(orderIndex, 2).toString();
					String companyName = OrdersModel.getValueAt(orderIndex, 3).toString();
					String orderDate = OrdersModel.getValueAt(orderIndex, 0).toString();

					//System.out.println("Product Name: " + productName);
					//System.out.println("Company Name: " + companyName);
					//System.out.println("Order Date: " + orderDate);

					String sql = "DELETE FROM `orders` WHERE LOWER(orders_name) = LOWER(?) AND LOWER(orders_company) = LOWER(?) AND orders_date = ?";
					//System.out.println("SQL Statement: " + sql);

					try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
						preparedStatement.setString(1, productName);
						preparedStatement.setString(2, companyName);
						preparedStatement.setString(3, orderDate);

						int affRows = preparedStatement.executeUpdate();

						if (affRows > 0) {
							OrdersModel.removeRow(orderIndex);

							for (int i = 0; i < OrdersModel.getRowCount(); i++) {
								OrdersModel.setValueAt(i + 1, i, 0);
							}
							//System.out.println("Deletion successful.");
						} else {
							JOptionPane.showMessageDialog(null, "Deletion from the database failed!");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});

		ordersPanel.add(ordersDeleteButton);

		ordersSaveButton = new JButton("Search");
		ordersSaveButton.setFont(my_font);
		ordersSaveButton.setFocusable(false);
		ordersSaveButton.setBounds(560, 610, 220, 40);
		ordersSaveButton.setBackground(new Color(255,36,0));
		ordersSaveButton.setForeground(Color.white);
		ordersSaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (currentDate.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date.");
					return;
				}
		
				else {
					java.util.Date selectedDate = currentDate.getDate();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(selectedDate);
					//System.out.println(formattedDate);

					String sql = "SELECT * FROM `orders` WHERE DATE(orders_date) = ?";

					try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
						preparedStatement.setString(1, formattedDate);

						try (ResultSet resultSet = preparedStatement.executeQuery()) {

							while (OrdersModel.getRowCount() > 0) {
								OrdersModel.removeRow(0);
							}

							while (resultSet.next()) {
								Object[] rowData = { resultSet.getString("orders_date"),
										resultSet.getInt("orders_serial"), resultSet.getString("orders_name"),
										resultSet.getString("orders_company"), resultSet.getInt("orders_quantity")

								};
								OrdersModel.addRow(rowData);
							}
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error retrieving data: " + e1.getMessage());
					}
				}

			}
		});
		ordersPanel.add(ordersSaveButton);

		ordersPrintButton = new JButton("Print");
		ordersPrintButton.setFont(my_font);
		ordersPrintButton.setFocusable(false);
		ordersPrintButton.setBounds(830, 610, 240, 40);
		ordersPrintButton.setBackground(new Color(0,0,255));
		ordersPrintButton.setForeground(Color.white);
		ordersPrintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (OrdersModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "No data found.");
					return;
				}
				MessageFormat header = new MessageFormat("Order list");
				MessageFormat footer = new MessageFormat("Page{0, number, integer}");
				try {
					ordersTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ordersPanel.add(ordersPrintButton);

		ordersUpdateButton = new JButton("Update");
		ordersUpdateButton.setFont(my_font);
		ordersUpdateButton.setFocusable(false);
		ordersUpdateButton.setBounds(290, 680, 220, 40);
		ordersUpdateButton.setBackground(new Color(0,127,92));
		ordersUpdateButton.setForeground(Color.white);
		ordersUpdateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (OrdersModel.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "No data found.");
					return;
				}

				try {
					String name = ordersProductNameTextfield.getText();
					String company = ordersProductCompanytTextfield.getText();
					String quantity = ordersQuantityTextfield.getText();
					int idx = ordersTable.getSelectedRow();

					String date = OrdersModel.getValueAt(idx, 0).toString();

					String updateQuery = "UPDATE orders SET orders_name=?, orders_company=?, orders_quantity=? WHERE orders_date=? AND orders_name=?";

					try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
						preparedStatement.setString(1, name);
						preparedStatement.setString(2, company);
						preparedStatement.setString(3, quantity);
						preparedStatement.setString(4, date);
						preparedStatement.setString(5, name);

						int rowsUpdated = preparedStatement.executeUpdate();

						if (rowsUpdated > 0) {
							JOptionPane.showMessageDialog(null, "Order updated successfully");

							OrdersModel.setValueAt(name, idx, 2);
							OrdersModel.setValueAt(company, idx, 3);
							OrdersModel.setValueAt(quantity, idx, 4);
						} else {
							JOptionPane.showMessageDialog(null,
									"Failed to update order. Please check your input and try again.");
						}
					}

					ordersProductNameTextfield.setText("");
					ordersProductCompanytTextfield.setText("");
					ordersQuantityTextfield.setText("");

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
		ordersPanel.add(ordersUpdateButton);

		ordersClearButton = new JButton("Clear");
		ordersClearButton.setFont(my_font);
		ordersClearButton.setFocusable(false);
		ordersClearButton.setBounds(560, 680, 220, 40);
		ordersClearButton.setBackground(new Color(128,0,0));
		ordersClearButton.setForeground(Color.white);
		ordersClearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				while (OrdersModel.getRowCount() != 0) {
					OrdersModel.removeRow(0);
				}

				ordersProductNameTextfield.setText("");
				ordersProductCompanytTextfield.setText("");
				ordersQuantityTextfield.setText("");
			}
		});
		ordersPanel.add(ordersClearButton);

		managementPane.add("Orders", ordersPanel);

		// Suggestion Tab

		suggestionPanel = new JPanel();
		suggestionPanel.setLayout(null);
		suggestionPanel.setBounds(0, 0, 800, 500);
		suggestionPanel.setBackground(Color.white);

		suggestionDrugListLabel = new JLabel("Drug List ");
		suggestionDrugListLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		suggestionDrugListLabel.setBounds(20, 10, 170, 30);
		suggestionDrugListLabel.setForeground(Color.black);
		suggestionPanel.add(suggestionDrugListLabel);

		suggestionTable = new JTable(suggestionModel);
		suggestionTable.setOpaque(true);
		suggestionTable.setFillsViewportHeight(true);
		suggestionTable.setBackground(Color.white);

		suggestionTable.setBounds(20, 110, 962, 220);
		suggestionTable.getTableHeader().setBackground(Color.blue);
		suggestionTable.getTableHeader().setForeground(Color.white);
		JScrollPane suggestionScrollPane = new JScrollPane(suggestionTable);
		suggestionScrollPane.setBounds(20, 50, 750, 680);
		suggestionPanel.add(suggestionScrollPane);

		suggestionInformationPanel = new JPanel();
		suggestionInformationPanel.setBounds(773, 50, 305, 681);
		suggestionInformationPanel.setBackground(new Color(192,153,153));
		suggestionInformationPanel.setLayout(null);
		suggestionPanel.add(suggestionInformationPanel);

		suggestionSearchLabel = new JLabel("Search Here");
		suggestionSearchLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		suggestionSearchLabel.setBounds(10, 10, 170, 30);
		suggestionSearchLabel.setForeground(Color.black);
		suggestionInformationPanel.add(suggestionSearchLabel);

		suggestionCompanyNameLabel = new JLabel("Company Name ");
		suggestionCompanyNameLabel.setFont(my_font);
		suggestionCompanyNameLabel.setBounds(10, 50, 170, 30);
		suggestionCompanyNameLabel.setForeground(Color.black);
		suggestionInformationPanel.add(suggestionCompanyNameLabel);

		suggestionCompanyNameTextField = new JTextField();
		suggestionCompanyNameTextField.setFont(my_font);
		suggestionCompanyNameTextField.setBounds(10, 90, 270, 30);
		suggestionCompanyNameTextField.setBackground(Color.white);
		suggestionInformationPanel.add(suggestionCompanyNameTextField);

		suggestionSearchTypeLNameLabel = new JLabel("Type");
		suggestionSearchTypeLNameLabel.setFont(my_font);
		suggestionSearchTypeLNameLabel.setBounds(10, 130, 170, 30);
		suggestionSearchTypeLNameLabel.setForeground(Color.black);
		suggestionInformationPanel.add(suggestionSearchTypeLNameLabel);

		suggestionSearchTypeLNameTextField = new JTextField();
		suggestionSearchTypeLNameTextField.setFont(my_font);
		suggestionSearchTypeLNameTextField.setBounds(10, 170, 270, 30);
		suggestionSearchTypeLNameTextField.setBackground(Color.white);
		suggestionInformationPanel.add(suggestionSearchTypeLNameTextField);

		suggestionSearchButton = new JButton("Search");
		suggestionSearchButton.setFont(my_font);
		suggestionSearchButton.setFocusable(false);
		suggestionSearchButton.setBounds(70, 220, 140, 40);
		suggestionSearchButton.setBackground(new Color(255,36,0));
		suggestionSearchButton.setForeground(Color.white);
		suggestionSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String companyName = suggestionCompanyNameTextField.getText();
				String itemName = suggestionSearchTypeLNameTextField.getText();

				try {
					String sql = "";
					if (itemName.equals("") && !companyName.equals("")) {
						sql = "SELECT * FROM `item` WHERE LOWER(item_company) = LOWER('" + companyName + "')";
					} else if (!itemName.equals("") && companyName.equals("")) {
						sql = "SELECT * FROM `item` WHERE LOWER(item_type) = LOWER('" + itemName + "')";
					} else if (!itemName.equals("") && !companyName.equals("")) {
						sql = "SELECT * FROM item WHERE LOWER(item_type) = LOWER('" + itemName
								+ "') and LOWER(item_company) = LOWER('" + companyName + "')";
					} else {
						JOptionPane.showMessageDialog(null, "Please enter data");
						return;
					}

					ResultSet rs = st.executeQuery(sql);

					while (suggestionModel.getRowCount() > 0) {
						suggestionModel.removeRow(0);
					}

					while (rs.next()) {
						String dbItemId = String.valueOf(rs.getInt(1));
						String dbItemName = rs.getString(2);
						String dbcompanyName = rs.getString(3);
						String dbItemMRP = String.valueOf(rs.getDouble(4));
						String dbItemTP = String.valueOf(rs.getDouble(5));
						String dbItemQuantity = String.valueOf(rs.getInt(6));
						String dbItemType = String.valueOf(rs.getString(7));

						//System.out.println(dbItemId);
						//System.out.println(dbcompanyName);

						Object newRow[] = { dbItemName, dbItemMRP, dbItemTP, dbItemQuantity, dbcompanyName,
								dbItemType };

						suggestionModel.addRow(newRow);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		suggestionInformationPanel.add(suggestionSearchButton);

		suggestionLogoutButton = new JButton("Logout");
		suggestionLogoutButton.setFont(my_font);
		suggestionLogoutButton.setFocusable(false);
		suggestionLogoutButton.setBounds(70, 310, 140, 40);
		suggestionLogoutButton.setBackground(new Color(86,3,25));
		suggestionLogoutButton.setForeground(Color.white);
		suggestionInformationPanel.add(suggestionLogoutButton);
		suggestionLogoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();
			}
		});

		managementPane.add("Suggestion", suggestionPanel);

		setVisible(true);

	}
}
