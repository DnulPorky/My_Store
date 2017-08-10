package entities;
/**
 * Class describing attributes and methods of a shopping cart
 * @author JetLi
 *
 */
public class ShoppingCart {
	/**
	 * shopping cart's id
	 */
	private int id;
	/**
	 * customer's id
	 */
	private int customerId;
	/**
	 * shopping cart's budget
	 */
	private double budget;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param customerId
	 * @param budget
	 */
	public ShoppingCart(int id, int customerId, double budget){
		this.id = id;
		this.customerId = customerId;
		this.budget = budget;
	}
	/**
	 * Getter
	 * 
	 * @return shopping cart's id
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
	/**
	 * Getter
	 * 
	 * @return shopping cart's budget
	 */
	public double getBudget() {
		return budget;
	}
	/**
	 * Setter
	 * 
	 * @param budget
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}
}
