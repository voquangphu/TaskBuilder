import java.io.IOException;

public class Execute {

    public void execute(String outputPath, String memoryPath) {
        eraseMemory(memoryPath);
        executeSript(outputPath);
    }

    private void executeSript(String outputPath) {
        try {
            Runtime.getRuntime().exec("sh " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eraseMemory(String memoryPath) {
        memoryPath = memoryPath.endsWith("/") ? memoryPath : memoryPath + "/";
        try {
            Runtime.getRuntime().exec("rm " + memoryPath + "*");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
