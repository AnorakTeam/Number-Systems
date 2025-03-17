package model;

/**
 * Class that handles the numbers and it's conversions in the following numeral systems:
 * Decimal, Binary, Octal and Hexadecimal.
 * 
 * In order to create a new number with a String as an argument, the number must be validated
 * beforehand, otherwise it will create an exception. 
 * 
 * @author josemanuelpr@ufps.edu.co
 */
public class Number implements NumberInterface{

    final public static byte DECIMAL = 0;
    final public static byte BINARY = 1;
    final public static byte OCTAL = 2;
    final public static byte HEX = 3;

    private int number;
    private int type;

    /**
     * Defaults the given integer number to a decimal value.
     * @param number the decimal value of the number.
     */
    public Number(int number){
        if(number < 0)
            throw new IllegalArgumentException("Invalid number: it can't be negative.");

        this.number = number;
        this.type = DECIMAL;
    }

    /**
     *  Constructor with two parameter for having a number: Given a 
     *  integer in decimal value that represents any of the following numeral systems: 
     *  Decimal, Binary, Octal and Hexadecimal, and a number indicating the type it is, 
     *  creates the object. If the type entered is not between 0 and 3 inclusive, it 
     *  defaults to a decimal.
     * 
     * @param number A valid number as a decimal value from the following numeral 
     *               systems: Decimal, Binary, Octal or Hexadecimal.
     * @param type A number between 0 and 3 both inclusive, being 0:decimal, 1:binary,
     *             2:octal, 3:hex. Instead of hardcoding the number in the call of the constructor,
     *             use the static values inside this class (e.g Number.HEX)
     */
    public Number(int number, int type){
        this(number);
        if( !(0 <= type && type <=3) )
            type = DECIMAL;
        else {
            this.type = type;
        }
    }

    /**
     *  Constructor with two parameter for having a number: Given a String that represents
     *  a valid number in the following systems: Decimal, Binary, Octal, Hexadecimal
     *  and a number indicating the type it is, creates the object.
     *  If the type entered is not between 0 and 3 inclusive, it defaults to a decimal.
     *   
     * @param number A valid number as a String from the following numeral 
     *               systems: Decimal, Binary, Octal or Hexadecimal.
     * @param type A number between 0 and 3 both inclusive, being 0:decimal, 1:binary,
     *             2:octal, 3:hex. Instead of hardcoding the number in the call of the constructor,
     *             use the static values inside this class (e.g Number.HEX)
     */
    public Number(String number, int type){
        this.type = type;

        if (!this.isValidNumber(number, type)) {
            throw new IllegalArgumentException("Invalid number format for the specified numeral system.");
        }

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

        if (this.number < 0) {
            throw new IllegalArgumentException("Negative numbers are not allowed.");
        }
    }

    /**
     * Validates whether the given string represents a valid number in the specified numeral system.
     * 
     * Given a string representing a number and a type indicating the numeral system 
     * (Decimal, Binary, Octal, or Hexadecimal), this method checks if the number is 
     * correctly formatted according to its numeral system.
     * 
     * Additionally, this method does not allow negative numbers. If the given string contains 
     * a negative sign, it will be considered invalid.
     * 
     * @param number The string representation of the number.
     * @param type A number between 0 and 3 both inclusive, being 0:decimal, 1:binary, 
     *             2:octal, 3:hex. Instead of hardcoding the number in the call of this method,
     *             use the static values inside this class (e.g Number.HEX)
     * 
     * @return true if the number is valid for the given numeral system, false otherwise.
     */
    private boolean isValidNumber(String number, int type) {
        if (number == null || number.isEmpty()) {
            return false;
        }
    
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
    
            switch (type) {
                case BINARY:
                    if (ch != '0' && ch != '1') {
                        return false;
                    }
                    break;
    
                case OCTAL:
                    if (ch < '0' || ch > '7') {
                        return false;
                    }
                    break;
    
                case HEX:
                    if (!((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))) {
                        return false;
                    }
                    break;
    
                default:
                    if (ch < '0' || ch > '9') {
                        return false;
                    }
                    break;
            }
        }
    
        return true;
    }

    private int convertBinaryToDecimal(String binary){
        int decimal = 0;
        int product = 1;

        while(!binary.equals("")){
            char nextDigit = binary.charAt(binary.length()-1); 
            if(nextDigit == '1'){
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

    public int getType(){
        return this.type;
    }
}
