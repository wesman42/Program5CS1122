/*
 * Program 5: Web Server Application
 * Written by Wesley Harrison and Karl Archinal
 * CS1122
 */
public class InventoryItem {
	String name; //The name of the inventory item.
	int stockQuantity; //The number of each inventory item at the current time.
	double costPerItem; //The cost of each inventory item.

	/*
	 * Sets the name item to itself, sets the stockQuantity to num, and sets the costPerItem to cost.
	 */
	public InventoryItem(String name, int num, double cost){
		this.name = name;
		stockQuantity = num;
		costPerItem = cost;
	}

	/*
	 * Sets the InventoryItem listing name to itself.
	 */
	public InventoryItem(String name){
		this.name = name;

	}

}
