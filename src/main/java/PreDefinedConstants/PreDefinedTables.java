package PreDefinedConstants;

import com.google.common.collect.ImmutableMap;

public final class PreDefinedTables {

    public static final ImmutableMap<String,String> JUMP_TABLE = ImmutableMap.<String, String>builder()
            .put("null", "000")
            .put("JGT", "001")
            .put("JGE", "010")
            .put("JLT", "100")
            .put("JNE", "101")
            .put("JLE", "110")
            .put("JMP", "111")
            .build();

}