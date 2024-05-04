package com.example.laba3_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        setupButtonClickListeners();
    }

    private void setupButtonClickListeners() {
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("0");
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("1");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("2");
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("3");
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("4");
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("5");
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("6");
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("7");
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("8");
            }
        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("9");
            }
        });

        findViewById(R.id.buttonPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("+");
            }
        });

        findViewById(R.id.buttonMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("-");
            }
        });

        findViewById(R.id.buttonMultiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("*");
            }
        });

        findViewById(R.id.buttonDivide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("/");
            }
        });
        findViewById(R.id.buttonDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression(".");
            }
        });
        findViewById(R.id.buttonEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }
    //добавление в editText
    private void appendToExpression(String value) {
        editText.getText().append(value);
    }
    // метод =
    private void calculateResult() {
        String expression = editText.getText().toString();

        try {

            double result = eval(expression);

            editText.setText(String.valueOf(result));
        } catch (Exception e) {

            editText.setText("Error");
        }
    }
    //вычисления
    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (Character.isWhitespace(ch)) nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // числа
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}
