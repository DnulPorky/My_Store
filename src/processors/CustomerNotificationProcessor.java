package processors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import entities.CustomerNotification;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on customer notifications
 * @author JetLi
 *
 */
public class CustomerNotificationProcessor {
	/**
	 * Inserts a customer notifications into db
	 * 
	 * @param customerNotification
	 * @return inserted customer notification
	 */
	public CustomerNotification insertCustomerNotification(CustomerNotification customerNotification) {
		try {
			for (CustomerNotification cn : getCustomerNotifications()) {
				if (cn.getNotifcationId() == customerNotification.getNotifcationId()
						&& cn.getCustomerId() == customerNotification.getCustomerId()) {
					System.out.println("Notification having id: " + customerNotification.getNotifcationId()
							+ " is already registered for customer with id: " + customerNotification.getCustomerId());
					return null;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "insert into customer_notifications values(" + customerNotification.getId() + ", "
					+ customerNotification.getCustomerId() + ", " + customerNotification.getNotifcationId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from customer_notifications where id=(select max(id) from customer_notifications)");
			rs.next();
			return new CustomerNotification(rs.getInt(1), rs.getInt(2), rs.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a customer notification from db
	 * 
	 * @param customer's notif id
	 * @return requested customer notification
	 */
	public CustomerNotification getCustomerNotification(int id) {
		CustomerNotification cn = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from customer_notifications where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			cn = new CustomerNotification(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cn;
	}
	/**
	 * 
	 * @return a linked list of all customers notifications from db
	 */
	public LinkedList<CustomerNotification> getCustomerNotifications() {
		LinkedList<CustomerNotification> customerNotifications = new LinkedList<CustomerNotification>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from customer_notifications";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				customerNotifications.add(new CustomerNotification(result.getInt(1), result.getInt(2), result.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerNotifications;
	}
	/**
	 * Updates a customer notification from db
	 * 
	 * @param id of the customer notification to be updated
	 * @param customerNotification who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status updateCustomerNotification(int id, CustomerNotification customerNotification) {
		try {

			for (CustomerNotification sci : getCustomerNotifications()) {
				if (sci.getNotifcationId() == customerNotification.getNotifcationId()
						&& sci.getCustomerId() == customerNotification.getCustomerId()) {
					System.out.println("Notification having id: " + customerNotification.getNotifcationId()
							+ " is already recorded for customer with id: " + customerNotification.getCustomerId());
					return Status.NOT_OK;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "update customer_notifications set customer_id=" + customerNotification.getCustomerId()
					+ ", notification_id=" + customerNotification.getNotifcationId() + " where id=" + id;
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
	 * Removes a customer notification from db
	 *  
	 * @param id of the removed customer notification
	 * @return status 'OK' if success, 'NOT OK' if failure
	 */
	public Status deleteCustomerNotification(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "delete from customer_notifications where id=" + id;
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

//	public static void main(String[] args) {
//		CustomerNotificationProcessor cnp =  new CustomerNotificationProcessor();
//		CustomerNotification cn = cnp.insertCustomerNotification(new CustomerNotification(2, 3, 40));
//		CustomerNotification cn = cnp.getCustomerNotification(141);
//		System.out.println(cnp.updateCustomerNotification(141, new CustomerNotification(1, 3, 42)));
//		System.out.println(cn.getId() + " " + cn.getCustomerId() + " " + cn.getNotifcationId());
//		System.out.println(cnp.deleteCustomerNotification(141));
//		for(CustomerNotification cn : cnp.getCustomerNotifications()){
//			System.out.println(cn.getId() + " " + cn.getCustomerId() + " " + cn.getNotifcationId());
//		}
//	}
}
