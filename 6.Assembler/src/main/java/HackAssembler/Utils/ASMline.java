package HackAssembler.Utils;

import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.*;

public class ASMline {

    private String ASMline;

    public ASMline(String string) {
        this.ASMline = string;
    }

    private boolean isLabelInstruction(String string) {
        return string.contains("(") && string.contains(")");
    }

    boolean isLabelInstruction() {
        return isLabelInstruction(ASMline);
    }

    static boolean isAddressInstruction(String string) {
        return string.startsWith(shtrudelSymbol);
    }

    boolean isAddressInstruction() {
        return isAddressInstruction(ASMline);
    }

    String removeParenthesis() {
        return ASMline.replace("(", "").replace(")", "");
    }

    public String extractMemorySymbol() {
        return ASMline.split(shtrudelSymbol)[1];
    }

    public ASMlineCPU issueCPUinstruction() {
        return new ASMlineCPU(ASMline);
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

}