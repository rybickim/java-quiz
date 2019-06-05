function drawQuestion(question){
    console.log(question);
    document.querySelector(".question").textContent="" + question;
}

function showAnswer(answer){
    console.log(answer);
    document.querySelector(".answer").textContent="" + answer;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/",
        success: function(){
            console.log("AJAX call was successful");
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("AJAX call complete")
        }
    });
}
