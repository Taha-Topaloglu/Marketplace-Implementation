package elements;
/**order class used for creating orders*/
public class Order {
	public double amount;public double price; int traderID; 
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID; this.amount = amount; this.price = price;
	}

}
