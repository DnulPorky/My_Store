package main;

import java.util.ArrayList;
import java.util.Map;

import entities.Customer;
import entities.DepartmentClient;
import entities.Item;
import entities.Notification;
import entities.ShoppingCartItem;
import entities.WishListItem;
import processors.DepartmentClientProcessor;
import processors.DepartmentProcessor;
import processors.NotificationProcessor;
import processors.ShoppingCartProcessor;
import processors.WishListProcessor;
import utils.CustomerItem;

/**
 * 
 *  ++ The store ++
 *  This project purpose is to build  a software that connects to an Oracle database and executes various operations over this,
 *  and commits those changes into database.
 *  Simulating an online store, this database is structured on departments, items, customers, shopping carts, wish lists, etc
 *  and each of those has its own attributes and "connections".
 *  e.g. - every department has its items, customers and notifications, each item has a name and price, each customer has 
 *  his name and his own shopping cart and wish list, each of those having specific items, and so on.
 *  The database's tables are connected in such manner so real life scenarios and demands to be met(
 *  e.g - 
 *   - if a customer adds an item to his shopping cart, he's automatically added as a client of the department this item belongs to
 *  and in the future he will receive notifications from this department;
 *   - if an item's price is modified, the shopping cart's budget where this item is present is modified accordingly.
 *   if the budget don't afford the new price, than item is removed from that shopping cart and it's price is returned to the budget;
 *    - if an item is removed, it will be removed from all shopping carts and wish lists it was present and the budget will be readjusted;
 *    - if an item is removed, added or modified to/from a department, a notification will be automatically send to 
 *    all department's clients, and this notification will have its time stamp, the nature of modification(remove/add/update) and 
 *    details of the item is concerning;
 *    - examples could continue, to see all the features of this project please read the documentation:) 
 *  Because the project lacks a graphical interface main class from bellow was written just for demonstrative purposes.
 *  It shows some examples of possible operations such as:
 *   - modify tables - insert/delete/select/update every table;
 *   - after inserting/removing/updating an item, a notification regarding this operation will be generated automatically and
 *   it will be send to all the client of the item's department;
 *	 - after adding/removing items to shopping cart/wish lists, 
 *	 shopping cart containing it adjusts its budget automatically or rejects the item if it isn't affordable;
 *   - select items of a department along with their attributes(id, name, price);
 *   - select clients of a department;
 *   - select total value of items from a shopping cart;
 *   - select notifications for a customer;
 *   - select items from a client shopping cart/wish list;
 *   - so on;
 *   
 * @author JetLi
 *
 */

public class Main {
	public static String user = "gogu";
	public static String password = "gogaie";
	
	public static void main(String[] args) {
		/**
		 * list items of specified department - param - departments ID
		 */
		DepartmentProcessor dp = new DepartmentProcessor();
		for(Item it : dp.getDepartmentItems(2)){
			System.out.println(it.toString());
		}
		/**
		 * list clients of specified department - param - department ID
		 */
		DepartmentClientProcessor dcp = new DepartmentClientProcessor();
		for(Customer c : dcp.getDepartmentCustomers(2)){
			System.out.println(c.toString());
		}
		/**
		 * get total value of items from specified shopping cart - param - customer ID
		 */
		ShoppingCartProcessor sc = new ShoppingCartProcessor();
		System.out.println("Shopping cart value: ");
		System.out.println(sc.getShoppingCartValue(1));
		//get items from specified customer shopping cart - param - customer ID
		System.out.println("Shopping cart content: ");
		for(CustomerItem ci : sc.getCustomerShoppingCartItems(1)){
			System.out.println(ci);
		}
		/**
		 * get items from specified customer wish list - param - customer ID
		 */
		WishListProcessor wp = new WishListProcessor();
		System.out.println("Wish list content: ");
		for(CustomerItem ci : wp.getCustomerWishListItems(2)){
			System.out.println(ci);
		}
		/**
		 * get notifications for specified client
		 */
		NotificationProcessor np = new NotificationProcessor();
		System.out.println("A listing of certain client notifications(customer ID, customer Name, notif. ID,  notif. type, notif. timestamp, notif. department ID, notif. item ID):");
		for (Map.Entry<Customer, ArrayList<Notification>> ent : np.getClientNotifications(1).entrySet()) {
			System.out
				.println(ent.getKey().getId() + " " + ent.getKey().getName() + " / " + ent.getValue().get(0).getId()
						+ " " + ent.getValue().get(0).getType() + " " + ent.getValue().get(0).getDate() + " "
						+ ent.getValue().get(0).getDeptId() + " " + ent.getValue().get(0).getItemId());
		}
	}
}
