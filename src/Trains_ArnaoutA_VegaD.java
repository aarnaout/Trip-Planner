/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 *
 * Final Project (CTA Trip Planner)
 * This class is a child of Vehicle Route class. The purpose of this class is to read train stops file, assign the appropriate
 * colors to each stop, and determine the transfer between two trains while returning the mutual train color that the user should take to
 * reach her/his destination.
 *
 */


import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Trains_ArnaoutA_VegaD extends VehicleRoute_ArnaoutA_VegaD{

			//instance variables
			private String[] colors;
			private String mutual_color1, mutual_color2;/*these two variables are only used to return additional information
			*from the transfer method */

			//default constructor
			public Trains_ArnaoutA_VegaD() {
				super();
				colors = new String[super.getEntire().length];
				mutual_color1 = null;
				mutual_color2 = null;
			}

			//nondefault constructor
			public Trains_ArnaoutA_VegaD(String aFileName, String aType) {
				super(aFileName,aType);
				colors = new String[super.getEntire().length];
				mutual_color1 = null;
				mutual_color2 = null;
			}

			//nondefault constructor
			public Trains_ArnaoutA_VegaD(String aFileName, String aType, int aNumOfStops ) {
				super(aFileName,aType,aNumOfStops);
				colors = new String[aNumOfStops];
				mutual_color1 = null;
				mutual_color2 = null;
			}



			//accessors

			public String getColor(int pos) {
				return colors[pos];
			}
			public String getMutual_Color1() {
				return mutual_color1;
			}
			public String getMutual_Color2() {
				return mutual_color2;
			}

			public String[] getColors(int pos) {
				return colors;
			}



			//mutators
			public void setColor(String aColor) {
				colors[super.getIndex()] = aColor;
			}

			public void setColors(String[] aColors) {
				colors = aColors;
			}

			public void setMutual_color1(String aColor) {
				mutual_color1 = aColor;
			}
			public void setMutual_color2(String aColor) {
				mutual_color2 = aColor;
			}


			//equals method
			public boolean equals(Trains_ArnaoutA_VegaD aTrain)
			{
			    for( int i=0; i<colors.length; i++)
					if (!(colors[i].equals(aTrain.getColor(i)))) return false;

			    if (super.equals(aTrain)) return true;

				else
				return false;

			}

			//toString method
			public String toString(){
			return "Vehicle type: " +super.getType() +"\n" +"Source txt file: " +super.getFileName() +"\n" +
					"The temporary mutual colors are: " +mutual_color1 +"," +mutual_color2;
			}


			//Return the position of Transfer Stop
			public int transfer(int pos1, int pos2){
				int pos3=-1;

				String [] tokens = colors[pos1].split(",");
				for(int i=0;i<tokens.length;i++)
					if (colors[pos2].contains((tokens[i]))) {mutual_color1 = tokens[i]; return -1;} //no need to transfer

				//transfer is required because there is no mutual color between the two stops
				//search into trains stops for any stop contains both color of destination and origin
				String temp;
				double distance = 1000;
				tokens = colors[pos1].split(","); //All the origin stop's colors
				String[] tokens2 = colors[pos2].split(","); //All the destination stop's colors

					//Outer loop to scan all the stops
					for(int i=0;i<super.getIndex();i++){
						temp = super.getObject(i).getName();


						//inner loop if a station contains at least one of...
						//the origin stop's colors and one of the destination stop's colors
						for(int j=0;j<tokens.length;j++)
								if (temp.contains(tokens[j]))
									for(int k=0;k<tokens2.length;k++)
										if(temp.contains(tokens2[k])) {
											//check if there is a transfer stop closer than this one or not
											double d = super.getObject(pos2).calcDistance(super.getObject(i));
																			if (d < distance)
																			{
																			distance = d;
																			pos3 = i;
																			mutual_color1 = tokens[j];
																			mutual_color2 = tokens2[k];
																			}
																	}
									} // finish from scanning all the stops

				return pos3; //the position of the transfer stop
			}




			// Read file method for trains
						public void readFile() throws IOException{
							Scanner inFile = new Scanner(new FileReader(super.getFileName()));
							String line;
							String [] tokens=null;


							while (inFile.hasNext()) {
								GPSLocation_ArnaoutA_VegaD tempLocation = new GPSLocation_ArnaoutA_VegaD();
								line = inFile.nextLine();
								tokens=line.split(",");
								tempLocation.setName(tokens[2]);
								tempLocation.setLat(tokens[3]);
								tempLocation.setLon(tokens[4]);


								// Looking for the color inside the name of this stop
								String tempColor = "";
								if(tempLocation.getName().contains("Green")) tempColor = tempColor +"Green,";
								if(tempLocation.getName().contains("Red")) tempColor = tempColor +"Red,";
								if(tempLocation.getName().contains("Orange")) tempColor = tempColor +"Orange";
								if(tempLocation.getName().contains("Blue")) tempColor = tempColor +"Blue";
								if(tempLocation.getName().contains("Pink")) tempColor = tempColor +"Pink";
								if(tempLocation.getName().contains("Brown")) tempColor = tempColor +"Brown";
								if(tempLocation.getName().contains("Purple")) tempColor = tempColor +"Purple";


								colors[super.getIndex()] = tempColor;
								super.add(tempLocation);
							}

								inFile.close();


						}





}
