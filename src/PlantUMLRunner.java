import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PlantUMLRunner {
    private static String pathUML;
    public static void setPath(String path){
        PlantUMLRunner.pathUML = path;
    }
    public static void generateUML(String data, String outDir, String fileOut){
        File file = new File(outDir + fileOut + ".txt");
        try(FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)){
            writer.write(data);
            writer.close();
            String code= "java -jar "+ PlantUMLRunner.pathUML +" -charset UTF-8 " + file.getPath()
                    + " -o "+outDir+ " "+ fileOut;
            Process process = Runtime.getRuntime().exec(code);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

//Napisz klasę PlantUMLRunner, posiadającą publiczne statyczne metody:
//- ustawienie ścieżki do uruchamialnego pliku jar.
//- wygenerowanie schematu po przekazaniu: napisu z danymi, ścieżki
// do katalogu wynikowego i nazwy pliku wynikowego.