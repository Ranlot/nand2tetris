// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

@white
M = 0
@black
M = -1

@RESETCURSOR
0 ; JMP

(LOOP)
@KBD
D = M
@SETBLACK
D ; JNE
@SETWHITE
0 ; JMP

(DRAW)
@color
D = M

@cursorPointer 
A = M 
M = D 

@cursorPointer
M = M + 1

@KBD //end of screen memory address is beginning of keyboard address space
D = A
@cursorPointer
D = D - M

@RESETCURSOR
D ; JLE
@LOOP
0 ; JMP

(SETWHITE)
@white
D = M
@color
M = D
@DRAW
0 ; JMP

(SETBLACK)
@black
D = M
@color
M = D
@DRAW
0 ; JMP

(RESETCURSOR) 
@SCREEN
D = A
@cursorPointer
M = D 

@LOOP
0 ; JMP

