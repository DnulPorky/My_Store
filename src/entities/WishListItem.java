package entities;
/**
 * Class describing attributes and methods of a wish list item
 * @author JetLi
 *
 */
public class WishListItem {
	/**
	 * wish list item's id
	 */
	private int id;
	/**
	 * wish list's id
	 */
	private int wishListId;
	/**
	 * item's id
	 */
	private int itemId;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param wishListId
	 * @param itemId
	 */
	public WishListItem(int id, int wishListId, int itemId){
		this.id = id;
		this.wishListId =wishListId;
		this.itemId = itemId;
	}
	/**
	 * Getter
	 * 
	 * @return wish list item's 
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
	 * @return wish list's id
	 */
	public int getWishListId() {
		return wishListId;
	}
	/** 
	 * Setter
	 * 
	 * @param wishListId
	 */
	public void setWishListId(int wishListId) {
		this.wishListId = wishListId;
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
