package me.tom.datetime.picker;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class DatePicker extends AbstractPicker {

    private IDatePickerListener mDatePickerListener;

    public interface IDatePickerListener {
        void onClear();
        void onOK(int year, int month, int day);
    }

    protected void setContentView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = layoutInflater.inflate(R.layout.dialog_date_picker, null);
    }

    @Override
    protected void onClearViewClick() {
        if (mDatePickerListener != null) {
            mDatePickerListener.onClear();
        }
    }

    @Override
    protected void onOkViewClick() {
        if (mDatePickerListener != null) {
            if (mIsDayPickerAvailable) {
                mDatePickerListener.onOK(
                        mYears.get(mYearPicker.getValue()),
                        mMonths.get(mMonthPicker.getValue()),
                        mDays.get(mDayPicker.getValue())
                );
            } else {
                mDatePickerListener.onOK(
                        mYears.get(mYearPicker.getValue()),
                        mMonths.get(mMonthPicker.getValue()),
                        1
                );
            }
        }
    }

    public DatePicker(Context context) {
        super(context);
    }

    public DatePicker(Context context, int year, int month) {
        super(context, year, month);
    }

    public DatePicker(Context context, int year, int month, int day) {
        super(context, year, month, day);
    }

    public DatePicker(Context context, ArrayList<Integer> years, ArrayList<Integer> months) {
        super(context, years, months);
    }

    public DatePicker(Context context, int year, int month, ArrayList<Integer> years, ArrayList<Integer> months) {
        super(context, year, month, years, months);
    }

    public DatePicker(Context context, int year, int month, int day, ArrayList<Integer> years, ArrayList<Integer> months) {
        super(context, year, month, day, years, months);
    }

    public void setDatePickerListener(IDatePickerListener datePickerListener) {
        mDatePickerListener = datePickerListener;
    }
}
