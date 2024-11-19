package com.myapp.quizapp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		// SavedRequestを取得（ログイン前のリクエストがあればそのURLにリダイレクト）
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		// themeIdを取得
		String themeId = request.getParameter("themeId");
		String targetUrl = "/quiz/set-questions";

		if (savedRequest != null) {
			// SavedRequestがある場合、そのURLにリダイレクト
			targetUrl = savedRequest.getRedirectUrl();
		} else if (themeId != null) {
			// SavedRequestがなく、themeIdがある場合に限り、テーマのURLにリダイレクト
			targetUrl += "?themeId=" + themeId;
		}

		// リダイレクト先を設定
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
