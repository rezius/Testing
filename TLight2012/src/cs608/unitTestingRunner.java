package cs608;


import java.io.*;

import java.lang.reflect.Method;
import java.math.*;


public class unitTestingRunner {

	int runs=0;
	int fails=0;
	int successes=0;
	int executionFailures=0;
	static String reason;
	
	void run(String sut) throws IOException {
		runTests(sut);
		report();
	}

	void runTests(String sutName) throws IOException {
		FileWriter fstream = null;
		try {
			fstream = new FileWriter("testReport.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  BufferedWriter out = new BufferedWriter(fstream);
		try {
			for (Method m:Class.forName(sutName).getMethods())
				if (m.isAnnotationPresent(MyTest.class)) {
					runs++;
					if ((Boolean)m.invoke(null)) {
						successes++;
					}
					else {
						fails++;
						System.out.println("Test "+m.getName()+" failed: "+reason);
						out.write("Test "+m.getName()+" failed: "+reason +"\n");
					}
				}
		} catch (Throwable ex) {
			System.out.println(ex.toString());
			try {
				out.write(ex.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		out.write("Report on the tests runned: \n");
		out.write(" **** runs="+runs+"\n");
		out.write(" **** fails="+fails+"\n" );
		out.write(" **** successes="+successes+"\n"  );
		out.write(" **** executionFailures="+executionFailures +"\n");
		double percentageOfSuccess;
		double percentageOfFailures;
		percentageOfSuccess = successes/(runs*1.0);
		percentageOfFailures =  fails/(runs*1.0);
		BigDecimal successRounded = new BigDecimal(percentageOfSuccess);
		successRounded = successRounded.setScale(3,BigDecimal.ROUND_HALF_UP);
		
		BigDecimal failureRounded = new BigDecimal(percentageOfFailures);
		failureRounded = failureRounded.setScale(3, BigDecimal.ROUND_HALF_UP);
		
		out.write("Percentage of success tests is: " + successRounded.doubleValue()*100 + "% \n");
		out.write("Percentage of failure tests is: " + failureRounded.doubleValue()*100 + "% \n");
		out.close();
	}
	
	void report() {
		
		System.out.println("Report");
		System.out.println("   runs="+runs);
		System.out.println("   fails="+fails);
		System.out.println("   successes="+successes);
		System.out.println("   executionFailures="+executionFailures);
	}
	
	public static void main(String[] args) throws IOException {

		unitTestingRunner r=new unitTestingRunner();
		r.run("cs608.LightsTest");

	}	

}
