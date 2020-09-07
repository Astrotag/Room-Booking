package greigmyles.roombooking.driver;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import greigmyles.roombooking.rooms.Booking;
import greigmyles.roombooking.rooms.BookingManager;

public class MainWindow {

	private JFrame frmCentralScotlandCollege;

	private BookingManager bookingManager;

	private AddClientDialog addClientDialog;

	private AddBookingDialog addBookingDialog;
	private JScrollPane scrollPane;
	JTextPane bookingPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmCentralScotlandCollege.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MainWindow() {

		initialize();

		try {
			bookingManager = new BookingManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCentralScotlandCollege = new JFrame();
		frmCentralScotlandCollege.setFont(new Font("Calibri", Font.PLAIN, 12));
		frmCentralScotlandCollege.setTitle("Central Scotland College");
		frmCentralScotlandCollege.setBounds(100, 100, 650, 300);
		frmCentralScotlandCollege.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 10, 0, 10));
		frmCentralScotlandCollege.getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnAddClient = new JButton("Add Client");
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClient();
			}

		});
		GridBagConstraints gbc_btnAddClient = new GridBagConstraints();
		gbc_btnAddClient.fill = GridBagConstraints.BOTH;
		gbc_btnAddClient.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddClient.gridx = 0;
		gbc_btnAddClient.gridy = 0;
		panel.add(btnAddClient, gbc_btnAddClient);

		JButton btnCreateBooking = new JButton("Create Booking");
		btnCreateBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addBooking();
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Critical Error, contact an administrator",
							"Critical Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnCreateBooking = new GridBagConstraints();
		gbc_btnCreateBooking.fill = GridBagConstraints.VERTICAL;
		gbc_btnCreateBooking.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateBooking.gridx = 0;
		gbc_btnCreateBooking.gridy = 1;
		panel.add(btnCreateBooking, gbc_btnCreateBooking);

		JButton btnListBookings = new JButton("List Bookings");
		btnListBookings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBookings();
			}
		});
		GridBagConstraints gbc_btnListBookings = new GridBagConstraints();
		gbc_btnListBookings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListBookings.insets = new Insets(0, 0, 5, 0);
		gbc_btnListBookings.gridx = 0;
		gbc_btnListBookings.gridy = 2;
		panel.add(btnListBookings, gbc_btnListBookings);

		JButton btnDeleteBooking = new JButton("Delete Booking");
		btnDeleteBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					deleteRecord();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "That booking does not exist! Please enter an ID",
							"Booking Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnDeleteBooking = new GridBagConstraints();
		gbc_btnDeleteBooking.fill = GridBagConstraints.BOTH;
		gbc_btnDeleteBooking.gridx = 0;
		gbc_btnDeleteBooking.gridy = 3;
		panel.add(btnDeleteBooking, gbc_btnDeleteBooking);

		Component horizontalGlue = Box.createHorizontalGlue();
		frmCentralScotlandCollege.getContentPane().add(horizontalGlue, BorderLayout.EAST);

		scrollPane = new JScrollPane();
		frmCentralScotlandCollege.getContentPane().add(scrollPane, BorderLayout.CENTER);

		bookingPane = new JTextPane();
		bookingPane.setEditable(false);
		bookingPane.setContentType("text/html");
		scrollPane.setViewportView(bookingPane);

	}

	/**
	 * 
	 */
	private void addClient() {
		if (addClientDialog == null) {
			addClientDialog = new AddClientDialog(bookingManager);
		}
		addClientDialog.setLocationRelativeTo(frmCentralScotlandCollege);
		addClientDialog.setVisible(true);

	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void addBooking() throws FileNotFoundException, ClassNotFoundException, IOException {

		if (addBookingDialog == null) {
			addBookingDialog = new AddBookingDialog(bookingManager);
		} else {
			addBookingDialog.clearClientDropdown();
			addBookingDialog.updateClientDropdown();
		}

		addBookingDialog.setLocationRelativeTo(frmCentralScotlandCollege);
		addBookingDialog.setVisible(true);

	}

	/**
	 * 
	 */
	private void showBookings() {

		scrollPane.setVisible(true);
		Booking[] bookingList = bookingManager.getBookings();

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-family: Calibri, sans-serif;' border=0><tr><th>Booking ID</th><th>Client Name</th><th>Room No</th><th>Date</th><th>Time</th></tr>\n");
		for (Booking b : bookingList) {
			sb.append("<tr><td>");
			sb.append(b.getBookingID());
			sb.append("</td><td>");
			sb.append(b.getClient().getClientName());
			sb.append("</td><td>");
			sb.append(b.getBookedRoom().getRoomNo());
			sb.append("</td><td>");
			sb.append(b.getBookingDate());
			sb.append("</td><td>");
			sb.append(b.getStartTime() + "-" + b.getEndTime());
			sb.append("</td></tr>\n");
		}
		sb.append("</table>");

		bookingPane.setText(sb.toString());
		EventQueue.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
		// btnPrint.setEnabled(true);
	}

	/**
	 * 
	 * @throws NumberFormatException
	 */
	private void deleteRecord() throws NumberFormatException {

		int id = Integer.parseInt(JOptionPane.showInputDialog(null, bookingManager.getBookings(), "Delete Booking", 0));

		if (bookingManager.cancelBooking(id)) {
			bookingManager.saveRecords();
		} else {
			JOptionPane.showMessageDialog(null, "The booking was not found!", "Booking Error",
					JOptionPane.ERROR_MESSAGE);
			deleteRecord();
		}

	}
}
