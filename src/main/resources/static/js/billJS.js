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
    var amount = button.parent().parent().children(":nth-child(3)").text()
    var id = button.data("id")
    var modal = $(this)
    modal.find(".modal-body input#edit-name").val(name);
    modal.find(".modal-body input#edit-due-date").val(date);
    modal.find(".modal-body input#edit-amount").val(amount);
    modal.find(".modal-body input#edit-id").val(id);
});

$('#payBill').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var name = button.parent().parent().children(":nth-child(1)").text()
    var amt = button.parent().parent().children(":nth-child(3)").text()
    var modal = $(this)
    console.log(name);
    console.log(amt);
    modal.find('.modal-body input#pay-amt').val(amt)
    modal.find('.modal-title span#pay-name').html(name)
});



// })();
