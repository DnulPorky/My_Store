package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.Customer;
import entities.Department;
import entities.Store;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on stores
 *  
 * @author JetLi
 *
 */
public class StoreProcessor {
	/**
	 * Inserts a store into db
	 * 
	 * @param store
	 * @return
	 */
	public Store insertStore(Store store) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "insert into stores values(" + store.getId() + ", '" + store.getName() + "')";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from stores where id=" + store.getId());
			rs.next();
			return new Store(rs.getInt(1), rs.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified store from db
	 * 
	 * @param id of requested store
	 * @return requested store
	 */
	public Store getStore(int id) {
		Store s = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from stores where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			s = new Store(rs.getInt(1), rs.getString(2));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	/**
	 * 
	 * @return a linked lists of all stores from db
	 */
	public LinkedList<Store> getStores() {
		LinkedList<Store> storeList = new LinkedList<Store>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "select * from stores";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				storeList.add(new Store(result.getInt(1), result.getString(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return storeList;
	}
	/**
	 * Updates specified store from db
	 * 
	 * @param id of the store to be updated
	 * @param store who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 * 
	 */
	Status updateStore(int id, Store store) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String str = store.getName();
			String sql = "update stores set name='" + str + "' where id=" + id;
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
	 * Removes specified store from db
	 * @param id of store to be deleted
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteStore(int id) {
		// return Status.OK sau Status.NOT_OK;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
			Statement st = con.createStatement();
			String sql = "delete from stores where id=" + id;
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
//		StoreProcessor sp = new StoreProcessor();
//		Store store = sp.insertStore(new Store(3, "Store3"));
//		Store store = sp.getStore(2);
//		System.out.println(store.getId() + " " + store.getName());
//		ListIterator<Store> itr = sp.getStores().listIterator();
//		while(itr.hasNext()){
//			Store it = itr.next();
//			System.out.println(">>>>>>> " + it.getId() + " " + it.getName());
//		}
//		Item it1 = new Item(2, 3, "Item2", 2.0);
//		System.out.println(sp.updateStore(3, new Store(3, "StoreNou")));
//		System.out.println(sp.deleteStore(3));
//	}


}
