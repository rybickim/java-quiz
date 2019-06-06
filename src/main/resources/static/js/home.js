function drawQuestion(question){

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/",
        success: function(){
            console.log("drawQuestion(): AJAX call was successful");
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("drawQuestion(): AJAX call complete")
        }
    });

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
            console.log("showAnswer(): AJAX call was successful");
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("showAnswer(): AJAX call complete")
        }
    });
}

function refreshPage() {
    window.location.href="/";
}
