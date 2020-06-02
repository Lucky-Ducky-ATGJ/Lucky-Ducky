(function() {
    "use strict";

    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Bills', 10],
            ['Food & Dining', 2],
            ['Entertainment', 2],
            ['Utilities', 2],
            ['Other', 20]
        ]);

        var options = {
            title: 'Summary Of Expenditures',
            pieHole: 0.3,
        };

        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
    }
})();