# Проект по автоматизации на сайт "[reqres.in](https://reqres.in)"

>  <a href="https://reqres.in" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/reqres.in.png" width="75" height="40"></a> - Бесплатный ресурс для тестирования API
---
## Содержание:

* <a href="#tools">Технологии и инструменты</a>

* <a href="#cases">Разделы, на которые реализованы тест кейсы</a>

* <a href="#jenkins">Сборка в Jenkins</a>

* <a href="#console">Команда для запуска из терминала</a>

* <a href="#allure">Allure отчет</a>

* <a href="#telegram">Уведомление в Telegram</a>

---

<a id="tools"></a>
### Технологии и инструменты:

<p align="center">
    <a href="https://www.jetbrains.com/idea/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/idea-logo.svg" width="70" height="70"></a>
    <a href="https://www.java.com/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/java-logo.svg" width="70" height="70"></a>
    <a href="https://github.com/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/github-logo.svg" width="70" height="70"></a>
    <a href="https://junit.org/junit5/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/junit5-logo.svg" width="70" height="70"></a>
    <a href="https://gradle.org/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/gradle-logo.svg" width="70" height="70"></a>
    <a href="https://docs.qameta.io/allure/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/allure-report-logo.svg" width="70" height="70"></a>
    <a href="https://www.jenkins.io/" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/jenkins-logo.svg" width="70" height="70"></a>
    <a href="https://rest-assured.io" target="_blank"><img align="center" src="https://github.com/Morozovk/Api/blob/master/media/rest_assured.png" width="70" height="70"></a>
</p>

---

<a id="cases"></a>
### Разделы, на которые реализованы тест кейсы:
- Проверка авторизации
- Проверка регистрации
- Проверка карточки юзера
- Проверка страницы с юзерами

---

<a id="jenkins"></a>
### Сборка в [Jenkins](https://jenkins.autotests.cloud/view/034/job/034-Morozovk98-API/)

- Реалиована джоба для удаленного запуска с выбором параметров

<p align="center">  
<img src="https://github.com/Morozovk/Api/blob/master/media/jenkins-job.png" width="950"/ alt="Jenkins-job"></a>  
</p>

<a id="params"></a>
#### Параметры сборки Jenkins:
Task - Выбор списка тест кейсов \
EMAIL - логин для авторизации \
PASSWORD - пароль для авторизации

<p align="center">  
<img src="https://github.com/Morozovk/Api/blob/master/media/jenlins-job-parametr.png" width="950"/ alt="Jenkins-job-parametr"></a>  
</p>

<a id="console"></a>
### Команда для запуска из терминала:

>gradlew clean test \
 gradle clean LogIn_test \
 gradle clean Registration_test \
 gradle clean UserCard_test \
 gradle clean Users_test

### Удаленный запуск через Jenkins:

> clean \
${Task} \
-DEMAIL="${EMAIL}" \
-DPASSWORD="${PASSWORD}"

---

<a id="allure"></a>
### Allure отчет

- Реализован Allure отчет

<p align="center">  
<img src="https://github.com/Morozovk/Api/blob/master/media/Allure-result.png" width="950"/ alt="Allure-result"></a>  
</p>

- В Allure отчете реализовно отображение Requst и Response

<p align="center">  
<img src="https://github.com/Morozovk/Api/blob/master/media/allure-testCases.png" width="950"/ alt="Allure-result"></a>  
</p>

---

<a id="telegram"></a>
### Уведомление в Telegram

- Реализован бот, который после каждого прохождения автотестов присылает уведомление с информацией по успешно пройденным или упавшим автотестам

<p align="center">  
<img src="https://github.com/Morozovk/Api/blob/master/media/telegram-bot.png" width="600"/ alt="Telegram-bot"></a>  
</p>
