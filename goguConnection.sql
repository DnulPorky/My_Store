-----Global variable-----
CREATE PACKAGE my_package IS my_flag BOOLEAN; END;
----------
UPDATE customers SET name = 'Client3' WHERE id = 3;
CREATE TABLE stores(id INT PRIMARY KEY,  name VARCHAR2(25));
CREATE TABLE departments(ID INT PRIMARY KEY, store_id INT, CONSTRAINT fk_store_id FOREIGN KEY(store_id) REFERENCES stores(ID));
ALTER TABLE departments ADD NAME VARCHAR2(25);
CREATE TABLE items(ID INT PRIMARY KEY, dept_id INT, CONSTRAINT fk_dept_id FOREIGN KEY(dept_id) REFERENCES departments(ID));
ALTER TABLE items ADD (NAME VARCHAR2(25), price REAL);
CREATE TABLE customers(ID INT PRIMARY KEY, NAME VARCHAR2(25));
CREATE TABLE shopping_carts(ID INT PRIMARY KEY, customer_id INT, CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customers(ID), budget REAL);
CREATE TABLE shopping_cart_items(ID INT PRIMARY KEY, shopping_cart_id INT, CONSTRAINT fk_shopping_cart_id FOREIGN KEY(shopping_cart_id) REFERENCES shopping_carts(ID), item_id INT, CONSTRAINT fk_item_id FOREIGN KEY(item_id) REFERENCES items(ID));
CREATE TABLE wish_lists(ID INT PRIMARY KEY, customer_id INT, CONSTRAINT fk_client_id FOREIGN KEY(customer_id) REFERENCES customers(ID));
CREATE TABLE wish_list_items(ID INT PRIMARY KEY, wish_list_id INT,  CONSTRAINT fk_wish_list_id FOREIGN KEY(wish_list_id) REFERENCES wish_lists(ID));
CREATE TABLE department_clients(ID INT PRIMARY KEY, department_id INT NOT NULL, customer_id INT NOT NULL, CONSTRAINT fk_cl_id FOREIGN KEY(customer_id) REFERENCES customers(ID), CONSTRAINT fk_depart_id FOREIGN KEY(department_id) REFERENCES departments(ID));

ALTER TABLE wish_list_items ADD item_id INT;
ALTER TABLE wish_list_items ADD CONSTRAINT fk_wl_item_id FOREIGN KEY(item_id) REFERENCES items(ID);

CREATE TABLE notifications(ID INT, TYPE VARCHAR2(10) NOT NULL, notification_date DATE, 
dept_id INT, CONSTRAINT fk_notif_dept_id FOREIGN KEY(dept_id) REFERENCES departments(ID), 
item_id INT, CONSTRAINT fk_notif_item_id FOREIGN KEY(item_id) REFERENCES items(ID));
CREATE TABLE customer_notications (ID INT PRIMARY KEY, customer_id INT, notification_id INT, CONSTRAINT fk_cust_id FOREIGN KEY(customer_id) REFERENCES customers(ID), CONSTRAINT fk_notif_id FOREIGN KEY(notification_id) REFERENCES notifications(ID));
ALTER TABLE customer_notifications DROP CONSTRAINT fk_notif_id; 
ALTER TABLE notifications ADD(CONSTRAINT id_pk PRIMARY KEY(ID));
ALTER TABLE items MODIFY(NAME NOT NULL);
ALTER TABLE departments MODIFY(NAME NOT NULL);
ALTER TABLE customers MODIFY(NAME NOT NULL);

INSERT INTO notifications VALUES(1, 'ADD', to_date('01/01/2001 23:10:11', 'dd/mm/yyyy hh24:mi:ss', 'nls_date_language=german'),1,1);
INSERT INTO notifications VALUES(1, 'REMOVE', SYSTIMESTAMP ,1,1);
ALTER TABLE notifications ADD CONSTRAINT pk_notif PRIMARY KEY(ID);
ALTER TABLE notifications DROP   CONSTRAINT pk_notif;

------------------------------------Some joins-----------------------------------------
SELECT NAME, price FROM items INNER JOIN shopping_cart_items ON items.ID = shopping_cart_items.item_id INNER JOIN shopping_carts ON shopping_carts.ID = shopping_cart_items.shopping_cart_id WHERE shopping_carts.customer_id =1;
SELECT customers.NAME, items.ID, items.dept_id, items.NAME, items.price FROM items INNER JOIN shopping_cart_items ON items.ID = shopping_cart_items.item_id INNER JOIN shopping_carts ON shopping_carts.ID = shopping_cart_items.shopping_cart_id JOIN customers ON customers.ID = shopping_carts.customer_id WHERE customers.ID = 1;
SELECT  items.NAME, items.price  FROM items RIGHT JOIN shopping_cart_items ON items.ID = shopping_cart_items.item_id; 
SELECT customers.NAME, notifications.TYPE, notifications.ID FROM customers INNER JOIN customer_notifications ON customers.ID = customer_notifications.customer_id INNER JOIN notifications ON customer_notifications.notification_id = notifications.ID WHERE customers.ID = 1;
SELECT customers.ID, customers.NAME, notifications.ID, notifications.TYPE, notifications.notification_date, notifications.dept_id, notifications.item_id FROM customers INNER JOIN customer_notifications ON customers.ID = customer_notifications.customer_id INNER JOIN notifications ON customer_notifications.notification_id = notifications.ID WHERE customers.ID = 1;
---------------------------------------------------------------------------------------

