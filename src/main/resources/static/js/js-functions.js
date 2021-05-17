function setTwoNumberDecimal() {
    this.valueOf = parseFloat(this.value).toFixed(2);
}
function valueCalculation() {
    this.quantity = document.getElementById("quantity").value;
    this.unitPrice = document.getElementById("unitPrice").value;
    let purchaseLineValue = (this.quantity*this.unitPrice).toFixed(2);
    let purchaseLineValueElements = document.getElementsByClassName("purchaseLineValue");
    for (let i = 0; i <purchaseLineValueElements.length;i++) {
        purchaseLineValueElements[i].value = purchaseLineValue;
    }
    // document.getElementById("purchaseLineValue").value=purchaseLineValue;
}
// function addPurchaseLine() {
//
//     document.getElementById("purchaseLineNumber").style.display="block";
// }
// function clearPurchaseLine() {
//     document.getElementById("purchaseLineNumber").style.display="none";
// }
