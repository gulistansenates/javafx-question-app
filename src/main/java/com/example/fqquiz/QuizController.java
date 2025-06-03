package com.example.fqquiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class QuizController {
    @FXML private Label questionLabel;
    @FXML private VBox optionsBox;
    @FXML private Label resultLabel;
    @FXML private Label timerLabel;

    private final QuizManager quizManager = new QuizManager();
    private Timeline countdown;
    private int timeLeft = 10;

    @FXML
    public void initialize() {
        quizManager.loadQuestions("/com/example/fqquiz/questions.txt");
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        resultLabel.setText("");
        optionsBox.getChildren().clear();
        if (!quizManager.hasNext()) {
            questionLabel.setText("🎉 Quiz Finished!");
            timerLabel.setText("");
            return;
        }

        Question q = quizManager.getNextQuestion();
        questionLabel.setText(q.getQuestionText());

        char optionLetter = 'A';
        for (String option : q.getOptions()) {
            Button btn = new Button(optionLetter + ") " + option);
            btn.setMaxWidth(Double.MAX_VALUE);
            char finalLetter = optionLetter;
            btn.setOnAction(e -> {
                stopTimer();
                if (finalLetter == q.getCorrectAnswer()) {
                    resultLabel.setText("✅ Correct!");
                } else {
                    resultLabel.setText("❌ Wrong! Correct: " + q.getCorrectAnswer());
                }
                new Timeline(new KeyFrame(Duration.seconds(2), ev -> loadNextQuestion())).play();
            });
            optionsBox.getChildren().add(btn);
            optionLetter++;
        }

        startTimer();
    }

    private void startTimer() {
        timeLeft = 10;
        timerLabel.setText("Time: " + timeLeft);
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                stopTimer();
                resultLabel.setText("⏳ Time's up!");
                new Timeline(new KeyFrame(Duration.seconds(2), ev -> loadNextQuestion())).play();
            }
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    private void stopTimer() {
        if (countdown != null) {
            countdown.stop();
        }
    }
}
