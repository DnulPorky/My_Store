package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import entities.Customer;
import entities.CustomerNotification;
import entities.Notification;
import main.Main;
import utils.NotificationType;
import utils.Status;
/**
 * Class describing possible operations on notifications
 * 
 * @author JetLi
 *
 */
public class NotificationProcessor {
	/**
	 * Inserts a notification into db
	 * 
	 * @param notification to be inserted
	 * @return inserted notification
	 */
	public Notification insertNotification(Notification notification) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(notification.getDate());
			String sql = "insert into notifications values(" + notification.getId() + ", '" + notification.getType()
					+ "', to_date('" + sdf + "', 'dd/mm/yyyy hh24:mi:ss'), " + notification.getDeptId() + ", "
					+ notification.getItemId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from notifications where id=(select max(id) from notifications)");
			rs.next();
			Notification notif = new Notification(rs.getInt(1), rs.getString(2), rs.getInt(4), rs.getInt(5));
			notif.setDate(rs.getTimestamp(3));
			return notif;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves specified notification from db
	 * 
	 * @param id of requested notification
	 * @return requested notification
	 */
	Notification getNotification(int id) {
		Notification notif = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from notifications where id=" + id;

			ResultSet rs = st.executeQuery(sql);
			rs.next();
			notif = new Notification(rs.getInt(1), rs.getString(2), rs.getInt(4), rs.getInt(5));
			notif.setDate(rs.getTimestamp(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notif;
	}
	/**
	 * 
	 * @return a linked list of all notifications from db
	 */
	public LinkedList<Notification> getNotifications() {
		LinkedList<Notification> notificationList = new LinkedList<Notification>();
		Notification notif = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from notifications";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				notif = new Notification(result.getInt(1), result.getString(2), result.getInt(4), result.getInt(5));
				notif.setDate(result.getTimestamp(3));

				notificationList.add(notif);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notificationList;
	}
	/**
	 * Updates a specified notification from db
	 * 
	 * @param id of notification to be updated
	 * @param notification who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateNotification(int id, Notification notification) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(notification.getDate());
			String sql = "update notifications set type='" + notification.getType() + "', notification_date = to_date('"
					+ sdf + "', 'dd/mm/yyyy hh24:mi:ss'), dept_id=" + notification.getDeptId() + ", item_id="
					+ notification.getItemId() + " where id=" + id;
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
	 * Removes a specified item from db
	 * @param id of the item to be removed
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteNotification(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "delete from notifications where id=" + id;
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
	 * Retrieves a list of all notifications received by a specified client
	 * @param customerId 
	 * @return the client's notifications list
	 */
	public HashMap<Customer, ArrayList<Notification>> getClientNotifications(int customerId) {
		HashMap<Customer, ArrayList<Notification>> myMap = new HashMap<Customer, ArrayList<Notification>>();
		Notification notif = null;
		Customer cust = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select customers.id, customers.name,"
					+ " notifications.id, notifications.type, notifications.notification_date, notifications.dept_id, notifications.item_id"
					+ " from customers"
					+ " inner join customer_notifications on customers.id = customer_notifications.customer_id"
					+ " inner join notifications on customer_notifications.notification_id = notifications.id"
					+ " where customers.id = " + customerId;
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				cust = new Customer(result.getInt(1), result.getString(2));
				notif = new Notification(result.getInt(3), result.getString(4), result.getInt(6), result.getInt(7));
				notif.setDate(result.getTimestamp(5));
				if (cust != null && notif != null) {
					ArrayList<Notification> temp = new ArrayList<Notification>();
					temp.add(notif);
					myMap.put(cust, temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myMap;
	}

//	public static void main(String[] args) {
//		NotificationProcessor ip = new NotificationProcessor();
		// Notification n = new Notification(2, "MODIFY", 2, 1);
		// System.out.println(n.getDate());
		// System.out.println(new SimpleDateFormat("dd/MM/yyyy
		// HH:mm:ss").format(n.getDate()));
		// Notification notification = ip.insertNotification(n);
		// System.out.println(notification.getId() + " " +
		// notification.getType() + " " + notification.getDate().toString() + "
		// " + notification.getDeptId() + " " + notification.getItemId());
		// Notification itm = ip.getNotification(1);
		// System.out.println(itm.getId() + " " + itm.getType() + " " +
		// itm.getDate() + " " + itm.getDeptId() + " "
		// + itm.getItemId());
		// ListIterator<Notification> itr =
		// ip.getNotifications().listIterator();
		// while(itr.hasNext()){
		// Notification it = itr.next();
		// System.out.println(">>>>>>> " + it.getId() + " " + it.getType() + " "
		// + it.getDate() + " " + it.getDeptId() + " " + it.getItemId());
		// }
		// Notification it1 = new Notification(2, 3, "Notification2", 2.0);
		// System.out.println(ip.updateNotification(1, n));
		// System.out.println(ip.deleteNotification(1));
//		for (Map.Entry<Customer, ArrayList<Notification>> ent : ip.getClientNotifications(3).entrySet()) {
//			System.out
//					.println(ent.getKey().getId() + " " + ent.getKey().getName() + " / " + ent.getValue().get(0).getId()
//							+ " " + ent.getValue().get(0).getType() + " " + ent.getValue().get(0).getDate() + " "
//							+ ent.getValue().get(0).getDeptId() + " " + ent.getValue().get(0).getItemId());
//		}
//		System.out.println(ip.getClientNotifications(7).size());
//	}
}
