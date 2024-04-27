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

public class AdminView extends JFrame {
	JTabbedPane managementPane;
	Font my_font = new Font("Times New Roman", Font.PLAIN, 20);

	// Receipt tab variables
	JPanel recieptPanel, stockPanel, historyPanel, ordersPanel, suggestionPanel;
	JPanel recepitTablePanel, recepitCustomerDataPanel, receiptPayPanel;
	JLabel discountLabel, totalAmountLabel, cashPaidLabel, returnLabel, counterNumberLabel, discountTotalAmountLabel;
	JLabel customerNameLabel, customerAddressLabel, customerMobileNumberLabel, customerDetailsLabel;
	JLabel itemNameLabel, quantityLabel, companyNameLabel;
	JButton updateButton, printButton, clearButton, addButton, deleteButton, applyDiscountButton, calculatReturnButton;
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
	JPanel stockOperationPanel, stockTablePanel, stockInformationPanel;
	JButton stockClearButton,stockLogout,stockAddButton, stockSearchButton, stockPrintButton, stockDeleteButton, stockUpdateButton;
	JTextField stockAddProductNameTextField, stockAddProductTPTextField, stockAddProductMRPTextField,
			stockAddQuantityTextField, stockAddCompanyTextField;
	boolean stockAddFlag = false, stockSearchFlag = false, stockUpdateFlag = false;
	JLabel stockTypeLabel;
	JTextField stockTypeTextField;

	// History tab variables
	JPanel historyDetailsPanel;
	JLabel historyCustomerReciptDetails,historyTimePeriodLabel, historyCounterNumberLabel, historyTotalItemsLabel, historyTotalQuantityLabel,
			historyTotalDiscountLabel;
	JLabel historyTotalMRPLabel, historyTotalBuyingPriceLabel, historyTotalMarginLabel;
	JLabel historyFromLabel, historyToLabel;
	JTextField historyTimePeriodTextField, historyCounterNumberTextField, historyTotalItemsTextField,
			historyTotalQuantityTextField;
	JTextField historyTotalDiscountTextField, historyTotalMRPTextField, historyTotalBuyingPriceTextField,
			historyTotalMarginTextField;
	JDateChooser historyFrom, historyTo;
	JComboBox<Integer> historycounterNumberComboBox;
	JButton historySearch,historyRecipt,historyClear;
	JTable historyTable;
	Object historyData[][] = {};
	String historyColumnNames[] = { "Date", "Name", "Phone", "Purchase", "Discount","CashPaid","Return" };
	DefaultTableModel historyModel = new DefaultTableModel(historyData, historyColumnNames);

	// Orders tab variables
	JLabel ordersProductName, ordersQuantity, ordersProductCompany, ordersDateJlabel;
	JTextField ordersQuantityTextfield, ordersProductCompanytTextfield, ordersProductNameTextfield;
	JTable ordersTable;
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
	JButton suggestionSearchButton;

