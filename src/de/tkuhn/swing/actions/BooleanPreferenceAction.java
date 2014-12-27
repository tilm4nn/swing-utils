/*
 * BooleanPreferenceAction.java
 *
 * Created on 6. Juli 2003, 22:58
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
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * <code>BooleanPreferenceAction</code> is a <code>BooleanAction</code> that is
 * linked together with a boolean <code>Preferences</code> mapping. The state of
 * the action is the reflected of the preference mapping. It also implements the
 * <code>ForwardingAction</code> interface.
 * 
 * @see de.tkuhn.swing.actions.BooleanAction
 * @see de.tkuhn.swing.actions.ForwardingAction
 * @see java.util.prefs.Preferences
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class BooleanPreferenceAction extends DelegatingAction implements
		BooleanAction, PreferenceChangeListener {

	private static final long serialVersionUID = 1L;

	private Preferences prefs;
	private String key;
	private boolean defaultReturn;
	private boolean oldValue;

	/**
	 * Creates a new instance of BooleanPreferenceAction
	 * 
	 * @param prefs
	 *            the <code>Preferences</code> holding the boolean mapping
	 * @param key
	 *            the key of the mapping
	 * @param defaultReturn
	 *            the boolean value that should returned if the backing store of
	 *            the <code>Preferences</code> is not reachable.
	 */
	public BooleanPreferenceAction(Preferences prefs, String key,
			boolean defaultReturn) {
		this.prefs = prefs;
		this.key = key;
		this.defaultReturn = defaultReturn;
		prefs.addPreferenceChangeListener(this);
		oldValue = isSelected();
	}

	/**
	 * Implementation of <code>preferenceChange()</code> in
	 * <code>PreferenceChangeListener</code>.
	 * 
	 * @param pce
	 *            the <code>PreferenceChangeEvent</code> to be processed.
	 */
	public void preferenceChange(PreferenceChangeEvent pce) {
		if (key.equals(pce.getKey())) {
			boolean newV = isSelected();
			boolean oldV = oldValue;
			oldValue = newV;
			firePropertyChange("selected", new Boolean(oldV), new Boolean(newV));
		}
	}

	/**
	 * If this is not called this object may not be garbage collected since it
	 * is registered as a <code>PreferenceChangeListener</code>.
	 */
	public void destroy() {
		prefs.removePreferenceChangeListener(this);
	}

	/**
	 * Getter for property selected.
	 * 
	 * @return Value of property selected.
	 */
	public boolean isSelected() {
		return prefs.getBoolean(key, defaultReturn);
	}

	/**
	 * Setter for property selected.
	 * 
	 * @param selected
	 *            New value of property selected.
	 */
	public void setSelected(boolean selected) {
		prefs.putBoolean(key, selected);
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

}
