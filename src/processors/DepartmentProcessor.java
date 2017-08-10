package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.Department;
import entities.Item;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on departments
 * @author JetLi
 *
 */
public class DepartmentProcessor {
	/**
	 * Inserts a department client into db
	 * 
	 * @param department to be inserted
	 * @return inserted department
	 */
	public Department insertDepartment(Department department) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "insert into departments" + " values(" + department.getId() + ", " + department.getStoreId() + ", '" + department.getName() + "')";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from departments where id=" + department.getId());
			rs.next();
			return new Department(rs.getInt(1), rs.getInt(2), rs.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified department from db
	 * @param id of requested department
	 * @return requested department
	 */
	Department getDepartment(int id) {
		Department dept = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from departments where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			dept = new Department(rs.getInt(1), rs.getInt(2), rs.getString(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}
	/**
	 * 
	 * @return a linked list of all departments from db
	 */
	public LinkedList<Department> getDepartments() {
		LinkedList<Department> departmentList = new LinkedList<Department>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from departments";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				departmentList.add(new Department(result.getInt(1), result.getInt(2), result.getString(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return departmentList;
	}
	/**
	 * Updates a specified department from db
	 * 
	 * @param id of the department to be updated
	 * @param dept who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateDepartment(int id, Department dept) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "update departments set name='" + dept.getName() + "', store_id=" + dept.getStoreId() + " where id=" + id;
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
	 * Removes a specified department from db
	 * 
	 * @param id of department to be deleted
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteDepartment(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "delete from departments where id=" + id;
			int result = st.executeUpdate(sql);
			if (result == 1) {
				con.close();
				return Status.OK;
			} else {
				con.close();
				return Status.NOT_OK;
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.NOT_OK;
	}
	/**
	 * Retrieves the list of items of specified department
	 * 
	 * @param deptId
	 * @return a linked list of all items of the specified department
	 */
	public LinkedList<Item> getDepartmentItems(int deptId) {
		LinkedList<Item> departmentItems = new LinkedList<Item>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from items where dept_id = " + deptId;
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				departmentItems.add(new Item(result.getInt(1), result.getInt(2), result.getString(3), result.getDouble(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentItems;
	}

	
//	public static void main(String[] args) {
//		DepartmentProcessor dp = new DepartmentProcessor();
//		Department department = dp.insertDepartment(new Department(6, 1, "Department6"));
//		Department department = dp.getDepartment(1);
//		System.out.println(department.getId() + " " + department.getStoreId() + " " + department.getName());
//		ListIterator<Department> itr = dp.getDepartments().listIterator();
//		while(itr.hasNext()){
//			Department it = itr.next();
//			System.out.println(">>>>>>> " + it.getId() + " " + it.getStoreId() + " " + it.getName());
//		}
//		Item it1 = new Item(2, 3, "Item2", 2.0);
//		System.out.println(dp.updateDepartment(3, new Department(3, 1, "Department3")));
//		System.out.println(dp.deleteDepartment(3));
//	}

}
