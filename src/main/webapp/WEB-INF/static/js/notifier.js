function notify(courseId, lesson) {
    let body = {
        courseId: courseId,
        lesson: lesson
    };

    $.ajax({
        url: "/lessonwatched",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
        }

    });
}