$(document).on('focus','#pis_number',function(){
    $(this).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url : '/searchApplicant',
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                method: 'get',
                data: {
                    q: request.term,
                },
                success: function( data ) {
                    response( $.map( data, function( item ) {
                        return {
                            id: item.id,
                            text: item.name,
                            cghsNumber: item.cghsNumber,
                            cghsExpiry: item.cghsExpiry,
                            cghsCategory: item.cghsCategory,
                            belt_number: item.beltNumber,
                            rank: item.rank,
                            value: item.pisNumber,
                            gender: item.gender
                        }
                    }));
                }
            });
        },
        autoFocus: true,
        minLength: 0,
        select: function( event, ui ) {
            debugger;
            $("#applicant_id").val(ui.item.id).trigger('change');
            $("#gender").val(ui.item.gender).trigger('change');
            $("#belt_number").val(ui.item.belt_number).trigger('change');
            $("#applicant_name").val(ui.item.text).trigger('change');
            $("#cghs_number").val(ui.item.cghsNumber).trigger('change');
            $("#cghs_expiry").val(ui.item.cghsExpiry).trigger('change');
            $("#cghs_category").val(ui.item.cghsCategory).trigger('change');
            $("#rank").val(ui.item.rank).trigger('change');
            document.querySelectorAll('.form-outline').forEach((formOutline) => {
                new mdb.Input(formOutline).update();
            });
        },
        open: function() {
            $(this).autocomplete('widget').css('z-index', 1051000);
            return false;
        }
    });
});