randomQuestionIndex = 0;
console.log(randomQuestionIndex);

function drawQuestion(question){
    document.querySelector(".question").textContent="" + question;
    console.log(42);
}

function showAnswer(count){
    console.log(randomQuestionIndex);
    console.log(count);
    alert('JC! A bomb!');
    document.querySelector(".answer").textContent="Answer was..." + count + "?";
}

function generateRandomIndex(count){
    randomQuestionIndex = Math.floor(Math.random() * count);
    console.log(randomQuestionIndex);
    alert('JC! A bomb!');
    document.querySelector(".question").textContent= "" + randomQuestionIndex;
}