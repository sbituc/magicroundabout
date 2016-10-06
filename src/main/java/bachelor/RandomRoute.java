package bachelor;

import java.util.Random;

public class RandomRoute 
{
    public static void main( String[] args )
    {
        String[] roadNamesList = {"County Road", "Drove Road", "Fleming Way", "Queen's Drive", "Shrivenham Road"};
        
        int j = 0;
        
        Random random = new Random(System.currentTimeMillis());
        
        for (int i = 0; i < 60000; i++) {
        	
	        int randomStart = random.nextInt(roadNamesList.length);
	        int randomEnd = random.nextInt(roadNamesList.length);
	        
	        /*
	         * chance of start point being the same as the end point was roughly at 20%
	         * reassigning a new end point in case of equality drops the rate to around 4% 
	         */
	        if (randomStart == randomEnd) {
				randomEnd = random.nextInt(roadNamesList.length);
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
