package model;

/**
 * 
 */
public class Number implements NumberInterface{

    final public static byte DECIMAL = 0;
    final public static byte BINARY = 1;
    final public static byte OCTAL = 2;
    final public static byte HEX = 3;

    private int number;
    private int type;

    public Number(){}

    public Number(int number){
        this.number = number;
        this.type = DECIMAL;
    }

    public Number(int number, int type){
        this(number);
        this.type = type;
    }

    public Number(String number, int type){
        this.type = type;
        switch (type) {
            case BINARY:
                this.number = convertBinaryToDecimal(number);
                break;

            case OCTAL:
                this.number = convertOctalToDecimal(number);
                break;

            case HEX:
                this.number = convertHexToDecimal(number);
                break;

            default:
                this.number = Integer.parseInt(number);
                break;
        }
    }

    private int convertBinaryToDecimal(String binary){
        int decimal = 0;
        int product = 1;

        while(!binary.equals("")){
            if(binary.charAt(binary.length()-1) == '1'){
                decimal += product;
            }

            binary = binary.substring(0, binary.length()-1);
            product *= 2;
        }

        return decimal;
    }

    private int convertOctalToDecimal(String octal){
        int decimal = 0;
        int product = 1;
        int digit = 0;

        while(!octal.equals("")){
            digit = Character.getNumericValue(octal.charAt(octal.length()-1));
            decimal += product * digit;

            octal = octal.substring(0, octal.length()-1);
            product *= 8;
        }

        return decimal;
    }

    private int convertHexToDecimal(String hex){
        int decimal = 0;
        int product = 1;
        int digit = 0;

        while(!hex.equals("")){
            digit = determineHexValueFromChar(hex.charAt(hex.length()-1));
            decimal += product * digit;

            hex = hex.substring(0, hex.length()-1);
            product *= 16;
        }

        return decimal;
    }

    private int determineHexValueFromChar(char digit){
        int integerValue = Character.getNumericValue(digit);

        if( ! (0 <= integerValue && integerValue <= 15))
            throw new RuntimeException("The value is not a valid hex digit: " + digit + ", " + integerValue);

        return integerValue;
    }

    @Override
    public String toString(){
        if(this.type == BINARY)
            return this.getBinary();
        
        if(this.type == OCTAL)
            return this.getOctal();

        if(this.type == HEX){
            return this.getHex();
        }

        return this.getDecimal();
    }

    @Override
    public String getDecimal() {
        return "" + this.number;
    }

    @Override
    public String getOctal() {
        String octal = "";
        int copyOfNumber = this.number;

        do {
            octal = "" + copyOfNumber % 8 + octal;
            copyOfNumber /= 8;
        } while(copyOfNumber > 0);
        
        return octal;
    }

    @Override
    public String getHex() {
        String hex = "";
        int copyOfNumber = this.number;
        int nextDigit = 0;

        do {
            nextDigit = copyOfNumber % 16;
            hex = determineHexValue(nextDigit) + hex;
            copyOfNumber /= 16;
        } while(copyOfNumber > 0);

        return hex;
    }

    private String determineHexValue(int digit){
        if(digit < 0 || 15 < digit) 
            throw new RuntimeException("The decimal digit is NOT a valid hex: " + digit);
        
        if(0 <= digit && digit <= 9)
            return "" + digit;
        
        byte letterAInAscii = 65;
        char resultingCharacter = (char)(letterAInAscii + digit - 10);
        return "" + resultingCharacter;
    }

    
    @Override
    public String getBinary() {
        String binary = "";
        int copyOfNumber = this.number;

        while(copyOfNumber > 0){
            binary = (copyOfNumber & 1) + "" + binary;
            copyOfNumber >>= 1;
        }

        return binary;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }
}
