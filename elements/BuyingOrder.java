package elements;
/**BuyingOrder class that is child class of Order and implements comparable*/
public class BuyingOrder extends Order implements Comparable<BuyingOrder>{
	public BuyingOrder(int traderID, double amount, double price) {  //constructor
		super(traderID, amount, price);
	
	}
/**Overriding compare in order to implement it as instructed*/
	@Override
	public int compareTo(BuyingOrder e) {
		if(price < e.price) {
			return 1;
		} else if(price > e.price) {
			return -1;
		} else {
			if(amount < e.amount) {
				return 1;
			} else if(amount > e.amount) {
				return -1;
			} else {
				if(traderID < e.traderID) {
					return -1;
				} else if(traderID > e.traderID) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	public int compare(BuyingOrder ord1, BuyingOrder ord2) {
		return ord1.compareTo(ord2);
	}
}	


