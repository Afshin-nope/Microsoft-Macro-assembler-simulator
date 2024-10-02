public class Register {
    public String name;
    public String value;
    public Variable pointingTo = null;
    public Integer count ;
    public Boolean changed = false;


    public Register(String name) {
        this.name = name;
        this.value = "0";
    }

    public void eraser() {
        this.value = "0";
    }

    public static String decimalToBinary(String decimal) {
        int decimalNumber = Integer.parseInt(decimal);

        if (decimalNumber == 0) {
            return "00000000000000000000000000000000";
        }

        StringBuilder binaryString = new StringBuilder();

        for (int i = 31; i >= 0; i--) {
            int mask = 1 << i;
            binaryString.append((decimalNumber & mask) != 0 ? "1" : "0");
        }

        if (decimalNumber > 0) {
            String line = binaryString.toString().replaceFirst("^0+(?!$)", "");
            String output = "";
            if (line.length()<32){
                for (int i = 0; i < 32 - line.length(); i++) {
                    output+= 0;
                }
                output += line;
                return output;
            }
        }

        return binaryString.toString();
    }


    public static String binaryToDecimal(String binaryString) {
        boolean isNegative = binaryString.charAt(0) == '1';

        if (isNegative) {
            StringBuilder flipped = new StringBuilder();
            for (int i = 0; i < binaryString.length(); i++) {
                flipped.append(binaryString.charAt(i) == '0' ? '1' : '0');
            }
            int absoluteValue = Integer.parseInt(flipped.toString(), 2) + 1;
            absoluteValue = -absoluteValue;
            return Integer.toString(absoluteValue);
        } else {
            return Integer.toString(Integer.parseInt(binaryString, 2));
        }
    }

    public static String binaryToHex(String binaryNumber) {
        int number = (int) Long.parseLong(binaryNumber, 2);

        String hexString = Integer.toHexString(number);

        if (hexString.length() < 8) {
            hexString = String.format("%8s", hexString).replace(' ', '0');
        } else if (hexString.length() > 8) {
            hexString = hexString.substring(hexString.length() - 8);
        }

        hexString = hexString.toUpperCase();

        return  hexString;
    }


    public static String hexToBinary(String hexString) {
        StringBuilder binaryString = new StringBuilder();

        for (int i = 0; i < hexString.length(); i++) {
            char hexDigit = hexString.charAt(i);
            int decimalValue = Character.digit(hexDigit, 16);

            String binaryChunk = String.format("%4s", Integer.toBinaryString(decimalValue)).replace(' ', '0');
            binaryString.append(binaryChunk);
        }

        String result = binaryString.toString();
        while (result.startsWith("0") && result.length() > 1) {
            result = result.substring(1);
        }

        return result;
    }

    public void errorCheck(String type){
        if (type.toLowerCase().equals("l")){
            if (Integer.valueOf(this.value)> 255){
                throw new RuntimeException();
            }
        }
        if (type.toLowerCase().equals("h")){
            if (Integer.valueOf(this.value)> 127 || Integer.valueOf(this.value)< -128){
                throw new RuntimeException();
            }
        }
        if (type.toLowerCase().equals("x")){
            if (Integer.valueOf(this.value)> 65535){
                throw new RuntimeException();
            }
        }
        if (type.toLowerCase().equals("full")){
            if (Integer.valueOf(this.value)> 2147483647 || Integer.valueOf(this.value)< -2147483648){
                throw new RuntimeException();
            }
        }
    }

}
