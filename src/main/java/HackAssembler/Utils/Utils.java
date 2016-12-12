package HackAssembler.Utils;

import HackAssembler.ASMtext.ASMtext;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static PreDefinedConstants.PreDefinedSymbols.*;
import static PreDefinedConstants.PreDefinedTables.originalMemorySymbolContent;

public class Utils {

    public static String make16bitBinary(long value) {
        String binaryValue = Long.toBinaryString(value);
        int missingBits = wordLength - binaryValue.length();
        return StringUtils.repeat(zeroBit, missingBits) + binaryValue;
    }

    public static List<Map<String, String>> createRelevantTables(Seq<ASMtext> allInstructions) {

        List<Map<String, String>> listOfTables = new ArrayList<>();

        Tuple2<Seq<ASMtext>, Seq<ASMtext>> duplicatedAllInstructions = allInstructions.duplicate();

        Map<String, String> labelTableContent = duplicatedAllInstructions.v1
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex()
                .collect(Collectors.toMap(x -> x.v1.v1.toString(), x -> make16bitBinary(x.v1.v2 - x.v2)));

        //keep only address instructions
        Seq<ASMtext> addressInstructions = duplicatedAllInstructions.v2.filter(ASMtext::isAddressInstruction);

        Set<String> preDefinedMemorySymbols = originalMemorySymbolContent.keySet();

        Set<String> labelSymbols = labelTableContent.keySet().stream()
                .map(x -> x.replace("(", "").replace(")", ""))
                .collect(Collectors.toSet());

        Map<String, String> memorySymbolsContent = addressInstructions
                .map(ASMtext::extractMemorySymbol)
                .distinct()
                .filter(memorySymbol -> !labelSymbols.contains(memorySymbol))
                .filter(memorySymbol -> !preDefinedMemorySymbols.contains(memorySymbol))
                .filter(memorySymbol -> !StringUtils.isNumeric(memorySymbol))
                .zipWithIndex()
                .map(numberedMemorySymbol -> Tuple.tuple(numberedMemorySymbol.v1,
                        make16bitBinary(numberedMemorySymbol.v2 + startOfFreeMemoryAddressSymbols)))
                .collect(Collectors.toMap(numberedMemorySymbol -> numberedMemorySymbol.v1,
                        numberedMemorySymbol -> numberedMemorySymbol.v2));

        listOfTables.add(labelTableContent);
        listOfTables.add(memorySymbolsContent);

        return listOfTables;

    }

}