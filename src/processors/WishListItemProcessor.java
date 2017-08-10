package processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;

import entities.WishListItem;
import main.Main;
import utils.Status;
/**
 * Class describing possible operations on wish list items
 * 
 * @author JetLi
 *
 */
public class WishListItemProcessor {
	/**
	 * Inserts an wish list item into db
	 * 
	 * @param wishListItem to be inserted 
	 * @return inserted item
	 */
	public WishListItem insertWishListItem(WishListItem wishListItem) {
		try {
			for (WishListItem sci : getWishListItems()) {
				if (sci.getItemId() == wishListItem.getItemId()
						&& sci.getWishListId() == wishListItem.getWishListId()) {
					System.out.println("Item having id: " + wishListItem.getId()
							+ " is already in the wish list with id: " + wishListItem.getWishListId());
					return null;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "insert into wish_list_items values(" + wishListItem.getId() + ", "
					+ wishListItem.getWishListId() + ", " + wishListItem.getItemId() + ")";
			st.executeUpdate(sql);
			ResultSet rs = st.executeQuery("Select * from wish_list_items where id=" + wishListItem.getId());
			rs.next();
			return new WishListItem(rs.getInt(1), rs.getInt(2), rs.getInt(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retrieves a specified wish list item from db
	 * 
	 * @param id of requested item
	 * @return requested item
	 */
	WishListItem getWishListItem(int id) {
		WishListItem sCartItem = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from wish_list_items where id=" + id;
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			sCartItem = new WishListItem(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sCartItem;
	}
	/**
	 * 
	 * @return a linked list of all wish list items from db
	 */
	public LinkedList<WishListItem> getWishListItems() {
		LinkedList<WishListItem> wishListList = new LinkedList<WishListItem>();
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "select * from wish_list_items";
			ResultSet result = st.executeQuery(sql);
			while (result.next()) {
				wishListList.add(new WishListItem(result.getInt(1), result.getInt(2), result.getInt(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wishListList;
	}
	/**
	 * Updates specified wish list item from db
	 * 
	 * @param id of item to be updated
	 * @param wishListItem who's data will be used for update
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	Status updateWishListItem(int id, WishListItem wishListItem) {
		try {

			for (WishListItem sci : getWishListItems()) {
				if (sci.getItemId() == wishListItem.getItemId()
						&& sci.getWishListId() == wishListItem.getWishListId()) {
					System.out.println("Item having id: " + wishListItem.getItemId()
							+ " is already in the wish list with id: " + wishListItem.getWishListId());
					return Status.NOT_OK;
				}
			}
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "update wish_list_items set wish_list_id=" + wishListItem.getWishListId()
					+ ", item_id=" + wishListItem.getItemId() + " where id=" + id;
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
	 * Removes specified wish list item from db
	 * @param id of item to be removed
	 * @return status 'OK' if success, 'NOT_OK' if failure
	 */
	public Status deleteWishListItem(int id) {
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", Main.user,
					Main.password);
			Statement st = con.createStatement();
			String sql = "delete from wish_list_items where id=" + id;
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
//		WishListItemProcessor dp = new WishListItemProcessor();
//		 WishListItem wishListItem = dp.insertWishListItem(new
//		 WishListItem(3, 2, 1));
//		 WishListItem wishListItem = dp.getWishListItem(3);
//		 System.out.println(wishListItem.getId() + " " +
//		 wishListItem.getWishListId() + " " +
//		 wishListItem.getItemId());
//		 ListIterator<WishListItem> itr =
//		 dp.getWishListItems().listIterator();
//		 while(itr.hasNext()){
//		 WishListItem sc = itr.next();
//		 System.out.println(">>>>>>> " + sc.getId() + " " +
//		 sc.getWishListId() + " " + sc.getItemId());
//		 }
//		 Item it1 = new Item(2, 3, "Item2", 2.0);
//		 System.out.println(dp.updateWishListItem(1, new
//		 WishListItem(2, 2, 2)));
//		 System.out.println(dp.deleteWishListItem(2));
//	}
}
