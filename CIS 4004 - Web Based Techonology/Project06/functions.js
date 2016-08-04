function addTime()
{
    document.getElementById("datetime").innerHTML = new Date().toString();
}

function highlightRow()
{
    this.style.background = "#FFFF00";
    this.style.color      = "#000";
}

function restoreRow()
{
    this.style.background = "#CCC";
    this.style.color      = "#00F";
}

function restoreRowAlt()
{
    this.style.background = "#00F";
    this.style.color      = "#CCC";
}

function styleTable()
{
    if (document.getElementsByClassName("kayak-table")[0])
        document.getElementsByClassName("kayak-table")[0].style.margin = "0 auto";
    
    var stripedTable = document.getElementsByClassName("stripe_table");
    
    for (i = 0; i < stripedTable.length; i++)
    {    
        stripedTable[i].style.margin   = "0 auto";
        stripedTable[i].style.fontSize = "14px";
        
        stripedTableRows = stripedTable[i].getElementsByTagName("tr");
        
        for (j = 0; j < stripedTableRows.length; j++)
        { 
            if (j == 0)
            {
                stripedTableRows[j].style.background = "#000";
                stripedTableRows[j].style.color = "#FFF";
            }
            
            else if ((j % 2) == 0)
            {    
                stripedTableRows[j].style.background = "#00F";
                stripedTableRows[j].style.color = "#CCC";
                stripedTableRows[j].addEventListener("mouseover", highlightRow);
                stripedTableRows[j].addEventListener("mouseout", restoreRowAlt);
            }
            
            else
            {
                stripedTableRows[j].style.background = "#CCC";
                stripedTableRows[j].style.color = "#00F";
                stripedTableRows[j].addEventListener("mouseover", highlightRow);
                stripedTableRows[j].addEventListener("mouseout", restoreRow);
            }
        }
    }
}
