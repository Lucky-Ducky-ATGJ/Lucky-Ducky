"use strict";

$(document).ready(function () {

    setTimeout(function calculateBalanceAutomatically() {
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;

        if (remainingBalance > 0) {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("green"));
        } else {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("red"));
        }

        $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2)).trigger('click');
    }, 10);
});

    function calculateBalance() {
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;

        if (remainingBalance > 0) {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("green"));
        } else {
            $(document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2).fontcolor("red"));
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
            $("#income").val(totalIncome/100)
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
            $("#expenditures").val(totalExpenditures/100)
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

$('#deleteGoal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget) // Button that triggered the modal
    let curId = button.data('i') // Current bill id
    let modal = $(this)
    modal.find('.modal-footer button#delete-goal-id').val(curId)
});

$('#editGoal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget)
    let name = button.parent().children(":nth-child(1)").text()
    let amount = button.parent().children(":nth-child(2)").val()
    let id = button.data("id") // Reads as "th:data-id"
    let modal = $(this)
    modal.find(".modal-body input#edit-goal-name").val(name);
    modal.find(".modal-body input#edit-goal-amount").val(amount);
    modal.find(".modal-body input#edit-goal-id").val(id);
});

$('#quackulator_calculate').click(function(){
    $('.quackulator_container_start').css("background-image", 'url("../img/quackulator_end.png")')
    setTimeout(function(){
        $('.quackulator_container_start').css("background-image", 'url("../img/quackulator_start.png")')
    }, 800)
});
