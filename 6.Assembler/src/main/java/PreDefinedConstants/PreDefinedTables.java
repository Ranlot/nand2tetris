package PreDefinedConstants;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

abstract public class PreDefinedTables {

    public static final ImmutableMap<String,String> jumpTableContent = ImmutableMap.<String, String>builder()
            .put("null", "000")
            .put("JGT", "001")
            .put("JEQ", "010")
            .put("JGE", "011")
            .put("JLT", "100")
            .put("JNE", "101")
            .put("JLE", "110")
            .put("JMP", "111")
            .build();

    public static final ImmutableMap<String, String> destinationTableContent = ImmutableMap.<String, String>builder()
            .put("null", "000")
            .put("M", "001")
            .put("D", "010")
            .put("MD", "011")
            .put("A", "100")
            .put("AM", "101")
            .put("AD", "110")
            .put("AMD", "111")
            .build();

    public static Map<String, String> memorySymbolContent = new HashMap<String, String>() {{
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

    public static final Map<String, String> computeTableContent = ImmutableMap.<String, String>builder()
            .put("0", "101010")
            .put("1", "111111")
            .put("-1", "111010")

            .put("D", "001100")
            .put("A", "110000")
            .put("M", "110000")

            .put("!D", "001101")
            .put("!A", "110001")
            .put("!M", "110001")

            .put("-D", "001111")
            .put("-A", "110011")
            .put("-M", "110011")

            .put("D+1", "011111")
            .put("1+D", "011111") //same as D+1

            .put("A+1", "110111")
            .put("1+A", "110111") //same as A+1

            .put("M+1", "110111")
            .put("1+M", "110111") //same as 1+M

            .put("D-1", "001110")
            .put("A-1", "110010")
            .put("M-1", "110010")

            .put("D+A", "000010")
            .put("A+D", "000010") //same as D+A

            .put("D+M", "000010")
            .put("M+D", "000010") //same as D+M

            .put("D-A", "010011")
            .put("D-M", "010011")

            .put("A-D", "000111")
            .put("M-D", "000111")

            .put("D&A", "000000")
            .put("A&D", "000000") //same as A&D

            .put("D&M", "000000")
            .put("M&D", "000000") //same as D&M

            .put("D|A", "010101")
            .put("A|D", "010101") //same as D|A

            .put("D|M", "010101")
            .put("M|D","010101")  //same as M|D

	        .build();

    /*public static Map<SymbolTableNames, Map<String, String>> getPreDefinedMaps() {
        Map<SymbolTableNames, Map<String, String>> mapOfPreDefinedMaps = new HashMap<>();
        mapOfPreDefinedMaps.put(jumpTable, jumpTableContent);
        mapOfPreDefinedMaps.put(destinationTable, destinationTableContent);
        //mapOfPreDefinedMaps.put(computeTable, computeTableContent);
        return mapOfPreDefinedMaps;
    }*/

}