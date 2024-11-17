package com.myapp.quizapp.service;

import java.util.List;

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

	// 新しく追加するメソッド
	public Theme getThemeById(Long themeId) {
		return themeRepository.findById(themeId)
				.orElseThrow(() -> new RuntimeException("Theme not found for id: " + themeId));
	}
}
