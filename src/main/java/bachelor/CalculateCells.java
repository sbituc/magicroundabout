package bachelor;

public class CalculateCells {
	
	public static void main( String[] args )
    {
//  - id  : 10
//    to  : [ 11 ]
//    lat : 51.562350
//    lon : -1.771391
		int x10 = 585163;
		int y10 = 5713079;
//    utmz: 30u

//  - id  : 11
//    to  : [ 12, 112, 28 ]
//    lat : 51.562514
//    lon : -1.771438
		int x11 = 585160;
		int y11 = 5713097;
//    utmz: 30u
		
//	  - id  : 12
//	    to  : [ 13, 27, 112 ]
//	    lat : 51.562559
//	    lon : -1.771635
	    int x12 = 585146;
	    int y12 = 5713102;
//	    utmz: 30u
	    
//	    - id  : 13
//	    to  : [ 14 ]
//	    lat : 51.562615
//	    lon : -1.771587
	    int x13 = 585149;
	    int y13 = 5713108;
//	    utmz: 30u
		
		/*
		 * straight line equation
		 *  y = mx + n
		 *  m - lineSlope
		 *  n - yIntercept
		 */
		double lineSlope = (y11 - y10)/(x11 - x10);
		System.out.println(lineSlope);
		double yIntercept = y10 - lineSlope * x10;
		System.out.println(yIntercept);

		/*
		double lineSlope2 = (y12 - y11)/(x12 - x11);
		System.out.println(lineSlope2);
		double yIntercept2 = y11 - lineSlope2 * x11;
		System.out.println(yIntercept2);

		double lineSlope3 = (y13 - y12)/(x13 - x12);
		System.out.println(lineSlope3);
		double yIntercept3 = y12 - lineSlope2 * x12;
		System.out.println(yIntercept3);
		*/
		
		
		
    }

}
