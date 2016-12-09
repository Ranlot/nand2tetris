package HackAssembler.ASMtext;

import static PreDefinedConstants.PreDefinedSymbols.*;

public class ASMtext {

    private String string;

    public ASMtext(String string) {
        this.string = string;
    }

    private boolean isLabelInstruction(String string) { return string.contains("(") && string.contains(")"); }

    public ASMtext sanitizeString() {
        String symbolLessString = string.split(commentSymbol)[0];
        String sanitizedString = symbolLessString.replaceAll(emptySpace, byNothing);
        return new ASMtext(sanitizedString);
    }

    public boolean isNotEmpty() {
        return ! string.isEmpty();
    }

    public boolean isCPUinstruction() {
        return ! isLabelInstruction(string);
    }

    public boolean isLabelInstruction() {
        return isLabelInstruction(string);
    }

    public ASMtextCPU issueCPUinstruction() {
        return new ASMtextCPU(string);
    }

    @Override
    public String toString() {
        return string;
    }
}