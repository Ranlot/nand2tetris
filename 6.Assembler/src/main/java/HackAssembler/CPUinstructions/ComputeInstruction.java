package HackAssembler.CPUinstructions;

import HackAssembler.Utils.ASMline;
import HackAssembler.Utils.RelevantTables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static HackAssembler.PreDefinedConstants.PreDefinedSymbols.*;

public class ComputeInstruction implements CPUinstruction {

    private ASMline instruction;

    public ComputeInstruction(ASMline instruction) {
        this.instruction = instruction;
    }

    private String AMswitch(String instruction) {
        return instruction.contains("M") ? "1" : "0";
    }

    private String makeBinaryInstruction(List<String> subComponents) {
        return computeInstructionMSBs + subComponents.stream().collect(Collectors.joining());
    }

    @Override
    public String decodeCPUInstruction(RelevantTables relevantTables) {

        Map<String, String> computeTable = relevantTables.getComputeTable();
        Map<String, String> destinationTable = relevantTables.getDestinationTable();
        Map<String, String> jumpTable = relevantTables.getJumpTable();

        List<String> subComponents = new ArrayList<>();

        if(instruction.isJumpInstruction()) {

            List<String> splitJump = instruction.instructionSplitter(jumpSeparator);

            String computation = splitJump.get(0);

            String AMswitchValue = AMswitch(computation);
            String computationInBinary = computeTable.get(computation);

            subComponents.add(AMswitchValue);
            subComponents.add(computationInBinary);
            subComponents.add(destinationTable.get(doNotSave));
            subComponents.add(jumpTable.get(splitJump.get(1)));

            return makeBinaryInstruction(subComponents);
        }

        List<String> splitJump = instruction.instructionSplitter(equalSeparator);

        String computation = splitJump.get(1);

        String AMswitchValue = AMswitch(computation);
        String computationInBinary = computeTable.get(computation);

        subComponents.add(AMswitchValue);
        subComponents.add(computationInBinary);
        subComponents.add(destinationTable.get(splitJump.get(0)));
        subComponents.add(jumpTable.get(doNotSave));

        return makeBinaryInstruction(subComponents);

    }

}
