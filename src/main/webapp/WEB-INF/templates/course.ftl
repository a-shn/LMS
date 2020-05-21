<html>
<head>
        <meta charset="UTF-8">
    <title>Course</title>
</head>
<body>
<#if isUserInCourse == false>
    <form method="post" action="joincourse?courseId=${courseId}">
        <input type="submit" id="btn" value="Join course">
    </form>
</#if>
Lessons:<br><br>
<#list lessons as lesson>
    <#if lesson.isWatched==true>Lesson #${lesson.lessonNumber} (watched):<br>
    <#else>Lesson #${lesson.lessonNumber}
        <form method="post" action="lessonwatched?courseId=${courseId}&lessonNumber=${lesson.lessonNumber}">
            <input type="submit" id="btn" value="watched">
        </form>
    </#if>
    <video width="320" height="240" controls src="${lesson.videoUrl}"></video><p>
</#list>
</body>
</html>