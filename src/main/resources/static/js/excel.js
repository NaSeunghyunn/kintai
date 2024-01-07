$(function() {
    $('.fa-sort').click(function() {
        if($(this).hasClass('fa-sort-up')) {
            $(this).removeClass('fa-sort-up').addClass('fa-sort-down');
        } else {
            $(this).removeClass('fa-sort-down').addClass('fa-sort-up');
        }
        $('.fa-sort').not(this).removeClass('fa-sort-down fa-sort-up');
    });

    searchMemberName();
});

function submitForm() {
    document.forms[0].submit();
}

function searchMemberName() {
    const name = $("#memberName").val();
    $('tbody tr').filter(function () {
        $(this).toggle($(this).find('.member-name').text().indexOf(name) > -1);
    });
}