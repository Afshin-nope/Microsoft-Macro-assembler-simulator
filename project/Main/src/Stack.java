public class Stack {
    public String addresses = "";
    public int esp = 0;

    public void push(String value, String type){
        if (type.equals("2")){
            this.addresses += value;
            this.esp += value.length();
        }
        else {
            this.addresses += value;
            this.esp += value.length();
        }
    }

    public String pop(String type){
        String output = "";
        if (type.equals("2")){
            output += this.addresses.substring(esp-1);
            esp -= 1;
        }else {
            output += this.addresses.substring(esp-3);
            esp -= 3;
        }
        return output;
    }
}
