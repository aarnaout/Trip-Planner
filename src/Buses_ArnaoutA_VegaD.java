/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 *
 * Final Project (CTA Trip Planner)
 * This class is a child of Vehicle Route class. The main purpose of this is to read Bus Stops file, assign the appropriate
 * IDs to each stop, and determine the transfer stop between two buses while returning the BUS ID that the user should take to
 * reach her/his destination.
 */


import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Buses_ArnaoutA_VegaD extends VehicleRoute_ArnaoutA_VegaD {


	//instance variables
	private String[] ids; //new array contains the IDs of the buses
	private String mutual_ID1;
	private String mutual_ID2;

	//default constructor
	public Buses_ArnaoutA_VegaD() {
		super();
		ids = new String[super.getEntire().length]; // the same length of GPSLocation array
		mutual_ID1=null;
		mutual_ID2=null;
	}

	//nondefault constructor
	public Buses_ArnaoutA_VegaD(String aFileName, String aType) {
		super(aFileName,aType);
		ids = new String[super.getEntire().length];
		mutual_ID1=null;
		mutual_ID2=null;

	}

	//nondefault constructor
	public Buses_ArnaoutA_VegaD(String aFileName, String aType, int aNumOfStops ) {
		super(aFileName,aType,aNumOfStops);
		ids = new String[aNumOfStops];
		mutual_ID1=null;
		mutual_ID2=null;

	}



	//accessors

	//return an element
	public String getID(int pos) {
		return ids[pos];
	}

	public String getMutual_ID1() {
		return mutual_ID1;

	}
	public String getMutual_ID2() {
		return mutual_ID2;

	}

	//return the entire array od ids
	public String[] getIDs(int pos) {
		return ids;
	}


	//mutators
	public void setID(String aID) {

		ids[super.getIndex()] = aID;
	}

	public void setIDs(String[] aIDs) {

		ids = aIDs;
	}

	public void setMutual_ID1(String aID) {

		mutual_ID1 = aID;
	}
	public void setMututal_ID2(String aID) {

		mutual_ID2 = aID;
	}

	//equals method
	public boolean equals(Buses_ArnaoutA_VegaD aBus)
	{
	    for( int i=0; i<ids.length; i++)
			if (!(ids[i].equals(aBus.getID(i)))) return false;

	    if (super.equals(aBus))
			return true;

		else
			return false;

	}

	//toString method
	public String toString(){
	return "Vehicle type: " +super.getType() +"\n" +"Source txt file: " +super.getFileName() +"\n" +
			"The temporary mutual IDs are: " +mutual_ID1 +"," +mutual_ID2;
	}

	// Read_file method for Buses
	public void readFile() throws IOException{
	Scanner inFile = new Scanner(new FileReader(super.getFileName()));
		String line;
		String [] tokens=null;


		while (inFile.hasNext())	{
			GPSLocation_ArnaoutA_VegaD tempLocation = new GPSLocation_ArnaoutA_VegaD();
			line = inFile.nextLine();
			tokens=line.split(",");
			tempLocation.setName(tokens[3]);
			tempLocation.setLat(tokens[7]);
			tempLocation.setLon(tokens[8]);
			ids[super.getIndex()] = tokens[0]; //before add method to be in the correct index
			super.add(tempLocation);



		}

			inFile.close();


	}


	//Return the location of the transfer stop
	// To get the intersection stop between the bus passing by the origin and the one passing by the destination
	// so if the name of the first bus ( x1 & y1 ) and the second one is (x2 & y2) we need to make sure that y1 == x2 which means these
	//two buses are intersecting with each other. Also we need to make sure that the intersection contains one of
	//the destination streets.
	public int transfer(int pos1, int pos2){
		int pos3 = -1;
		String oriName = super.getObject(pos1).getName();
		String desName = super.getObject(pos2).getName(); //Name of destination's stop
		String aStreet2 = desName.substring(1,desName.indexOf(" &")); //Name of the first intersection's street
		String bStreet2 = desName.substring(desName.lastIndexOf("& ")+2,desName.length()-1); //Name of the second intersection's street
		String bStreet1, aStreet3;

		if(ids[pos1].equals(ids[pos2])) {mutual_ID1 = ids[pos1];  return -1;} //no need to transfer. the same bus line
		else if(oriName.contains(aStreet2)) {mutual_ID1 = ids[pos2];  return -1;} //no need to transfer. the same a Street
		else if(oriName.contains(bStreet2)) {mutual_ID1 = ids[pos1];  return -1;} //no need to transfer. the same b Street

		else // look up for transfer stop

		{

			//search into origin bus line's stops for any stop by which user could transfer from to the destination's bus
			int i=0,j=0;
			String temp,temp2 = null;
			boolean loopGuardian=false;
			boolean loopGuardian2=false;

			while(i<super.getIndex() && loopGuardian==false)
					{
					 	if(ids[pos1].equals(ids[i])) //the bus passing by the origin
					 	{	temp = super.getObject(i).getName();
					 		bStreet1 = temp.substring(temp.lastIndexOf("& ")+2,temp.length()-1); //the secondary street
					 		if (bStreet1.equals(aStreet2) || bStreet1.equals(bStreet2))
					 		{
					 			//inner loop to scan the intersection stop between the origin bus and destination bus
					 			while(j<super.getIndex() && loopGuardian2 == false)
					 				{
					 					if(ids[pos2].equals(ids[j]))
					 					{	//the bus passing by the destination
					 						temp2 = super.getObject(j).getName();
					 						aStreet3 = temp2.substring(1,temp2.indexOf(" &")); //the primary street

					 						if(bStreet1.equals(aStreet3))
					 						{pos3=i; loopGuardian2= true;}
					 					}

					 					j++;
					 				}
					 		}

					 	}
						i++;
					}

		}

		if (pos3 == -1) {mutual_ID1 = ids[pos1];return -2;} //transfer is not applicable
		else {mutual_ID1 = ids[pos1];	mutual_ID2 = ids[pos2];
			  return pos3; // position of the transfer stop
			}

	}




}
