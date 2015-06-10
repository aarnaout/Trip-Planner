/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 *
 * Final Project (CTA Trip Planner)
 * This is the application class.
 * This is a trip planner contains all the CTA trains (except Yellow line), in addition to twenty buses whose numbers are:
 * 1,2,3,4,6,7,9,10,11,12,21,29,53,55,72,73,74,91,92,152. In fact, we can add buses as much as we want.
 *
 * The program will prompt the user to enter the coordinates of both the origin and destination either manually or from a menu.
 * And then it decides which one of these modes are the best:
 * - Trains only mode
 * - Buses only mode
 * - Hybrid mode: which contains three sub modes: (trains --> buses) , (buses --> trains) or (buses --> trains --> buses).
 *
 * Output, it will print out the trip information along with the whole distance the user should walk. This information
 * will be printed out to the console and stored in a txt file that its name contains the timestamp of the run.
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class App {

	public static void main(String[] args) throws IOException {

		//These sorting command are used only one time to generate 4 sorted txt files sorted by longitude or latitude
		// ***NOTE*** It will take a long time to run ONLY the first time you run, after that it'll work so fast.
		File f1 = new File("BuseStopsSLat.txt");File f2 = new File("BuseStopsSLon.txt");
		File f3 = new File("elStopsSLat.txt");File f4 = new File("elStopsSLon.txt");
		if(!f1.exists() || !f2.exists() || !f3.exists() || !f4.exists())
		{
		Sorting_ArnaoutA_VegaD sBusLat = new Sorting_ArnaoutA_VegaD("BusesStops_ArnaoutA_VegaD.txt","BuseStopsSLat.txt",7);
		Sorting_ArnaoutA_VegaD sBusLon = new Sorting_ArnaoutA_VegaD("BusesStops_ArnaoutA_VegaD.txt","BuseStopsSLon.txt",8);
		Sorting_ArnaoutA_VegaD sTrainLat = new Sorting_ArnaoutA_VegaD("elStops_ArnaoutA_VegaD.txt","elStopsSLat.txt",3);
		Sorting_ArnaoutA_VegaD sTrainLon = new Sorting_ArnaoutA_VegaD("elStops_ArnaoutA_VegaD.txt","elStopsSLon.txt",4);
		sBusLat.sort();sBusLon.sort();sTrainLat.sort();sTrainLon.sort();
		} // end of sorting

		//Prompting the user
		User_Prompter_ArnaoutA_VegaD prompt = new User_Prompter_ArnaoutA_VegaD();
		String[] menuChoice = prompt.menu();

while (!menuChoice[0].equals("0")) //loop the whole program
		{

		//Creates an instance of each of Buses and Trains classes
			Buses_ArnaoutA_VegaD bus = new Buses_ArnaoutA_VegaD("busesStops_ArnaoutA_VegaD.txt", "Bus",3200);
			Trains_ArnaoutA_VegaD train = new Trains_ArnaoutA_VegaD("elStops_ArnaoutA_VegaD.txt","Train");


		//Reads the files of trains and buses stops
				train.readFile();
				bus.readFile();


		//diffining new decimal format for the output distance
		DecimalFormat decimal = new DecimalFormat("##.###");

		//declare variables of distances between stops
		double trainD1,trainD2,busD1,busD2,totalTrainD,totalBusD;

		//storing coordinates information in objects of GPSLocation
		GPSLocation_ArnaoutA_VegaD origin = new GPSLocation_ArnaoutA_VegaD(menuChoice[0],menuChoice[1],menuChoice[2]);
		GPSLocation_ArnaoutA_VegaD destination = new GPSLocation_ArnaoutA_VegaD(menuChoice[3],menuChoice[4],menuChoice[5]);

		final double MAX = 0.7; // A constant of the acceptable distance. Helps to accelerate the code.

		//Gets nearest stop to origin for both train and bus. In addition to the distances to each one of them.
		int closestBus_Origin = bus.getNearestStop(origin); busD1 = bus.getTempDistance();
		int closestTrain_Origin = train.getNearestStop(origin); trainD1 = train.getTempDistance();

		//Gets nearest stop to destination for both train and bus
		int closestBus_dest = bus.getNearestStop(destination); busD2 = bus.getTempDistance();
		int closestTrain_dest = train.getNearestStop(destination); trainD2 = train.getTempDistance();

		// determine if there is a need to transfer for trains or buses
		int train_transfer = train.transfer(closestTrain_Origin, closestTrain_dest);
		int bus_transfer = bus.transfer(closestBus_Origin, closestBus_dest);
		String train_MutualColor1 = train.getMutual_Color1(), train_MutualColor2 = train.getMutual_Color2();
		String bus_MutualID1 =  bus.getMutual_ID1(), bus_MutualID2 = bus.getMutual_ID2();

		//total distance for both trains & buses
		totalTrainD = trainD1 + trainD2;
		totalBusD = busD1 + busD2;

//Check 3 possible cases
		boolean trainsBetter = (totalTrainD < totalBusD) || (bus_transfer == -2); //train is better in term of the distance or application?
		boolean busesBetter = (totalBusD < totalTrainD) && (bus_transfer != -2); // buses better in term of distance?

		// (1) Trains are enough
		if ((totalTrainD < MAX ) && trainsBetter)
			{train.getRoute(closestTrain_Origin,closestTrain_dest, train_transfer,train_MutualColor1,train_MutualColor2);
			System.out.println("The whole distance you need to walk is approximately: " +decimal.format(totalTrainD) +" Km");
			}


		// (2) buses are enough
		else if ((totalBusD < MAX) && busesBetter)
			{bus.getRoute(closestBus_Origin, closestBus_dest, bus_transfer,bus_MutualID1,bus_MutualID2);
			System.out.println("The whole distance you need to walk is approximately: " +decimal.format(totalBusD) +" Km");
			}


		// (3) hybrid mode
		else // we have 3 new possible cases here: (bus->train), (bus->train->bus) or (train->bus)
		{
			/////////////////////declare some important variables ////////////////////////////////////
			boolean output=false;  // to determine that we already had a good solution or we gotta choose one of the previous solutions
			final double C = 0.7; // coefficient of efficiency used to prevent choosing one stop just below the MAX distance in one part
			double bus_train_TD = 1000, bus_train_bus_TD = 1000, train_bus_TD = 1000, bus_trainD; //total distance for each case instantiated with a big value

			//two elements array to store the intersection-stop's position of both bus & train
			int[] bus_train=new int[2], bus_train_bus= new int[2]; //positions of intersection stops

			int bus_trainBTransfer = 0,bus_train_busTransfer = 0, bus_train_bus_Ttransfer = 0; //position of transfer stops
			int train_busTTransfer = 0,train_busBTransfer = 0, bus_trainTTransfer = 0;
			String bus_trainMutualID1 = null,bus_trainMutualID2 = null, bus_train_busMutualID1 = null, bus_train_busMutualID2 = null;
			String train_busMutualID1 = null, train_busMutualID2 = null, bus_trainMutualColor1 = null,bus_trainMutualColor2 = null;
			String bus_train_busMutualColor1 = null,bus_train_busMutualColor2 = null, train_busMutualColor1 = null,train_busMutualColor2 = null;

			int[] train_bus = null;
			//////////////////////////////////////end of declaration///////////////////////////////////


		//case (bus->train)

			if((busD1+trainD2)<MAX)
					{
					bus_train=train.getNearestIntersection(bus, bus.getID(closestBus_Origin));
					bus_trainD = train.getIntersection_distance();
			 		bus_train_TD = busD1 + bus_trainD + trainD2;

			 		bus_trainBTransfer = bus.transfer(closestBus_Origin, bus_train[0]); //no transfer, but just to help the getMutual_ID
			 		bus_trainMutualID1 = bus.getMutual_ID1(); 	bus_trainMutualID2 = bus.getMutual_ID2();
			 		bus_trainTTransfer = train.transfer(bus_train[1], closestTrain_dest); //no transfer, but just to help the getMutual_color
			 		bus_trainMutualColor1 = train.getMutual_Color1(); bus_trainMutualColor2 = train.getMutual_Color2();

			 		if (bus_train_TD < MAX ) //test if bus-->train is enough
			 			{
			 			bus.getRoute(closestBus_Origin, bus_train[0], bus_trainBTransfer,bus_trainMutualID1,bus_trainMutualID2);
			 			train.getRoute(bus_train[1], closestTrain_dest, bus_trainTTransfer ,bus_trainMutualColor1,bus_trainMutualColor2);
			 			output = true;
			 			System.out.println("The whole distance you need to walk is approximately: " +decimal.format(bus_train_TD) +" Km");
			 			}
					}
		//case  (bus-->train-->bus)
			 else if(totalBusD < MAX){
				 			//copy the same code from the previous bus-train case
				 			bus_train=train.getNearestIntersection(bus, bus.getID(closestBus_Origin));

							bus_trainD = train.getIntersection_distance();
			 				bus_train_TD = busD1 + bus_trainD + trainD2;

			 				bus_trainBTransfer = bus.transfer(closestBus_Origin, bus_train[0]); //no transfer, but just to help the getMutual_ID
			 				bus_trainMutualID1 = bus.getMutual_ID1(); 	bus_trainMutualID2 = bus.getMutual_ID2();
			 				bus_trainTTransfer = train.transfer(bus_train[1], closestTrain_dest); //no transfer, but just to help the getMutual_color
			 				bus_trainMutualColor1 = train.getMutual_Color1(); bus_trainMutualColor2 = train.getMutual_Color2();
			 				// end of copy

			 				bus_train_bus = train.getNearestIntersection(bus, bus.getID(closestBus_dest));
			 				bus_train_bus_TD = busD1 + bus_trainD + busD2 + train.getIntersection_distance();

			 				bus_train_bus_Ttransfer = train.transfer(bus_train[1], bus_train_bus[1]); //no transfer, but just to help the getMutual_color
			 				bus_train_busMutualColor1 = train.getMutual_Color1();bus_train_busMutualColor2 = train.getMutual_Color2();
			 				bus_train_busTransfer = bus.transfer(bus_train_bus[0], closestBus_dest); //no transfer, but just to help the getMutual_ID
			 				bus_train_busMutualID1 = bus.getMutual_ID1(); bus_train_busMutualID2 = bus.getMutual_ID2();

			 				if  (bus_train_bus_TD < MAX ) //test if (bus-->train-->bus) is enough
			 					{
			 						bus.getRoute(closestBus_Origin, bus_train[0], bus_trainBTransfer,bus_trainMutualID1,bus_trainMutualID2); //the same as old step (bus-->train)
			 						train.getRoute(bus_train[1], bus_train_bus[1], bus_train_bus_Ttransfer ,bus_train_busMutualColor1,bus_train_busMutualColor2); //-1 because as mentioned before about the previous -1
			 						bus.getRoute(bus_train_bus[0], closestBus_dest, bus_train_busTransfer,bus_train_busMutualID1,bus_train_busMutualID2);
			 						output = true;
			 						System.out.println("The whole distance you need to walk is approximately: " +decimal.format(bus_train_bus_TD) +" Km");
			 					}
			 			}


		//case (train->bus) might be expanded to cover this case (train-->bus-->), but I don't think it efficient!
			else
			{
				train_bus = train.getNearestIntersection(bus, bus.getID(closestBus_dest));
				train_busTTransfer = train.transfer(closestTrain_Origin, train_bus[1]);
				train_busMutualColor1 = train.getMutual_Color1(); train_busMutualColor2 = train.getMutual_Color2();
				train_bus_TD = trainD1 + busD2 + train.getIntersection_distance();

				train_busBTransfer = bus.transfer(train_bus[0], closestBus_dest);
				train_busMutualID1 = bus.getMutual_ID1(); train_busMutualID2 = bus.getMutual_ID2();

				if (train_bus_TD< MAX) //test if (train-->bus) is enough
				{
				 train.getRoute(closestTrain_Origin, train_bus[1], train_busTTransfer,train_busMutualColor1,train_busMutualColor2 );
				 bus.getRoute(train_bus[0], closestBus_dest ,train_busBTransfer ,train_busMutualID1,train_busMutualID2);
				 output = true;
				 System.out.println("The whole distance you need to walk is approximately: " +decimal.format(train_bus_TD) +" Km");
				}
			}

		if (output == false) //In case there is no ability to fulfill the MAX distance condition, present the best route of the five cases
		{	final double CC=0.5; // The second coefficient of efficiency. prevent taking another bus or train for just 0.5 Km
			//  Only trains are the BEST result
			if (trainsBetter && (totalTrainD < train_bus_TD+CC) && (totalTrainD <bus_train_TD+CC) && (totalTrainD < bus_train_bus_TD+2*CC) )
			{	// The same commands from the previous lines
				train.getRoute(closestTrain_Origin,closestTrain_dest, train_transfer,train_MutualColor1,train_MutualColor2);
				System.out.println("The whole distance you need to walk is approximately: " +decimal.format(totalTrainD) +" Km");
			}

			// Only buses are the BEST result
			else if (busesBetter && (totalBusD < train_bus_TD+CC) && (totalBusD <bus_train_TD+CC) && (totalBusD < bus_train_bus_TD+2*CC) )
			{	// The same commands from the previous lines
				bus.getRoute(closestBus_Origin, closestBus_dest, bus_transfer,bus_MutualID1,bus_MutualID2);
				System.out.println("The whole distance you need to walk is approximately: " +decimal.format(totalBusD) +" Km");
			}



			// Choose the best one of the hybrid mode's results
			else if(train_bus_TD < bus_train_bus_TD+CC && train_bus_TD<bus_train_TD) // (train_busD) is the best
			{	 // The same commands from the previous lines
				train.getRoute(closestTrain_Origin, train_bus[1], train_busTTransfer,train_busMutualColor1,train_busMutualColor2 );
				bus.getRoute(train_bus[0], closestBus_dest ,train_busBTransfer ,train_busMutualID1,train_busMutualID2);
				System.out.println("The whole distance you need to walk is approximately: " +decimal.format(train_bus_TD) +" Km");
			}

			else if(bus_train_TD<bus_train_bus_TD && bus_train_TD<train_bus_TD) // (bus_trainTotalD) is the best
			{	// The same commands from the previous lines
				bus.getRoute(closestBus_Origin, bus_train[0], bus_trainBTransfer,bus_trainMutualID1,bus_trainMutualID2);
	 			train.getRoute(bus_train[1], closestTrain_dest, bus_trainTTransfer ,bus_trainMutualColor1,bus_trainMutualColor2);
	 			System.out.println("The whole distance you need to walk is approximately: " +decimal.format(bus_train_TD) +" Km");
			}

			else // (bus_train_bus) is the best
			{	// The same commands from the previous lines
				bus.getRoute(closestBus_Origin, bus_train[0], bus_trainBTransfer,bus_trainMutualID1,bus_trainMutualID2); //the same as old step (bus-->train)
				train.getRoute(bus_train[1], bus_train_bus[1], bus_train_bus_Ttransfer ,bus_train_busMutualColor1,bus_train_busMutualColor2); //-1 because as mentioned before about the previous -1
				bus.getRoute(bus_train_bus[0], closestBus_dest, bus_train_busTransfer,bus_train_busMutualID1,bus_train_busMutualID2);
				System.out.println("The whole distance you need to walk is approximately: " +decimal.format(bus_train_bus_TD) +" Km");
			}



		}

		} //end of hybrid mode


		//exporting the route information to an appropriate text file with date&time in its name
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		 Date date = new Date();

		String output_FileName = "ArnaoutA_VegaD "+dateFormat.format(date)+".txt"; //the new name of the file
		String y=""; //temporary variable to store the previous data.
		Scanner in = new Scanner(new FileReader("temp.txt"));
		PrintWriter out = new PrintWriter(new FileWriter(output_FileName));

		while (in.hasNext()) //copy the temporary file
			y=y+in.nextLine()+"\n";
		in.close();

		File f = new File("temp.txt");
		f.delete(); //delete the temporary file

		out.println(y);
		out.close();
		System.out.println("Your trip information has been printed out to this file: " +output_FileName +"\n");
		System.out.println("********************************************************************************");


		menuChoice = prompt.menu();
		}// end of the main loop

	}
}
