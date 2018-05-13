import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Config {

    private String currentPath;
    private String actionMapPath;
    private String actionPath;
    private String commandListPath;
    private String outputPath;
    private String memoryPath;
    private String[] runtimeList;
    private String[] extensionList;
    private String[] prefixList;
    private String[] suffixList;
    private String[] argumentList;

    public void readConfig() {
        getCurrentPath();
        File config = new File(currentPath + "/config");
        Scanner input = null;
        try {
          input = new Scanner(config);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(input == null) {
            return;
        }

        while(input.hasNext()) {
            String line = input.nextLine();
            if(line.startsWith("//")) {
                continue;
            }
            if(line.startsWith("action-map")) {
                setActionMapPath(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("action-base")) {
                setActionPath(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("command-list")) {
                setCommandListPath(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("output-script")) {
                setOutputPath(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("memory")) {
                setMemoryPath(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("runtime")) {
                setRuntime(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("extension")) {
                setExtensionList(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("prefix")) {
                setPrefixList(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("suffix")) {
                setSuffixList(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
            if(line.startsWith("argument")) {
                setArgumentList(line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")));
            }
        }

        if(!validatePath()) {
            System.out.println("Configuration file or folder not found. Please check config file.");
            return;
        }
    }

    public String getActionListPath() {
        return actionMapPath;
    }

    public String getActionPath() {
        return actionPath;
    }

    public String getCommandListPath() {
        return commandListPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getMemoryPath() {
        return memoryPath;
    }

    public String[] getRuntimeList() {
        return runtimeList;
    }

    public String[] getExtensionList() {
        return extensionList;
    }

    public String[] getPrefixList() {
        return prefixList;
    }

    public String[] getSuffixList() {
        return suffixList;
    }

    public String[] getArgumentList() {
        return argumentList;
    }

    private boolean validatePath() {
        boolean validate = true;
        File actionList = new File(actionMapPath);
        if(!actionList.exists() || !actionList.isFile()) {
            validate = false;
        }

        File action = new File(actionPath);
        if(!action.exists() || !action.isDirectory()) {
            validate = false;
        }

        File commandList = new File(commandListPath);
        if(!commandList.exists() || !commandList.isFile()) {
            validate = false;
        }

        File output = new File(outputPath);
        if(!output.exists() || !output.isFile()) {
            validate = false;
        }

        File memory = new File(memoryPath);
        if(!memory.exists() || !memory.isDirectory()) {
            validate = false;
        }

        return validate;
    }

    private void setActionMapPath(String path) {
        actionMapPath = currentPath + "/" + path;
    }

    private void setActionPath(String path) {
        actionPath = currentPath + "/" + getPathWithoutTail(path);
    }

    private void setCommandListPath(String path) {
        commandListPath = currentPath + "/" + path;
    }

    private void setOutputPath(String path) {
        outputPath = currentPath + "/" + path;
    }

    private void setMemoryPath(String path) {
        memoryPath = currentPath + "/" + getPathWithoutTail(path);
    }

    private void setRuntime(String runtime) {
        StringTokenizer tokenizer = new StringTokenizer(runtime, "|");
        runtimeList = new String[tokenizer.countTokens()];
        for(int x = 0 ; x < runtimeList.length ; x++) {
            runtimeList[x] = tokenizer.nextToken();
        }
    }

    private void setExtensionList(String extension) {
        StringTokenizer tokenizer = new StringTokenizer(extension, "|");
        extensionList = new String[tokenizer.countTokens()];
        for(int x = 0 ; x < extensionList.length ; x++) {
            extensionList[x] = tokenizer.nextToken();
        }
    }

    private void setPrefixList(String prefix) {
        StringTokenizer tokenizer = new StringTokenizer(prefix, "|");
        prefixList = new String[tokenizer.countTokens()];
        for(int x = 0 ; x < prefixList.length ; x++) {
            String token = tokenizer.nextToken();
            for(int y = 0 ; y < runtimeList.length ; y++) {
                if(token.startsWith(runtimeList[y])) {
                    prefixList[x] = token.substring(token.indexOf(runtimeList[y]) + runtimeList[y].length() + 1, token.length());
                }
            }
        }
    }

    private void setSuffixList(String suffix) {
        StringTokenizer tokenizer = new StringTokenizer(suffix, "|");
        suffixList = new String[tokenizer.countTokens()];
        for(int x = 0 ; x < suffixList.length ; x++) {
            String token = tokenizer.nextToken();
            for(int y = 0 ; y < runtimeList.length ; y++) {
                if(token.startsWith(runtimeList[y])) {
                    suffixList[x] = token.substring(token.indexOf(runtimeList[y]) + runtimeList[y].length() + 1, token.length());
                }
            }
        }
    }

    private void setArgumentList(String argument) {
        StringTokenizer tokenizer = new StringTokenizer(argument, "|");
        argumentList = new String[tokenizer.countTokens()];
        for(int x = 0 ; x < argumentList.length ; x++) {
            String token = tokenizer.nextToken();
            for(int y = 0 ; y < runtimeList.length ; y++) {
                if(token.startsWith(runtimeList[y])) {
                    argumentList[y] = token.substring(token.indexOf(runtimeList[y]) + runtimeList[y].length() + 1, token.length());
                }
            }
        }
    }

    private void getCurrentPath() {
        try {
            currentPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
            currentPath = getPathWithoutTail(currentPath);
            if(currentPath.endsWith(".jar")) {
                currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String getPathWithoutTail(String path) {
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }
}
