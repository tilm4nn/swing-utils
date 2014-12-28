/*
 * RowSortingTableModel.java
 *
 * Created on 3. Juni 2003, 23:04
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.tkuhn.util.DefaultComparator;
import de.tkuhn.util.OrderComparator;

/**
 * This Class is a decorator wrapper for <code>TableColumnModel</code>s that
 * enhances them wiht the ability of sorting the rows according to the values of
 * a column in ascending or descending order. In addition it provides a means
 * for storing and resetting the state of the sort order.
 * 
 * If you use <code>JUserFriendlyTable</code> you should not need to use this
 * class.
 * 
 * @see de.tkuhn.swing.table.JUserFriendlyTable
 * @see javax.swing.table.TableColumnModel
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class RowSortingTableModel extends AbstractTableModel implements
		TableModel {

	private static final long serialVersionUID = 1L;
	/** Value for sort() if data should not be sorted */
	public static final int NO_COLUMN = -1;
	/** Value for sort() in which order data should be sorted */
	public static final boolean ASCENDING = OrderComparator.ASCENDING;
	/** Value for sort() in which order data should be sorted */
	public static final boolean DESCENDING = OrderComparator.DESCENDING;

	/** The decorated <code>TableModel</code> */
	private TableModel model = null;

	/** Holds the row numbers of the original model in sorted order */
	private int[] sortedRow = null;

	/** Saves the sort order (ascending or descending) for each column */
	private boolean[] colOrder = null;

	/** The actual sort order used */
	// private boolean sortOrder;
	/** The actual column sorted by */
	private int sortColumn = NO_COLUMN;
	/** The class of the actual sortColum's objects */
	// private Class sortColClass;

	/** Used to fetch the right sorting values */
	private Comparator<Integer> sortingComparator = null;

	/** Used to enable sorting order */
	private OrderComparator<Object> orderComparator = new OrderComparator<Object>();

	/** Used for default sorting */
	private Comparator<?> defaultComparator = new DefaultComparator();

	/** Used to catch MouseEvents on the Table */
	private MouseListener mouseListener = null;

	/** Used to catch changes on the original Model */
	private TableModelListener tableModelListener = null;

	/**
	 * Convenience method for enabling row sorting for a given table. After you
	 * have done this you should not set a new <code>TableModel</code> at the
	 * table directly if you don't want to lose the sorting capability. Use
	 * <code>setNewTableModel(..)</code> instead. Also <code>getModel()</code>
	 * on the table will not return the original model of the table but an
	 * instance of <code>RowSortingTableModel</code>.
	 * 
	 * @see #setNewTableModel
	 * @param table
	 *            the table to enable row sorting for
	 * @return the same table
	 */
	public static JTable enableRowSorting(JTable table) {
		TableModel model = table.getModel();
		if (model instanceof RowSortingTableModel)
			throw new IllegalArgumentException(
					"Row sorting allready enabled for this table!");
		RowSortingTableModel support = new RowSortingTableModel();
		support.setupTableForRowSorting(table);
		return table;
	}

	/**
	 * Set a new <code>TableModel</code> at a table you have enabled row sorting
	 * before using <code>enableRowSorting(table)</code>. This will keep the
	 * sorting state if the new model is similar to the old one.
	 * 
	 * @see #enableRowSorting
	 * @param table
	 *            the table to set the new model for
	 * @param model
	 *            the new model to be set
	 */
	public static void setNewTableModel(JTable table, TableModel model) {
		TableModel sorter = table.getModel();
		if (sorter instanceof RowSortingTableModel) {
			((RowSortingTableModel) sorter).setModel(model);
		} else
			throw new IllegalArgumentException(
					"Table must be set up for row sorting!");
	}

	/** Instantiate the Listeners an the Comparator used by this model */
	private void initMembers() {
		mouseListener = new MouseAdapter() {

			/**
			 * Implementation of <code>mouseClicked(MouseEvent e)</code> in
			 * <code>MouseListener</code>
			 * 
			 * @see java.awt.event.MouseListener#
			 * @param e
			 *            The MouseEvent to be processed
			 */
			public void mouseClicked(MouseEvent e) {
				doMouseClicked(e);
			}
		};

		tableModelListener = new TableModelListener() {

			/**
			 * Implementation of <code>tableChanged(event)</code> in the
			 * interface <code>javax.swing.event.TableModelListener</code>.
			 * 
			 * @param tme
			 *            the event to process
			 * @see javax.swing.event.TableModelListener
			 */
			public void tableChanged(TableModelEvent tme) {
				doTableChanged(tme);
			}
		};

		sortingComparator = new Comparator<Integer>() {

			/**
			 * Implementation of <code>compare(obj,obj)</code> in the interface
			 * <code>java.util.Comparator</code> used to sort the rows.
			 * 
			 * @param i1
			 *            row one to be compared
			 * @param i2
			 *            row two to be compared
			 * @return the result of comparison
			 */
			public int compare(Integer i1, Integer i2) {
				return doCompare(i1, i2);
			}
		};

	}

	/** Creates a new instance of RowSortingTableModel */
	public RowSortingTableModel() {
		initMembers();
	}

	/**
	 * Creates a new instance of RowSortingTableModel that wrapps the given
	 * model
	 * 
	 * @param model
	 *            the model that will be decorated
	 */
	public RowSortingTableModel(TableModel model) {
		this();
		setModel(model);
	}

	/**
	 * Resorts the data if necessary.
	 * 
	 * @param e
	 *            The MouseEvent to be processed
	 */
	void doMouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			JTable table = ((JTableHeader) e.getSource()).getTable();
			TableColumnModel colModel = table.getColumnModel();
			int viewCol = colModel.getColumnIndexAtX(e.getX());
			int modelCol = table.convertColumnIndexToModel(viewCol);
			if (e.getClickCount() == 1 && modelCol != -1) {
				if (modelCol == sortColumn)
					colOrder[sortColumn] = !colOrder[sortColumn];
				else
					sortColumn = modelCol;
				sort();
				fireTableDataChanged();
			}
		}
	}

	/**
	 * Registers this <code>RowSortingTableModel</code> at the given table by
	 * setting itself as its new model and wrapping the table's old model. Also
	 * installs the header extension for event listening at the table header.
	 * 
	 * @param aTable
	 *            the table to enable row sorting for
	 */
	public void setupTableForRowSorting(JTable aTable) {
		if (aTable != null) {
			TableModel model = aTable.getModel();
			if (!(model instanceof RowSortingTableModel)) {
				setModel(model);
				aTable.setModel(this);
				installHeaderExtension(aTable);
			}
		}
	}

	/**
	 * Remove row sorting from a table at which it was previously installed
	 * using <code>setupTableForRowSorting(table)</code> by setting the table's
	 * model to its old model again and removing the header extension for event
	 * listening
	 * 
	 * @param aTable
	 *            the table to remove row sorting from
	 * @see #setupTableForRowSorting
	 */
	public void tearDownTableFromRowSorting(JTable aTable) {
		TableModel sortModel = aTable.getModel();
		if (sortModel == this) {
			aTable.setModel(model);
			uninstallHeaderExtension(aTable);
		}
	}

	/**
	 * Install the header extension for event listening at the given table. You
	 * should not need to call this manually!. Use
	 * <code>setupTableForRowSorting(table)</code> to install row sorting.
	 * 
	 * @param aTable
	 *            the table to install the extension
	 * @see #setupTableForRowSorting
	 */
	public void installHeaderExtension(JTable aTable) {
		aTable.setColumnSelectionAllowed(false);
		aTable.getTableHeader().addMouseListener(mouseListener);
	}

	/**
	 * Remove the header extension for event listening from the given table. You
	 * should not need to call this manually!. Use
	 * <code>tearDownTableFromRowSorting(table)</code> to uninstall row sorting.
	 * 
	 * @param aTable
	 *            the table to uninstall the extension from
	 * @see #tearDownTableFromRowSorting
	 */
	public void uninstallHeaderExtension(JTable aTable) {
		aTable.getTableHeader().removeMouseListener(mouseListener);
	}

	/**
	 * Set a new <code>TableModel</code> in this decorator and notify all
	 * <code>TableModelListeners</code>. This will keep the sorting state if the
	 * new model is similar to the old one.
	 * 
	 * @param aModel
	 *            the new model to be installed
	 */
	public void setModel(TableModel aModel) {
		if (model != aModel) {
			if (model != null)
				model.removeTableModelListener(tableModelListener);
			if (aModel != null) {
				TableModel oldModel = model;
				Object state = null;
				if (oldModel != null)
					state = getState();
				model = aModel;
				init();
				model.addTableModelListener(tableModelListener);
				if (oldModel != null)
					internalSetState(state);
			} else {
				model = null;
				sortedRow = null;
				colOrder = null;
				sortColumn = NO_COLUMN;
			}
			fireTableStructureChanged();
		}
	}

	/** Initialize the members of this object after a model change */
	private void init() {
		initRows();
		initCols();
	}

	/** Initializes row data */
	private void initRows() {
		sortedRow = new int[model.getRowCount()];
		for (int i = model.getRowCount() - 1; i >= 0; i--)
			sortedRow[i] = i;
	}

	/** Initialize column data */
	private void initCols() {
		colOrder = new boolean[model.getColumnCount()];
		if (model.getColumnCount() == 0)
			sortColumn = NO_COLUMN;
		else
			Arrays.fill(colOrder, true);
	}

	/**
	 * Get the <code>TableModel</code> decorated by this wrapper.
	 * 
	 * @return the decorated <code>TableModel</code>
	 */
	public TableModel getModel() {
		return model;
	}

	/** Resort the rows by values of <code>sortColumn</code> */
	private void sort() {
		if (sortColumn == NO_COLUMN) {
			initRows();
		} else {
			orderComparator.setOrder(colOrder[sortColumn]);
			Integer[] rows = new Integer[sortedRow.length];

			Comparator<?> comparator = null;
			if (model instanceof ComparatorTableModel) {
				comparator = ((ComparatorTableModel) model)
						.getComparator(sortColumn);
			}
			if (comparator == null)
				comparator = defaultComparator;
			orderComparator.setComparator((Comparator<Object>) comparator);

			for (int i = sortedRow.length - 1; i >= 0; i--)
				rows[i] = i;

			Arrays.sort(rows, sortingComparator);

			for (int i = sortedRow.length - 1; i >= 0; i--) {
				sortedRow[i] = rows[i];
			}
		}
	}

	/**
	 * Have the decorator sort the rows by values of given column in given
	 * order. Also notifies listeners of changes.
	 * 
	 * @param columnIndex
	 *            index of column to sort by. Could be an int &gt;= 0 or NO_COLUMN
	 * @param order
	 *            can be ASCENDING or DESCENDING
	 * @see #NO_COLUMN
	 * @see #ASCENDING
	 * @see #DESCENDING
	 */
	public void sort(int columnIndex, boolean order) {
		if ((columnIndex <= getColumnCount() && columnIndex >= 0)
				|| columnIndex == NO_COLUMN) {

			boolean mustSort = false;
			if (columnIndex != sortColumn)
				mustSort = true;
			else if (columnIndex >= 0 && colOrder[columnIndex] != order)
				mustSort = true;

			if (mustSort) {
				sortColumn = columnIndex;
				if (sortColumn != NO_COLUMN)
					colOrder[sortColumn] = order;
				sort();
				fireTableDataChanged();
			}
		}
	}

	/**
	 * Creates a Memento of the state of this wrapper. This can be used for a
	 * restauration of the State.
	 * 
	 * @return a JavaBean representing the state of the RowSorting Interface.
	 */
	public Object getState() {
		RowSortingState state = new RowSortingState();

		state.setSortCol(sortColumn);
		if (model != null) {
			String[] names = new String[model.getColumnCount()];
			for (int i = 0; i < model.getColumnCount(); i++)
				names[i] = model.getColumnName(i);
			state.setColNames(names);
			state.setColOrder((boolean[]) colOrder.clone());
		} else {
			state.setColNames(new String[0]);
			state.setColOrder(new boolean[0]);
		}
		return state;
	}

	/**
	 * Resets this wrapper to the given state if the column structure is similar
	 * to the one present while <code>getState()</code> was called. This method
	 * must be called with a state object obtained by <code>getState()</code>.
	 * 
	 * @param aState
	 *            the state to set.
	 * @return true if the column structure was similar and the state has been
	 *         reset.
	 * @see #getState
	 */
	public boolean setState(Object aState) {
		boolean ok = internalSetState(aState);
		fireTableDataChanged();
		return ok;
	}

	/**
	 * Resets this wrapper to the given state if the column structure is similar
	 * to the one present while <code>getState()</code> was called. This method
	 * must be called with a state object obtained by <code>getState()</code>.
	 * This does not notify event listeners contrary to
	 * <code>setState(state)</code> but returns true if rows had to be sorted.
	 * 
	 * @param state
	 *            the state to set.
	 * @return true if the column structure was similar and the state has been
	 *         reset.
	 * @see #getState
	 */
	private boolean internalSetState(Object aState) {
		if (aState == null)
			throw new IllegalArgumentException("NULL is not a legal state.");
		if (!(aState instanceof RowSortingState))
			throw new IllegalArgumentException(
					"This was not a legal state obtained with getState()");
		RowSortingState state = (RowSortingState) aState;
		if (isSimilar(state)) {
			colOrder = (boolean[]) state.getColOrder().clone();
			sortColumn = state.getSortCol();
			sort();
			return true;
		}
		return false;
	}

	/**
	 * Check if the model is similar to the model present at the given state.
	 * 
	 * @param state
	 *            the state to check
	 * @return true if similar
	 */
	private boolean isSimilar(RowSortingState state) {
		String[] names = state.getColNames();
		if (names.length != model.getColumnCount())
			return false;
		String colName;

		for (int i = 0; i < names.length; i++) {
			colName = model.getColumnName(i);
			if (names[i] == null || colName == null) {
				if (colName != names[i])
					return false;
			} else if (!names[i].equals(colName))
				return false;
		}
		return true;
	}

	/**
	 * Implements <code>getColumnCount()</code> in the interface
	 * <code>TableModel</code>.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public int getColumnCount() {
		return (model == null) ? 0 : model.getColumnCount();
	}

	/**
	 * Implements <code>getRowCount()</code> in the interface
	 * <code>TableModel</code>.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public int getRowCount() {
		return (model == null) ? 0 : model.getRowCount();
	}

	/**
	 * Implements <code>getValueAt(row,col)</code> in the interface
	 * <code>TableModel</code>. Recalculate row by sort order.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return model.getValueAt(getModelRow(rowIndex), columnIndex);
	}

	/**
	 * Implements <code>getColumnClass(col)</code> in the interface
	 * <code>TableModel</code>.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return model.getColumnClass(columnIndex);
	}

	/**
	 * Implements <code>getColumnName(col)</code> in the interface
	 * <code>TableModel</code>.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public String getColumnName(int columnIndex) {
		return model.getColumnName(columnIndex);
	}

	/**
	 * Implements <code>isCellEditable(row,col)</code> in the interface
	 * <code>TableModel</code>. Recalculate row by sort order.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return model.isCellEditable(getModelRow(rowIndex), columnIndex);
	}

	/**
	 * Implements <code>getColumnCount()</code> in the interface
	 * <code>TableModel</code>. Recalculate row by sort order.
	 * 
	 * @see javax.swing.table.TableModel
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		model.setValueAt(aValue, getModelRow(rowIndex), columnIndex);
	}

	/**
	 * Return the row number of the original model for the sorted row number.
	 * 
	 * @param row
	 *            the sorted row number
	 * @return the row number in the original model
	 */
	public int getModelRow(int row) {
		return sortedRow[row];
	}

	/**
	 * Reinitializes this wrapper and resorts the rows if necessary. Also
	 * recalculates the row numbers in the <code>TableModelEvent</code> for the
	 * listeners
	 * 
	 * @param tme
	 *            the event to process
	 */
	void doTableChanged(TableModelEvent tme) {
		boolean mustSort = false;
		if (tme.getFirstRow() == TableModelEvent.HEADER_ROW) {
			Object state = getState();
			init();
			internalSetState(state);
			fireTableStructureChanged();
			return;
		}
		if (tme.getType() == TableModelEvent.INSERT
				|| tme.getType() == TableModelEvent.DELETE) {
			initRows();
			mustSort = true;
		}
		if (tme.getColumn() == TableModelEvent.ALL_COLUMNS
				|| tme.getColumn() == sortColumn) {
			mustSort = true;
		}
		if (mustSort)
			sort();
		fireTableDataChanged();
	}

	/**
	 * Treats input as two instances of <code>java.lang.Integer</code> that
	 * identify the to rows to be compared. Returns comparison value depending
	 * on the value of member <code>sortColum</code>
	 * 
	 * @param o1
	 *            row one to be compared
	 * @param o2
	 *            row two to be compared
	 * @return the result of invocation of compare on the orderComparator with
	 *         the real objects to compare.
	 */
	private int doCompare(Integer i1, Integer i2) {
		return orderComparator.compare(model.getValueAt(i1, sortColumn),
				model.getValueAt(i2, sortColumn));
	}

}
