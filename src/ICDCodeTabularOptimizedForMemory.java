import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{
    Map<String, String> stats = new HashMap<>();

    private Path path;
    public ICDCodeTabularOptimizedForMemory(Path path) {
        this.path = path;
    }

    @Override
    public String getDescription(String icd10) {
        try(Stream<String> fileLines = Files.lines(this.path)){ // reads lines you always need to close streams and make try-catch
            return fileLines.skip(87)
                    .map(String::trim)
                    .filter(line -> line.matches("[A-Z]\\d\\d.*"))
                    .map(line -> line.split(" ", 2))
                    .filter(s -> s[0].equals(icd10)) //s to wszystkie linijki z podzielonego stringu, s[0] bo pod 0 indeksem znajduje sie kod icd10
                    .findFirst()//gives the first element of the filtered stream
                    .map(s -> s[1])//extracts the description (the second element of the array) from the matched icd10
                    .orElse("no info");
        } catch (IOException | IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }
}
