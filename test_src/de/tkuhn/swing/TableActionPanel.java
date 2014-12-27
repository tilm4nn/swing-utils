/*
 * TabelPanel.java
 *
 * Created on 14. Mai 2003, 20:42
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

package de.tkuhn.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import de.tkuhn.swing.actions.ActionManager;
import de.tkuhn.swing.actions.ActionUtils;
import de.tkuhn.swing.actions.BooleanAction;
import de.tkuhn.swing.actions.BooleanStateAction;

/**
 * This is a demofile showing how to use JUserFriendlyTable, ActionManager and
 * ActionUtils
 * 
 * @author Tilmann Kuhn
 */
public class TableActionPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** Used to demonstrate BooleanAction */
	private BooleanAction preserveAction;

	/** Creates new form TabelPanel */
	public TableActionPanel() {
		initComponents();
		saveState();
		table.setModel(createModel());

		// Simple demo how to use ActionManager to create Actions
		ActionManager manager = new ActionManager(this);
		Action action = manager.newAction("save");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl S"));
		action.putValue(Action.SHORT_DESCRIPTION,
				"Save current table state to disk.");
		action.putValue(Action.NAME, "Save State");

		action = manager.newAction("load");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl L"));
		action.putValue(Action.SHORT_DESCRIPTION, "Load table state from disk.");
		action.putValue(Action.NAME, "Load State");

		action = manager.newAction("model");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl M"));
		action.putValue(Action.SHORT_DESCRIPTION,
				"Creates and sets a new TableModel for the table.");
		action.putValue(Action.NAME, "New Model");

		action = manager.newAction("balance");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl B"));
		action.putValue(Action.SHORT_DESCRIPTION,
				"Balance column width based on content.");
		action.putValue(Action.NAME, "Balance Columns");

		// Demo for the use of BooleanAction
		preserveAction = new BooleanStateAction(true);
		preserveAction.putValue(Action.ACTION_COMMAND_KEY, "preserve");
		preserveAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl P"));
		preserveAction.putValue(Action.SHORT_DESCRIPTION,
				"Preserve column state at \"New Model\".");
		preserveAction.putValue(Action.NAME, "Preserve State");
		manager.putAction(preserveAction);

		// Registers the Action's keys and adds them to a JToolBar using
		// ActionUtils
		ActionUtils.registerAllKeys(table, manager.getActions());
		ActionUtils.addAll(toolBar, manager.getActions());
	}

	/** Processes the events of the Actions */
	public void actionPerformed(ActionEvent ae) {
		String com = ae.getActionCommand();
		if ("save".equals(com)) {

			saveState();

		} else if ("load".equals(com)) {

			loadState();

		} else if ("model".equals(com)) {

			if (!preserveAction.isSelected())
				table.setModel(null);
			table.setModel(createModel());

		} else if ("balance".equals(com)) {

			table.balanceColumns();
		}
	}

	/** Save the table state */
	private void saveState() {
		try {
			Object state = table.getState();
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(
					"tablestate.xml"));
			encoder.writeObject(state);
			encoder.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/** Load the table state */
	private void loadState() {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(
					"tablestate.xml"));
			Object state = decoder.readObject();
			decoder.close();
			table.setState(state);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/** Just creates a TableModel for testing */
	private TableModel createModel() {
		DefaultTableModel model = new DefaultTableModel();
		Object[] data = { "red", "green", "blue", "black", "orange", "yellow",
				"brown" };
		model.addColumn("Farbe", data);
		Object[] data1 = { new Integer(200), new Integer(100),
				new Integer(700), new Integer(400), new Integer(900),
				new Integer(300), new Integer(500) };
		model.addColumn("Zahl", data1);
		Object[] data2 = { new Date(), new Date(), new Date(), new Date(),
				new Date(), new Date(), new Date() };
		model.addColumn("Datum", data2);
		return model;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		jScrollPane = new javax.swing.JScrollPane();
		table = new de.tkuhn.swing.table.JUserFriendlyTable();
		toolBar = new javax.swing.JToolBar();

		setLayout(new java.awt.BorderLayout());

		jScrollPane.setViewportView(table);

		add(jScrollPane, java.awt.BorderLayout.CENTER);

		add(toolBar, java.awt.BorderLayout.NORTH);

	}// GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private de.tkuhn.swing.table.JUserFriendlyTable table;
	private javax.swing.JToolBar toolBar;
	private javax.swing.JScrollPane jScrollPane;
	// End of variables declaration//GEN-END:variables

}
