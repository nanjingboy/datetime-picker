package me.tom.datetime.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;

public class DateTimePicker extends AbstractPicker {

    protected NumberPicker mHourPicker;
    protected NumberPicker mMinutePicker;

    private IDateTimePickerListener mDateTimePickerListener;

    public interface IDateTimePickerListener {
        void onClear();
        void onOK(int year, int month, int day, int hour, int minute);
    }

    protected void setContentView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = layoutInflater.inflate(R.layout.dialog_datetime_picker, null);
    }

    protected void initialize(Context context, int year, int month, int day, int hour, int minute, ArrayList<Integer> years, ArrayList<Integer> months) {
        super.initialize(context, year, month, day, years, months);

        mHourPicker = (NumberPicker) mContentView.findViewById(R.id.hourPicker);
        setPickerDividerColor(context, mHourPicker);
        String[] hourPickerDisplayValues = new String[24];
        for (int index = 0; index < 24; index++) {
            if (index < 10) {
                hourPickerDisplayValues[index] = "0" + index;
            } else {
                hourPickerDisplayValues[index] = String.valueOf(index);
            }
        }
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);
        mHourPicker.setDisplayedValues(hourPickerDisplayValues);
        if (hour >= 0 && hour < 24) {
            mHourPicker.setValue(hour);
        } else {
            mHourPicker.setValue(0);
        }

        mMinutePicker = (NumberPicker) mContentView.findViewById(R.id.minutePicker);
        setPickerDividerColor(context, mMinutePicker);
        String[] minutePickerDisplayValues = new String[60];
        for (int index = 0; index < 60; index++) {
            if (index < 10) {
                minutePickerDisplayValues[index] = "0" + index;
            } else {
                minutePickerDisplayValues[index] = String.valueOf(index);
            }
        }
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setDisplayedValues(minutePickerDisplayValues);
        if (minute >= 0 && minute < 60) {
            mMinutePicker.setValue(minute);
        } else {
            mMinutePicker.setValue(0);
        }
    }

    @Override
    protected void onClearViewClick() {
        if (mDateTimePickerListener != null) {
            mDateTimePickerListener.onClear();
        }
    }

    @Override
    protected void onOkViewClick() {
        if (mDateTimePickerListener != null) {
            mDateTimePickerListener.onOK(
                    mYears.get(mYearPicker.getValue()),
                    mMonths.get(mMonthPicker.getValue()),
                    mDays.get(mDayPicker.getValue()),
                    mHourPicker.getValue(),
                    mMinutePicker.getValue()
            );
        }
    }

    public DateTimePicker(Context context) {
        this(context,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));
    }

    public DateTimePicker(Context context, int year, int month, int day, int hour, int minute) {
        initialize(context, year, month, day, hour, minute, getDefaultYears(), getDefaultMonths());
    }

    public DateTimePicker(Context context, ArrayList<Integer> years, ArrayList<Integer> months) {
        this(context,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                years,
                months);
    }

    public DateTimePicker(Context context, int year, int month, int day, int hour, int minute, ArrayList<Integer> years, ArrayList<Integer> months) {
        initialize(context, year, month, day, hour, minute, years, months);
    }

    public void setDateTimePickerListener(IDateTimePickerListener dateTimePickerListener) {
        mDateTimePickerListener = dateTimePickerListener;
    }

    @Override
    public void setDayPickerAvailableStatus(boolean isDayPickerAvailable) {
    }
}
