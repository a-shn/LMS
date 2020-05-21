<#import "lib/spring.ftl" as spring />
<html>
<head>
    <title><@spring.message "signup.page.signup"/></title>
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>
<div>
    <@spring.bind "signUpForm"/>
    <form method="post" action="signup">
        <center><h1><@spring.message "signup.page.signup"/></h1>
            <p><@spring.message "signup.page.login"/>:</p>
<#--            <input type="text" name="login"/>-->
            <@spring.formInput "signUpForm.login"/>
            <p>e-mail:</p>
            <@spring.formInput "signUpForm.email"/><br>
            <@spring.showErrors "<br>", "error"/>
<#--            <input type="text" name="email"/>-->
            <p><@spring.message "signup.page.password"/>:</p>
            <@spring.formInput "signUpForm.password"/><br>
            <@spring.showErrors "<br>", "error"/>
<#--            <input type="password" name="password"/>-->
            <p>
                <input type="submit" id="btn">
            </p></center>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"><br>
    </form>
</div>

</body>
</html>