/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 *
 * Final Project (CTA Trip Planner)
 * This class represents a vehicle route which has many stops. These stops are saved as GPS locations into an array.
 * The most important methods in this class are: - Get Nearest Stop: which return a closest stop to a GPS location.
 * 												 - Get Nearest Intersection stop: which return the closest two stop to each other
 * 													one from a bus line and the other from a train line.
 * 												 - Get Route: which basically print out the instructions to get from a stop to another
 * Also this class include an abstract method which is read file method, which forces the children to contains this method.
 *
 */


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public abstract class VehicleRoute_ArnaoutA_VegaD {



	final int NumOfStops = 1000;


	//instance variables
	private String type; // train or bus
	private GPSLocation_ArnaoutA_VegaD [] arr; // to store stops temporarly
	private int index;
	private String fileName; // txt file name of buses or trains stops
	private double temp_distance, intersection_distance; /*these two variables are only used to return additional information
	*from the getNearestStop & getNearestIntersection methods respectively */

	//default constructor
	public VehicleRoute_ArnaoutA_VegaD(){
		arr = new GPSLocation_ArnaoutA_VegaD[NumOfStops];
		index = 0;
		fileName = "";
		type = "";
		temp_distance = 1000;
		intersection_distance =1000;

	}

	//non default constructor
		public VehicleRoute_ArnaoutA_VegaD(String aFileName, String aType){
			arr = new GPSLocation_ArnaoutA_VegaD[NumOfStops];
			type = aType;
			fileName = aFileName;
			temp_distance = 1000;
			intersection_distance =1000;
		}

	//non default constructor
		public VehicleRoute_ArnaoutA_VegaD(String aFileName, String aType, int aNumOfStops){
			arr = new GPSLocation_ArnaoutA_VegaD[aNumOfStops];
			type = aType;
			fileName = aFileName;
			temp_distance = 1000;
			intersection_distance =1000;
			}


	// accessor

		public String getType(){
			return type;
		}

		public int getIndex(){
			return index;
		}

		public String getFileName(){
			return fileName;
		}

		public GPSLocation_ArnaoutA_VegaD getObject(int position){
			return arr[position];

		}


		public GPSLocation_ArnaoutA_VegaD[] getEntire(){
			GPSLocation_ArnaoutA_VegaD[] copy=new GPSLocation_ArnaoutA_VegaD[arr.length];
			for( int i=0; i<index; i++) copy[i] = arr[i];

			return copy;
		}

		public double getTempDistance(){
			return temp_distance;

		}
		public double getIntersection_distance(){
			return 	intersection_distance;

		}



		//mutator

		public void add(GPSLocation_ArnaoutA_VegaD object){
			if(index<arr.length){
			arr[index] = object;
			index++;}
			else System.out.println("error, there is no space");
		}

		public void setIndex(int x){
			index = x;
		}

		public void setType (String aType){
		type = aType;
		}

		public void setFileName (String aFileName){
			fileName = aFileName;
			}

		public void setTemp_distance(double x){
			temp_distance = x;
		}
		public void setIntersection_distance(double x){
			intersection_distance = x;
		}

		//euqals method
		public boolean equals(VehicleRoute_ArnaoutA_VegaD n){

		if((n.getIndex()!=index) || !(n.getType().equals(type)) ) return false;
		if(arr.length!=n.getEntire().length) return false;

		for( int i=0; i<arr.length; i++)
			if (!(arr[i].equals(n.getObject(i)))) return false;

		return true;}

		//toString method
				public String toString(){
				return "Vehicle type: " +type +"\n" +"Source txt file: " +fileName;
				}

		//delete
		public void delete(int pos){
					if (pos>0 && pos<index){
						for( int i=pos; i<index; i++)
							arr[i] = arr[i+1];
						index--;			}
								}
		//insert
		public void insert(int pos, GPSLocation_ArnaoutA_VegaD object){
					for( int i=index; i>pos; i--)
						arr[i] = arr[i-1];
					arr[pos] = object;
					index++;
				}

		//find
		public int find(GPSLocation_ArnaoutA_VegaD o){
						 for( int i=0; i<index; i++) {
							 if(arr[i].equals(o)) return i;
						 }
						 return -1;
					 }

		//delete an object
		 public int delete(GPSLocation_ArnaoutA_VegaD o){
						 //find
						 for( int i=0; i<index; i++) {
							 if(arr[i].equals(o)) { // delete and shift
								 					for( int j=i; j<index; j++)
								 						arr[j] = arr[j+1];

								 						index--;return i;	}


						 							}
						 return -1;
					 }


		//is Empty
		 public boolean isEmpty(){
						 if(index == 0) return true;
						 else return false;
					 }

		//is Full
		public boolean isFull(){
						if(index==arr.length) return true;
						else return false;
					 }


		//more capacity; I added 5 more places
		 public void moreCapacity() {
						 	final int increment = 5;
						 	GPSLocation_ArnaoutA_VegaD [] temp = new GPSLocation_ArnaoutA_VegaD[index+increment];
							 for( int i=0; i<index; i++)
								 temp[i] = arr[i];
							 arr = temp;

					 						}

		//getRoute Method

			public void getRoute(int pos1,int pos2, int pos3, String mutual1, String mutual2 ) throws IOException{
				String x;
				if (pos1==pos2) x= "You know what! it's a pretty short distance, why don't you burn few calories?";
				else if (pos3==-2) x = "Sorry, but in our database there is no available route between your origin and destination";

				else {

					x=  "From this stop: " +arr[pos1].getName() +" take the " +mutual1 +"-line " +type + " which is going to this stop: \n";
					if (pos3>=0) x = x +arr[pos3].getName() + " \n then at that stop transfer to the " +mutual2 +"-line " +type +" which is going to this stop: \n";
					x = x + arr[pos2].getName() + " Then get off the " +type+" at that stop.";
					}

				System.out.println(x);


				//Open output file for writing
				File f = new File("temp.txt");
				if(f.exists()){ //file is exist
					Scanner temp_in = new Scanner(new FileReader(f));
					String y="";

					while (temp_in.hasNext())
						y=y+temp_in.nextLine()+"\n";
					temp_in.close();
					f.delete();
					PrintWriter out = new PrintWriter(new FileWriter(f));
					out.println(y);
					out.println(x);
					out.close();

				}

				else {//file is not exist
					PrintWriter out = new PrintWriter(new FileWriter(f));
					out.println(x);
					out.close();

				}
			}


		//get nearest stop
			public int getNearestStop(GPSLocation_ArnaoutA_VegaD aLocation){
				int pos=0;   // the position of the nearest stop in the arrays
				double d,distance=10000; // Instantiate with a great value to compare it with the real distance

				for(int i=0;i<index;i++){
					d = aLocation.calcDistance(arr[i]);
					if (d < distance)
						{
						distance = d;
						pos = i;

						}
				}
				temp_distance = distance;
				return pos;

			}


		//get nearest intersection-stop between a bus and a train
			public int[] getNearestIntersection(Buses_ArnaoutA_VegaD x,String aBusID){
				int[] temp_pos1= {-1,-1},pos= {-1,-1};
				double d,distance1=1000, distance=1000; // Instantiate them with a great value


					//outerloop to scan all the buses' stops
					for(int i=0;i<x.getIndex();i++)
					{

						if(x.getID(i).equals(aBusID))
							{
							//inner loop to scan all the el trains' stops
							for(int j=0;j<index;j++)
									{
									d = x.getObject(i).calcDistance(arr[j]);
									if (d < distance1)	//check between trains' stops
										{
										distance1 = d;
										temp_pos1 [0]= i;temp_pos1 [1]=j;
										}
									}


							if (distance1 < distance) { // check between buses' stops
								distance = distance1;
								pos = temp_pos1;
													}
						}

				}
				//the best intersection-stop between all bus' stops and trains lines passing along the destination stop
				intersection_distance = distance;
				return pos;

			}

		//Abstract readFile method
			abstract void readFile() throws IOException;




		}
