import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WriteAction {

    public void writeActionScript(ArrayList<String> actions, String actionPath, String outputPath, String[] runtime,
                                  String[] extensions, String[] prefixes, String[] suffixed, String[] arguments) {
        eraseFile(outputPath);
        for(String action : actions) {
            StringTokenizer tokenizer = new StringTokenizer(action);
            String actionName = tokenizer.nextToken();
            String remainder = action.substring(actionName.length());

            String extension = "";
            for(String extensionTemp : extensions) {
                String fileName = actionPath + "/" + actionName + extensionTemp;
                File file = new File(fileName);
                if(file.exists() && file.isFile()) {
                    extension = extensionTemp;
                    break;
                }
            }

            File file = new File(actionPath + "/" + actionName + extension);
            if(!file.exists() || !file.isFile()) {
                System.out.println("File " + actionName + extension + " not found.");
                return;
            }

            int runtimeIndex = 0;
            for(int x = 0 ; x < extensions.length ; x++) {
                if(extension.equals(extensions[x])) {
                    runtimeIndex = x;
                    break;
                }
            }

            String command = runtime[runtimeIndex] + " " + arguments[runtimeIndex] + " " + prefixes[runtimeIndex]
                    + actionName + suffixed[runtimeIndex] + remainder;
            command = command = command.substring(0, command.lastIndexOf(" ")) + " > " + command.substring(command.lastIndexOf(" ") + 1, command.length());
            writeFile(outputPath, "\n" + command);
        }

    }

    private void writeFile(String outputPath, String str) {
        try{
            // Create file
            FileWriter fstream = new FileWriter(outputPath, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str);
            //Close the output stream
            out.close();
        }catch (IOException e){//Catch exception if any
            e.printStackTrace();
        }
    }

    private void eraseFile(String filename)
    {
        try{
            // Create file
            FileWriter fstream = new FileWriter(filename, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("");
            //Close the output stream
            out.close();
        }catch (IOException e){//Catch exception if any
            e.printStackTrace();
        }
    }
}
