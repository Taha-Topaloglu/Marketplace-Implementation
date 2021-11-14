package elements;
/**Wallet class that keeps track of each trader's assets*/
public class Wallet {
	/**fields of wallet and constructor*/
	private double dollars; private double coins; private double blockedDollars; private double blockedCoins;
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}
	
	/**getter and setter methods for fields*/
	public double getDollars() {
		return dollars;
	}
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}
	public double getCoins() {
		return coins;
	}
	public void setCoins(double coins) {
		this.coins = coins;
	}
	public double getBlockedDollars() {
		return blockedDollars;
	}
	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}
	public double getBlockedCoins() {
		return blockedCoins;
	}
	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
	

}
