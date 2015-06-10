/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 * 
 * Final Project (CTA Trip Planner)
 * This class represents a menu that prompts the  user to enter the coordinates of both origin and destination or select them from a menu.
 * 
 */


import java.util.Scanner;


public class User_Prompter_ArnaoutA_VegaD {
	
	//instance variables
	private String [] ori_des; //contians the origin coordinates & name and destination coordinates & name
	private int index;
	
	//default constructor 
	public User_Prompter_ArnaoutA_VegaD(){
	String [] ori_des = new String[6];
	index=0;
	}
	
	//non default constructor 
		public User_Prompter_ArnaoutA_VegaD(int size){
		String [] ori_des = new String[size];
		index=0;
		}
		
	//accessor
		
		public int getIndex(){
			return index;
		}
		
		public String[] getOri_Des(){
			return ori_des;
		}
		public String getElementOri_Des(int pos){
			return ori_des[pos];
		}
		
	//mutator
		public void setIndex(int x){
			index = x;
		}
		public void setOri_des(String[] x){
			ori_des = x;
		}
		public void add(String aString){
			if(index<ori_des.length){
				ori_des[index] = aString;
			index++;}
			else System.out.println("error, there is no space");
		}
		
	//equals method
		public boolean equals(User_Prompter_ArnaoutA_VegaD x){
			if(x.getIndex() != index) return false;
			
			for(int j=0;j<x.getIndex();j++)
				{if (!ori_des[j].equals(x.getElementOri_Des(j)))
					return false;
				}
			return true;
		}
		
	
	public static String oriPrompt()
	{
		String  origin = "";
		Scanner oriInput = new Scanner(System.in);
		System.out.println("Enter the latitude of your origin: ");
		origin = origin + oriInput.nextLine() +",";
		System.out.println("Enter the longitude of your origin: ");
		origin = origin + oriInput.nextLine() +",";
		System.out.println("Enter the name of your origin: ");
		origin = origin +  oriInput.nextLine();
		return origin;
	}
	
