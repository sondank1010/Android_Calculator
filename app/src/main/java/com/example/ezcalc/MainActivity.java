package com.example.ezcalc;

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

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBracesOpen, buttonBracesClose;
    MaterialButton buttonDivide, buttonMultiply, buttonMinus, buttonAdd, buttonEquals;
    MaterialButton button1,button2,button3,button4,button5,button6,button7,button8,button9,button0;
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

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);


        buttonC = assignId(R.id.button_c);
        buttonBracesOpen =assignId(R.id.button_open_bracket);
        buttonBracesClose = assignId(R.id.button_closed_bracket);
        buttonDivide = assignId(R.id.button_divide);
        buttonMultiply =assignId(R.id.button_multiply);
        buttonMinus = assignId(R.id.button_minus);
        buttonAdd = assignId(R.id.button_add);
        buttonEquals = assignId(R.id.button_equal);
        button1 = assignId(R.id.button_1);
        button2 = assignId(R.id.button_2);
        button3 =assignId(R.id.button_3);
        button4 = assignId(R.id.button_4);
        button5 = assignId(R.id.button_5);
        button6 = assignId(R.id.button_6);
        button7 = assignId(R.id.button_7);
        button8 = assignId(R.id.button_8);
        button9 = assignId(R.id.button_9);
        button0 = assignId(R.id.button_0);
        buttonAC = assignId(R.id.button_ac);
        buttonDot = assignId(R.id.button_dot);

    }

    public MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View v) {
        //Ep kieu sang dang btn
        MaterialButton btn = (MaterialButton) v;
        String buttonText = btn.getText().toString();

        String datatocalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            if(!datatocalculate.isEmpty()){
                datatocalculate = datatocalculate.substring(0,datatocalculate.length()-1);
            }

            //Tranh bi loi undefined
            if (datatocalculate.isEmpty()){
                datatocalculate = "0";
            }
            solutionTv.setText(datatocalculate);
            return;

        } else {
            if (datatocalculate.equals("0") && !buttonText.equals(".")) {
                datatocalculate = buttonText;
            } else {
                datatocalculate = datatocalculate + buttonText;
            }
        }

        solutionTv.setText(datatocalculate);
        String finalResult = getResult(datatocalculate);
        if (!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    //hàm tính toán
    public String getResult(String data){
        try {
            //Initial JS environment
            Context context = Context.enter();

            //Tat toi uu hoa
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();

            //Tinh toan va convert ve to string
            String finalResults = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            //Trim duoi neu ket thuc bang .0
            if (finalResults.endsWith(".0")){
                finalResults = finalResults.replace(".0","");
            }
            return finalResults;
        } catch(Exception e){
            return "Err";
        }
    }
}

