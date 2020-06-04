// (function () {
    "use strict";

$('#deleteBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var curId = button.data('i')
    var modal = $(this)
    modal.find('.modal-footer button#delete-id').val(curId)
});

$('#editBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var name = button.parent().parent().children(":nth-child(1)").text()
    var date = button.parent().parent().children(":nth-child(2)").text()
    var amount = button.parent().parent().children(":nth-child(3)").text().substring(1)
    var id = button.data("id")
    var modal = $(this)
    modal.find(".modal-body input#edit-name").val(name);
    modal.find(".modal-body input#edit-due-date").val(date);
    modal.find(".modal-body input#edit-amount").val(amount);
    modal.find(".modal-body input#edit-id").val(id);
});

$('#payBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var id = button.data("pay")
    var name = button.parent().parent().children(":nth-child(1)").text()
    console.log(name);
    var amt = button.parent().parent().children(":nth-child(3)").text().substring(1)
    console.log(amt);
    var modal = $(this)
    modal.find('.modal-title span#pay-name').html(name)
    modal.find('.modal-body input#pay-amt').val(amt)
    modal.find('.modal-body input#payment-name').val(name)
    modal.find('.modal-body input#payment-id').val(id)
});

function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("bill-table");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir === "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir === "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}




// })();
