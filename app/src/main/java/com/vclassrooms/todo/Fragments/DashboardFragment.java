package com.vclassrooms.todo.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.NameValueDataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.charts.TagCloud;
import com.anychart.charts.Venn;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.scales.OrdinalColor;
import com.vclassrooms.todo.Model.DataPoint;
import com.vclassrooms.todo.Model.Task;
import com.vclassrooms.todo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {


    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    ProgressDialog progressDialog;

    private AdView adView;
    AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        AnyChartView anyChartView = view.findViewById(R.id.pieChart);
        adView = (AdView) view.findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        progressDialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).child("Tasks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                progressDialog.setMessage("wait a moment...");
                progressDialog.show();
                Pie pie = AnyChart.pie();

                List<DataEntry> data = new ArrayList<>();
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                        DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                        data.add(new ValueDataEntry(dataPoint.getTitle(), dataPoint.getTotalHour()));
                    }
                }
                pie.data(data);
                pie.labels().position("outside");
                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Tasks")
                        .padding(0d, 0d, 10d, 0d);

                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(Align.CENTER);

                anyChartView.setChart(pie);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
       // progressDialog.dismiss();
        return view;
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}