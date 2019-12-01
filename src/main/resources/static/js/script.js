$(document).ready(function() {
    var $ingredient3 = $('#id_ingredient3').closest('div').hide();
	var $ingredient4 = $('#id_ingredient4').closest('div').hide();
	var $ingredient5 = $('#id_ingredient5').closest('div').hide();
	var $ingredient6 = $('#id_ingredient6').closest('div').hide();
	var $ingredient7 = $('#id_ingredient7').closest('div').hide();
	var $ingredient8 = $('#id_ingredient8').closest('div').hide();

    $('#id_ingredient2').change(function() {
            $ingredient3.show();
    });
    $('#id_ingredient3').change(function() {
            $ingredient4.show();
    });
    $('#id_ingredient4').change(function() {
            $ingredient5.show();
    });
    $('#id_ingredient5').change(function() {
            $ingredient6.show();
    });
    $('#id_ingredient6').change(function() {
            $ingredient7.show();
    });
    $('#id_ingredient7').change(function() {
            $ingredient8.show();
    });
});

