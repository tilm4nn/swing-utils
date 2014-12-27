/*
 * CascadingComboBoxModel.java
 *
 * Created on 20. Juli 2003, 17:37
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

package de.tkuhn.swing.combobox;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.tkuhn.swing.event.ListDataAdapter;

/**
 * The <code>CascadingComboBoxModel</code> is a means to provide a row of combo
 * boxes where the items selectable in one combobox depend on the selected item
 * in the previous combo box.
 * 
 * @version 0.9.3
 * @author Tilmann Kuhn
 */
public class CascadingComboBoxModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = 1L;

	/** The combo box model this model depends on */
	private ComboBoxModel superModel = null;
	/** The object that is selected in the super model */
	private Object superSelected = null;

	/** Used to catch events from the super model */
	private ListDataListener listDataListener = null;

	/** Creates a new CascadingComboBoxModel. */
	public CascadingComboBoxModel() {
		super();
	}

	/**
	 * Creates a new CascadingComboBoxModel with the given super model
	 * 
	 * @param superModel
	 *            the model this model depends on
	 */
	public CascadingComboBoxModel(ComboBoxModel superModel) {
		super();
		setSuperModel(superModel);
	}

	/**
	 * Creates a new CascadingComboBoxModel with contents based on the given
	 * object a it was selected in the super model. The supermodel itself must
	 * still be set!
	 * 
	 * @param superSelected
	 *            the virtually selected Object
	 */
	public CascadingComboBoxModel(Object superSelected) {
		super();
		computeNewContents(superSelected);
	}

	/**
	 * Constructs a CascadingComboBoxModel object initialized with an array of
	 * objects. The supermodel itself must still be set!
	 * 
	 * @param items
	 *            the items put into the combo box
	 */
	public CascadingComboBoxModel(Object[] items) {
		super(items);
	}

	/**
	 * Constructs a CascadingComboBoxModel object initialized with a vector. The
	 * supermodel itself must still be set!
	 * 
	 * @param v
	 *            a vector containing the items put into the combo box
	 */
	public CascadingComboBoxModel(Vector<?> v) {
		super(v);
	}

	/**
	 * Constructs a CascadingComboBoxModel object initialized with an array of
	 * objects and the given super model.
	 * 
	 * @param items
	 *            the items put into the combo box
	 * @param superModel
	 *            the model this model depends on
	 */
	public CascadingComboBoxModel(Object[] items, ComboBoxModel superModel) {
		super(items);
		setSuperModel(superModel);
	}

	/**
	 * Constructs a CascadingComboBoxModel object initialized with a vector and
	 * the given super model.
	 * 
	 * @param v
	 *            a vector containing the items put into the combo box
	 * @param superModel
	 *            the model this model depends on
	 */
	public CascadingComboBoxModel(Vector<?> v, ComboBoxModel superModel) {
		super(v);
		setSuperModel(superModel);
	}

	/**
	 * Getter for property superModel.
	 * 
	 * @return Value of property superModel.
	 */
	public ComboBoxModel getSuperModel() {
		return superModel;
	}

	/**
	 * Setter for property superModel.
	 * 
	 * @param superModel
	 *            New value of property superModel.
	 */
	public void setSuperModel(ComboBoxModel superModel) {
		if (this.superModel != superModel) {
			uninstallListDataListener(this.superModel);
			this.superModel = superModel;
			installListDataListener(this.superModel);

			if (this.superModel != null) {
				setSuperSelected(this.superModel.getSelectedItem());
			} else {
				setSuperSelected(null);
			}
		}
	}

	/**
	 * Installs the ListDataListener at the model.
	 * 
	 * @param model
	 *            the model to register the Listener with
	 */
	private void installListDataListener(ComboBoxModel model) {
		if (listDataListener == null)
			listDataListener = new ListDataAdapter() {

				/**
				 * Implementation of <code>contentsChanged()</code> in the
				 * interface <code>ListDataListener</code>.
				 * 
				 * @param listDataEvent
				 *            the <code>ListDataEvent</code> to be processed
				 */
				public void contentsChanged(ListDataEvent listDataEvent) {
					doContentsChanged(listDataEvent);
				}
			};

		if (model != null)
			model.addListDataListener(listDataListener);
	}

	/**
	 * Remove the ListDataListener from the model.
	 * 
	 * @param the
	 *            model to remove the Listener from
	 */
	private void uninstallListDataListener(ComboBoxModel model) {
		if (model != null)
			model.removeListDataListener(listDataListener);
	}

	/**
	 * Determines the new item selected in the supermodel and sets it.
	 * 
	 * @param listDataEvent
	 *            the <code>ListDataEvent</code> to be processed
	 */
	void doContentsChanged(ListDataEvent listDataEvent) {
		if (listDataEvent.getType() == ListDataEvent.CONTENTS_CHANGED)
			setSuperSelected(((ComboBoxModel) listDataEvent.getSource())
					.getSelectedItem());
	}

	/**
	 * Sets the new selected element of the super model only if it changed. This
	 * method is invoked each time the selected item in the super model changes
	 * and invokes itself the method <code>computeNewContents()</code>
	 * 
	 * @param selected
	 *            the newly selected object
	 */
	protected void setSuperSelected(Object selected) {
		if (superSelected != selected) {
			superSelected = selected;
			computeNewContents(selected);
		}
	}

	/**
	 * Compute the new Contents based on the new object selected in the super
	 * model. This implementation just removes any items in this combo box and
	 * is meant to be overridden by extending classes! This method is invoked
	 * each time the selected item in the super model changes to a truly new
	 * value.
	 * 
	 * @param superSelected
	 *            the newly selected object of the super model
	 */
	public void computeNewContents(Object superSelected) {
		removeAllElements();
	}

	/**
	 * Getter for property superSelected.
	 * 
	 * @return Value of property superSelected.
	 */
	public Object getSuperSelected() {
		return superSelected;
	}

}
