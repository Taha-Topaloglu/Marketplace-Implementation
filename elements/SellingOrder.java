package elements;
/**SellingOrder class that is child class of Order and implements comparable*/
public class SellingOrder extends Order implements Comparable<SellingOrder>{
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);

	}
	/**Overriding compare in order to implement it as instructed*/
	@Override
	public int compareTo(SellingOrder e) {
		if(price < e.price) {
			return -1;
		} else if(price > e.price) {
			return 1;
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
	public int compare(SellingOrder ord1, SellingOrder ord2) {
		return ord1.compareTo(ord2);
	}

}
