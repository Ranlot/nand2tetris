package HackAssembler;

import java.util.Map;

//#TODO maje this a singleton
public class RelevantTables {

    private Map<String, String> labelTable;
    private Map<String, String> memorySymbol;
    private Map<String, String> computeTable;
    private Map<String, String> jumpTable;
    private Map<String, String> destinationTable;

    public RelevantTables(Map<String, String> labelTable,
                          Map<String, String> memorySymbol,
                          Map<String, String> computeTable,
                          Map<String, String> jumpTable,
                          Map<String, String> destinationTable) {
        this.labelTable = labelTable;
        this.memorySymbol = memorySymbol;
        this.computeTable = computeTable;
        this.jumpTable = jumpTable;
        this.destinationTable = destinationTable;
    }

    public Map<String, String> getLabelTable() {
        return labelTable;
    }

    public Map<String, String> getMemorySymbol() {
        return memorySymbol;
    }

    public Map<String, String> getComputeTable() {
        return computeTable;
    }

    public Map<String, String> getJumpTable() {
        return jumpTable;
    }

    public Map<String, String> getDestinationTable() {
        return destinationTable;
    }
}
