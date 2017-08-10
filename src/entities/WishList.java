package entities;
/**
 * Class describing attributes and methods of a wish list
 * @author JetLi
 *
 */
public class WishList {
	/**
	 * wish list's id
	 */
	private int id;
	/**
	 * customer's id
	 */
	private int customerId;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param customerId
	 */
	public WishList(int id, int customerId){
		this.id = id;
		this.customerId = customerId;
	}
	/**
	 * Getter
	 * 
	 * @return wish list's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter
	 * 
	 * @return customer's id
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * Setter
	 * 
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
