package com.m2dl.cracotte.cracottechallenge.scores;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.m2dl.cracotte.cracottechallenge.R;
import com.m2dl.cracotte.cracottechallenge.game.GameActivity;
import com.m2dl.cracotte.cracottechallenge.menu.MenuActivity;
import com.m2dl.cracotte.cracottechallenge.scores.domain.Score;
import com.m2dl.cracotte.cracottechallenge.scores.domain.ScoresTable;
import com.m2dl.cracotte.cracottechallenge.scores.services.GlobalScoresService;
import com.m2dl.cracotte.cracottechallenge.scores.services.LocalScoresService;
import com.m2dl.cracotte.cracottechallenge.utils.service.InternetConnectivityService;

import java.util.Map;
import java.util.TreeSet;

public class ScoresActivity extends Activity {
    private static final int NB_SCORES_DISPLAYED = 10;

    private ScoresTable globalScoresTable;
    private ScoresTable localScoresTable;
    String[] playerNamesList = new String[NB_SCORES_DISPLAYED];
    String[] playerScoresList = new String[NB_SCORES_DISPLAYED];

    private Button publishScoreButton;
    private Button playAgainButton;
    private Button menuButton;
    private TextView currentPersonnalScoreTextView;
    private RecyclerView recyclerView;
    private TabLayout scoresTabLayout;
    private EditText nameEditText;
    private TextView nameTextView;

