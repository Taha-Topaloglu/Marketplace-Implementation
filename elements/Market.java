package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**Market class that every trade shares, contains main priority queues and does most of the calculations.*/
public class Market {
	/**Queues that store orders and other variables that are used to make calculations easier*/
	protected int fee;
	public int successfulTransactions = 0;
	private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder>();
	private PriorityQueue<BuyingOrder> buyingOrders = new PriorityQueue<BuyingOrder>();
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public Market(int fee) {  //constructor
		this.fee = fee;
	}
	/**method giveSellOrder takes a selling order and adds it to PQ sellingOrders
	 * @param SellingOrder order
	 * @return nothing
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	/** method giveBuyOrder takes a buying order and adds it to PQ buyingOrders
	 * @param BuyingOrder order
	 * @return nothing
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}
	/**method makeOpenMarketOperation compensates orders in order to set the price pf PQoin to given price
	 * @param price
	 * @param ArrayList<Trader> traders
	 * @return nothing
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
		try {
			while(buyingOrders.peek().price >= price) {
				BuyingOrder buyOrder = buyingOrders.peek(); 
				SellingOrder sellOrder = new SellingOrder(0,buyOrder.amount,buyOrder.price);
				giveSellOrder(sellOrder);
				checkTransactions(traders);
			}
			while(sellingOrders.peek().price <= price) {
				SellingOrder sellOrder = sellingOrders.peek(); 
				BuyingOrder buyOrder = new BuyingOrder(0,sellOrder.amount,sellOrder.price);
				giveBuyOrder(buyOrder);
				checkTransactions(traders);
			}
		} catch(Exception e) {
			
		}
	}
	/**method checkTransactions is where transactions happen until prices no longer overlap
	 * @param ArrayList<Trader> traders
	 * @return nothing
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		if(!(buyingOrders.peek() == null) && !(sellingOrders.peek() == null) && buyingOrders.peek().price >= sellingOrders.peek().price) {
			successfulTransactions++;
			while(buyingOrders.peek().price >= sellingOrders.peek().price) {
				double myFee = fee;
				int buyerId = buyingOrders.peek().traderID; int sellerId = sellingOrders.peek().traderID;
				double buyingPrice = buyingOrders.peek().price; double sellingPrice = sellingOrders.peek().price;
				double buyingAmount = buyingOrders.peek().amount; double sellingAmount = sellingOrders.peek().amount;
				Trader buyer = traders.get(buyerId); Trader seller = traders.get(sellerId);
				if(buyingAmount > sellingAmount) {
					double remainingAmount = buyingAmount - sellingAmount;
					buyer.getWallet().setBlockedDollars(buyer.getWallet().getBlockedDollars() - buyingAmount*buyingPrice); seller.getWallet().setBlockedCoins(seller.getWallet().getBlockedCoins() - sellingAmount);
					seller.getWallet().setDollars(seller.getWallet().getDollars() + (sellingAmount*sellingPrice*(1.0 - (myFee/1000))));
					buyer.getWallet().setCoins(buyer.getWallet().getCoins() + sellingAmount);
					buyer.getWallet().setDollars(buyer.getWallet().getDollars() + (sellingAmount*(buyingPrice-sellingPrice)));
					buyingOrders.poll(); sellingOrders.poll();
					buyer.buy(remainingAmount, buyingPrice, this);
				} else if(buyingAmount == sellingAmount) {
					buyer.getWallet().setBlockedDollars(buyer.getWallet().getBlockedDollars() - sellingAmount*buyingPrice); seller.getWallet().setBlockedCoins(seller.getWallet().getBlockedCoins() - sellingAmount);
					seller.getWallet().setDollars(seller.getWallet().getDollars() + (sellingAmount*sellingPrice*(1.0 - (myFee/1000))));
					buyer.getWallet().setCoins(buyer.getWallet().getCoins() + sellingAmount);
					buyer.getWallet().setDollars(buyer.getWallet().getDollars() + (buyingAmount*(buyingPrice-sellingPrice)));
					buyingOrders.poll(); sellingOrders.poll();
				} else if(buyingAmount < sellingAmount) {
					double remainingAmount = sellingAmount - buyingAmount;
					buyer.getWallet().setBlockedDollars(buyer.getWallet().getBlockedDollars() - buyingAmount*buyingPrice); seller.getWallet().setBlockedCoins(seller.getWallet().getBlockedCoins() - sellingAmount);
					seller.getWallet().setDollars(seller.getWallet().getDollars() + (buyingAmount*sellingPrice*(1.0 - (myFee/1000)))); seller.getWallet().setCoins(seller.getWallet().getCoins() + remainingAmount);
					buyer.getWallet().setCoins(buyer.getWallet().getCoins() + buyingAmount);
					buyer.getWallet().setDollars(buyer.getWallet().getDollars() + (buyingAmount*(buyingPrice-sellingPrice)));
					buyingOrders.poll(); sellingOrders.poll();
					seller.sell(remainingAmount, sellingPrice, this);
					
				} else {
					continue;
				}
				if((buyingOrders.peek() == null) || (sellingOrders.peek() == null)) {
					break;					
				}
			}
			
		}
	}
	
	/**getter and setter methods for necessary fields*/
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}
	public void setSellingOrders(PriorityQueue<SellingOrder> sellingOrders) {
		this.sellingOrders = sellingOrders;
	}
	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}
	public void setBuyingOrders(PriorityQueue<BuyingOrder> buyingOrders) {
		this.buyingOrders = buyingOrders;
	}
}
