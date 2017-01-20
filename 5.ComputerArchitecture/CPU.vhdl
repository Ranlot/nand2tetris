/**
 * The Hack CPU (Central Processing unit), consisting of an ALU,
 * two registers named A and D, and a program counter named PC.
 * The CPU is designed to fetch and execute instructions written in 
 * the Hack machine language. In particular, functions as follows:
 * Executes the inputted instruction according to the Hack machine 
 * language specification. The D and A in the language specification
 * refer to CPU-resident registers, while M refers to the external
 * memory location addressed by A, i.e. to Memory[A]. The inM input 
 * holds the value of this location. If the current instruction needs 
 * to write a value to M, the value is placed in outM, the address 
 * of the target location is placed in the addressM output, and the 
 * writeM control bit is asserted. (When writeM==0, any value may 
 * appear in outM). The outM and writeM outputs are combinational: 
 * they are affected instantaneously by the execution of the current 
 * instruction. The addressM and pc outputs are clocked: although they 
 * are affected by the execution of the current instruction, they commit 
 * to their new values only in the next time step. If reset==1 then the 
 * CPU jumps to address 0 (i.e. pc is set to 0 in next time step) rather 
 * than to the address resulting from executing the current instruction. 
 */

CHIP CPU {

    IN  inM[16],         // M = contents of RAM[A]
        instruction[16], // Instruction for execution
        reset;

    OUT outM[16],        // M value output
        writeM,          // Write to M? 
        addressM[15],    // Address in data memory (of M)
        pc[15];          // address of next instruction

    PARTS:
    // ALU result can be saved of A-register, D-register and M-register = RAM[A]
    // Both A and D are CPU-resident but M-register is external
    // This is why writeM is part of the out interface (and not A-D which must be dealt with within the CPU itself)

    // A-instruction ; 15-bit address
    // 0vvv vvvv vvvv vvvv

    // C-instruction
    // 111a c1c2c3c4 c5c6d1d2 d3j1j2j3 
    // The instruction is the same for A/M but: A if a = 0 else M

    // 1) decode instruction type

    Not(in=instruction[15], out=Ainstruction); 
    Not(in=Ainstruction, out=Cinstruction); 

    // 2) set value of A register; can change because of 2 reasons:
    //   i)  C-instruction with save to A-register destination; set ALUtoA to 1
    //   ii) A-instruction

    And(a=Cinstruction, b=instruction[5], out=ALUtoA);  // i)
    Or(a=Ainstruction, b=ALUtoA, out=loadA); // ii) (if ALUtoA = 1 we have a C-instruction, so we should never get to Or(a=1, b=1, ...) as it's mutually exclusive...)

    Mux16(a=instruction, b=ALUout, sel=ALUtoA, out=AregisterIn); // select which value to put in A-register
    ARegister(in=AregisterIn, load=loadA, out=AregisterOut); // clocked

    // http://nand2tetris-questions-and-answers-forum.32033.n3.nabble.com/De-bus-pins-td2802816.html
    // We consider only the C-instrucion, in case of a A-instruction, the same code will apply and will lead to random input to ALU
    // Only C-instructions can mutate memory registers...

    Mux16(a=AregisterOut, b=inM, sel=instruction[12], out=AMswitch); // input to ALU: select A-register or M-register based on value of a-bit

    //3) Set the value of D register; can only change in case of a Cinstruction and that we decide to save ALU result to Dregister

    And(a=Cinstruction, b=instruction[4], out=loadD);
    DRegister(in=ALUout, load=loadD, out=DregisterOut);

    // Carry out the C-instruction: software (binary assembly code) meets hardware!!!
    // zx = c1 ; nx = c2 ; zy = c3 ; ny = c4 ; f = c5 ; no = c6

    ALU(x=DregisterOut, y=AMswitch, zx=instruction[11], nx=instruction[10], zy=instruction[9], ny=instruction[8], f=instruction[7], no=instruction[6], out=ALUout, zr=ALUzeroOut, ng=ALUnegativeOut);

    // Decide to write ALU output into main memory M = RAM[A]?

    Or16(a=false, b=AregisterOut, out[0..14]=addressM);  // Copy the address content of A-register unconditionnally
    Or16(a=false, b=ALUout, out=outM);			 // Copy the result of the ALU unconditionnally
    And(a=Cinstruction, b=instruction[3], out=writeM);   // Set writeM to true in case of C-instruction and d3=1 (machine language specs)

    And(a=ALUzeroOut, b=instruction[1], out=JEQ);
    And(a=ALUnegativeOut, b=instruction[2], out=JLT);
    Or(a=ALUzeroOut, b=ALUnegativeOut, out=isZeroOrNeg);
    Not(in=isZeroOrNeg, out=isPositive);
    And(a=isPositive, b=instruction[0], out=JGT);

    Or(a=JEQ, b=JLT, out=jumpLessThanOrZero);
    Or(a=jumpLessThanOrZero, b=JGT, out=jumpToA);

    // 0 ; JMP
    // Unconditional jumps are accompanied by compute 0 in ALU

    And(a=Cinstruction, b=jumpToA, out=loadPC);
    Not(in=loadPC, out=isRegularIncrement);

    // Address in A-register is only 15-bit; program counter

    PC(in=AregisterOut, load=loadPC, inc=isRegularIncrement, reset=reset, out[0..14]=pc);

}
