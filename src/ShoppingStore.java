import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ShoppingStore {
	public static Map<String, Double> items = new TreeMap<>();
	private static List<String> shoppingCartItems = new ArrayList<>();
	private static List<Double> shoppingCartPrices = new ArrayList<>();
	private static List<Integer> shoppingCartOrderNumber = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scnr = new Scanner(System.in);
		String purchase = "";
		String cont = "";
		boolean valid;
		int id = 0;
		String clear;
		
		addItems();
		
		System.out.println("Welcome to Guenther's Market!");
		displayMenu();
		do {
			System.out.print("Please add a item you would like to purchase: ");
			try {
				if(scnr.hasNextInt()) {
					id = scnr.nextInt();
					purchase = "";
				}
				else if(scnr.hasNext()) {
					purchase = scnr.nextLine();
				}
			} catch(IndexOutOfBoundsException e) {
				continue;
			}
			if(purchase.equalsIgnoreCase("display")) {
				displayShoppingCart();
				valid = true;
			}
			if(purchase.equalsIgnoreCase("menu")) {
				displayMenu();
				valid = true;
			}
			
			if(purchase.equals("")) {
				valid = verifyItem(id);
			}
			else {
				valid = verifyItem(purchase);
			}
			
			if(!valid) {
				valid = true;
				continue;
			}
			
			System.out.print("Do you want to continue shopping?(y/n): ");
			if(purchase.equals("")) {
				clear = scnr.nextLine();
				cont = scnr.nextLine();
			}
			else {
				cont = scnr.nextLine();
			}
		} while(!cont.equalsIgnoreCase("n"));
		String low = lowestAmount(shoppingCartItems);
		String high = highestAmount(shoppingCartItems);
		System.out.println("Thank you for shopping!");
		System.out.printf("Average item cost: %-3.2f\n", averageAmount(shoppingCartPrices));
		System.out.printf("Most cost effective item: %-8s %-3.2f\n", low, items.get(low));
		System.out.printf("Most luxurious item: %-8s %-3.2f\n", high, items.get(high));
		System.out.println("Goodbye");
	}
	
	public static void displayMenu() {
		System.out.printf("%-3s %-10s %-10s\n", "id", "Item", "Price");
		String pads = String.format("%20s", "").replace(' ', '=');
		System.out.printf("%-20s\n", pads);
		
		displayItems();
		System.out.println("Enter Display to veiw shopping cart.");
		System.out.println("Enter Menu to veiw Menu.");
		
	}
	
	public static void displayItems() {
		String names = items.keySet().toString();
		names = names.substring(1);
		names = names.substring(0, names.length()-1);
		String[] name = names.split(", ");
		int j = 0;
		
		for(Double i:items.values()) {
			System.out.printf("%-3d %-10s $%-10.2f\n", getShoppingCartOrderNumber(j)+1, name[j], i);
			j++;
		}
	}
	
	public static void addItems() {
		items.put("apple" , 0.99);
		items.put("eggs", 2.50);
		items.put("bacon", 3.79);
		items.put("grapes", 4.40);
		items.put("oranges", 2.38);
		items.put("milk", 2.99);
		items.put("cherries", 4.29);
		items.put("cheese", 2.79);
		
		populateOrderNumber();
	}
	
	public static boolean verifyItem(String item) {
		if(items.get(item) != null) {
			addToShoppingCart(item);
			return true;
		}
		else {
			System.out.println("Invalid input please try again.");
			return false;
		}
	}
	
	public static boolean verifyItem(int item) {
		int i = 1;
		for(String name:items.keySet()) {
			if(i == item) {
				addToShoppingCart(name);
				return true;
			}
			i++;
		}
		System.out.println("Invalid input please try again.");
			return false;
	}
	
	public static void addToShoppingCart(String item) {
		shoppingCartItems.add(item);
		shoppingCartPrices.add(items.get(item));
		System.out.printf("%-10s $%-10.2f\n", item, items.get(item));
	}
	
	public static void displayShoppingCart() {
		if(!shoppingCartItems.isEmpty()) {
			int j = 0;
			System.out.println("Your shopping cart contains:");
			
			for(String name: shoppingCartItems) {
				System.out.printf("%-10s $%-10.2f\n", name, shoppingCartPrices.get(j));
				j++;
			}
		}
		else {
			System.out.println("Shopping cart is empty!");
		}
	}
	
	public static double averageAmount(List<Double> items) {
		double sum = 0;
		for(Double price: shoppingCartPrices) {
			sum += price;
		}
		sum = sum/shoppingCartPrices.size();
		displayShoppingCart();
		
		return sum;
	}
	
	public static String lowestAmount(List<String> input) {
		double low = 0;
		String lowest = "";
		double price;
		
		for(String item: input) {
			price = items.get(item);
			if(low == 0) {
				low = price;
				lowest = item;
			}
			if(low > price) {
				low = price;
				lowest = item;
			}
		}
		
		return lowest;
	}
	
	public static String highestAmount(List<String> input) {
		double high = 0;
		String highest = "";
		double price;
		
		for(String item: input) {
			price = items.get(item);
			if(high < price) {
				high = price;
				highest = item;
			}
		}
		
		return highest;
	}
	
	public static void populateOrderNumber() {
		int i = 0;
		for(Double item:items.values()) {
			shoppingCartOrderNumber.add(i);
			i++;
		}
	}
	
	public static Integer getShoppingCartOrderNumber(int index) {
		return shoppingCartOrderNumber.get(index);
	}
}
