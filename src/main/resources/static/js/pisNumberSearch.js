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
                            value: item.pisNumber
                        }
                    }));
                }
            });
        },
        autoFocus: true,
        minLength: 0,
        select: function( event, ui ) {
            $("#applicant_id").val(ui.item.id);
            $("#belt_number").val(ui.item.belt_number);
            $("#applicant_name").val(ui.item.text)
            $("#cghs_number").val(ui.item.cghsNumber)
            $("#cghs_expiry").val(ui.item.cghsExpiry)
            $("#cghs_category").val(ui.item.cghsCategory).change();
            $("#rank").val(ui.item.rank).change();
        }
    });
});