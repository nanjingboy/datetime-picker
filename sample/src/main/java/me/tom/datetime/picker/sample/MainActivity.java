package me.tom.datetime.picker.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import me.tom.datetime.picker.DatePicker;
import me.tom.datetime.picker.DateTimePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final RadioButton radioAllButtons = (RadioButton) findViewById(R.id.allButtons);

        Button datePickerButton = (Button) findViewById(R.id.datePicker);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(MainActivity.this);
                datePicker.setClearAvailableStatus(radioAllButtons.isChecked());
                datePicker.setDatePickerListener(new DatePicker.IDatePickerListener() {
                    @Override
                    public void onClear() {
                        Log.d("Date Picker:", "clear");
                    }

                    @Override
                    public void onOK(int year, int month, int day) {
                        Log.d("Date Picker:", year + "-" + month + "-" + day);
                    }
                });
                datePicker.show();
            }
        });

        Button datePickerWithoutDayButton = (Button) findViewById(R.id.datePickerWithoutDay);
        datePickerWithoutDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker(MainActivity.this);
                datePicker.setDayPickerAvailableStatus(false);
                datePicker.setClearAvailableStatus(radioAllButtons.isChecked());
                datePicker.setDatePickerListener(new DatePicker.IDatePickerListener() {
                    @Override
                    public void onClear() {
                        Log.d("Date Picker No Day:", "clear");
                    }
                    
                    @Override
                    public void onOK(int year, int month, int day) {
                        Log.d("Date Picker No Day:", year + "-" + month + "-" + day);
                    }
                });
                datePicker.show();
            }
        });

        Button datetimePickerButton = (Button) findViewById(R.id.datetimePicker);
        datetimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker dateTimePicker = new DateTimePicker(MainActivity.this);
                dateTimePicker.setClearAvailableStatus(radioAllButtons.isChecked());
                dateTimePicker.setDateTimePickerListener(new DateTimePicker.IDateTimePickerListener() {
                    @Override
                    public void onClear() {
                        Log.d("Date Time Picker:", "clear");
                    }

                    @Override
                    public void onOK(int year, int month, int day, int hour, int minute) {
                        Log.d("Date Time Picker:", year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                });
                dateTimePicker.show();
            }
        });
    }
}
