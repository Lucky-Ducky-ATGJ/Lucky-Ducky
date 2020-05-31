"use strict";
    function calculateBalance(){
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;
        document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2);
    }

    function importIncome(){
        let getIncome = "SELECT sum(amount_in_cents) as totalIncome FROM transaction as t WHERE t.is_income = true";
    }
