package com.edumacation.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result, newNumber;
    private TextView displayOperation;

    // variable to hold operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=", TAG = "MainActivity";

    private static final String STATE_PENDING_OPERATION = "PendingOperation", STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNum);
        displayOperation = findViewById(R.id.operation);

        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnDecimal = findViewById(R.id.btnDecimal);

        Button btnResult = findViewById(R.id.btnResult);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnAdd = findViewById(R.id.btnAdd);

        View.OnClickListener numListener = view -> {
            Button b = (Button) view;
            newNumber.append(b.getText().toString());
            Log.d(TAG, b.getText().toString() + " clicked");
        };

        btn0.setOnClickListener(numListener);
        btn1.setOnClickListener(numListener);
        btn2.setOnClickListener(numListener);
        btn3.setOnClickListener(numListener);
        btn4.setOnClickListener(numListener);
        btn5.setOnClickListener(numListener);
        btn6.setOnClickListener(numListener);
        btn7.setOnClickListener(numListener);
        btn8.setOnClickListener(numListener);
        btn9.setOnClickListener(numListener);
        btnDecimal.setOnClickListener(numListener);

        View.OnClickListener opListener = view -> {
            Button b = (Button) view;
            String op = b.getText().toString();
            String value = newNumber.getText().toString();

            try {
                performOperation(Double.valueOf(value), op);
            } catch (NumberFormatException ex) {
                newNumber.setText("");
            }

            pendingOperation = op;
            displayOperation.setText((pendingOperation));
        };

        btnResult.setOnClickListener(opListener);
        btnAdd.setOnClickListener(opListener);
        btnSubtract.setOnClickListener(opListener);
        btnMultiply.setOnClickListener(opListener);
        btnDivide.setOnClickListener(opListener);

    }

    // To restore state when the app is rotated
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);

        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);

        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if ((value == 0)) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }

}