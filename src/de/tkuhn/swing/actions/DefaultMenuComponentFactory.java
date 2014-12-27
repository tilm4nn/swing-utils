/*
 * DefaultMenuComponentFactory.java
 *
 * Created on 6. Juli 2003, 23:19
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

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

/**
 * Implementation of <code>ActionComponentFactory</code> that creates components
 * that are <code>MenuItems</code> suitable for menus.
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class DefaultMenuComponentFactory implements ActionComponentFactory {

	/** Creates a new instance of DefaultJToolBarComponentFactory */
	public DefaultMenuComponentFactory() {
	}

	/**
	 * Create a new <code>JComponent</code> that will serve as a display for the
	 * <code>Action</code>.
	 * 
	 * @param action
	 *            the action you need a component for
	 * @return the JComponent that displays the action
	 */
	public JComponent createActionComponent(Action action) {
		if (action instanceof BooleanAction) {
			BooleanAction bolAct = (BooleanAction) action;
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(bolAct);
			new BooleanActionCheckBoxMenuItemMediator(bolAct, item);
			return item;
		} else {
			return new JMenuItem(action);
		}

	}

}
