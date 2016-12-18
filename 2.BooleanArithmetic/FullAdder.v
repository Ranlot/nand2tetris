/**
 * Computes the sum of three bits.
 */

CHIP FullAdder {
    IN a, b, c;  // 1-bit inputs
    OUT sum,     // Right bit of a + b + c
        carry;   // Left bit of a + b + c

    PARTS:
    HalfAdder(a=a, b=b, sum=sumab, carry=carryab); 
    HalfAdder(a=sumab, b=c, sum=sum, carry=carryabc);
    Or(a=carryab, b=carryabc, out=carry);
}
