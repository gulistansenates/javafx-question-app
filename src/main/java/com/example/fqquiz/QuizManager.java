package com.example.fqquiz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class QuizManager {
    private final List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;

    public void loadQuestions(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String questionText = line;
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = reader.readLine().substring(3);
                }
                char correct = reader.readLine().trim().charAt(0);
                questions.add(new Question(questionText, options, correct));
                reader.readLine();
            }
        } catch (Exception e) {
            System.err.println("Error loading questions: " + e.getMessage());
        }
    }

    public Question getNextQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex++);
        }
        return null;
    }

    public boolean hasNext() {
        return currentIndex < questions.size();
    }
}
