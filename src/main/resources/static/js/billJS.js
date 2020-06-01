// (function () {
    "use strict";

$('#deleteBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var curId = button.data('i') // Current bill id
    var modal = $(this) // current modal
    modal.find('.modal-footer button#delete-id').val(curId) // set bill id to value in button
});

$('#editBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var name = button.parent().parent().children(":nth-child(1)").text() // Current bill name
    var date = button.parent().parent().children(":nth-child(2)").text() // Current bill date
    var amount = button.parent().parent().children(":nth-child(3)").text() // Current bill amount
    var id = button.data("id") // Current bill id
    var modal = $(this) // current modal
    modal.find(".modal-body input#edit-name").val(name); // set bill name to show in input
    modal.find(".modal-body input#edit-due-date").val(date); // set bill duedate to show in input
    modal.find(".modal-body input#edit-amount").val(amount); // set bill amount to show in input
    modal.find(".modal-body input#edit-id").val(id); // set bill id to value in hidden input
});





// })();
