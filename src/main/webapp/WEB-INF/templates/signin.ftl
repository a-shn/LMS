<!doctype html>
<#import "lib/spring.ftl" as spring/>
<html lang="en">
<head>
    <title><@spring.message "signin.page.signin"/></title>
</head>
<body>
<form method="post" action="signin">
    <center><h1><@spring.message "signin.page.signin"/></h1>
        <p><a href="/signup"><@spring.message "signin.page.signup"/></a> </p>
        <p>e-mail:</p>
        <input type="email" name="email"/>
        <p><@spring.message "signin.page.password"/>:</p>
        <input type="password" name="password"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"><br>
        <label>
            <input type="checkbox" name="remember-me"><@spring.message "signin.page.rememberme"/>
        </label>
        <p>
            <input type="submit" id="btn">
        </p></center>
</form>
</body>
</html>