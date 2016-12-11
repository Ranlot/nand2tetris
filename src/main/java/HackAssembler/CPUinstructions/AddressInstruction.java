package HackAssembler.CPUinstructions;

import PreDefinedConstants.SymbolTableNames;

import java.util.Map;

import static PreDefinedConstants.PreDefinedSymbols.addressInstruction;
import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;

public class AddressInstruction implements CPUinstruction {

    private String instruction;

    AddressInstruction(String instruction) {
        this.instruction = instruction;
    }

    private String extractMemorySymbol(String instruction) {
        return instruction.split(shtrudelSymbol)[1];
    }

    @Override
    public String decodeInstruction(Map<SymbolTableNames, Map<String, String>> allSymbolMaps) {
        return addressInstruction + "->" + instruction + ".";
    }

}
