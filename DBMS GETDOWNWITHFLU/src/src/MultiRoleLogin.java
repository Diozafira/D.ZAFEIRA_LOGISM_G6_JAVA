import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

public class MultiRoleLogin {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/getdownwithflu";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Govo1986";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MultiRoleLogin().createLoginForm());
	}

	public void createLoginForm() {
		JFrame frame = new JFrame("Φόρμα εισόδου");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		frame.add(panel, BorderLayout.CENTER);

		JLabel lblTitle = new JLabel("Σύνδεση");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 10, 20, 10);
		panel.add(lblTitle, gbc);

		JLabel lblUsername = new JLabel("Όνομα χρήστη:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 10, 5, 10);
		panel.add(lblUsername, gbc);

		JTextField txtUsername = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
		panel.add(txtUsername, gbc);

		JLabel lblPassword = new JLabel("Κωδικός:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 10, 5, 10);
		panel.add(lblPassword, gbc);

		JPasswordField txtPassword = new JPasswordField();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
		panel.add(txtPassword, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 10, 10, 10);
		panel.add(buttonPanel, gbc);

		JButton btnLogin = new JButton("Σύνδεση");
		btnLogin.addActionListener(e -> {
			String username = txtUsername.getText();
			String password = new String(txtPassword.getPassword());

			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Συμπληρώστε όλα τα πεδία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			} else {
				authenticateUser(username, password, frame);
			}
		});
		buttonPanel.add(btnLogin);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(e -> System.exit(0));
		buttonPanel.add(btnClose);

		frame.setVisible(true);
	}

	private void authenticateUser(String username, String password, JFrame frame) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement("SELECT role FROM user WHERE username = ? AND password = ?")) {

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String role = resultSet.getString("role");

					SwingUtilities.invokeLater(() -> {
						frame.dispose();
						switch (role.toLowerCase()) {
							case "admin":
								new AdminWindow();
								break;
							case "analyst":
								new AnalystWindow();
								break;
							case "simpleuser":
								new SimpleUserWindow();
								break;
						}
					});
				} else {
					JOptionPane.showMessageDialog(frame, "Εσφαλμένα στοιχεία εισόδου!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(frame, "Σφάλμα σύνδεσης με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}

class AdminWindow extends JFrame {
	JPanel panel;
	JTable diseasesTable, countriesTable, reportsTable;
	JTextField iddiseasesTextField, nameTextField, descriptionTextField, dateTextField;
	JTextField idcountriesTextField, countryNameTextField, continentTextField, populationTextField;
	JTextField idreportTextField, commentTextField, reportDateTextField;
	JButton addDiseasesButton, updateDiseasesButton, deleteDiseasesButton;
	JButton addCountryButton, updateCountryButton, deleteCountryButton;
	JButton addReportButton, updateReportButton, deleteReportButton;

	public AdminWindow() {
		setTitle("Περιβάλλον Διαχείρισης");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		panel = new JPanel(new GridBagLayout());
		add(panel);

		createUIComponents();
		updateDiseasesTable();
		updateCountryTable();
		updateReportsTable();


		setVisible(true);
	}

	private void createUIComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;


		// ** Ασθένειες - Diseases Form **
		JLabel iddiseasesLabel = new JLabel("Κωδικός ασθένειας:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(iddiseasesLabel, gbc);
		iddiseasesTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(iddiseasesTextField, gbc);

		JLabel nameLabel = new JLabel("Όνομα:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(nameLabel, gbc);
		nameTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(nameTextField, gbc);

		JLabel descriptionLabel = new JLabel("Περιγραφή:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(descriptionLabel, gbc);
		descriptionTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(descriptionTextField, gbc);

		JLabel dateLabel = new JLabel("Ημερομηνία:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(dateLabel, gbc);
		dateTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 3;
		panel.add(dateTextField, gbc);

		addDiseasesButton = new JButton("Προσθήκη");
		updateDiseasesButton = new JButton("Επικαιροποίηση");
		deleteDiseasesButton = new JButton("Διαγραφή");

		JPanel diseasesButtonPanel = new JPanel();
		diseasesButtonPanel.add(addDiseasesButton);
		diseasesButtonPanel.add(updateDiseasesButton);
		diseasesButtonPanel.add(deleteDiseasesButton);


		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		panel.add(diseasesButtonPanel, gbc);


		String[] diseasesColumns = {"ID", "Όνομα", "Περιγραφή", "Ημερομηνία"};
		DefaultTableModel diseasesModel = new DefaultTableModel(diseasesColumns, 0);
		diseasesTable = new JTable(diseasesModel);

		int rowHeight = 25;
		diseasesTable.setRowHeight(rowHeight);

		int visibleRows = 5;
		diseasesTable.setPreferredScrollableViewportSize(new Dimension(400, visibleRows * rowHeight));
		diseasesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane diseasesScrollPane = new JScrollPane(diseasesTable);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		panel.add(diseasesScrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;


		diseasesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTableMouseClicked(e);
			}
		});

		addDiseasesButton.addActionListener(e -> jbtnAddNewActionPerformed(e));
		updateDiseasesButton.addActionListener(e -> jbtnUpdateActionPerformed(e));
		deleteDiseasesButton.addActionListener(e -> jbtnDeleteActionPerformed(e));


		// ** Χώρες - Countries Form **
		JLabel idcountriesLabel = new JLabel("Κωδικός χώρας:");
		gbc.gridx = 0;
		gbc.gridy = 6;
		panel.add(idcountriesLabel, gbc);
		idcountriesTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 6;
		panel.add(idcountriesTextField, gbc);

		JLabel countryNameLabel = new JLabel("Χώρα:");
		gbc.gridx = 0;
		gbc.gridy = 7;
		panel.add(countryNameLabel, gbc);
		countryNameTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 7;
		panel.add(countryNameTextField, gbc);

		JLabel continentLabel = new JLabel("Ήπειρος:");
		gbc.gridx = 0;
		gbc.gridy = 8;
		panel.add(continentLabel, gbc);
		continentTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 8;
		panel.add(continentTextField, gbc);

		JLabel populationLabel = new JLabel("Πληθυσμός:");
		gbc.gridx = 0;
		gbc.gridy = 9;
		panel.add(populationLabel, gbc);
		populationTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 9;
		panel.add(populationTextField, gbc);

		addCountryButton = new JButton("Προσθήκη");
		updateCountryButton = new JButton("Επικαιροποίηση");
		deleteCountryButton = new JButton("Διαγραφή");

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		JPanel countryButtonPanel = new JPanel();
		countryButtonPanel.add(addCountryButton);
		countryButtonPanel.add(updateCountryButton);
		countryButtonPanel.add(deleteCountryButton);
		panel.add(countryButtonPanel, gbc);

		String[] countryColumns = {"ID", "Χώρα", "Ήπειρος", "Πληθυσμός"};
		DefaultTableModel countriesModel = new DefaultTableModel(countryColumns, 0);
		countriesTable = new JTable(countriesModel);
		JScrollPane countriesScrollPane = new JScrollPane(countriesTable);

		int rowCountriesHeight = 25;
		countriesTable.setRowHeight(rowCountriesHeight);

		int visibleCountriesRows = 5;
		countriesTable.setPreferredScrollableViewportSize(new Dimension(400, visibleRows * rowCountriesHeight));
		countriesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		panel.add(countriesScrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;


		countriesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTableCountriesMouseClicked(e);
			}
		});

		addCountryButton.addActionListener(e -> jbtnAddCountryNewActionPerformed(e));
		updateCountryButton.addActionListener(e -> jbtnUpdateCountryActionPerformed(e));
		deleteCountryButton.addActionListener(e -> jbtnDeleteCountryActionPerformed(e));

		// ** Αναφορές - Reports Form **
		JLabel idreportLabel = new JLabel("Κωδικός αναφοράς:");
		gbc.gridx = 0;
		gbc.gridy = 12;
		panel.add(idreportLabel, gbc);
		idreportTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 12;
		panel.add(idreportTextField, gbc);

		JLabel commentLabel = new JLabel("Σχόλιο:");
		gbc.gridx = 0;
		gbc.gridy = 13;
		panel.add(commentLabel, gbc);
		commentTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 13;
		panel.add(commentTextField, gbc);

		JLabel reportDateLabel = new JLabel("Ημερομηνία:");
		gbc.gridx = 0;
		gbc.gridy = 14;
		panel.add(reportDateLabel, gbc);
		reportDateTextField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 14;
		panel.add(reportDateTextField, gbc);

		addReportButton = new JButton("Προσθήκη");
		updateReportButton = new JButton("Επικαιροποίηση");
		deleteReportButton = new JButton("Διαγραφή");

		gbc.gridx = 0;
		gbc.gridy = 15;
		gbc.gridwidth = 2;
		JPanel reportButtonPanel = new JPanel();
		reportButtonPanel.add(addReportButton);
		reportButtonPanel.add(updateReportButton);
		reportButtonPanel.add(deleteReportButton);
		panel.add(reportButtonPanel, gbc);

		String[] reportColumns = {"ID", "Σχόλιο", "Ημερομηνία Καταγραφής"};
		DefaultTableModel reportsModel = new DefaultTableModel(reportColumns, 0);
		reportsTable = new JTable(reportsModel);
		JScrollPane reportsScrollPane = new JScrollPane(reportsTable);

		int rowReportsHeight = 25;
		reportsTable.setRowHeight(rowReportsHeight);

		int visibleReportsRows = 5;
		reportsTable.setPreferredScrollableViewportSize(new Dimension(400, visibleRows * rowReportsHeight));
		reportsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		gbc.gridx = 0;
		gbc.gridy = 16; // Correct gridy value
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		panel.add(reportsScrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;


		reportsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTableReportsMouseClicked(e);
			}
		});

		addReportButton.addActionListener(e -> jbtnAddReportNewActionPerformed(e));
		updateReportButton.addActionListener(e -> jbtnUpdateReportActionPerformed(e));
		deleteReportButton.addActionListener(e -> jbtnDeleteReportActionPerformed(e));
	}


	public void updateDiseasesTable() {
		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("SELECT * FROM diseases");
			 ResultSet rs = pst.executeQuery()) {

			DefaultTableModel model = (DefaultTableModel) diseasesTable.getModel();
			model.setRowCount(0);

			while (rs.next()) {
				Vector<Object> columnData = new Vector<>();
				columnData.add(rs.getInt("iddiseases"));
				columnData.add(rs.getString("name"));
				columnData.add(rs.getString("description"));
				columnData.add(rs.getDate("discovery_date"));

				model.addRow(columnData.toArray());
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Σφάλμα: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void jbtnAddNewActionPerformed(java.awt.event.ActionEvent evt) {
		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("INSERT INTO diseases (iddiseases, name, description, discovery_date) VALUES (?, ?, ?, ?)")) {

			try {
				int id = Integer.parseInt(iddiseasesTextField.getText());
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να πάρει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setInt(1, id);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			pst.setString(2, nameTextField.getText());
			pst.setString(3, descriptionTextField.getText());

			try {
				LocalDate discoveryDate = LocalDate.parse(dateTextField.getText());
				pst.setDate(4, java.sql.Date.valueOf(discoveryDate));
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(this, "Η ημερομηνία πρέπει να είναι της μορφής: YYYY-MM-DD.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Η καταχώριση προστέθηκε");
				updateDiseasesTable();
				clearDiseaseFields();
			} else {
				JOptionPane.showMessageDialog(this, "Η καταχώριση απέτυχε");
			}

		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Αποτυχία! " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void clearDiseaseFields() {
		iddiseasesTextField.setText("");
		nameTextField.setText("");
		descriptionTextField.setText("");
		dateTextField.setText("");
	}

	private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedRow = diseasesTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Επιλέξτε τι θέλετε να επικαιροποιήσετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("UPDATE diseases SET name = ?, description = ?, discovery_date = ? WHERE iddiseases = ?")) {

			pst.setString(1, nameTextField.getText());
			pst.setString(2, descriptionTextField.getText());
			try {
				LocalDate discoveryDate = LocalDate.parse(dateTextField.getText());
				pst.setDate(3, java.sql.Date.valueOf(discoveryDate));
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(this, "Η ημερομηνία πρέπει να είναι της μορφής: YYYY-MM-DD.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				int id = Integer.parseInt(iddiseasesTextField.getText());
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να λάβει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setInt(4, id);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Μη έγκυρη τιμή", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Η καταχώριση άλλαξε");
				updateDiseasesTable();
			} else {
				JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για ενημέρωση.");
			}

		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Αποτυχία: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedRow = diseasesTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Επιλέξτε τη γραμμή που θέλετε να διαγράψετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int iddiseases = Integer.parseInt(diseasesTable.getValueAt(selectedRow, 0).toString());
			int deleteItem = JOptionPane.showConfirmDialog(null, "Σίγουρα επιθυμείτε τη διαγραφή;", "Προειδοποίηση", JOptionPane.YES_NO_OPTION);
			if (deleteItem == JOptionPane.YES_OPTION) {
				try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
					 PreparedStatement pst = sqlConn.prepareStatement("DELETE FROM diseases WHERE iddiseases = ?")) {

					pst.setInt(1, iddiseases);
					int rowsAffected = pst.executeUpdate();
					if (rowsAffected > 0) {
						JOptionPane.showMessageDialog(this, "H καταχώριση διαγράφηκε");
						updateDiseasesTable();
						clearDiseaseFields();
					} else {
						JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για διαγραφή.");
					}
				}
			}
		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε την εγγραφή αυτή: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void JTableMouseClicked(java.awt.event.MouseEvent evt) {
		int selectedRow = diseasesTable.getSelectedRow();
		if (selectedRow != -1) {
			DefaultTableModel model = (DefaultTableModel) diseasesTable.getModel();
			iddiseasesTextField.setText(model.getValueAt(selectedRow, 0).toString());
			nameTextField.setText(model.getValueAt(selectedRow, 1).toString());
			descriptionTextField.setText(model.getValueAt(selectedRow, 2).toString());
			dateTextField.setText(model.getValueAt(selectedRow, 3).toString());
		}


	}

	public void updateCountryTable() {
		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("SELECT * FROM countries");
			 ResultSet rs = pst.executeQuery()) {

			DefaultTableModel model = (DefaultTableModel) countriesTable.getModel();
			model.setRowCount(0);

			while (rs.next()) {
				Vector<Object> columnData = new Vector<>();
				columnData.add(rs.getInt("idcountries"));
				columnData.add(rs.getString("country_name"));
				columnData.add(rs.getString("continent"));
				columnData.add(rs.getLong("population"));

				model.addRow(columnData.toArray());
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Σφάλμα: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void jbtnAddCountryNewActionPerformed(java.awt.event.ActionEvent evt) {
		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("INSERT INTO countries (idcountries, country_name, continent, population) VALUES (?, ?, ?, ?)")) {

			try {
				int id = Integer.parseInt(idcountriesTextField.getText());
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να πάρει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setInt(1, id);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			pst.setString(2, countryNameTextField.getText());
			pst.setString(3, continentTextField.getText());
			try {
				long population = Integer.parseInt(populationTextField.getText());
				if (population <= 0) {
					JOptionPane.showMessageDialog(this, "O πληθυσμός δεν μπορεί να πάρει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setLong(4, population);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Η καταχώριση προστέθηκε");
				updateCountryTable();
				clearCountryFields();
			} else {
				JOptionPane.showMessageDialog(this, "Η καταχώριση απέτυχε");
			}

		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Αποτυχία! " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void clearCountryFields() {
		idcountriesTextField.setText("");
		countryNameTextField.setText("");
		continentTextField.setText("");
		populationTextField.setText("");
	}

	private void jbtnUpdateCountryActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedRow = countriesTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Επιλέξτε τι θέλετε να επικαιροποιήσετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
			 PreparedStatement pst = sqlConn.prepareStatement("UPDATE countries SET country_name = ?, continent = ?, population = ? WHERE idcountries = ?")) {

			pst.setString(1, countryNameTextField.getText());
			pst.setString(2, continentTextField.getText());
			try {
				int population = Integer.parseInt(populationTextField.getText());
				if (population <= 0) {
					JOptionPane.showMessageDialog(this, "O πληθυσμός δεν μπορεί να πάρει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setLong(3, population);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				int id = Integer.parseInt(idcountriesTextField.getText());
				if (id <= 0) {
					JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να λάβει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pst.setInt(4, id);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Μη έγκυρη τιμή", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(this, "Η καταχώριση άλλαξε");
				updateCountryTable();
			} else {
				JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για ενημέρωση.");
			}

		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Αποτυχία: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void jbtnDeleteCountryActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedRow = countriesTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Επιλέξτε τη γραμμή που θέλετε να διαγράψετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int idcountries = Integer.parseInt(countriesTable.getValueAt(selectedRow, 0).toString());
			int deleteItem = JOptionPane.showConfirmDialog(null, "Σίγουρα επιθυμείτε τη διαγραφή;", "Προειδοποίηση", JOptionPane.YES_NO_OPTION);
			if (deleteItem == JOptionPane.YES_OPTION) {
				try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
					 PreparedStatement pst = sqlConn.prepareStatement("DELETE FROM countries WHERE idcountries = ?")) {

					pst.setInt(1, idcountries);
					int rowsAffected = pst.executeUpdate();
					if (rowsAffected > 0) {
						JOptionPane.showMessageDialog(this, "H καταχώριση διαγράφηκε");
						updateCountryTable();
						clearCountryFields();
					} else {
						JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για διαγραφή.");
					}
				}
			}
		} catch (SQLException | NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε την εγγραφή αυτή: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void JTableCountriesMouseClicked(java.awt.event.MouseEvent evt) {
		int selectedRow = countriesTable.getSelectedRow();
		if (selectedRow != -1) {
			DefaultTableModel model = (DefaultTableModel) countriesTable.getModel();
			idcountriesTextField.setText(model.getValueAt(selectedRow, 0).toString());
			countryNameTextField.setText(model.getValueAt(selectedRow, 1).toString());
			continentTextField.setText(model.getValueAt(selectedRow, 2).toString());
			populationTextField.setText(model.getValueAt(selectedRow, 3).toString());
		}
	}


		public void updateReportsTable() {
			try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
				 PreparedStatement pst = sqlConn.prepareStatement("SELECT idreport, comment, report_date FROM reports");
				 ResultSet rs = pst.executeQuery()) {

				DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();
				model.setRowCount(0);

				while (rs.next()) {
					Vector<Object> columnData = new Vector<>();
					columnData.add(rs.getInt("idreport"));
					columnData.add(rs.getString("comment"));
					columnData.add(rs.getDate("report_date"));

					model.addRow(columnData.toArray());
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Σφάλμα: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		private void jbtnAddReportNewActionPerformed(java.awt.event.ActionEvent evt) {
			try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
				 PreparedStatement pst = sqlConn.prepareStatement("INSERT INTO reports (idreport, comment, report_date) VALUES (?, ?, ?)")) {

				try {
					int id = Integer.parseInt(idreportTextField.getText());
					if (id <= 0) {
						JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να πάρει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
						return;
					}
					pst.setInt(1, id);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρο αριθμό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}

				pst.setString(2, commentTextField.getText());


				try {
					LocalDate reportDate = LocalDate.parse(reportDateTextField.getText());
					pst.setDate(3, java.sql.Date.valueOf(reportDate));
				} catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(this, "Η ημερομηνία πρέπει να είναι της μορφής: YYYY-MM-DD.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(this, "Η καταχώριση προστέθηκε");
					updateReportsTable();
					clearReportFields();
				} else {
					JOptionPane.showMessageDialog(this, "Η καταχώριση απέτυχε");
				}

			} catch (SQLException | NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Αποτυχία! " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		private void clearReportFields() {
			idreportTextField.setText("");
			commentTextField.setText("");
			reportDateTextField.setText("");
		}

		private void jbtnUpdateReportActionPerformed(java.awt.event.ActionEvent evt) {
			int selectedRow = reportsTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Επιλέξτε τι θέλετε να επικαιροποιήσετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
				 PreparedStatement pst = sqlConn.prepareStatement("UPDATE reports SET comment = ?, report_date = ? WHERE idreport = ?")) {

				pst.setString(1, commentTextField.getText());

				try {
					LocalDate reportDate = LocalDate.parse(reportDateTextField.getText());
					pst.setDate(2, java.sql.Date.valueOf(reportDate));
				} catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(this, "Η ημερομηνία πρέπει να είναι της μορφής: YYYY-MM-DD.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					int id = Integer.parseInt(idreportTextField.getText());
					if (id <= 0) {
						JOptionPane.showMessageDialog(this, "Το Id δεν μπορεί να λάβει αρνητική τιμή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
						return;
					}
					pst.setInt(3, id);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Μη έγκυρη τιμή", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(this, "Η καταχώριση άλλαξε");
					updateDiseasesTable();
				} else {
					JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για ενημέρωση.");
				}

			} catch (SQLException | NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Αποτυχία: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		private void jbtnDeleteReportActionPerformed(java.awt.event.ActionEvent evt) {
			int selectedRow = reportsTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Επιλέξτε τη γραμμή που θέλετε να διαγράψετε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				int idreport = Integer.parseInt(reportsTable.getValueAt(selectedRow, 0).toString());
				int deleteItem = JOptionPane.showConfirmDialog(null, "Σίγουρα επιθυμείτε τη διαγραφή;", "Προειδοποίηση", JOptionPane.YES_NO_OPTION);
				if (deleteItem == JOptionPane.YES_OPTION) {
					try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
						 PreparedStatement pst = sqlConn.prepareStatement("DELETE FROM reports WHERE idreport = ?")) {

						pst.setInt(1, idreport);
						int rowsAffected = pst.executeUpdate();
						if (rowsAffected > 0) {
							JOptionPane.showMessageDialog(this, "H καταχώριση διαγράφηκε");
							updateReportsTable();
							clearReportFields();
						} else {
							JOptionPane.showMessageDialog(this, "Η καταχώριση δεν βρέθηκε για διαγραφή.");
						}
					}
				}
			} catch (SQLException | NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε την εγγραφή αυτή: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		private void JTableReportsMouseClicked(java.awt.event.MouseEvent evt) {
			int selectedRow = reportsTable.getSelectedRow();
			if (selectedRow != -1) {
				DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();
				idreportTextField.setText(model.getValueAt(selectedRow, 0).toString());
				commentTextField.setText(model.getValueAt(selectedRow, 1).toString());
				reportDateTextField.setText(model.getValueAt(selectedRow, 2).toString());
			}

	}
}



	// Παράθυρο Αναλυτή
	class AnalystWindow extends JFrame {
		JPanel panelanalyst;
		JTable diseasesTable;
		JTextField iddiseasesTextField, nameTextField, descriptionTextField, dateTextField;
		JLabel tip = new JLabel();
		JDateChooser dateChooser1;
		JDateChooser dateChooser2;


		AnalystWindow() {
			setTitle("Περιβάλλον Αναλύσεων");
			setSize(1200, 800);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			panelanalyst = new JPanel(new GridBagLayout());
			add(panelanalyst);


			createUIComponents();
			updateDiseasesTable();


			setVisible(true);
		}

		private void createUIComponents() {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 10, 10);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1.0;


			String[] diseasesColumns = {"ID", "Όνομα", "Περιγραφή", "Ημερομηνία"};
			DefaultTableModel diseasesModel = new DefaultTableModel(diseasesColumns, 0);
			diseasesTable = new JTable(diseasesModel);

			int rowHeight = 25;
			diseasesTable.setRowHeight(rowHeight);

			int visibleRows = 5;
			diseasesTable.setPreferredScrollableViewportSize(new Dimension(400, visibleRows * rowHeight));
			diseasesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			JScrollPane diseasesScrollPane = new JScrollPane(diseasesTable);

			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weighty = 1.0;
			panelanalyst.add(diseasesScrollPane, gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.gridwidth = 2;
			tip.setHorizontalAlignment(SwingConstants.CENTER);
			tip.setText("Για περισσότερα στατιστικά δεδομένα επικοινωνήστε με τον διαχειριστή του συστήματος!");
			panelanalyst.add(tip, gbc);


			dateChooser1 = new JDateChooser();
			dateChooser2 = new JDateChooser();
			dateChooser1.setDateFormatString("yyyy-MM-dd");
			dateChooser2.setDateFormatString("yyyy-MM-dd");

			// Panel for Date Choosers (to group them)
			JPanel dateChooserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30,5)); // FlowLayout for horizontal arrangement
			dateChooserPanel.add(new JLabel("ΑΠΟ:")); // Added labels
			dateChooserPanel.add(dateChooser1);
			dateChooserPanel.add(new JLabel("ΕΩΣ:"));
			dateChooserPanel.add(dateChooser2);


			gbc = new GridBagConstraints();
			gbc.insets = new Insets(20, 5, 200, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 2; // Span both columns
			panelanalyst.add(dateChooserPanel, gbc);

			diseasesTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JTableMouseClicked(e);
				}
			});
		}

		public void updateDiseasesTable() {
			try (Connection sqlConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");
				 PreparedStatement pst = sqlConn.prepareStatement("SELECT * FROM diseases");
				 ResultSet rs = pst.executeQuery()) {

				DefaultTableModel model = (DefaultTableModel) diseasesTable.getModel();
				model.setRowCount(0);

				while (rs.next()) {
					Vector<Object> columnData = new Vector<>();
					columnData.add(rs.getInt("iddiseases"));
					columnData.add(rs.getString("name"));
					columnData.add(rs.getString("description"));
					columnData.add(rs.getDate("discovery_date"));

					model.addRow(columnData.toArray());
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Σφάλμα: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		private void JTableMouseClicked(java.awt.event.MouseEvent evt) {
			int selectedRow = diseasesTable.getSelectedRow();
			if (selectedRow != -1) {
				DefaultTableModel model = (DefaultTableModel) diseasesTable.getModel();
				iddiseasesTextField.setText(model.getValueAt(selectedRow, 0).toString());
				nameTextField.setText(model.getValueAt(selectedRow, 1).toString());
				descriptionTextField.setText(model.getValueAt(selectedRow, 2).toString());
				dateTextField.setText(model.getValueAt(selectedRow, 3).toString());
			}
		}
	}

		// Παράθυρο επισκέπτη
		class SimpleUserWindow extends JFrame {
			SimpleUserWindow() {
				setTitle("Περιβάλλον επισκέπτη");
				setSize(400, 300);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLocationRelativeTo(null);
				JLabel label = new JLabel("Καλωσήλθατε!", JLabel.CENTER);
				add(label);
				setVisible(true);
			}
		}


