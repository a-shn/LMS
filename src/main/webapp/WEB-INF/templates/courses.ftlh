<html>
<head>
    <title>Courses</title>
</head>
<body>
Your courses:
<#list courses as course>
    ${course}
</#list>

<p>Upload new .torrent course</p>
<form method="POST" action="/uploadCourse" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit" value="upload torrent"/>
</form>
</body>
</html>