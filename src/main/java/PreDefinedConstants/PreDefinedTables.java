package PreDefinedConstants;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static PreDefinedConstants.SymbolTableNames.destinationTable;
import static PreDefinedConstants.SymbolTableNames.jumpTable;

abstract public class PreDefinedTables {

    private static final ImmutableMap<String,String> jumpTableContent = ImmutableMap.<String, String>builder()
            .put("null", "000")
            .put("JGT", "001")
            .put("JGE", "010")
            .put("JLT", "100")
            .put("JNE", "101")
            .put("JLE", "110")
            .put("JMP", "111")
            .build();

    private static final ImmutableMap<String, String> destinationTableContent = ImmutableMap.<String, String>builder()
            .put("null", "000")
            .put("M", "001")
            .put("D", "010")
            .put("MD", "011")
            .put("A", "100")
            .put("AM", "101")
            .put("AD", "110")
            .put("AMD", "111")
            .build();

    public static Map<String, String> originalMemorySymbolContent = new HashMap<String, String>() {{
        put("SP", "0");
        put("LCL", "1");
        put("ARG", "2");
        put("THIS", "3");
        put("THAT", "4");
        put("R0", "0");
        put("R1", "1");
        put("R2", "2");
        put("R3", "3");
        put("R4", "4");
        put("R5", "5");
        put("R6", "6");
        put("R7", "7");
        put("R8", "8");
        put("R9", "9");
        put("R10", "10");
        put("R11", "11");
        put("R12", "12");
        put("R13" , "13");
        put("R14", "14");
        put("R15", "15");
        put("SCREEN","16384");
        put("KBD", "24576");
    }};

    public static Map<SymbolTableNames, Map<String, String>> getPreDefinedMaps() {
        Map<SymbolTableNames, Map<String, String>> mapOfPreDefinedMaps = new HashMap<>();
        mapOfPreDefinedMaps.put(jumpTable, jumpTableContent);
        mapOfPreDefinedMaps.put(destinationTable, destinationTableContent);
        return mapOfPreDefinedMaps;
    }

}