----- Make records unique on department_clients-----
ALTER TABLE department_clients ADD CONSTRAINT unique_dept_clients UNIQUE(department_id, customer_id);
----------

----- Make records unique on shopping_cart_items-----
ALTER TABLE shopping_cart_items ADD CONSTRAINT unique_sc_items UNIQUE(shopping_cart_id, item_id);
---------- 

----- Make records unique on wish_list_items-----
ALTER TABLE wish_list_items ADD CONSTRAINT unique_wl_items UNIQUE(wish_list_id, item_id);
----------


---------------Creating a sequence and a trigger that increments and autocompletes id column(PK) om motification table---------------
CREATE SEQUENCE notif_seq START WITH 2;

CREATE OR REPLACE TRIGGER notif_auto
BEFORE INSERT ON notifications
FOR EACH ROW
BEGIN
  SELECT notif_seq.nextval INTO :NEW.ID FROM dual;
END;
----------------end of trigger-----------------

---------------------------Sequence and before insert trigger that increments and autocompletes id column on customer_notifications table---------------------------------
CREATE SEQUENCE customer_notif_seq START WITH 1;

CREATE OR REPLACE TRIGGER customer_notif_auto
BEFORE INSERT ON customer_notifications
FOR EACH ROW
BEGIN
  SELECT customer_notif_seq.nextval INTO :NEW.ID FROM dual;
END;
------------------------------end of trigger----------------------------------------------------------------------------------------------------------------------

--------------------Sequence that increments and autocompletes id column on insert into department_clients--------------------
CREATE SEQUENCE department_clients_seq START WITH 1;

CREATE OR REPLACE TRIGGER department_clients_auto
BEFORE INSERT ON department_clients
FOR EACH ROW
BEGIN
  SELECT department_clients_seq.nextval INTO :NEW.ID FROM dual;
END;
-----end of trigger-----

----------Trigger after insert on items----------
CREATE OR REPLACE TRIGGER after_insert_item
AFTER INSERT ON items
FOR EACH ROW
BEGIN
  INSERT INTO notifications VALUES(1,'ADD' ,SYSTIMESTAMP,:NEW.dept_id, :NEW.ID);
END;
----------end of trigger----------

-----Trigger before delete item that first deletes the item from wish_list_items and from shopping_cart_items list and-----
-----then modifies corespondent shopping carts budgets and creates a new 'REMOVE' notification.-----
CREATE OR REPLACE TRIGGER delete_item
BEFORE DELETE ON items
FOR EACH ROW
BEGIN
    my_package.my_flag := TRUE;
FOR rec IN
  (SELECT shopping_carts.ID FROM shopping_carts JOIN shopping_cart_items ON shopping_carts.ID = shopping_cart_items.shopping_cart_id WHERE shopping_cart_items.item_id = :OLD.ID)
  loop
    my_package.my_flag := TRUE;
    UPDATE shopping_carts SET budget = budget + :OLD.price WHERE ID = rec.ID; 
    DELETE FROM shopping_cart_items WHERE item_id = :OLD.ID;
    DELETE FROM wish_list_items WHERE item_id = :OLD.ID;
    my_package.my_flag := TRUE;
  END loop;
  my_package.my_flag := FALSE;
  INSERT INTO notifications VALUES(1,'REMOVE' ,SYSTIMESTAMP,:OLD.dept_id, :OLD.ID);
END;
-----end of trigger-----


-----Trigger before insert on shopping_cart_items that checks if-----
-----corespondent budget allows this operation and if so then modifies the budget-----
CREATE OR REPLACE TRIGGER before_insert_sc_items
BEFORE INSERT ON shopping_cart_items
FOR EACH ROW
  DECLARE 
    v_price REAL;
    v_budget REAL;
BEGIN
  SELECT items.price INTO v_price FROM items WHERE items.ID = :NEW.item_id;
  SELECT shopping_carts.budget INTO v_budget FROM shopping_carts WHERE ID = :NEW.shopping_cart_id;
  IF 
    v_budget - v_price >= 0 
    THEN 
      UPDATE shopping_carts SET budget = v_budget - v_price WHERE ID = :NEW.shopping_cart_id; 
    ELSE
      raise_application_error(-20001, 'This item cannot be added this shopping_cart because it s price exceeds budget!');
  END IF;
END;
-----end of trigger-----

