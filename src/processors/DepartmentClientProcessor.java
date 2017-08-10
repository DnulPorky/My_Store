package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Customer;
import entities.DepartmentClient;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on customers
 *
 * @author JetLi
 *
 */
public class DepartmentClientProcessor {
	/**
	 * Inserts a department client into db
	 * 
	 * @param departmentClient to be inserted
	 * @return inserted department client
	 */
	public DepartmentClient insertDepartmentClient(DepartmentClient departmentClient) {
		try {
			for (DepartmentClient dc : getDepartmentClients()) {
				if (dc.getDepartmentId() == departmentClient.getDepartmentId()
						&& dc.getCustomerId() == departmentClient.getCustomerId()) {
					System.out.println("Customer having id: " + departmentClient.getCustomerId()
							+ " is already registered as client for department with id: " + departmentClient.getDepartmentId());
					return null;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "insert into department_clients values(" + departmentClient.getId() + ", "
					+ departmentClient.getDepartmentId() + ", " + departmentClient.getCustomerId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from department_clients where id = (select max(id) from department_clients)");
			rs.next();
			return new DepartmentClient(rs.getInt(1), rs.getInt(2), rs.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified department client from db
	 * 
	 * @param id of the requested client
	 * @return requested client
	 */
	public DepartmentClient getDepartmentClient(int id) {
		DepartmentClient dc = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from department_clients where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			dc = new DepartmentClient(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}
	/**
	 * 
	 * @return a linked list of all department clients from db
	 */
	public LinkedList<DepartmentClient> getDepartmentClients() {
		LinkedList<DepartmentClient> departmentClients = new LinkedList<DepartmentClient>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from department_clients";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				departmentClients.add(new DepartmentClient(result.getInt(1), result.getInt(2), result.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentClients;
	}
	/**
	 * Updates a specified department client from db
	 * 
	 * @param id of the client to be updated
	 * @param departmentClient who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status updateDepartmentClient(int id, DepartmentClient departmentClient) {
		try {

			for (DepartmentClient dc : getDepartmentClients()) {
				if (dc.getDepartmentId() == departmentClient.getDepartmentId()
						&& dc.getCustomerId() == departmentClient.getCustomerId()) {
					System.out.println("Customer having id: " + id
							+ " is already recorded as client for department with id: " + departmentClient.getDepartmentId());
					return Status.NOT_OK;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "update department_clients set department_id = " + departmentClient.getDepartmentId()
					+ ", customer_id = " + departmentClient.getCustomerId() + " where id=" + id;
			int result = st.executeUpdate(sql);
			if (result == 1) {
				con.close();
				return Status.OK;
			} else {
				con.close();
				return Status.NOT_OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.NOT_OK;	}
	/**
	 * Removes a specified department client from db
	 * 
	 * @param id of the client to be removed
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteDepartmentClient(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "delete from department_clients where id=" + id;
			int result = st.executeUpdate(sql);
			if (result == 1) {
				con.close();
				return Status.OK;
			} else {
				con.close();
				return Status.NOT_OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.NOT_OK;
	}
	/**
	 * Returns the client's list of specified department
	 * @param deptId
	 * @return a linked list of all clients of a specified department  
	 */
	public LinkedList<Customer> getDepartmentCustomers(int deptId) {
		LinkedList<Customer> departmentCustomers = new LinkedList<Customer>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select customers.id, customers.name from customers join department_clients on customers.id = department_clients.customer_id where department_id = " + deptId;
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				departmentCustomers.add(new Customer(result.getInt(1), result.getString(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentCustomers;
	}
	
//	public static void main(String[] args) {
//		DepartmentClientProcessor dcp =  new DepartmentClientProcessor();
//		DepartmentClient dc = dcp.insertDepartmentClient(new DepartmentClient(21, 2, 2));
//		DepartmentClient dc = dcp.getDepartmentClient(112);
//		dcp.updateDepartmentClient(112, new DepartmentClient(4, 2, 2));
//		System.out.println(dc.getId() + " " + dc.getDepartmentId() + " " + dc.getCustomerId());
//		System.out.println(dcp.deleteDepartmentClient(112));
//		for(DepartmentClient dc : dcp.getDepartmentClients()){
//			System.out.println(dc.getId() + " " + dc.getDepartmentId() + " " + dc.getCustomerId());
//		}
//	}

}
