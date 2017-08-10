package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import entities.Customer;
import entities.Item;
import main.Main;
import utils.CustomerItem;
import utils.Status;
/**
 * Class describing possible operations on customers
 * @author JetLi
 *
 */
public class CustomerProcessor implements Processor {
	/**
	 * Inserts a customer into db
	 * 
	 * @param customer to be inserted
	 * @return inserted customer
	 */
	Customer insertCustomer(Customer customer) {
		// iei o conexiune la baza de date
		// creezi sql-ul de insert be baza argumentului INSERT into customers (
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "insert into customers" + " values(" + customer.getId() + ", '" + customer.getName() + "')";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from customers where id=" + customer.getId());
			rs.next();
			return new Customer(rs.getInt(1), rs.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified customer from db
	 * 
	 * @param customer's id
	 * @return requested customer
	 */
	Customer getCustomer(int id) {
		Customer c = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from customers where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			c = new Customer(rs.getInt(1), rs.getString(2));

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	/**
	 * 
	 * @return a linked list of all customers from db
	 */
	public LinkedList<Customer> getCustomers() {
		LinkedList<Customer> customerList = new LinkedList<Customer>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from customers";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				customerList.add(new Customer(result.getInt(1), result.getString(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}
	/**
	 * Updates a customer from db
	 * 
	 * @param id the customer to be updated
	 * @param customer who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateCustomer(int id, Customer customer) {
		// select customer dupa id, pe acel obiect cu id-ul idn argument,
		// suprascrii celelalte proprieti din customer
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String str = customer.getName();
			String sql = "update customers set name='" + str + "' where id=" + id;

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
	 * Removes specified customer from db
	 * 
	 * @param id of the customer to be removed
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteCustomer(int id) {
		// return Status.OK sau Status.NOT_OK;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "delete from customers where id=" + id;
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
}
