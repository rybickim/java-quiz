function sendDTOToController(){

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/addQuiz/submit?question=" + data.question + "&category=" + data.category,
        data: {
            question: $("#question").val(),
            category: $("#category").val()
        },
        success: function(data){
            console.log("addQuestionToDb(): AJAX call was successful, data: " +
            data.question + ", " + data.category);
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        complete: function() {
            console.log("addQuestionToDb(): AJAX call complete")
        }
    });

    $(".addedNotification").textContent="Question was added";
}
