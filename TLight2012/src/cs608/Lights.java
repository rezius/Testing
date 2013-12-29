package cs608;

/**
 * Class to represent the current colour of a set of traffic lights
 * @Author sbrown
 * @Version 2.0
 */
public class Lights extends Part {

	/**
	 * Estimate the lifetime of a light.
	 * <ul>
	 * <li>LEDs have a base lifetime of 1,000,000 cycles
	 * <ul>
	 * <li>For LEDs, if the brightness>50, the lifetime is reduced by 100*brightness
	 * </ul>
	 * <li>Incendescents have a base lifetime of 10,000 cycles
	 * <ul>
	 * <li>For Incendescents, the lifetime is reduced by 10*brightness
	 * <li>For Incendescents, the lifetime is doubled if the off current is between 0.1 and 0.2 (inclusive)
	 * </ul>
	 * </ul>
	 *
	 * @param   leds              true if led bulb
	 * @param   brightness        the brightness factor (1..100) - less than 1 is treated as 1, and greater than 100 is treated as 100
	 * @param  offCurrentFactor  the level of current used when the bulb is off (valid values from 0.0 to 1.0)
	 * @return The estimated lifetime (in cycles)
	 * <br>Or -1 on any input errors
	 */
	public static int estimatedLifetime(boolean leds, int brightness, double offCurrentFactor)
	{
		int baseLife=10000; // base lifetime
		int estimate=-1;
		boolean done=false;
		int loop=0;

		if (brightness<1) brightness=1;
		if (brightness>100) brightness=100;

		// check for valid offCurrentFactor first
		if ((offCurrentFactor>=0.0)&&(offCurrentFactor<=1.0)) {

			while (!done) {

				// loop 0 - led bulb processing
				if (loop==0) {
					loop=1;
					if (leds) {
						estimate = baseLife*100;
						if (brightness>95)
							estimate = estimate - (brightness*100);
						else if (brightness>90)
							estimate = estimate - (brightness*100);
						else if (brightness>50)
							estimate = estimate - (brightness*100);
						done = true;
					}
				}

				// loop 1 - incandescent bulb processing
				else if (loop==1) {
					loop = 2;
					if (!leds) {
						estimate = baseLife;
						estimate = estimate - (10*brightness);
						if ((brightness>0)&&(offCurrentFactor>=0.1)&&(offCurrentFactor<=0.2))
							estimate = 2*estimate;
						done = true;
					}
				}

				// loop 3 - flourscent bulb processing
				else if (loop==2) {
					done = true;
					estimate = 10 * baseLife;
				}

			}

		}
		else {
			estimate = -1;
		}


		return estimate;
	}


  /**
   * Available lights colours
   */
   public enum Color { RED, GREEN, AMBER };

   // current lights colour
   private Color c;

  /**
   * Creates a Lights object, with the color set to the initial setting
   *
   * @param initialSetting initial lights colour
   */
   public Lights(String lightId, Color initialSetting)
   {
	   super(lightId);
	   c = initialSetting;
   }

  /**
   * Set the lights to the required colour
   *
   * @param required the required lights colour
   */
   public void set(Color required)
   {
      c = required;
   }

  /**
   * Get the current lights colour
   *
   * @return the current lights colour
   */
   public Color get()
   {
      return c;
   }

}