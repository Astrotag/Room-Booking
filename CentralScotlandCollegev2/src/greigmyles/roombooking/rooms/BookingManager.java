package greigmyles.roombooking.rooms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JOptionPane;

/**
 * @author Greig Myles
 *
 */
public class BookingManager {
	private static final String FILE_SUFFIX = ".txt";
	HashMap<Integer, Client> clientList;
	HashMap<Integer, Booking> bookingsList;
	ArrayList<Room> roomsList = new ArrayList<Room>();

	private String clientFile = "RoomBookingSystem-Clients" + FILE_SUFFIX;
	private String bookingFile = "RoomBookingSystem-Bookings" + FILE_SUFFIX;
	private File clientStorageLocation = new File(clientFile);
	private File bookingStorageLocation = new File(bookingFile);

	private int clientIdCounter = 0;
	private int bookingIdCounter = 0;

	/**
	 * Constructor for the booking manager. This will assign the hashmaps for the
	 * bookings and client lists, and add the rooms to an array list from the
	 * addRooms() method.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public BookingManager() throws IOException, ClassNotFoundException {

		Objects.requireNonNull(clientStorageLocation, "Storage location cannot be null");
		Objects.requireNonNull(bookingStorageLocation, "Storage location cannot be null");

		clientList = new HashMap<Integer, Client>();
		bookingsList = new HashMap<Integer, Booking>();
		roomsList = addRooms();

		loadClientRecords();
		loadBookingRecords();

	}

	/**
	 * A method to add rooms to the roomsList ArrayList.
	 * 
	 * @return roomsList - An array list which holds the information on the rooms
	 *         that are available to book.
	 */
	public ArrayList<Room> addRooms() {

		roomsList.add(new Room(6, 0, 12, false, false));
		roomsList.add(new Room(8, 18, 10, true, true));
		roomsList.add(new Room(11, 20, 0, true, true));
		roomsList.add(new Room(13, 6, 0, false, true));
		roomsList.add(new Room(14, 18, 2, true, true));
		roomsList.add(new Room(15, 18, 10, true, true));
		roomsList.add(new Room(17, 18, 10, true, true));
		roomsList.add(new Room(108, 0, 20, true, false));
		roomsList.add(new Room(120, 18, 0, true, true));
		roomsList.add(new Room(301, 18, 6, true, true));

		return roomsList;
	}

	/**
	 * The method that is called from the interface to create a booking. The booking
	 * will find attempt to find the room which suits the booking determined by the
	 * amount of computers required. If a room is found then a booking will be
	 * created using a client, room, time slot, id and date.
	 * 
	 * @param noOfComputers - The entered amount of computers which the user has
	 *                      asked for
	 * @param startTime     - The start time determined by the timeslot the user has
	 *                      chosen.
	 * @param bookingDate   - The date that was picked by the user, using the
	 *                      DatePicker.
	 * @param client        - The client that made the booking
	 */
	public void createBooking(int noOfComputers, Timeslot timeslot, LocalDate bookingDate, Client client) {

		Room room = null;
		try {
			room = findRoom(noOfComputers, bookingDate, timeslot);
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(null, "An error has occured, contact an administrator", "System Error",
					JOptionPane.ERROR_MESSAGE);
		}

		if (room != null) {
			bookingsList.put(bookingIdCounter + 1,
					new Booking(bookingIdCounter + 1, timeslot, room, bookingDate, client));
			bookingIdCounter++;

			JOptionPane.showMessageDialog(null, "The booking was created successfully", "Booking Made",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, "No rooms can be found matching those requirements", "Booking Error",
					JOptionPane.INFORMATION_MESSAGE);
		}

		saveRecords();
	}

