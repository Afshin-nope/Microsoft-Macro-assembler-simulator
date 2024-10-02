public class Flags {
    public Integer overFlow =0;
    public Integer carry =0;
    public Integer zero =0;
    public Integer sign =0;
    public Integer auxiliary =0;
    public Integer parity =0;


    public String makeString(){
        return ("overflow : " + this.overFlow + " carry : " + this.carry + " zero : " + this.zero + " sign : " + this.sign + " auxiliary : " + this.auxiliary + " parity : " + this.parity);
    }
}
