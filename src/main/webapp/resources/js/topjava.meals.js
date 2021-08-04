const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
};

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}

function filterTable() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(function (data) {
        updateDataTable(data)
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );

    $("#startDate, #endDate").datetimepicker({
        timepicker: false,
        format: 'd.m.Y'
    });

    $("#startTime, #endTime").datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});


