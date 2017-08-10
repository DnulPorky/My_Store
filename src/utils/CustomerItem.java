package utils;

import entities.Item;
/**
 * Class describing an item from a customer's shopping cart/wish list
 * @author JetLi
 *
 */
public class CustomerItem {
	/**
	 * Name of the customer who owns the shopping cart/wish list containing this item
	 */
	private String name;
	/**
	 * Item from shopping cart/wish list
	 */
	private Item item;
	/**
	 * Constuctor
	 * 
	 * @param name
	 * @param item
	 */
	public CustomerItem(String name, Item item){
		this.name = name;
		this.item = item;
	}
	/**
	 * Getter
	 * 
	 * @return customer's name
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
	 * @return item from shopping cart/wish list
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * Setter
	 * 
	 * @param item
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	@Override
	public String toString(){
		return "Client: " + this.name +", Item id: " + item.getId() + ",  Item name: " + item.getName() + ", Item dept: " + item.getDeptId() + ", Item price: " + item.getPrice();
	}
}
