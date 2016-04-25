/*
 * Program 5: Web Server Application
 * Written by Wesley Harrison and Karl Archinal
 * CS1122
 */
public class Orders  {
	String name; //The name of the person adding the item to the order.
	InventoryItem item; //The actual item of the item to be added to the order.
	int quantity; //The number of item(s).
	double priceper = 0; //The price of each item.
	int totalcost = 0; //The final, total price of the order.

	/*
	 * Sets the name, item, and quantity variables to itself.
	 */
	public Orders( String name, InventoryItem item, int quantity ) {
		this.name = name;
		this.item = item;
		this.quantity = quantity;
	}



}


