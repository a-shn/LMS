<html>
<head>
    <title>Courses</title>
</head>
<body>
Your courses: <br>
<#list courses as course>
    <a href="/course?courseId=${course.courseId}">${course.courseName}</a><br>
</#list>

<p>Upload new .torrent course</p>
<form method="POST" action="/uploadCourse" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit" value="upload torrent"/>
</form>
</body>
</html>