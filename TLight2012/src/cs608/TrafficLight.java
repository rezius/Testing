package cs608;

/**
 * Class to provide control of a set of traffic lights at an intersection. The two crossing roads are
 * treated as running north-south, and west-east.
 * @Author sbrown
 * @Version 2.0
 */
public class TrafficLight extends Part {

	/**
	 * Number of cycles until maintenance is needed
	 */
	public static final int MAINTENANCE_CYCLES=100000;
	/**
	 * Index for North-South Lights
	 */
	public static final int NS_LIGHTS=0;
	/**
	 * Index for East-West Lights
	 */
	public static final int EW_LIGHTS=1;

	// Types
	private enum States {
		INVALID, ALL_OFF,
		NORTH_SOUTH_GO, NORTH_SOUTH_WARN, NORTH_SOUTH_STOPPED,
		EAST_WEST_GO, EAST_WEST_WARN, EAST_WEST_STOPPED
	};

	private States state;        // Current state
	private boolean maintenance; // Maintenance needed flag
	private int cycles;          // Number of cycles since last maintenance (of traffic light hardware)
	private Lights[] l;          // Array of lights

	/**
	 * Creates a TrafficLight object, with all the lights off, maintenance flag not set, and cycle counter zeroed
	 *
	 * @param name   unique name for this traffic light controller object
	 */
	public TrafficLight(String name)
	{
		super(name);
		maintenance = false;
		cycles = 0;
		state = States.ALL_OFF;
		l = new Lights[2];
		l[NS_LIGHTS]=new Lights(name+"-NS",Lights.Color.RED);
		l[EW_LIGHTS]=new Lights(name+"-EW",Lights.Color.RED);
	}

	/**
	 * 
	 * @return An array showing the current state of the lights
	 */
	public Lights[] getLights() {
		return l;
	}

	/**
	 * Cycle the lights.
	 * <br>The lights on each road cycle between Green -> Amber -> Red -> Green etc
	 * <br>Note Amber represents a warning
	 * <br>When the lights on one road are green, those on the other road must be red
	 * <br>When one road is amber, the other road must be red
	 * <br>When one road is red, the other road may be red, green or amber
	 * <br>After initialisation, the first cycle should set north-south to green, and east-west to red
	 * <br>After cycle has been called MAINTENANCE_CYCLES times, the maintenance flag should be set and kept set until cleared
	 *
	 * @return true if the cycle operated correctly
	 * <br>false if there is an internal error: after an internal error, the object is invalid and all further return values and methods have undefined operation.
	 */
	public boolean cycle()
	{
		boolean ok=true;

		if (state != States.INVALID) {
			if (cycles<MAINTENANCE_CYCLES)
				cycles++;
			if (cycles==MAINTENANCE_CYCLES) {
				maintenance=true;
			}
			if (state==States.ALL_OFF) {
				state = States.NORTH_SOUTH_GO;
				l[NS_LIGHTS].set(Lights.Color.GREEN);
			}
			else if (state==States.NORTH_SOUTH_GO) {
				state = States.NORTH_SOUTH_WARN;
				l[NS_LIGHTS].set(Lights.Color.AMBER);
			}
			else if (state==States.NORTH_SOUTH_WARN) {
				l[NS_LIGHTS].set(Lights.Color.RED);
				state = States.NORTH_SOUTH_STOPPED;
			}
			else if (state==States.NORTH_SOUTH_STOPPED) {
				l[EW_LIGHTS].set(Lights.Color.GREEN);
				state = States.EAST_WEST_GO;
			}
			else if (state==States.EAST_WEST_GO) {
				state = States.EAST_WEST_WARN;
				l[EW_LIGHTS].set(Lights.Color.AMBER);
			}
			else if (state==States.EAST_WEST_WARN) {
				l[EW_LIGHTS].set(Lights.Color.RED);
				state = States.EAST_WEST_STOPPED;
			}
			else if (state==States.EAST_WEST_STOPPED) {
				l[NS_LIGHTS].set(Lights.Color.GREEN);
				state = States.NORTH_SOUTH_GO;
			}
		}
		else {
			ok = false;
		}

		return ok;

	}

	/**
	 * Find the number of cycles since maintenance was last done.
	 *
	 * @return       the number of cycles
	 */
	public int getCycles()
	{
		return cycles;
	}

	/**
	 * Find out whether maintenance is needed.
	 *
	 * @return       a flag that indicates whether maintenance is due.
	 */
	public boolean maintenanceNeeded()
	{
		return maintenance;
	}

	/**
	 * Clear the maintenance flag (after maintenance has been performed on the hardware), and reset the maintenance cycle counter.
	 * <p>Truth-table:
	 * <table border="1">
	 *    <tr><td colspan="2" align="left"><b>Rules</b></td><td>1</td><td>2</td><td>3</td><td>4</td></tr>
	 *    <tr><td rowspan="2"><b>Causes</b></td>
	 *        <td align="right">  maintenance       </td><td>T</td><td>T</td><td>F</td><td>F</td></tr>
	 *    <tr><td align="right">  force             </td><td>T</td><td>F</td><td>T</td><td>F</td></tr>
	 *    <tr><td rowspan="3"><b>Effects</b></td>
	 *        <td align="right">  maintenance       </td><td>T</td><td>F</td><td>F</td><td>F</td></tr>
	 *    <tr><td align="right">  return value      </td><td>F</td><td>T</td><td>T</td><td>F</td></tr>
	 *    <tr><td align="right">  cycle counter==0  </td><td>F</td><td>T</td><td>T</td><td>*</td></tr>
	 * </table>
	 *
	 * @param force A flag that indicates whether the maintenance flag should be cleared if not already set.
	 *
	 * @return       A flag that indicates whether the operation succeeded.
	 */
	public boolean clearMaintenanceFlag(boolean force)
	{
		boolean status=false;

		if ((maintenance && (!force))||((!maintenance)&&force)) {
			cycles = 0;
			status = true;
		}
		else {
			status = false;
		}

		maintenance = false;

		return status;

	}

}