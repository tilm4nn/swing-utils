/*
 * Created on 11.06.2003
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

/**
 * A JavaBean used to store the state of a <code>RowSortingTableModel</code>.
 * 
 * You should not have to use this class.
 * 
 * @author tilmann
 * @version 0.9.3
 * @see de.tkuhn.swing.table.RowSortingTableModel
 */
public class RowSortingState implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The order in which the col was sorted last time */
	private boolean[] colOrder;

	/** The names of the columns last time */
	private String[] colNames;

	/** The number of the column of the last sort */
	private int sortCol;

	/**
	 * Getter for property colOrder.
	 * 
	 * @return Value of property colOrder.
	 */
	public boolean[] getColOrder() {
		return colOrder;
	}

	/**
	 * Getter for property sortCol.
	 * 
	 * @return Value of property sortCol.
	 */
	public int getSortCol() {
		return sortCol;
	}

	/**
	 * Setter for property colOrder.
	 * 
	 * @param order
	 *            New value of property colOrder.
	 */
	public void setColOrder(boolean[] order) {
		colOrder = order;
	}

	/**
	 * Setter for property sortCol.
	 * 
	 * @param col
	 *            New value of property sortCol.
	 */
	public void setSortCol(int col) {
		sortCol = col;
	}

	/**
	 * Getter for property colNames.
	 * 
	 * @return Value of property colNames.
	 */
	public String[] getColNames() {
		return colNames;
	}

	/**
	 * Setter for property colNames.
	 * 
	 * @param names
	 *            New value of property colNames.
	 */
	public void setColNames(String[] names) {
		colNames = names;
	}

}
