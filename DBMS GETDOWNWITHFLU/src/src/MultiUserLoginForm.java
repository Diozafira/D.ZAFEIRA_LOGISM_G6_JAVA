import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MultiUserLoginForm {
	JFrame frame;
	JLabel loginIcon;
	JLabel title;
	JLabel userIcon;
	JLabel passwordIcon;
	JLabel selectAccountIcon;
	JTextField usernameTextField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton closeButton;
	JComboBox comboBox;
	String[] values={"Select Account Type","Admin", "Analyst", "User"};

	MultiUserLoginForm(){

		frame=new JFrame();
		frame.setSize(350,500);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.white);
		frame.setResizable(false);


		ImageIcon icon1=new ImageIcon("login.png");
		loginIcon =new JLabel(icon1);
		loginIcon.setBounds(115,20,icon1.getIconWidth(),icon1.getIconHeight());

		title=new JLabel("Login");
		title.setBounds(155,130,150,70);
		title.setFont(new Font("ARIAL",Font.BOLD,15));


		comboBox=new JComboBox(values);
		comboBox.setBounds(110,195,180,30);
		comboBox.setBackground(Color.white);


		ImageIcon icon2=new ImageIcon("usertype.png");
		selectAccountIcon =new JLabel(icon2);
		selectAccountIcon.setBounds(50,190,icon2.getIconWidth(),icon2.getIconHeight());

		ImageIcon icon3=new ImageIcon("user.png");
		userIcon =new JLabel(icon3);
		userIcon.setBounds(50,240,icon3.getIconWidth(),icon3.getIconHeight());


		usernameTextField=new JTextField();
		usernameTextField.setBounds(110,245,180,30);
		usernameTextField.setFont(new Font("ARIAL",Font.BOLD,15));


		ImageIcon icon4=new ImageIcon("lock.png");
		passwordIcon = new JLabel(icon4);
		passwordIcon.setBounds(50,295,icon4.getIconWidth(),icon4.getIconHeight());


		passwordField=new JPasswordField();
		passwordField.setBounds(110,300,180,30);
		passwordField.setFont(new Font("ARIAL",Font.BOLD,15));


		loginButton=new JButton("Login");
		loginButton.setBounds(65,360,100,30);
		loginButton.setFocusable(false);
		loginButton.setFont(new Font("Arial",Font.BOLD,15));
		loginButton.setBackground(new Color(0,165,246));
		loginButton.setForeground(Color.white);


		closeButton=new JButton("Close");
		closeButton.setBounds(185,360,100,30);
		closeButton.setFocusable(false);
		closeButton.setFont(new Font("Arial",Font.BOLD,15));
		closeButton.setBackground(new Color(244,67,54));
		closeButton.setForeground(Color.white);


		frame.add(loginIcon);
		frame.add(title);
		frame.add(userIcon);
		frame.add(usernameTextField);
		frame.add(passwordIcon);
		frame.add(passwordField);
		frame.add(selectAccountIcon);
		frame.add(comboBox);
		frame.add(loginButton);
		frame.add(closeButton);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String accountType=comboBox.getSelectedItem().toString();
					String userName=usernameTextField.getText();
					String password=passwordField.getText();
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/getdownwithflu", "root", "Govo1986");//Establishing connection
					//System.out.println("Connected With the database successfully");
					PreparedStatement preparedStatement=connection.prepareStatement("select * from user");
					ResultSet resultSet=preparedStatement.executeQuery();
					if(accountType.equalsIgnoreCase("Select Account Role")||userName.equalsIgnoreCase("")||password.equalsIgnoreCase("")){
						JOptionPane.showMessageDialog(null,"Please Enter all Fields");
					}
					else {

						while (resultSet.next()) {
							if (accountType.equalsIgnoreCase(resultSet.getString("ACCOUNT_TYPE")) && userName.equalsIgnoreCase(resultSet.getString("USERNAME")) && password.equalsIgnoreCase(resultSet.getString("PASSWORD"))) {
								if (accountType.equalsIgnoreCase("Admin")) {

									AdminWindow adminWindow = new AdminWindow();
									frame.dispose();
									break;

								} else if (accountType.equalsIgnoreCase("User")) {

									UserWindow userWindow = new UserWindow();
									frame.dispose();
									break;

								}
							}

						}
						if(resultSet.isAfterLast()){
							JOptionPane.showMessageDialog(null,"Username & Password did not match");
						}


					}


				} catch (SQLException exception) {

					System.out.println("Error while connecting to the database");

				}


			}
		});


		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});


		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public static void main(String[] args){

		MultiUserLoginForm multiUserLoginForm=new MultiUserLoginForm();


	}

	public static class AnalystWindow {
	}
}

class AdminWindow extends JFrame{

	AdminWindow(){
		JFrame frame=new JFrame();
		JLabel label=new JLabel("Admin Window");

		frame.setSize(300,300);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		label.setBounds(100,100,100,50);
		frame.add(label);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class UserWindow extends JFrame{

	UserWindow(){
		JFrame frame=new JFrame();
		JLabel label=new JLabel("User Window");

		frame.setSize(300,300);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		label.setBounds(100,100,100,50);
		frame.add(label);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
