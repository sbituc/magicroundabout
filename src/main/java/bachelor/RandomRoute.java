package bachelor;

import java.util.Random;

public class App 
{
    public static void main( String[] args )
    {
        String[] roadNamesList = {"County Road", "Drove Road", "Fleming Way", "Queen's Drive", "Shrivenham Road"};
        
        int j = 0;
        for (int i = 0; i < 60000; i++) {
        	
	        int randomStart = new Random().nextInt(roadNamesList.length);
	        int randomEnd = new Random().nextInt(roadNamesList.length);
	        
	        /*
	         * chance of start point being the same as the end point was roughly at 20%
	         * reassigning a new end point in case of equality dropped the rate to around 4% 
	         */
	        if (randomStart == randomEnd) {
				randomEnd = new Random().nextInt(roadNamesList.length);
				if (randomStart == randomEnd) {
					j++;
				}
			}
	        
	        String startPointPicked =  roadNamesList[randomStart];
	        String endPointPicked =  roadNamesList[randomEnd];
			
	        System.out.println(i + ":\t" + startPointPicked + "\t-->\t" + endPointPicked + "\t\tS=E: " + j);
	        
		}
        
    }
}
