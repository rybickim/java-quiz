function drawQuestion(question){
    document.querySelector(".question").textContent="" + question;
    console.log(42);
}

function showAnswer(answer){
    console.log(answer);
    document.querySelector(".answer").textContent="" + answer;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/",
        data: JSON.stringify({index : 42}),
        dataType: "json",
        success: function(response){
            console.log(response);
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("AJAX call done")
        }
    });
}

function generateRandomIndex(count){
    // randomQuestionIndex = Math.floor(Math.random() * count);
    // console.log(randomQuestionIndex);
    // alert('generateRandomIndex(), randomQuestionIndex: ' + randomQuestionIndex);
    // document.querySelector(".question").textContent= "" + randomQuestionIndex;

}