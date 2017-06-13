package me.tom.datetime.picker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public abstract class AbstractPicker {

    protected boolean mIsDayPickerAvailable;
    protected boolean mIsClearAvailable;

    protected ArrayList<Integer> mYears;
    protected ArrayList<Integer> mMonths;
    protected ArrayList<Integer> mDays;

    protected NumberPicker mYearPicker;
    protected NumberPicker mMonthPicker;
    protected NumberPicker mDayPicker;

    protected AlertDialog mAlertDialog;
    protected View mContentView;

    protected TextView mClearView;
    protected TextView mCancelView;
    protected TextView mOKView;

    abstract protected void setContentView(Context context);

    abstract protected void onClearViewClick();
    abstract protected void onOkViewClick();

    protected void initialize(Context context, int year, int month, int day, ArrayList<Integer> years, ArrayList<Integer> months) {
        setContentView(context);

        mYears = years;
        mMonths = months;

        mYearPicker = (NumberPicker) mContentView.findViewById(R.id.yearPicker);
        setPickerDividerColor(context, mYearPicker);
        String[] yearPickerDisplayValues = new String[mYears.size()];
        for (int index = 0; index < mYears.size(); index++) {
            yearPickerDisplayValues[index] = String.valueOf(mYears.get(index));
        }
        mYearPicker.setMinValue(0);
        mYearPicker.setMaxValue(mYears.size() - 1);
        mYearPicker.setDisplayedValues(yearPickerDisplayValues);
        if (mYears.indexOf(year) > -1) {
            mYearPicker.setValue(mYears.indexOf(year));
        } else {
            mYearPicker.setValue(0);
        }
        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal != oldVal) {
                    int monthIndex = mMonths.indexOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
                    if (monthIndex > -1) {
                        mMonthPicker.setValue(monthIndex);
                    } else {
                        mMonthPicker.setValue(0);
                    }
                    setDays(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                }
            }
        });

        mMonthPicker = (NumberPicker) mContentView.findViewById(R.id.monthPicker);
        setPickerDividerColor(context, mMonthPicker);
        String[] monthPickerDisplayValues = new String[mMonths.size()];
        for (int index = 0; index < mMonths.size(); index++) {
            if (mMonths.get(index) < 10) {
                monthPickerDisplayValues[index] = "0" + mMonths.get(index);
            } else {
                monthPickerDisplayValues[index] = String.valueOf(mMonths.get(index));
            }
        }
        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(mMonths.size() - 1);
        mMonthPicker.setDisplayedValues(monthPickerDisplayValues);
        if (mMonths.indexOf(month) > -1) {
            mMonthPicker.setValue(mMonths.indexOf(month));
        } else {
            mMonthPicker.setValue(0);
        }

        mDayPicker = (NumberPicker) mContentView.findViewById(R.id.dayPicker);
        setPickerDividerColor(context, mDayPicker);
        setDayPickerAvailableStatus(true);
        setDays(day);

        mClearView = (TextView) mContentView.findViewById(R.id.clear);
        setClearAvailableStatus(true);

        mCancelView = (TextView) mContentView.findViewById(R.id.cancel);
        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mOKView = (TextView) mContentView.findViewById(R.id.ok);
        mOKView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onOkViewClick();
            }
        });

        mAlertDialog = new AlertDialog.Builder(context).create();
        mAlertDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setCancelable(true);
        mAlertDialog.setCanceledOnTouchOutside(true);
        mAlertDialog.setView(mContentView);
    }

    protected void setPickerDividerColor(Context context, NumberPicker picker) {
        Field[] numberPickerFields = NumberPicker.class.getDeclaredFields();
        for (Field field : numberPickerFields) {
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    field.set(picker, ContextCompat.getDrawable(context, R.color.colorPrimary));
                } catch (IllegalArgumentException e) {
                } catch (Resources.NotFoundException e) {
                } catch (IllegalAccessException e) {
                }
                break;
            }
        }
    }

    protected void setDays(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYears.get(mYearPicker.getValue()), mMonths.get(mMonthPicker.getValue()) - 1, 1);
        int daysCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mDays = new ArrayList<>();
        String[] dayPickerDisplayValues = new String[daysCount];
        for (int index = 1; index <= daysCount; index++) {
            mDays.add(index);
            if (index < 10) {
                dayPickerDisplayValues[index - 1] = "0" + index;
            } else {
                dayPickerDisplayValues[index - 1] = String.valueOf(index);
            }
        }

        mDayPicker.setDisplayedValues(null);
        mDayPicker.setMinValue(0);
        mDayPicker.setMaxValue(mDays.size() - 1);
        mDayPicker.setDisplayedValues(dayPickerDisplayValues);

        if (mDays.indexOf(day) > -1) {
            mDayPicker.setValue(mDays.indexOf(day));
        }
    }

    public ArrayList<Integer> getDefaultYears() {
        ArrayList<Integer> years = new ArrayList<>();
        for (int index = 1970; index <= Calendar.getInstance().get(Calendar.YEAR) + 10; index++) {
            years.add(index);
        }

        return years;
    }

    public ArrayList<Integer> getDefaultMonths() {
        return new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    }

    public void setDayPickerAvailableStatus(boolean isDayPickerAvailable) {
        mIsDayPickerAvailable = isDayPickerAvailable;
        if (mIsDayPickerAvailable) {
            mDayPicker.setVisibility(View.VISIBLE);
            mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal != oldVal) {
                        setDays(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    }
                }
            });
        } else {
            mDayPicker.setVisibility(View.GONE);
            mMonthPicker.setOnValueChangedListener(null);
        }
    }

    public void setClearAvailableStatus(boolean isClearAvailable) {
        mIsClearAvailable = isClearAvailable;
        if (mIsClearAvailable) {
            mClearView.setVisibility(View.VISIBLE);
            mClearView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    onClearViewClick();
                }
            });
        } else {
            mClearView.setVisibility(View.GONE);
            mClearView.setOnClickListener(null);
        }
    }

    public boolean isShowing() {
        return mAlertDialog.isShowing();
    }

    public void show() {
        if (!isShowing()) {
            mAlertDialog.show();
        }
    }

    public void dismiss() {
        if (isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
