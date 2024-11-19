package com.myapp.quizapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Theme;
import com.myapp.quizapp.repositories.ThemeRepository;

@Service
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeRepository themeRepository;

	@Override
	public List<Theme> getAllThemes() {
		return themeRepository.findAll();
	}

	// メソッドで特定のテーマが認証を必要とするかどうかを判定
	@Override
    public boolean isAuthenticationRequired(Long themeId) {
		Theme selectedTheme = getThemeById(themeId);
		Logger logger = LogManager.getLogger();
		logger.error("★ ★ ★ThemeServiceImpl isAuthenticationRequired Start");
		logger.error("★themeId ; " + themeId);
		logger.error("★selectedTheme.getIsAuthenticationRequired() ; " + selectedTheme.getIsAuthenticationRequired());
        return selectedTheme.getIsAuthenticationRequired();
    }

    // 新しく追加するメソッド
	@Override
	public Theme getThemeById(Long themeId) {
		return themeRepository.findById(themeId)
				.orElseThrow(() -> new RuntimeException("Theme not found for id: " + themeId));
	}
}
