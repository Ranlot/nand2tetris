package HackAssembler.CPUinstructions;

import HackAssembler.RelevantTables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static PreDefinedConstants.PreDefinedSymbols.*;

public class ComputeInstruction implements CPUinstruction {

    private String instruction;

    public ComputeInstruction(String instruction) {
        this.instruction = instruction;
    }

    private boolean isJumpInstruction(String instruction) {
        return instruction.contains(jumpSeparator);
    }

    private List<String> instructionSplitter(String instruction, String splitCharacter) {

        List<String> res = new ArrayList<>();

        String[] splitJump = instruction.split(splitCharacter);
        String lhs = splitJump[0];
        String rhs = splitJump[1];

        res.add(lhs);
        res.add(rhs);

        return res;

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

        //String opCodeValue = opCode(instruction) ? "1" : "0";

        List<String> subComponents = new ArrayList<>();

        if(isJumpInstruction(instruction)) {

            List<String> splitJump = instructionSplitter(instruction, jumpSeparator);

            String computation = splitJump.get(0);

            String AMswitchValue = AMswitch(computation);
            String computationInBinary = computeTable.get(computation);

            subComponents.add(AMswitchValue);
            subComponents.add(computationInBinary);
            subComponents.add(destinationTable.get(doNotSave));
            subComponents.add(jumpTable.get(splitJump.get(1)));

            return makeBinaryInstruction(subComponents);
        }

        List<String> splitJump = instructionSplitter(instruction, equalSeparator);

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
