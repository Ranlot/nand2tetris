package HackAssembler.CPUinstructions;

import PreDefinedConstants.SymbolTableNames;

import java.util.Map;

import static PreDefinedConstants.PreDefinedSymbols.computeInstruction;

public class ComputeInstruction implements CPUinstruction {

    private String instruction;

    ComputeInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String decodeInstruction(Map<SymbolTableNames, Map<String, String>> allSymbolMaps) {
        return computeInstruction + "->" + instruction + ".";
    }

}
