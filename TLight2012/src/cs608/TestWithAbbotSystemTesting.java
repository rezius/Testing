package cs608;

//
// Sample GUI system test for the ManualControl application using Abbot as a test tool
//

import static org.junit.Assert.*;
import abbot.tester.*;
import abbot.tester.Robot;
import abbot.finder.*;

import java.awt.Component;

import org.junit.*;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import cs608.ManualControl;

public class TestWithAbbotSystemTesting {

	static private boolean runningFromMain=false;
	static String logFileName="TestWithAbbot.log";

	ManualControl sut;
	BasicFinder f;

	int tests=0, testsPassed=0, testsFailed=0, testsError=0;

	String name;

	JButton button_1=null, button_2=null, button_3=null;
	Lamp lamp_1=null, lamp_2=null, lamp_3=null, lamp_4=null, lamp_5=null, lamp_6=null,
		 lamp_7=null, lamp_8=null,lamp_9=null,lamp_10=null,lamp_11=null,lamp_12=null;

	JTextComponentTester textTester;
	WindowTester windowTester;
	Robot robot; // Abbot robot

	// setup the test execution environment, and startup the application
	@Before public void setup() {
		// create a text tester for text-type components
		textTester = new JTextComponentTester();
		// create a windows tester for window-type components
		windowTester = new WindowTester();
		// create an abbot robot
		robot = new abbot.tester.Robot();
		// set the 
		// start the application
		sut=new ManualControl();
		while (!sut.isShowing());
		robot.waitForIdle();
		// create a finder
		f=new BasicFinder();
		// find the components needed for the tests
		try {			
			lamp_1 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "NORTHGREEN") );
			lamp_2 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "NORTHAMBER") );
			lamp_3 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "NORTHRED") );
			lamp_4 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "SOUTHGREEN") );
			lamp_5 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "SOUTHAMBER") );
			lamp_6 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "SOUTHRED") );
			lamp_7 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "EASTGREEN") );
			lamp_8 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "EASTAMBER") );
			lamp_9 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "EASTRED") );
			lamp_10 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "WESTGREEN") );
			lamp_11 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "WESTAMBER") );
			lamp_12 = (Lamp)f.find(sut, new NameMatcher(Lamp.class, "WESTRED") );
						
			button_1 = (JButton)f.find(sut, new JButtonTextMatcher("CYCLE") );
			button_2 = (JButton)f.find(sut, new JButtonTextMatcher("LOCK") );
			button_3 = (JButton)f.find(sut, new JButtonTextMatcher("EXIT") );
		}
		catch (Exception e) {
			System.out.println("Exception in test setup");
			System.out.println(e.toString());
			e.printStackTrace();
			System.exit(1);
		}
	}

	// test1
	@Test public void test1() {

		boolean success=true;
		tests++;
		name=new String("test1");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.green, lamp_1.getBackground() )) success=false;
				if (!check( Color.green, lamp_4.getBackground() )) success=false;
				if (!check( Color.red, lamp_9.getBackground() )) success=false;
				if (!check( Color.red, lamp_12.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}

	}

	// test2
	@Test public void test2() {

		boolean success=true;
		tests++;
		name=new String("test2");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.orange, lamp_2.getBackground() )) success=false;
				if (!check( Color.orange, lamp_5.getBackground() )) success=false;
				if (!check( Color.red, lamp_9.getBackground() )) success=false;
				if (!check( Color.red, lamp_12.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}

	}

	// test3
	@Test public void test3() {

		boolean success=true;
		tests++;
		name=new String("test3");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.red, lamp_3.getBackground() )) success=false;
				if (!check( Color.red, lamp_6.getBackground() )) success=false;
				if (!check( Color.red, lamp_9.getBackground() )) success=false;
				if (!check( Color.red, lamp_12.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}

	}

	// test4
	@Test public void test4() {

		boolean success=true;
		tests++;
		name=new String("test4");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.red, lamp_3.getBackground() )) success=false;
				if (!check( Color.red, lamp_6.getBackground() )) success=false;
				if (!check( Color.green, lamp_7.getBackground() )) success=false;
				if (!check( Color.green, lamp_10.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}
	}
	
	// test5
	@Test public void test5() {

		boolean success=true;
		tests++;
		name=new String("test5");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.red, lamp_3.getBackground() )) success=false;
				if (!check( Color.red, lamp_6.getBackground() )) success=false;
				if (!check( Color.orange, lamp_8.getBackground() )) success=false;
				if (!check( Color.orange, lamp_11.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}
	}

	// test6
	@Test public void test6() {

		boolean success=true;
		tests++;
		name=new String("test6");

		try {
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.red, lamp_3.getBackground() )) success=false;
				if (!check( Color.red, lamp_6.getBackground() )) success=false;
				if (!check( Color.red, lamp_9.getBackground() )) success=false;
				if (!check( Color.red, lamp_12.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}
	}
	
	// test7
	@Test public void test7() {

		boolean success=true;
		tests++;
		name=new String("test7");

		try {
			button_2.doClick();
			robot.waitForIdle();
			button_1.doClick();
			robot.waitForIdle();
			if (runningFromMain) {
				if (!check( Color.red, lamp_3.getBackground() )) success=false;
				if (!check( Color.red, lamp_6.getBackground() )) success=false;
				if (!check( Color.red, lamp_9.getBackground() )) success=false;
				if (!check( Color.red, lamp_12.getBackground() )) success=false;
				if (success) testsPassed++;
				else testsFailed++;
			}
		}
		catch (Exception e) {
			testsError++;
		}
	}

	/*@After public void closeDown() {
		windowTester.actionClose(sut);
	}*/

	// check a string is correct
	boolean check( String expected, String actual ) {
		if (!actual.equals(expected)) {
			System.out.println("FAIL test "+name+": "+actual+" != "+expected);
			return false;
		}
		return true;
	}

	// check a Color is correct
	boolean check( Color expected, Color actual ) {
		if (actual!=expected) {
			System.out.println("FAIL test "+name+": "+actual+" != "+expected);
			return false;
		}
		return true;
	}

	// print the test results
	public void printResults() {
		PrintStream log=null;		
		System.out.println("Test Results for application Traffic Lights Manual Control");
		System.out.println("   test date : "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		System.out.println("   tests run: "+tests);		
		System.out.println("   tests passed: "+testsPassed);		
		System.out.println("   tests failed: "+testsFailed);		
		System.out.println("   tests didn't run: "+testsError);		
		try {
			log = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(logFileName), true)));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to create output log file "+logFileName);
			System.exit(1);
		}
		System.out.println("Test Results for application Traffic Lights Manual Control");
		log.println("   test date : "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		log.println("   tests run: "+tests);		
		log.println("   tests passed: "+testsPassed);		
		log.println("   tests failed: "+testsFailed);		
		log.println("   tests didn't run: "+testsError);		
		log.close();
	}

	// run the tests
	public static void main(String[] args) {
		TestWithAbbotSystemTesting t=new TestWithAbbotSystemTesting();
		runningFromMain = true;
		t.setup();
		t.test1();
		t.test2();
		t.test3();
		t.test4();
		t.test5();
		t.test6();
		t.test7();
		t.printResults();
	}

}

	
// you have to define a new Matcher for each match - so easiest to define a generic matcher class
// match by class & name
class NameMatcher implements Matcher {

	private Class<?> type;
	private String name;

	NameMatcher(Class<?> findType, String findName) {
		type = findType;
		name = new String(findName);
	}

	// return true if the Component matches the specified Class and name
	public boolean matches(Component c) {
		return ( (c.getClass()==type) && (c.getName()!=null) && (c.getName().equals(name)) );
	} 

}

//match by JButton by text
class JButtonTextMatcher implements Matcher {

	private String text;

	JButtonTextMatcher(String findText) {
		text = new String(findText);
	}

	// return true if the Component matches the specified Class and name
	public boolean matches(Component c) {
		return ( (c.getClass()==JButton.class) && (((JButton)c).getText()!=null) && (((JButton)c).getText().equals(text)) );
	}
	
}