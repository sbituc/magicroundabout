package bachelor;

public class calculateCells {
	
	public static void main( String[] args )
    {
//  - id  : 10
//    to  : [ 11 ]
//    lat : 51.562350
//    lon : -1.771391
		int startX = 585163;
		int startY = 5713079;
//    utmz: 30u

//  - id  : 11
//    to  : [ 12, 112, 28 ]
//    lat : 51.562514
//    lon : -1.771438
		int endX = 585160;
		int endY = 5713097;
//    utmz: 30u
		
		/*
		 * straight line equation
		 *  y = mx + n
		 *  m - lineSlope
		 *  n - yIntercept
		 */
		double lineSlope = (endY - startY)/(endX - startX);
		System.out.println(lineSlope);
		
		double yIntercept = startY - lineSlope * startX;
		System.out.println(yIntercept);
		
    }

}
