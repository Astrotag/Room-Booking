package greigmyles.roombooking.rooms;

import java.io.Serializable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class Client implements Serializable {

	/**
	 * Predicate to validate a clients name
	 */
	public static final Predicate<String> isValidName = Pattern.compile("^[A-Z][a-z]+$").asPredicate();
	
	/**
	 * Predicate to validate a clients email address
	 * 
	 * http://regexlib.com/REDetails.aspx?regexp_id=21
	 */
	public static final Predicate<String> isValidEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
			.asPredicate();
	
	/**
	 * Predicate to validate a clients phone number
	 * 
	 * http://regexlib.com/REDetails.aspx?regexp_id=308
	 */
	public static final Predicate<String> isValidNumber = Pattern
			.compile("^07([\\d]{3})[(\\D\\s)]?[\\d]{3}[(\\D\\s)]?[\\d]{3}$").asPredicate();
	
	private int clientID;
	private String clientPhoneNumber, clientEmailAddress, clientName;

	/**
	 * Constructor that takes a generated ID and clients entered email address without a phone numberr.
	 * 
	 * @param clientName
	 * @param clientID
	 * @param phoneNumber
	 */

	public Client(String clientName, int clientID, String emailAddress) {
		this.clientID = clientID;
		this.clientName = clientName;
		this.clientEmailAddress = emailAddress;
	}

	/**
	 * Overloaded Client constructor for all details entered
	 * 
	 * @param clientName
	 * @param clientID
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public Client(String clientName, int clientID, String emailAddress, String phoneNumber) {
		setClientID(clientID);
		setClientEmailAddress(emailAddress);
		setClientPhoneNumber(phoneNumber);
		setClientName(clientName);
	}

	/**
	 * @return
	 */
	public String getClientPhoneNumber() {
		return clientPhoneNumber;
	}

	/**
	 * @param contactInfoPhone
	 */
	private void setClientPhoneNumber(String contactInfoPhone) {
		this.clientPhoneNumber = contactInfoPhone;
	}

	/**
	 * @return the clients email address
	 */
	public String getClientEmailAddress() {
		return clientEmailAddress;
	}

	/**
	 * @param contactInfoEmail
	 */
	private void setClientEmailAddress(String contactInfoEmail) {
		this.clientEmailAddress = contactInfoEmail;
	}

	/**
	 * @return the clients name
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * 
	 * @param clientName
	 */
	private void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the clients ID
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * @param the clientID to set
	 */
	private void setClientID(int clientID) {
		this.clientID = clientID;
	}

	@Override
	public String toString() {
		return "Client " + getClientID() + " : " + getClientName();
	}

}
