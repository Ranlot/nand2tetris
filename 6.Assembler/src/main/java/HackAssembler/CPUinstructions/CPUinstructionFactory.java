package HackAssembler.CPUinstructions;

import org.jooq.lambda.tuple.Tuple2;

import static PreDefinedConstants.PreDefinedSymbols.addressInstruction;

public class CPUinstructionFactory {

    public CPUinstruction makeCPUinstruction(Tuple2<String, String> instructionTuple) {

        return instructionTuple.v1.equals(addressInstruction) ? new AddressInstruction(instructionTuple.v2) : new ComputeInstruction(instructionTuple.v2);

    }

}
