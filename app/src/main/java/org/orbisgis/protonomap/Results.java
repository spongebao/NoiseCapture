package org.orbisgis.protonomap;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

public class Results extends MainActivity {

    static float Leqi; // for testing

    // For the Charts
    public PieChart rneChart;
    public PieChart neiChart;
    protected BarChart sChart; // Spectrum representation

    // Other ressources
    private String[] ltob;  // List of third-octave bands (defined as ressources)
    private String[] catNE; // List of noise level category (defined as ressources)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initDrawer();

        // RNE PieChart
        rneChart = (PieChart) findViewById(R.id.RNEChart);
        rneChart.setUsePercentValues(true);
        rneChart.setHoleColorTransparent(true);
        rneChart.setHoleRadius(40f);
        rneChart.setDescription("");
        rneChart.setDrawCenterText(true);
        rneChart.setDrawHoleEnabled(true);
        rneChart.setRotationAngle(0);
        rneChart.setRotationEnabled(true);
        rneChart.setDrawSliceText(false);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // rneChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        // mChart.setTouchEnabled(false);
        rneChart.setCenterText("RNE");
        rneChart.setCenterTextColor(Color.WHITE);
        setRNEData(4, 100);
        //rneChart.animateXY(1500, 1500, EasingFunction.EaseInOutQuad, EasingFunction.EaseInOutQuad);
        //rneChart.spin(2000, 0, 360);
        Legend lrne = rneChart.getLegend();
        lrne.setTextColor(Color.WHITE);
        lrne.setTextSize(8f);
        lrne.setPosition(LegendPosition.RIGHT_OF_CHART_CENTER);
        lrne.setEnabled(true); // Hide legend

        // NEI PieChart
        neiChart = (PieChart) findViewById(R.id.NEIChart);
        neiChart.setUsePercentValues(true);
        neiChart.setHoleColorTransparent(true);
        neiChart.setHoleRadius(75f);
        neiChart.setDescription("");
        neiChart.setDrawCenterText(true);
        neiChart.setDrawHoleEnabled(true);
        neiChart.setRotationAngle(90);
        neiChart.setRotationEnabled(true);
        neiChart.setDrawSliceText(false);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // rneChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        // mChart.setTouchEnabled(false);
        setNEIData(100);
        neiChart.setCenterText(String.format("%.1f", Leqi).concat(" dB(A)"));
        neiChart.setCenterTextSize(15);
        neiChart.setCenterTextColor(Color.WHITE);
        //rneChart.animateXY(1500, 1500, EasingFunction.EaseInOutQuad, EasingFunction.EaseInOutQuad);
        //rneChart.spin(2000, 0, 360);
        Legend lnei = neiChart.getLegend();
        //lnei.setPosition(LegendPosition.RIGHT_OF_CHART);
        lnei.setEnabled(false); // Hide legend

        // Change the text and the textcolor in the corresponding textview
        // for the Leqi value
        //final TextView mTextView = (TextView) findViewById(R.id.textView_value_SEL);
        //mTextView.setText(String.format("%.1f", Leqi));

        // Cumulated spectrum
        sChart = (BarChart) findViewById(R.id.spectrumChart);
        sChart.setDrawBarShadow(false);
        sChart.setDescription("");
        sChart.setPinchZoom(false);
        sChart.setDrawGridBackground(false);
        sChart.setMaxVisibleValueCount(0);
        // XAxis parameters: hide all
        XAxis xls = sChart.getXAxis();
        xls.setPosition(XAxisPosition.BOTTOM);
        xls.setDrawAxisLine(true);
        xls.setDrawGridLines(false);
        xls.setDrawLabels(true);
        xls.setTextColor(Color.WHITE);
        // YAxis parameters (left): main axis for dB values representation
        YAxis yls = sChart.getAxisLeft();
        yls.setDrawAxisLine(true);
        yls.setDrawGridLines(true);
        yls.setAxisMaxValue(141.f);
        yls.setStartAtZero(true);
        yls.setTextColor(Color.WHITE);
        yls.setGridColor(Color.WHITE);
        setDataS(30, 115);
        // YAxis parameters (right): no axis, hide all
        YAxis yrs = sChart.getAxisRight();
        yrs.setEnabled(false);
        // Legend: hide all
        Legend ls = sChart.getLegend();
        ls.setEnabled(false); // Hide legend

    }

    // Generate artificial data for spectrum representation
    private void setDataS(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        ltob= getResources().getStringArray(R.array.tob_list_array);
        for (int i = 0; i < count; i++) {
            xVals.add(ltob[i % 30]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (20f+Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        //set1.setBarSpacePercent(35f);
        //set1.setColors(new int[] {Color.rgb(0, 153, 204), Color.rgb(0, 153, 204), Color.rgb(0, 153, 204),
        //        Color.rgb(51, 181, 229), Color.rgb(51, 181, 229), Color.rgb(51, 181, 229)});
        set1.setColors(new int[] {Color.rgb(0, 128, 255), Color.rgb(0, 128, 255), Color.rgb(0, 128, 255),
                Color.rgb(102, 178, 255), Color.rgb(102, 178, 255), Color.rgb(102, 178, 255)});

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        sChart.setData(data);
    }


    // Generate artificial data for RNE
    private void setRNEData(int count, float range) {

        float mult3 = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult3) + mult3 / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        catNE= getResources().getStringArray(R.array.catNE_list_array);
        for (int i = 0; i < count + 1; i++)
            xVals.add(catNE[i % catNE.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Sound level");
        dataSet.setSliceSpace(3f);
        dataSet.setColors(NE_COLORS);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.BLACK);
        rneChart.setData(data);

        // undo all highlights
        //rneChart.highlightValues(null);
        //rneChart.invalidate();
    }

    // Generate artificial data for NEI
    private void setNEIData(float range) {

        float mult5 = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        Leqi = (float) Math.random() * mult5;
        yVals1.add(new Entry( Leqi, 0));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(catNE[0 % catNE.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "NEI");
        dataSet.setSliceSpace(3f);
        int nc=getNEcatColors(Leqi);    // Choose the color category in function of the sound level
        dataSet.setColor(NE_COLORS[nc]);   // Apply color category for the corresponding sound level

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setDrawValues(false);
        neiChart.setData(data);

        // undo all highlights
        //rneChart.highlightValues(null);
        //rneChart.invalidate();
    }

}
