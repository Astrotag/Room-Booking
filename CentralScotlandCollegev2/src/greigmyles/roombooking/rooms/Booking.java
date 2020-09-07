package greigmyles.roombooking.rooms;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import greigmyles.roombooking.rooms.BookingManager.Timeslot;

public class Booking implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookingID;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate bookingDate;
	private Client client;
	private Room bookedRoom;
	private Timeslot timeslot;

	/**
	 * @param bookingID
	 * @param startTime
	 * @param endTime
	 * @param bookingDate
	 * @param client
	 */
	public Booking(int bookingID, Timeslot timeslot, Room room, LocalDate bookingDate, Client client) {
		setBookingID(bookingID);
		setTimeslot(timeslot);
		setStartTime(timeslot.getStartTime());
		setEndTime(timeslot.getEndTime());
		setBookedRoom(room);
		setBookingDate(bookingDate);
		setClient(client);

	}

	/**
	 * @return the bookingID
	 */
	public int getBookingID() {
		return bookingID;
	}

	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * @return the bookingDate
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @return the bookedRoom
	 */
	public Room getBookedRoom() {
		return bookedRoom;
	}

	/**
	 * @param bookedRoom the bookedRoom to set
	 */
	private void setBookedRoom(Room bookedRoom) {
		this.bookedRoom = bookedRoom;
	}

	/**
	 * @param bookingID the bookingID to set
	 */
	private void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	/**
	 * @param startTime the startTime to set
	 */
	private void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	private void setEndTime(LocalTime time) {
		this.endTime = time;
	}

	/**
	 * @param bookingDate the bookingDate to set
	 */
	private void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * @param client the client to set
	 */
	private void setClient(Client client) {
		this.client = client;
	}

	public Timeslot getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}

	@Override
	public String toString() {
		return "ID : " + getBookingID() + ", Date : " + getBookingDate() + ", Client : " + getClient().getClientName()
				+ ", Room No : " + getBookedRoom().getRoomNo();
	}

}
