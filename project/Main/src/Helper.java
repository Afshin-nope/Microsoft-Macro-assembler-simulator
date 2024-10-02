import java.util.ArrayList;

public abstract class Helper {

    public static String commaRemover(String word) {
        String output = "";
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] != ',' && letters[i] != ' ') {
                output += letters[i];
            }
        }
        return output;
    }

    public static ArrayList<String> seperator(String input) {
        String first = "";
        String second = "";
        int flag = 0;
        char[] line = input.toCharArray();
        for (int i = 0; i < line.length; i++) {
            if (line[i] != ' ') {
                if (line[i] == ',') {
                    flag = 1;
                } else if (flag == 0) {
                    first += line[i];
                } else if (flag == 1) {
                    second += line[i];
                }
            }
        }
        if (second.equals("")) {
            second = "null";
        }
        ArrayList<String> output = new ArrayList<String>();
        output.add(first);
        output.add(second);
        return output;
    }

    public static ArrayList<String> arrayCheck(String word) {
        String index = "";
        char[] input = word.toCharArray();
        int flag = 0;
        int sign = 1;
        String isArray = "";
        String varName = "";
        for (int i = 0; i < input.length; i++) {
            if (input[i] == '[') {
                flag = 1;
            }
            if (flag == 1 && input[i] >= '0' && input[i] <= '9') {
                index += input[i];
            }
            if (flag == 1 && input[i] == '-') {
                sign = -1;
            }
        }

        for (int i = 0; i < input.length; i++) {
            if (input[i] != ' ' && input[i] != ',') {
                if (input[i] == '[') {
                    break;
                }
                varName += input[i];
            }
        }

        if (flag == 0) {
            isArray = "false";
        } else {
            isArray = "true";
            if (index.equals("")) {
                index = "0";
            } else {
            }
        }
        ArrayList<String> output = new ArrayList<String>();
        if (index.equals("")) {
            index = "0";
        }
        output.add(varName);
        output.add(isArray);
        output.add(index);
        return output;
    }

    public static ArrayList<String> arraySeperator(String input) {
        String val = "";
        ArrayList<String> output = new ArrayList<String>();
        input += ',';
        char[] line = input.toCharArray();
        for (int i = 0; i < line.length; i++) {
            if (line[i] != ' ' && line[i] != ',') {
                val += line[i];
            }
            if (line[i] == ',') {
                output.add(val);
                val = "";
            }
        }
        return output;
    }

    public static Integer typeFind(String type) {
        if (type.equals("byte") || type.equals("sbyte")) {
            return 1;
        }
        if (type.equals("word") || type.equals("sword")) {
            return 2;
        }
        if (type.equals("dword") || type.equals("sdword")) {
            return 4;
        } else
            return -1;
    }

    public static ArrayList<String> bracketFixer(String line) {
        String var = "";
        String index = "";
        String sign = "";
        int openFlag = 0;
        int operatorFlag = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                openFlag = 1;
            } else if (line.charAt(i) == ']') {
                break;

            } else if (line.charAt(i) == '+' || line.charAt(i) == '-' || line.charAt(i) == '*' || line.charAt(i) == '/') {
                operatorFlag = 1;
                sign += line.charAt(i);
            } else if (openFlag == 1 && operatorFlag == 0) {
                if (line.charAt(i) != ' ') {
                    var += line.charAt(i);
                }
            } else if (operatorFlag == 1) {
                if (line.charAt(i) != ' ') {
                    index += line.charAt(i);
                }
            }
        }
        if (openFlag == 0) {
            int ptrFound = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'p' && line.charAt(i) == 't' && line.charAt(i) == 'r') {
                    ptrFound = 1;
                } else if (ptrFound == 1) {
                    if (line.charAt(i) != ' ') {
                        var += line.charAt(i);
                    }
                }
            }
        }
        if (index.equals("")) {
            index = "0";
        }
        ArrayList<String> output = new ArrayList<String>();
        output.add(var);
        output.add(sign);
        output.add(index);
        return output;
    }

    public static String removeSubstring(String original, String toRemove) {
        if (original == null || toRemove == null || toRemove.isEmpty()) {
            return original;
        }
        return original.replace(toRemove, "");
    }

    public static String getStringAfter(String original, String delimiter) {
        if (original == null || delimiter == null || delimiter.isEmpty()) {
            return original;
        }

        int index = original.indexOf(delimiter);
        if (index != -1) {
            return original.substring(index + delimiter.length());
        }
        return "";
    }

    public static ArrayList<String> arrayFiller(String line) {
        ArrayList<String> output = new ArrayList<String>();
        String word = "";
        line += ",";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                if (line.charAt(i) != ',') {
                    word += line.charAt(i);
                }
                if (line.charAt(i) == ',') {
                    output.add(word);
                    word = "";
                }
            }
        }
        return output;
    }

    public static String stringToAsciiBinary(String input) {
        StringBuilder binaryString = new StringBuilder();

        for (char ch : input.toCharArray()) {
            String binaryChar = String.format("%8s", Integer.toBinaryString(ch)).replace(' ', '0');
            binaryString.append(binaryChar);
        }

        return binaryString.toString();
    }

    public static ArrayList<String> adjustList(ArrayList<String> list, int interval) {
        if (list == null || interval <= 0) {
            throw new IllegalArgumentException("List cannot be null and interval must be greater than zero.");
        }

        int newSize = (list.size() - 1) * interval + 1;
        ArrayList<String> resultList = new ArrayList<>(newSize);

        for (int i = 0; i < newSize; i++) {
            resultList.add("");
        }

        for (int i = 0; i < list.size(); i++) {
            resultList.set(i * interval, list.get(i));
        }

        return resultList;
    }

    public static String pureLength(ArrayList<String> input){
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            if (! input.get(i).equals("")){
                count++;
            }
        }
        return Integer.toString(count);
    }
}