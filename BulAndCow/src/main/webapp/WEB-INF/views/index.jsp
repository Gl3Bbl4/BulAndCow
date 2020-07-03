<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Бык или Корова</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1 class="header__title">Игра "Бык или Корова"</h1>
            <a class="header__auth" href="${pageContext.request.contextPath}/authorization">Авторизация</a>
        </header>

        <main class="content">
            <div class="content__game">
                <h4 class="content__game__name title">Игра</h4>
                <form action="${pageContext.request.contextPath}/enterValue" method="post">
                    <div class="content__game__panel">
                        <input type="number" name="value" size="9" maxlength="4" placeholder="Введите 4-х значное число" class="content__game__panel__input input">
                        <input type="hidden" name="idGame" value="${idGame}" class="content__game__panel__input input">
                        <button id="button" class="content__game__panel__button button">Сделать ход</button>
                    </div>
                </form>
            </div>
            <div class="content__steps">
                <h4 class="content__steps__name title">Ходы</h4>
                <ul>
                <c:forEach  items="${stepList}" var ="step">
                        <li>${step}</li>
                    <ul>
                </c:forEach>
            </div>
            <div class="content__rate">
                <h4 class="content__rate__name title">Рейтинг</h4>
                <table border="1">
                    <tr>
                        <th>Имя</th>
                        <th>Рейтинг</th>
                    </tr>
                    <c:forEach  items="${ratingVOList}" var ="ratingVO">
                    <tr>
                        <td>${ratingVO.player.name}</td>
                        <td>${ratingVO.avgAttempt}</td>
                    </tr>
                    </c:forEach>
            </div>
        </main>
    </div>
</body>
</html>