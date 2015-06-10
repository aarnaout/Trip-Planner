/* Arnaout, Abdulrhman.
 * Vega, Daniel.
 * CS 201, Fall 2012
 * Nov. 27, 2012
 * 
 * Final Project (CTA Trip Planner)
 * This class is used to save an GPS location defined by its longitude, latitude and a name. Also it includes a
 * method to calculate the distance between to GPS locations per Kilo meter unit.
 * 
 */

public class GPSLocation_ArnaoutA_VegaD {
	

	//instance variables
		private String lat,lon,name;
		

	//default constructor
			public GPSLocation_ArnaoutA_VegaD() {
				
				//start point 31st stop, end point Art Institute
				lat= "";
				lon= "";
				name = "";
				
			}
			
	//non-default constructor
			public GPSLocation_ArnaoutA_VegaD(String latitude,String longitude,String n) {
				lat = latitude;
				lon = longitude;
				name = n;
				}	
	//non-default constructor
			public GPSLocation_ArnaoutA_VegaD(String latitude,String longitude) {
				lat = latitude;
				lon = longitude;
				name ="";
				}	
			
			
	//accessor 
			public String getLat() {return lat;}
			public String getLon() {return lon;}
			public String getName() {return name;}
		
			
	//mutator
			public void setLat (String latitude){
				lat = latitude;
			}
		
			public void setLon (String longitude){
				lon = longitude;
			}
			
			public void setName (String n){
				name = n;
			}
						
	//toString
			public String toString(){
				return (name +": " +lat +"," +lon);
			}
			
	//equals method
			public boolean equals(GPSLocation_ArnaoutA_VegaD x){ 
				if (lat.equals(x.getLat()) && lon.equals(x.getLon())  && name.equals(x.getName())) return true;
				else return false;
						
	
			}
			
	//calcDistance method per Km
			public double calcDistance(GPSLocation_ArnaoutA_VegaD aStop) 
			{
				double lat1 = Double.parseDouble(lat);
				double lon1 = Double.parseDouble(lon);
				double lat2 = Double.parseDouble(aStop.getLat());
				double lon2 = Double.parseDouble(aStop.getLon());
				double EarthRadius = 6378.1370D;
				double d2r = (Math.PI / 180D);
				
				double dlong = (lon2 - lon1) * d2r;
				double dlat = (lat2 - lat1) * d2r;
				double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * d2r) * Math.cos(lat2 * d2r) * Math.pow(Math.sin(dlong / 2D), 2D);
				double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
				double distance = EarthRadius * c;

				    				
				return distance;

			}
	
	

}
