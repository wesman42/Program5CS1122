
public class Orders {
	String name;
	InventoryItem item;
	int quantity;

	public Orders(String name, InventoryItem item, int quantity ) {
		this.name = name;
		this.item = item;
		this.quantity = quantity;
		
	}

	public boolean getStatus() {

		return false;
	}

}
