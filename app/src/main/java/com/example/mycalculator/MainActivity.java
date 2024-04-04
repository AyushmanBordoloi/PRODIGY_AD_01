package com.example.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView workingsTV, resultTV;
    MaterialButton buttonDel, buttonOpnBrc, buttonClBrc;
    MaterialButton buttonDiv, buttonMul, buttonAdd, buttonSub, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTV = findViewById(R.id.resultTV);
        workingsTV = findViewById(R.id.workingsTV);

        assignID(buttonAC, R.id.button_ac);
        assignID(buttonOpnBrc, R.id.button_ob);
        assignID(buttonClBrc, R.id.button_cb);
        assignID(buttonDiv, R.id.button_div);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonMul, R.id.button_mul);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(buttonSub, R.id.button_sub);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(buttonAdd, R.id.button_add);
        assignID(buttonDot, R.id.button_dot);
        assignID(button0, R.id.button_0);
        assignID(buttonDel, R.id.button_del);
        assignID(buttonEquals, R.id.button_eq);

    }

    void assignID(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = workingsTV.getText().toString();

        if (buttonText.equals("AC"))
        {
            workingsTV.setText("");
            resultTV.setText("0");
            return;
        }

        if (buttonText.equals("="))
        {
            workingsTV.setText(resultTV.getText());
            return;
        }

        if (buttonText.equals("DEL"))
        {
            if (dataToCalculate.length()>0)
            {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
            } else if (dataToCalculate.length()==1 && !dataToCalculate.equals("0")) {
                workingsTV.setText("");
                resultTV.setText("0");
            }
        }

        else
        {
            dataToCalculate = dataToCalculate+buttonText;
        }
        workingsTV.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")){
            resultTV.setText(finalResult);
        }
    }

    String getResult(String data)
    {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            if (finalResult.endsWith(".0"))
            {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }
        catch (Exception e){
            return "Err";
        }
    }
}