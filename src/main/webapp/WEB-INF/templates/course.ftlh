<html>
<head>
    <title>Course</title>
</head>
<body>
Lessons:
<#list lessons as lesson>
    Lesson #${lesson.lessonNumber}:<br>
    <video width="320" height="240" controls src="${lesson.videoUrl}" type="video/mp4"></video><br>
</#list>
</body>
</html>