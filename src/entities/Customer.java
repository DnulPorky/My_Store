package entities;
/**
 * Class describing attributes and methods of the customer
 * @author JetLi
 *
 */
public class Customer {
	/**
	 * Customer's id
	 */
	private int id;
	/**
	 * Customer's name
	 */
	private String name;
	/**
	 * Constructor 
	 * @param id
	 * @param name
	 */
	public Customer(int id, String name){
		this.id = id;
		this.name = name;
	}
	/**
	 * Getter 
	 * @return customer's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter 
	 * @param customer's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter
	 * @return customer's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter
	 * @param customer's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * To string
	 * @return string - customer id, customer's name
	 */
	public String toString(){
		return this.id + ", " + this.name;
	}
}
