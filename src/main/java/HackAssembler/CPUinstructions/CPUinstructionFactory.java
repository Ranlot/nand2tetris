package HackAssembler.CPUinstructions;

import org.jooq.lambda.tuple.Tuple2;

import static PreDefinedConstants.PreDefinedSymbols.Ainstruction;

public class CPUinstructionFactory {

    public Instruction getCPUinstruction(Tuple2<String, String> instructionTuple) {

        return instructionTuple.v1.equals(Ainstruction) ? new Ainstruction(instructionTuple.v2) : new Cinstruction(instructionTuple.v2);

    }

}
