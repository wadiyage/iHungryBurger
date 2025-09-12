import java.util.*;
class IHungryBurger{
	public static String[] orderIDs = {"B0001","B0002","B0003","B0004","B0005","B0006","B0007","B0008","B0009","B0010","B0011","B0012"};
	
	public static String[] customerIDs = {"0712132457","0754523587","0775475657","0773212489","0721232567","0724557890","0773245657","0724125689","0753212845","0776572325","0754523587","0773245657"};
	public static String[] customerNames = {"Nimal Perera","Kasun Fernando","Amal Jayasinghe","Sahan Gamage","Dilani Ekanayake","Priya Rajapaksa","Ruwan Wickramage","Malith Weerasinghe","Shani Herath","Tharun Abeywardhane","Kasun Fernando","Ruwan Wickramage"};
	
	public static final double BURGERPRICE = 500;
	public static int[] burgerQuantities = {5,3,4,9,2,1,2,1,3,5,2,4};
	
	public static final int PREPARING = 0;
	public static final int DELIVERED = 1;
	public static final int CANCELED = 2;
	public static int[] statuses = {DELIVERED,DELIVERED,DELIVERED,CANCELED,DELIVERED,CANCELED,CANCELED,DELIVERED,PREPARING,PREPARING,DELIVERED,PREPARING};
	
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		do {
			clearConsole();
			System.out.println("------------------------------------------------------------------------");
			System.out.println("|                               iHungry Burger                         |");
			System.out.println("------------------------------------------------------------------------");
		
			System.out.println("[1] Place Order                 [2] Search Best Customer                ");
			System.out.println("[3] Search Order                [4] Search Customer                     ");
			System.out.println("[5] View Orders                 [6] Update Order Details                ");
			System.out.println("[7] Exit                                                                ");
		
		
			System.out.print("\nEnter an option to continue > ");
			int op = sc.nextInt();
		
			switch(op) {
				case 1: placeOrder();
						break;
				case 2: searchBestCustomer();
						break;
				case 3: searchOrder();
						break;
				case 4: searchCustomer();
						break;
				case 5: viewOrders();
						break;
				case 6: updateOrderDetails();
						break;
				case 7: 
				default: 
						exit();
			}
		}	while(true);
	}
	
	public static void placeOrder() {
		Scanner sc = new Scanner(System.in);
		L1:do {
			clearConsole();
		
			System.out.println("------------------------------------------------------------------------");
			System.out.println("|                               PLACE ORDER                            |");
			System.out.println("------------------------------------------------------------------------\n\n");
		
			String generatedID = generateOrderID();
			System.out.println("ORDER ID - "+generatedID);
			System.out.println("================\n\n");
		
			String customerID="", customerName="";
			L2:do {
				System.out.print("Enter Customer ID (phone no.): ");
				customerID = sc.next();
				sc.nextLine();
			
				if(isValidatedCustomerID(customerID) && isValidatedPhoneNumber(customerID)) {
					int index = indexOf(customerID);
					if(index!=-1) {
						System.out.println("Already exists...");
						
						customerName = customerNames[index];
						System.out.println("\n\tCustomer Name   : " + customerName);
						break L2;
					} else {
						System.out.print("Customer Name   : ");
						customerName = sc.nextLine();
						break L2;
					}
				} else {
					System.out.println("\n\tThat's not validated! You can try another...");
					continue L2;
				}
			} while(true);
		
			L2:do {
				System.out.print("Enter Burger Quantity - ");
				int burgerQuantity = sc.nextInt();
		
				double totalValue=0;
				if(burgerQuantity>0) {
					totalValue = BURGERPRICE*burgerQuantity;
					System.out.printf("Total value - %5.2f\n", totalValue);
				
					L3:do {
						System.out.print("\tAre you confirm order (Y/N) - ");
						char op = sc.next().charAt(0);
		
						if(op=='Y' || op=='y') {
							extendedDataSet();
				
							orderIDs[orderIDs.length-1]=generatedID;
							customerIDs[orderIDs.length-1]=customerID;
							customerNames[orderIDs.length-1]=customerName;
							burgerQuantities[orderIDs.length-1]=burgerQuantity;
							statuses[orderIDs.length-1]=PREPARING;
				
							System.out.println("\n\tYour order is entered to the system successfully...");
				
							L4:do {
								System.out.print("\nDo you want to place another? (Y/N)");
								char op2 = sc.next().charAt(0);
				
								if(op2=='Y' || op2=='y') {
									continue L1;
								} else if(op2=='N' || op2=='n') {
									break L1;
								} else {
									System.out.println("\tWrong input. Try again...");
									continue L4;
								}
							} while(true);
						} else if(op=='N' || op=='n') {
							System.out.println("\tYour order is cancelled...");
							L4:do {
								System.out.print("\nDo you want to place another? (Y/N)");
								char op2 = sc.next().charAt(0);
				
								if(op2=='Y' || op2=='y') {
									continue L1;
								} else if(op2=='N' || op2=='n') {
									break L1;
								} else {
									System.out.println("\tWrong input. Try again...");
									continue L4;
								}
							} while(true);
						} else {
							System.out.println("\tWrong input. Try again...");
							continue L3;
						}
					} while(true);
				} else {
					System.out.println("\n\tThat must be greater than zero. You can try another...");
					continue L2;
				}
			} while(true);
			
		} while(true);
	}
	
	public static void searchBestCustomer() {
		Scanner sc = new Scanner(System.in);
		
		String[] removedDuplicatedCustomerIDs = removeDuplicates();
		double[] totalPurchased = totalPurchasedforBestCustomer(removedDuplicatedCustomerIDs);
		sortingForDescendingTogether(removedDuplicatedCustomerIDs,totalPurchased);
		
		String[] filteredNamesForRelevantRemovedDuplicatedIDs = searchFilteredNamesForRelevantRemovedDuplicatedIDs(removedDuplicatedCustomerIDs);
		
		L1:do {
			clearConsole();
		
			System.out.println("------------------------------------------------------------------------");
			System.out.println("|                               BEST Customer                          |");
			System.out.println("------------------------------------------------------------------------\n\n");
		
			System.out.println("------------------------------------------------------------------------");
			System.out.println("| CustomerID     Name                            Total                 |");
			System.out.println("------------------------------------------------------------------------");
		
		
			for(int i=0;i<removedDuplicatedCustomerIDs.length;i++) {
				System.out.printf("| %10s     %-21s          %7.2f                |\n", removedDuplicatedCustomerIDs[i],filteredNamesForRelevantRemovedDuplicatedIDs[i],totalPurchased[i]);
				System.out.println("------------------------------------------------------------------------");
			}
		
			L2:do {
				System.out.print("\nDo you want to go back to main menu (Y/N): ");
				char op = sc.next().charAt(0);
				
				if(op=='Y' || op=='y') {
					break L1;
				} else if(op=='N' || op=='n') {
					continue L1;
				} else {
					System.out.println("\tWrong input. Try again...");
					continue L2;
				}
			} while(true);
		} while(true);
	}
	
	public static void searchOrder() {
		Scanner sc = new Scanner(System.in);
		L1:do {
			clearConsole();
		
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("|                               SEARCH ORDER DETAILS                           |");
			System.out.println("--------------------------------------------------------------------------------");
		
			System.out.print("\nEnter order Id - ");
			String enteredID = sc.next();
			sc.nextLine();
			
			int index = haveAlreadyOrdered(enteredID);
			if(index!=-1) {
				System.out.println("\n----------------------------------------------------------------------------------------");
				System.out.println("| OrderID   CustomerID    Name                   Quantity    OrderValue    OrderStatus |");
				System.out.println("----------------------------------------------------------------------------------------");
		
		
				System.out.printf("| %5s     %10s    %-21s     %1d          %7.2f       %9s |\n", orderIDs[index],customerIDs[index],customerNames[index],burgerQuantities[index],(burgerQuantities[index]*BURGERPRICE),getStatusInTextMode(statuses[index]));
				System.out.println("----------------------------------------------------------------------------------------\n");
				
			} else {
				L2:do {
					System.out.print("\n\n\n\tInvalid Order ID. Do you want to enter again? (Y/N): ");
					char op = sc.next().charAt(0);
				
					if(op=='Y' || op=='y') {
						continue L1;
					} else if(op=='N' || op=='n') {
						break L1;
					} else {
						System.out.println("\tWrong input. Try again...");
						continue L2;
					}
				} while(true);
			}
			L2:do {
				System.out.print("\nDo you want to search another order details (Y/N): ");
				char op = sc.next().charAt(0);
				
				if(op=='Y' || op=='y') {
					continue L1;
				} else if(op=='N' || op=='n') {
					break L1;
				} else {
					System.out.println("\tWrong input. Try again...");
					continue L2;
				}
			} while(true);
		
		} while(true);
	}
	
	public static void searchCustomer() {
		Scanner sc = new Scanner(System.in);
		L1:do {
			clearConsole();
		
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("|                               SEARCH CUSTOMER DETAILS                           |");
			System.out.println("--------------------------------------------------------------------------------");
		
			System.out.print("\nEnter customer Id - ");
			String customerID = sc.next();
			sc.nextLine();
		
			
			if(isAlreadyACustomer(customerID)) {
				System.out.println("\n\nCustomerID - "+customerID);
				
				System.out.println("Name       - "+customerNames[indexOf(customerID)]);
				
				System.out.println("\nCustomer Order Details");
				System.out.println("========================");
				
				System.out.println("\n----------------------------------------------");
				System.out.println("| Order_ID   Order_Quantity    Total_Value   |");
				System.out.println("----------------------------------------------");
				
				int index = -1;
				String[] removedDuplicatedCustomerIDs = removeDuplicates();
				for(int i=0;i<removedDuplicatedCustomerIDs.length;i++) {
					if(removedDuplicatedCustomerIDs[i].equals(customerID)) {
						index = i;
					}
				}
				
				for(int j=0;j<customerIDs.length;j++) {
					if(removedDuplicatedCustomerIDs[index].equals(customerIDs[j])) {
						System.out.printf("| %5s       %1d                 %7.2f      |\n", orderIDs[j],burgerQuantities[j],(burgerQuantities[j]*BURGERPRICE));
						System.out.println("----------------------------------------------");
					}
				}
				
			} else {
				System.out.println("\n\n\t This customer ID is not added yet....");
			}
			L2:do {
				System.out.print("\nDo you want to search another customer details (Y/N): ");
				char op = sc.next().charAt(0);
				
				if(op=='Y' || op=='y') {
					continue L1;
				} else if(op=='N' || op=='n') {
					break L1;
				} else {
					System.out.println("\tWrong input. Try again...");
					continue L2;
				}
			} while(true);
		
		} while(true);
	}
	
	public static void viewOrders() {
		Scanner sc = new Scanner(System.in);
		L1:do {
			clearConsole();
		
			System.out.println("------------------------------------------------------------------------");
			System.out.println("|                               VIEW ORDER LIST                        |");
			System.out.println("------------------------------------------------------------------------");
		
			System.out.println("\n[1] Delivered Order                                                     ");
			System.out.println("[2] Preparing Order                                                     ");
			System.out.println("[3] Cancel Order                                                        ");
		
			System.out.print("\nEnter an option to continue > ");
			int op = sc.nextInt();
		
			switch(op) {
				case 1: {
					L2:do {
						clearConsole();
		
						System.out.println("------------------------------------------------------------------------");
						System.out.println("|                               DELIVERED ORDER                        |");
						System.out.println("------------------------------------------------------------------------\n\n");
		
						System.out.println("------------------------------------------------------------------------");
						System.out.println("| OrderID   CustomerID     Name                 Quantity    OrderValue |");
						System.out.println("------------------------------------------------------------------------");
		
		
						for(int i=0;i<statuses.length;i++) {
							if(statuses[i]==DELIVERED) {
								System.out.printf("| %5s     %10s     %-21s   %1d          %7.2f  |\n", orderIDs[i],customerIDs[i],customerNames[i],burgerQuantities[i],(burgerQuantities[i]*BURGERPRICE));
								System.out.println("------------------------------------------------------------------------");
							}
						}
		
						L3:do {
							System.out.print("\nDo you want to go to home page (Y/N): ");
							char op2 = sc.next().charAt(0);
				
							if(op2=='Y' || op2=='y') {
								break L1;
							} else if(op2=='N' || op2=='n') {
								break L2;
							} else {
								System.out.println("\tWrong input. Try again...");
								continue L3;
							}
						} while(true);
					} while(true);
				};
					break;
			case 2: {
				L2:do {
					clearConsole();
		
					System.out.println("------------------------------------------------------------------------");
					System.out.println("|                               PREPARING ORDER                        |");
					System.out.println("------------------------------------------------------------------------\n\n");
		
					System.out.println("------------------------------------------------------------------------");
					System.out.println("| OrderID   CustomerID     Name                 Quantity    OrderValue |");
					System.out.println("------------------------------------------------------------------------");
		
		
					for(int i=0;i<statuses.length;i++) {
						if(statuses[i]==PREPARING) {
							System.out.printf("| %5s     %10s     %-21s   %1d          %7.2f  |\n", orderIDs[i],customerIDs[i],customerNames[i],burgerQuantities[i],(burgerQuantities[i]*BURGERPRICE));
							System.out.println("------------------------------------------------------------------------");
						}
					}
		
					L3:do {
						System.out.print("\nDo you want to go to home page (Y/N): ");
						char op2 = sc.next().charAt(0);
				
						if(op2=='Y' || op2=='y') {
							break L1;
						} else if(op2=='N' || op2=='n') {
							break L2;
						} else {
							System.out.println("\tWrong input. Try again...");
							continue L3;
						}
					} while(true);
				} while(true);
			};
				break;
			case 3: {
				L2:do {
					clearConsole();
		
					System.out.println("------------------------------------------------------------------------");
					System.out.println("|                               CANCEL ORDER                           |");
					System.out.println("------------------------------------------------------------------------\n\n");
		
					System.out.println("------------------------------------------------------------------------");
					System.out.println("| OrderID   CustomerID     Name                 Quantity    OrderValue |");
					System.out.println("------------------------------------------------------------------------");
		
		
					for(int i=0;i<statuses.length;i++) {
						if(statuses[i]==CANCELED) {
							System.out.printf("| %5s     %10s     %-21s   %1d          %7.2f  |\n", orderIDs[i],customerIDs[i],customerNames[i],burgerQuantities[i],(burgerQuantities[i]*BURGERPRICE));
							System.out.println("------------------------------------------------------------------------");
						}
					}
		
					L3:do {
						System.out.print("\nDo you want to go to home page (Y/N): ");
						char op2 = sc.next().charAt(0);
				
						if(op2=='Y' || op2=='y') {
							break L1;
						} else if(op2=='N' || op2=='n') {
							break L2;
						} else {
							System.out.println("\tWrong input. Try again...");
							continue L3;
						}
					} while(true);
				} while(true);
			};
				break;
				default:
					continue L1;
			}
		} while(true);
	}
	
	public static void updateOrderDetails() {
		Scanner sc = new Scanner(System.in);
		L1:do {
			clearConsole();
		
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("|                               UPDATE ORDER DETAILS                           |");
			System.out.println("--------------------------------------------------------------------------------");
		
			System.out.print("\nEnter order Id - ");
			String enteredID = sc.next();
			sc.nextLine();
			
			int index = haveAlreadyOrdered(enteredID);
			if(index!=-1) {
				if(statuses[index]==1) {
					System.out.println("\tThis Order is already delivered...You can not update this order...");
					L2:do {
						System.out.print("\n\n\nDo you want to update another order details? (Y/N): ");
						char op = sc.next().charAt(0);
				
						if(op=='Y' || op=='y') {
							continue L1;
						} else if(op=='N' || op=='n') {
							break L1;
						} else {
							System.out.println("\tWrong input. Try again...");
							continue L2;
						}
					} while(true);
				}
				if(statuses[index]==2) {
					System.out.println("\tThis Order is already cancelled...You can not update this order...");
					L2:do {
						System.out.print("\n\n\nDo you want to update another order details? (Y/N): ");
						char op = sc.next().charAt(0);
				
						if(op=='Y' || op=='y') {
							continue L1;
						} else if(op=='N' || op=='n') {
							break L1;
						} else {
							System.out.println("\tWrong input. Try again...");
							continue L2;
						}
					} while(true);
				}
				if(statuses[index]==0) {
					System.out.println("\nOrderID     - "+enteredID);
					System.out.println("CustomerID  - "+customerIDs[index]);
					System.out.println("Name        - "+customerNames[index]);
					System.out.println("Quantity    - "+burgerQuantities[index]);
					System.out.println("OrderValue  - "+(burgerQuantities[index]*BURGERPRICE));
					System.out.println("OrderStatus - "+getStatusInTextMode(statuses[index]));
				
					System.out.println("\nWhat do you want to update ?");
					System.out.println("        (01) Quantity ");
					System.out.println("        (02) Status ");
				
					System.out.print("\nEnter your option - ");
					int op = sc.nextInt();
				
					switch(op) {
						case 1: {
							L2:do {
								clearConsole();
		
								System.out.println("Quantity Update");
								System.out.println("================");
		
								System.out.println("\nOrderID    - "+orderIDs[index]);
								System.out.println("CustomerID - "+customerIDs[index]);
								System.out.println("Name       - "+customerNames[index]);
								
								L3:do {
									System.out.print("\nEnter your quantity update value - ");
									int quantityUpdateValue = sc.nextInt();
			
									if(quantityUpdateValue>0) {
										burgerQuantities[index]=quantityUpdateValue;
										System.out.println("\n\t Update order quantity successfully...");
				
										System.out.println("\nnew order quantity   - "+burgerQuantities[index]);
										System.out.println("new order value - "+(burgerQuantities[index]*BURGERPRICE));
				
										L4:do {
											System.out.print("\nDo you want to update another order details? (Y/N): ");
											char op2 = sc.next().charAt(0);
					
											if(op2=='Y' || op2=='y') {
												continue L1;
											} else if(op2=='N' || op2=='n') {
												break L1;
											} else {
												System.out.println("\tWrong input. Try again...");
												continue L4;
											}
										} while(true);
									} else {
										System.out.println("\n\tThat must be greater than zero. You can try another...");
										continue L3;
									}
								} while(true);
							} while(true);
						}
						case 2: {
							L2:do {
								clearConsole();
		
								System.out.println("Status Update");
								System.out.println("================");
		
								System.out.println("\nOrderID    - "+orderIDs[index]);
								System.out.println("CustomerID - "+customerIDs[index]);
								System.out.println("Name       - "+customerNames[index]);
		
								System.out.println("\n\t(0) CANCEL ");
								System.out.println("\t(1) PREPARING ");
								System.out.println("\t(2) DELIVERED ");
		
								L3:do {
									System.out.print("\nEnter new order status - ");
									int updateOrderStatus = sc.nextInt();
									
									if(updateOrderStatus==0) {
										updateOrderStatus=2;
									} else if(updateOrderStatus==1) {
										System.out.println("\n\t Already available! You can try another...");
										continue L3;
									} else if(updateOrderStatus==2) {
										updateOrderStatus=1;
									} else {
										System.out.println("\n\t That's not a status! You can try another...");
										continue L3;
									}
									
									statuses[index]=updateOrderStatus;
									System.out.println("\n\t Update order status successfully...");
			
									System.out.println("\nnew order status   - "+getStatusInTextMode(statuses[index]));
			
									L4:do {
										System.out.print("\nDo you want to update another order details? (Y/N): ");
										char op2 = sc.next().charAt(0);
				
										if(op2=='Y' || op2=='y') {
											continue L1;
										} else if(op2=='N' || op2=='n') {
											break L1;
										} else {
											System.out.println("\tWrong input. Try again...");
											continue L4;
										}
									} while(true);
								} while(true);
							} while(true);
						}
						default:
							continue L1;
							
					}
				} 
				
			} else {
				L2:do {
					System.out.print("\n\n\n\tInvalid Order ID. Do you want to enter again? (Y/N): ");
					char op = sc.next().charAt(0);
				
					if(op=='Y' || op=='y') {
						continue L1;
					} else if(op=='N' || op=='n') {
						break L1;
					} else {
						System.out.println("\tWrong input. Try again...");
						continue L2;
					}
				} while(true);
			}
			
		} while(true);
	}
	
	public static String generateOrderID() {
		if(orderIDs.length<=0)
			return "B00001";
		else {
			String recentlyGeneratedOrderID = orderIDs[orderIDs.length-1];
			String separatedIntegerOnRecentlyGenerated = recentlyGeneratedOrderID.substring(1);
			int convertedIntegerOnRecentlyGenerated = Integer.parseInt(separatedIntegerOnRecentlyGenerated);
			int newlyGeneratedID = ++convertedIntegerOnRecentlyGenerated;
			String formattedGeneratedID = String.format("B%04d",newlyGeneratedID);
			return formattedGeneratedID;
		}
	}
	
	public static boolean isValidatedCustomerID(String customerID) {
		if(customerID.charAt(0)=='0'  && customerID.length()==10) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidatedPhoneNumber(String phoneNumber) {
		for(int i=0;i<phoneNumber.length();i++) {
			if(!((phoneNumber.charAt(i)>=48) && (phoneNumber.charAt(i)<=57))) 
				return false;
		}
		return true;
	}
	
	public static int indexOf(String customerID) {
		for(int i=0;i<customerIDs.length;i++) {
			if(customerIDs[i].equals(customerID)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void extendedDataSet() {
		String[] tempariedOrderIDs = new String[orderIDs.length+1];
		String[] tempariedCustomerIDs = new String[orderIDs.length+1];
		String[] tempariedCustomerNames = new String[orderIDs.length+1];
		int[] tempariedBurgerQuantities = new int[orderIDs.length+1];
		int[] tempariedStatuses = new int[orderIDs.length+1];
		
		for(int i=0;i<orderIDs.length;i++) {
			tempariedOrderIDs[i]=orderIDs[i];
			tempariedCustomerIDs[i]=customerIDs[i];
			tempariedCustomerNames[i]=customerNames[i];
			tempariedBurgerQuantities[i]=burgerQuantities[i];
			tempariedStatuses[i]=statuses[i];
		}
		
		orderIDs=tempariedOrderIDs;
		customerIDs=tempariedCustomerIDs;
		customerNames=tempariedCustomerNames;
		burgerQuantities=tempariedBurgerQuantities;
		statuses=tempariedStatuses;
	}
	
	public static boolean isDuplicated(String[] removedDuplicatedCustomerIDs, String key) {
		for(int i=0;i<removedDuplicatedCustomerIDs.length;i++) {
			if(removedDuplicatedCustomerIDs[i]==key)
				return true;
		}
		return false;
	}
	
	public static String[] removeDuplicates() {
		String[] removedDuplicatedCustomerIDs = new String[0];
		for(int i=0;i<customerIDs.length;i++) {
			if(!isDuplicated(removedDuplicatedCustomerIDs,customerIDs[i])) {
				String[] tempariedCustomerIDs = new String[removedDuplicatedCustomerIDs.length+1];
				for(int j=0;j<removedDuplicatedCustomerIDs.length;j++) {
					tempariedCustomerIDs[j]=removedDuplicatedCustomerIDs[j];
				}
				tempariedCustomerIDs[tempariedCustomerIDs.length-1]=customerIDs[i];
				removedDuplicatedCustomerIDs=tempariedCustomerIDs;
			}
		}
		return removedDuplicatedCustomerIDs;
	}
	
	public static double[] totalPurchasedforBestCustomer(String[] removedDuplicatedCustomerIDs) {
		double[] totalPurchased = new double[removedDuplicatedCustomerIDs.length];
		for(int i=0;i<removedDuplicatedCustomerIDs.length;i++) {
			for(int j=0;j<customerIDs.length;j++) {
				if(removedDuplicatedCustomerIDs[i].equals(customerIDs[j])) {
					totalPurchased[i]+=burgerQuantities[j]*BURGERPRICE;
				}
			}
		}
		return totalPurchased;
	}
	
	public static void sortingForDescendingTogether(String[] removedDuplicatedCustomerIDs, double[] totalPurchased) {
		for(int i=totalPurchased.length-1;i>=0;i--) {
			for(int j=0;j<i;j++) {
				if(totalPurchased[j+1]>totalPurchased[j]) {
					double tempTotalPurchased = totalPurchased[j+1];
					totalPurchased[j+1]=totalPurchased[j];
					totalPurchased[j]=tempTotalPurchased;
					
					String tempRemovedDuplicatedCustomerID = removedDuplicatedCustomerIDs[j+1];
					removedDuplicatedCustomerIDs[j+1]=removedDuplicatedCustomerIDs[j];
					removedDuplicatedCustomerIDs[j]=tempRemovedDuplicatedCustomerID;
				}
			}
		}
	}
	
	public static String[] searchFilteredNamesForRelevantRemovedDuplicatedIDs(String[] removedDuplicatedCustomerIDs) {
		String[] filteredNamesForRelevantRemovedDuplicatedIDs = new String[removedDuplicatedCustomerIDs.length];
		for(int i=0;i<removedDuplicatedCustomerIDs.length;i++) {
			for(int j=0;j<customerIDs.length;j++) {
				if(removedDuplicatedCustomerIDs[i]==customerIDs[j]) {
					filteredNamesForRelevantRemovedDuplicatedIDs[i]=customerNames[j];
				}
			}
		}
		return filteredNamesForRelevantRemovedDuplicatedIDs;
	}
	
	public static int haveAlreadyOrdered(String orderID) {
		for(int i=0;i<orderIDs.length;i++) {
			if(orderIDs[i].equalsIgnoreCase(orderID)) {
				return i;
			}
		}
		return -1;
	}
	
	public static String getStatusInTextMode(int status) {
		String statusInTextMode = "";
		if(status==DELIVERED)
			statusInTextMode = "DELIVERED";
		else if(status==CANCELED)
			statusInTextMode = "CANCELED";
		else 
			statusInTextMode = "PREPARING";
		return statusInTextMode;
	}
	
	public static boolean isAlreadyACustomer(String customerID) {
		String[] removeDuplicatedCustomerIDs = removeDuplicates();
		for(int i=0;i<removeDuplicatedCustomerIDs.length;i++) {
			if(removeDuplicatedCustomerIDs[i].equals(customerID))
				return true;
		}
		return false;
	}
	
	public static void clearConsole(){
		try{
			final String os=System.getProperty("os.name");
			if(os.contains("Windows")){
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}else{
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		}catch(final Exception e){
			e.printStackTrace();
		}
	}
	
	public static void exit() {
		clearConsole();
		System.out.println("\n\t\tYou left the program...\n");
		System.exit(0);
	}
	
	
}
