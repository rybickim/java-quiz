function addQuestionToDb(){

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/addQuiz",
        success: function(){
            console.log("addQuestionToDb(): AJAX call was successful");
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("addQuestionToDb(): AJAX call complete")
        }
    });

    document.querySelector(".addedNotification").textContent="Question was added";
}
