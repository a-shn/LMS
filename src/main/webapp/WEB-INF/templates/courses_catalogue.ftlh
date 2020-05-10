<html>
<head>
    <title>Courses</title>
</head>
<body>
Public courses: <br>
<#list courses as course>
    <a href="/course?courseId=${course.courseId}">${course.courseName}</a><br>
</#list>
</body>
</html>