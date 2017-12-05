import java.util.ArrayList;

public class Main {

    public static void main(String args[]) {
        Config config = new Config();
        ParseAction parseAction = new ParseAction();
        WriteAction writeAction = new WriteAction();
        Execute execute = new Execute();

        config.readConfig();
        ArrayList<String> actions = parseAction.parseAction(config.getActionListPath(), config.getCommandListPath(),
                config.getMemoryPath());
        writeAction.writeActionScript(actions, config.getActionPath(), config.getOutputPath(), config.getRuntimeList(),
                config.getExtensionList(), config.getPrefixList(), config.getSuffixList(), config.getArgumentList());
        //execute.execute(config.getOutputPath(), config.getMemoryPath());
    }
}
