package greigmyles.roombooking.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import greigmyles.roombooking.rooms.BookingManager;
import greigmyles.roombooking.rooms.Client;

@SuppressWarnings("serial")
public class AddClientDialog extends JDialog {

	private static BookingManager bookingManager;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtNumber;
	private JTextField txtEmail;

	private JLabel lblPhoneNumber, lblEmailAddress, lblName;

	/**
	 * Create the dialog.
	 */
	public AddClientDialog(BookingManager bm) {
		initialize();

		bookingManager = bm;
	}

	/**
	 * 
	 */
	private void handleOkButton() {

		if (!validateFields()) {
			JOptionPane.showMessageDialog(contentPanel, "There was a problem processing the data\n Check and try again",
					"Input Error", JOptionPane.ERROR_MESSAGE);
		}

		else {
			try {
				bookingManager.createClient(txtName.getText(), txtNumber.getText(), txtEmail.getText());

				// reset the fields
				txtName.setText("");
				txtNumber.setText("");
				txtEmail.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		setVisible(false);

	}

	/**
	 * 
	 */
	private void handleCancelButton() {
		clearFields();
		setVisible(false);

	}

	/**
	 * 
	 */
	private void clearFields() {
		txtName.setText("");
		txtNumber.setText("");
		txtEmail.setText("");
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateFields() {
		boolean dataIsOk = true;
		String clientName = txtName.getText();
		String phoneNumber = txtNumber.getText();
		String emailAddr = txtEmail.getText();
		Client[] list = bookingManager.getClients();

		if (!Client.isValidName.test(clientName)) {
			dataIsOk = false;
			JOptionPane.showMessageDialog(contentPanel,
					"A name must begin with a capital and be less than 30 characters", "Name Error",
					JOptionPane.ERROR_MESSAGE);
			lblName.setBackground(Color.RED);
		}

		if (!Client.isValidNumber.test(phoneNumber)) {
			dataIsOk = false;
			JOptionPane.showMessageDialog(contentPanel,
					"The phone number must begin with '0' and be no more than 12 numbers", "Phone Number Error",
					JOptionPane.ERROR_MESSAGE);
			lblPhoneNumber.setBackground(Color.RED);
		}

		if (!Client.isValidEmail.test(emailAddr) || emailAddr.isEmpty()) {
			dataIsOk = false;
			JOptionPane.showMessageDialog(contentPanel,
					"The email address is mandatory must have at least 1 character, contain an '@' and have a domain at the end",
					"Email Address Error", JOptionPane.ERROR_MESSAGE);
			lblEmailAddress.setBackground(Color.RED);
		}

		for (Client client : list) {
			if (client.getClientEmailAddress().equals(emailAddr)) {
				JOptionPane.showMessageDialog(contentPanel, "This client already exists in the system",
						"Client exists error", JOptionPane.ERROR_MESSAGE);
				dataIsOk = false;
			}
		}

		return dataIsOk;
	}

	/**
	 * 
	 */
	private void initialize() {
		setTitle("Add Client");
		setBounds(100, 100, 450, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			lblName = new JLabel("Name :");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.anchor = GridBagConstraints.EAST;
			gbc_lblName.insets = new Insets(0, 0, 5, 5);
			gbc_lblName.gridx = 1;
			gbc_lblName.gridy = 1;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			txtName = new JTextField();
			GridBagConstraints gbc_txtName = new GridBagConstraints();
			gbc_txtName.insets = new Insets(0, 0, 5, 5);
			gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtName.gridx = 2;
			gbc_txtName.gridy = 1;
			contentPanel.add(txtName, gbc_txtName);
			txtName.setColumns(10);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_horizontalStrut.gridx = 3;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
			gbc_horizontalStrut.gridx = 4;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			lblPhoneNumber = new JLabel("Phone Number : ");
			GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
			gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
			gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhoneNumber.gridx = 1;
			gbc_lblPhoneNumber.gridy = 2;
			contentPanel.add(lblPhoneNumber, gbc_lblPhoneNumber);
		}
		{
			txtNumber = new JTextField();
			GridBagConstraints gbc_txtNumber = new GridBagConstraints();
			gbc_txtNumber.insets = new Insets(0, 0, 5, 5);
			gbc_txtNumber.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtNumber.gridx = 2;
			gbc_txtNumber.gridy = 2;
			contentPanel.add(txtNumber, gbc_txtNumber);
			txtNumber.setColumns(10);
		}
		{
			lblEmailAddress = new JLabel("E-mail Address : ");
			GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
			gbc_lblEmailAddress.anchor = GridBagConstraints.EAST;
			gbc_lblEmailAddress.insets = new Insets(0, 0, 0, 5);
			gbc_lblEmailAddress.gridx = 1;
			gbc_lblEmailAddress.gridy = 3;
			contentPanel.add(lblEmailAddress, gbc_lblEmailAddress);
		}
		{
			txtEmail = new JTextField();
			GridBagConstraints gbc_txtEmail = new GridBagConstraints();
			gbc_txtEmail.insets = new Insets(0, 0, 0, 5);
			gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEmail.gridx = 2;
			gbc_txtEmail.gridy = 3;
			contentPanel.add(txtEmail, gbc_txtEmail);
			txtEmail.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						handleOkButton();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						handleCancelButton();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

}
