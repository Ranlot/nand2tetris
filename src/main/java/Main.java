import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static int countWords(String instruction) {
        return instruction.split(" ").length;
    }

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("src/main/resources/Add.asm");

        try(Stream<String> streamOfInstructions = Files.lines(path)) {
            List<Integer> numberOfWords = streamOfInstructions.map(Main::countWords).collect(Collectors.toList());
            System.out.println(numberOfWords);

        }

    }

}
