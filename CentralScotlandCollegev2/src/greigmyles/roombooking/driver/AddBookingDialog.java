package greigmyles.roombooking.driver;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import greigmyles.roombooking.rooms.BookingManager;
import greigmyles.roombooking.rooms.BookingManager.Timeslot;
import greigmyles.roombooking.rooms.Client;

import com.github.lgooddatepicker.components.DatePicker;

@SuppressWarnings("serial")
public class AddBookingDialog extends JDialog {
//
	private static BookingManager bookingManager;
	JComboBox<Client> clientsDropdown;
	JComboBox<Timeslot> timeslotDropdown;
	DatePicker datePicker;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtReqComp;

	/**
	 * Create the dialog.
	 */
	public AddBookingDialog(BookingManager bm) {
		bookingManager = bm;

		initialize();
	}

	/**
	 * 
	 */
	private void handleOkButton() {

		Client client = (Client) clientsDropdown.getSelectedItem();
		Timeslot timeslot = (Timeslot) timeslotDropdown.getSelectedItem();

		LocalDate date = datePicker.getDate();

		int comps = 0;

		try {
			comps = Integer.parseInt(txtReqComp.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			comps = -1;
			System.out.println("No Worky");
		}

		if (validateFields(comps, date)) {
			bookingManager.createBooking(comps, timeslot, date, client);
		}

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

		txtReqComp.setText("");

	}

	/**
	 * 
	 * @param noOfComputers
	 * @param dateOfBooking
	 * @return
	 */
	private boolean validateFields(int noOfComputers, LocalDate dateOfBooking) {
		boolean dataIsOk = true;
		LocalDate today = LocalDate.now();

		if (noOfComputers >= 0 && dateOfBooking.isAfter(today)) {
			dataIsOk = true;
			System.out.println("Data is ok");
		} else {
			dataIsOk = false;
			System.out.println("Data is not ok");
		}

		return dataIsOk;
	}

	/**
	 * 
	 */
	private void initialize() {
		LocalDate today = LocalDate.now().plusDays(1);

		setTitle("Add Booking");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 39, 34, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblClient = new JLabel("Client : ");
			GridBagConstraints gbc_lblClient = new GridBagConstraints();
			gbc_lblClient.fill = GridBagConstraints.BOTH;
			gbc_lblClient.insets = new Insets(0, 0, 5, 5);
			gbc_lblClient.gridx = 1;
			gbc_lblClient.gridy = 1;
			contentPanel.add(lblClient, gbc_lblClient);
		}
		{

			clientsDropdown = new JComboBox<Client>();

			updateClientDropdown();

			GridBagConstraints gbc_clientsDropdown = new GridBagConstraints();
			gbc_clientsDropdown.insets = new Insets(0, 0, 5, 5);
			gbc_clientsDropdown.fill = GridBagConstraints.HORIZONTAL;
			gbc_clientsDropdown.gridx = 2;
			gbc_clientsDropdown.gridy = 1;
			contentPanel.add(clientsDropdown, gbc_clientsDropdown);
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
			JLabel lblReqComps = new JLabel("Required Computers : ");
			GridBagConstraints gbc_lblReqComps = new GridBagConstraints();
			gbc_lblReqComps.anchor = GridBagConstraints.EAST;
			gbc_lblReqComps.fill = GridBagConstraints.VERTICAL;
			gbc_lblReqComps.insets = new Insets(0, 0, 5, 5);
			gbc_lblReqComps.gridx = 1;
			gbc_lblReqComps.gridy = 2;
			contentPanel.add(lblReqComps, gbc_lblReqComps);
		}
		{
			txtReqComp = new JTextField();
			GridBagConstraints gbc_txtReqComp = new GridBagConstraints();
			gbc_txtReqComp.insets = new Insets(0, 0, 5, 5);
			gbc_txtReqComp.fill = GridBagConstraints.BOTH;
			gbc_txtReqComp.gridx = 2;
			gbc_txtReqComp.gridy = 2;
			contentPanel.add(txtReqComp, gbc_txtReqComp);
			txtReqComp.setColumns(10);
		}
		{
			JLabel lblReqDate = new JLabel("Requried Date : ");
			GridBagConstraints gbc_lblReqDate = new GridBagConstraints();
			gbc_lblReqDate.fill = GridBagConstraints.BOTH;
			gbc_lblReqDate.insets = new Insets(0, 0, 5, 5);
			gbc_lblReqDate.gridx = 1;
			gbc_lblReqDate.gridy = 3;
			contentPanel.add(lblReqDate, gbc_lblReqDate);
		}
		{
			datePicker = new DatePicker();
			datePicker.setDate(today);
			GridBagConstraints gbc_datePicker = new GridBagConstraints();
			gbc_datePicker.insets = new Insets(0, 0, 5, 5);
			gbc_datePicker.fill = GridBagConstraints.BOTH;
			gbc_datePicker.gridx = 2;
			gbc_datePicker.gridy = 3;
			contentPanel.add(datePicker, gbc_datePicker);
		}
		{
			JLabel lblTimeslot = new JLabel("Timeslot :");
			GridBagConstraints gbc_lblTimeslot = new GridBagConstraints();
			gbc_lblTimeslot.anchor = GridBagConstraints.WEST;
			gbc_lblTimeslot.fill = GridBagConstraints.VERTICAL;
			gbc_lblTimeslot.insets = new Insets(0, 0, 0, 5);
			gbc_lblTimeslot.gridx = 1;
			gbc_lblTimeslot.gridy = 4;
			contentPanel.add(lblTimeslot, gbc_lblTimeslot);
		}
		{
			timeslotDropdown = new JComboBox<Timeslot>(BookingManager.Timeslot.values());
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 4;
			contentPanel.add(timeslotDropdown, gbc_comboBox);
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

	/**
	 * 
	 */
	public void clearClientDropdown() {
		clientsDropdown.removeAllItems();
	}

	/**
	 * 
	 */
	public void updateClientDropdown() {
		Client[] clients = bookingManager.getClients();

		for (Client client : clients) {
			clientsDropdown.addItem(client);
		}
	}

}
