$('#treatment_by').on('change', function() {
    let selected = this.value;
    if(selected === 'SELF') {
        $(".relative").hide();
        $("#relative_name").val("");
        $("#relative_cghs_number").val("");
        $("#relative_cghs_expiry").val("");
    } else {
        $(".relative").show();
    }
});


$('#treatment_by').trigger('change');