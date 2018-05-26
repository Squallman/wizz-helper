function submitData() {
    let oTable = document.getElementById('inputTable');
    let rowLength = oTable.rows.length;
    let data = [];
    for (let i = 1; i < rowLength; i++) {
        let oCells = oTable.rows.item(i).cells;
        let from = oCells.item(0).getElementsByTagName('select')[0].options[oCells.item(0).getElementsByTagName('select')[0].selectedIndex].value;
        let to =  oCells.item(1).getElementsByTagName('select')[0].options[oCells.item(1).getElementsByTagName('select')[0].selectedIndex].value;
        let day =  parseInt(oCells.item(2).getElementsByTagName('select')[0].options[oCells.item(2).getElementsByTagName('select')[0].selectedIndex].value);
        data.push({
            from : from,
            to : to,
            day : day
        });
    }

    let loader = document.getElementsByClassName('loader');
    loader[0].style.display = 'block';
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', '/search', true);
    xmlhttp.onload = function (e) {
        if (xmlhttp.readyState === 4) {
            if (xmlhttp.status === 200) {
                loader[0].style.display = 'none';
                doWork(xmlhttp.response);
            } else {
                loader[0].style.display = 'none';
                alert(xmlhttp.status + ': ' + xmlhttp.statusText);
            }
        }

    };

    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify(data));
}

function doWork(response) {
    let result = JSON.parse(response);
    let table = document.getElementById("myTable");
    for (let option of result) {
        let price = option.price;
        let numberOfFlights = option.flights.length;
        buildFirstRow(table, option.flights[0], price, numberOfFlights);
        for (let j = 1; j < numberOfFlights; j++) {
            buildOtherRow(table, option.flights[j]);
        }
    }
}

function buildOtherRow(table, data) {
    let additionalRow = table.insertRow();
    let directionCell = additionalRow.insertCell();
    directionCell.innerHTML = convertName(data.departureStation) + " - " + convertName(data.arrivalStation);
    let dateCell = additionalRow.insertCell();
    dateCell.innerHTML = getCorrectTime(data.departureDates[0], true);
}

function convertName(code) {
    switch (code) {
        case 'LWO': return 'Lviv';
        case 'GDN': return 'Gdansk';
        case 'VIE': return 'Viene';
        case 'BUD': return 'Budapesht';
        case 'IEV': return 'Kyiv';
        default: return 'Cannot parse'
    }
}

function buildFirstRow(table, data, price, numberOfFlights) {
    let priceRow = table.insertRow();
    let startDateCell = priceRow.insertCell();
    startDateCell.setAttribute('rowspan', numberOfFlights);
    startDateCell.innerHTML = getCorrectTime(data.departureDate, false);
    let priceCell = priceRow.insertCell();
    priceCell.setAttribute('rowspan', numberOfFlights);
    priceCell.innerHTML = price + " UAN";
    let directionCell = priceRow.insertCell();
    directionCell.innerHTML = convertName(data.departureStation) + " - " + convertName(data.arrivalStation);
    let dateCell = priceRow.insertCell();
    dateCell.innerHTML = getCorrectTime(data.departureDates[0], true);
}

function getCorrectTime(timestamp, withTime) {
    let date = new Date(timestamp);
    let day = '0' + date.getDate();
    let month = '0' + (date.getMonth() + 1);
    let year = date.getFullYear();
    if (withTime) {
        let hours = date.getUTCHours();
        let minutes = "0" + date.getMinutes();
        let seconds = "0" + date.getSeconds();
        return day.substr(-2) + '.' + month.substr(-2) + '.' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    } else {
        return day.substr(-2) + '.' + month.substr(-2) + '.' + year;
    }

}

function inputTableInsertRow() {
    let table = document.getElementById("inputTable");
    let length = table.rows.length;
    table.innerHTML += `<tr id='input_row_${length}'>\r
            <td>\r
                <select>\r
                    <option value="LWO">Lviv</option>\r
                    <option value="GDN">Gdansk</option>\r
                    <option value="VIE">Viene</option>\r
                    <option value="BUD">Budapesht</option>\r
                    <option value="IEV">Kiev</option>\r
                </select>\r
            </td>\r
            <td>\r
                <select>\r
                    <option value="LWO">Lviv</option>\r
                    <option value="GDN">Gdansk</option>\r
                    <option value="VIE">Viene</option>\r
                    <option value="BUD">Budapesht</option>\r
                    <option value="IEV">Kiev</option>\r
                </select>\r
            </td>\r
            <td>\r
                <select>\r
                    <option value="1">1st day</option>\r
                    <option value="2">2nd day</option>\r
                    <option value="3">3rd day</option>\r
                    <option value="4">4th day</option>\r
                    <option value="5">5th day</option>\r
                    <option value="6">6th day</option>\r
                    <option value="7">7th day</option>\r
                    <option value="8">8th day</option>\r
                    <option value="9">9th day</option>\r
                </select>\r
            </td>\r
            <td>\r
                <button onclick='inputTableInsertRow()'>+</button>\r
                <button onclick='inputTableRemoveRow(this)'>-</button>\r
            </td>\r
        </tr>`;
}

function inputTableRemoveRow(r) {
    let i = r.parentNode.parentNode.rowIndex;
    document.getElementById("inputTable").deleteRow(i);
}