	/**
	 * The method which finds the room to return to the createBooking() method. It
	 * will use an ArrayList which will consist of the list of rooms. That ArrayList
	 * will them be filtered first by the amount of computers each room has, and the
	 * required amount of computers, then the rooms will be checked to see if those
	 * rooms have been booked on that date & time slot. The rooms that have been
	 * booked, or don't meet the required amount of computers will be removed from
	 * the filteredList. The filteredList will then be sorted and the room which
	 * best fits will be returned.
	 * 
	 * @param noOfComputers - Number of computers required in the booking
	 * @param date          - Date of the booking
	 * @param timeslot      - Time slot which holds start and end times for the
	 *                      requested booking
	 * 
	 * @return - The room that meets the requirements with the lowest wasted space.
	 * @throws NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public Room findRoom(int noOfComputers, LocalDate date, Timeslot timeslot) throws NullPointerException {
		ArrayList<Room> filteredRooms;
		filteredRooms = (ArrayList<Room>) roomsList.clone();

		Room bestFit = null;

		filteredRooms = filterRoomsBySize(noOfComputers, filteredRooms);

		filteredRooms = filterByBookings(date, timeslot, filteredRooms);

		bestFit = getBestFit(filteredRooms);

		return bestFit;
	}

	/**
	 * The method which removes rooms if they are currently booked in another
	 * booking on the selected date and timeslot.
	 * 
	 * @param date          - Date chosen by the user to book on
	 * @param timeslot      - Timeslot chosen by the user
	 * @param filteredRooms - A filtered list of rooms
	 * 
	 * @return a filtered list of rooms
	 */
	private ArrayList<Room> filterByBookings(LocalDate date, Timeslot timeslot, ArrayList<Room> filteredRooms) {
		for (Map.Entry<Integer, Booking> entry : bookingsList.entrySet()) {

			Room room = entry.getValue().getBookedRoom();

			for (int i = 0; i < filteredRooms.size(); i++) {

				if ((entry.getValue().getBookingDate().equals(date)
						&& entry.getValue().getTimeslot().getStartTime().equals(timeslot.getStartTime()))
						&& room.getRoomNo() == filteredRooms.get(i).getRoomNo()) {

					filteredRooms.remove(i);
				}
			}
		}

		return filteredRooms;
	}

	/**
	 * The method which removes rooms if they do not have enough computers to match
	 * the required amount.
	 * 
	 * @param noOfComputers - amount of computers required
	 * @param filteredRooms - filtered rooms array list
	 * 
	 * @return the filteredRooms array list
	 */
	private ArrayList<Room> filterRoomsBySize(int noOfComputers, ArrayList<Room> filteredRooms) {

		if (filteredRooms.size() > 1) {
			for (int i = 0; i < filteredRooms.size(); i++) {
				if (filteredRooms.get(i).getNoOfComputers() < noOfComputers) {
					System.out.println("Removing a room : " + filteredRooms.get(i).getRoomNo());
					filteredRooms.remove(i);
					i--;
				}
			}
		} else {
			System.out.println("No Rooms available");
		}
		return filteredRooms;
	}

	/**
	 * A method to sort and return the best fitting room for the requirements.
	 * 
	 * @param filteredRooms - ArrayList for rooms
	 * 
	 * @return the first room in the array list
	 */
	private Room getBestFit(ArrayList<Room> filteredRooms) {

		if (filteredRooms.size() > 0) {
			System.out.println(filteredRooms);
			Collections.sort(filteredRooms);
			System.out.println(filteredRooms);

			return filteredRooms.get(0);
		}

		else {
			return null;
		}
	}

