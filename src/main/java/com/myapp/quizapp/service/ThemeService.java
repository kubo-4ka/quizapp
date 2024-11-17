package com.myapp.quizapp.service;

import java.util.List;

import com.myapp.quizapp.model.Theme;

public interface ThemeService {
    List<Theme> getAllThemes();
    public Theme getThemeById(Long themeId);
}
