package HackAssembler.Utils;

import HackAssembler.ASMtext.ASMtext;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static PreDefinedConstants.PreDefinedSymbols.*;
import static PreDefinedConstants.PreDefinedTables.memorySymbolContent;

public class Utils {

    /*public static String make16bitBinary(String string) {
        //String binaryValue = t.toString();
        int missingBits = wordLength - string.length();
        return StringUtils.repeat(zeroBit, missingBits) + string;
    }*/

    public static void writeToFile(FileWriter fw, String string) {
        try {
            fw.write(String.format("%s%n", string));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, String>> createRelevantTables(Seq<ASMtext> allInstructions) {

        List<Map<String, String>> listOfTables = new ArrayList<>();

        Tuple2<Seq<ASMtext>, Seq<ASMtext>> duplicatedAllInstructions = allInstructions.duplicate();

        Map<String, String> labelTableContent = duplicatedAllInstructions.v1
                .zipWithIndex()
                .filter(numberedInstruction -> numberedInstruction.v1.isLabelInstruction())
                .zipWithIndex()
                .collect(Collectors.toMap(x -> x.v1.v1.removeParenthesis(), x -> Long.toString(x.v1.v2 - x.v2)));
                //.collect(Collectors.toMap(x -> x.v1.v1.toString(), x -> make16bitBinary(Long.toString(x.v1.v2 - x.v2))));

        //keep only address instructions
        Seq<ASMtext> addressInstructions = duplicatedAllInstructions.v2.filter(ASMtext::isAddressInstruction);

        Set<String> preDefinedMemorySymbols = memorySymbolContent.keySet();

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
                        numberedMemorySymbol.v2 + startOfFreeMemoryAddressSymbols))
                .collect(Collectors.toMap(numberedMemorySymbol -> numberedMemorySymbol.v1,
                        numberedMemorySymbol -> numberedMemorySymbol.v2.toString()));

        listOfTables.add(labelTableContent);
        listOfTables.add(memorySymbolsContent);

        return listOfTables;

    }

}