	public AdminView() {

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
		managementPane.setBackground(new Color(51, 153, 255)); // tabs color
		managementPane.setBounds(0, 0, 1100, 800);
		managementPane.setForeground(Color.black);
		add(managementPane);

		// // Stock Tab
		stockPanel = new JPanel();
		stockPanel.setBackground(new Color(255, 248, 231));
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
		stockInformationPanel.setBackground(new Color(192, 153, 153));
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
		stockAddProductTPTextField.setBounds(165, 450, 125, 30);
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
		stockTypeTextField.setBounds(165, 530, 125, 30);
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
		stockClearButton.setBounds(160, 680, 130, 40);
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
		stockPrintButton.setBackground(new Color(0, 0, 255));
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
		
		
		stockLogout = new JButton("Logout");
		stockLogout.setFont(my_font);
		stockLogout.setFocusable(false);
		stockLogout.setBounds(20, 680, 130, 40);
		stockLogout.setBackground(new Color(226, 6, 44));
		stockLogout.setForeground(Color.white);
		stockLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginFrame();
			}
		});
		
		stockInformationPanel.add(stockLogout);
		
		
		
		stockPanel.add(stockPrintButton);

		stockAddButton = new JButton("Add");
		stockAddButton.setFont(my_font);
		stockAddButton.setFocusable(false);
		stockAddButton.setBounds(180, 600, 130, 40);
		stockAddButton.setBackground(new Color(116, 195, 101));
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
					// System.out.println(cnt);
					// System.out.println(id);
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
		stockDeleteButton.setBackground(new Color(215, 59, 62));
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
		stockUpdateButton.setBackground(new Color(0, 127, 92));
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
									// System.out.println("Update successful!");
									JOptionPane.showMessageDialog(null, "Update successful!");
									stockAddProductNameTextField.setText("");
									stockAddProductMRPTextField.setText("");
									stockAddProductTPTextField.setText("");
									stockAddQuantityTextField.setText("");
									stockAddCompanyTextField.setText("");
									stockTypeTextField.setText("");
									return;
								} else {
									// System.out.println("Update failed. No rows updated.");
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
							// System.out.println("Item not found for " + stockproductName);
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
		stockSearchButton.setBackground(new Color(255, 36, 0));
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

						// System.out.println(dbItemId);
						// System.out.println(dbcompanyName);

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

		// // History Tab
		historyPanel = new JPanel();
		historyPanel.setBackground(Color.white);
		historyPanel.setLayout(null);

		historyTimePeriodLabel = new JLabel("Time Period");
		historyTimePeriodLabel.setFont(my_font);
		historyTimePeriodLabel.setBounds(20, 20, 170, 30);
		historyTimePeriodLabel.setForeground(Color.black);
		historyPanel.add(historyTimePeriodLabel);

		historyFromLabel = new JLabel("From");
		historyFromLabel.setFont(my_font);
		historyFromLabel.setBounds(20, 60, 170, 30);
		historyFromLabel.setForeground(Color.black);
		historyPanel.add(historyFromLabel);

		historyFrom = new JDateChooser();
		historyFrom.setBounds(120, 60, 170, 30);
		historyPanel.add(historyFrom);

		historyToLabel = new JLabel("To");
		historyToLabel.setFont(my_font);
		historyToLabel.setBounds(360, 60, 170, 30);
		historyToLabel.setForeground(Color.black);
		historyPanel.add(historyToLabel);

		historyTo = new JDateChooser();
		historyTo.setBounds(430, 60, 170, 30);
		historyPanel.add(historyTo);

		historySearch = new JButton("Details");
		historySearch.setFont(my_font);
		historySearch.setFocusable(false);
		historySearch.setBounds(630, 60, 120, 30);
		historySearch.setBackground(new Color(255, 36, 0));
		historySearch.setForeground(Color.white);
		historySearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (historyFrom.getDate() == null || historyTo.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Please select a date.");
					return;
				}

				try {

					java.util.Date fromDate = historyFrom.getDate();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(fromDate);

					if (formattedDate.equals("")) {
						JOptionPane.showMessageDialog(null, "Item not found in the database.");
						return;
					}

					// System.out.println(formattedDate);

					java.util.Date toDate = historyTo.getDate();
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate1 = dateFormat1.format(toDate);

					if (formattedDate1.equals("")) {
						JOptionPane.showMessageDialog(null, "Item not found in the database.");
						return;
					}

					String sql = "SELECT DISTINCT `rec_item_name` FROM `transaction` WHERE date BETWEEN '"
							+ formattedDate + "' AND '" + formattedDate1 + "'";
					ResultSet historyRs = st.executeQuery(sql);
					int itemCount = 0;
					while (historyRs.next()) {
						itemCount++;
					}
					// System.out.println(itemCount);

					int quantity = 0;
					double MRP = 0.0, TP = 0.0;

					String sql1 = "SELECT SUM(`quantity`),SUM(`total_mrp`)," + "SUM(`total_tp`) "
							+ "FROM `transaction` WHERE date BETWEEN '" + formattedDate + "' AND '" + formattedDate1
							+ "'";

					ResultSet historyRS1 = st.executeQuery(sql1);
					while (historyRS1.next()) {
						quantity = historyRS1.getInt(1);
						MRP = historyRS1.getInt(2);
						TP = historyRS1.getInt(3);
					}
					// System.out.println(quantity);
					// System.out.println(MRP);
					// System.out.println(TP);
					historyTotalItemsTextField.setText(String.valueOf(itemCount));
					historyTotalQuantityTextField.setText(String.valueOf(quantity));
					historyTotalMRPTextField.setText(String.valueOf(MRP));
					historyTotalBuyingPriceTextField.setText(String.valueOf(TP));

					double mergine = 0.0, discount = 0.0;

					String sql2 = "SELECT SUM(`customer_discount`) FROM `customer` WHERE customer_date BETWEEN '"
							+ formattedDate + "' AND '" + formattedDate1 + "'";

					ResultSet historyRS2 = st.executeQuery(sql2);
					while (historyRS2.next()) {
						discount = historyRS2.getInt(1);
					}
					// System.out.println(discount);
					historyTotalDiscountTextField.setText(String.valueOf(discount));

					mergine = MRP - TP - discount;
					historyTotalMarginTextField.setText(String.valueOf(mergine));

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		historyPanel.add(historySearch);

		historyDetailsPanel = new JPanel();
		historyDetailsPanel.setBackground(new Color(247,233,142));
		historyDetailsPanel.setBounds(10, 100, 1060, 625);
		historyDetailsPanel.setLayout(null);
		historyPanel.add(historyDetailsPanel);

		// history details panel

		historyTotalItemsLabel = new JLabel("Total Items");
		historyTotalItemsLabel.setFont(my_font);
		historyTotalItemsLabel.setBounds(20, 10, 170, 30);
		historyTotalItemsLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalItemsLabel);

		historyTotalItemsTextField = new JTextField();
		historyTotalItemsTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalItemsTextField.setBounds(20, 50, 200, 30);
		historyTotalItemsTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalItemsTextField);

		historyTotalQuantityLabel = new JLabel("Total Quantity");
		historyTotalQuantityLabel.setFont(my_font);
		historyTotalQuantityLabel.setBounds(20, 100, 170, 30);
		historyTotalQuantityLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalQuantityLabel);

		historyTotalQuantityTextField = new JTextField();
		historyTotalQuantityTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalQuantityTextField.setBounds(20, 150, 200, 30);
		historyTotalQuantityTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalQuantityTextField);

		historyTotalDiscountLabel = new JLabel("Total Discount");
		historyTotalDiscountLabel.setFont(my_font);
		historyTotalDiscountLabel.setBounds(20, 200, 170, 30);
		historyTotalDiscountLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalDiscountLabel);

		historyTotalDiscountTextField = new JTextField();
		historyTotalDiscountTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalDiscountTextField.setBounds(20, 250, 200, 30);
		historyTotalDiscountTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalDiscountTextField);

		historyTotalMRPLabel = new JLabel("Total MRP");
		historyTotalMRPLabel.setFont(my_font);
		historyTotalMRPLabel.setBounds(20, 300, 170, 30);
		historyTotalMRPLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalMRPLabel);

		historyTotalMRPTextField = new JTextField();
		historyTotalMRPTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalMRPTextField.setBounds(20, 350, 200, 30);
		historyTotalMRPTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalMRPTextField);

		historyTotalBuyingPriceLabel = new JLabel("Total TP");
		historyTotalBuyingPriceLabel.setFont(my_font);
		historyTotalBuyingPriceLabel.setBounds(20, 400, 170, 30);
		historyTotalBuyingPriceLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalBuyingPriceLabel);

		historyTotalBuyingPriceTextField = new JTextField();
		historyTotalBuyingPriceTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalBuyingPriceTextField.setBounds(20, 450, 200, 30);
		historyTotalBuyingPriceTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalBuyingPriceTextField);

		historyTotalMarginLabel = new JLabel("Total Margin");
		historyTotalMarginLabel.setFont(my_font);
		historyTotalMarginLabel.setBounds(20, 500, 170, 30);
		historyTotalMarginLabel.setForeground(Color.black);
		historyDetailsPanel.add(historyTotalMarginLabel);

		historyTotalMarginTextField = new JTextField();
		historyTotalMarginTextField.setFont(new Font("Times New Roman", Font.BOLD, 20));
		historyTotalMarginTextField.setBounds(20, 550, 200, 30);
		historyTotalMarginTextField.setBackground(Color.white);
		historyDetailsPanel.add(historyTotalMarginTextField);
		
		historyCustomerReciptDetails = new JLabel("Customer Recipt Details");
		historyCustomerReciptDetails.setFont(my_font);
		historyCustomerReciptDetails.setBounds(300, 10,370, 30);
		historyCustomerReciptDetails.setForeground(Color.black);
		historyDetailsPanel.add(historyCustomerReciptDetails);
		
		
		historyTable = new JTable(historyModel);
		JScrollPane historyScrollPane = new JScrollPane(historyTable);
		historyScrollPane.setBounds(300, 50, 730, 530);
		historyTable.setOpaque(true);
		historyTable.setFillsViewportHeight(true);
		historyTable.setBackground(Color.white);
		historyTable.setBackground(Color.white);
		historyTable.getTableHeader().setBackground(Color.blue);
		historyTable.getTableHeader().setForeground(Color.white);

		historyTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		historyDetailsPanel.add(historyScrollPane);
	
		historyRecipt = new JButton("Recipt Details");
		historyRecipt.setFont(my_font);
		historyRecipt.setFocusable(false);
		historyRecipt.setBounds(770, 60, 170, 30);
		historyRecipt.setBackground(new Color(255, 79,0));
		historyRecipt.setForeground(Color.white);
		historyRecipt.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (historyFrom.getDate() == null || historyTo.getDate() == null) {
		            JOptionPane.showMessageDialog(null, "Please select a date.");
		            return;
		        }

		        try {
		            java.util.Date fromDate = historyFrom.getDate();
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            String formattedDate = dateFormat.format(fromDate);

		            java.util.Date toDate = historyTo.getDate();
		            String formattedDate1 = dateFormat.format(toDate);

		            String sql = "SELECT * FROM customer WHERE customer_date BETWEEN ? AND ?";
		            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
		                preparedStatement.setString(1, formattedDate);
		                preparedStatement.setString(2, formattedDate1);

		                ResultSet resultSet = preparedStatement.executeQuery();

		                historyModel.setRowCount(0);

		                while (resultSet.next()) {
		                    String date = resultSet.getString("customer_date");
		                    String name = resultSet.getString("customer_name");
		                    String phone = resultSet.getString("customer_phone");
		                    double purchase = resultSet.getDouble("customer_tot_purchase");
		                    double discount = resultSet.getDouble("customer_discount");
		                    double cashPaid = resultSet.getDouble("tot_cashpaid");
		                    double returnAmount = resultSet.getDouble("tot_return");

		                    Object[] rowData = { date, name, phone, purchase, discount, cashPaid, returnAmount };
		                    historyModel.addRow(rowData);
		                }
		            }

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		historyPanel.add(historyRecipt);
		
		
		historyClear = new JButton("Clear");
		historyClear.setFont(my_font);
		historyClear.setFocusable(false);
		historyClear.setBounds(960, 60, 110, 30);
		historyClear.setBackground(new Color(203, 65,11));
		historyClear.setForeground(Color.white);
		historyClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				while (historyModel.getRowCount() != 0) {
					historyModel.removeRow(0);
				}
				
				historyTotalItemsTextField.setText("");
				historyTotalQuantityTextField.setText("");
				historyTotalDiscountTextField.setText("");
				historyTotalMRPTextField.setText("");
				historyTotalBuyingPriceTextField.setText("");
				historyTotalMarginTextField.setText("");
				
				
			}
		});
		
		historyPanel.add(historyClear);
	

		managementPane.add("History", historyPanel);

		// // Orders Tab
		ordersPanel = new JPanel();
		ordersPanel.setBackground(new Color(255, 228, 196));
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
		ordersAddButton.setBackground(new Color(116, 195, 101));
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
							// System.out.println("Order added successfully!");
						} else {
							// System.out.println("Failed to add order.");
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
		ordersDeleteButton.setBackground(new Color(215, 59, 62));
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

					// System.out.println("Product Name: " + productName);
					// System.out.println("Company Name: " + companyName);
					// System.out.println("Order Date: " + orderDate);

					String sql = "DELETE FROM `orders` WHERE LOWER(orders_name) = LOWER(?) AND LOWER(orders_company) = LOWER(?) AND orders_date = ?";
					// System.out.println("SQL Statement: " + sql);

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
							// System.out.println("Deletion successful.");
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
		ordersSaveButton.setBackground(new Color(255, 36, 0));
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
					// System.out.println(formattedDate);

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
		ordersPrintButton.setBackground(new Color(0, 0, 255));
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
		ordersUpdateButton.setBackground(new Color(0, 127, 92));
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
		ordersClearButton.setBackground(new Color(128, 0, 0));
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
		setVisible(true);

	}

}
