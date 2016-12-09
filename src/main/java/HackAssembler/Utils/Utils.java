package HackAssembler.Utils;

import HackAssembler.ASMtext.ASMtext;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Map;
import java.util.stream.Collectors;

import static PreDefinedConstants.PreDefinedSymbols.wordLength;
import static PreDefinedConstants.PreDefinedSymbols.zeroBit;

public class Utils {

    private static String make16bitBinary(long value) {
        String binaryValue = Long.toBinaryString(value);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    //note that the type is misleading since this function also has the side-effect of closing the input stream
    //this is why we had to duplicate the original stream of instructions
    public static Map<String, String> createLabelTable(Seq<ASMtext> allInstructions) {

        Seq<Tuple2<Tuple2<ASMtext, Long>, Long>> indexedLabelInstructions = allInstructions
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex();

        return indexedLabelInstructions.collect(Collectors.toMap(x -> x.v1.v1.toString(), x -> make16bitBinary(x.v1.v2 - x.v2)));
    }

}