function addRow() {
    var table = document.getElementById('editWares');
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cellName = row.insertCell(0);
    cellName.innerHTML ="<input type='text' id='wares" + rowCount + ".name' name='wares[" + (rowCount - 1) + "].name' th:disabled='${invoice.cancelled}\'>";

    var cellQuantity = row.insertCell(1);
    cellQuantity.innerHTML ="<input type='number' step='1' min='0' id='wares" + rowCount + ".quantity' name='wares[" + (rowCount - 1) + "].quantity' th:disabled='${invoice.cancelled}\'>";

    var cellPricePerOne = row.insertCell(2);
    cellPricePerOne.innerHTML ="<input type='number' step='1' min='0' id='wares" + rowCount + ".pricePerOne' name='wares[" + (rowCount - 1) + "].pricePerOne' th:disabled='${invoice.cancelled}\'>";

    var cellDiscount = row.insertCell(3);
    cellDiscount.innerHTML ="<input type='number' step='0.01' min='0' max='100' id='wares" + rowCount + ".discount' name='wares[" + (rowCount - 1) + "].discount' th:disabled='${invoice.cancelled}\'>";

    var cellTaxRate = row.insertCell(4);
    cellTaxRate.innerHTML ="<input type='number' step='0.01' min='0' max='100' id='wares" + rowCount + ".taxRate' name='wares[" + (rowCount - 1) + "].taxRate' th:disabled='${invoice.cancelled}\'>";

    var cellDelete = row.insertCell(5);
    var buttonDelete = document.createElement('button');
    buttonDelete.type = "button";
    buttonDelete.innerText = "Smazat";
    buttonDelete.onclick = (function(buttonDelete) {return function() {deleteRow(buttonDelete);}})(buttonDelete);
    cellDelete.appendChild(buttonDelete);
}

function deleteRow(button) {
    var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}