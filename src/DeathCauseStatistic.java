import java.util.Arrays;

public class DeathCauseStatistic {

    private String icd10;
    private int[] deaths;

    public DeathCauseStatistic(String icd10, int[] deaths) {
        this.icd10 = icd10;
        this.deaths = deaths;
    }
    public String getIcd10() {
        return icd10;
    }

    public static DeathCauseStatistic fromCsvLine(String line){
        String[] parts = line.split(",", -1);
        String icd10 = parts[0].trim();
        int[] deaths = Arrays.stream(parts).skip(2).mapToInt(s -> s.equals("-") ? 0 : Integer.parseInt(s)).toArray();
        return new DeathCauseStatistic(icd10, deaths);
    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "icd10='" + icd10 + '\'' +
                ", deaths=" + Arrays.toString(deaths) +
                '}';
    }
    public AgeBracketDeaths ageBracketDeaths(int age){
        String ages = "0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95+";
        int index = age/5;
        int young = index * 5;
        int old = young+4;
        if(age >= 95){
            index = deaths.length - 1;
            old = Integer.MAX_VALUE;
        }
        return new AgeBracketDeaths(young, old, deaths[index]);
    }
    public record AgeBracketDeaths(int young, int old, int deathCount) {

    }
}
