"use strict";
$("document").ready(function () {
    setTimeout(function calculateBalanceAutomatically() {
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;
        $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2)).trigger('click');
    }, 10);
});

    function calculateBalance() {
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;

        if (remainingBalance > 0) {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("darkgreen"));
        } else {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("firebrick"));
        }
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
            $("#income").val(totalIncome)
        });
    }

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
    }

// jQuery = on button click, run addGoalFunding function that will make a json call to database and append value to id of "#progressBar"

$("document").ready(function () {

    $("#addFundsButton").click(function (event) {
        event.preventDefault()
        addGoalFunding();
    });

    setTimeout(function addGoalFunding() {
        let request = $.ajax({
            url: '/goals.json',
            type: "get",
            dataType: "text"
        });

        request.done(function (goalTotal) {
            $(".progressBar").val(goalTotal).value.trigger('click')
        }, 10);

        $('#addFundsModal').on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget)
            let name = button.data('goal')
            let modal = $(this)
            modal.find('.modal-body input#goalName').val(name)
        });
    })
});
