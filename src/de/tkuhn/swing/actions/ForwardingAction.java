/*
 * ForwardingAction.java
 *
 * Created on 6. Juli 2003, 22:44
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

package de.tkuhn.swing.actions;

import java.awt.event.ActionListener;

import javax.swing.Action;

/**
 * A <code>ForwardingAction</code> is a special <code>Action</code> that
 * forwards all incoming <code>ActionEvents</code> to listeners that can be
 * registered beyond mere processing of the events.
 * 
 * @see javax.swing.Action
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public interface ForwardingAction extends Action {

	/**
	 * Register a listener for events that arrive at the action.
	 * 
	 * @param listener
	 *            the <code>ActionListener</code>
	 */
	public void addActionListener(ActionListener listener);

	/**
	 * Remove a listener from this Action's listener list.
	 * 
	 * @param listener
	 *            the <code>ActionListener</code> to be removed.
	 */
	public void removeActionListener(ActionListener listener);

}
