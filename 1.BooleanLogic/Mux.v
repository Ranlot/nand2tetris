/** 
 * Multiplexor:
 * out = a if sel == 0
 *       b otherwise
 */

CHIP Mux {
    IN a, b, sel;
    OUT out;

    PARTS:
    Nand(a=b, b=sel, out=n1);
    Nand(a=sel, b=sel, out=n2);
    Nand(a=n2, b=a, out=n3);
    Nand(a=n1, b=n3, out=out);
}
