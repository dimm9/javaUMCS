import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{

    Map<String, String> icdMap = new HashMap<>(); //key - ICD-10 code, value - information
    public ICDCodeTabularOptimizedForTime(Path path) {
        try(Stream<String> fileLines = Files.lines(path)){ //reads lines from a file using Files.lines(path) returns a stream of lines from the file
            fileLines.skip(87)
                    .map(String::trim) //removes whitespace from the string
                    .filter(line -> line.matches("[A-Z]\\d\\d.*")) // regular expression (icd10 format)
                    .map(line -> line.split(" ", 2))// splits each line into 2 parts, returns an array of strings(first element - ICD-10 code, second - info)
                    .forEach(strings ->
                            icdMap.put(strings[0], strings[1])// assigns array values to map
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDescription(String icd10) throws IndexOutOfBoundsException{
        if(icdMap.get(icd10) != null){
            return icdMap.get(icd10);
        }else{
            throw new IndexOutOfBoundsException();
        }

    }
}
