package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.Item;
import entities.WishList;
import main.Main;
import utils.CustomerItem;
import utils.Status;
/**
 * Class describing possible operations on wish lists
 * 
 * @author JetLi
 *
 */
public class WishListProcessor {
	/**
	 * Inserts a wish list into db
	 * 
	 * @param wishList to be inserted
	 * @return inserted wish list
	 */
	public WishList insertWishList(WishList wishList) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "insert into wish_lists values(" + wishList.getId() + ", " + wishList.getCustomerId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from wish_lists where id=" + wishList.getId());
			rs.next();
			return new WishList(rs.getInt(1), rs.getInt(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves specified wish list from db
	 *  
	 * @param id of requested wish list
	 * @return requested wish list
	 */
	WishList getWishList(int id) {
		WishList wList = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from wish_lists where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			wList = new WishList(rs.getInt(1), rs.getInt(2));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wList;
	}
	/**
	 * 
	 * @return a linked list of all wish list from db
	 */
	public LinkedList<WishList> getWishLists() {
		LinkedList<WishList> wishLists = new LinkedList<WishList>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from wish_lists";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				wishLists.add(new WishList(result.getInt(1), result.getInt(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wishLists;
	}
	/**
	 * Updates specified wish list from db
	 *  
	 * @param id of wish list to be updated
	 * @param wishList who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateWishList(int id, WishList wishList) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "update wish_lists set customer_id=" + wishList.getCustomerId() + " where id=" + id;
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
	 * Removes specified wish list from db
	 * 
	 * @param id of wish list to be deleted
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteWishList(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "delete from wish_lists where id=" + id;
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
	 * Retrieves a linked list of all items from specified customer wish list
	 * 
	 * @param customerId of customer that owns the wish list
	 * @return a linked list of all items from specified customer wish list
	 */
	public ArrayList<CustomerItem> getCustomerWishListItems(int customerId){
		ArrayList<CustomerItem> customerScItems = new ArrayList<CustomerItem>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select customers.name, items.id, items.dept_id, items.name, items.price from items"
					+ " inner join wish_list_items on items.id = wish_list_items.item_id"
					+ " inner join wish_lists on wish_lists.id = wish_list_items.wish_list_id"
					+ " join customers on customers.id = wish_lists.customer_id where customers.id = " + customerId;
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				customerScItems.add(new CustomerItem(result.getString(1), new Item(result.getInt(2),result.getInt(3), result.getString(4), result.getDouble(5))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerScItems;
	}
	

//	public static void main(String[] args) {
//		WishListProcessor cp = new WishListProcessor();
//		for(CustomerItem wItem : cp.getCustomerWishListItems(3)){
//			System.out.println(wItem.toString());
//		}
//	}
}