    private long score;
    private boolean hasNewScore;
    private GlobalScoresService globalScoresService;
    private LocalScoresService localScoresService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initDatabases();
        initData();
        initComponents();
        initRecyclerView();
        updateScoresDisplay();
    }

    private void initDatabases() {
        initGlobalDatabase();
        initLocalDatabase();
    }

    private void initGlobalDatabase() {
        if (InternetConnectivityService.isInternetReachable(this)) {
            globalScoresService = new GlobalScoresService(this);
        } else {
            Toast.makeText(this, getResources().getString(R.string.scores_toast_pasInternetPasChargement), Toast.LENGTH_SHORT).show();
        }
    }

    private void initLocalDatabase() {
        localScoresService = new LocalScoresService(getApplicationContext());
    }

    public void receiveNewDataFromDatabase(ScoresTable newScoresTable) {
        globalScoresTable = newScoresTable;
        updateDynamicData();
    }

    public void updateDynamicData() {
        updateLocalScores();
        updateScoresDisplay();
        updateRecyclerView();
    }

    private void initData() {
        initScore();
        initHasNewScore();
        updateLocalScores();
    }

    private void initScore() {
        score = getIntent().getLongExtra("scorePerformed", 0);
    }

    private void updateLocalScores() {
        localScoresTable = localScoresService.getRegisteredScores();
    }

    private void initHasNewScore() {
        hasNewScore = getIntent().getBooleanExtra("hasNewScore", false);
    }

    private void updateScoresDisplay() {
        ScoresTable scoresToShow;
        if (scoresTabLayout.getSelectedTabPosition() == 0) {
            scoresToShow = localScoresTable;
        } else if (scoresTabLayout.getSelectedTabPosition() == 1) {
            if (InternetConnectivityService.isInternetReachable(this)) {
                scoresToShow = globalScoresTable;
            } else {
                Toast.makeText(this, getResources().getString(R.string.scores_toast_pasInternetPasChargement), Toast.LENGTH_SHORT).show();
                scoresToShow = localScoresTable;
                scoresTabLayout.selectTab(scoresTabLayout.getTabAt(0));
            }
        } else {
            return; // TODO erreur
        }
        if (scoresToShow != null  && scoresToShow.getScores() != null) {
            TreeSet<Score> treeSetScores = createTreeSetOfScores(scoresToShow);
            for (int i=1; i<=NB_SCORES_DISPLAYED; i++) {
                if (i >= scoresToShow.getNbScores()) {
                    playerNamesList[i - 1] = "";
                    playerScoresList[i - 1] = "";
                } else {
                    Score currentScore = treeSetScores.pollFirst();
                    if (currentScore != null){
                        playerNamesList[i - 1] = currentScore.getPlayerName();
                        playerScoresList[i - 1] = currentScore.getScore().toString();
                    }
                }
            }
        }
    }

    private TreeSet<Score> createTreeSetOfScores(ScoresTable scoresTable) {
        TreeSet<Score> treeSetScores = new TreeSet<>();
        for (Map.Entry<String, Score> entry : scoresTable.getScores().entrySet()) {
            treeSetScores.add(entry.getValue());
        }
        return treeSetScores;
    }

    private void initComponents() {
        initPublishScoreButton();
        initPlayAgainButton();
        initMenuButton();
        initNameEditText();
        initNameTextView();
        initCurrentPersonnalScoreTextView();
        updateRecyclerView();
        initTabLayout();
    }

    private void initPublishScoreButton() {
        publishScoreButton  = findViewById(R.id.score_button_publier);
        publishScoreButton.setOnClickListener(listener -> {
            String playerName = nameEditText.getText().toString();
            if (hasNewScore && !playerName.isEmpty()) {
                hasNewScore = false;
                localScoresService.savePlayerName(playerName);
                if (InternetConnectivityService.isInternetReachable(this)) {
                    globalScoresService.publishNewScore(playerName, score);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.scores_toast_pasInternetPasPublication), Toast.LENGTH_SHORT).show();
                }
                localScoresService.publishNewScore(playerName, score);
                updateDynamicData();
            } else if (!hasNewScore){
                Toast.makeText(this, getResources().getString(R.string.scores_toast_scoreDejaPublie), Toast.LENGTH_SHORT).show();

            } else if (playerName.isEmpty()){
                Toast.makeText(this, getResources().getString(R.string.scores_toast_nomVide), Toast.LENGTH_SHORT).show();
            }
        });
        if (!hasNewScore) {
            publishScoreButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initPlayAgainButton() {
        playAgainButton = findViewById(R.id.score_button_rejouer);
        playAgainButton.setOnClickListener(listener -> {
            Intent gameIntent = new Intent().setClass(listener.getContext(), GameActivity.class);
            startActivity(gameIntent);
            finish();
        });
        if (!hasNewScore) {
            playAgainButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initMenuButton() {
        menuButton = findViewById(R.id.score_button_menu);
        menuButton.setOnClickListener(listener -> {
            Intent mainIntent = new Intent().setClass(listener.getContext(), MenuActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    private void initNameEditText() {
        nameEditText = findViewById(R.id.score_plainText_yourName);
        String playerName = localScoresService.getSavedPlayerName();
        nameEditText.setText(playerName);
        if (!hasNewScore) {
            nameEditText.setVisibility(View.INVISIBLE);
        }
    }

    private void initNameTextView() {
        nameTextView = findViewById(R.id.score_textView_yourName);
        if (!hasNewScore) {
            nameTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initCurrentPersonnalScoreTextView() {
        currentPersonnalScoreTextView = findViewById(R.id.score_textView_scoreRealise);
        String text = getResources().getString(R.string.scores_textView_scoreEffectue) + " " + score;
        currentPersonnalScoreTextView.setText(text);
        if (!hasNewScore) {
            currentPersonnalScoreTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initRecyclerView() {
        recyclerView.findViewById(R.id.score_recyclerView_affichageScores);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    outRect.right = 0;
                    outRect.left = 0;
                    outRect.top = -6;
                    outRect.bottom = -6;
                } else {
                    outRect.right = 0;
                    outRect.left = 0;
                    outRect.top = -2;
                    outRect.bottom = -2;
                }
            }
        });
    }

    private void updateRecyclerView() {
        recyclerView = findViewById(R.id.score_recyclerView_affichageScores);
        ScoresAdapter scoresAdapter = new ScoresAdapter(playerNamesList,playerScoresList);
        recyclerView.setAdapter(scoresAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTabLayout() {
        scoresTabLayout = findViewById(R.id.score_tabLayout_scoresTabLayout);
        scoresTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateDynamicData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
