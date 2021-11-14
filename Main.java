package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;
public class Main {
	/**Main class that import other elements(such as market,order and trader) from other package
	 * and uses given input to print desired outputs
	 * @param args
	 * @throws FileNotFoundException
	 */
	
	public static void main(String args[]) throws FileNotFoundException {
		ArrayList<Trader> traders = new ArrayList<Trader>();
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		int A = in.nextInt();
		Random myRandom = new Random(A);
		int B = in.nextInt(); int C = in.nextInt(); int D = in.nextInt();
		int fee = B; Market market = new Market(fee);	
		int invalidQueries = 0;
		for(int i = 0; i < C; i++) {
			double myDollar = Double.parseDouble(in.next());
			double myPcoin = Double.parseDouble(in.next());
			Trader trader = new Trader(myDollar,myPcoin);
			trader.setId(trader.getNumberOfUsers());
			traders.add(trader);
		}

		for(int j = 0; j < D; j++) {
			int myQueryID = Integer.parseInt(in.next());
			
			if(myQueryID == 10) {
				int buyerId = Integer.parseInt(in.next());
				double buyPrice = Double.parseDouble(in.next());
				double buyingAmount = Double.parseDouble(in.next());
				if(traders.get(buyerId).buy(buyingAmount, buyPrice, market) == 1) {
					market.checkTransactions(traders);
				} else {
					invalidQueries += 1;
				}
			} else if(myQueryID == 20) {
				int sellerId = Integer.parseInt(in.next());
				double sellPrice = Double.parseDouble(in.next());
				double sellingAmount = Double.parseDouble(in.next());
				if(traders.get(sellerId).sell(sellingAmount, sellPrice, market) == 1) {
					market.checkTransactions(traders);
				} else {
					invalidQueries += 1;
				}
			} else if(myQueryID == 11) {
				int marketBuyerId = Integer.parseInt(in.next());
				double marketBuyingAmount = Double.parseDouble(in.next());
				if(!(market.getSellingOrders().peek() == (null))) {
					double marketBuyPrice = market.getSellingOrders().peek().price;
					if(traders.get(marketBuyerId).buy(marketBuyingAmount, marketBuyPrice, market) == 1) {
						market.checkTransactions(traders);
					} else {
						invalidQueries += 1;
					}
				} else {
					invalidQueries += 1;
				}
			} else if(myQueryID == 21) {
				int marketSellerId = Integer.parseInt(in.next());
				double marketSellingAmount = Double.parseDouble(in.next());
				if(!(market.getBuyingOrders().peek() == (null))) {
					double marketSellPrice = market.getBuyingOrders().peek().price;
					if(traders.get(marketSellerId).sell(marketSellingAmount, marketSellPrice, market) == 1) {
						market.checkTransactions(traders);
					} else {
						invalidQueries += 1;
					}
				} else {
					invalidQueries += 1;
				}
			} else if(myQueryID == 3) {
				int depositId = Integer.parseInt(in.next());
				double depositDollars = Double.parseDouble(in.next());
				Trader depositTrader = traders.get(depositId);
				depositTrader.getWallet().setDollars(depositTrader.getWallet().getDollars() + depositDollars);
			} else if(myQueryID == 4) {
				int withdrawId = Integer.parseInt(in.next());
				double withdrawDollars = Double.parseDouble(in.next());
				Trader withdrawTrader = traders.get(withdrawId);
				if(withdrawTrader.getWallet().getDollars() >= withdrawDollars) {
					withdrawTrader.getWallet().setDollars(withdrawTrader.getWallet().getDollars() - withdrawDollars);				
				} else {
					invalidQueries += 1;
				}
			} else if(myQueryID == 5) {
				int walletStatusId = Integer.parseInt(in.next());
				Trader caseFiveTrader = traders.get(walletStatusId); 
				out.print("Trader " + walletStatusId + ": ");
				out.printf("%.5f", caseFiveTrader.getWallet().getDollars());
				out.print("$ ");
				out.printf("%.5f", caseFiveTrader.getWallet().getCoins());
				out.print("PQ");
				out.println();
			} else if(myQueryID == 777) {
				for(Trader trader : traders) {
					trader.getWallet().setCoins(trader.getWallet().getCoins() + (myRandom.nextDouble()*10));
				}
			} else if(myQueryID == 666) {
				int openMarketPrice = Integer.parseInt(in.next());
				market.makeOpenMarketOperation(openMarketPrice, traders);
			} else if(myQueryID == 500) {
				double totalSellingPQ = 0; double totalBuyingD = 0; 
				for(SellingOrder sellOrd : market.getSellingOrders()) {
					totalSellingPQ += sellOrd.amount;
				}
				for(BuyingOrder buyOrd : market.getBuyingOrders()) {
					totalBuyingD += buyOrd.amount*buyOrd.price;
				}
				out.print("Current market size: ");
				out.printf("%.5f", totalBuyingD);
				out.print(" ");
				out.printf("%.5f", totalSellingPQ);
				out.println();
			} else if(myQueryID == 501) {
				out.println("Number of successful transactions: " + market.successfulTransactions);
			} else if(myQueryID == 502) {
				out.println("Number of invalid queries: " + invalidQueries);
			} else if(myQueryID == 505) {
				double cp_Buying; double cp_Selling;
				if(!(market.getSellingOrders().peek() == null)) {
				cp_Buying = market.getSellingOrders().peek().price;
				} else {
					cp_Buying = 0;
				}
				if(!(market.getBuyingOrders().peek() == null)) {
				cp_Selling = market.getBuyingOrders().peek().price;
				} else {
					cp_Selling = 0;
				}
				double cp_Average;
				if(!(cp_Buying == 0 && cp_Selling == 0)) {
					cp_Average = (cp_Buying + cp_Selling)/2;
				} else {
					cp_Average = cp_Buying + cp_Selling;
				}
				out.print("Current prices: ");
				out.printf("%.5f", cp_Selling);
				out.print(" ");
				out.printf("%.5f", cp_Buying);
				out.print(" ");
				out.printf("%.5f", cp_Average);
				out.println();
			} else if(myQueryID == 555) {
				for(Trader trader : traders) {
					out.print("Trader " + trader.getId() + ": ");
					out.printf("%.5f",trader.getWallet().getDollars());
					out.print("$ ");
					out.printf("%.5f",trader.getWallet().getCoins());
					out.print("PQ");
					out.println();
				}
			} else {
				continue;
			}
		

		
		
		
		
		
		
		
		
		
		}
	}
}
