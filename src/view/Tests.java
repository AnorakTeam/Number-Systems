package view;

import model.ByteRepresentation;

public class Tests {

    public static void main(String[] args) {
        ByteRepresentation rep = new ByteRepresentation(1, ByteRepresentation.MEGA, true);
        println(rep.representSIPower(ByteRepresentation.TERA));
        println(rep.representISO_IECPower(ByteRepresentation.TERA));
        println(rep);
    }

    public static void println(){
        System.out.println();
    }

    public static void println(Object msg){
        System.out.println(msg);
    }
}

