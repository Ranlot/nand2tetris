/**
 * The complete address space of the Hack computer's memory,
 * including RAM and memory-mapped I/O. 
 * The chip facilitates read and write operations, as follows:
 *     Read:  out(t) = Memory[address(t)](t)
 *     Write: if load(t-1) then Memory[address(t-1)](t) = in(t-1)
 * In words: the chip always outputs the value stored at the memory 
 * location specified by address. If load==1, the in value is loaded 
 * into the memory location specified by address. This value becomes 
 * available through the out output from the next time step onward.
 * Address space rules:
 * Only the upper 16K+8K+1 words of the Memory chip are used. 
 * Access to address>0x6000 is invalid. Access to any address in 
 * the range 0x4000-0x5FFF results in accessing the screen memory 
 * map. Access to address 0x6000 results in accessing the keyboard 
 * memory map. 
 */

CHIP Memory {
    IN in[16], load, address[15];
    OUT out[16];

    PARTS:
    DMux4Way(in=load, sel=address[13..14], a=dummyRAM1, b=dummyRAM2, c=loadScreen, d=keyboard);
    Or(a=dummyRAM1, b=dummyRAM2, out=loadRAM);

    RAM16K(in=in, load=loadRAM, address=address[0..13], out=outRAM);
    Screen(in=in, load=loadScreen, address=address[0..12], out=outScreen);
    Keyboard(out=outKeyboard);

    Mux4Way16(a=outRAM, b=outRAM, c=outScreen, d=outKeyboard, sel=address[13..14], out=out);
}