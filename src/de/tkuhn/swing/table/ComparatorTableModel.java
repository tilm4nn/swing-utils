/*
 * ComparatorTableModel.java
 *
 * Created on 30. Juli 2003, 14:31
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

import java.util.Comparator;

import javax.swing.table.TableModel;

/**
 * This adds a method to <code>TableModel</code> that may be used to retrieve
 * <code>Comparator</code>s which should be used to sort the values of columns.
 * 
 * @see javax.swing.table.TableModel
 * @see java.util.Comparator
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public interface ComparatorTableModel extends TableModel {

	/**
	 * Returns a <code>Comparator</code> that can be used to compare two Objects
	 * in this <code>TableModel</code>s column <code>columnIndex</code>.
	 * 
	 * @param columnIndex
	 *            the column number we need a <code>Comparator</code> for.
	 * @return the <code>Comparator</code> or <code>null</code> if there is no
	 *         <code>Comparator</code> for this column.
	 * @see java.util.Comparator
	 */
	public Comparator<?> getComparator(int columnIndex);

}
