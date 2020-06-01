"use strict";
    function calculateBalance(){
        let remainingBalance = document.getElementById('income').value - document.getElementById('expenditures').value;
        document.getElementById('balance').innerHTML = "$" + remainingBalance.toFixed(2);
    }
