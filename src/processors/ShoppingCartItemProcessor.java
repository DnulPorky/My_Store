package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.ShoppingCartItem;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on shopping carts items
 * 
 * @author JetLi
 *
 */
public class ShoppingCartItemProcessor {
	/**
	 * Inserts a shopping cart item into db
	 * 
	 * @param shoppingCartItem to be added
	 * @return inserted shopping cart item
	 */
	public ShoppingCartItem insertshoppingCartItem(ShoppingCartItem shoppingCartItem) {
		try {
			for (ShoppingCartItem sci : getShoppingCartItems()) {
				if (sci.getItemId() == shoppingCartItem.getItemId()
						&& sci.getShoppingCartId() == shoppingCartItem.getShoppingCartId()) {
					System.out.println("Item having id: " + shoppingCartItem.getId()
							+ " is already in the shopping cart with id: " + shoppingCartItem.getShoppingCartId());
					return null;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "insert into shopping_cart_items values(" + shoppingCartItem.getId() + ", "
					+ shoppingCartItem.getShoppingCartId() + ", " + shoppingCartItem.getItemId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from shopping_cart_items where id=" + shoppingCartItem.getId());
			rs.next();
			return new ShoppingCartItem(rs.getInt(1), rs.getInt(2), rs.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified shopping cart item
	 * 
	 * @param id of requested shopping cart item
	 * @return requested shopping cart item
	 */
	ShoppingCartItem getshoppingCartItem(int id) {
		ShoppingCartItem sCartItem = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from shopping_cart_items where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			sCartItem = new ShoppingCartItem(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sCartItem;
	}
	/**
	 * 
	 * @return a linked list of all shopping cart items from db
	 */
	public LinkedList<ShoppingCartItem> getShoppingCartItems() {
		LinkedList<ShoppingCartItem> shoppingCartList = new LinkedList<ShoppingCartItem>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from shopping_cart_items";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				shoppingCartList.add(new ShoppingCartItem(result.getInt(1), result.getInt(2), result.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoppingCartList;
	}
	/**
	 * Updates a specified shopping cart item from db
	 * 
	 * @param id of the shopping cart to be updated
	 * @param shoppingCartItem who's data will be use for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateShoppingCartItem(int id, ShoppingCartItem shoppingCartItem) {
		try {

			for (ShoppingCartItem sci : getShoppingCartItems()) {
				if (sci.getItemId() == shoppingCartItem.getItemId()
						&& sci.getShoppingCartId() == shoppingCartItem.getShoppingCartId()) {
					System.out.println("Item having id: " + shoppingCartItem.getId()
							+ " is already in the shopping cart with id: " + shoppingCartItem.getShoppingCartId());
					return Status.NOT_OK;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "update shopping_cart_items set shopping_cart_id=" + shoppingCartItem.getShoppingCartId()
					+ ", item_id=" + shoppingCartItem.getItemId() + " where id=" + id;
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
	 * Removes a specified shopping cart item from db
	 * @param id of the shopping cart item to be deleted
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteShoppingCartItem(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "delete from shopping_cart_items where id=" + id;
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
//		ShoppingCartItemProcessor dp = new ShoppingCartItemProcessor();
		// ShoppingCartItem shoppingCartItem = dp.insertshoppingCartItem(new
		// ShoppingCartItem(2, 1, 1));
		// ShoppingCartItem shoppingCartItem = dp.getshoppingCartItem(2);
		// System.out.println(shoppingCartItem.getId() + " " +
		// shoppingCartItem.getShoppingCartId() + " " +
		// shoppingCartItem.getItemId());
//		 ListIterator<ShoppingCartItem> itr =
//		 dp.getShoppingCartItems().listIterator();
//		 while(itr.hasNext()){
//		 ShoppingCartItem sc = itr.next();
//		 System.out.println(">>>>>>> " + sc.getId() + " " +
//		 sc.getShoppingCartId() + " " + sc.getItemId());
//		 }
		// Item it1 = new Item(2, 3, "Item2", 2.0);
//		 System.out.println(dp.updateShoppingCartItem(1, new
//		 ShoppingCartItem(2, 2, 3)));
		// System.out.println(dp.deleteShoppingCartItem(2));
//	}
}
