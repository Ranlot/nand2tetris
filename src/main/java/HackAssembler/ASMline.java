package HackAssembler;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import static PreDefinedConstants.PreDefinedSymbols.*;

public class ASMline {

    private String string;

    public ASMline(String string) {
        this.string = string;
    }

    public ASMline sanitizeString() {
        String symbolLessString = string.split(commentSymbol)[0];
        String sanitizedString = symbolLessString.replaceAll(emptySpace, byNothing);
        return new ASMline(sanitizedString);
    }

    public boolean isNotEmpty() {
        return ! string.isEmpty();
    }

    public boolean isCPUinstruction() {
        return ! (string.contains("(") && string.contains(")"));
    }

    public boolean isLabelInstruction() {
        return string.contains("(") && string.contains(")");
    }

    public Tuple2<String, String> getCPUinstructionType() {
        return Tuple.tuple(isAinstruction(string) ? Ainstruction : Cinstruction, string);
    }

    private boolean isAinstruction(String string) {
        return string.startsWith(shtrudelSymbol);
    }

    @Override
    public String toString() {
        return string;
    }
}
