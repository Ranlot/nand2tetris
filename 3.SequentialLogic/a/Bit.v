/**
 * 1-bit register:
 * If load[t] == 1 then out[t+1] = in[t]
 *                 else out does not change (out[t+1] = out[t])
 */

CHIP Bit {
    IN in, load;
    OUT out;

    PARTS:
    Mux(a=previousState, b=in, sel=load, out=transientState);
    DFF(in=transientState, out=previousState, out=out);
}
