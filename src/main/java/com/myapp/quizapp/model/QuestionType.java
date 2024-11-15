package com.myapp.quizapp.model;

public enum QuestionType {
    SINGLE_CHOICE,  // 単一選択
    MULTIPLE_CHOICE; // 複数選択

    // fromString メソッドの追加
    public static QuestionType fromString(String value) {
        for (QuestionType type : QuestionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
