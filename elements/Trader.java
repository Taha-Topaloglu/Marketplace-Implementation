package elements;
/**Trader class that creates traders with given parameters and handles orders*/
public class Trader {
	/**Fields used in constructor and numberOfUsers which keeps track of trader ID*/
	private int id; private Wallet wallet; public static int numberOfUsers = -1;
	public Trader(double dollars, double coins) {
		Wallet wallet = new Wallet(dollars,coins);
		this.wallet = wallet;
		numberOfUsers++;

	}
	/**method sell gives a selling order if certain conditions are met
	 * @param double amount how many coins trader wants to buy
	 * @param double price how much dollars trader can spare for each coin
	 * @param Market market 
	 * @return 1 if order is given, -1 if it is not
	 */
	public int sell(double amount, double price, Market market) {
		if(this.getWallet().getCoins() >= amount) {
			SellingOrder order = new SellingOrder(this.getId(), amount, price);
			market.giveSellOrder(order);
			this.getWallet().setBlockedCoins(this.getWallet().getBlockedCoins() + amount);
			this.getWallet().setCoins(this.getWallet().getCoins() - amount);
			return 1;
		} else {
			return -1;
		}
		
	}
	/**method buy gives a buying order if certain conditions are met
	 * @param double amount how many coins trader wants to sell
	 * @param double price how much dollars traders wants for each unit of coin
	 * @param Markte market
	 * @return 1 if order is given, -1 if it is not
	 */
	public int buy(double amount, double price, Market market) {
		if(this.getWallet().getDollars() >= amount*price) {
			BuyingOrder order = new BuyingOrder(this.getId(), amount, price);
			market.giveBuyOrder(order);
			this.getWallet().setBlockedDollars(this.getWallet().getBlockedDollars() + amount*price);
			this.getWallet().setDollars(this.getWallet().getDollars() - amount*price);
			return 1;
		} else {
			return -1;
		}
	}
	
	
	
	/**getter and setters for necessary fields*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	public int getNumberOfUsers() {
		return numberOfUsers;
	}
	public static void setNumberOfUsers(int numberOfUsers) {
		Trader.numberOfUsers = numberOfUsers;
	}		
	
}
