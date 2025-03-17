package controller;

/**
 * Sample Skeleton for 'MainView.fxml' Controller Class
 */

 import java.net.URL;
 import java.util.ResourceBundle;
 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
 import javafx.scene.control.ChoiceBox;
 import javafx.scene.control.TextField;
 import javafx.scene.text.Text;

 import model.Number;
 import model.Operation;
 import model.ByteRepresentation;
 import model.IEEE754;
 
 public class MainController {
 
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="binaryConvertField"
    private TextField binaryConvertField; // Value injected by FXMLLoader

    @FXML // fx:id="completeIEEE"
    private TextField completeIEEE; // Value injected by FXMLLoader

    @FXML // fx:id="convertButton"
    private Button convertButton; // Value injected by FXMLLoader

    @FXML // fx:id="decimalConvertField"
    private TextField decimalConvertField; // Value injected by FXMLLoader

    @FXML // fx:id="exponentText"
    private TextField exponentText; // Value injected by FXMLLoader

    @FXML // fx:id="hexConvertField"
    private TextField hexConvertField; // Value injected by FXMLLoader

    @FXML // fx:id="inputBytesFieldLeft"
    private TextField inputBytesFieldLeft; // Value injected by FXMLLoader

    @FXML // fx:id="inputBytesFieldRight"
    private TextField inputBytesFieldRight; // Value injected by FXMLLoader

    @FXML // fx:id="inputField"
    private TextField inputField; // Value injected by FXMLLoader

    @FXML // fx:id="inputIEEEFieldLeft"
    private TextField inputIEEEFieldLeft; // Value injected by FXMLLoader

    @FXML // fx:id="inputIEEEFieldRight"
    private TextField inputIEEEFieldRight; // Value injected by FXMLLoader

    @FXML // fx:id="mantissaText"
    private TextField mantissaText; // Value injected by FXMLLoader

    @FXML // fx:id="number1Field"
    private TextField number1Field; // Value injected by FXMLLoader

    @FXML // fx:id="number2Field"
    private TextField number2Field; // Value injected by FXMLLoader

    @FXML // fx:id="numeralChoiceBox"
    private ChoiceBox<String> numeralChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="octalConvertField"
    private TextField octalConvertField; // Value injected by FXMLLoader

    @FXML // fx:id="operateNumbersButton"
    private Button operateNumbersButton; // Value injected by FXMLLoader

    @FXML // fx:id="outputOperationField"
    private TextField outputOperationField; // Value injected by FXMLLoader

    @FXML // fx:id="outputRepresentationField"
    private TextField outputRepresentationField; // Value injected by FXMLLoader

    @FXML // fx:id="outputTypeOperation"
    private ChoiceBox<String> outputTypeOperation; // Value injected by FXMLLoader

    @FXML // fx:id="presicionChoiceBox"
    private ChoiceBox<String> presicionChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="presicionChoiceBoxRight"
    private ChoiceBox<String> presicionChoiceBoxRight; // Value injected by FXMLLoader

    @FXML // fx:id="representButtonLeft"
    private Button representButtonLeft; // Value injected by FXMLLoader

    @FXML // fx:id="representButtonRight"
    private Button representButtonRight; // Value injected by FXMLLoader

    @FXML // fx:id="representIEEEButtonLeft1"
    private Button representIEEEButtonLeft1; // Value injected by FXMLLoader

    @FXML // fx:id="representIEEEButtonRight1"
    private Button representIEEEButtonRight1; // Value injected by FXMLLoader

    @FXML // fx:id="signText"
    private TextField signText; // Value injected by FXMLLoader

    @FXML // fx:id="typeNumber1ChoiceBox"
    private ChoiceBox<String> typeNumber1ChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="typeNumber2ChoiceBox"
    private ChoiceBox<String> typeNumber2ChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="typeOfOperation"
    private ChoiceBox<String> typeOfOperation; // Value injected by FXMLLoader

    @FXML // fx:id="typeRepLeftChoiceBox"
    private ChoiceBox<String> typeRepLeftChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="typeRepRightInputChoiceBox"
    private ChoiceBox<String> typeRepRightInputChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="typeRepRightOutputChoiceBox"
    private ChoiceBox<String> typeRepRightOutputChoiceBox; // Value injected by FXMLLoader
 
     @FXML
    void onConvertSystem(ActionEvent event) {
        String input = inputField.getText().trim();
        String typeStr = (String) numeralChoiceBox.getValue();
        byte type;

        switch (typeStr) {
            case "Decimal":
                type = Number.DECIMAL;
                break;
            case "Binary":
                type = Number.BINARY;
                break;
            case "Octal":
                type = Number.OCTAL;
                break;
            case "Hexadecimal":
                type = Number.HEX;
                break;
            default:
                showError("Invalid numeral system selected.");
                return;
        }

        try {
            Number number = new Number(input, type);
            decimalConvertField.setText(number.getDecimal());
            binaryConvertField.setText(number.getBinary());
            octalConvertField.setText(number.getOctal());
            hexConvertField.setText(number.getHex());
        } catch (IllegalArgumentException e) {
            showError("Invalid number for the selected numeral system.");
        }
    }
 
     @FXML
     void onOperateNumbers(ActionEvent event) {
        if (number1Field.getText().trim().isEmpty() ||
            number2Field.getText().trim().isEmpty() ||
            typeNumber1ChoiceBox.getValue() == null ||
            typeNumber2ChoiceBox.getValue() == null ||
            outputTypeOperation.getValue() == null ||
            typeOfOperation.getValue() == null) {
            showError("ERROR: There are elements not specified.");
            return;
        }
        
        String num1Str = number1Field.getText().trim();
        String num2Str = number2Field.getText().trim();
        byte type1 = mapNumeralType(typeNumber1ChoiceBox.getValue());
        byte type2 = mapNumeralType(typeNumber2ChoiceBox.getValue());
        byte outputType = mapNumeralType(outputTypeOperation.getValue());
        String opType = typeOfOperation.getValue();
        
        try {
            Operation operation = new Operation(num1Str, type1, num2Str, type2, outputType);
            String result;
            switch (opType) {
                case "Sum":
                    result = operation.sum();
                    break;
                case "Substraction":
                    result = operation.subs();
                    break;
                case "Multiplication":
                    result = operation.mult();
                    break;
                case "Division":
                    result = operation.div();
                    break;
                default:
                    showError("Invalid operation type selected.");
                    return;
            }
            outputOperationField.setText(result);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    void onRepresentLeft(ActionEvent event) {
       String inputStr = inputBytesFieldLeft.getText().trim();
       String selectedOption = typeRepLeftChoiceBox.getValue();
       
       if (inputStr.isEmpty() || selectedOption == null) {
           showError("Please enter the number of bytes and select a representation type.");
           return;
       }
       
       double amount;
       try {
           amount = Double.parseDouble(inputStr);
       } catch (NumberFormatException e) {
           showError("Invalid input for bytes. Please enter a valid number.");
           return;
       }
       
       try {
           ByteRepresentation br = new ByteRepresentation(amount);
           String result;
           
           switch (selectedOption) {
               case "KB":
                   result = br.representSIPower(ByteRepresentation.KILO);
                   break;
               case "MB":
                   result = br.representSIPower(ByteRepresentation.MEGA);
                   break;
               case "GB":
                   result = br.representSIPower(ByteRepresentation.GIGA);
                   break;
               case "TB":
                   result = br.representSIPower(ByteRepresentation.TERA);
                   break;
               case "KiB":
                   result = br.representISO_IECPower(ByteRepresentation.KILO);
                   break;
               case "MiB":
                   result = br.representISO_IECPower(ByteRepresentation.MEGA);
                   break;
               case "GiB":
                   result = br.representISO_IECPower(ByteRepresentation.GIGA);
                   break;
               case "TiB":
                   result = br.representISO_IECPower(ByteRepresentation.TERA);
                   break;
               default:
                   showError("Invalid representation type selected.");
                   return;
           }
           outputRepresentationField.setText(result);
       } catch (Exception e) {
           showError(e.getMessage());
       }
   }

   // Heck, this was a nightmare. Would be correct to refactor all of this? yeah, but
   // will i do it? ummm... i don't have much time, i'll leave it as it is, as bad as it
   // might see rn
   @FXML
   void onRepresentRight(ActionEvent event) {
       String inputStr = inputBytesFieldRight.getText().trim();
       String inputTypeStr = typeRepRightInputChoiceBox.getValue();
       String outputTypeStr = typeRepRightOutputChoiceBox.getValue();
       
       if (inputStr.isEmpty() || inputTypeStr == null || outputTypeStr == null) {
           showError("Please enter the number of bytes and select both input and output representation types.");
           return;
       }
       
       double amount;
       try {
           amount = Double.parseDouble(inputStr);
       } catch (NumberFormatException e) {
           showError("Invalid input for bytes. Please enter a valid number.");
           return;
       }
       
       try {
           byte inputTypeConstant;
           boolean inputIsSI;
           switch (inputTypeStr) {
               case "KB":
                   inputTypeConstant = ByteRepresentation.KILO;
                   inputIsSI = true;
                   break;
               case "MB":
                   inputTypeConstant = ByteRepresentation.MEGA;
                   inputIsSI = true;
                   break;
               case "GB":
                   inputTypeConstant = ByteRepresentation.GIGA;
                   inputIsSI = true;
                   break;
               case "TB":
                   inputTypeConstant = ByteRepresentation.TERA;
                   inputIsSI = true;
                   break;
               case "KiB":
                   inputTypeConstant = ByteRepresentation.KILO;
                   inputIsSI = false;
                   break;
               case "MiB":
                   inputTypeConstant = ByteRepresentation.MEGA;
                   inputIsSI = false;
                   break;
               case "GiB":
                   inputTypeConstant = ByteRepresentation.GIGA;
                   inputIsSI = false;
                   break;
               case "TiB":
                   inputTypeConstant = ByteRepresentation.TERA;
                   inputIsSI = false;
                   break;
               default:
                   showError("Invalid input representation type selected.");
                   return;
           }
           
           ByteRepresentation br = new ByteRepresentation(amount, inputTypeConstant, inputIsSI);
           String result;
           
           switch (outputTypeStr) {
               case "KB":
                   result = br.representSIPower(ByteRepresentation.KILO);
                   break;
               case "MB":
                   result = br.representSIPower(ByteRepresentation.MEGA);
                   break;
               case "GB":
                   result = br.representSIPower(ByteRepresentation.GIGA);
                   break;
               case "TB":
                   result = br.representSIPower(ByteRepresentation.TERA);
                   break;
               case "KiB":
                   result = br.representISO_IECPower(ByteRepresentation.KILO);
                   break;
               case "MiB":
                   result = br.representISO_IECPower(ByteRepresentation.MEGA);
                   break;
               case "GiB":
                   result = br.representISO_IECPower(ByteRepresentation.GIGA);
                   break;
               case "TiB":
                   result = br.representISO_IECPower(ByteRepresentation.TERA);
                   break;
               default:
                   showError("Invalid output representation type selected.");
                   return;
           }
           
           outputRepresentationField.setText(result);
       } catch (Exception e) {
           showError(e.getMessage());
       }
   }

   
   @FXML
   void onRepresentIEEELeft(ActionEvent event) {
       String inputStr = inputIEEEFieldLeft.getText().trim();
       String precisionStr = presicionChoiceBox.getValue();
       
       if (inputStr.isEmpty() || precisionStr == null) {
           showError("Please enter a number and select a precision.");
           return;
       }
       
       try {
           IEEE754 ieee;
           if (precisionStr.equals("32-bits")) {
               // Parse as float for 32-bit IEEE754
               float number = Float.parseFloat(inputStr);
               ieee = new IEEE754(number);
           } else {
               // Parse as double for 64-bit IEEE754
               double number = Double.parseDouble(inputStr);
               ieee = new IEEE754(number);
           }
           
           // Set the output TextFields using the IEEE754 methods
           signText.setText(ieee.getSignInString());
           exponentText.setText(ieee.getExponentInBinary());
           mantissaText.setText(ieee.getMantissaInBinary());
           completeIEEE.setText(ieee.toString());
       } catch (NumberFormatException e) {
           showError("Invalid number format. Please enter a valid number.");
       } catch (Exception e) {
           showError(e.getMessage());
       }
   }


   @FXML
void onRepresentIEEERight(ActionEvent event) {
    String inputStr = inputIEEEFieldRight.getText().trim();
    String precisionStr = presicionChoiceBoxRight.getValue();
    
    if (inputStr.isEmpty() || precisionStr == null) {
        showError("Please enter a binary number and select a precision.");
        return;
    }
    
    try {
        IEEE754 ieee;
        // Use the binary-string constructor.
        if (precisionStr.equals("32-bits")) {
            ieee = new IEEE754(inputStr, true);  // true indicates 32-bit (simple) precision.
        } else {
            ieee = new IEEE754(inputStr, false); // false indicates 64-bit (double) precision.
        }
        
        // Set the output fields with the decimal equivalents.
        signText.setText(String.valueOf(ieee.getSignInString()));
        exponentText.setText(String.valueOf(ieee.getExponentInDecimal()));
        mantissaText.setText(String.valueOf(ieee.getMantissaInDecimal()));
        completeIEEE.setText(ieee.toString());
    } catch (NumberFormatException e) {
        showError("Invalid binary number format. Please enter a valid binary number.");
    } catch (Exception e) {
        showError(e.getMessage());
    }
}
 
 
 
 
     @FXML // This method is called by the FXMLLoader when initialization is complete
     void initialize() {
        assert binaryConvertField != null : "fx:id=\"binaryConvertField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert convertButton != null : "fx:id=\"convertButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert decimalConvertField != null : "fx:id=\"decimalConvertField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert exponentText != null : "fx:id=\"exponentText\" was not injected: check your FXML file 'MainView.fxml'.";
        assert hexConvertField != null : "fx:id=\"hexConvertField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert inputBytesFieldLeft != null : "fx:id=\"inputBytesFieldLeft\" was not injected: check your FXML file 'MainView.fxml'.";
        assert inputBytesFieldRight != null : "fx:id=\"inputBytesFieldRight\" was not injected: check your FXML file 'MainView.fxml'.";
        assert inputField != null : "fx:id=\"inputField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert inputIEEEFieldLeft != null : "fx:id=\"inputIEEEFieldLeft\" was not injected: check your FXML file 'MainView.fxml'.";
        assert inputIEEEFieldRight != null : "fx:id=\"inputIEEEFieldRight\" was not injected: check your FXML file 'MainView.fxml'.";
        assert mantissaText != null : "fx:id=\"mantissaText\" was not injected: check your FXML file 'MainView.fxml'.";
        assert number1Field != null : "fx:id=\"number1Field\" was not injected: check your FXML file 'MainView.fxml'.";
        assert number2Field != null : "fx:id=\"number2Field\" was not injected: check your FXML file 'MainView.fxml'.";
        assert numeralChoiceBox != null : "fx:id=\"numeralChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert octalConvertField != null : "fx:id=\"octalConvertField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert operateNumbersButton != null : "fx:id=\"operateNumbersButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert outputOperationField != null : "fx:id=\"outputOperationField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert outputRepresentationField != null : "fx:id=\"outputRepresentationField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert outputTypeOperation != null : "fx:id=\"outputTypeOperation\" was not injected: check your FXML file 'MainView.fxml'.";
        assert presicionChoiceBox != null : "fx:id=\"presicionChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert representButtonLeft != null : "fx:id=\"representButtonLeft\" was not injected: check your FXML file 'MainView.fxml'.";
        assert representButtonRight != null : "fx:id=\"representButtonRight\" was not injected: check your FXML file 'MainView.fxml'.";
        assert representIEEEButtonLeft1 != null : "fx:id=\"representIEEEButtonLeft1\" was not injected: check your FXML file 'MainView.fxml'.";
        assert representIEEEButtonRight1 != null : "fx:id=\"representIEEEButtonRight1\" was not injected: check your FXML file 'MainView.fxml'.";
        assert signText != null : "fx:id=\"signText\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeNumber1ChoiceBox != null : "fx:id=\"typeNumber1ChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeNumber2ChoiceBox != null : "fx:id=\"typeNumber2ChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeOfOperation != null : "fx:id=\"typeOfOperation\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeRepLeftChoiceBox != null : "fx:id=\"typeRepLeftChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeRepRightInputChoiceBox != null : "fx:id=\"typeRepRightInputChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";
        assert typeRepRightOutputChoiceBox != null : "fx:id=\"typeRepRightOutputChoiceBox\" was not injected: check your FXML file 'MainView.fxml'.";

        // Convert tab
        numeralChoiceBox.getItems().addAll("Decimal", "Binary", "Octal", "Hexadecimal");
        convertButton.setDisable(true);
        // i literally found this in youtube that fixed my problem LOLLLLL
        inputField.textProperty().addListener((obs, oldVal, newVal) -> validateConversionInput());
        numeralChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateConversionInput());
        
        
        // Operation Tab
        typeNumber1ChoiceBox.getItems().addAll("Decimal", "Binary", "Octal", "Hexadecimal");
        typeNumber2ChoiceBox.getItems().addAll("Decimal", "Binary", "Octal", "Hexadecimal");
        outputTypeOperation.getItems().addAll("Decimal", "Binary", "Octal", "Hexadecimal");
        typeOfOperation.getItems().addAll("Sum", "Substraction", "Multiplication", "Division");
        operateNumbersButton.setDisable(true);
        number1Field.textProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
        number2Field.textProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
        typeNumber1ChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
        typeNumber2ChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
        outputTypeOperation.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
        typeOfOperation.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateOperationInput());
    
        // bits and bytes tab
        // left side
        typeRepLeftChoiceBox.getItems().addAll("KB", "MB", "GB", "TB", "KiB", "MiB", "GiB", "TiB");
        representButtonLeft.setDisable(true);
        inputBytesFieldLeft.textProperty().addListener((obs, oldVal, newVal) -> validateByteRepresentationInput());
        typeRepLeftChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateByteRepresentationInput());
        
        // right side
        typeRepRightInputChoiceBox.getItems().addAll("KB", "MB", "GB", "TB", "KiB", "MiB", "GiB", "TiB");
        typeRepRightOutputChoiceBox.getItems().addAll("KB", "MB", "GB", "TB", "KiB", "MiB", "GiB", "TiB");
        representButtonRight.setDisable(true);
        inputBytesFieldRight.textProperty().addListener((obs, oldVal, newVal) -> validateRightByteRepresentationInput());
        typeRepRightInputChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateRightByteRepresentationInput());
        typeRepRightOutputChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateRightByteRepresentationInput());
        
        // IEEE tab
        presicionChoiceBox.getItems().addAll("32-bits", "64-bits");
        representIEEEButtonLeft1.setDisable(true);
        representIEEEButtonLeft1.setDisable(true);
        inputIEEEFieldLeft.textProperty().addListener((obs, oldVal, newVal) -> validateIEEELeftInput());
        presicionChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateIEEELeftInput());     
    
        presicionChoiceBoxRight.getItems().addAll("32-bits", "64-bits");
        representIEEEButtonRight1.setDisable(true);
        inputIEEEFieldRight.textProperty().addListener((obs, oldVal, newVal) -> validateIEEERightInput());
        presicionChoiceBoxRight.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validateIEEERightInput());
    }

    private void validateConversionInput() {
        boolean valid = !inputField.getText().trim().isEmpty() && numeralChoiceBox.getValue() != null;
        convertButton.setDisable(!valid);
    }


    private void validateOperationInput() {
        boolean valid = !number1Field.getText().trim().isEmpty() &&
                        !number2Field.getText().trim().isEmpty() &&
                        typeNumber1ChoiceBox.getValue() != null &&
                        typeNumber2ChoiceBox.getValue() != null &&
                        outputTypeOperation.getValue() != null &&
                        typeOfOperation.getValue() != null;
        operateNumbersButton.setDisable(!valid);
    }

    private void validateByteRepresentationInput() {
        String inputText = inputBytesFieldLeft.getText().trim();
        String choiceValue = typeRepLeftChoiceBox.getValue();
        boolean valid = !inputText.isEmpty() && choiceValue != null;
        representButtonLeft.setDisable(!valid);
    }

    private void validateRightByteRepresentationInput() {
        boolean valid = !inputBytesFieldRight.getText().trim().isEmpty()
                        && typeRepRightInputChoiceBox.getValue() != null
                        && typeRepRightOutputChoiceBox.getValue() != null;
        representButtonRight.setDisable(!valid);
    }

    private void validateIEEELeftInput() {
        String inputText = inputIEEEFieldLeft.getText().trim();
        String precision = presicionChoiceBox.getValue();
        boolean valid = !inputText.isEmpty() && precision != null;
        representIEEEButtonLeft1.setDisable(!valid);
    }

    private void validateIEEERightInput() {
        String inputText = inputIEEEFieldRight.getText().trim();
        String precision = presicionChoiceBoxRight.getValue();
        boolean valid = !inputText.isEmpty() && precision != null;
        representIEEEButtonRight1.setDisable(!valid);
    }

    /**
     * This method translates the text input to it's equivalent inside the Operation class.
     */
    private byte mapNumeralType(String typeStr) {
        switch (typeStr) {
            case "Decimal":
                return Number.DECIMAL;
            case "Binary":
                return Number.BINARY;
            case "Octal":
                return Number.OCTAL;
            case "Hexadecimal":
                return Number.HEX;
            default:
                throw new IllegalArgumentException("Unknown numeral type: " + typeStr);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
 
 }
 