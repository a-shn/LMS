<html>
<head>
    <meta charset="UTF-8">
    <title>Course</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="../static/js/notifier.js"></script>
</head>
<body>
<#if isUserInCourse == false>
    <form method="post" action="joincourse?courseId=${courseId}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" id="btn" value="Join course">
    </form>
</#if>
Lessons:<br><br>
<#list lessons as lesson>
    <#if lesson.isWatched==true>Lesson #${lesson.lessonNumber} (watched):<br>
    <#else>Lesson #${lesson.lessonNumber}: <br>
    </#if>
    <video onended="notify(${courseId}, ${lesson.lessonNumber})" width="320" height="240" controls src="${lesson.videoUrl}"></video><p>
</#list>
</body>
</html>