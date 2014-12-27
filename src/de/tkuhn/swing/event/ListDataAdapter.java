/*
 * ListDataAdapter.java
 *
 * Created on 3. August 2003, 20:31
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

package de.tkuhn.swing.event;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This Class provides a default implementation of <code>ListDataListener</code>
 * with empty implementation methods.
 * 
 * @see javax.swing.event.ListDataListener
 * 
 * @version 0.9.3
 * @author Tilmann Kuhn
 */
public class ListDataAdapter implements ListDataListener {

	/** Creates a new instance of ListDataAdapter */
	public ListDataAdapter() {
	}

	/**
	 * Sent when the contents of the list has changed in a way that's too
	 * complex to characterize with the previous methods. For example, this is
	 * sent when an item has been replaced. Index0 and index1 bracket the
	 * change.
	 * 
	 * @param e
	 *            a <code>ListDataEvent</code> encapsulating the event
	 *            information
	 */
	public void contentsChanged(ListDataEvent e) {
	}

	/**
	 * Sent after the indices in the index0,index1 interval have been inserted
	 * in the data model. The new interval includes both index0 and index1.
	 * 
	 * @param e
	 *            a <code>ListDataEvent</code> encapsulating the event
	 *            information
	 */
	public void intervalAdded(ListDataEvent e) {
	}

	/**
	 * Sent after the indices in the index0,index1 interval have been removed
	 * from the data model. The interval includes both index0 and index1.
	 * 
	 * @param e
	 *            a <code>ListDataEvent</code> encapsulating the event
	 *            information
	 */
	public void intervalRemoved(ListDataEvent e) {
	}

}
