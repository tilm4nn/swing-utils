                   README: SwingUtils 0.9.3 Release
                   --------------------------------
                             August 2003


Contents:

* Installation notes
* Target audience
* Changes since the 0.9 release
* Contents of the release
* How to get started
* How to build
* How to reach me 
* Limitations and bugs
* Copyright


INSTALLATION NOTES

Just put the jar file into your classpath.



TARGET AUDIENCE

Java Swing developers.



CHANGES SINCE THE 0.9 RELEASE

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



HOW TO REACH ME

The e.ways to reach me:
http://www.tkuhn.de
swingutils@tkuhn.de



LIMITATIONS AND BUGS

Not known so far. Please report your experiences to swingutils@tkuhn.de




THE MIT LICENSE

Copyright (C) 2003 
Tilmann Kuhn           Gildestr. 34
http://www.tkuhn.de    76149 Karlsruhe
swingutils@tkuhn.de    Germany

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.