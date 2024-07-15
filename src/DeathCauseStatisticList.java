import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeathCauseStatisticList {
    private List<DeathCauseStatistic> statistics = new ArrayList<>();
    public void repopulate(String path){
        for(DeathCauseStatistic dcs :  statistics){
            statistics.remove(dcs);
        }
        try(Stream<String> fileLines = Files.lines(Path.of(path))){
            statistics = fileLines.skip(2).map(DeathCauseStatistic::fromCsvLine).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DeathCauseStatistic> mostDeadly(int age, int n){
        return statistics.stream().sorted((o1, o2) -> Integer.compare(
                o1.ageBracketDeaths(age).deathCount(),
                o2.ageBracketDeaths(age).deathCount()))
                .limit(n).toList();
    }

}
