/*
 * ActionUtils.java
 *
 * Created on 30. Mai 2003, 16:25
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

import java.util.Collection;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * This class is a repository for static functions that operate on
 * <code>Action</code>s and help using <code>Collection</code>s of
 * <code>Action</code>s.
 * 
 * @see javax.swing.Action
 * 
 * @author Tilmann Kuhn
 * @version 0.9.3
 */
public class ActionUtils {

	private static ActionComponentFactory toolBarComponentFactory = new DefaultToolBarComponentFactory();
	private static ActionComponentFactory menuComponentFactory = new DefaultMenuComponentFactory();

	/**
	 * Creates a new instance of ActionUtil. Private since this object is not
	 * really useful
	 */
	private ActionUtils() {
	}

	/**
	 * Registers the key shortcuts of all <code>Actions</code> objects in the
	 * given <code>Collection</code> with the given <code>JComponent</code>. To
	 * achieve that the values of <code>Action.ACTION_COMMAND_KEY</code> and
	 * <code>Action.ACCELERATOR_KEY</code> are queried for every
	 * <code>Action</code> Then the key-shortcut is mapped with the command
	 * String as identifier in the <code>InputMap</code> of the component and
	 * the identifier is mapped with the <code>Action</code> in the
	 * <code>ActionMap</code> of the component.
	 * 
	 * @param component
	 *            the <code>JComponent</code> to map the key shortcuts with.
	 * @param actions
	 *            a <code>Collection</code> that may contain some
	 *            <code>Action</code>s.
	 * @return the same component
	 */
	public static JComponent registerAllKeys(JComponent component,
			Collection<Action> actions) {
		InputMap imap = component.getInputMap();
		ActionMap amap = component.getActionMap();
		String command;
		KeyStroke key;
		for (Action action : actions) {
			command = (String) action.getValue(Action.ACTION_COMMAND_KEY);
			key = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
			if (command != null && key != null) {
				imap.put(key, command);
				amap.put(command, action);
			}
		}
		return component;
	}

	/**
	 * Adds all <code>Action</code> objects in the given <code>Collection</code>
	 * to the given <code>JToolBar</code>
	 * 
	 * @param toolBar
	 *            the toolbar to add the actions to
	 * @param actions
	 *            a <code>Collection</code> that may contain some
	 *            <code>Action</code>s.
	 * @return the same toolbar
	 */
	public static JToolBar addAll(JToolBar toolBar, Collection<Action> actions) {

		for (Action action : actions) {
			toolBar.add(toolBarComponentFactory.createActionComponent(action));
		}
		return toolBar;
	}

	/**
	 * Adds all <code>Action</code> objects in the given <code>Collection</code>
	 * to the given <code>JMenu</code>
	 * 
	 * @param menu
	 *            the menu to add the actions to
	 * @param actions
	 *            a <code>Collection</code> that may contain some
	 *            <code>Action</code>s.
	 * @return the same menu
	 */
	public static JMenu addAll(JMenu menu, Collection<Action> actions) {

		for (Action action : actions) {
			menu.add(menuComponentFactory.createActionComponent(action));
		}
		return menu;
	}

	/**
	 * Set the <code>ActionComponentFactory</code> used when adding
	 * <code>Action</code>s to a JToolBar.
	 * 
	 * @param factory
	 *            New value of property toolBarActionComponentFactory.
	 */
	public static void setToolBarActionComponentFactory(
			ActionComponentFactory factory) {
		toolBarComponentFactory = factory;
	}

	/**
	 * Set the <code>ActionComponentFactory</code> used when adding
	 * <code>Action</code>s to a JMenu.
	 * 
	 * @param factory
	 *            New value of property menuActionComponentFactory.
	 */
	public static void setMenuActionComponentFactory(
			ActionComponentFactory factory) {
		menuComponentFactory = factory;
	}

	/**
	 * Get the <code>ActionComponentFactory</code> usable for adding
	 * <code>Action</code>s to a JToolBar.
	 * 
	 * @return The ActionComponentFactory for JToolBars.
	 */
	public static ActionComponentFactory getToolBarActionComponentFactory() {
		return toolBarComponentFactory;
	}

	/**
	 * Get the <code>ActionComponentFactory</code> usable for adding
	 * <code>Action</code>s to a JMenu.
	 * 
	 * @return The ActionComponentFactory for Menus.
	 */
	public static ActionComponentFactory getMenuActionComponentFactory() {
		return menuComponentFactory;
	}

}
