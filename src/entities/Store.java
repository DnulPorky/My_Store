package entities;
/**
 * Class describing attributes and methods of the store
 * @author JetLi
 *
 */
public class Store {
	/**
	 * store's id
	 */
	private int id;
	/**
	 * store's name
	 */
	private String name;
	/**
	 * constructor
	 * 
	 * @param id
	 * @param name
	 */
	public Store(int id, String name){
		this.id = id;
		this.name = name;
	}
	/**
	 * Getter
	 * 
	 * @return store's id
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
	 * @return store's name
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
