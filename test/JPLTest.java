import jpl.*;

public class JPLTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		System.out.println("Y=" + (new Query("X=3, Y is X+2").oneSolution().get("Y")) );
	}

}
