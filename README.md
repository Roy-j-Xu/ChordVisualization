# Chord Visualization

As of now, this is a GUI that allows user to create, play, and apply operations to chords on a Tonnetz. 



## How does the visualization work

There are two forms of visualization in this program.

The panel on the right-hand side shows a Tonnetz structure. In this structure, two notes are connected if they form m3, M3 or P5 interval. At current stage all nodes in this panel represents pitches rather than pitch classes. To learn more about Tonnetz, you can visit https://en.wikipedia.org/wiki/Tonnetz.

The panel on the left-hand side shows a circle of fifths. Each node here represents a pitch class, and forms P5 with any of their neighbors. Learn more on https://en.wikipedia.org/wiki/Circle_of_fifths.

The left-hand-side panel also shows the structure of current chord.

![App](examples/App.png)



## Usage

Left click on notes to press. Right click to set rotation center. Note that rotation is not a well defined operation on the Tonnetz, so it can behave in unexpected way. You can also change instrument in the settings menu. The file menu currently does nothing, do not bother clicking it.