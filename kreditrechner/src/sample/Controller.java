package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.text.NumberFormat;

public class Controller {
    public static final int MONTH_IN_THE_YEAR = 12;
    public static final int NUMBER_TO_PERCENT = 100;
    public double principal = 0;
    public double interestRate = 0;
    public int howManyYears = 0;

    @FXML
    TextField textFieldPrincipal;

    @FXML
    TextField textFieldInterestRate;

    @FXML
    TextField textFieldTerm;

    @FXML
    TextField textFieldMonthlyPayment;

    @FXML
    TextField textFieldTotalPayment;

    @FXML
    Button buttonBerechnen;

    @FXML
    Button buttonNeueBerechnung;
    // Taste Berechnen //
    @FXML
    void buttonBerechnenClicked(ActionEvent event) {
        if (validationPrincipal()) {
            if (validationInterestRate()) {
                if (validationHowManyYears()) {
                    resultMonthlyPayment();
                    resultPayment();
                    buttonBerechnen.setDisable(true);
                }
            }
        }
    }
    // Taste Neue Berechnung //
    @FXML
    void buttonNeueBerechnungClicked(ActionEvent event) {
        buttonBerechnen.setDisable(false);
        textFieldMonthlyPayment.setText("");
        textFieldTotalPayment.setText("");
        textFieldInterestRate.setText("");
        textFieldPrincipal.setText("");
        textFieldTerm.setText("");
    }
    // Datenvalidierung "Darlehensbetrag" //
    private boolean validationPrincipal() {
        try {
            principal = Double.parseDouble(textFieldPrincipal.getText());
            if (principal >= 0)
                return true;
            else {
                alert("Bitte nennen Sie Ihre Darlehensbeträge in positive Zahl");
                textFieldPrincipal.setText("");
                return false;
            }
        } catch (NumberFormatException e) {
            alert("Bitte nennen Sie Ihre Darlehensbeträge");
            textFieldPrincipal.setText("");
            return false;
        }
    }
    // Datenvalidierung "Sollzins" //
    private boolean validationInterestRate() {
        try {
            interestRate = Double.parseDouble(textFieldInterestRate.getText());
            if (interestRate >= 0 && interestRate <= 100)
                return true;
            else {
                alert("Bitte nennen Sie Ihre Kredit Sollzins in Zahl von 0 bis 100");
                textFieldInterestRate.setText("");
                return false;
            }
        } catch (NumberFormatException e) {
            alert("Bitte nennen Sie Ihre Kredit Sollzins");
            textFieldInterestRate.setText("");
            return false;
        }
    }
    // Datenvalidierung "Jahre Laufzeit" //
    private boolean validationHowManyYears() {
        try {
            howManyYears = Integer.parseInt(textFieldTerm.getText());
            if (howManyYears >= 0)
                return true;
            else {
                alert("Bitte nennen Sie Ihre Kreditlaufzeit in positive Zahl");
                textFieldTerm.setText("");
                return false;
            }
        } catch (NumberFormatException e) {
            alert("Bitte nennen Sie Ihre Kreditlaufzeit in Jahre");
            textFieldTerm.setText("");
            return false;
        }
    }
    // Gesamte Kreditbetrag anzeigen //
    private void resultPayment() {
        textFieldTotalPayment.setText(String.valueOf(NumberFormat.getCurrencyInstance().format
                (totalPayback())));
    }
    // Monatliche Rate anzeigen //
    private void resultMonthlyPayment() {
        textFieldMonthlyPayment.setText(String.valueOf(NumberFormat.getCurrencyInstance().format
                (monthlyPayment())));
    }
    // Alert fenster //
    private void alert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Falsche Eingabe");
        alert.setContentText(content);
        alert.showAndWait();
    }
    // Zahl zu Prozent //
    public double percentInterestRate() {
        double percentInterestRate = interestRate / NUMBER_TO_PERCENT;
        return percentInterestRate;
    }
    // Monatszins //
    public double monthlyInterestRate() {
        double monthlyInterestRate = percentInterestRate() / MONTH_IN_THE_YEAR;
        return monthlyInterestRate;
    }
    // Raten Zahl //
    public int numberOfPayments() {
        int numberOfPayments = howManyYears * MONTH_IN_THE_YEAR;
        return numberOfPayments;
    }
    // Monatsrate //
    public double monthlyPayment() {
        double monthlyPayment =
                principal * monthlyInterestRate() * (Math.pow( 1 + monthlyInterestRate(), numberOfPayments())) /
                        ((Math.pow( 1 + monthlyInterestRate(), numberOfPayments()))- 1);

        return monthlyPayment;
    }
    // Gesamt Kredit //
    public double totalPayback() {
        double totalPayback = monthlyPayment() * numberOfPayments();
        return totalPayback;
    }
}
