package HackAssembler.Utils;

import HackAssembler.CPUinstructions.AddressInstruction;
import HackAssembler.CPUinstructions.CPUinstruction;
import HackAssembler.CPUinstructions.ComputeInstruction;

import java.util.ArrayList;
import java.util.List;

import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.*;

public class ASMline {

    private String ASMline;

    public ASMline(String string) {
        this.ASMline = string;
    }

    private boolean isLabelInstruction(String string) {
        return string.contains("(") && string.contains(")");
    }

    private static boolean isAddressInstruction(String string) {
        return string.startsWith(shtrudelSymbol);
    }

    boolean isAddressInstruction() {
        return isAddressInstruction(ASMline);
    }

    boolean isLabelInstruction() {
        return isLabelInstruction(ASMline);
    }

    String removeParenthesis() {
        return ASMline.replace("(", "").replace(")", "");
    }

    public String extractMemorySymbol() {
        return ASMline.split(shtrudelSymbol)[1];
    }

    public ASMline sanitizeString() {
        String symbolLessString = ASMline.split(commentSymbol)[0];
        String sanitizedString = symbolLessString.replaceAll(emptySpace, byNothing);
        return new ASMline(sanitizedString);
    }

    public boolean isNotEmpty() {
        return ! ASMline.isEmpty();
    }

    public boolean isCPUinstruction() {
        return ! isLabelInstruction(ASMline);
    }

    public CPUinstruction makeCPUinstruction() {
        return isAddressInstruction(ASMline) ?
                new AddressInstruction(new ASMline(ASMline)) :
                new ComputeInstruction(new ASMline(ASMline));
    }

    public boolean isJumpInstruction() {
        return ASMline.contains(jumpSeparator);
    }

    public List<String> instructionSplitter(String splitCharacter) {

        List<String> res = new ArrayList<>();

        String[] splitJump = ASMline.split(splitCharacter);
        String lhs = splitJump[0];
        String rhs = splitJump[1];

        res.add(lhs);
        res.add(rhs);

        return res;
    }

    @Override
    public String toString() {
        return ASMline;
    }
}
