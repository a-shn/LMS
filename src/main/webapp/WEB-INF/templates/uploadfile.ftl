<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>upload file</title>
</head>
<body>
    <form method="POST" action="uploadfiles" style="text-align: center" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="file" name="file"/><br/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>