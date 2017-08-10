package entities;
/**
 * Class describing attributes and methods of a department client
 * @author JetLi
 *
 */
public class DepartmentClient {
	/**
	 * id of department client
	 */
	private int id;
	/**
	 * department's id
	 */
	private int departmentId;
	/**
	 * customer's id
	 */
	private int customerId;
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param departmentId
	 * @param customerId
	 */
	public DepartmentClient(int id, int departmentId, int customerId){
		this.id = id;
		this.departmentId = departmentId;
		this.customerId = customerId;
	}
	/**Getter
	 * 
	 * @return department client id
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
	 * @return department's id
	 */
	public int getDepartmentId() {
		return departmentId;
	}
	/**
	 * Setter
	 * 
	 * @param departmentId
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
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
