<html>
<head>
    <title>Courses</title>
</head>
<body>
Your courses: <br>
<#list courses as course>
    <#if course.status == "DOWNLOADING">
    ${course.courseName} - DOWNLOADING <br>
    <#else>
    <a href="/course?courseId=${course.courseId}">${course.courseName}</a><br>
    </#if>
</#list>

<p>Upload new .torrent course</p>
<form method="POST" action="/uploadCourse" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit" value="upload torrent"/>
</form>
</body>
</html>