package entities;
/**
 * Class describing attributes and methods of a customer notification 
 * @author JetLi
 *
 */

public class CustomerNotification {
	/**
	 * customer notification id;
	 */
	private int id;
	/**
	 * id of the customer
	 */
	private int customerId;
	/**
	 * id of notification
	 */
	private int notificationId;
	/**
	 * Constructor
	 * @param id
	 * @param customerId
	 * @param notificationId
	 */
	public CustomerNotification(int id, int customerId, int notificationId){
		this.id = id;
		this.customerId = customerId;
		this.notificationId = notificationId;
	}
	/**
	 * Getter
	 * @return customer notification's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter
	 * @param customer notification's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter
	 * @return customer's id
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * Setter
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * Getter
	 * @return notificationId
	 */
	public int getNotifcationId() {
		return notificationId;
	}
	/**
	 * Setter
	 * @param notifcationId
	 */
	public void setNotifcationId(int notifcationId) {
		this.notificationId = notifcationId;
	}
}
