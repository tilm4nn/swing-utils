/*
 * AbstractTreeModel.java
 *
 * Created on 30. Mai 2003, 18:16
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

package de.tkuhn.swing.tree;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * An abstract implementation of <code>TreeModel</code> that provides basic
 * implementations for some of the functions in this interface and can help
 * implementing own <code>TreeModel</code>s not using
 * <code>DefaultMutableTreeNode</code>s as <code>DefautlTreeModel</code> does.
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public abstract class AbstractTreeModel implements TreeModel {

	/** A set containing TreeModelListeners */
	protected Set<TreeModelListener> listeners = new HashSet<TreeModelListener>();

	/** Creates a new instance of AbstractTreeModel */
	public AbstractTreeModel() {
	}

	/**
	 * Adds a listener for the <code>TreeModelEvent</code> posted after the tree
	 * changes.
	 * 
	 * @param tml
	 *            the listener to add
	 * @see #removeTreeModelListener
	 */
	public void addTreeModelListener(TreeModelListener tml) {
		listeners.add(tml);
	}

	/**
	 * Returns the child of <I>parent</I> at index <I>index</I> in the parent's
	 * child array. <I>parent</I> must be a node previously obtained from this
	 * data source. This should not return null if <i>index</i> is a valid index
	 * for <i>parent</i> (that is <i>index</i> &gt;= 0 &amp;&amp; <i>index</i> &lt;
	 * getChildCount(<i>parent</i>)).
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the child of <I>parent</I> at index <I>index</I>
	 */
	public abstract Object getChild(Object parent, int index);

	/**
	 * Returns the number of children of <I>parent</I>. Returns 0 if the node is
	 * a leaf or if it has no children. <I>parent</I> must be a node previously
	 * obtained from this data source.
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the number of children of the node <I>parent</I>
	 */
	public abstract int getChildCount(Object parent);

	/**
	 * Returns the index of child in parent. If either the parent or child is
	 * <code>null</code>, returns -1. This functionality is emulated using
	 * <code>getChildCount(parent)</code> and
	 * <code>getChild(parent,index)</code>. So it is not supposed to be fast ;-)
	 * 
	 * @param parent
	 *            a note in the tree, obtained from this data source
	 * @param child
	 *            the node we are interested in
	 * @return the index of the child in the parent, or -1 if either the parent
	 *         or the child is <code>null</code>
	 */

	public int getIndexOfChild(Object parent, Object child) {
		if (parent != null && child != null)
			for (int i = getChildCount(parent) - 1; i >= 0; i--) {
				if (getChild(parent, i) == child)
					return i;
			}
		return -1;
	}

	/**
	 * Returns the root of the tree. Is not implemented here.
	 * 
	 * @return the root of the tree
	 */
	public abstract Object getRoot();

	/**
	 * Returns whether the specified node is a leaf node. This implementation
	 * simply checks if <code>getChildCount(node) == 0</code>
	 * 
	 * @param node
	 *            the node to check
	 * @return true if the node is a leaf node
	 */
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	/**
	 * Removes a listener previously added with
	 * <code>addTreeModelListener</code>.
	 * 
	 * @see #addTreeModelListener
	 * @param tml
	 *            the listener to remove
	 */
	public void removeTreeModelListener(TreeModelListener tml) {
		listeners.remove(tml);
	}

	/**
	 * Messaged when the user has altered the value for the item identified by
	 * <code>path</code> to <code>newValue</code>. If <code>newValue</code>
	 * signifies a truly new value the model should post a
	 * <code>treeNodesChanged</code> event.
	 * 
	 * This is an empty implementation that does nothing.
	 * 
	 * @param path
	 *            path to the node that the user has altered
	 * @param newValue
	 *            the new value from the TreeCellEditor
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node being changed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the changed elements
	 * @param children
	 *            the changed elements
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesChanged(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where new elements are being inserted
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the new elements
	 * @param children
	 *            the new elements
	 */
	protected void fireTreeNodesInserted(Object source, Object[] path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesInserted(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where elements are being removed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the removed elements
	 * @param children
	 *            the removed elements
	 */
	protected void fireTreeNodesRemoved(Object source, Object[] path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesRemoved(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where the tree model has changed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the affected elements
	 * @param children
	 *            the affected elements
	 */
	protected void fireTreeStructureChanged(Object source, Object[] path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeStructureChanged(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node being changed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the changed elements
	 * @param children
	 *            the changed elements
	 */
	protected void fireTreeNodesChanged(Object source, TreePath path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesChanged(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where new elements are being inserted
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the new elements
	 * @param children
	 *            the new elements
	 */
	protected void fireTreeNodesInserted(Object source, TreePath path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesInserted(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where elements are being removed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the removed elements
	 * @param children
	 *            the removed elements
	 */
	protected void fireTreeNodesRemoved(Object source, TreePath path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeNodesRemoved(tme);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where the tree model has changed
	 * @param path
	 *            the path to the root node
	 * @param childIndicies
	 *            the indices of the affected elements
	 * @param children
	 *            the affected elements
	 */
	protected void fireTreeStructureChanged(Object source, TreePath path,
			int[] childIndicies, Object[] children) {
		TreeModelEvent tme = new TreeModelEvent(source, path, childIndicies,
				children);
		fireTreeStructureChanged(tme);
	}

	/**
	 * Fire a <code>treeNodesChanged(tme)</code> for all listeners registered
	 * with this <code>TreeModel</code>.
	 * 
	 * @param tme
	 *            the <code>TreeModelEvent</code> to be passed
	 */
	protected void fireTreeNodesChanged(TreeModelEvent tme) {
		for (TreeModelListener listener : listeners) {
			listener.treeNodesChanged(tme);
		}
	}

	/**
	 * Fire a <code>treeNodesInserted(tme)</code> for all listeners registered
	 * with this <code>TreeModel</code>.
	 * 
	 * @param tme
	 *            the <code>TreeModelEvent</code> to be passed
	 */
	protected void fireTreeNodesInserted(TreeModelEvent tme) {
		for (TreeModelListener listener : listeners) {
			listener.treeNodesInserted(tme);
		}
	}

	/**
	 * Fire a <code>treeNodesRemoved(tme)</code> for all listeners registered
	 * with this <code>TreeModel</code>.
	 * 
	 * @param tme
	 *            the <code>TreeModelEvent</code> to be passed
	 */
	protected void fireTreeNodesRemoved(TreeModelEvent tme) {
		for (TreeModelListener listener : listeners) {
			listener.treeNodesRemoved(tme);
		}
	}

	/**
	 * Fire a <code>treeStructureChanged(tme)</code> for all listeners
	 * registered with this <code>TreeModel</code>.
	 * 
	 * @param tme
	 *            the <code>TreeModelEvent</code> to be passed
	 */
	protected void fireTreeStructureChanged(TreeModelEvent tme) {
		for (TreeModelListener listener : listeners) {
			listener.treeStructureChanged(tme);
		}
	}

}
