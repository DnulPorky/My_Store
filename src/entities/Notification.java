package entities;

import java.util.Date;

import utils.NotificationType;
/**
 * Class describing attributes and methods of a notification
 * @author JetLi
 *
 */
public class Notification {
	/**
	 * notification's id
	 */
	private int id;
	/**
	 * notification's type(ADD/REMOVE/UPDATE)
	 */
	private String type;
	/**
	 * time stamp
	 */
	private Date date;
	/**
	 * department's id
	 */
	private int deptId;
	/**
	 * item's id
	 */
	private int itemId;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param type
	 * @param deptId
	 * @param itemId
	 */
	public Notification(int id, String type, int deptId, int itemId ){
		this.id = id;
		this.date = new Date();
		this.type = type;
		this.deptId = deptId;
		this.itemId = itemId;
	}
	/**
	 * Getter
	 * 
	 * @return notification's id
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
	 * @return notification's type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Setter
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter
	 * 
	 * @return notification time stamp
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Setter
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Getter
	 * 
	 * @return notification's department id
	 */
	public int getDeptId() {
		return deptId;
	}
	/**Setter
	 * 
	 * @param deptId
	 */
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	/**
	 * Getter
	 * 
	 * @return notification's item id
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
