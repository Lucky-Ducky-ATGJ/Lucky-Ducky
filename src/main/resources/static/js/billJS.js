// (function () {
    "use strict";

$('#deleteBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var curId = button.data('i') // Current bill id
    var modal = $(this)
    modal.find('.modal-footer button#delete-id').val(curId)
});

$('#editBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var name = button.parent().parent().children(":nth-child(1)").text() // Current bill name
    var date = button.parent().parent().children(":nth-child(2)").text() // Current bill date
    var amount = button.parent().parent().children(":nth-child(3)").text() // Current bill amount
    var id = button.data("id")
    var modal = $(this)
    modal.find(".modal-body input#edit-name").val(name);
    modal.find(".modal-body input#edit-due-date").val(date);
    modal.find(".modal-body input#edit-amount").val(amount);
    modal.find(".modal-body input#edit-id").val(id);
});





// })();
