// Encapsulated string array
// *************************************

public class StringArray {

		final int MAX_SIZE = 100;
			
		//instance variables
		String [] strArr;
		int index;
		
		//default constructor
		public StringArray(){
			strArr= new String[MAX_SIZE];
			index = 0;
			
		}
		
		//non-default constructor
		public StringArray(int size){
			strArr= new String[size];
			index = 0;
			
		}
		
		
		//accessor
		
		public String getElement(int position){
			return strArr[position];
			
		}
		public int getIndex(){
			return index;
			
		}
		
		
		public String[] getEntire(){
			String[] copy=new String[strArr.length];
			for( int i=0; i<index; i++) copy[i] = strArr[i];
			
			return copy;
		}
		
		//mutator
		
		public void add(String object){
			strArr[index] = object;
			index++;
		}
		
		public void delete(int pos){
			if (pos>0 && pos<index){
				for( int i=pos; i<index; i++)
				strArr[i] = strArr[i+1];
				index--;			}
						}
		
		public void insert(int pos, String object){
			for( int i=index; i>pos; i--)
			strArr[i] = strArr[i-1];
			strArr[pos] = object;
			index++;
		}
		
		//is Empty
		public boolean isEmpty(){
						 if(index == 0) return true;
						 else return false;
					 }
					 
		//is Full
		public boolean isFull(){
						if(index==strArr.length) return true;
						else return false;
					 }
		//trim
		public void trim(){
			String [] temp= new String[index];
			for( int i=0; i<index; i++) temp[i]=strArr[i];
			
			strArr = temp;
				
			}
		
		//euqals method
		public boolean equals(StringArray n){
			
		if(n.getIndex()!=index) return false;
		if(strArr.length!=n.getEntire().length) return false;
		
		for( int i=0; i<strArr.length; i++)
			if (!(strArr[i].equals(n.getElement(i)))) return false;
		
		return true;}
			
		//compareTo
		public int compareTo(StringArray n){
			for( int i=0; i<index; i++){
				if(strArr[i].compareTo(n.getElement(i))>1) return 1;
				else if(strArr[i].compareTo(n.getElement(i))<1) return -1;
				
				}
			 return 0;
		}
			 
		//isThere Method
			 public int isThere(String o){
				 for( int i=0; i<index; i++) {
					 if(strArr[i].equals(o))return i;
				 }
				 return -1;
			 }
			 
			 //more capacity; I added 5 more places 
			 public void moreCapacity() {
							 	final int increment = 500;
							 	String [] temp = new String[index+increment];
								 for( int i=0; i<index; i++) 
									 temp[i] = strArr[i];
								 strArr = temp;
										}	 
			
			 //swap method
			 public void swap(int pos1, int pos2)
				{
					String temp = strArr[pos1];
					strArr[pos1] = strArr[pos2];
					strArr[pos2] = temp;
					
				}
			 
}


