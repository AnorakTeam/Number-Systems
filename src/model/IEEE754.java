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


    public IEEE754(float number){
        int simple = Float.floatToRawIntBits(number);
        this.setSimpleIEEE754(simple);
        this.isSimple = true;
    }

    public IEEE754(double number){
        this.binary = Double.doubleToRawLongBits(number);
        this.isSimple = false;
    }

    public IEEE754(String number, boolean isSimple) throws IllegalArgumentException{
        try{
            this.validateIEE754String(number);
            convertIEEE754StringToLong(number);
            this.isSimple = isSimple;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    private void validateIEE754String(String number){
        if(number.length() != DOUBLE_LENGTH && number.length() != SIMPLE_LENGTH){
            throw new IllegalArgumentException("The number has a different length than a simple/double IEEE754 number.");
        }

        for(byte i = 0; i < number.length(); i++){
            char nextDigit = number.charAt(i);
            if(nextDigit != '0' && nextDigit != '1')
                throw new IllegalArgumentException("There is an invalid digit in the given string: " + nextDigit);
        }
    }

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

    public byte getSignInDecimal(){
        if(this.isSimple){
            return (byte) (this.binary & SIMPLE_SIGN);
        } else {
            return (byte) (this.binary & DOUBLE_SIGN);
        }
    }

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

    public String toIEEE754NormalizedRepresentation(){
        String rep = "";
        if(this.getSignInString() == "-"){
            rep += "-";
        }

        rep += this.getMantissaInDecimal()+1;
        rep += " * 2 ^ " + this.getExponentInDecimal();
        
        return rep;
    }

    @Override
    public String toString(){
        return this.toBinaryRepresentation() + " == " + this.toIEEE754NormalizedRepresentation();
    }
}
