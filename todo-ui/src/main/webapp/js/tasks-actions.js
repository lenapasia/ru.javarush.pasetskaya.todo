function getBaseUrl(){
    let current_path = window.location.href;
    let end_position = current_path.indexOf('?');
    return current_path.substring(0, end_position);
}

function add_task() {
    let value_description = $("#description_new").val();
    let value_status = $("#status_new").val();

    $.ajax({
        url: getBaseUrl(),
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({"description": value_description, "status": value_status}),
        dataType: 'json',
        async: false,
        statusCode: {
            200: function() {
                document.location.reload();
            }
        }
    });
}

function delete_task(task_id) {
    let url = getBaseUrl() + task_id;
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(){
            window.location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Unable to delete task: ' + textStatus + ' ' + errorThrown);
        }
    });
}

function edit_task(task_id) {
    // remove delete button
    let identifier_delete = "#delete_" + task_id;
    $(identifier_delete).remove();

    // replace "edit" button with "save"
    let identifier_edit = "#edit_" + task_id;
    let current_td_element = $(identifier_edit).parent()
    $(identifier_edit).remove();
    let save_tag = "<button  id='save_" + task_id + "' class=\"btn btn-success btn-xs\" type=\"button\">Save</button>";
    $(current_td_element).html(save_tag);
    let property_save_tag = "update_task(" + task_id + ")";
    let new_edit_element = current_td_element.children()[0];
    $(new_edit_element).attr("onclick", property_save_tag);

    // use input element for description
    let current_tr_element = $(current_td_element).parent();
    let children = current_tr_element.children();
    let td_description = children[1];
    td_description.innerHTML = "<input id='input_description_" + task_id
        + "' class='form-control' type='text' placeholder='description' aria-describedby='basic-addon1' value='"
        + td_description.innerHTML + "'>";

    // use select element for status
    let td_status = children[2];
    let status_id = "#input_status_" + task_id;
    let status_current_value = td_status.innerHTML;
    td_status.innerHTML = getDropdownStatusHtml(task_id);
    $(status_id).val(status_current_value).change();
}

function getDropdownStatusHtml(task_id) {
    let status_id = "input_status_" + task_id;
    return "<label for='status' style='display: none'></label>"
        + "<select id=" + status_id + " class='form-select' name='status'>"
        + "<option selected value='IN_PROGRESS'>IN_PROGRESS</option>"
        + "<option value='DONE'>DONE</option>"
        + "<option value='PAUSED'>PAUSED</option>"
        + "</select>";
}

function update_task(task_id) {
    let url = getBaseUrl() + task_id;
    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#input_status_" + task_id).val();

    const requestBody = {
        description: value_description,
        status: value_status
    };

    $.ajax({
        url: url,
        type: 'PUT',
        data: JSON.stringify(requestBody),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: false,
        statusCode: {
            200: function() {
                document.location.reload();
            }
        }
    });
}