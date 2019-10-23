// function sendDTOToController(){
//
//     var question = $("#question").val();
//     var category = $("#category").val();
//
//     $.ajax({
//         type: "POST",
//         contentType: "application/json",
//         url: "/addQuiz/submit",
//         data: {
//             question: question,
//             category: category
//         },
//         success: function(data){
//             console.log("addQuestionToDb(): AJAX call was successful, data: " +
//             data.question + ", " + data.category);
//         },
//         error: function(e){
//             console.log("ERROR: ", e);
//         },
//         complete: function() {
//             console.log("addQuestionToDb(): AJAX call complete")
//         }
//     });
//
//     $(".addedNotification").textContent="Question was added";
// }
