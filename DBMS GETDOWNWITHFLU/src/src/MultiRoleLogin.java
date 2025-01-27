import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//Εδώ ξεκινάει η φόρμα εισόδου με credentials πολλαπλών ρόλων
public class MultiRoleLogin {
	// JDBC URL, username, and password
	private static final String DB_URL = "jdbc:mysql://localhost:3306/getdownwithflu";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Govo1986";
//Εδώ ξεκινά η Main
	public static void main(String[] args) {
		new MultiRoleLogin().createLoginForm();
	}

	public void createLoginForm() {
		JFrame frame = new JFrame("Φόρμα εισόδου");
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);

		// Components
		JLabel lblTitle = new JLabel("Σύνδεση");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setBounds(160, 20, 200, 30);
		frame.add(lblTitle);

		JLabel lblUsername = new JLabel("Όνομα χρήστη:");
		lblUsername.setBounds(50, 70, 100, 30);
		frame.add(lblUsername);

		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(150, 70, 180, 30);
		frame.add(txtUsername);

		JLabel lblPassword = new JLabel("Κωδικός:");
		lblPassword.setBounds(50, 120, 100, 30);
		frame.add(lblPassword);

		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setBounds(150, 120, 180, 30);
		frame.add(txtPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(150, 180, 100, 30);
		frame.add(btnLogin);

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(270, 180, 100, 30);
		frame.add(btnClose);

		// Button Actions
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());

				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Συμπληρώστε όλα τα πεδία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
				} else {
					authenticateUser(username, password, frame);
				}
			}
		});

		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		frame.setVisible(true);
	}

	private void authenticateUser(String username, String password, JFrame frame) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT role FROM user WHERE username = ? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String role = resultSet.getString("role");

				switch (role.toLowerCase()) {
					case "admin":
						JOptionPane.showMessageDialog(frame, "Συγχαρητήρια! Εισήλθατε ως διαχειριστής");
						new AdminWindow();
						break;
					case "analyst":
						JOptionPane.showMessageDialog(frame, "Είσοδος ως αναλυτής!");
						new AnalystWindow();
						break;
					case "simpleuser":
						JOptionPane.showMessageDialog(frame, "Είσοδος ως επισκέπτης!");
						new SimpleUserWindow();
						break;
				}
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "Εσφαλμένα στοιχεία εισόδου!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Αποτυχία !", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

// Admin window
class AdminWindow extends JFrame {
	AdminWindow() {
		setTitle("Περιβάλλον Διαχείρισης");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JLabel label = new JLabel("Καλωσήλθατε!", JLabel.CENTER);
		add(label);
		setVisible(true);
	}
}

// Analyst window
class AnalystWindow extends JFrame {
	AnalystWindow() {
		setTitle("Περιβάλλον Αναλύσεων");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JLabel label = new JLabel("Καλωσήλθατε", JLabel.CENTER);
		add(label);
		setVisible(true);
	}
}

// Simple User window
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
