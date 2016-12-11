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
    }};

    public static Map<SymbolTableNames, Map<String, String>> getPreDefinedMaps() {
        Map<SymbolTableNames, Map<String, String>> mapOfPreDefinedMaps = new HashMap<>();
        mapOfPreDefinedMaps.put(jumpTable, jumpTableContent);
        mapOfPreDefinedMaps.put(destinationTable, destinationTableContent);
        return mapOfPreDefinedMaps;
    }

}