package entities;
/**
 * Class describing attributes and methods of a shopping cart's item
 * @author JetLi
 *
 */
public class ShoppingCartItem {
	/**
	 * shopping cart item's id
	 */
	private int id;
	/**
	 * id of the shopping cart whom this item belongs to
	 */
	private int shoppingCartId;
	/**
	 * item's id
	 */
	private int itemId;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param shoppingCartId
	 * @param itemId
	 */
	public ShoppingCartItem(int id, int shoppingCartId, int itemId){
		this.id = id;
		this.shoppingCartId = shoppingCartId;
		this.itemId = itemId;
	}
	/**
	 * Getter
	 * 
	 * @return
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
	 * @return shopping cart's id
	 */
	public int getShoppingCartId() {
		return shoppingCartId;
	}
	/**
	 * Setter
	 * 
	 * @param shoppingCartId
	 */
	public void setShoppingCartId(int shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}
	/**
	 * Getter
	 * 
	 * @return item's id
	 */
	public int getItemId() {
		return itemId;
	}
	/**
	 * Setter
	 * 
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
}
