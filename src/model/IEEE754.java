package model;


/**
 * 
 * @author josemanuelpr@ufps.edu.co
 */
public class IEEE754 {

    // index/position of the mantissa in both presicions. Used in the for-loops.
    private final byte SIMPLE_MANTISSA = 23;
    private final byte DOUBLE_MANTISSA = 52;

    // index/position of the exponent in both precisions. Used in the for-loops.
    private final byte SIMPLE_EXPONENT = 8;
    private final byte DOUBLE_EXPONENT = 11;

    // The precisions for substracting to the exponents.
    private final byte SIMPLE_PRECISION = 127;
    private final short DOUBLE_PRECISION = 1023;

    // These are the positions of the signs in the two precisions, they are just the 
    // min value of each primitive type.
    private final long SIMPLE_SIGN = Integer.MIN_VALUE;
    private final long DOUBLE_SIGN = Long.MIN_VALUE;

    private final long DOUBLE_UPPER_MASK = -4294967296l; // 32 ones at the right, 32 zeros at the left
    //private final long DOUBLE_LOWER_MASK = 4294967295l; // the opposite of the upper mask, i don't  
                                                          // think i will use it, although i'll leave it here

    // The length of the simple and double precision numbers, used in the for-loops
    private final int SIMPLE_LENGTH = 32;
    private final int DOUBLE_LENGTH = 64;


    // Attributes, a long value that saves either simple or double precision IEEE numbers
    // and a boolean indicating if the binary is 32 (true) or 64 (false) bits.
    private long binary = 0;
    private boolean isSimple; 

    /**
     * Creates an object that handles all 32-bit IEEE754 parts with the given decimal number.
     * @param number Float type number to convert.
     */
    public IEEE754(float number){
        int simple = Float.floatToRawIntBits(number);
        this.setSimpleIEEE754(simple);
        this.isSimple = true;
    }

    /**
     * Creates an object that handles all 64-bit IEEE754 parts with the given decimal number.
     * @param number Double type number to convert.
     */
    public IEEE754(double number){
        this.binary = Double.doubleToRawLongBits(number);
        this.isSimple = false;
    }

