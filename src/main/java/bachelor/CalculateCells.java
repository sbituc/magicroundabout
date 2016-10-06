package bachelor;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class CalculateCells {

	private static double lineSlope( Point2D p1,Point2D p2 ) {
        /*
        System.out.println("P1: (" + p1.getX() + "," + p1.getY() + ")");
        System.out.println("P2: (" + p2.getX() + "," + p2.getY() + ")");
        System.out.println( p2.getY() - p1.getY() );
        System.out.println( p2.getX() - p1.getX() );
        */
		return Math.round( ((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()))*1000000.0 ) / 1000000.0;
//        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX()) ;
	}

	private static double yIntercept( Point2D p1,Point2D p2 ) {
        /*
        System.out.println("P1: (" + p1.getX() + "," + p1.getY() + ")");
        System.out.println("P2: (" + p2.getX() + "," + p2.getY() + ")");
        */
	    return Math.round( p1.getY() - lineSlope( p1, p2 ) * p1.getX() );
    }

    private static double distance( Point2D p1,Point2D p2 ) {
        /*
        System.out.println("P1: (" + p1.getX() + "," + p1.getY() + ")");
        System.out.println("P2: (" + p2.getX() + "," + p2.getY() + ")");
        */
        return Math.round( (Math.sqrt( Math.pow((p2.getX() - p1.getX()),2) + Math.pow((p2.getY() - p1.getY()), 2) ) )*100.0 ) / 100.0;

    }

    public static void main( String[] args ) {

//  - id  : 10
//    to  : [ 11 ]
//    lat : 51.562350
//    lon : -1.771391
		double x10 = 585163;
		double y10 = 5713079;
//    utmz: 30u

//  - id  : 11
//    to  : [ 12, 112, 28 ]
//    lat : 51.562514
//    lon : -1.771438
		double x11 = 585160;
		double y11 = 5713097;
//    utmz: 30u
		
//	  - id  : 12
//	    to  : [ 13, 27, 112 ]
//	    lat : 51.562559
//	    lon : -1.771635
	    double x12 = 585146;
	    double y12 = 5713102;
//	    utmz: 30u
	    
//	    - id  : 13
//	    to  : [ 14 ]
//	    lat : 51.562615
//	    lon : -1.771587
	    double x13 = 585149;
	    double y13 = 5713108;
//	    utmz: 30u


        Point2D p1 = new Point2D.Double(x10,y10);
        Point2D p2 = new Point2D.Double(x11,y11);

		/*
		 * straight line equation
		 *  y = mx + n
		 *  m - lineSlope
		 *  n - yIntercept
		 */
        System.out.println("lineSlope");
		System.out.println( lineSlope(p1,p2) );
        System.out.println("yIntercept");
		System.out.println( yIntercept(p1,p2) );


        double lineSlope2 = (y11 - y10)/(x11 - x10);
        System.out.println("Anstieg: " + lineSlope2);
        double yIntercept2 = y10 - lineSlope2 * x10;
        System.out.println("ySchnitt: " + yIntercept2);


//        Line2D l = new Line2D.Double(p1,p2);

        System.out.println("distance");
//        System.out.println(l.getP1() + " -- " + l.getP2());
        System.out.println(distance(p1,p2));



        // TODO solve problem with edges, where x coordinates are less than 4 m apart.

        /*
        double x_toStartWith = p1.getX();
        double x_toEndWith = p2.getX();

        if (x_toStartWith < x_toEndWith) {
            while (x_toStartWith < x_toEndWith) {
                System.out.println("value of x : " + x_toStartWith);
                x_toStartWith += 4;
            }
        }
        else {
            while (x_toStartWith > x_toEndWith) {
                System.out.println("value of x : " + x_toStartWith);
                x_toStartWith -= 4;
            }
        }
        */
		
		
    }

}
