function sendMessage(text) {
    let body = {
        text: text
    };

    $.ajax({
        url: "/sendMessage",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
        }

    });
}

// LONG POLLING
function receiveMessage() {
    $.ajax({
        url: "/getNewMessage",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            $('#messages').first().after('<li>\'' + response[0]['text'] + '\' - said ' + response[0]["from"] + '</li>');
            receiveMessage();
        }
    })
}

function initiateMessages() {
    $.ajax({
        url: "/getMessages",
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            for (let i = response.length - 1; i >= 0 ; i--) {
                $('#messages').first().after('<li>\'' + response[i]['text'] + '\' - said ' + response[i]["from"] + '</li>');
            }
            receiveMessage();
        }
    })
}