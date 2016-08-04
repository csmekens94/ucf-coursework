function addTime()
{
    document.getElementById("datetime").innerHTML = new Date().toString();
}

function styleTable()
{
    // Table Elements Set to Variables
    var t       = document.getElementsByClassName("accomodations-table");
    var tHeader = document.getElementsByClassName("accomodations-table-row-header");
    var tRow    = document.getElementsByClassName("accomodations-table-row");
    var tRowAlt = document.getElementsByClassName("accomodations-table-row-alt");
    
    // Adjust Font Size and Center The Table
    t[0].style.margin = "0 auto";
    t[0].style.fontSize = "12px";
    
    //Adjust Colors for Table Header
    tHeader[0].style.background = "#000";
    tHeader[0].style.color = "#FFF";
    
    // Adjust Colors for Normal Rows
    tRow[0].style.background = "#CCC";
    tRow[0].style.color = "#00F";
    
    // Adjust Colors for Alternate Rows
    tRowAlt[0].style.background = "#00F";
    tRowAlt[0].style.color = "#CCC";
    
    // Adjust Colors for Normal Rows
    tRow[1].style.background = "#CCC";
    tRow[1].style.color = "#00F";
    
    // Adjust Colors for Alternate Rows
    tRowAlt[1].style.background = "#00F";
    tRowAlt[1].style.color = "#CCC";
    
    // Adjust Colors for Normal Rows
    tRow[2].style.background = "#CCC";
    tRow[2].style.color = "#00F";
}
