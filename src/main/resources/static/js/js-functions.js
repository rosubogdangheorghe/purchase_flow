function setTwoNumberDecimal() {
    this.valueOf = parseFloat(this.value).toFixed(2);
}

function valueCalculation() {
    this.quantity = document.getElementById("quantity").value;
    this.unitPrice = document.getElementById("unitPrice").value;
    let purchaseLineValue = (this.quantity * this.unitPrice).toFixed(2);
    let purchaseLineValueElements = document.getElementsByClassName("purchaseLineValue");
    for (let i = 0; i < purchaseLineValueElements.length; i++) {
        purchaseLineValueElements[i].value = purchaseLineValue;
    }
}

function receptionValueComputation() {

    this.receivedQuantity = document.getElementById("receivedQuantity").value;
    this.receivedPrice = document.getElementById("receivedPrice").value;
    let receivedPurchaseLineValue = (this.receivedQuantity * this.receivedPrice).toFixed(2);
    let purchaseLineValueElements = document.getElementsByClassName("receivedValue");
    for (let i = 0; i < purchaseLineValueElements.length; i++) {
        purchaseLineValueElements[i].value = receivedPurchaseLineValue;
    }
}

function sortTableBySupplier() {
    let table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("sortTable");
    switching = true;
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[3];
            y = rows[i + 1].getElementsByTagName("td")[3];
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }

}

function sortTableByRequest() {
    let table, rows;

}

$(document).ready(function () {
    $('#footer').ready(function () {
        let index = Math.floor(Math.random() * 100);
        $.ajax(
            "https://type.fit/api/quotes",
            {
                type: 'GET',
                dataType: 'json',
                success: function (result, status, xhr) {
                    $('#quote').replaceWith('<span id = quote>' + result[index].text + '</span>');
                    $('#author').replaceWith('<span id = author>' + result[index].author + '</span>');

                },
                error: function (jqxhr, textStatus, errorMessage) {
                    $('#quote').append('Error' + errorMessage);
                }
            }
        );
    });

});




