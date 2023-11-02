# Chord Visualization

As of now, this is a GUI that allows user to create, play, and apply operations to chords on a Tonnetz. 



## How does the visualization work

There are three forms of visualization in this program.

- The panel on the right-hand side shows a Tonnetz structure. In this structure, two notes are connected when they form m3, M3 or P5 interval. At current stage all nodes in this panel represent pitches rather than pitch classes. Visit https://en.wikipedia.org/wiki/Tonnetz to learn more.

- The panel on the left-hand side shows a circle of fifths. Each node here represents a pitch class, and forms P5 with any of their neighbors. Learn more on https://en.wikipedia.org/wiki/Circle_of_fifths. The left-hand-side panel also shows the structure of current chord. 

![App](examples/App.png)

- By changing the setting you can also check out the dual graph of the Tonnetz. Here red nodes represents major chords, and blue ones represents minor chord. Two chords are connected when they share 2 common notes.

![DualNet](examples/DualNet.png)

## Usage

Left click on notes to press. Use the control panel to apply operations to the pressed notes. These operations are:

- Move by P5, M3, m3 interval. All notes will go up or down by certain interval after this operation.
- Rotation. All notes will rotate by 60 degree according to rotation center after this operation. Right click to set rotation center. Since Tonnetz is topologically a torus, this operation is only well-defined locally. It can behave in unexpected way when your note group is too large or when it touches right or left boundary. This operation is currently not available for dual-Tonnetz.

You can also change instrument in the settings menu. All instruments in general midi are supported.

The file menu currently does nothing, do not bother clicking it.