	/**
	 * A method that will create a client using an overloaded method. If there is
	 * not a phone number entered then it will use the first method to create a
	 * client. If there is a phone number it will use the second. There is also an
	 * id counter which adds an ID to the hash map for clients.
	 * 
	 * @param clientName   - the name entered for the client
	 * @param phoneNumber  - the phone number entered for the client (not mandatory)
	 * @param emailAddress - the email address entered for the client
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void createClient(String clientName, String phoneNumber, String emailAddress)
			throws FileNotFoundException, IOException {

		System.out.println(clientIdCounter);
		if (phoneNumber.isEmpty()) {
			clientList.put(clientIdCounter + 1, new Client(clientName, clientIdCounter + 1, emailAddress));
			clientIdCounter++;
		}

		else {
			clientList.put(clientIdCounter + 1, new Client(clientName, clientIdCounter + 1, emailAddress, phoneNumber));
			clientIdCounter++;
		}

		saveClientRecords();
	}

	/**
	 * A short method to cancel a booking by taking in the id of a booking that is
	 * being cancelled.
	 * 
	 * @param bookingId
	 * @return
	 */
	public boolean cancelBooking(int bookingId) {
		Booking b = bookingsList.remove(bookingId);
		return b != null;
	}

	/**
	 * A method to return an array of clients which is used in the interface for a
	 * client drop-down.
	 * 
	 * @return an array of clients from the ArrayList
	 */
	public Client[] getClients() {
		Client[] allClients = clientList.values().toArray(new Client[clientList.size()]);

		return allClients;
	}

	/**
	 * A method that returns the current bookings. Used in the interface to show the
	 * bookings.
	 * 
	 * @return an array of bookings from the bookingsList ArrayList
	 */
	public Booking[] getBookings() {
		Booking[] allBookings = bookingsList.values().toArray(new Booking[bookingsList.size()]);

		return allBookings;
	}

	/**
	 * A method which uses serialisation to load the clients from file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void loadClientRecords() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (clientStorageLocation.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(clientStorageLocation))) {

				clientIdCounter = ois.readInt();
				clientList = (HashMap<Integer, Client>) ois.readObject();
			}
		}
	}

	/**
	 * A method which uses serialisation to save the clients to a file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveClientRecords() throws FileNotFoundException, IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(clientStorageLocation))) {

			oos.writeInt(clientIdCounter);
			oos.writeObject(clientList);
		}
	}

	/**
	 * A method which uses serialisation to save the bookings from a file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void saveBookingRecords() throws FileNotFoundException, IOException, ClassNotFoundException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(bookingStorageLocation))) {
			oos.writeInt(bookingIdCounter);
			oos.writeObject(bookingsList);
		}
	}

	/**
	 * A method which uses serialisation to load the bookings from file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private void loadBookingRecords() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (bookingStorageLocation.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(bookingStorageLocation))) {

				bookingIdCounter = ois.readInt();
				bookingsList = (HashMap<Integer, Booking>) ois.readObject();
			}
		}
	}

	/**
	 * A method which called the saveBooking() and loadBooking() methods in the
	 * interface. This is to allow the saving and loading to be private.
	 * 
	 */
	public void saveRecords() {
		try {
			saveBookingRecords();
			loadBookingRecords();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error saving the booking. Please try again!", "Booking Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error saving the booking. Please try again!", "Booking Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving the booking. Please try again!", "Booking Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Enumeration which holds the time slots for a booking. Time slots are made to
	 * only allow a booking to happen in certain times of the day. They have a start
	 * and end time which can be used to display in the interface. Also uses a
	 * toString method.
	 *
	 */
	public enum Timeslot {
		TIMESLOT1(LocalTime.of(9, 00), LocalTime.of(10, 55)), TIMESLOT2(LocalTime.of(11, 00), LocalTime.of(12, 55)),
		TIMESLOT3(LocalTime.of(13, 00), LocalTime.of(14, 55)), TIMESLOT4(LocalTime.of(15, 00), LocalTime.of(16, 55));

		private LocalTime startTime;
		private LocalTime endTime;

		Timeslot(LocalTime start, LocalTime end) {
			this.setStartTime(start);
			this.setEndTime(end);
		}

		public LocalTime getStartTime() {
			return startTime;
		}

		private void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}

		public LocalTime getEndTime() {
			return endTime;
		}

		private void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}

		public String toString() {
			return getStartTime().toString() + " - " + getEndTime().toString();
		}

	}
}