    /**
     * Creates an object that handles all 64-bit IEEE754 parts with the given binary String 
     * and a boolean value indicating if it's simple or double precision.
     * @param number Binary string with 32 or 64 0s and 1s.
     * @param isSimple Indicates the precision: simple (true) or double (false).
     * @throws IllegalArgumentException If the given String is not a valid 32 or 64 bit IEEE754 number.
     */
    public IEEE754(String number, boolean isSimple) throws IllegalArgumentException{
        try{
            this.validateIEE754String(number);
            convertIEEE754StringToLong(number);
            this.isSimple = isSimple;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    /**
     * Checks if the given binary string is valid for a IEEE754 number.
     * The criteria is the following: The string must contain 32 or 64 characters 
     * and the string must only contain 0s and 1s.
     * @throws IllegalArgumentException if one of the criterias above is not met.
     */
    private void validateIEE754String(String number) throws IllegalArgumentException{
        if(number.length() != DOUBLE_LENGTH && number.length() != SIMPLE_LENGTH){
            throw new IllegalArgumentException("The number has a different length than a simple/double IEEE754 number.");
        }

        for(byte i = 0; i < number.length(); i++){
            char nextDigit = number.charAt(i);
            if(nextDigit != '0' && nextDigit != '1')
                throw new IllegalArgumentException("There is an invalid digit in the given string: " + nextDigit);
        }
    }

    /**
     * Converts the binary String into a long number (the attribute of this object)
     *  representing either a 32 bit or 64 bit IEEE754 representation.
     * @param number the String containing the binary number.
     */
    private void convertIEEE754StringToLong(String number){
        this.binary = 0;

        for(byte i = 0; i < number.length(); i++){
            this.binary <<= 1;
            byte nextExponentDigit = (byte) (number.charAt(i) & 1);
            this.binary = this.binary + nextExponentDigit;
        }
    }


    private void setSimpleIEEE754(int number){
        this.binary = this.binary & DOUBLE_UPPER_MASK; // just cleaning the left 32 bits
        this.binary = number;
    }

    /**
     * Returns a binary string representing the 0s and 1s of the exponent.
     * @return a String representing the exponent.
     */
    public String getExponentInBinary(){
        String representation = this.toBinaryRepresentation();
        byte leftBound = 1;
        byte rightBound;

        if(this.isSimple){
            rightBound = SIMPLE_EXPONENT;
        } else {
            rightBound = DOUBLE_EXPONENT;
        }

        String exponent = representation.substring(leftBound, rightBound + 1);
        return exponent;
    }

    /**
     * Returns the exponent of the IEEE754 representation.
     * @return an integer value representing the exponent.
     */
    public int getExponentInDecimal(){
        String exponentInString = this.getExponentInBinary();
        short exponentRawValue = 0;
        
        while(exponentInString.length() != 0){
            exponentRawValue <<= 1;
            
            byte nextDigit = (byte) exponentInString.charAt(0);
            exponentRawValue += nextDigit & 1;

            exponentInString = exponentInString.substring(1, exponentInString.length());
        }

        short precision = 0;
        if(this.isSimple)
            precision = SIMPLE_PRECISION;
        else
            precision = DOUBLE_PRECISION;

        return exponentRawValue - precision;
    }

    /**
     * Returns the sign, "+" or "-", of the number.
     * @return "+" for positive, "-" for negative.
     */
    public String getSignInString(){
        String sign = "";
        boolean isPositive;

        if(this.isSimple){
            isPositive = (this.binary & SIMPLE_SIGN) == 0;
        } else {
            isPositive = (this.binary & DOUBLE_SIGN) == 0;
        }

        if(isPositive){
            sign = "+";
        } else {
            sign = "-";
        }

        return sign;
    }

    /**
     * Returns the leftmost bit of the 32 or 64 bit IEEE754 representation.
     * @return 0 if positive, 1 if negative.
     */
    public byte getSignInDecimal(){
        if(this.isSimple){
            return (byte) (this.binary & SIMPLE_SIGN);
        } else {
            return (byte) (this.binary & DOUBLE_SIGN);
        }
    }

    /**
     * Returns the mantissa (decimal part) of the number as a binary string.
     * The length of the string is 23 for simple precision, or 52 for double.
     * @return a String containing the 1s and 0s of the mantissa.
     */
    public String getMantissaInBinary(){
        String representation = this.toBinaryRepresentation();
        byte leftBound;
        byte rightBound = (byte) representation.length();

        if(this.isSimple){
            leftBound = SIMPLE_LENGTH - SIMPLE_MANTISSA;
        } else {
            leftBound = DOUBLE_LENGTH - DOUBLE_MANTISSA;
        }

        String mantissa = representation.substring(leftBound, rightBound);
        return mantissa;
    }

    /**
     * Returns the decimal value of the mantissa (decimal part) as a double.
     * @return a double value as the decimal part (mantissa)
     */
    public double getMantissaInDecimal(){
        String mantissaInString = this.getMantissaInBinary();
        double indexDecimalValue = 1;
        double mantissa = 0;

        while(mantissaInString.length() != 0){
            indexDecimalValue /= 2;
            byte nextDigit = (byte) (mantissaInString.charAt(0) & 1);
            if(nextDigit == 1){
                mantissa += indexDecimalValue;
            }

            mantissaInString = mantissaInString.substring(1, mantissaInString.length());
        }

        return mantissa;
    }

    /**
     * Represents the number as a binary IEEE754 of 32 or 64 bits.
     * @return a String with 0s and 1s, representing the IEEE754 expression.
     */
    public String toBinaryRepresentation(){
        String msg = "";
        long copy = this.binary;
        byte limit;

        if (this.isSimple)
            limit = SIMPLE_LENGTH;
        else 
            limit = DOUBLE_LENGTH;

        while (limit > 0){
            msg = (copy & 1) + msg;
            copy >>= 1;
            limit--; 
        }

        return msg;
    }

    /**
     * Returns the number in the IEEE754 normalized representation:
     * (+-) (1 + mantissa) * 2 ^ (exponent)
     * @return a String representing the normalized version of the number.
     */
    public String toIEEE754NormalizedRepresentation(){
        String rep = "";

        rep += this.getSignInString();
        rep += this.getMantissaInDecimal()+1;
        rep += " * 2 ^ " + this.getExponentInDecimal();
        
        return rep;
    }

    /**
     * Returns the representation of both the binary (32 or 64 bits), followed by
     * " == ", and then the normalized version of the number.
     * e.g "(binary) == (+-) (1 + mantissa) * 2 ^ (exponent)".
     */
    @Override
    public String toString(){
        return this.toBinaryRepresentation() + " == " + this.toIEEE754NormalizedRepresentation();
    }
}
