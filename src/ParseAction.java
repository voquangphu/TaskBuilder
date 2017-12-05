import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ParseAction {

    public ArrayList<String> parseAction(String actionListPath, String commandListPath, String memoryPath) {
        ArrayList<String> actions = new ArrayList<>();
        ArrayList<String> instructions = getCommandList(commandListPath);

        for(int x = 0 ; x < instructions.size() ; x++)
        {
            actions.add(instructions.get(x));
        }

        ArrayList<String> listOutputs = new ArrayList<>();

        int count = 1; // used to calculate output variables

        File file = new File(actionListPath);
        for(int x = 0 ; x < actions.size() ; x++) {
            String actionName = "";
            ArrayList<String> params = new ArrayList<>();
            String output = "";

            StringTokenizer token = new StringTokenizer(actions.get(x));
            int numToken = token.countTokens();

            for (int y = 0; y < numToken; y++) {
                String temp = token.nextToken();
                if (y == 0) {
                    actionName = temp;
                }

                if (y > 0) {
                    if (y < numToken - 1) {
                        params.add(temp);
                    } else {
                        output = temp;
                        listOutputs.add(output);
                    }
                }
            }

            Scanner input = null;
            try {
                input = new Scanner(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<String> listActions = new ArrayList<>();

            while (input.hasNext()) {
                String line = input.nextLine();
                if (line.length() > 0 && line.charAt(0) == '#') {
                    if (line.substring(1, line.indexOf(" ")).equals(actionName)) // if match the action
                    {
                        String mainAction = line.substring(1, line.length());
                        StringTokenizer tempToken = new StringTokenizer(mainAction);
                        int tempNumToken = tempToken.countTokens();

                        ArrayList<String> mainParams = new ArrayList<>();
                        String mainOutput = "";

                        for (int y = 0; y < tempNumToken; y++) {
                            String temp = tempToken.nextToken();
                            if (y > 0) {
                                if (y < tempNumToken - 1) {
                                    mainParams.add(temp);
                                } else
                                    mainOutput = temp;
                            }
                        }

                        while (input.hasNext()) {
                            String temp = input.nextLine();
                            if (temp.length() > 0) {
                                listActions.add(temp);
                            } else {
                                break;
                            }
                        }

                        if (listActions.size() == 0) // foundation action
                        {

                        } else {
                            ArrayList<String> tempOutputs = new ArrayList<>();
                            for (int y = 0; y < listActions.size(); y++) {
                                String temp = listActions.get(y);

                                // insert input from action request to parsed action
                                for (int z = 0; z < mainParams.size(); z++) {
                                    if (temp.contains(" " + mainParams.get(z) + " ")) {
                                        temp = temp.replace(" " + mainParams.get(z) + " ", " " + params.get(z) + " ");
                                    }
                                }


                                if (temp.substring(temp.lastIndexOf(" ") + 1, temp.length()).equals(mainOutput)) {
                                    // map output from action request to parsed action
                                    temp = temp.substring(0, temp.length() - mainOutput.length()) + output;
                                } else {
                                    // create other temporary outputs
                                    String outputTemp = temp.substring(temp.lastIndexOf(" ") + 1, temp.length());
                                    boolean proceed = false;
                                    if (!outputTemp.contains("tempOutput")) {
                                        temp = temp.substring(0, temp.lastIndexOf(" ")) + " " + "tempOutput" + count;
                                        proceed = true;
                                    }

                                    if (y < listActions.size() - 1 && proceed) {
                                        for (int z = y + 1; z < listActions.size(); z++) {
                                            String tempTemp = listActions.get(z);
                                            if (tempTemp.contains(" " + outputTemp + " ")) // if redundant input
                                            {
                                                tempTemp = tempTemp.replace(" " + outputTemp + " ", " " + "tempOutput" + count + " ");
                                            }

                                            if (tempTemp.substring(tempTemp.lastIndexOf(" ") + 1, tempTemp.length()).equals(outputTemp)) // if redundant output
                                            {
                                                if (!tempTemp.substring(tempTemp.lastIndexOf(" ") + 1, tempTemp.length()).contains("tempOutput")) {
                                                    tempTemp = tempTemp.substring(0, tempTemp.lastIndexOf(" ")) + " " + "tempOutput" + count;
                                                }
                                            }

                                            listActions.set(z, tempTemp);
                                        }
                                    }
                                    count++;
                                }

                                listActions.set(y, temp);

                                //------------------
                                StringTokenizer token1 = new StringTokenizer(temp);
                                int numToken1 = token1.countTokens();

                                String output1 = "";

                                for (int z = 0; z < numToken1; z++) {
                                    if (z == numToken1 - 1) {
                                        output1 = token1.nextToken();
                                        if (output1.equals(mainOutput)) {
                                            temp = temp.substring(0, temp.length() - output1.length()) + output;
                                        }
                                    } else
                                        token1.nextToken();
                                }

                                if (output1.equals(output)) {
                                    output1 = "tempOutput" + count;
                                    for (int t = 0; t < listActions.size(); t++) {
                                        String temp1 = listActions.get(t);
                                        if (temp1.contains(" " + output1 + " ")) {
                                            temp1 = temp1.replace("  " + output1 + " ", "tempOutput" + count);
                                        }
                                        listActions.set(t, temp1);
                                    }
                                    count++;
                                }
                                listActions.set(y, temp);
                                tempOutputs.add(output1);
                            }

                            for (int y = 0; y < tempOutputs.size(); y++) {
                                listOutputs.add(tempOutputs.get(y));
                            }

                            //-----------------------------
                        /*
                        if(x < parsedInstructions.size() - 1)
                        {
                            for(int z = 0 ; z < listOutputs.size() ; z++)
                            {
                                boolean occur = false;
                                for(int y = x + 1 ; y < parsedInstructions.size() ; y++)
                                {
                                    String value = parsedInstructions.get(y);
                                    String tempOutput = parsedInstructions.get(y).substring(parsedInstructions.get(y).lastIndexOf(" "), parsedInstructions.get(y).length());
                                    if(tempOutput.equals(listOutputs.get(z)))
                                    {
                                        occur = true;
                                        value = value.substring(0, value.length() - tempOutput.length()) + "tempOutput" + count;
                                    }
                                    if(value.contains(" " + listOutputs.get(z) + " "))
                                    {
                                        occur = true;
                                        value = value.replace(" " + listOutputs.get(z) + " ", "tempOutput" + count);
                                    }
                                }

                                if(occur)
                                {
                                    count++;
                                    //remember update outputs of original instructions if parsedInstructions change
                                }
                            }
                        }*/
                            //----------------------------
                            actions.remove(x);

                            for (int y = 0; y < listActions.size(); y++) {
                                actions.add(x + y, listActions.get(y));
                            }

                            x--;
                        }
                        break;
                    }
                }

            }
        }

        return addMemoryPath(actions, memoryPath);
    }

    private ArrayList<String> getCommandList(String commandListPath) {
        ArrayList<String> commandList = new ArrayList<>();

        File commandFile = new File(commandListPath);
        Scanner input = null;
        try {
            input = new Scanner(commandFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(input.hasNext()) {
            String line = input.nextLine();
            if(line.length() > 0) {
                commandList.add(line);
            }
        }

        return commandList;
    }

    private ArrayList<String> addMemoryPath(ArrayList<String> actions, String memoryPath) {
        for(int x = 0 ; x < actions.size() ; x++) {
            String action = actions.get(x);
            String actionWithMemoryPath = "";
            StringTokenizer tokenizer = new StringTokenizer(action);
            int numTokens = tokenizer.countTokens();
            for(int y = 0 ; y < numTokens ; y++) {
                if(y == 0) {
                    actionWithMemoryPath += tokenizer.nextToken();
                } else {
                    String token = tokenizer.nextToken();
                    boolean directInput = false;
                    if(token.startsWith("string:")) {
                        token = token.substring("string:".length());
                        directInput = true;
                    }

                    actionWithMemoryPath += directInput ? " " + token : " " + memoryPath + "/" + token;
                }
            }
            actions.set(x, actionWithMemoryPath);
        }

        return actions;
    }
}
