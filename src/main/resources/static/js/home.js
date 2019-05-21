function drawQuestion(question){
    document.querySelector(".question").textContent="" + question;
    console.log(42);
}

function showAnswer(count){
    document.querySelector(".answer").textContent="Answer was..." + count + "?";
}

function generateRandomIndex(count){
    document.querySelector(".question").textContent= "" + Math.floor(Math.random() * count);
}