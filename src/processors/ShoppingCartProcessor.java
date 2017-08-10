package processors;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.Item;
import entities.ShoppingCart;
import main.Main;
import utils.CustomerItem;
import utils.Status;
/**
 * Class describing possible operations on shopping carts
 * 
 * @author JetLi
 *
 */
public class ShoppingCartProcessor {
		/**
		 * Inserts a shopping cart into db
		 * 
		 * @param shoppingCart to be added
		 * @return inserted shopping cart
		 */
		public ShoppingCart insertshoppingCart(ShoppingCart shoppingCart) {
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "insert into shopping_carts values(" + shoppingCart.getId() + ", " + shoppingCart.getCustomerId() + ", " + shoppingCart.getBudget() + ")";
				st.executeUpdate(sql);
				ResultSet rs = st.executeQuery("Select * from shopping_carts where id=" + shoppingCart.getId());
				rs.next();
				return new ShoppingCart(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * Retrieves a specified shopping cart from db
		 * 
		 * @param id of requested shopping cart
		 * @return requested shopping cart
		 */
		ShoppingCart getshoppingCart(int id) {
			ShoppingCart sCart = null;
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "select * from shopping_carts where id=" + id;
				ResultSet rs = st.executeQuery(sql);
				rs.next();
				sCart = new ShoppingCart(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sCart;
		}
		/**
		 * 
		 * @return a linked list of all shopping carts from db
		 */
		public LinkedList<ShoppingCart> getShoppingCarts() {
			LinkedList<ShoppingCart> shoppingCartList = new LinkedList<ShoppingCart>();
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "select * from shopping_carts";
				ResultSet result = st.executeQuery(sql);
				while (result.next()) {
					shoppingCartList.add(new ShoppingCart(result.getInt(1), result.getInt(2), result.getDouble(3)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return shoppingCartList;
		}
		/**
		 * Updates a specified shopping cart from db
		 * 
		 * @param id of the shopping cart to be updated
		 * @param shoppingCart who's data will be used for update
		 * @return status 'OK' if success, 'NOT_OK' if failure
		 */
		Status updateShoppingCart(int id, ShoppingCart shoppingCart) {
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "update shopping_carts set customer_id=" + shoppingCart.getCustomerId() + ", budget=" + shoppingCart.getBudget() + " where id=" + id;
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
		 * Removes a certain shopping cart from db
		 * @param id of the shopping cart to be removed
		 * @return status 'OK' if success, 'NOT_OK' if failure
		 */
		public Status deleteShoppingCart(int id) {
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "delete from shopping_carts where id=" + id;
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
		 * Retrieves a list of all items from specified customr's shopping cart 
		 * @param customerId 
		 * @return linked list of all items from specified customr's shopping cart
		 */
		public ArrayList<CustomerItem> getCustomerShoppingCartItems(int customerId){
			ArrayList<CustomerItem> customerScItems = new ArrayList<CustomerItem>();
			try {
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user, Main.password);
				Statement st = con.createStatement();
				String sql = "select customers.name, items.id, items.dept_id, items.name, items.price from items"
						+ " inner join shopping_cart_items on items.id = shopping_cart_items.item_id"
						+ " inner join shopping_carts on shopping_carts.id = shopping_cart_items.shopping_cart_id"
						+ " join customers on customers.ID = shopping_carts.customer_id where customers.id = " + customerId;
				ResultSet result = st.executeQuery(sql);
				while (result.next()) {
					customerScItems.add(new CustomerItem(result.getString(1), new Item(result.getInt(2),result.getInt(3), result.getString(4), result.getDouble(5))));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return customerScItems;
		}
		/**
		 * Retrieves total value of items from specified shopping cart
		 * 
		 * @param customerId of customer that owns the specified shopping cart
		 * @return total value of items from specified shopping cart
		 */
		public Double getShoppingCartValue(int customerId){
			Double totalPrice = 0.00;
			for(CustomerItem wItem : this.getCustomerShoppingCartItems(customerId)){
				totalPrice += wItem.getItem().getPrice();
			}
			return totalPrice;
		}
		
//		public static void main(String[] args) {
//			ShoppingCartProcessor dp = new ShoppingCartProcessor();
//			ShoppingCart shoppingCart = dp.insertshoppingCart(new ShoppingCart(2, 1, 180));
//			ShoppingCart shoppingCart = dp.getshoppingCart(1);
//			System.out.println(shoppingCart.getId() + " " + shoppingCart.getCustomerId() + " " + shoppingCart.getBudget());
//			ListIterator<ShoppingCart> itr = dp.getShoppingCarts().listIterator();
//			while(itr.hasNext()){
//				ShoppingCart sc = itr.next();
//				System.out.println(">>>>>>> " + sc.getId() + " " + sc.getCustomerId() + " " + sc.getBudget());
//			}
//			Item it1 = new Item(2, 3, "Item2", 2.0);
//			System.out.println(dp.updateShoppingCart(1, new ShoppingCart(2, 1, 175)));
//			System.out.println(dp.deleteShoppingCart(2));


//				ShoppingCartProcessor cp = new ShoppingCartProcessor();
//				for(CustomerItem wItem : cp.getCustomerShoppingCartItems(1)){
//					System.out.println(wItem.toString());
//				}
//				System.out.println(cp.getShoppingCartValue(1));
//		}
}
