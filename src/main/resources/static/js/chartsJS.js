"use strict";

google.charts.load("current", {packages:["corechart"]});  //this loads visualization API and corechart package
google.charts.setOnLoadCallback(drawChart); //this sets callback to run when the Google Visualization API is loaded

function drawChart() {

    let request = $.ajax({
        url: '/spentbycategory.json',
        type: "get",
        dataType: "text"
    });

    let totalByCategory;
  request.done(function (spentByCategory) {
        console.log(spentByCategory);
        console.log(typeof spentByCategory);
        totalByCategory = spentByCategory;
        console.log("totalByCategory --> " + totalByCategory);
        // $("#expenditures").val(totalExpenditures)
      return spentByCategory
    });

    request.fail(function (parameter1, parameter2, error) {
        console.log(parameter1);
        console.log(parameter2);
        console.log(error);
    });

    console.log("This is totalByCategory second console log--> " + totalByCategory);
    console.log(typeof totalByCategory);

    var data = google.visualization.arrayToDataTable([
        ['Categories', '% of spending'],
        ['Bills', 25],
        ['Utilities', 15],
        ['Entertainment', 19],
        ['Food/Dining', 21],
        ['Fuel', 16],
        ['Lodging', 0],
        ['Loans', 0],
        ['Bills', 39],
        ['Miscellaneous', 20],
        ['Shopping', 0],
        ['Groceries', 12],
        ['Gifts/Donations', 1],
        ['Personal', 3],
        ['Budget Goal', 28]
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
