import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    public static void writeFile(String fileName, String data) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWritter = new FileWriter(file.getName(),true);
        fileWritter.write(data);
        fileWritter.close();
    }
}