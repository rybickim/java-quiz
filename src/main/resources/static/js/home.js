var randomQuestionIndex;
console.log(randomQuestionIndex);

function drawQuestion(question){
    document.querySelector(".question").textContent="" + question;
    console.log(42);
}

function showAnswer(count){
    console.log(randomQuestionIndex);
    console.log(count);
    alert('showAnswer(), count: ' + count);
    document.querySelector(".answer").textContent="Answer was..." + count + "?";
}

function generateRandomIndex(count){
    randomQuestionIndex = Math.floor(Math.random() * count);
    console.log(randomQuestionIndex);
    alert('generateRandomIndex(), randomQuestionIndex: ' + randomQuestionIndex);
    document.querySelector(".question").textContent= "" + randomQuestionIndex;
}