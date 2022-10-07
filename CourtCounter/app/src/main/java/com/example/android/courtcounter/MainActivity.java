package com.example.android.courtcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    /**
     * Displays the given scoreTeamA for Team A.
     */
    public void displayForTeamA(int scoreTeamA) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(scoreTeamA));
    }

    /**
     * Displays the given scoreTeamB for Team B.
     */
    public void displayForTeamB(int scoreTeamB) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(scoreTeamB));
    }

    /**
     * Add 3 point to scoreTeamA.
     */
    public void addScoreTeamA3(View view) {
        scoreTeamA = scoreTeamA+3;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Add 2 point to scoreTeamA.
     */
    public void addScoreTeamA2(View view) {
        scoreTeamA = scoreTeamA+2;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Add 1 point to scoreTeamA.
     */
    public void addScoreTeamA1(View view) {
        scoreTeamA = scoreTeamA+1;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Add 3 point to scoreTeamB.
     */
    public void addScoreTeamB3(View view) {
        scoreTeamB = scoreTeamB+3;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Add 2 point to scoreTeamB.
     */
    public void addScoreTeamB2(View view) {
        scoreTeamB = scoreTeamB+2;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Add 1 point to scoreTeamB.
     */
    public void addScoreTeamB1(View view) {
        scoreTeamB = scoreTeamB+1;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Reset scoreTeamA and scoreTeamB.
     */
    public void resetScoreTeams(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }
}
