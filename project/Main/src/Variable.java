import javax.lang.model.type.NullType;
import java.util.ArrayList;

public class Variable {
    public String name;
    public String type;
    public ArrayList<String> values;

    public Variable(String name, String type, ArrayList<String> values) {
        this.name = name;
        this.type = type;
        this.values = values;
    }

    @Override
    public String toString() {
        return "name: " + name + ", type: " + type + ", values: " + values;
    }

    public void errorCheck(){
        if (this.type.toLowerCase().equals("byte")){
            for (String val : this.values){
                if (Integer.valueOf(val)> 255 && !val.equals("")){
                    throw new RuntimeException();
                }
            }
        }
        if (this.type.toLowerCase().equals("sbyte")){
            for (String val : this.values){
                if ((Integer.valueOf(val)> 127 || Integer.valueOf(val)< -128) && !val.equals("")){
                    throw new RuntimeException();
                }
            }
        }
        if (this.type.toLowerCase().equals("word")){
            for (String val : this.values){
                if (Integer.valueOf(val)> 65535 && !val.equals("")){
                    throw new RuntimeException();
                }
            }
        }
        if (this.type.toLowerCase().equals("sdword")){
            for (String val : this.values){
                if ((Integer.valueOf(val)> 2147483647 || Integer.valueOf(val)< -2147483648)  && !val.equals("")){
                    throw new RuntimeException();
                }
            }
        }
    }

}