	public static String desPrompt()
	{
		String destination ="";
		Scanner desInput = new Scanner(System.in);
		System.out.println("Enter the latitude of your destination: ");
		destination = destination + desInput.nextLine() +",";
		System.out.println("Enter the longitude of your destination: ");
		destination = destination +  desInput.nextLine() +",";
		System.out.println("Enter the name of your destination: ");
		destination = destination +  desInput.nextLine();
		return destination;
	}
	
	
	//this is a menu which will ask the user to either enter the coordinates manually or from a menu
	public String[] menu()
	{
		int choice=0;
		boolean guardian;
		ori_des = new String[6];
		
		Scanner menuInput = new Scanner(System.in);
		System.out.println("To enter new coordinates manually press '1'" +"\n" +"To choose them from a menu press '2'");
		System.out.println("Or to exit press '3': ");
		
		choice = menuInput.nextInt();
		
		do {
			if (choice < 1 || choice > 3)
				{
				System.out.println("Bad choice, please try agian: ");
				choice = menuInput.nextInt();
				guardian = false;
				}
			else guardian = true;
			
		}while(guardian== false);
		
		String ori = null,des= null;	
		
		if(choice == 1){
			 ori = oriPrompt();
			 des = desPrompt();
			 System.out.println("**************************** Your Trip Information *******************************"  +"\n");
			}
		
		else if(choice == 2)
						{	//prompting
							System.out.println("Please select one of these origins: ");
							System.out.println("1-  North Ave & Waller 41.909511,-87.768083");
							System.out.println("2-  North and Central Parkr 41.91005901,-87.71655544");
							System.out.println("3-  Midway Airport 41.7859722,-87.7524167");
							System.out.println("4-  35-Bronzeville IIT 41.831688,-87.62585");
							System.out.println("5-  Cicero-Cermak 41.851886,-87.745467");
							System.out.println("6-  Tommy Gun’s Garage 41.853186,-87.623709");
							System.out.println("7-  O'Hare Airport 41.982973,-87.859996");
							System.out.println("8-  E Hyde Park & Kimbark 41.80243723,-87.59532709");
							
							int choiceOrigin = menuInput.nextInt();
							//make sure the user had a correct choice
							do {
								if (choiceOrigin < 1 || choiceOrigin > 8)
									{
									System.out.println("Bad choice, please try agian: ");
									choiceOrigin = menuInput.nextInt();
									guardian = false;
									}
								else guardian = true;
								
							}while(guardian== false);
						
							//prompting
							System.out.println("Now, please select one of these destinations: ");
							System.out.println("1-  North Ave & Austin 41.909376,-87.775066");
							System.out.println("2-  Pulaski & Erie 41.892726,-87.725928");
							System.out.println("3-  35-Bronzeville IIT 41.831688,-87.62585");
							System.out.println("4-  O'Hare Airport 41.982973,-87.859996");
							System.out.println("5-  SB 41.8383158,-87.6266167");
							System.out.println("6-  Roosevelt 41.867386,-87.627034");
							System.out.println("7-  Midway Airport 41.7859722,-87.7524167");
							System.out.println("8-  Michigan & 9th Street 41.870503,-87.624006");
							System.out.println("9-  54th Cermak-Pink 41.85177331,-87.75669201");
							System.out.println("10-  Cermak & McCormick Place place 41.851402,-87.616361");
							int choiceDest = menuInput.nextInt();
							 System.out.println("**************************** Your Trip Information *******************************"  +"\n");
							//make sure the user had a correct choice
							do {
								if (choiceDest < 1 || choiceDest > 10)
									{
									System.out.println("Bad choice, please try agian: ");
									choiceDest = menuInput.nextInt();
									guardian = false;
									}
								else guardian = true;
								
							}while(guardian== false);
						
						
						switch (choiceOrigin) // enter origin
									{
											 
										case 1: {  ori= "41.909511,-87.768083,North Ave & Waller";  break;}
										case 2: {  ori = "41.91005901,-87.71655544,North and Central Park"; break;}
										case 3: {  ori = "41.7859722,-87.7524167,Midway Airport"; break;}
										case 4: {  ori = "41.831688,-87.62585,35-Bronzeville IIT";break;}	
										case 5: {  ori= "41.851886,-87.745467,Cicero-Cermak";break;}
										case 6: {  ori= "41.853186,-87.623709,Tommy Gun’s Garage"; break;}
										case 7: {  ori= "41.982973,-87.859996,O'Hare Airport"; break;}
										case 8: {  ori= "41.80243723,-87.59532709,E Hyde Park & Kimbark"; break;}
										//no need for default, it's impossible to get this case in our conditions
									}
								
						switch (choiceDest) //enter destination
									{

										case 1: {  des = "41.909376,-87.775066,North Ave & Austin"; break;}
										case 2: {  des = "41.892726,-87.725928,Pulaski & Erie"; break;}
										case 3: {  des =  "41.831688,-87.62585,35-Bronzeville IIT"; break;}
										case 4: {  des = "41.982973,-87.859996,O'Hare Airport"; break;}
										case 5: {  des =  "41.8383158,-87.6266167,SB"; break;}
										case 6: {  des=  "41.867386,-87.627034,Roosevelt"; break;}
										case 7: {  des= "41.7859722,-87.7524167,Midway Airport"; break;}
										case 8: {  des=  "41.870503,-87.624006,Michigan & 9th Street"; break;}
										case 9: {  des=  "41.85177331,-87.75669201,54th Cermak-Pink"; break;} 
										case 10:{  des=  "41.851402,-87.616361,54th random"; break;}
										//no need for default, it's impossible to get this case in our conditions
									}

						}
		else {	 ori = "0,0,!";
				 des ="0,0,!";
			}
		
		
		String [] tokens= null;
		tokens = ori.split(",");
		for(int i=0;i<3;i++)
			ori_des[i]=tokens[i];
		
		tokens = des.split(",");
		for(int j=3;j<6;j++)
			ori_des[j]=tokens[j-3];
		
		return ori_des;
	}
	
}
