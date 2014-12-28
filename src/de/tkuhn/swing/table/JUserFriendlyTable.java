/*
 * JUserFriendlyTable.java
 *
 * Created on 3. Juni 2003, 18:32
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
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * This subclass of <code>JTable</code> provides the additional ability for the
 * user to hide/unhide its columns using a contextual menu of the table header
 * and further it supports sorting of the table's rows by column in ascending
 * and descending order. It also preserves the size of the columns, their
 * position and sorting state if a new <code>TableModel</code> is set that is
 * similar to the old one.
 * 
 * In addition the column hiding state, size, position and sorting state can be
 * obtained as a <code>Serializable</code> object to be made persistent. <b>
 * Attention: This might not work between different versions of this class </b>
 * 
 * Functionality is achieved by using the
 * <code>ColumnStateTableColumnModel</code> and a
 * <code>RowSortingTableModel</code>
 * 
 * 
 * @see de.tkuhn.swing.table.RowSortingTableModel
 * @see de.tkuhn.swing.table.ColumnStateTableColumnModel
 * @see javax.swing.JTable
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class JUserFriendlyTable extends JTable implements TableModelListener {

	private static final long serialVersionUID = 1L;

	/** True if initialization is over */
	private boolean initialized = false;

	/**
	 * True if the table should preserve the column states when the model
	 * changes.
	 */
	private boolean statePreserving = true;

	/** The RowSortingTableModel used */
	private RowSortingTableModel model = null;

	/** The ColumnStateTableColumnModel used */
	private ColumnStateTableColumnModel columnModel = null;

	/**
	 * Constructs a default <code>JUserFriendlyTable</code> that is initialized
	 * with a default data model, a default column model, and a default
	 * selection model.
	 * 
	 * @see javax.swing.JTable#createDefaultDataModel
	 * @see #createDefaultColumnModel
	 * @see javax.swing.JTable#createDefaultSelectionModel
	 */
	public JUserFriendlyTable() {
		super();
		init();
	}

	/**
	 * Constructs a <code>JUserFriendlyTable</code> that is initialized with
	 * <code>tableModel</code> as the data model, a default column model, and a
	 * default selection model.
	 * 
	 * @param tableModel
	 *            the data model for the table
	 * @see #createDefaultColumnModel
	 * @see javax.swing.JTable#createDefaultSelectionModel
	 */
	public JUserFriendlyTable(TableModel tableModel) {
		super(tableModel);
		init();
	}

	/**
	 * Constructs a <code>JUserFriendlyTable</code> that is initialized with
	 * <code>tableModel</code> as the data model, and
	 * <code>selectionModel</code> as the selection model. If any of the
	 * parameters are <code>null</code> this method will initialize the table
	 * with the corresponding default model.
	 * 
	 * @param tableModel
	 *            the data model for the table
	 * @param selectionModel
	 *            the row selection model for the table
	 * @see javax.swing.JTable#createDefaultDataModel
	 * @see javax.swing.JTable#createDefaultSelectionModel
	 */
	public JUserFriendlyTable(TableModel tableModel,
			ListSelectionModel selectionModel) {
		super(tableModel, null, selectionModel);
		init();
	}

	/**
	 * Constructs a <code>JUserFriendlyTable</code> with <code>numRows</code>
	 * and <code>numColumns</code> of empty cells using
	 * <code>DefaultTableModel</code>. The columns will have names of the form
	 * "A", "B", "C", etc.
	 * 
	 * @param numRows
	 *            the number of rows the table holds
	 * @param numColumns
	 *            the number of columns the table holds
	 * @see javax.swing.table.DefaultTableModel
	 */
	public JUserFriendlyTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		init();
	}

	/**
	 * Constructs a <code>JUserFriendlyTable</code> to display the values in the
	 * <code>Vector</code> of <code>Vectors</code>, <code>rowData</code>, with
	 * column names, <code>columnNames</code>. The <code>Vectors</code>
	 * contained in <code>rowData</code> should contain the values for that row.
	 * In other words, the value of the cell at row 1, column 5 can be obtained
	 * with the following code:
	 * 
	 * <pre>
	 * ((Vector) rowData.elementAt(1)).elementAt(5);
	 * </pre>
	 * <p>
	 * 
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public JUserFriendlyTable(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		init();
	}

	/**
	 * Constructs a <code>JUserFriendlyTable</code> to display the values in the
	 * two dimensional array, <code>rowData</code>, with column names,
	 * <code>columnNames</code>. <code>rowData</code> is an array of rows, so
	 * the value of the cell at row 1, column 5 can be obtained with the
	 * following code:
	 *  
	 * <pre>
	 * rowData[1][5];
	 * </pre>
	 * 
	 * All rows must be of the same length as <code>columnNames</code>.
	 *  
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public JUserFriendlyTable(final Object[][] rowData,
			final Object[] columnNames) {
		super(rowData, columnNames);
		init();
	}

	/** Initialize the components of the JUserFriendlyTable */
	private void init() {
		installColumnHiding();
		installRowSorting();
		balanceColumns();
		initialized = true;
	}

	/**
	 * Creates a Memento of the state of this table. This can be used for a
	 * restoration of the State.
	 * 
	 * @return an Object representing the state of the table that implements the
	 *         <code>Serializable</code> Interface. And can also be saved using
	 *         the <code>XMLEncoder</code>.
	 * @see java.io.Serializable
	 * @see java.beans.XMLEncoder
	 */
	public Object getState() {
		Object[] states = { columnModel.getState(), model.getState() };
		return states;
	}

	/**
	 * Resets this table to the given state if the <code>TableModel</code> is
	 * similar to the model present when <code>getState()</code> was called.
	 * This method must be called with a state object obtained by
	 * <code>getState()</code>.
	 * 
	 * @param state
	 *            the state to set.
	 * @return true if the model was similar and the state has been reset.
	 * @see #getState
	 */
	public boolean setState(Object state) {
		Object[] states = (Object[]) state;
		return columnModel.setState(states[0]) && model.setState(states[1]);
	}

	/**
	 * <b>This method must not be called directly by user!</b> Overrides
	 * <code>createDefaultColumnModel()</code> in <code>JTable</code>
	 * 
	 * @return a new <code>ColumnStateTableColumnModel</code>
	 */
	protected TableColumnModel createDefaultColumnModel() {
		return new ColumnStateTableColumnModel();
	}

	/** Install column hiding support */
	private void installColumnHiding() {
		columnModel = (ColumnStateTableColumnModel) getColumnModel();
		columnModel.installPopup(this);
	}

	/** Install row sorting support */
	private void installRowSorting() {
		model = new RowSortingTableModel();
		model.setupTableForRowSorting(this);
	}

	/**
	 * Balance the width of the table columns based on their content.
	 */
	public void balanceColumns() {
		TableColumn column = null;
		Component comp = null;

		int headerWidth = 0;
		int cellWidth = 0;
		int maxCellWidth = 0;

		TableColumnModel colModel = getColumnModel();
		JTableHeader header = getTableHeader();
		if (colModel == null || header == null)
			return;
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		for (int i = 0; i < getColumnCount(); i++) {

			maxCellWidth = 10;

			column = colModel.getColumn(i);
			comp = headerRenderer.getTableCellRendererComponent(null,
					column.getHeaderValue(), false, false, 0, i);
			headerWidth = comp.getPreferredSize().width;

			for (int j = 0; j < getRowCount(); j++) {
				comp = getCellRenderer(j, i).getTableCellRendererComponent(
						this, getValueAt(j, i), false, false, j, i);
				cellWidth = comp.getPreferredSize().width;

				if (cellWidth > maxCellWidth)
					maxCellWidth = cellWidth;
			}

			column.setPreferredWidth(Math.max(headerWidth, maxCellWidth));
		}
	}

	/**
	 * Sets a new <code>TableModel</code> for this table. The
	 * <code>RowSortingTableModel</code> this table uses is not replaced. The
	 * given model is set at the sorting model instead. In addition if the new
	 * model is similar to the old one the column hiding, size, position state
	 * and row sorting state are preserved.
	 * 
	 * @param aModel
	 *            the new <code>TableModel</code> to be used
	 */
	public void setModel(TableModel aModel) {
		if (initialized) {
			Object state = null;
			if (statePreserving)
				state = columnModel.getState();
			model.setModel(aModel);
			if (statePreserving) {
				if (!columnModel.setState(state))
					balanceColumns();
			} else {
				balanceColumns();
			}
		} else {
			super.setModel(aModel);
		}
	}

	/**
	 * Since this subclass uses a <code>RowSortingTableModel</code> to provide
	 * row order the returned <code>TableModel</code> is an instance of that
	 * class. That does mean that <code>getModel()</code> does not return
	 * <code>TableModel</code> instances set with <code>setModel(model)</code>.
	 * To obtain the 'real' data model you should use
	 * <code>getUnsortedModel()</code>
	 * 
	 * @return the <code>RowSortingTableModel</code> used by this table
	 * @see #getUnsortedModel
	 */
	public TableModel getModel() {
		return super.getModel();
	}

	/**
	 * Return the raw unsorted data of this table that is sorted by this
	 * <code>JUserFriendlyTable</code>'s <code>RowSortingTableModel</code>.
	 * 
	 * @return the 'real' <code>TableModel</code> used by this table
	 */
	public TableModel getUnsortedModel() {
		if (initialized)
			return model.getModel();
		else
			return super.getModel();
	}

	/**
	 * <b>This method must not be called directly by user!</b> Implementation of
	 * <code>tableChanged(event)</code> in the interface
	 * <code>TableModelListener</code>. Preserves the state of the
	 * <code>ColumnStateColumnModel</code> if the table structure changes and
	 * forwards the event to its super class.
	 * 
	 * @see javax.swing.event.TableModelListener
	 * @param tme
	 *            the event to process
	 */
	public void tableChanged(TableModelEvent tme) {
		boolean preserveState = (tme.getFirstRow() == TableModelEvent.HEADER_ROW)
				&& (columnModel != null) && statePreserving;
		Object state = null;
		if (preserveState)
			state = columnModel.getState();
		super.tableChanged(tme);
		if (preserveState) {
			if (!columnModel.setState(state))
				balanceColumns();
		} else {
			balanceColumns();
		}
	}

	/**
	 * Is the table preserving the column state on a model change or just
	 * balancing columns?
	 * 
	 * @return Value of property statePreserving.
	 */
	public boolean isStatePreserving() {
		return statePreserving;
	}

	/**
	 * Set if the table should preserve column state on a model change or just
	 * balance columns.
	 * 
	 * @param statePreserving
	 *            New value of property statePreserving.
	 */
	public void setStatePreserving(boolean statePreserving) {
		boolean old = this.statePreserving;
		this.statePreserving = statePreserving;
		this.firePropertyChange("statePreserving", old, statePreserving);
	}

}
