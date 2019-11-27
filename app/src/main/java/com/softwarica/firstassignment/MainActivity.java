package com.softwarica.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvCheckDate, tvCheckoutDate, tvResult, tvRoomPrice;
    Spinner spinRoom;
    EditText etAdult, etChild, etRoom;
    Button btnCalculate;
    int year1, year2;
    int month1, month2;
    int day1, day2;
    int adult, children, room;
    double total, roomPrice, vat, grossTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding
        tvCheckDate= findViewById(R.id.tvCheckDate);
        tvCheckoutDate = findViewById(R.id.tvCheckoutDate);
        tvResult = findViewById(R.id.tvResult);
        tvRoomPrice = findViewById(R.id.tvRoomPrice);
        spinRoom = findViewById(R.id.spinRoom);
        etAdult = findViewById(R.id.etAdult);
        etChild = findViewById(R.id.etChild);
        etRoom = findViewById(R.id.etRoom);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Data in array for spinner (Different Rooms)
        final String rooms[] = {"Delux - Rs. 2000", "Presidential - Rs. 5000", "Premium - Rs. 4000"};
        ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                rooms
        );
        spinRoom.setAdapter(adapter);

        // For Checkin Date
        tvCheckDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckinDate();
            }
        });

        // For Checkout Date
        tvCheckoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckoutDate();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validation
                if (TextUtils.isEmpty(etAdult.getText())) {
                    etAdult.setError("Please enter number of adult.");
                    return;
                }
                if (TextUtils.isEmpty(etChild.getText())) {
                    etChild.setError("Please enter number of children.");
                    return;
                }
                if (TextUtils.isEmpty(etRoom.getText())) {
                    etRoom.setError("Please enter number of rooms.");
                    return;
                }


                Calendar c = Calendar.getInstance();
                c.set(year1, month1, day1);
                Calendar c1 = Calendar.getInstance();
                c1.set(year2, month2, day2);

                //Calculating the difference in selected dates
                long diffMillis = c1.getTimeInMillis() - c.getTimeInMillis();
                long daysDiff = (diffMillis / (24 * 60 * 60 * 1000));


                adult = Integer.parseInt(etAdult.getText().toString());
                children = Integer.parseInt(etChild.getText().toString());
                room = Integer.parseInt(etRoom.getText().toString());


                // Defining the price of different rooms
                if (spinRoom.getSelectedItem() == "Deluxe - Rs. 2000") {
                    tvRoomPrice.setText("2000");
                }
                if (spinRoom.getSelectedItem() == "Presidential - Rs. 5000") {
                    tvRoomPrice.setText("5000");
                }
                if (spinRoom.getSelectedItem() == "Premium - Rs. 4000") {
                    tvRoomPrice.setText("4000");
                }

                roomPrice = Double.parseDouble(tvRoomPrice.getText().toString());

                total = roomPrice * room * daysDiff;
                vat = total * 0.13;
                grossTotal = total + vat;

                // Displaying Result
                tvResult.setText("Total: Rs." + total + "\n" + "Vat Rs.:" + vat + "\n" + "Gross Total: Rs." + grossTotal);
            }
        });
    }


    // Show selected checkin dates to users
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Check in Date: \n" + (month + 1) + "/" + dayOfMonth + "/" + year;
        day1 = dayOfMonth;
        month1 = month;
        year1 = year;
        tvCheckDate.setText(date);
    }

    private void loadCheckinDate() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, year, month, day);
        datePickerDialog.show();
    }


    // Show selected checkout dated to users
    private void loadCheckoutDate() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Check out Date: \n" + (month + 1) + "/" + dayOfMonth + "/" + year;
                day2 = dayOfMonth;
                month2 = month;
                year2 = year;
                tvCheckoutDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
