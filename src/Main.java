import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DeathCauseStatisticList list = new DeathCauseStatisticList();
        list.repopulate("zgony.csv");
        List<DeathCauseStatistic> deadly = list.mostDeadly(26, 5);
        ICDCodeTabularOptimizedForTime stats = new ICDCodeTabularOptimizedForTime(Path.of("icd10.txt"));
        List<String> icd10 = deadly.stream().map(DeathCauseStatistic::getIcd10).toList();

        for(String code : icd10){
            System.out.println(code);
            System.out.println(stats.getDescription(code));
        }

        ICDCodeTabularOptimizedForMemory statsMem = new ICDCodeTabularOptimizedForMemory(Path.of("icd10.txt"));
        for(String code : icd10){
            System.out.println(statsMem.getDescription(code));
        }

    }
}
