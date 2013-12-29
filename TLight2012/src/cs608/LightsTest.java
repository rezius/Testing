package cs608;

import java.lang.*;
import cs608.LightsTest;
import cs608.MyTest;
import cs608.Lights.Color;
///////////////////////////////////////////////////////////////////
//        Black box testing for the methods estimated Lifetime
//
//        1.Equivalence partitioning
// Based on the specifications we can divide the input parameters in the following partitions:
//  Parameter leds  TRUE...FALSE
//  Parameter offCurrentFactor can be divided in 3 partitions:
//             1. offCurrentFactor<0.0  => Error case
//             2. 0.0<=offCurrentFactor<0.1
//             3. 0.1<=offCurrentFactor<=0.2
//             4. 0.2<offCurrentFactor<1.0
//             5. offCurrentFactor>1.0  => Error Case
//  Parameter Brightness can be divided in the following partitions based on the specifications:
//             1. Brightness<1.0
//             2. 1.0<=Brightness<=50.0
//             3. 50<Brightness<=100.0
//             4. Brightness>100.0
//   
//  For the output parameter we will have the values:
//  -1 in case the offCurrentFactor value is not within the range [0.0...0.1]
//  1...9999 when Led is False and offCurrentFactor is in the range [0.0...0.1[ and ]0.2...1.0]
//  10000...20000 when Led is False and  offCurrentFactor is within the range [0.1...0.2]
//  20001...100000 if the offCurrent Factor is within the range [0.0...0.1] and Led is True
//
//   BASED ON THE PARTITIONS WE CAN CREATE THE FOLLOWING TEST CASES
//
//   Case       Parameter          Range                           Test
//    1         Brightness         INT_MIN..1                       2
//    2         Brightness         1..50                            1
//    3         Brightness         51..100                          3
//    4         Brightness         101..INT_MAX                     4
//    5         leds               TRUE                             2
//    6         leds               FALSE                            3
//    7*        offCurrentFactor   <0.0                             5
//    8         offCurrentFactor   0.0<=offCurrentFactor<0.1        3
//    9         offCurrentFactor   0.1<=offCurrentFactor=<0.2       1
//    10        offCurrentFactor   0.2<offCurrentFactor<=1.0        
//    11*       offCurrentFactor   >1.0
//    Output Parameter
//    12        estimate           1..9999                          4
//    13        estimate           10000...20000                    1
//    14        estimate           20001...1000000                  2
//    15        estimate            -1                              5
//    Based on the test cases we can derive the following test data:
//    
//    ID        Test cases covered      Brightness      leds    offCurrentFactor     ExpectedOutput
//    1          2,6,9,13                 6             FALSE    0.1                 Expected between 10000 and 20000
//    2          1,5,10,14                -3            TRUE     0.5                 Expected less than or equal to 1000000				 
//    3          3,6,8,12                97             FALSE    0.05                Expected less than 10000
//    4          4,6,8,15                200            FALSE    0.4                 Expected less than 10000                 
//    5          7*                      150            TRUE    -0.5                 Expected equal to -1
//    6          11*                     56             FALSE    2.4                 Expected equal to -1
//
//
//
//    2.Boundary Value Analysis 
//    Based on the equivalence partitions defined the following Test cases canbe derived for the Boundary value analysis:
//
//    Case       Parameter              Boundary Value       Test
//    1          Brightness             INT_MIN
//    2          Brightness             0
//    3          Brightness             1
//    4          Brightness             50
//    5          Brightness             51
//    6          Brightness             100
//    7          Brightness             101
//    8          Brightness             INT_MAX
//    9*         offCurrentFactor       DOUBLE_MIN
//    10*        offCurrentFactor      -0.00001
//    11         offCurrentFactor       0.0
//    12         offCurrentFactor       0.09999
//    13         offCurrentFactor       0.1
//    14         offCurrentFactor       0.2
//    15         offCurrentFactor       0.20001
//    16         offCurrentFactor       1.0
//    17*        offCurrentFactor       1.00001
//    18*        offCurrentFactor       DOUBLE_MAX
//    19         leds                   TRUE
//    20         leds                   FALSE
//    
//    Based on this boundary values the following Test Data can be defined:
//
//    ID         Test Cases Covered        Brightness      offCurrentFactor    leds		ExpectedOutput
//    7           1,12,19                  INT_MIN              0.09999        TRUE		Expected less than or equal to 1000000
//    8           2,13,20                    0                  0.1            FALSE            Expected between 10000 and 20000
//    9           3,11,20                    1                  0.0            FALSE            Expected less than 10000
//    10          4,15,19                    50                 0.20001        TRUE             Expected less than or equal to 1000000
//    11          5,16,20                    51                 1.0            FALSE            Expected less than or equal to 1000000
//    12          6,14,19                    100                0.2            TRUE             Expected less than or equal to 1000000
//    13          7,13,20                    101                0.1            FALSE            Expected between 10000 and 20000
//    14          8,16,19                   INT_MAX             1.0            TRUE             Expected less than or equal to 1000000
//    15          9*                         50              DOUBLE_MIN        FALSE			Expected equal to -1
//    16          10*                        100               -0.0001         TRUE				Expected equal to -1							
//    17          17*                        0                  1.0001         FALSE            Expected equal to -1
//    18          18*                       INT_MIN          DOUBLE_MAX        FALSE            Expected equal to -1
//
//
//
//    3.Combinatoral testing using the Truth Tables
//
//  Causes                           Rule1    Rule2   Rule3   Rule4   Rule5   Rule6   Rule7   Rule8    Rule9   Rule10
//  1.Brightness<1                     *        *       T       T       T       T       T       T        F       F
//  2. 1<=Brightness=<50               *        *       F       F       F       F       F       F        T       T
//  3. 51<=Brightness=<100             *        *       F       F       F       F       F       F        F       F
//  4. Brightness>100                  *        *       F       F       F       F       F       F        F       F
//  5. offCurrentFactor<0.0            T        F       F       F       F       F       F       F        F       F
//  6. 0.0<=offCurrentFactor<0.1       *        *       T       T       F       F       F       F        T       T
//  7. 0.1<=offCurrentFactor=<0.2      *        *       F       F       T       T       F       F        F       F
//  8. 0.2<offCurrentFactor=<1         *        *       F       F       F       F       T       T        F       F
//  9. offCurrentFactor>1              F        T       F       F       F       F       F       F        F       F
//  10. leds=true                      *        *       T       F       T       F       T       F        T       F
//
//  Effects 
//  estimate = -1                      T        T       F       F       F       F       F       F        F       F
//  estimate = 0..9999                 F        F       T       F       F       F       T       F        T       T
//  estimate = 10000.20000             F        F       F       T       T       F       F       F        F       F
//  estimate = 20001...1000000         F        F       F       F       F       T       F       T        F       F
// 
//
//
//  Causes                                 Rule11  Rule12  Rule13  Rule14  Rule15  Rule16   Rule17  Rule18
//  1.Brightness<1                           F       F       F       F       F       F        F       F
//  2. 1<=Brightness=<50                     T       F       F       F       F       F        F       F
//  3. 51<=Brightness=<100                   F       T       T       T       T       F        F       F
//  4. Brightness>100                        F       F       F       F       F       T        T       T
//  5. offCurrentFactor<0.0                  F       F       F       F       F       F        F       F
//  6. 0.0<=offCurrentFactor<0.1             F       T       F       F       F       T        T       F
//  7. 0.1<=offCurrentFactor=<0.2            T       T       F       F       T       F        F       T
//  8. 0.2<offCurrentFactor=<1               F       F       T       T       F       F        F       F
//  9. offCurrentFactor>1                    F       F       F       F       F       F        T       F
//  10. leds=true                            T       F       T       F       T       F        T       F
//
//  Effects 
//  estimate = -1                            F       F       F       F       F       F        F       F
//  estimate = 1..9999                       F       F       T       F       F       T        F       F
//  estimate = 10000...20000                 T       F       F       F       F       F        T       F
//  estimate = 20001...1000000               F       T       F       T       T       F        F       T
//
//   Causes                                 Rule19  Rule20  Rule21  Rule22  Rule23  Rule24   Rule25  Rule26
//  1.Brightness<1                           F       F       F       F       F       F        F       F
//  2. 1<=Brightness=<50                     F       F       F       F       F       F        F       F
//  3. 51<=Brightness=<100                   F       F       F       F       F       T        F       T
//  4. Brightness>100                        T       T       T       T       F       F        F       F
//  5. offCurrentFactor<0.0                  F       F       F       F       T       T        F       F
//  6. 0.0<=offCurrentFactor<0.1             F       F       F       F       F       F        F       F
//  7. 0.1<=offCurrentFactor=<0.2            T       F       F       F       T       T        F       F
//  8. 0.2<offCurrentFactor=<1               F       T       T       T       F       F        T       T
//  9. offCurrentFactor>1                    F       F       F       F       F       F        F       F
//  10. leds=true                            T       F       T       F       T       F        T       F
//
//  Effects 
//  estimate = -1                            F       F       F       F       F       F        F       F
//  estimate = 1..9999                       F       F       T       F       F       F        T       F
//  estimate = 10000...20000                 F       T       F       F       T       F        F       F
//  estimate = 20001...1000000               T       F       F       T       F       T        F       T
// 
//
//   Since combinatorial testing gives us the list of all possible combinations in this case there are 4(brightness)*3(offCurrentFactor)*2(led) + 2 special occasions(offCurrent<0 or >1.0)=26 Cases in total
//   In order to not repeat any test case we will refer from the past test for those cases(rules) which can already be covered from the tests made
//   
//   So test cases are :
//   
//   Case(Rule)           Test
//   1                     5
//   2                     6
//   3                     19
//   4                     7
//   5                     8
//   6                     20
//   7                     21
//   8                     2
//   9                     9
//   10                    22
//   11                    1
//   12                    23
//   13                    24
//   14                    10
//   15                    25
//   16                    3
//   17                    26
//   18                    12
//   19                    11
//   20                    27
//   21                    28
//   22                    29
//   23                    13
//   24                    30
//   25                    4
//   26                    14
//
//
//  The tests which were not already defined are from 15-26. In the case where e.g only the leds parameter was to be changed the other parameters have remained the same(just to increase efficiency)
//
//  Test         Test case covered       Brightness     offCurrentValue    leds			ExpectedOutput
//   19                3                  INT_MIN          0.09999         FALSE		Expected less than or equal to 10000		
//   20                6                     0              0.1            TRUE			Expected less than or equal to 1000000
//   21                7                    -3              0.5            FALSE		Expected less than or equal to 10000
//   22                10                    1              0.0            TRUE         Expected less than or equal to 1000000
//   23                12                    6              0.1            TRUE			Expected less than or equal to 1000000
//   24                13                    50            0.20001         FALSE		Expected less than or equal to 10000
//   25                15                    97             0.05           TRUE		    Expected less than or equal to 1000000
//   26                17                   100             0.2            FALSE        Expected between 10000 and 20000
//   27                20                    51             1.0            TRUE         Expected less than or equal to 1000000
//   28                21                   120             0.02           FALSE        Expected less than or equal to 10000
//   29                22                   120             0.02           TRUE			Expected less than or equal to 1000000
//   30                24                   101             0.1            TRUE         Expected less than or equal to 1000000
//
//   
//
//
//  WHITEBOX TESTING
//  
//  In order to perform the first 2 methods of whitebox we must firstly draw the CFG. In my case the CFG contains 27 nodes.
//
//  In the case of statement coverage it is enough that all these nodes to be covered at least once by the test cases.
//  Each node is a different test case. So there are 27 test cases.
//  Just looking up at the code the only conditions which are added and for which maybe additional test cases will be needed is only in the case
//  of Brightness parameter with value between 90 and 95, 50 and 90 and greater than 95.Also some test case will be added if it is not already defined.
//
//             Nodes(cases) Covered                   Test 
//         1,2,3,5,6,7,8,9,11,13,15,16,6,27            31
//         1,3,4,5,26,27                               5
//         1,3,5,6,7,16,6,7,17,18,19,20,21,22,6,27     32
//         1,3,5,6,7,8,9,10,15,16,6,27                 25
//         1,3,5,6,7,8,9,11,12,15,16,6,27              33
//         1,3,5,6,7,8,9,11,13,14,15,16,6,27           27
//
//   There were added 3 tests 31,32,33
//   Test ID            Brightness    offCurrentFactor     leds 		ExpectedOutput
//      31                -5              0.2              TRUE			Expected less than or equal to 1000000			
//      32                97              0.15             FALSE		Expected between 10000 and 20000
//      33                93              0.05             TRUE			Expected less than or equal to 1000000
//
//   
//    As it can be seen 3 nodes are not executed since the condition if(loop=2)
//    will never be reached , since in both cases for leds=true or false the value
//    of boolean variable done will be set to TRUE.
//
//
//
//   the additional branch is between 19 and 21 which will be like the case 3 , with the difference
//   0.15 for the offcurrent factor it will be changed to 0.3 e.g (not between 0.1 and 0.2)
//
//
//     Test cases covered                               Test
//   1.B1,B2,B5,B7,B8,B9,B10,B12,B13,B19,B21,B36         31
//   2.B3,B4,B4,B37,B38                                  5
//   3.B3,B5,B7,B8,B20,B21,B22,B23,B25,B28,B30,B31,B36   32
//   4.B3,B5,B7,B8,B9,B10,B11,B16,B19,B21,B36            25
//   5.B3,B5,B7,B8,B9,B10,B12,B14,B17,B19,B21,B36        33
//   6.B3,B5,B7,B8,B9,B10,B12,B13,B15,B18,B19,B21,B36    27
//   7.3.B3,B5,B7,B8,B20,B21,B22,B23,B27,B30,B31,B36     34
//
//   Test ID              Brightness  offCurrentFactor   leds		ExpectedOutput
//    34                    97           0.3             FALSE		Expected less than or equal to 10000
//
//
//
//   
//    Since with the test case all the possible conditions(since is not possible to have a 100 % code coverage)
//    there will be given one example for each method like condition coverage, decision condition coverage, multiple condition coverage and du pair
//    
//    Condition coverage:
//    The following if statement will be taken into account
//    if ((offCurrentFactor>=0.0)&&(offCurrentFactor<=1.0))
//
//    Case                 Condition                     Test
//    1                    offCurrentFactor>=0            1
//    2                   !(offCurrentFactor>=0)          5
//    3                    offCurrentFactor<=1.0          1
//    4                   !(offCurrentFactor<=1.0)        6
//
//
//
//    Decision coverage:
//    For the same if statement we can perform the decision coverage:
//    Case                  Condition                               Test
//    1          (offCurrentFactor>=0.0)&&(offCurrentFactor<=1.0)    1
//    2         !((offCurrentFactor>=0.0)&&(offCurrentFactor<=1.0))  5    
//    3                     offCurrentFactor>=0                      1
//    4                    !(offCurrentFactor>=0)                    5
//    5                     offCurrentFactor<=1.0                    1
//    6                    !(offCurrentFactor<=1.0)                  6
//
//
//
//   Multiple condition coverage
//   
//   One test will be used to show the multiple condition coverage:
//   
//   Test  32 (97,0.15,False) 
//
//    Case 1                         Value
//   Brightness <1                   False
//   Brightness >100                 False
//   offCurrentFactor>0.0            True
//   offCurrentFactor<1.0            True
//   !done                           True
//   loop==0                         False
//   leds                            False
//   Brightness>95                   Not tested
//   Brightness>90                   Not tested
//   Brightness>50                   Non tested
//   loop==1                         True
//   Brightness>0                    True
//   offCurrentFactor>=0.1           True
//   offCurrentFactor<=0.2           True
//   loop==2                         Non-testable
//  
//
//   Path coverage:
//   Path is from entry point to the exit point:
//   
//   Entry point is node 1 and the exit point is the node 27
//   During the branch coverage all the possible paths were already tested, so we take 1 example of that:
//   
//   Case            Nodes covered                        Test
//   1         1,3,5,6,7,8,9,11,12,15,16,6,27              33 
//  

