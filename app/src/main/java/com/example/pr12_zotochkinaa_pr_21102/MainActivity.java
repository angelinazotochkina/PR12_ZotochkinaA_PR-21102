package com.example.pr12_zotochkinaa_pr_21102;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
    }

    double current_number = 0;
    long fraction = 0, whole = 0;
    short zeros = 0;
    boolean exists_dot = false;
    int sign = 1;

    void upd_current_number() {

        try {
            String text = "";

            if (sign == -1 && whole == 0) {
                text += "-";
            }

            text += String.valueOf(whole);

            if (exists_dot) {

                text += ".";

                for (int i = 0; i < zeros; i++) {
                    text += "0";
                }

                if (fraction != 0) {
                    text += String.valueOf(fraction);
                }
            }
            current_number = Double.parseDouble(text);

            tv.setText(text);
        }
        catch (NumberFormatException e) {

            e.printStackTrace();

            onclick_clear(null);
            tv.setText("Nan");
        }
    }

    void set_current_number(double number) {

        current_number = number;

        whole = (long) (Math.floor(Math.abs(number)) *
                number / Math.abs(number));
        fraction = 0;

        String text = String.valueOf(number);

        zeros = 0;
        sign = 1;

        boolean dot_found = false;
        boolean zero_found = false;
        boolean zero_end_found = false;

        for (int i = 0; i < text.length(); i++) {

            if (text.charAt(i) == '-') {
                sign = -1;
                continue;
            }
            if (text.charAt(i) == '.') {
                dot_found = true;
                zero_found = text.charAt(i + 1) == '0';
                continue;
            }
            if (dot_found) {
                if (zero_found && !zero_end_found) {
                    if ( text.charAt(i) == '0') {
                        zeros++;
                        continue;
                    }
                    else {
                        zero_end_found = true;
                    }
                }

                fraction *= 10;
                fraction += Long.valueOf(String.valueOf(text.charAt(i)));
            }
        }

        exists_dot = dot_found;

        upd_current_number();

    }
    void addDigitToCurrentNumber(int d) {

        if (exists_dot) {

            if(fraction == 0 && d == 0) zeros++;

            fraction *= 10;
            fraction += d;
        }
        else {
            whole *= 10;
            whole += d;
        }

        upd_current_number();
    }

    public void onclick_0(View v) {
        addDigitToCurrentNumber(0);
    }
    public void onclick_1(View v) {
        addDigitToCurrentNumber(1);
    }
    public void onclick_2(View v) {
        addDigitToCurrentNumber(2);
    }
    public void onclick_3(View v) {
        addDigitToCurrentNumber(3);
    }
    public void onclick_4(View v) {
        addDigitToCurrentNumber(4);
    }
    public void onclick_5(View v) {
        addDigitToCurrentNumber(5);
    }
    public void onclick_6(View v) {
        addDigitToCurrentNumber(6);
    }
    public void onclick_7(View v) {
        addDigitToCurrentNumber(7);
    }
    public void onclick_8(View v) {
        addDigitToCurrentNumber(8);
    }
    public void onclick_9(View v) {
        addDigitToCurrentNumber(9);
    }
    public void onclick_dot(View v) {
        exists_dot = true;
        tv.setText(tv.getText().toString() + ".");
    }

    char operation = ' ';
    double previous = 0;

    void set_operator(char operation_) {

        previous = current_number;
        operation = operation_;
        current_number = 0;

        exists_dot = false;
        whole = 0;
        fraction = 0;
        zeros = 0;
        sign = 1;

        tv.setText(String.valueOf(operation_));
    }

    public void onclick_plus(View v) {
        set_operator('+');
    }
    public void onclick_minus(View v) {
        set_operator('-');
    }
    public void onclick_multiply(View v) {
        set_operator('*');
    }
    public void onclick_divide(View v) {
        set_operator('/');
    }
    public void onclick_percent(View v) {
        set_operator('%');
    }
    public void onclick_sqrt(View v) {
        set_current_number(Math.sqrt(current_number));
    }
    public void onclick_back(View v) {

        if (exists_dot) {

            fraction /= 10;

            if (fraction == 0) {
                exists_dot = false;
            }
            upd_current_number();
        }
        else {
            whole /= 10;
            upd_current_number();
        }
    }
    public void onclick_clear(View v) {

        operation = ' ';
        exists_dot = false;
        whole = 0;
        fraction = 0;
        zeros = 0;
        sign = 1;

        upd_current_number();
    }
    public void onclick_equal(View v) {

        switch (operation) {
            case '+': set_current_number(previous + current_number); break;
            case '-': set_current_number(previous - current_number); break;
            case '*': set_current_number(previous * current_number); break;
            case '/': set_current_number(previous / current_number); break;
            case '%': set_current_number(previous /100); break;
        }

        previous = 0;
        operation = ' ';
    }

}