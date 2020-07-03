<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<div class="authorization">
    <div class="authorization__enter">
        <form method="POST" action="${pageContext.request.contextPath}/login">
            <h4 class="title">Вход</h4>
            <input type="text" name="email" id="auth-email" placeholder="Введите email"
                   class="authorization__enter__input input">
            <input type="text" name="password" id="auth-password" placeholder="Введите password"
                   class="authorization__enter__input input">
            <button id="authorization" class="authorization__enter__button button">Войти</button>
        </form>
    </div>
    <div class="authorization__registration">
        <form action="/reg" method="post">
            <h4 class="title">Регистрация</h4>
            <input type="text" name="name" placeholder="Введите имя" class="authorization__enter__input input">
            <input type="text" name="email" placeholder="Введите email" class="authorization__enter__input input">
            <input type="text" name="password" placeholder="Введите password"
                   class="authorization__enter__input input">
            <button type="submit" class="authorization__registration__button button" id="registration">
                Зарегистрироваться
            </button>
        </form>
    </div>
</div>
</body>
</html>