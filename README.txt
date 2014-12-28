SwingUtils Java API
-------------------
Copyright (C) 2014 Tilmann Kuhn
See LICENSE.txt for details of the MIT license.

Contents of this readme:

* Purpose
* Target audience
* Installation notes
* Change history
* Contents of the release
* How to get started
* How to build
* How to contact me 
* Limitations and bugs



PURPOSE

A Java API that adds some reusable components to the Java Swing API. It
contains an extension of the JTable providing sort by column, hiding/
showing of columns, auto balance column width, save and restore of table
state (sort order, column width, column position, column visibility). In
addition there is an ActionManager that helps in creating and managing
Action objects, make Actions visible in the GUI and react to user
interaction.



TARGET AUDIENCE

Java Swing developers.



INSTALLATION NOTES

Just put the jar file into your classpath.



CHANGE HISTORY

0.9.5  - [Changed] README, LICENSE
0.9.4  - [Changed] README, build script, JavaDoc
0.9.3  - [Changed] JUserFriendlyTable
          Fixed NullPointerException in blanaceColumns()
          balanceColumns() does not do anything if columnModel or tableHeader are null   
       - [Changed] JUserFriendlyTable
          Added Property "statePreserving" in JUserFriendlyTable which determines if
          the table in case of a model change should preserve the column state if
          possible or just balance columns.
       - [Changed] ActionUtils
          Added methods for direct retrieval of the used ActionComponentFactories.

0.9.2  - [Changed] RowSortingTableModel
          Moved reusable components out of RowSortingTableModel.
       - [Added] DefaultComparator
          that can compare any two Objects and
       - [Added] OrderComparator
          a Comparator wrapper that can change the order of comparisons.
       - [Moved] ActionManager and ActionUtils
          into the package actions
       - [Changed] ActionManager
          It now supports handling and and collecting
          ActionEvents from the Action subclass ForwardingAction.
       - [Added] ForwardingAction
          Interface of an Action that does also forward
          its events to listeners.
       - [Added] DelegatingAction
          A simple implementation of ForwardingAction
       - [Added] ActionComponentFactory: An abstract factory, that can be used to
          generate GUI-Widgets for Actions to display them.
       - [Added] DefaultMenuComponentFactory
          This factory generates Widgets for menus.
       - [Added] DefaultToolBarComponentFactory
          This factory generates Widgets for toolbars.
       - [Changed] ActionUtils
          DefaultMenuComponentFactory and DefaultToolBarComponentFactory are
          now used to create Widgets for Actions.
       - [Added] BooleanAction
          It is an Interface of an Action that has a boolean state.
       - [Added] BooleanPreferenceAction
          It is a BooleanAction that draws its state from Preferences
       - [Added] BooleanStateAction
          It is a BooleanAction that keeps its state as a java bean property
       - [Added] BooleanActionAbstractButtonMediator
          Link between BooleanAction and AbstractButton. Updates the button
          if the state of the Action changes.
       - [Added] BooleanActionCheckBoxMenuItemMediator
          Link between BooleanAction and JCheckBoxMenuItem. Updates the menu
          item if the state of the Action changes.
       - [Added] CascadingComboBoxModel
          An implementation of ComboBoxModel that easily allows to create
          a row of comboboxes where contents of one combobox depend on the
          selected item in another combobox.
       - [Fixed] DefaultComparator
          Severe logical bug in comparing Numbers.
       - [Fixed] ColumnStateTableColumnModel
          A bug leading to ArrayIndexOutOfBoundsException while unhiding
          Columns.
       - [Changed] ColumnStateTableColumnModel
          Added method for unhiding all Columns at once.
       - [Changed] RowSortingTableModel
          Added method computing original row number for a given view
          row number.

0.9.1  - [Changed] JUserFriendlyTable,
                   RowSortingTableModel,
                   ColumnStateTableColumnModel
          All states in these classes can now be saved and loaded with
          java.beans.XMLEncoder/java.beans.XMLDecoder.
       - [Changed] JUserFriendlyTable
          It can now compute and set optimal column widthes using
          balanceColumns(). It does this automaticly when setting the first 
          TableModel.
       - [Fixed] RowSortingTableModel
          Cleaned some bugs leading to ArrayIndexOutOfBoundsExceptions.

0.9    - First release.



CONTENTS OF THE RELEASE

README.txt         this file
LICENSE.txt        license of SwingUtils
build.xml          ant build script

docs/              contains documentation about SwingUtils
src/               contains the SwingUtils source files
lib/               contains libraries SwingUtils depends on, none in the moment
test_src/          contains demo files for the use of SwingUtils



HOW TO GET STARTED

I recommend taking a look at the source demos in test_src/ and at the api-docs
in docs/api/



HOW TO BUILD

I recommend usage of the ant building environment (http://ant.apache.org/)
together with the provided build script (build.xml). An Eclipse project file
is also included.



HOW TO CONTACT ME

http://www.tkuhn.de
swingutils@tkuhn.de



LIMITATIONS AND BUGS

Not known so far. Please report your experiences to me.
