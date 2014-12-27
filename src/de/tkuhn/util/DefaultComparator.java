/*
 * DefaultComparator.java
 *
 * Created on 30. Juli 2003, 14:47
 *
 * The MIT License
 *
 * Copyright (C) 2003 
 * Tilmann Kuhn           Gildestr. 34
 * http://www.tkuhn.de    76149 Karlsruhe
 * swingutils@tkuhn.de    Germany
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.tkuhn.util;

import java.util.Comparator;

/**
 * This is a <code>Comparator</code> that compares any pair of
 * <code>Object</code>s.
 * 
 * First of all this <code>Comparator</code> defines <code>null</code> as less
 * than anything else.
 * 
 * Two <code>Boolean</code>s: false is less than true
 * 
 * Two <code>Number</code>s are compared using their double value.
 * 
 * Two <code>Comparable</code>s: If one object is of the same or an extending
 * class as the other the <code>compareTo(Object obj)</code> method of the
 * extending class is used to compare the two objects. E.g. if
 * <code>o1.getClass.isInstance(o2)</code> holds, than
 * <code>o2.compareTo()</code> will be used.
 * 
 * In all other cases simply the String values of the two objects will be
 * compared.
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class DefaultComparator implements Comparator<Object> {

	/** Creates a new instance of DefaultComparator */
	public DefaultComparator() {
	}

	/**
	 * Implementation of <code>compare(obj,obj)</code> in the interface
	 * <code>java.util.Comparator</code>.
	 * 
	 * @param o1
	 *            row one to be compared
	 * @param o2
	 *            row two to be compared
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second.
	 */
	public int compare(Object o1, Object o2) {
		// If both values are null, return 0.
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) { // Define null less than everything.
			return -1;
		} else if (o2 == null) {
			return 1;
		}

		// compare Comparables
		if ((o1 instanceof Comparable) && o1.getClass().isInstance(o2)) {
			return ((Comparable) o2).compareTo(o1);

		} else if ((o2 instanceof Comparable) && o2.getClass().isInstance(o1)) {
			return ((Comparable) o1).compareTo(o2);

		} else if (o1 instanceof Boolean && o2 instanceof Boolean) {
			boolean b1 = ((Boolean) o1).booleanValue();
			boolean b2 = ((Boolean) o2).booleanValue();

			if (b1 == b2) {
				return 0;
			} else if (b1) { // Define false < true
				return 1;
			} else {
				return -1;
			}

		} else if (o1 instanceof Number && o2 instanceof Number) {
			double d1 = ((Number) o1).doubleValue();
			double d2 = ((Number) o2).doubleValue();
			if (d1 < d2) {
				return -1;
			} else if (d1 > d2) {
				return 1;
			} else {
				return 0;
			}

			// just compare the string values
		} else {
			return o1.toString().compareTo(o2.toString());
		}
	}

}
