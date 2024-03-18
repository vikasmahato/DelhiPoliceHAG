$('#treatment_by').on('change', function() {
    let selected = this.value;
    if(selected === 'SELF') {
        $(".relative").hide();
        $(".relative input").val("");
        $(".relative input").attr("required", false);
    } else {
        $(".relative").show();
        $(".relative input").attr("required", "required")
    }
});


$('#treatment_by').trigger('change');