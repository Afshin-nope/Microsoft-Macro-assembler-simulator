import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.server.RemoteRef;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        File input = new File("");// PUT THE ADDRESS OF YOUR TXT FILE
        try (Scanner scan = new Scanner(input)) {
            ArrayList<Variable> dataSet;
            Stack stack = new Stack();
            Flags flags = new Flags();
            dataSet = dataReader(scan);
            ArrayList<Register> registers;
            registers = regCreator();
            instructionOperator(scan, registers, dataSet, flags, stack, input);

            for (Register reg : registers){
                System.out.println(reg.name + "  " + reg.value );
            }

            System.out.println(stack.esp);
            System.out.println(dataSet);
            System.out.println("stack : " + stack.addresses);
            System.out.println(flags.makeString());


        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + input.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public static boolean jumpChecker(Scanner scan,String goCheck, File input, Stack stack, String type, ArrayList<Variable> dataSet, ArrayList<Register> registers, Flags flags){
        String label = "";
        boolean output = false;
        label = Helper.commaRemover(scan.nextLine().toLowerCase());
        if (type.equals("call") || type.equals("invoke")){

        }else {
            label += ":";
        }
        /*if (! type.equals("loop")){
            stack.push("jump", "4");
        }*/

        //reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
        if (goCheck.equals("jz") || goCheck.equals("je")){
            if (flags.zero ==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jnz") || goCheck.equals("jne")){
            if (flags.zero ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jc")){
            if (flags.carry ==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jnc")){
            if (flags.carry ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jo")){
            if (flags.overFlow ==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jno")){
            if (flags.overFlow ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("js")){
            if (flags.sign ==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jns")){
            if (flags.sign ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jp")){
            if (flags.parity ==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jnp")){
            if (flags.parity ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jcxz")){
            String line = Register.decimalToBinary(registers.get(2).value).substring(16,32);
            if (Integer.valueOf(Register.binaryToDecimal(line)) ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jecxz")){
            if (Integer.valueOf(registers.get(2).value) ==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("ja") || goCheck.equals("jnbe")){
            if (flags.zero ==0 && flags.carry==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jae") || goCheck.equals("jnb")){
            if (flags.carry==0){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jb") || goCheck.equals("jnae")){
            if (flags.carry==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jbe") || goCheck.equals("jna")){
            if (flags.zero ==1 || flags.carry==1){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jg") || goCheck.equals("jnle")){
            if (flags.zero ==0 && flags.sign == flags.overFlow){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jge") || goCheck.equals("jnl")){
            if (flags.sign==flags.overFlow){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jl") || goCheck.equals("jnge")){
            if (flags.sign!=flags.overFlow){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("jle") || goCheck.equals("jng")){
            if (flags.zero ==1 || flags.sign != flags.overFlow){
                output = true;
                reachedLabel(input, label, stack, "jump", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("loop")){
            registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
            if (Integer.valueOf(registers.get(2).value) > 0){
                output = true;
                reachedLabel(input, label, stack, "loop", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("loopz") || goCheck.equals("loope")){
            registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
            if (Integer.valueOf(registers.get(2).value) > 1 && flags.zero==1){
                output = true;
                reachedLabel(input, label, stack, "loop", dataSet, registers, flags);
            }
        }
        if (goCheck.equals("loopnz") || goCheck.equals("loopne")){
            registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
            if (Integer.valueOf(registers.get(2).value) > 1 && flags.zero==0){
                output = true;
                reachedLabel(input, label, stack, "loop", dataSet, registers, flags);
            }
        }
        /////function
        if (type.equals("call")){
            reachedLabel(input, label, stack, "call", dataSet, registers, flags);
        }

        /*if (!type.equals("loop")){
            stack.addresses = stack.addresses.substring(0, stack.esp-4);
            stack.esp -=4;
        }*/
        return output;
    }
    public static void reachedLabel(File input, String label, Stack stack, String type, ArrayList<Variable> dataSet, ArrayList<Register> registers, Flags flags){
        try (Scanner scan = new Scanner(input)){
            while (scan.hasNext()){
                String next = scan.next().toLowerCase();
                if (next.equals(label)){
                    if (type.equals("call")){
                        if (scan.next().toLowerCase().equals("proc")){
                            instructionOperator(scan, registers, dataSet, flags, stack, input);
                            break;
                        }
                    } else if (type.equals("loop")) {
                        instructionOperator(scan, registers, dataSet, flags, stack, input);
                        break;
                    } else {
                        instructionOperator(scan, registers, dataSet, flags, stack, input);
                        break;
                    }
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("File not found: " + input.getAbsolutePath());
            e.printStackTrace();
        }

    }
    public static void instructionOperator(Scanner scan, ArrayList<Register> registers, ArrayList<Variable> dataSet, Flags flags, Stack stack, File input){
        int breakFlag = 0;
        int mainFlag = 0;
        String idk = "";
        while (scan.hasNext()) {
            String instruction = scan.next().toLowerCase();
            boolean happened ;
            switch (instruction) {
                case "add":
                    processInstruction(scan, registers, dataSet, "add", flags, stack);
                    break;
                case "sub":
                    processInstruction(scan, registers, dataSet, "sub", flags, stack);
                    break;
                case "mov":
                    processInstruction(scan, registers, dataSet, "mov", flags, stack);
                    break;
                case "movzx":
                    processInstruction(scan, registers, dataSet, "movzx", flags, stack);
                    break;
                case "movsx":
                    processInstruction(scan, registers, dataSet, "movsx", flags, stack);
                    break;
                case "inc":
                    processInstruction(scan, registers, dataSet, "inc", flags, stack);
                    break;
                case "dec":
                    processInstruction(scan, registers, dataSet, "dec", flags, stack);
                    break;
                case "xor":
                    processInstruction(scan, registers, dataSet, "xor", flags, stack);
                    break;
                case "and":
                    processInstruction(scan, registers, dataSet, "and", flags, stack);
                    break;
                case "or":
                    processInstruction(scan, registers, dataSet, "or", flags, stack);
                    break;
                case "not":
                    processInstruction(scan, registers, dataSet, "not", flags, stack);
                    break;
                case "neg":
                    processInstruction(scan, registers, dataSet, "neg", flags, stack);
                    break;
                case "test":
                    processInstruction(scan, registers, dataSet, "test", flags, stack);
                    break;
                case "cmp":
                    processInstruction(scan, registers, dataSet, "cmp", flags, stack);
                    break;
                case "push":
                    processInstruction(scan, registers, dataSet, "push", flags, stack);
                    break;
                case "pop":
                    processInstruction(scan, registers, dataSet, "pop", flags, stack);
                    break;
                case "xchg":
                    processInstruction(scan, registers, dataSet, "xchg", flags, stack);
                    break;
                ///////////////jumps
                case "jump":
                    happened = jumpChecker(scan, "jump", input, stack, "jump", dataSet, registers, flags);
                    breakFlag =1;
                    break;
                case "loop":
                    happened = jumpChecker(scan, "loop", input, stack, "loop", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    //registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
                    break;
                case "loopz":
                    happened = jumpChecker(scan, "loopz", input, stack, "loop", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    //registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
                    break;
                case "loope":
                    happened = jumpChecker(scan, "loope", input, stack, "loop", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    //registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
                    break;
                case "loopnz":
                    happened = jumpChecker(scan, "loopnz", input, stack, "loop", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    //registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
                    break;
                case "loopne":
                    happened = jumpChecker(scan, "loopne", input, stack, "loop", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    //registers.get(2).value = Integer.toString(Integer.valueOf(registers.get(2).value) -1);
                    break;
                case "jz":
                    happened = jumpChecker(scan, "jz", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnz":
                    happened = jumpChecker(scan, "jnz", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jc":
                    happened = jumpChecker(scan, "jc", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                case "jnc":
                    happened = jumpChecker(scan, "jnc", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jo":
                    happened = jumpChecker(scan, "jo", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jno":
                    happened = jumpChecker(scan, "jno", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "js":
                    happened = jumpChecker(scan, "js", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jns":
                    happened = jumpChecker(scan, "jns", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jp":
                    happened = jumpChecker(scan, "jp", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnp":
                    happened = jumpChecker(scan, "jnp", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "je":
                    happened = jumpChecker(scan, "je", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jne":
                    happened = jumpChecker(scan, "jne", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jcxz":
                    happened = jumpChecker(scan, "jcxz", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jecxz":
                    happened = jumpChecker(scan, "jecxz", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "ja":
                    happened = jumpChecker(scan, "ja", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnbe":
                    happened = jumpChecker(scan, "jnbe", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jae":
                    happened = jumpChecker(scan, "jae", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnb":
                    happened = jumpChecker(scan, "jnb", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jb":
                    happened = jumpChecker(scan, "jb", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnae":
                    happened = jumpChecker(scan, "jnae", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jbe":
                    happened = jumpChecker(scan, "jbe", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jna":
                    happened = jumpChecker(scan, "jna", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jg":
                    happened = jumpChecker(scan, "jg", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnle":
                    happened = jumpChecker(scan, "jnle", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jge":
                    happened = jumpChecker(scan, "jge", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnl":
                    happened = jumpChecker(scan, "jnl", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jl":
                    happened = jumpChecker(scan, "jl", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jnge":
                    happened = jumpChecker(scan, "jnge", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jle":
                    happened = jumpChecker(scan, "jle", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "jng":
                    happened = jumpChecker(scan, "jng", input, stack, "jump", dataSet, registers, flags);
                    if (happened==true){
                        breakFlag =1;
                    }
                    break;
                case "call":
                    happened = jumpChecker(scan, "", input, stack, "call", dataSet, registers, flags);
                    break;
                case "invoke":
                    happened = jumpChecker(scan, "", input, stack, "invoke", dataSet, registers, flags);
                    break;
                case "ret":
                    breakFlag= 1;
                    break;
                case "main":
                    mainFlag= 1;
                    break;
                case "proc":
                    if (mainFlag== 0){
                        String l = "";
                        l = scan.next();
                        while (! l.equals("endp")){
                            l = scan.next().toLowerCase();
                        }
                    }else {
                        mainFlag = 0;
                    }
                    break;
                case "byte":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "byte");
                    break;
                case "sbyte":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "sbyte");
                    break;
                case "word":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "word");
                    break;
                case "sword":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "sword");
                    break;
                case "dword":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "dword");
                    break;
                case "sdword":
                    dataReaderCode(scan, idk.toLowerCase(), dataSet, "sdword");
                    break;
                default :
                    idk = instruction;
            }
            if (breakFlag==1){
                break;
            }
        }
    }

    public static void processInstruction(Scanner scan, ArrayList<Register> registers, ArrayList<Variable> dataSet, String operation, Flags flags, Stack stack) {
        String line = scan.nextLine().toLowerCase();
        ArrayList<String> input = Helper.seperator(line);
        String first = input.get(0);
        String second = input.get(1);
        ArrayList<String> helpFirst = Helper.arrayCheck(first);
        ArrayList<String> helpSecond = Helper.arrayCheck(second);
        int stackFlag = 0;
        int found = 1;
        /////low high check
        if (helpFirst.get(0).equals("ax") || helpFirst.get(0).equals("ah") || helpFirst.get(0).equals("al") ||
                helpFirst.get(0).equals("bx") || helpFirst.get(0).equals("bh") || helpFirst.get(0).equals("bl") ||
                helpFirst.get(0).equals("cx") || helpFirst.get(0).equals("ch") || helpFirst.get(0).equals("cl") ||
                helpFirst.get(0).equals("dx") || helpFirst.get(0).equals("dh") || helpFirst.get(0).equals("dl")) {
            found = 0;
        }
        if (helpSecond.get(0).equals("ax") || helpSecond.get(0).equals("ah") || helpSecond.get(0).equals("al") ||
                helpSecond.get(0).equals("bx") || helpSecond.get(0).equals("bh") || helpSecond.get(0).equals("bl") ||
                helpSecond.get(0).equals("cx") || helpSecond.get(0).equals("ch") || helpSecond.get(0).equals("cl") ||
                helpSecond.get(0).equals("dx") || helpSecond.get(0).equals("dh") || helpSecond.get(0).equals("dl")) {
            found = 0;
        }
        if (found ==0){
            semiRegAdder(registers);
        }
        ////xchg
        if (operation.equals("xchg")){
            int done = 0;
            for (Register reg : registers){
                if (reg.name.equals(helpFirst.get(0))){
                    done = 1;
                    int whatever = 0;
                    for (Register idk : registers){
                        if (idk.name.equals(helpSecond.get(0))){
                            String temp = reg.value;
                            reg.value = idk.value;
                            idk.value = temp;
                            whatever = 1;
                            break;
                        }
                    }
                    if (whatever==0){
                        for (Variable var : dataSet){
                            if (var.name.equals(helpSecond.get(0))){
                                String temp = reg.value;
                                reg.value = var.values.get(Integer.valueOf(helpSecond.get(2)));
                                var.values.set(Integer.valueOf(helpSecond.get(2)),   temp);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if (done==0){
                for (Variable var : dataSet){
                    if (var.name.equals(helpFirst.get(0))){
                        int whatever = 0;
                        for (Register reg : registers){
                            if (reg.name.equals(helpSecond.get(0))){
                                String temp = var.values.get(Integer.valueOf(helpFirst.get(2)));
                                var.values.set(Integer.valueOf(helpFirst.get(2)), reg.value);
                                reg.value = temp;
                                whatever = 1;
                                break;
                            }
                        }
                        if (whatever==0){
                            for (Variable idk : dataSet){
                                if (idk.name.equals(helpSecond.get(0))){
                                    String temp = var.values.get(Integer.valueOf(helpFirst.get(2)));
                                    var.values.set(Integer.valueOf(helpFirst.get(2)), idk.values.get(Integer.valueOf(helpSecond.get(2))));
                                    idk.values.set(Integer.valueOf(helpSecond.get(2)), temp);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        else if (second.contains("lengthof")){
            ////offset checker
            String idk = Helper.getStringAfter(second, "lengthof");
            String two = "";
            for (Variable var : dataSet){
                if (var.name.equals(idk)){
                    two = Helper.pureLength(var.values);
                    break;
                }
            }
            String one = "";
            for (Register reg : registers){
                if (reg.name.equals(helpFirst.get(0))){
                    one = reg.name;
                    regOperation(one, two, operation, registers, flags, stack, dataSet);
                    break;
                }
            }
            for (Variable var : dataSet){
                if (var.name.equals(helpFirst.get(0))){
                    one = var.name;
                    varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                    break;
                }
            }
        } else if (second.contains("sizeof")) {
            String idk = Helper.getStringAfter(second, "sizeof");
            String two = "";
            for (Variable var : dataSet){
                if (var.name.equals(idk)){
                    two = Helper.pureLength(var.values);
                    if (var.type.equals("byte") || var.type.equals("sbyte")){

                    }
                    if (var.type.equals("word") || var.type.equals("sword")){
                        two = Integer.toString((Integer.valueOf(two) *2));
                    }
                    if (var.type.equals("dword") || var.type.equals("sdword")){
                        two = Integer.toString((Integer.valueOf(two) *4));
                    }
                    break;
                }
            }
            String one = "";
            for (Register reg : registers){
                if (reg.name.equals(helpFirst.get(0))){
                    one = reg.name;
                    regOperation(one, two, operation, registers, flags, stack, dataSet);
                    break;
                }
            }
            for (Variable var : dataSet){
                if (var.name.equals(helpFirst.get(0))){
                    one = var.name;
                    varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                    break;
                }
            }
        } else if (second.contains("offset") || first.contains("offset")){
            if (operation.equals("push")){
                String idk = "offset";
                idk += Helper.removeSubstring(first,"offset");
                stack.push(idk,"4");
            } else {
                for (Register reg : registers){
                    if (reg.name.equals(helpFirst.get(0))){
                        int innerFound = 0;
                        for (Variable var : dataSet){
                            if (Helper.removeSubstring(helpSecond.get(0), "offset").equals(var.name)){
                                reg.pointingTo = var;
                                innerFound = 1;
                                break;
                            }
                        }
                        if (innerFound==0){
                            throw new RuntimeException();
                        }else {
                            break;
                        }
                    }
                }
            }
        }else {

            ////ptr check
            if (first.charAt(0)=='[' || second.charAt(0)=='['){
                ArrayList<String> tempOne = Helper.bracketFixer(first);
                ArrayList<String> tempTwo = Helper.bracketFixer(second);
                String one = "";
                if (first.charAt(0)=='['){
                    for (Register reg : registers){
                        if (reg.name.equals(Helper.bracketFixer(first).get(0)) && (reg.pointingTo !=null) ){
                            for (Variable var : dataSet){
                                if (var.equals(reg.pointingTo)){
                                    one = var.name;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    for (Variable var : dataSet){
                        if (var.name.equals(tempOne.get(0))){
                            one = var.name;;
                            break;
                        }
                    }
                }
                String two = "";
                if (second.charAt(0)=='['){
                    for (Register reg : registers){
                        if (reg.name.equals(Helper.bracketFixer(second).get(0)) && (reg.pointingTo !=null) ){
                            for (Variable var : dataSet){
                                if (var.equals(reg.pointingTo)){
                                    two = var.name;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    for (Variable var : dataSet){
                        if (var.name.equals(tempTwo.get(0))){
                            two = var.name;;
                            break;
                        }
                    }
                }

                try {
                    two =Integer.toString(Integer.valueOf(second));
                    varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                }catch (Exception e){
                    if (two.equals("")){
                        varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                    }
                    for (Variable var : dataSet){
                        if (var.name.equals(two)){
                            two = var.values.get(Integer.valueOf(tempTwo.get(2)));
                            varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                        }
                    }
                }
            }
            else if (helpFirst.get(0).contains("ptr") || helpSecond.get(0).contains("ptr")){
                String typeFirst = "";
                if (helpFirst.get(0).contains("ptr")){
                    if (helpFirst.get(0).contains("byte")){
                        typeFirst = "byte";
                    }else if (helpFirst.get(0).contains("word")){
                        typeFirst = "word";
                    }if (helpFirst.get(0).contains("dword")){
                        typeFirst = "dword";
                    }
                }
                String typeSecond = "";
                if (helpSecond.get(0).contains("ptr")){
                    if (helpSecond.get(0).contains("byte")){
                        typeSecond = "byte";
                    }else if (helpSecond.get(0).contains("word")){
                        typeSecond = "word";
                    }if (helpSecond.get(0).contains("dword")){
                        typeSecond = "dword";
                    }
                }
                ArrayList<String> tempOne = new ArrayList<String>();
                ArrayList<String> tempTwo = new ArrayList<String>();
                if (! typeFirst.equals("")){
                    tempOne = Helper.bracketFixer(first);
                }
                if (! typeSecond.equals("")){
                    tempTwo = Helper.bracketFixer(second);
                }

                if (! typeSecond.equals("")){
                    String two = "";
                    for (Variable var : dataSet){
                        if (var.name.equals(tempTwo.get(0))){
                            String idk2 = var.values.get(Integer.valueOf(tempTwo.get(2)));
                            if (typeSecond.equals("byte")){
                                if (var.type.equals("byte") || var.type.equals("sbyte")){
                                    two = idk2;
                                }
                                if (var.type.equals("word") || var.type.equals("sword")){
                                    two = Register.binaryToDecimal(Register.decimalToBinary(idk2).substring(16,32));
                                }
                                if (var.type.equals("dword") || var.type.equals("sdword")){
                                    two = Register.binaryToDecimal(Register.decimalToBinary(idk2).substring(24,32));
                                }
                            }
                            //
                            if (typeSecond.equals("word")){
                                if (var.type.equals("byte") || var.type.equals("sbyte")){
                                    two = Register.decimalToBinary(idk2).substring(16,32) + Register.decimalToBinary(var.values.get(Integer.valueOf(tempTwo.get(2)) + 1)).substring(16,32);
                                    two = Register.binaryToDecimal(two);
                                }
                                if (var.type.equals("word") || var.type.equals("sword")){
                                    two = idk2;
                                }
                                if (var.type.equals("dword") || var.type.equals("sdword")){
                                    two = Register.binaryToDecimal(Register.decimalToBinary(idk2).substring(16,32));
                                }
                            }
                            if (typeSecond.equals("dword")){
                                if (var.type.equals("byte") || var.type.equals("sbyte")){
                                    two = Register.decimalToBinary(idk2).substring(24,32) +
                                            Register.decimalToBinary(var.values.get(Integer.valueOf(tempTwo.get(2)) + 1)).substring(24,32) +
                                            Register.decimalToBinary(var.values.get(Integer.valueOf(tempTwo.get(2)) + 2)).substring(24,32) +
                                            Register.decimalToBinary(var.values.get(Integer.valueOf(tempTwo.get(2)) + 3)).substring(24,32);
                                    two = Register.binaryToDecimal(two);
                                }
                                if (var.type.equals("word") || var.type.equals("sword")){
                                    two = Register.decimalToBinary(idk2).substring(16,32) + Register.decimalToBinary(var.values.get(Integer.valueOf(tempTwo.get(2)) + 1)).substring(16,32);
                                    two = Register.binaryToDecimal(two);
                                }
                                if (var.type.equals("dword") || var.type.equals("sdword")){
                                    two = idk2;
                                }
                            }
                            break;
                        }
                    }
                    String one = helpFirst.get(0);
                    int isReg = 0;
                    for (Register reg : registers){
                        if (reg.name.equals(one)){
                            one = reg.name;
                            isReg = 1;
                            regOperation(one, two, operation, registers, flags, stack, dataSet);
                            break;
                        }
                    }
                    if (isReg==0){
                        for (Variable var : dataSet){
                            if (var.name.equals(one)){
                                one = var.name;
                                varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                            }
                        }
                    }
                }
            }
            else {
                for (Register reg : registers){
                    if (reg.name.equals(helpFirst.get(0))){
                        String one = reg.name;
                        String two = helpSecond.get(0);
                        for (Register idk : registers){
                            if (idk.name.equals(helpSecond.get(0))){
                                two = idk.value;
                                break;
                            }
                        }
                        for (Variable var : dataSet){
                            if (var.name.equals(helpSecond.get(0))){
                                two = var.values.get(Integer.valueOf(helpSecond.get(2)));
                                break;
                            }
                        }
                         regOperation(one, two, operation, registers, flags, stack, dataSet);
                        stackFlag =1;
                        break;
                    }
                }
                for (Variable var : dataSet){
                    if (var.name.equals(helpFirst.get(0))){
                        String one = var.name;
                        String two = helpSecond.get(0);
                        for (Register reg : registers){
                            if (reg.name.equals(helpSecond.get(0))){
                                two = reg.value;
                                break;
                            }
                        }
                        for (Variable idk : dataSet){
                            if (idk.name.equals(helpSecond.get(0))){
                                two = idk.values.get(Integer.valueOf(helpSecond.get(2)));
                                break;
                            }
                        }
                        varOperation(one, two, operation, dataSet, Integer.valueOf(helpFirst.get(2)), flags, stack);
                        stackFlag =1;
                        break;
                    }
                }
            }
            try {
                Integer.valueOf(first);
            }catch (Exception e){
                stackFlag = 1;
            }
            if (stackFlag==0){
                stack.push(Register.decimalToBinary(first), "4");
            }
            if (found==0){
                semiRegRemover(registers);
            }
        }

    }
    public static void regOperation(String first, String second, String operator, ArrayList<Register> registers, Flags flags, Stack stack, ArrayList<Variable> dataSet){
        switch (operator) {
            case "add":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        int value = Integer.parseInt(reg.value) + Integer.parseInt(second);
                        reg.value = Integer.toString(value);
                        regUpdateFlag(flags, reg, 4);
                        break;
                    }
                }
                break;
            case "sub":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        int value = Integer.parseInt(reg.value) - Integer.parseInt(second);
                        reg.value = Integer.toString(value);
                        regUpdateFlag(flags, reg, 4);
                        break;
                    }
                }
                break;
            case "mov":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        reg.value = second;
                        break;
                    }
                }
                break;
            case "movzx":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        reg.value = second;
                        reg.changed = true;
                        break;
                    }
                }
                break;
            case "movsx":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        reg.value = second;
                        break;
                    }
                }
                break;
            case "inc":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value);
                        num ++;
                        reg.value = Integer.toString(num);
                        regUpdateFlag(flags, reg, 4);
                        break;
                    }
                }
                break;
            case "dec":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value);
                        num --;
                        reg.value = Integer.toString(num);
                        regUpdateFlag(flags, reg, 4);
                        break;
                    }
                }
                break;
            case "xor":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        int x = Integer.valueOf(reg.value);
                        int y = Integer.valueOf(second);
                        Integer num = x ^ y;
                        reg.value = Integer.toString(num);
                        break;
                    }
                }
                break;
            case "and":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        int x = Integer.valueOf(reg.value);
                        int y = Integer.valueOf(second);
                        Integer num = x & y;
                        reg.value = Integer.toString(num);
                        break;
                    }
                }
                break;
            case "not":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = ~ Integer.valueOf(reg.value);
                        reg.value = Integer.toString(num);
                        break;
                    }
                }
                break;
            case "or":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value) | Integer.valueOf(second);
                        reg.value = Integer.toString(num);
                        break;
                    }
                }
                break;
            case "neg":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value) * -1;
                        reg.value = Integer.toString(num);
                        regUpdateFlag(flags, reg, 4);
                        break;
                    }
                }
                break;
            case "cmp":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value) - Integer.valueOf(second);
                        Register temp = new Register("temp");
                        temp.value = Integer.toString(num);
                        regUpdateFlag(flags, temp, 4);
                        break;
                    }
                }
                break;
            case "test":
                for (Register reg : registers){
                    if (reg.name.equals(first)){
                        Integer num = Integer.valueOf(reg.value) & Integer.valueOf(second);
                        Register temp = new Register("temp");
                        temp.value = Integer.toString(num);
                        regUpdateFlag(flags, temp, 4);
                        break;
                    }
                }
                break;
            case "push":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        stack.push(Register.decimalToBinary(reg.value), "4");
                        break;
                    }
                }
                break;
            case "pop":
                for (Register reg : registers) {
                    if (reg.name.equals(first)) {
                        if (stack.addresses.substring(stack.esp-32, stack.esp).contains("offset")){
                            String line = Helper.getStringAfter(stack.addresses, "offset");
                            String idk = "";
                            stack.esp -= stack.addresses.length() - stack.addresses.indexOf("offset");
                            stack.addresses = stack.addresses.substring(0, stack.addresses.indexOf("offset"));

                            for (Variable idccc : dataSet){
                                if (idccc.name.equals(line)){
                                    reg.pointingTo = idccc;
                                    break;
                                }
                            }
                            break;
                        }else{
                            String value = stack.addresses.substring(stack.esp-32, stack.esp);
                            stack.addresses = stack.addresses.substring(0, stack.esp-32);
                            stack.esp -= 32;
                            reg.value = Register.binaryToDecimal(value);
                            break;
                        }
                    }
                }
                break;
            // Add more operations here
        }
        //return registers;
    }
    public static void varOperation(String first, String second, String operator, ArrayList<Variable> dataSet, Integer index, Flags flags, Stack stack) {
        switch (operator) {
            case "add":
                for (Variable var : dataSet) {
                    if (var.name.equals(first)) {
                        int value = Integer.parseInt(var.values.get(index)) + Integer.parseInt(second);
                        var.values.set(index, Integer.toString(value));
                        String line = var.values.get(index);
                        varUpdateFlag(flags, line, Helper.typeFind(var.type.toLowerCase()));
                        break;
                    }
                }
                break;
            case "sub":
                for (Variable var : dataSet) {
                    if (var.name.equals(first)) {
                        int value = Integer.parseInt(var.values.get(index)) - Integer.parseInt(second);
                        var.values.set(index, Integer.toString(value));
                        break;
                    }
                }
                break;
            case "mov":
                for (Variable var : dataSet) {
                    if (var.name.equals(first)) {
                        var.values.set(index, second);
                        break;
                    }
                }
                break;
            case "inc":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        Integer num = Integer.valueOf(var.values.get(index));
                        num ++;
                        var.values.set(index, Integer.toString(num)) ;
                        break;
                    }
                }
                break;
            case "dec":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        Integer num = Integer.valueOf(var.values.get(index));
                        num --;
                        var.values.set(index, Integer.toString(num));
                        String line = var.values.get(index);
                        varUpdateFlag(flags, line, Helper.typeFind(var.type.toLowerCase()));
                        break;
                    }
                }
                break;
            case "xor":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index));
                        int y = Integer.valueOf(second);
                        Integer num = x ^ y;
                        var.values.set(index, Integer.toString(num));
                        break;
                    }
                }
                break;
            case "or":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index));
                        int y = Integer.valueOf(second);
                        Integer num = x | y;
                        var.values.set(index, Integer.toString(num));
                        break;
                    }
                }
                break;
            case "and":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index));
                        int y = Integer.valueOf(second);
                        Integer num = x & y;
                        var.values.set(index, Integer.toString(num));
                        break;
                    }
                }
                break;
            case "not":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index));
                        Integer num = ~ x;
                        var.values.set(index, Integer.toString(num));
                        break;
                    }
                }
                break;
            case "neg":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index)) * -1;
                        Integer num = x;
                        var.values.set(index, Integer.toString(num));
                        String line = var.values.get(index);
                        varUpdateFlag(flags, line, Helper.typeFind(var.type.toLowerCase()));
                        break;
                    }
                }
                break;
            case "cmp":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index)) - Integer.valueOf(second);
                        Integer num = x;
                        String line = Integer.toString(num);
                        varUpdateFlag(flags, line, Helper.typeFind(var.type.toLowerCase()));
                        break;
                    }
                }
                break;
            case "test":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        int x = Integer.valueOf(var.values.get(index)) & Integer.valueOf(second);
                        Integer num = x;
                        String line = Integer.toString(num);
                        varUpdateFlag(flags, line, Helper.typeFind(var.type.toLowerCase()));
                        break;
                    }
                }
                break;
            case "push":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        String line = Register.decimalToBinary(var.values.get(index));
                        if (var.type.toLowerCase().equals("byte") ||var.type.toLowerCase().equals("sbyte")){
                            throw new RuntimeException();
                        }
                        if (var.type.toLowerCase().equals("word") ||var.type.toLowerCase().equals("sword")){
                            stack.push(line.substring(line.length()-16, line.length()), "2");
                        }
                        if (var.type.equals("dword") ||var.type.equals("sdword")){
                            stack.push(line, "4");
                        }
                        break;
                    }
                }
                break;
            case "pop":
                for (Variable var : dataSet){
                    if (var.name.equals(first)){
                        if (stack.addresses.substring(stack.esp-32, stack.esp).contains("offset")){
                            String line = Helper.getStringAfter(stack.addresses, "offset");
                            String idk = "";
                            stack.esp -= stack.addresses.length() - stack.addresses.indexOf("offset");
                            stack.addresses = stack.addresses.substring(0, stack.addresses.indexOf("offset"));
                            for (Variable idccc : dataSet){
                                if (idccc.name.equals(line)){
                                    var.values.set(index, line);
                                }
                            }
                            break;
                        }else {
                            String line ;
                            if (var.type.equals("byte") ||var.type.equals("sbyte")){
                                throw new RuntimeException();
                            }
                            if (var.type.equals("word") ||var.type.equals("sword")){
                                line = stack.addresses.substring(stack.esp-16, stack.esp);
                                stack.addresses = stack.addresses.substring(0, stack.esp-16);
                                stack.esp -= 16;
                                var.values.set(index,Register.binaryToDecimal(line));
                            }
                            if (var.type.equals("dword") ||var.type.equals("sdword")){
                                line = stack.addresses.substring(stack.esp-32, stack.esp);
                                stack.addresses = stack.addresses.substring(0, stack.esp-32);
                                stack.esp -= 32;
                                var.values.set(index,Register.binaryToDecimal(line));
                            }
                            break;
                        }
                    }
                }
                break;
            // Add more operations here
        }
    }
    public static void regUpdateFlag(Flags flags, Register reg, Integer byteCount){

        if (Integer.valueOf(reg.value) < 0){
            flags.sign =1;
            flags.carry = 1;
        }else {
            flags.sign =0;
            flags.carry = 0;
        }
        //
        if (Integer.valueOf(reg.value) == 0){
            flags.zero = 1;
        }else {
            flags.zero = 0;
        }
        //
        if (byteCount==2){
            if (Integer.valueOf(reg.value)>65535){
                flags.carry = 1;
            }else {
                flags.carry = 0;
            }
            //
            if (Integer.valueOf(reg.value)<-32768 || Integer.valueOf(reg.value)>32767){
                flags.overFlow = 1;
            }else {
                flags.overFlow =0;
            }
        }else if (byteCount==1){
            if (Integer.valueOf(reg.value)>255){
                flags.carry = 1;
            }else {
                flags.carry =0;
            }
            //
            if (Integer.valueOf(reg.value)<-128 || Integer.valueOf(reg.value)>127){
                flags.overFlow = 1;
            }else {
                flags.overFlow =0;
            }
        }
        //
        int count = 0;
        String line = Register.decimalToBinary(reg.value);
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '1'){
                count++;
            }
        }
        if (count%2 ==0){
            flags.parity = 1;
        }else {
            flags.parity = 0;
        }
    }
    public static void varUpdateFlag(Flags flags, String var, Integer byteCount){

        if (Integer.valueOf(var) < 0){
            flags.sign =1;
        }else {
            flags.sign =0;
        }
        //
        if (Integer.valueOf(var) == 0){
            flags.zero = 1;
        }else {
            flags.zero = 0;
        }
        //
        if (byteCount==2){
            if (Integer.valueOf(var)>65535 || Integer.valueOf(var)<0){
                flags.carry = 1;
            }else {
                flags.carry = 0;
            }
            //
            if (Integer.valueOf(var)<-32768 || Integer.valueOf(var)>32767){
                flags.overFlow = 1;
            }else {
                flags.overFlow =0;
            }
        }else if (byteCount==1){
            if (Integer.valueOf(var)>255 || Integer.valueOf(var)<0){
                flags.carry = 1;
            }else {
                flags.carry =0;
            }
            //
            if (Integer.valueOf(var)<-128 || Integer.valueOf(var)>127){
                flags.overFlow = 1;
            }else {
                flags.overFlow =0;
            }
        }else if (byteCount==4){
            if (Integer.valueOf(var)<0){
                flags.carry =1;
            }else {
                flags.carry =0;
            }
        }
        //
        int count = 0;
        String line = Register.decimalToBinary(var);
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '1'){
                count++;
            }
        }
        if (count%2 ==0){
            flags.parity = 1;
        }else {
            flags.parity = 0;
        }
    }

    public static void semiRegRemover(ArrayList<Register> registers){
        for (Register outer : registers){
            for (Register inner : registers){
                if (inner.changed==false){
                    if (inner.name.charAt(1) == outer.name.charAt(0)){
                        if (outer.name.charAt(1) == 'x'){
                            String line = Register.decimalToBinary(inner.value).substring(0,16) + Register.decimalToBinary(outer.value).substring(16,32);
                            inner.value = Register.binaryToDecimal(line);
                        }
                        if (outer.name.charAt(1) == 'h'){
                            String line = Register.decimalToBinary(inner.value).substring(0,16) + Register.decimalToBinary(outer.value).substring(24,32)
                                    + Register.decimalToBinary(inner.value).substring(24,32);
                            inner.value = Register.binaryToDecimal(line);
                        }
                        if (outer.name.charAt(1) == 'l'){
                            String line = Register.decimalToBinary(inner.value).substring(0,24) + Register.decimalToBinary(outer.value).substring(24,32);
                            inner.value = Register.binaryToDecimal(line);
                        }
                        break;
                    }
                }
            }
        }
        int size = registers.size();
        for (int i = 8; i < size; i++) {
            registers.remove(8);
        }
    }
    public static void semiRegAdder(ArrayList<Register> registers){
        Register ax = new Register("ax");
        Register al = new Register("al");
        Register ah = new Register("ah");

        Register bx = new Register("bx");
        Register bh = new Register("bh");
        Register bl = new Register("bl");

        Register cx = new Register("cx");
        Register ch = new Register("ch");
        Register cl = new Register("cl");

        Register dx = new Register("dx");
        Register dh = new Register("dh");
        Register dl = new Register("dl");

        int count = 0;
        for (Register reg : registers){
            count++;
            if (reg.name.equals("eax")){
                ax.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,32));
                ah.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,24));
                al.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(24,32));
            }
            if (reg.name.equals("ebx")){
                bx.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,32));
                bh.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,24));
                bl.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(24,32));
            }
            if (reg.name.equals("ecx")){
                cx.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,32));
                ch.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,24));
                cl.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(24,32));
            }
            if (reg.name.equals("edx")){
                dx.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,32));
                dh.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(16,24));
                dl.value = Register.binaryToDecimal(Register.decimalToBinary(reg.value).substring(24,32));
            }
            if (count==3){
                break;
            }
        }
        registers.add(ax);
        registers.add(ah);
        registers.add(al);

        registers.add(bx);
        registers.add(bh);
        registers.add(bl);

        registers.add(cx);
        registers.add(ch);
        registers.add(cl);

        registers.add(dx);
        registers.add(dh);
        registers.add(dl);
    }

    public static ArrayList<Register> regCreator(){
        Register eax = new Register("eax");
        Register ebx = new Register("ebx");
        Register ecx = new Register("ecx");
        Register edx = new Register("edx");
        Register esi = new Register("esi");
        Register edi = new Register("edi");
        Register esp = new Register("esp");
        Register ebp = new Register("ebp");
        ArrayList<Register> registers = new ArrayList<Register>();
        registers.add(eax);
        registers.add(ebx);
        registers.add(ecx);
        registers.add(edx);
        registers.add(esi);
        registers.add(edi);
        registers.add(esp);
        registers.add(ebp);
        return registers;
    }
    public static ArrayList<Variable> dataReader(Scanner scan){
        ArrayList<Variable> dataSet = new ArrayList<Variable>();
        int start = 0 ;
        while (true){
            String name = scan.next().toLowerCase();
            if (name.toLowerCase().equals(".code")){
                break;
            } else if (name.toLowerCase().equals(".data")) {
                start = 1;
            } else if (start==1){
                String type = "";
                if (name.equals("byte") || name.equals("sbyte") || name.equals("word") || name.equals("sword") || name.equals("dword") || name.equals("sdword")){
                    type= name;
                    name = "";
                }else {
                    type = scan.next().toLowerCase();
                }
                String line = scan.nextLine();
                String val = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line.toCharArray()[i] != ' ' && line.toCharArray()[i]!= '<' && line.toCharArray()[i]!='>' && line.toCharArray()[i]!= '"'){
                        val += line.toCharArray()[i];
                    }

                }
                Variable variable = new Variable(name,type,Helper.arraySeperator(val));
                try {
                    Integer.valueOf(variable.values.get(0));
                }catch (RuntimeException e){
                    ArrayList<String> idk = new ArrayList<String>();
                    for (int i = 0; i < variable.values.size(); i++) {
                        for (int j = 0; j < variable.values.get(i).length(); j++) {
                            String l = "";
                            String dummy = "";
                            dummy += variable.values.get(i).charAt(j);
                            l += Register.binaryToDecimal(Helper.stringToAsciiBinary(dummy));
                            idk.add(l);
                        }
                    }
                    variable.values = idk;
                }
                if (type.equals("byte") ||type.equals("sbyte")){

                } else if (type.equals("word") ||type.equals("sword")) {
                    variable.values = Helper.adjustList(variable.values, 2);
                } else if (type.equals("dword") ||type.equals("sdword")) {
                    variable.values = Helper.adjustList(variable.values, 4);

                }
                dataSet.add(variable);
            }
        }

        return dataSet;
    }
    public static void dataReaderCode(Scanner scan, String word, ArrayList<Variable> dataSet, String type){
        String line = scan.nextLine().toLowerCase();
        ArrayList<String> values = Helper.arrayFiller(line);
        Variable idkooo = new Variable(word, type, values);
        try {
            Integer.valueOf(idkooo.values.get(0));
        }catch (RuntimeException e){
            ArrayList<String> idccc = new ArrayList<String>();
            for (int i = 0; i < idkooo.values.size(); i++) {
                for (int j = 0; j < idkooo.values.get(i).length(); j++) {
                    String l = "";
                    String dummy = "";
                    dummy += idkooo.values.get(i).charAt(j);
                    l += Register.binaryToDecimal(Helper.stringToAsciiBinary(dummy));
                    idccc.add(l);
                }
            }
            idkooo.values = idccc;
        }
        if (type.equals("byte") ||type.equals("sbyte")){

        } else if (type.equals("word") ||type.equals("sword")) {
            idkooo.values = Helper.adjustList(idkooo.values, 2);
        } else if (type.equals("dword") ||type.equals("sdword")) {
            idkooo.values = Helper.adjustList(idkooo.values, 4);

        }
        dataSet.add(idkooo);
    }
    public static Integer regFind(String name, ArrayList<Register> list){
        Integer num = -1;
        for (Register reg : list){
            if (reg.name.equals(name.toLowerCase())){
                num = list.indexOf(reg);
            }
        }
        return num;
    }
    public static Integer varFind(String name, ArrayList<Variable> list){
        Integer num = -1;
        for (Variable var : list){
            if (var.name.equals(name.toLowerCase())){
                num = list.indexOf(var);
            }
        }
        return num;
    }

}
