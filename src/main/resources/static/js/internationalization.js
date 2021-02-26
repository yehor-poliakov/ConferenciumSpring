$(document).ready(function() {
    $('input[name="lang"]').change(function () {
        var input = $('input[name="lang"]:checked');
        window.location.replace('?lang=' + input.val());
    });
});