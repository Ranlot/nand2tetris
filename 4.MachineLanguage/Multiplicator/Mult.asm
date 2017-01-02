// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@R0
D = M
@a
M = D

@R1
D = M
@b
M = D

@R2
M = 0

@counter
M = 0

@sum
M = 0

(LOOP)

@a
D = M
@counter
D = D - M

@END
D; JEQ

@b
D = M
@sum
M = D + M

@counter
M = M + 1

@LOOP
0; JMP

(END)

@sum
D = M
@R2
M = D

@END
0; JMP

