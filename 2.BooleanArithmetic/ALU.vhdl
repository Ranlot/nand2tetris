/**
 * The ALU (Arithmetic Logic Unit).
 * Computes one of the following functions:
 * x+y, x-y, y-x, 0, 1, -1, x, y, -x, -y, !x, !y,
 * x+1, y+1, x-1, y-1, x&y, x|y on two 16-bit inputs, 
 * according to 6 input bits denoted zx,nx,zy,ny,f,no.
 * In addition, the ALU computes two 1-bit outputs:
 * if the ALU output == 0, zr is set to 1; otherwise zr is set to 0;
 * if the ALU output < 0, ng is set to 1; otherwise ng is set to 0.
 */

// if (zx == 1) set x = 0
// if (nx == 1) set x = !x
// if (zy == 1) set y = 0
// if (ny == 1) set y = !y
// if (f == 1)  set out = x + y
// if (f == 0)  set out = x & y
// if (no == 1) set out = !out
// if (out == 0) set zr = 1
// if (out < 0) set ng = 1

CHIP ALU {
    IN  
        x[16], y[16],
        zx, nx,
	zy, ny,
        f,
        no;
    OUT 
        out[16],
        zr, 
        ng;

    PARTS:

    Not16(in=x, out=negatedx);
    Not16(in=y, out=negatedy);

    Mux4Way16(a=x, b=false, c=negatedx, d=true, sel[0]=zx, sel[1]=nx, out=finalx);
    Mux4Way16(a=y, b=false, c=negatedy, d=true, sel[0]=zy, sel[1]=ny, out=finaly);

    Add16(a=finalx, b=finaly, out=added);
    And16(a=finalx, b=finaly, out=anded);

    Mux16(a=anded, b=added, sel=f, out=result);

    Not16(in=result, out=negResult);

    Mux16(a=result, b=negResult, sel=no, out=out, out[0..7]=firstHalf, out[8..15]=secondHalf, out[15]=msb);

    // determine if the entire out is zero
    Or8Way(in=firstHalf, out=anyFirstHalf);
    Or8Way(in=secondHalf, out=anySecondHalf);
    Or(a=anyFirstHalf, b=anySecondHalf, out=anyAtAll);
    Xor(a=anyAtAll, b=true, out=zr);

    // determine if the first bit is a 1, and thus negative
    And(a=msb, b=true, out=ng);
}
