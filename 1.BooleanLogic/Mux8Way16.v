/**
 * 8-way 16-bit multiplexor:
 * out = a if sel == 000
 *       b if sel == 001
 *       etc.
 *       h if sel == 111
 */

CHIP Mux8Way16 {
    IN a[16], b[16], c[16], d[16],
       e[16], f[16], g[16], h[16],
       sel[3];
    OUT out[16];

    PARTS:
    
    Mux4Way16(a=a, b=b, c=c, d=d, sel= sel[0..1], out=abcd); 
    Mux4Way16(a=e, b=f, c=g, d=h, sel= sel[0..1], out=efgh);

    Mux16(a=abcd, b=efgh, sel=sel[2], out=out);
}