----------Trigger before update on item----------
-----Check if shopping carts budgets containing the item that is about to modified allows the update and, if so, ajusts the budgets.-----
-----Otherwise removes the item from shopping cart and readjust the budget.In any case it creates a new 'UPDATE' notification.-----
CREATE OR REPLACE TRIGGER before_update_item
BEFORE UPDATE ON items
FOR EACH ROW
DECLARE CURSOR curs_select IS SELECT shopping_carts.ID, shopping_carts.budget, shopping_cart_items.item_id FROM shopping_carts JOIN shopping_cart_items 
              ON shopping_carts.ID = shopping_cart_items.shopping_cart_id WHERE shopping_cart_items.item_id = :OLD.ID;
 rec curs_select%rowtype;
BEGIN
  FOR rec IN curs_select
loop
        IF rec.budget IS NOT NULL AND rec.budget + :OLD.price - :NEW.price >= 0  
          THEN UPDATE shopping_carts SET shopping_carts.budget = shopping_carts.budget + :OLD.price - :NEW.price WHERE shopping_carts.ID = rec.ID;
        ELSE
                  my_package.my_flag := TRUE;
                  DELETE FROM shopping_cart_items WHERE item_id = :OLD.ID AND shopping_cart_id = rec.ID ;
                  UPDATE shopping_carts SET shopping_carts.budget = shopping_carts.budget + :OLD.price WHERE shopping_carts.ID = rec.ID AND rec.item_id = :OLD.ID;                  
        END IF;  
    END loop;
      my_package.my_flag := FALSE;
      INSERT INTO notifications VALUES(1,'UPDATE' ,SYSTIMESTAMP,:NEW.dept_id, :NEW.ID);
END;
----------end of trigger----------


-----Trigger after delete on shopping_cart_items that readjusts shopping_cart_budget-----
CREATE OR REPLACE TRIGGER before_delete_sc_item
BEFORE DELETE ON shopping_cart_items
FOR EACH ROW
DECLARE price REAL;
BEGIN
  IF my_package.my_flag = FALSE THEN
    SELECT items.price INTO price FROM items WHERE items.ID = :OLD.item_id;
    UPDATE shopping_carts SET shopping_carts.budget = shopping_carts.budget + price WHERE shopping_carts.ID = :OLD.shopping_cart_id;
    END IF;
END;
-----end of trigger-----



-----Trigger afer insert on shopping_carts_items-----
-----This trigger insert into department_clients table the customer who put an item into his shopping cart and the department of the item-----
-----This way the customer becomes a potential client of item's department so he will receive notifications from this department-----
CREATE OR REPLACE TRIGGER after_insert_sc_item
AFTER INSERT ON shopping_cart_items
FOR EACH ROW
DECLARE deptid INT;
  customerid INT;
  flag boolean;
BEGIN
  flag := FALSE;
  SELECT dept_id INTO deptid FROM items WHERE ID = :NEW.item_id;
  SELECT customer_id INTO customerid FROM shopping_carts WHERE ID = :NEW.shopping_cart_id;
  FOR rec IN (SELECT * FROM department_clients)
    loop
      IF rec.department_id = deptid AND rec.customer_id = customerid
        THEN
          flag := TRUE;
      END IF;
    END loop;
      IF flag = FALSE
        THEN
          INSERT INTO department_clients VALUES(1, deptid, customerid);
      END IF;
END;
-----end of trigger-----

-----Trigger after insert on wislists_items-----
-----This trigger insert into department_clients table the customer who put an item into his wish list and the department of the item-----
-----This way the customer becomes a potential client of item's department so he will receive notifications from this department-----
CREATE OR REPLACE TRIGGER after_insert_wl_item
AFTER INSERT ON wish_list_items
FOR EACH ROW
DECLARE deptid INT;
  customerid INT;
  flag boolean;
BEGIN
  flag := FALSE;
  SELECT dept_id INTO deptid FROM items WHERE ID = :NEW.item_id;
  SELECT customer_id INTO customerid FROM wish_lists WHERE ID = :NEW.wish_list_id;
  FOR rec IN (SELECT * FROM department_clients)
    loop
      IF rec.department_id = deptid AND rec.customer_id = customerid
        THEN
          flag := TRUE;
      END IF;
    END loop;
      IF flag = FALSE
        THEN
          INSERT INTO department_clients VALUES(1, deptid, customerid);
      END IF;
END;
-----end of trigger-----


-----Trigger after insert on notifications table-----
-----Every notification regards an item and after it is forwarded to the clients of item's department-----
CREATE OR REPLACE TRIGGER notification_inserted
AFTER INSERT ON notifications
FOR EACH ROW
DECLARE id_dept int;
  id_notif int;
BEGIN 
  SELECT :new.dept_id into id_dept from dual;
  SELECT :new.id into id_notif from dual;
  FoR rec IN (SELECT customer_id FROM department_clients WHERE department_id = id_dept)
    LOOP
      INSERT INTO customer_notifications VALUES (1, rec.customer_id, id_notif);
    END LOOP;
  END;
-----end of trigger-----

