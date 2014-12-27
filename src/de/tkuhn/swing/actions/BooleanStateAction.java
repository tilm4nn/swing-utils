/*
 * BooleanStateAction.java
 *
 * Created on 6. Juli 2003, 18:34
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

import java.awt.event.ActionEvent;

/**
 * A simple <code>DelegatingAction</code> JavaBean that implements the
 * <code>BooleanAction</code> interface by holding the state in the boolean
 * property selected as an attribute. It also implements the
 * <code>ForwardingAction</code> interface.
 * 
 * @see de.tkuhn.swing.actions.BooleanAction
 * @see de.tkuhn.swing.actions.ForwardingAction
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class BooleanStateAction extends DelegatingAction implements
		BooleanAction {

	private static final long serialVersionUID = 1L;

	private boolean selected = true;

	/** Creates a new instance of BooleanStateAction */
	public BooleanStateAction() {
	}

	/**
	 * Creates a new instance of BooleanStateAction with given initial value.
	 * 
	 * @param selected
	 *            initial value for the selected property.
	 */
	public BooleanStateAction(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Implementation of <code>actionPerformed()</code> in
	 * <code>ActionListener</code>.
	 * 
	 * @param actionEvent
	 *            the <code>ActionEvent</code> to be processed.
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		setSelected(!isSelected());
		super.actionPerformed(actionEvent);
	}

	/**
	 * Getter for property selected.
	 * 
	 * @return Value of property selected.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Setter for property selected.
	 * 
	 * @param selected
	 *            New value of property selected.
	 */
	public void setSelected(boolean selected) {
		boolean old = this.selected;
		this.selected = selected;
		firePropertyChange("selected", new Boolean(old), new Boolean(selected));
	}

}
