/*
 * RememberingTableColumnModel.java
 *
 * Created on 4. Juni 2003, 16:01
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

package de.tkuhn.swing.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * This implementation of <code>TableColumnModel</code> provides the ability of
 * hiding and unhiding table columns from display using a contextual menu of the
 * table header. It also offers support for preserving column state such as
 * size, position and hiding state over changes of <code>TableModel</code> in a
 * JTable by providing objects that represent a distinct state that can be reset
 * lateron.
 * 
 * If you use <code>JUserFriendlyTable</code> you should not need to use this
 * class.
 * 
 * @see de.tkuhn.swing.table.JUserFriendlyTable
 * @see javax.swing.table.TableColumnModel
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class ColumnStateTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;

	/** Class used to keep track of hidden columns */
	private class HiddenColumn {

		TableColumn column;
		int lastIndex;

	}

	/** The popup menu for hiding/unhiding */
	private JPopupMenu popup = new JPopupMenu();

	/** Mapping between MenuItems and column identifiers */
	private Map<JCheckBoxMenuItem, Object> idMap = new HashMap<JCheckBoxMenuItem, Object>();
	private Map<Object, JCheckBoxMenuItem> itemMap = new HashMap<Object, JCheckBoxMenuItem>();

	/** A map containing hidden columns */
	private Map<Object, HiddenColumn> hiddenCols = new HashMap<Object, HiddenColumn>();

	/** Used to catch MouseEvents on the Table */
	private MouseListener mouseListener = new MouseAdapter() {

		/**
		 * Implementation of <code>mousePressed(MouseEvent e)</code> in
		 * <code>MouseListener</code> Show the popup if necessary.
		 * 
		 * @param e
		 *            the event to be processed
		 * @see java.awt.event.MouseListener
		 */
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		/**
		 * Implementation of <code>mouseReleased(MouseEvent e)</code> in
		 * <code>MouseListener</code> Show the popup if necessary.
		 * 
		 * @param e
		 *            the event to be processed
		 * @see java.awt.event.MouseListener
		 */
		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}
	};

	private ActionListener actionListener = new ActionListener() {

		/**
		 * Implementation of <code>actionPerformed(ActionEvent ae)</code> as
		 * defined by <code>ActionListener</code>.
		 * 
		 * @param ae
		 *            the action event
		 */
		public void actionPerformed(ActionEvent ae) {
			doActionPerformed(ae);
		}
	};

	/** Creates a new instance of RememberingTableColumnModel */
	public ColumnStateTableColumnModel() {
	}

	/**
	 * Creates a Memento of the state of this column model. This can be used for
	 * a restoration of the State.
	 * 
	 * @return an Object that represents the state of the table columns and
	 *         implements the <code>Serializable</code> interface. It can also
	 *         be saved using the <code>XMLEncoder</code>
	 * @see java.io.Serializable
	 * @see java.beans.XMLEncoder
	 */
	public Object getState() {
		int columnCount = getColumnCount();
		ColumnState[] state = new ColumnState[columnCount + hiddenCols.size()];
		for (int i = 0; i < columnCount; i++) {
			TableColumn col = getColumn(i);
			Object id = col.getIdentifier();
			state[i] = getColumnState(id);
		}
		for (Object id : hiddenCols.keySet()) {
			state[columnCount++] = getColumnState(id);
		}
		return state;
	}

	/**
	 * Resets this column model to the given state if the column structure is
	 * similar to the one present while <code>getState()</code> was called. This
	 * method must be called with a state object obtained by
	 * <code>getState()</code>.
	 * 
	 * @param aState
	 *            the state to set.
	 * @see #getState
	 * @return true if column structure was similar and state has been reseted
	 */
	public boolean setState(Object aState) {
		if (aState == null)
			throw new IllegalArgumentException("NULL is not a legal state!");
		if (!(aState instanceof ColumnState[]))
			throw new IllegalArgumentException(
					"This is not a state obtaind by getState()");
		ColumnState[] state = (ColumnState[]) aState;
		if (isSimilar(state)) {
			for (ColumnState colState : state) {
				setColumnState(colState);
			}
			return true;
		}
		return false;
	}

	/**
	 * Check if this <code>ColumnModel</code> is similar to the one the given
	 * state belongs to. It uses column count and identifiers for that purpose.
	 * 
	 * @param state
	 *            the state to check.
	 * @return true if similar
	 */
	private boolean isSimilar(ColumnState[] state) {
		if (state.length != getColumnCount() + hiddenCols.size())
			return false;
		try {
			for (ColumnState colState : state) {
				if (!hiddenCols.containsKey(colState.getColId()))
					getColumnIndex(colState.getColId());
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Get the state for a single column in this model
	 * 
	 * @param colId
	 *            the identifier of the column
	 * @return the memento object
	 */
	protected ColumnState getColumnState(Object colId) {
		ColumnState state = new ColumnState(colId);
		try {
			int index = getColumnIndex(colId);
			state.saveState(getColumn(index));
			state.setIndex(index);
			state.setHidden(false);

			// Seems to be a hidden column
		} catch (IllegalArgumentException e) {
			HiddenColumn col = (HiddenColumn) hiddenCols.get(colId);
			state.saveState(col.column);
			state.setIndex(col.lastIndex);
			state.setHidden(true);
		}
		return state;
	}

	/**
	 * Resets the state of a single column in this model
	 * 
	 * @param colState
	 *            the state to be established
	 */
	protected void setColumnState(ColumnState colState) {
		Object colId = colState.getColId();
		try {
			int index = getColumnIndex(colId);
			colState.resetState(getColumn(index));
			if (colState.isHidden()) {
				hideColumn(colId);
			} else {
				int stateIndex = colState.getIndex();
				if (index != stateIndex && stateIndex < getColumnCount())
					moveColumn(index, stateIndex);
			}

			// Maybe it is a hidden column
		} catch (IllegalArgumentException e) {
			if (hiddenCols.containsKey(colId)) {
				HiddenColumn col = (HiddenColumn) hiddenCols.get(colId);
				colState.resetState(col.column);
				col.lastIndex = colState.getIndex();
				if (!colState.isHidden()) {
					showColumn(colId);
				}
			}
		}
	}

	/**
	 * Install the popup menu used by this column model for column hiding on a
	 * <code>JTable</code>
	 * 
	 * @param table
	 *            the table to install the popup on
	 */
	public void installPopup(JTable table) {
		// Must trigger the popup menu
		table.getTableHeader().addMouseListener(mouseListener);
	}

	/**
	 * Remove the popup menu installed by this instance from the table
	 * 
	 * @param table
	 *            the table to remove the popup from
	 */
	public void uninstallPopup(JTable table) {
		// Remove Listener
		table.getTableHeader().removeMouseListener(mouseListener);
	}

	/**
	 * Hide a column and set the state of the popup menu
	 * 
	 * @param colId
	 *            the column identifier
	 */
	public void hideColumn(Object colId) {
		if (getColumnCount() > 1) {
			try {
				HiddenColumn col = new HiddenColumn();
				col.lastIndex = getColumnIndex(colId);
				col.column = getColumn(col.lastIndex);
				super.removeColumn(col.column);
				hiddenCols.put(colId, col);
				(itemMap.get(colId)).setSelected(false);
			} catch (IllegalArgumentException e) {
			}
		}
	}

	/**
	 * Show a previously hidden column and set the state of the popup menu
	 * 
	 * @param colId
	 *            the column identifier
	 */
	public void showColumn(Object colId) {
		if (hiddenCols.containsKey(colId)) {
			HiddenColumn col = (HiddenColumn) hiddenCols.remove(colId);
			super.addColumn(col.column);
			int index = getColumnIndex(colId);
			if (col.lastIndex != index) {
				if (col.lastIndex >= getColumnCount()) {
					moveColumn(index, getColumnCount() - 1);
				} else {
					moveColumn(index, col.lastIndex);
				}
			}
			(itemMap.get(colId)).setSelected(true);
		}
	}

	/**
	 * Show all hidden columns
	 */
	public void showAllColumns() {
		Object[] ids = hiddenCols.keySet().toArray();
		for (int i = ids.length - 1; i >= 0; i--) {
			showColumn(ids[i]);
		}
	}

	/**
	 * This is invoked by popopup menu items when users click on them and
	 * hides/unhides the appropriate column and adjusts the popup menu
	 * 
	 * @param ae
	 *            the action event
	 */
	void doActionPerformed(ActionEvent ae) {
		JCheckBoxMenuItem item = (JCheckBoxMenuItem) ae.getSource();
		item.setSelected(!item.isSelected());
		Object colId = idMap.get(item);
		if (hiddenCols.containsKey(colId))
			showColumn(colId);
		else
			hideColumn(colId);
	}

	/**
	 * Shows the popup menu if the event is a popup-trigger
	 * 
	 * @param e
	 *            the event to be checked
	 */
	void showPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show((Component) e.getSource(), e.getX(), e.getY());
		}
	}

	/**
	 * <b>This should not be called by a user directly!</b> Implementation of
	 * <code>addColumn(column)</code> in the <code>TableColumnModel</code>
	 * interface. Adds a column to this column model and creates an item in the
	 * popup menu for hiding
	 * 
	 * @param aColumn
	 *            the column to be added
	 */
	public void addColumn(TableColumn aColumn) {
		Object id = aColumn.getIdentifier();
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(
				(String) aColumn.getHeaderValue(), true);
		item.addActionListener(actionListener);
		idMap.put(item, id);
		itemMap.put(id, item);
		popup.add(item);
		super.addColumn(aColumn);
	}

	/**
	 * <b>This should not be called by a user directly!</b> Implementation of
	 * <code>removeColumn(column)</code> in the <code>TableColumnModel</code>
	 * interface. Removes a column from this column model and its corresponding
	 * item in the popup menu
	 * 
	 * @param aColumn
	 *            the column to be removed
	 */
	public void removeColumn(TableColumn aColumn) {
		Object id = aColumn.getIdentifier();
		if (hiddenCols.containsKey(id))
			hiddenCols.remove(id);
		else
			super.removeColumn(aColumn);
		JCheckBoxMenuItem item = (JCheckBoxMenuItem) itemMap.remove(id);
		item.removeActionListener(actionListener);
		idMap.remove(item);
		popup.remove(item);
		if (getColumnCount() == 0) {
			resetHiddenCols();
		}
	}

	/** Clears all information about hidden columns */
	private void resetHiddenCols() {
		popup.removeAll();
		idMap.clear();
		itemMap.clear();
		hiddenCols.clear();
	}
}
