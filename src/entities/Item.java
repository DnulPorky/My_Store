package entities;
/**
 * Class describing attributes and methods of an item
 * @author JetLi
 *
 */
public class Item {
	/**
	 * item's id
	 */
	private int id;
	/**
	 * item's name
	 */
	private String name;
	/**
	 * id of the item's department
	 */
	private int deptId;
	/**
	 * items price
	 */
	private double price;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param deptId
	 * @param name
	 * @param price
	 */
	public Item(int id, int deptId, String name,  double price){
		this.id = id;
		this.deptId = deptId;
		this.name = name;
		this.price = price;
	}
	/**
	 * Getter
	 * 
	 * @return item's id
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
	 * @return item's id
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter
	 * 
	 * @return item's department id 
	 */
	public int getDeptId() {
		return deptId;
	}
	/**
	 * Setter
	 * 
	 * @param deptId
	 */
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	/**
	 * Getter
	 * 
	 * @return item's price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Setter
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return string - item's id, item's department id, item's name, item's price
	 */
	public String toString(){
		return this.id + " " + this.deptId + " " + this.name + " " + this.price; 
	}
}
