package entities;
/**
 * Class describing attributes and methods of a department
 * @author JetLi
 *
 */
public class Department {
	/**
	 * department's id
	 */
	private int id;
	/**
	 * store's id
	 */
	private int storeId;
	/**
	 * department's name
	 */
	private String name;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param storeId
	 * @param name
	 */
	public Department(int id, int storeId, String name){
		this.id = id;
		this.storeId = storeId;
		this.name = name;
	}
	/**
	 * Getter
	 * 
	 * @return department's id
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
	 * @return store's id
	 */
	public int getStoreId() {
		return storeId;
	}
	/**
	 * Setter
	 * 
	 * @param storeId
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	/**
	 * Getter
	 * 
	 * @return department's name
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
}
