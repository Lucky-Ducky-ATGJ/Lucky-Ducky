
"use strict";

google.charts.load("current", {packages:["corechart"]});  //this loads visualization API and corechart package
google.charts.setOnLoadCallback(drawChart); //this sets callback to run when the Google Visualization API is loaded

function drawChart() {

    let request = $.ajax({
        url: '/spentbycategory.json',
        type: "get",
        dataType: "text"
    });

    request.done(function (spentByCategory) {
        console.log(spentByCategory);
        console.log(typeof spentByCategory);
        // $("#expenditures").val(totalExpenditures)
    });

    request.fail(function (parameter1, parameter2, error) {
        console.log(parameter1);
        console.log(parameter2);
        console.log(error);
    });

    var data = google.visualization.arrayToDataTable([
        ['Task', 'Hours per Day'],
        ['Bills', 10],
        ['Food & Dining', 2],
        ['Entertainment', 2],
        ['Utilities', 2],
        ['Other', 20]
    ]);

    //below is possibly code I may use to create a new array with imported data from database
//     google.charts.setOnLoadCallback(function() {
//         drawChart(result);
//     });
//
    var options = {
        title: 'Summary Of Expenditures',
        pieHole: 0.3,
    };

    var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
    chart.draw(data, options);

}
