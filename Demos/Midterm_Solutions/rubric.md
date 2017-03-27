# Rubric - 20 marks overall

Note:  Students will implement this project in several different ways.  While we can assign some modest marks to things like coding style, documentation, I don't think it should really be a major factor.  Time pressure may make students take shortcuts they otherwise would not necessarily have taken.

A good example of this is that very few students will probably use an interface, as I have done, to handle modifying the UI from the controller.  This is ok.


## Exit Functionality - 1.0 mark total

0.5 marks - adding function to the controller to handle the menu item select event
0.0 marks - automatically saving the current file before exit is optional
0.5 marks - exit from the program


## Open Folder Functionality - 6.0 marks total

0.5 marks - adding function to the controller to handle the menu item select event
2.5 marks - traverse File (using listFiles(), for example) to discover directory structure
2.5 marks - using directory structure, create TreeViewItem hierarchy
0.5 marks - add TreeViewItem hierarchy as data source for TreeView


## File Load Functionality - 5.5 marks total

2.5 marks - add a changed listener to the TreeView to recognize when a new file item is selected
2.5 marks - when a new file item is selected, load its contents into a string
0.5 marks - add the contents string to the TextArea


## Save Functionality - 4.5 marks total

0.5 marks - adding function to the controller to handle the menu item select event
0.5 marks - put the contents of the TextArea into a string
2.5 marks - save the string to the correct file
1.0 mark  - update the user interface to reflect that the file has not been modified


## File Modified Functionality - 3.0 marks total

2.0 marks - add a changed listener to the TextArea to recognize when the content has been modified
1.0 mark  - update the user interface to reflect that the file has been modified
