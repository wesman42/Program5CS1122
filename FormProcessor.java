import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class FormProcessor {

	File file;
	String request;
	public FormProcessor(File file, String request) {
		this.file = file;
		this.request = request;
	}


	public File generateOrderUpdate() throws FileNotFoundException {
		File updatedHTML = new File(file.getName());
		PrintWriter update = new PrintWriter(updatedHTML);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()){
			if (read.hasNext("<body>")) {
				update.println("<body>");
				update.println("<p><i><font color=\"red\"> Your Order Has Been Entered Into the System! </font></i>");
				update.println("<i><font color=\"red\"> Check the fulfilled orders list to see if it was fulfilled! </font></i></p>");
				read.nextLine();
				read.nextLine();
			} else {
				update.println(read.nextLine());
			}
		}
		read.close();
		update.close();
		return updatedHTML;
	}

	public File generateInventoryUpdate() throws FileNotFoundException {
		File updatedHTML = new File(file.getName());
		PrintWriter update = new PrintWriter(updatedHTML);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()){
			if (read.hasNext("<body>")) {
				update.println("<body>");
				update.println("<p><i><font color=\"red\"> Your item has been added to the Inventory List! </font></i>");
				update.println("<i><font color=\"red\"> Check the fulfilled orders list to see if it was fulfilled! </font></i></p>");
				read.nextLine();
				read.nextLine();
			} else {
				update.println(read.nextLine());
			}
		}
		read.close();
		update.close();
		return updatedHTML;

	}

	public Orders getOrder() {
		String name = "";
		String item = "";
		int quantity = 0;

		name = request.substring(request.indexOf("?Name=")+ 6,request.indexOf('&'));

		item = request.substring(request.indexOf( "&Item=" )+ 6,request.indexOf( "&Quantity" ));

		quantity = Integer.parseInt(request.substring(request.indexOf( "&Quantity=" )+ 10,request.indexOf( "&Sub" )));

		System.out.println(name);
		System.out.println(item);
		System.out.println(quantity);

		return new Orders(name, new InventoryItem(item), quantity);

	}

	public InventoryItem getInventoryItem() {
		String name = "";
		int quantity = 0;
		double costPer = 0.0;

		name = request.substring(request.indexOf("?Name=")+ 6,request.indexOf('&'));

		quantity = Integer.parseInt(request.substring(request.indexOf( "&Quantity=" )+ 10,request.indexOf( "&Cost" )));

		costPer = Double.parseDouble((request.substring(request.indexOf( "&CostPer=" )+ 9,request.indexOf( "&Sub" ))));
		return new InventoryItem(name,quantity,costPer);	
	}

	public void getFulfillment() {

	}
	public File getOutstanding()throws FileNotFoundException {
		File updatedHTML = new File(file.getName());
		PrintWriter update = new PrintWriter(updatedHTML);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()){
			if (read.hasNext("<p>")) {
				update.println("<p>");
				if (Program5.outstandingOrders.isEmpty()) {
					update.println("<i><font color=\"red\"> There are currently NO outstanding orders available. </font></i>");
				} else {
					for(int i = 0; i < Program5.outstandingOrders.size(); i++) {
							Orders order = Program5.outstandingOrders.get(i);
						System.out.println(i + ":" + "<br>" + "NAME: " + order.name);
						update.println(i + ":" + "<br>" + "NAME: " + order.name);
							update.println("<br>" + "Item: " + order.item.name);
							update.println("<br>" + "Quantity: " + order.quantity);
						
					}
				}
			} else {
				update.println(read.nextLine());
			}
		}
		read.close();
		update.close();
		return updatedHTML;
	}
	public File getFulfillmentList() throws FileNotFoundException {
		File updatedHTML = new File(file.getName());
		PrintWriter update = new PrintWriter(updatedHTML);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()){
			if (read.hasNext("<p>")) {
				update.println("<p>");
				if (Program5.fulfilledOrders.isEmpty()) {
					update.println("<i><font color=\"red\"> There are currently NO fulfilled orders available. </font><font color=\"black\"></i>");
				} else {
					for(int i = 0; i < Program5.fulfilledOrders.size(); i++) {
						if (!Program5.fulfilledOrders.isEmpty()) {
							Orders order = Program5.fulfilledOrders.element();
							update.println(i+ ":" + "<br>" + "NAME: " + order.name);
							update.println("<br>" + "Item: " + order.item.name);
							update.println("<br>" + "Quantity: " + order.quantity);
							update.println("<br>" + "Price Per Item " + (order.item.costPerItem * 1.5));
							update.println("<br>" + "Total: " + (order.item.costPerItem * order.quantity));
						} else {
							break;
						}
					}
				}
				read.nextLine();
				read.nextLine();
			} else {
				update.println(read.nextLine());
			}
		}
		read.close();
		update.close();
		return updatedHTML;
	}
	
	public void checkFulfillmentStatus() {
		Orders[] orders = Program5.outstandingOrders.toArray( new Orders[Program5.outstandingOrders.size()]);
		InventoryItem[] inventory = Program5.inventoryList.toArray(new InventoryItem[Program5.inventoryList.size()]);
		for (int i = 0; i < orders.length; i++) {
				for (int j = 0; j < inventory.length; j++ ) {
					if( orders[i].item.name.equals(inventory[j].name) && (orders[i].quantity < inventory[j].stockQuantity)) {
						inventory[j].stockQuantity -= orders[i].quantity;
						orders[i].item = inventory[j];
						Program5.fulfilledOrders.add(orders[i]);
						Program5.outstandingOrders.remove(orders[i]);
						
					}
				}
		}
	}
	
	
}