public class LightsTest extends unitTestingRunner {

	@MyTest
	public static boolean test_001() {

		String test = "test_001";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 6, 0.1);

		if ((result >= 10000) && (result <= 20000)) {
			return true;
		} else {
			reason = new String(
					"Expected between 10000 and 20000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_002() {

		String test = "test_002";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, -3, 0.5);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_003() {
		String test = "test_003";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 97, 0.05);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_004() {
		String test = "test_004";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 200, 0.4);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_005() {
		String test = "test_005";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 150, -0.5);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_006() {
		String test = "test_006";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 56, 2.4);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_007() {
		String test = "test_007";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 1, 0.0);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_008() {
		String test = "test_008";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 0, 0.1);

		if ((result >= 10000) && (result <= 20000)) {
			return true;
		} else {
			reason = new String(
					"Expected between 10000 and 20000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_009() {
		String test = "test_009";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 1, 0.0);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_010() {
		String test = "test_010";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 50, 0.20001);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_011() {
		String test = "test_011";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 51, 1.0);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_012() {
		String test = "test_012";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 100, 0.2);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_013() {
		String test = "test_013";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 101, 0.1);

		if ((result >= 10000) && (result <= 20000)) {
			return true;
		} else {
			reason = new String(
					"Expected between 10000 and 20000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_014() {
		String test = "test_014";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, Integer.MAX_VALUE, 1.0);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}

	}

	@MyTest
	public static boolean test_015() {
		String test = "test_015";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 50, Double.MIN_VALUE);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_016() {
		String test = "test_016";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 100, -0.0001);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_017() {
		String test = "test_017";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 0, 1.0001);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_018() {
		String test = "test_018";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l
				.estimatedLifetime(false, Integer.MIN_VALUE, Double.MAX_VALUE);

		if ((result == -1)) {
			return true;
		} else {
			reason = new String("Expected equal to -1, actual result :"
					+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_019() {
		String test = "test_019";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, Integer.MIN_VALUE, 0.09999);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_020() {
		String test = "test_020";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 0, 0.1);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_021() {
		String test = "test_021";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, -3, 0.5);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_022() {
		String test = "test_022";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 1, 0.0);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_023() {
		String test = "test_023";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 6, 0.1);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_024() {
		String test = "test_024";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 50, 0.20001);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_025() {
		String test = "test_025";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 97, 0.05);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result:"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_026() {
		String test = "test_026";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 100, 0.2);

		if ((result >= 10000) && (result <= 20000)) {
			return true;
		} else {
			reason = new String(
					"Expected between 10000 and 20000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_027() {
		String test = "test_027";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 51, 1.0);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_028() {
		String test = "test_028";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 120, 0.02);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_029() {
		String test = "test_029";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 120, 0.02);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_030() {
		String test = "test_030";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 101, 0.1);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_031() {
		String test = "test_031";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, -5, 0.2);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_032() {
		String test = "test_032";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 97, 0.15);

		if ((result >= 10000) && (result <= 20000)) {
			return true;
		} else {
			reason = new String(
					"Expected between 10000 and 20000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_033() {
		String test = "test_033";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(true, 93, 0.05);

		if ((result >= 20001 && result <= 1000000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than 20001 and less than or equal to 1000000, actual result :"
							+ result);
			return false;
		}
	}

	@MyTest
	public static boolean test_034() {
		String test = "test_034";

		Lights l = new Lights(test, Color.RED);
		int result;
		result = l.estimatedLifetime(false, 97, 0.3);

		if ((result >= 1 && result < 10000)) {
			return true;
		} else {
			reason = new String(
					"Expected greater than or equal to 1 and less than 10000, actual result :"
							+ result);
			return false;
		}
	}

}
