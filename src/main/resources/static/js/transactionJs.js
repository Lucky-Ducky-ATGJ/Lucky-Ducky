// (function () {
"use strict";

$('#deleteTransaction').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var curId = button.data('i') // Current bill id
    var modal = $(this)
    modal.find('.modal-footer button#delete-id').val(curId)
});

$('#editTransaction').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var name = button.parent().parent().children(":nth-child(1)").text() // Current bill name
    var amount = button.parent().parent().children(":nth-child(3)").text() // Current bill amount
    var income = button.parent().parent().children(":nth-child(2)").text() // is it income?
    var category = button.parent().parent().children(":nth-child(2)").text() // category
    var id = button.data("id")
    var modal = $(this)
    modal.find(".modal-body input#edit-name").val(name);
    modal.find(".modal-body input#edit-due-date").val(date);
    modal.find(".modal-body input#edit-amount").val(amount);
    modal.find(".modal-body input#edit-id").val(id);
});

// })();
