package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.Customer;
import entities.Item;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on items
 * 
 * @author JetLi
 *
 */
public class ItemProcessor {
	/**
	 * Inserts an item into db
	 * 
	 * @param item to be inserted 
	 * @return inserted item
	 */
	public Item insertItem(Item item) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "insert into items" + " values(" + item.getId() + ", " + item.getDeptId() + ", '" + item.getName() + "'," + item.getPrice() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from items where id=" + item.getId());
			rs.next();
			return new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves specified item from db
	 * 
	 * @param id of requested item
	 * @return requested item
	 */
	Item getItem(int id) {
		Item itm = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from items where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			itm = new Item(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itm;
	}
	/**
	 * 
	 * @return a linked list of all items from db
	 */
	public LinkedList<Item> getItems() {
		LinkedList<Item> itemList = new LinkedList<Item>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from items";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				itemList.add(new Item(result.getInt(1), result.getInt(2), result.getString(3), result.getDouble(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemList;
	}
	/**
	 * Updates a specified item from db
	 * 
	 * @param id of item to be updated
	 * @param item who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateItem(int id, Item item) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "update items set name='" + item.getName() + "', dept_id=" + item.getDeptId() + ", price=" + item.getPrice() + " where id=" + id;
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
	 * @param id of item to be deleted
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteItem(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "delete from items where id=" + id;
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
//		ItemProcessor ip = new ItemProcessor();
//		Item item = ip.insertItem(new Item(2, 3, "Item11", 2.5));
//		Item itm = ip.getItem(2);
//		System.out.println(item.getId() + " " + item.getDeptId() + " " + item.getName() + " " + item.getPrice());
//		ListIterator<Item> itr = ip.getItems().listIterator();
//		while(itr.hasNext()){
//			Item it = itr.next();
//			System.out.println(">>>>>>> " + it.getId() + " " + it.getDeptId() + " " + it.getName() + " " + it.getPrice());
//		}
//		Item it1 = new Item(2, 3, "Item2", 2.0);
//		System.out.println(ip.updateItem(2, it1));
//		System.out.println(ip.deleteItem(3));
//	}
}
