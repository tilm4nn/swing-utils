/*
 * BooleanActionAbstractButtonMediator.java
 *
 * Created on 6. Juli 2003, 23:46
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;

/**
 * This Mediator can be used to propagate state changes of a
 * <code>BooleanAction</code> to an <code>AbstractButton</code>.
 * 
 * @see de.tkuhn.swing.actions.BooleanAction
 * @see javax.swing.AbstractButton
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
class BooleanActionAbstractButtonMediator implements PropertyChangeListener {

	private AbstractButton button;

	/**
	 * Creates a new instance of BooleanActionAbstractButtonMediator.
	 * 
	 * @param action
	 *            the <code>BooleanAction</code> to be connected
	 * @param button
	 *            the <code>AbstractButten</code> to be connected
	 */
	public BooleanActionAbstractButtonMediator(BooleanAction action,
			AbstractButton button) {
		this.button = button;
		button.setSelected(action.isSelected());
		action.addPropertyChangeListener(this);
	}

	/**
	 * Implementation of <code>propertyChange()</code> in
	 * <code>PropertyChangeListener</code>
	 * 
	 * @param pce
	 *            the PropertyChangeEvent to be processed
	 */
	public void propertyChange(PropertyChangeEvent pce) {
		if ("selected".equals(pce.getPropertyName()))
			button.setSelected(((Boolean) pce.getNewValue()).booleanValue());
	}

}
