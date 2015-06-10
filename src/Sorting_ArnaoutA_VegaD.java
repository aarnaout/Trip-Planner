import java.util.Scanner;
import java.io.*;

public class Sorting_ArnaoutA_VegaD{

	//instance variables
	int position; //the position of the number which the file will be ordered in term of it.
	String unsortedFileName, sortedFileName;

	//default constructor
	public Sorting_ArnaoutA_VegaD(){
		position = 0;
		unsortedFileName ="unsorted.txt";
		sortedFileName = "sorted.txt";
	}

	public Sorting_ArnaoutA_VegaD(String FileName_Unsorted, String FileName_sorted, int pos){
		position = pos;
		unsortedFileName =FileName_Unsorted;
		sortedFileName = FileName_sorted;
	}

	//accessor
	public int getPostion(){
		return position;
	}

	public String getUnsortedFileName(){
		return unsortedFileName;
	}
	public String getSortedFileName(){
		return sortedFileName;
	}


	//mutator
	public void setPosition(int aPosition){
		position = aPosition;
	}

	public void setFileName_Unsorted(String aFileName_Unsorted){
		unsortedFileName = aFileName_Unsorted;
	}

	public void setFileName_sorted(String aFileName_sorted){
		sortedFileName = aFileName_sorted;
	}


	//toString
	public String toString(){
		return "This file: " +unsortedFileName +" is going to be sorted in term of the numbers contained in the position " +position
				+" . The sorted output file's name: " +sortedFileName;
	}

	//equals
	public boolean equals(Sorting_ArnaoutA_VegaD x){
		if (position == x.getPostion() && unsortedFileName.equals(x.getUnsortedFileName()) && sortedFileName.equals(x.getSortedFileName()) )
			return true;
		else
			return false;
	}

	//this method sorts and outputs the file passed to this class
	public void sort() throws IOException{
		StringArray t = new StringArray(2000);
		Scanner inFile = new Scanner(new FileReader(unsortedFileName));
		String line;
		String [] tokens=null, temp_tokens=null;
		double d1,d2;



		//read the file and then store it into the array
		while (inFile.hasNext()) {
			line = inFile.nextLine();
			if(t.isFull()) t.moreCapacity();
			t.add(line);
			}
		t.trim();
		inFile.close();
		//outer loop
		for (int i=0;i<t.getIndex();i++)
		{
			tokens = t.getElement(i).split(",");
			d1 = Math.abs(Double.parseDouble(tokens[position]));

			//inner loop
			for(int j=i+1;j<t.getIndex();j++)
			{
			temp_tokens = t.getElement(j).split(",");
			d2 = Math.abs(Double.parseDouble(temp_tokens[position]));
			if(d2<d1)
				{t.swap(i, j); d1=d2;}
			}
		}

		PrintWriter out = new PrintWriter(new FileWriter(sortedFileName));
		for (int k=0;k<t.getIndex()-1;k++)
			out.println(t.getElement(k));
		out.print(t.getElement(t.getIndex()-1)); //to avoid printing an empty line at the end
		out.close();

	}






	}
