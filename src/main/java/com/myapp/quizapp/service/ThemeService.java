package com.myapp.quizapp.service;

import java.util.List;

import com.myapp.quizapp.model.Theme;

public interface ThemeService {
    List<Theme> getAllThemes();
    public boolean isAuthenticationRequired(Long themeId);
    public Theme getThemeById(Long themeId);
}
