/*
 * ColumnState.java
 *
 * Created on 11. Juni 2003, 10:34
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

import java.io.Serializable;

import javax.swing.table.TableColumn;

/**
 * JavaBean used to store the state of a <code>TableColumn</code> in the
 * <code>ColumnStateTableColumnModel</code>
 * 
 * You should not have to use this class.
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 * @see de.tkuhn.swing.table.ColumnStateTableColumnModel
 */
public class ColumnState implements Serializable {

	private static final long serialVersionUID = 1L;

	private int maxWidth;
	private int minWidth;
	private int prefWidth;
	private int index;
	private boolean hidden;
	private Object colId;

	/**
	 * Create a new ColumnState for given colId
	 * 
	 * @param colId
	 *            the column identifier
	 */
	public ColumnState(Object colId) {
		setColId(colId);
	}

	/** Creates a new ColumnState */
	public ColumnState() {
	}

	/**
	 * Save the widthes of the column in this state
	 * 
	 * @param col
	 *            the column
	 */
	public void saveState(TableColumn col) {
		maxWidth = col.getMaxWidth();
		minWidth = col.getMinWidth();
		prefWidth = col.getPreferredWidth();
	}

	/**
	 * Reset the column's widthes to the ones in this state
	 * 
	 * @param col
	 *            the column
	 */
	public void resetState(TableColumn col) {
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setPreferredWidth(prefWidth);
	}

	/**
	 * Getter for property maxWidth.
	 * 
	 * @return Value of property maxWidth.
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Setter for property maxWidth.
	 * 
	 * @param maxWidth
	 *            New value of property maxWidth.
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Getter for property minWidth.
	 * 
	 * @return Value of property minWidth.
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * Setter for property minWidth.
	 * 
	 * @param minWidth
	 *            New value of property minWidth.
	 */
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	/**
	 * Getter for property prefWidth.
	 * 
	 * @return Value of property prefWidth.
	 */
	public int getPrefWidth() {
		return prefWidth;
	}

	/**
	 * Setter for property prefWidth.
	 * 
	 * @param prefWidth
	 *            New value of property prefWidth.
	 */
	public void setPrefWidth(int prefWidth) {
		this.prefWidth = prefWidth;
	}

	/**
	 * Getter for property index.
	 * 
	 * @return Value of property index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Setter for property index.
	 * 
	 * @param index
	 *            New value of property index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Getter for property hidden.
	 * 
	 * @return Value of property hidden.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Setter for property hidden.
	 * 
	 * @param hidden
	 *            New value of property hidden.
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Getter for property colId.
	 * 
	 * @return Value of property colId.
	 */
	public java.lang.Object getColId() {
		return colId;
	}

	/**
	 * Setter for property colId.
	 * 
	 * @param colId
	 *            New value of property colId.
	 */
	public void setColId(java.lang.Object colId) {
		this.colId = colId;
	}

}
