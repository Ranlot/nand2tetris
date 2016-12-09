package HackAssembler.CPUinstructions;

import PreDefinedConstants.SymbolTableNames;

import java.util.Map;

public interface CPUinstruction {

    String decodeInstruction(Map<SymbolTableNames, Map<String, String>> allSymbolMaps);

}
