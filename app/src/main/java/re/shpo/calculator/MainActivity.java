package re.shpo.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText input1, input2, result;

    LogicService logicService;
    boolean mBound = false;

    private ServiceConnection logicConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            LogicService.LocalBinder binder = (LogicService.LocalBinder) service;
            logicService = binder.getService();
            mBound = true;
            Toast.makeText(MainActivity.this, "Logic Service Connected!", Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            logicService = null;
            mBound = false;
            Toast.makeText(MainActivity.this, "Logic Service Disconnected!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            this.bindService(new Intent(MainActivity.this, LogicService.class),
                    logicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            mBound = false;
            this.unbindService(logicConnection);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.input_1);
        input2 = findViewById(R.id.input_2);
        result = findViewById(R.id.input_result);
    }

    public void addButtonClick(View v) {
        if (mBound) {
            try {
                double number1 = Double.parseDouble(String.valueOf(this.input1.getText()));
                double number2 = Double.parseDouble(String.valueOf(this.input2.getText()));
                result.setText(Double.toString(logicService.add(number1, number2)));
            } catch (Exception e) {
                result.setText("Invalid input");
            }
        }
    }

    public void subButtonClick(View v) {
        if (mBound) {
            try {
                double number1 = Double.parseDouble(String.valueOf(this.input1.getText()));
                double number2 = Double.parseDouble(String.valueOf(this.input2.getText()));
                result.setText(Double.toString(logicService.sub(number1, number2)));
            } catch (Exception e) {
                result.setText("Invalid input");
            }
        }
    }

    public void mulButtonClick(View v) {
        if (mBound) {
            try {
                double number1 = Double.parseDouble(String.valueOf(this.input1.getText()));
                double number2 = Double.parseDouble(String.valueOf(this.input2.getText()));
                result.setText(Double.toString(logicService.mul(number1, number2)));
            } catch (Exception e) {
                result.setText("Invalid input");
            }
        }
    }

    public void divButtonClick(View v) {
        if (mBound) {
            try {
                double number1 = Double.parseDouble(String.valueOf(this.input1.getText()));
                double number2 = Double.parseDouble(String.valueOf(this.input2.getText()));
                if (number2 == 0.0) {
                    throw new ArithmeticException();
                }
                result.setText(Double.toString(logicService.div(number1, number2)));
            } catch (ArithmeticException ae) {
                result.setText("ArithmeticException occured!");
            } catch (Exception e) {
                result.setText("Invalid input");
            }
        }
    }
}
