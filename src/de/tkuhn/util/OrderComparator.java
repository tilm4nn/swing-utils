/*
 * OrderComparator.java
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
 * This class defines a <code>Comparator</code> that is a wrapper for other
 * <code>Comparator</code>s an can change the order of their comparisons.
 * 
 * @see java.util.Comparator
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class OrderComparator<T> implements Comparator<T> {

	/** Ascending compare order */
	public static final boolean ASCENDING = true;
	/** Descending compare order */
	public static final boolean DESCENDING = false;

	/** Used to delegate comparisons */
	private Comparator<T> comparator = null;

	/** Order to which comparisons are transformed */
	private boolean order = ASCENDING;

	/**
	 * Creates a new instance of OrderComparator using ascending order
	 */
	public OrderComparator() {
	}

	/**
	 * Creates a new instance of OrderComparator using the given order.
	 * 
	 * @param order
	 *            transform comparisons to this order
	 */
	public OrderComparator(boolean order) {
		setOrder(order);
	}

	/**
	 * Creates a new instance of OrderComparator using ascending order,
	 * delegating to the given Comparator.
	 * 
	 * @param comparator
	 *            Comparator to delegate comparisons to
	 */
	public OrderComparator(Comparator<T> comparator) {
		setComparator(comparator);
	}

	/**
	 * Creates a new instance of OrderComparator using the given order,
	 * delegating to the given Comparator.
	 * 
	 * @param comparator
	 *            Comparator to delegate comparisons to
	 * @param order
	 *            transform comparisons to this order
	 */
	public OrderComparator(Comparator<T> comparator, boolean order) {
		setComparator(comparator);
		setOrder(order);
	}

	/**
	 * Get the Comparator that is wrapped by this OrderComparator.
	 * 
	 * @return Value of property comparator.
	 */
	public Comparator<T> getComparator() {
		return comparator;
	}

	/**
	 * Set the Comparator that will be wrapped by this OrderComparator.
	 * 
	 * @param comparator
	 *            New value of property comparator.
	 */
	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
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
	 * @throws IllegalStateException
	 *             if no comparator for delegation is set.
	 */
	public int compare(T o1, T o2) throws IllegalStateException {
		if (comparator == null)
			throw new IllegalStateException("No comparator set!");
		int result = comparator.compare(o1, o2);
		if (order)
			return result;
		else
			return -result;
	}

	/**
	 * Get the order this wrapper uses to transform the comparisons to.
	 * 
	 * @return Value of property order.
	 */
	public boolean getOrder() {
		return order;
	}

	/**
	 * Set the order this wrapper will use to transform the comparisons to.
	 * 
	 * @param order
	 *            New value of property order.
	 */
	public void setOrder(boolean order) {
		this.order = order;
	}

}
