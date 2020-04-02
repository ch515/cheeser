/**
 * Copyright (c) 2020 Caleb Heydon
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch515.cheeser;

import java.util.concurrent.locks.LockSupport;

/**
 * This is a simple helper class to help with timing
 * 
 * @author Caleb Heydon
 */
public class Timer {
	/**
	 * Returns the current timestamp with nanosecond precision
	 * 
	 * @return
	 */
	public static double getTimestamp() {
		return System.nanoTime() / 1000000000.0;
	}
	
	/**
	 * Has the current thread sleep for a certain number of seconds
	 * 
	 * @param seconds
	 */
	public static void sleep(double seconds) {
		if (seconds > 0) {
			LockSupport.parkNanos((long) (seconds * 1000000000));
		}
	}

	private boolean running;
	private double startTime;

	/**
	 * Resets the timer
	 */
	public void reset() {
		running = false;
		startTime = 0;
	}

	/**
	 * Starts counting
	 */
	public void start() {
		startTime = getTimestamp();
		running = true;
	}

	/**
	 * Gets the time the timer has been counting
	 * 
	 * @return
	 */
	public double get() {
		return getTimestamp() - startTime;
	}

	/**
	 * Returns true if the timer is running
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	public Timer() {
		reset();
	}
}
