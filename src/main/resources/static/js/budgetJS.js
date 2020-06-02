"use strict";

function calculateBalance() {
    let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;
    document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2);
}

// jQuery = on button click, run incomeButton function that will make a json call to database and append value to id of "#income"

$("#importIncome").click(function (event) {
    event.preventDefault()
    incomeButton();
});

function incomeButton() {
    let request = $.ajax({
        url: '/transactions.json',
        type: "get",
        dataType: "text"
    });

    request.done(function (totalIncome) {
        console.log(totalIncome);
        console.log(typeof totalIncome);
        $("#income").val(totalIncome)
    });

    request.fail(function (parameter1, parameter2, error) {
        console.log(parameter1);
        console.log(parameter2);
        console.log(error);
    });

    // jQuery = on button click, run expenditureButton function that will make a json call to database and append value to id of "#expenditures"

    $("#importExpenditures").click(function (event) {
        event.preventDefault()
        expenditureButton();
    });

    function expenditureButton() {
        let request = $.ajax({
            url: '/transactions2.json',
            type: "get",
            dataType: "text"
        });

        request.done(function (totalExpenditures) {
            console.log(totalExpenditures);
            console.log(typeof totalExpenditures);
            $("#expenditures").val(totalExpenditures)
        });

        request.fail(function (parameter1, parameter2, error) {
            console.log(parameter1);
            console.log(parameter2);
            console.log(error);
        });
    }
}

