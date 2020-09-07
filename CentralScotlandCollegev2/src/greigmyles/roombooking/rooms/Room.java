package greigmyles.roombooking.rooms;

import java.io.Serializable;

public class Room implements Comparable<Room>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noOfComputers, breakoutDesks, roomNo;
	private boolean smartboard, printer;

	/**
	 * 
	 * @param roomNo        jav
	 * @param noOfComputers
	 * @param breakoutDesks
	 * @param smartboard
	 * @param printer
	 */
	public Room(int roomNo, int noOfComputers, int breakoutDesks, boolean smartboard, boolean printer) {
		this.roomNo = roomNo;
		this.noOfComputers = noOfComputers;
		this.breakoutDesks = breakoutDesks;
		this.smartboard = smartboard;
		this.printer = printer;
	}

	/**
	 * @return the noOfComputers
	 */
	public int getNoOfComputers() {
		return noOfComputers;
	}

	/**
	 * @return the breakoutDesks
	 */
	public int getBreakoutDesks() {
		return breakoutDesks;
	}

	/**
	 * @return the roomNo
	 */
	public int getRoomNo() {
		return roomNo;
	}

	/**
	 * @return the smartboard boolean
	 */
	public boolean hasSmartboard() {
		return smartboard;
	}

	/**
	 * @return the printer boolean
	 */
	public boolean hasPrinter() {
		return printer;
	}

	/**
	 * 
	 * @param printer
	 * @return printer - returns string value depending on boolean
	 */
	public String hasPrinter(boolean printer) {
		return (printer) ? "Yes" : "No";

	}

	/**
	 * Does this room have a smartboard
	 * 
	 * @param smartboard
	 * @return smartboard - returns string value depending on boolean
	 */
	public String hasSmartboard(boolean smartboard) {
		return (smartboard) ? "Yes" : "No";

	}

	@Override
	public String toString() {
		return "[Room Number = " + getRoomNo() + ", Computers = " + getNoOfComputers() + ", Breakout Desks = "
				+ getBreakoutDesks() + ", Smartboard = " + hasSmartboard(hasSmartboard()) + ", Printer = "
				+ hasPrinter(hasPrinter()) + "]";
	}

	/**
	 * The overridden compareTo method first checks the amount of computers in the
	 * rooms, if the rooms are the same it will check the number of break out desks.
	 * SHould this also be the same, then it will order by the room number.
	 */
	@Override
	public int compareTo(Room room) {
		if (room.getNoOfComputers() > this.noOfComputers) {
			return -1;
		} else if (room.getNoOfComputers() == this.noOfComputers) {
			if (room.getBreakoutDesks() > this.breakoutDesks) {
				return -1;
			} else if (room.getBreakoutDesks() == this.breakoutDesks) {
				if (room.getRoomNo() > this.roomNo) {
					return -1;
				} else if (room.getRoomNo() == this.roomNo) {
					return 0;
				} else {
					return 1;
				}
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

}
