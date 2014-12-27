/*
 * ActionManager.java
 *
 * Created on 27. Mai 2003, 17:09
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
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class can be used for assistance with <code>javax.swing.Actions</code>
 * to create Actions that forward all incoming ActionEvents to one single or
 * multiple ActionListeners. It also can be used as a general container for
 * Actions and will also enable forwarding for any <code>ForwardingAction</code>
 * that is added
 * 
 * @see javax.swing.Action
 * @see java.awt.event.ActionListener
 * @see de.tkuhn.swing.actions.ForwardingAction
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class ActionManager extends HashMap<Object, Action> implements
		Map<Object, Action> {

	private static final long serialVersionUID = 1L;

	/** Actions are stored in the Map and in this list to preserve order */
	List<Action> actionList = new ArrayList<Action>();

	/** Inner class that provides the Actions created by the Manager */
	private class ActionForward extends AbstractAction {

		private static final long serialVersionUID = 1L;

		/**
		 * Just forwards the ActionEvent to the Manager
		 * 
		 * @param actionEvent
		 *            the ActionEvent to be forwarded
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			forwardActionEvent(actionEvent);
		}

	}

	/** Used to forward events coming from <code>ForwardingAction</code>s */
	private ActionListener actionForwarder = new ActionForward();

	/**
	 * Forward an ActionEvent to all ActionListeners registered with this
	 * ActionManager
	 * 
	 * @param actionEvent
	 *            the event to be forwarded
	 */
	void forwardActionEvent(ActionEvent actionEvent) {
		Iterator<ActionListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			(iter.next()).actionPerformed(actionEvent);
		}
	}

	/** A Set containing all ActionListeners registered with this ActionManager */
	private Set<ActionListener> listeners = new HashSet<ActionListener>();

	/** Creates a new instance of ActionManager */
	public ActionManager() {
		super();
	}

	/**
	 * Creates a new instance of ActionManager and registers the ActionListener
	 * 
	 * @param listener
	 *            the ActionListener to be registered with the newly created
	 *            ActionManager
	 */
	public ActionManager(ActionListener listener) {
		super();
		addActionListener(listener);
	}

	/**
	 * Registers an ActionListener with this ActionManager
	 * 
	 * @param listener
	 *            the ActionListener to be registered with this ActionManager
	 */
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Unregister an ActionListener registered with this ActionManager
	 * 
	 * @param listener
	 *            the ActionListener to be unregistered
	 */
	public void removeActionListener(ActionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Create an new Action and associate it with the given object.
	 * 
	 * @param key
	 *            the key to create an Action for.
	 * @return the Action.
	 */
	public Action newAction(Object key) {
		if (key == null)
			throw new IllegalArgumentException("No legal key passed!");
		if (super.containsKey(key))
			throw new IllegalArgumentException(
					"Allready kontains a mapping for that key!");
		Action action = new ActionForward();
		super.put(key, action);
		actionList.add(action);
		return action;
	}

	/**
	 * Create an new Action and associate it with the given key. In addition the
	 * key is added to the Action using
	 * putValue(Action.ACTION_COMMAND_KEY,command)
	 * 
	 * @param command
	 *            the key to create an Action for.
	 * @return the Action.
	 */
	public Action newAction(String command) {
		if (command == null)
			throw new IllegalArgumentException(
					"No legal command string passed!");
		if (super.containsKey(command))
			throw new IllegalArgumentException(
					"Allready kontains a mapping for that key!");
		Action action = new ActionForward();
		action.putValue(Action.ACTION_COMMAND_KEY, command);
		super.put(command, action);
		actionList.add(action);
		return action;
	}

	/**
	 * Get the Action associated with the given key
	 * 
	 * @param key
	 *            the key to retrieve the Action for
	 * @return the Action or null if key is not mapped
	 */
	public Action getAction(Object key) {
		return (Action) get(key);
	}

	/**
	 * Remove the Action associated with the given key.
	 * 
	 * @param key
	 *            the key the Action is associated with
	 * @return the Action.
	 */
	public Action removeAction(Object key) {
		Action action = (Action) remove(key);
		if (action != null) {
			actionList.remove(action);
			if (action instanceof ForwardingAction)
				((ForwardingAction) action)
						.removeActionListener(actionForwarder);
		}
		return action;
	}

	/**
	 * Implementation of remove() in java.util.Map
	 * 
	 * @param key
	 *            the key of the Action you want to remove.
	 * @return the removed Action if key was found null otherwise.
	 */
	public Action remove(Object key) {
		return removeAction(key);
	}

	/**
	 * Get a Collection containing all Actions associated with this
	 * ActionManager
	 * 
	 * @return all Actions in this ActionManager
	 */
	public Collection<Action> getActions() {
		return Collections.unmodifiableCollection(actionList);
	}

	/**
	 * Allows users to put their own <code>Action</code>s in this
	 * <code>ActionManager</code>. <b>Caution: If an <code>Action</code> fires,
	 * that was not created by this <code>ActionManager</code> listeners of this
	 * manager are not notified of the event!</b> The <code>Action</code> to be
	 * put will be mapped with it's command String as key.
	 * 
	 * @param action
	 *            the <code>Action</code> to be mapped.
	 * @return previous action associated with specified key, or null if there
	 *         was no mapping for key.
	 * @throws IllegalArgumentException
	 *             if the command String of the action or the action itself is
	 *             <code>null</code>
	 */
	public Action putAction(Action action) throws IllegalArgumentException {
		if (action == null)
			throw new IllegalArgumentException(
					"You cannot put null Actions in an ActionManager!");
		String key = (String) action.getValue(Action.ACTION_COMMAND_KEY);
		if (key == null)
			throw new IllegalArgumentException(
					"This Action has a null command String!");
		return putAction(key, action);
	}

	/**
	 * Allows users to put their own <code>Action</code>s in this
	 * <code>ActionManager</code>. <b>Caution: If an <code>Action</code> fires,
	 * that was not created by this <code>ActionManager</code> listeners of this
	 * manager are not notified of the event!</b> The <code>Action</code> to be
	 * put will be mapped with the given key.
	 * 
	 * @param action
	 *            the <code>Action</code> to be mapped.
	 * @param key
	 *            the key to map the Action with.
	 * @return previous action associated with specified key, or null if there
	 *         was no mapping for key.
	 * @throws IllegalArgumentException
	 *             if the command String of the action is <code>null</code>
	 */
	public Action putAction(Object key, Action action)
			throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException(
					"The ActionManager does not accept null keys!");
		if (action == null)
			throw new IllegalArgumentException(
					"You cannot put null Actions in an ActionManager!");
		Action old = (Action) super.put(key, action);
		if (old != null)
			actionList.remove(old);
		if (old instanceof ForwardingAction)
			((ForwardingAction) action).removeActionListener(actionForwarder);
		actionList.add(action);
		if (action instanceof ForwardingAction)
			((ForwardingAction) action).addActionListener(actionForwarder);
		return old;
	}

	/**
	 * Implementation of <code>put(key,value)</code> in interface
	 * <code>java.util>Map</code>. Overrides <code>put(key,value)</code> in
	 * <code>java.util.HashMap</code> to deny the putting of non
	 * <code>Action</code> objects.
	 * 
	 * @param value
	 *            an <code>Action</code> to be mapped in this
	 *            <code>ActionManager</code>
	 * @param key
	 *            the key the <code>Action</code> should be mapped with
	 * @return previous action associated with specified key, or null if there
	 *         was no mapping for key.
	 * @throws UnsupportedOperationException
	 *             if the value given was not an instance of <code>Action</code>
	 * @throws IllegalArgumentException
	 *             if an Object other than <code>Action</code> was given as
	 *             value parameter.
	 */
	public Action put(Object key, Action value)
			throws UnsupportedOperationException, IllegalArgumentException {

		if (value == null)
			throw new IllegalArgumentException(
					"You cannot put null Actions in an ActionManager!");
		if (value instanceof Action) {
			return putAction(key, (Action) value);
		} else
			throw new UnsupportedOperationException(
					"You can not put own Objects other than Actions in an ActionManager!");
	}

	/**
	 * This method can be used to put all <code>Action</code>s in the given Map
	 * into this <cod>ActionManager</code>. Non <code>Action</code> objects are
	 * ignored as are any <code>Action</code>s that are associated with a
	 * <code>null</code> key. <b>Caution: If an <code>Action</code> fires, that
	 * was not created by this <code>ActionManager</code> listeners of this
	 * manager are not notified of the event!</b>
	 * 
	 * @param map
	 *            a map containing actions
	 */
	public void putAll(Map<?, ? extends Action> map) {
		Object key;
		Action action;
		for (Map.Entry<?, ? extends Action> entry : map.entrySet()) {
			key = entry.getKey();
			action = entry.getValue();
			if (key != null && action != null)
				put(key, action);
		}
	}

	/** Removes all mapped Actions from this Manager */
	public void clear() {
		for (Action action : actionList) {
			if (action instanceof ForwardingAction)
				((ForwardingAction) action)
						.removeActionListener(actionForwarder);
		}
		super.clear();
		actionList.clear();
	}

}
