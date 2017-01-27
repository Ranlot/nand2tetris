package HackAssembler.Utils;

import HackAssembler.ASMtext.ASMtext;
import PreDefinedConstants.DynamicalTables;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static PreDefinedConstants.PreDefinedSymbols.shtrudelSymbol;
import static PreDefinedConstants.PreDefinedSymbols.startOfFreeMemoryAddressSymbols;
import static PreDefinedConstants.PreDefinedTables.memorySymbolsMap;

public class Utils {

    public static void writeToFile(FileWriter fw, String string) {
        try {
            fw.write(String.format("%s%n", string));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractMemorySymbol(String string) {
        return string.split(shtrudelSymbol)[1];
    }

    public static Map<DynamicalTables, Map<String, String>> createRelevantTables(Seq<ASMtext> allInstructions) {
        Map<DynamicalTables, Map<String, String>> relevantTables = new HashMap<>();

        Tuple2<Seq<ASMtext>, Seq<ASMtext>> duplicatedAllInstructions = allInstructions.duplicate();

        Map<String, String> labelTableContent = duplicatedAllInstructions.v1
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex()
                .collect(Collectors.toMap(x -> x.v1.v1.removeParenthesis(), x -> Long.toString(x.v1.v2 - x.v2)));

        Set<String> labelSymbols = labelTableContent.keySet();

        Set<String> preDefinedMemorySymbols = memorySymbolsMap.keySet();

        //keep only address instructions
        Seq<ASMtext> addressInstructions = duplicatedAllInstructions.v2.filter(ASMtext::isAddressInstruction);

        Map<String, String> memorySymbolsContent = addressInstructions
                .map(ASMtext::extractMemorySymbol)
                .distinct()
                .filter(memorySymbol -> !labelSymbols.contains(memorySymbol))
                .filter(memorySymbol -> !preDefinedMemorySymbols.contains(memorySymbol))
                .filter(memorySymbol -> !StringUtils.isNumeric(memorySymbol))
                .zipWithIndex()
                .map(numberedMemorySymbol -> Tuple.tuple(numberedMemorySymbol.v1,
                        numberedMemorySymbol.v2 + startOfFreeMemoryAddressSymbols))
                .collect(Collectors.toMap(numberedMemorySymbol -> numberedMemorySymbol.v1,
                        numberedMemorySymbol -> numberedMemorySymbol.v2.toString()));

        relevantTables.put(DynamicalTables.labelSymbols, labelTableContent);
        relevantTables.put(DynamicalTables.memorySymbols, memorySymbolsContent);

        return relevantTables;
    }
}