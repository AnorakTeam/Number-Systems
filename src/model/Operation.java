package model;



public class Operation {
    private Number num1;
    private Number num2;
    private byte outputType;

    public static final byte SUM = 0;
    public static final byte SUBS = 1;
    public static final byte MULT = 2;
    public static final byte DIV = 3;

    public Operation(Number num1, Number num2, byte outputType) {
        this.num1 = num1;
        this.num2 = num2;
        this.outputType = outputType;
    }

    public Operation(String num1, byte type1, String num2, byte type2, byte outputType) {
        this.num1 = new Number(num1, type1);
        this.num2 = new Number(num2, type2);
        this.outputType = outputType;
    }

    public String sum() {
        int result = num1.getNumber() + num2.getNumber();
        return new Number(validate(result), this.outputType).toString();
    }

    public String subs() {
        int result = num1.getNumber() - num2.getNumber();
        return new Number(validate(result), this.outputType).toString();
    }

    public String mult() {
        int result = num1.getNumber() * num2.getNumber();
        return new Number(validate(result), this.outputType).toString();
    }

    public String div() {
        if (num2.getNumber() == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        int result = num1.getNumber() / num2.getNumber();
        return new Number(validate(result), this.outputType).toString();
    }

    private int validate(int result) {
        if (result < 0) {
            throw new IllegalArgumentException("Negative numbers/operations are not allowed.");
        }
        return result;
    }
}

