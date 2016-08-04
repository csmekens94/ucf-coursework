/**
 *  Robert Arango
 *  09/08/2015
 */

// Variables and Arrays
letterFrequenciesLargest = 0;
var letterFrequencies = {
  A: 0, B: 0, C: 0, D: 0, E: 0, F: 0, G: 0, H: 0, I: 0, J: 0, K: 0, L: 0, M: 0,
  N: 0, O: 0, P: 0, Q: 0, R: 0, S: 0, T: 0, U: 0, V: 0, W: 0, X: 0, Y: 0, Z: 0
};


// Function that resets all values for the letter counts
function letterFrequenciesReset() {
  letterFrequencies.A = 0;
  letterFrequencies.B = 0;
  letterFrequencies.C = 0;
  letterFrequencies.D = 0;
  letterFrequencies.E = 0;
  letterFrequencies.F = 0;
  letterFrequencies.G = 0;
  letterFrequencies.H = 0;
  letterFrequencies.I = 0;
  letterFrequencies.J = 0;
  letterFrequencies.K = 0;
  letterFrequencies.L = 0;
  letterFrequencies.M = 0;
  letterFrequencies.N = 0;
  letterFrequencies.O = 0;
  letterFrequencies.P = 0;
  letterFrequencies.Q = 0;
  letterFrequencies.R = 0;
  letterFrequencies.S = 0;
  letterFrequencies.T = 0;
  letterFrequencies.U = 0;
  letterFrequencies.V = 0;
  letterFrequencies.W = 0;
  letterFrequencies.X = 0;
  letterFrequencies.Y = 0;
  letterFrequencies.Z = 0;
  letterFrequenciesLargest = 0;
}


// Function goes though encrypted message and counts how many times each letter appears
function letterFrequenciesCount() {
  str = document.getElementById('encryptedMessage').value.toUpperCase();
  
  for (var i = 0, len = str.length; i < len; i++) {
    if (str[i] == "A") letterFrequencies.A++;
    if (str[i] == "B") letterFrequencies.B++;
    if (str[i] == "C") letterFrequencies.C++;
    if (str[i] == "D") letterFrequencies.D++;
    if (str[i] == "E") letterFrequencies.E++;
    if (str[i] == "F") letterFrequencies.F++;
    if (str[i] == "G") letterFrequencies.G++;
    if (str[i] == "H") letterFrequencies.H++;
    if (str[i] == "I") letterFrequencies.I++;
    if (str[i] == "J") letterFrequencies.J++;
    if (str[i] == "K") letterFrequencies.K++;
    if (str[i] == "L") letterFrequencies.L++;
    if (str[i] == "M") letterFrequencies.M++;
    if (str[i] == "N") letterFrequencies.N++;
    if (str[i] == "O") letterFrequencies.O++;
    if (str[i] == "P") letterFrequencies.P++;
    if (str[i] == "Q") letterFrequencies.Q++;
    if (str[i] == "R") letterFrequencies.R++;
    if (str[i] == "S") letterFrequencies.S++;
    if (str[i] == "T") letterFrequencies.T++;
    if (str[i] == "U") letterFrequencies.U++;
    if (str[i] == "V") letterFrequencies.V++;
    if (str[i] == "W") letterFrequencies.W++;
    if (str[i] == "X") letterFrequencies.X++;
    if (str[i] == "Y") letterFrequencies.Y++;
    if (str[i] == "Z") letterFrequencies.Z++;
  }
  
  // Determines the letter with the highest count that helps generate the progress bars
  $.each(letterFrequencies, function( index, value ) {
    if (value > letterFrequenciesLargest) {
      letterFrequenciesLargest = value;
    }
  });
  
  // Populates the progress bars
  letterFrequenciesProgressBars("#letFreqA",letterFrequencies.A)
  letterFrequenciesProgressBars("#letFreqB",letterFrequencies.B);
  letterFrequenciesProgressBars("#letFreqC",letterFrequencies.C);
  letterFrequenciesProgressBars("#letFreqD",letterFrequencies.D);
  letterFrequenciesProgressBars("#letFreqE",letterFrequencies.E);
  letterFrequenciesProgressBars("#letFreqF",letterFrequencies.F);
  letterFrequenciesProgressBars("#letFreqG",letterFrequencies.G);
  letterFrequenciesProgressBars("#letFreqH",letterFrequencies.H);
  letterFrequenciesProgressBars("#letFreqI",letterFrequencies.I);
  letterFrequenciesProgressBars("#letFreqJ",letterFrequencies.J);
  letterFrequenciesProgressBars("#letFreqK",letterFrequencies.K);
  letterFrequenciesProgressBars("#letFreqL",letterFrequencies.L);
  letterFrequenciesProgressBars("#letFreqM",letterFrequencies.M);
  letterFrequenciesProgressBars("#letFreqN",letterFrequencies.N);
  letterFrequenciesProgressBars("#letFreqO",letterFrequencies.O);
  letterFrequenciesProgressBars("#letFreqP",letterFrequencies.P);
  letterFrequenciesProgressBars("#letFreqQ",letterFrequencies.Q);
  letterFrequenciesProgressBars("#letFreqR",letterFrequencies.R);
  letterFrequenciesProgressBars("#letFreqS",letterFrequencies.S);
  letterFrequenciesProgressBars("#letFreqT",letterFrequencies.T);
  letterFrequenciesProgressBars("#letFreqU",letterFrequencies.U);
  letterFrequenciesProgressBars("#letFreqV",letterFrequencies.V);
  letterFrequenciesProgressBars("#letFreqW",letterFrequencies.W);
  letterFrequenciesProgressBars("#letFreqX",letterFrequencies.X);
  letterFrequenciesProgressBars("#letFreqY",letterFrequencies.Y);
  letterFrequenciesProgressBars("#letFreqZ",letterFrequencies.Z);
}


// Function that updates the Progress Bars
function letterFrequenciesProgressBars(id,obj) {
  if (obj == 0) {
    $(id).html("").width("0%");
  }
  else {
    $(id).html(obj).width(obj/letterFrequenciesLargest*100+"%");
  }
}


// Function loops through the encrypted and determines possible recurrences of
// letters in 2, 3, 4, 5 letter sequences.
function letterOccurrences() {
  enMsg = document.getElementById('encryptedMessage').value.toUpperCase().replace(/\s/g, "X");
  twoAll = [], twoSorted = [], threeAll = [], threeSorted = [], fourAll = [], fourSorted = [], fiveAll = [], fiveSorted = [];
  
  for (var i = 0, len = enMsg.length; i < len; i++) {
    if ((i + 2) <= enMsg.length) { twoAll.push(enMsg[i].concat(enMsg[i+1])); }
    if ((i + 3) <= enMsg.length) { threeAll.push(enMsg[i].concat(enMsg[i+1],enMsg[i+2])); }
    if ((i + 4) <= enMsg.length) { fourAll.push(enMsg[i].concat(enMsg[i+1],enMsg[i+2],enMsg[i+3])); }
    if ((i + 5) <= enMsg.length) { fiveAll.push(enMsg[i].concat(enMsg[i+1],enMsg[i+2],enMsg[i+3],enMsg[i+4])); }
  }
  
  $.each(twoAll, function(index, letters) {
    if($.inArray(letters, twoSorted) === -1) { twoSorted.push(letters); }
  });
  $.each(threeAll, function(index, letters) {
    if($.inArray(letters, threeSorted) === -1) { threeSorted.push(letters); }
  });
  $.each(fourAll, function(index, letters) {
    if($.inArray(letters, fourSorted) === -1) { fourSorted.push(letters); }
  });
  $.each(fiveAll, function(index, letters) {
    if($.inArray(letters, fiveSorted) === -1) { fiveSorted.push(letters); }
  });
  
  var letterOccurrencesTwoLetter = letterOccurrencesCreateSortedArrays(twoSorted);
  var letterOccurrencesThreeLetter = letterOccurrencesCreateSortedArrays(threeSorted);
  var letterOccurrencesFourLetter = letterOccurrencesCreateSortedArrays(fourSorted);
  var letterOccurrencesFiveLetter = letterOccurrencesCreateSortedArrays(fiveSorted);
  
  letterOccurrencesGenerateTable(letterOccurrencesTwoLetter, "#twoLetters", '2 Letters', 3);
  letterOccurrencesGenerateTable(letterOccurrencesThreeLetter, "#threeLetters", '3 Letters', 2);
  letterOccurrencesGenerateTable(letterOccurrencesFourLetter, "#fourLetters", '4 Letters', 1);
  letterOccurrencesGenerateTable(letterOccurrencesFiveLetter, "#fiveLetters", '5 Letters', 1);
}


// Creates arrays that have removed any duplicates and stores the counts of those occurences as well
function letterOccurrencesCreateSortedArrays(array) {
  var tempArray = [];
  for (var i = 0, len = array.length; i < len; i++) {
    regext = new RegExp(array[i], 'g');
    num = enMsg.match(regext);
    if (num.length > 1) { tempArray.push(array[i].concat(' (<span style="color: red;">',num.length,'</span>)')); }
  }
  return tempArray;
}


// Generate the tables that hold the values that have occured multiple times
function letterOccurrencesGenerateTable(array, id, title, columns) {
  var tableHeader    = '<table class="table table-condensed table-bordered" style="background: #FFF; text-align: center;">';
  var tableRowStart  = '<tr>';
  var tableColStart  = '<td>';
  var tableColEnd    = '</td>';
  var tableRowEnd    = '</tr>';
  var tableFooter    = '</table>';
  var generatedTable = "";
  
  generatedTable = generatedTable.concat(tableHeader);
  generatedTable = generatedTable.concat(tableRowStart,'<td colspan="',columns,'">','<b>',title,'</b>',tableColEnd,tableRowEnd);
  
  for (var i = 0, len = array.length; i < len; i++) {
    if (i == 0) {
      generatedTable = generatedTable.concat(tableRowStart,tableColStart,array[i],tableColEnd);
    }
    else if ((i % columns) == 0) {
      generatedTable = generatedTable.concat(tableRowEnd,tableRowStart,tableColStart,array[i],tableColEnd);
    }
    else {
      generatedTable = generatedTable.concat(tableColStart,array[i],tableColEnd);
    }
  }
  
  arrayLength = array.length;
  
  while ((arrayLength % columns) != 0) {
    generatedTable = generatedTable.concat(tableColStart,tableColEnd);
    arrayLength++;
  }
  
  generatedTable = generatedTable.concat(tableRowEnd,tableFooter);
  $(id).html(generatedTable);
}


// The main decryption function, really doesn't decrypt, just checks for new values
// as it reprints the encrypted message with values from the letter inputs.
function decryptMessage() {
  // Puts the values of encrypted message into a variable
  var enMsg = document.getElementById('encryptedMessage').value.toUpperCase().replace(/\s/g, "X");
  var temp = "";
  // Steps through the variable and replaces the corresponding values with the ones from the 
  // letter fields and then colors those letters red in the decrypted message box
  for (var i = 0, len = enMsg.length; i < len; i++) {
    var letter = checkInputs(enMsg[i]);
    if (letter !== '') {
      temp = temp.concat('<span style="color:red;">',letter,"</span> ");
    }
    else {
      temp = temp.concat(enMsg[i], " ");
    }
  }
  // Print out values to the decrypted message
  $("#decryptedMessage").html(temp);
}


// Checks input and adds classes for a bit of flair, then returns a value
function checkInputs(letter) {
  // Removes all Success and Error classes from the Letter input fields, basically a fresh start
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputB").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  $("#decryptInputA").removeClass('has-error').removeClass('has-success');
  
  // When function is called it is passed a letter from the encrypted message and then
  // checks if that letter has a replacement value from the letter boxes, then returns either 
  // a blank or the value and also updates the classes to add color to the fields
  if (letter === 'A') {
    var letterValue = document.getElementById('decryptA').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputA").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputA").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'B') {
    var letterValue = document.getElementById('decryptB').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputB").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputB").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'C') {
    var letterValue = document.getElementById('decryptC').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputC").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputC").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'D') {
    var letterValue = document.getElementById('decryptD').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputD").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputD").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'E') {
    var letterValue = document.getElementById('decryptE').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputE").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputE").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'F') {
    var letterValue = document.getElementById('decryptF').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputF").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputF").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'G') {
    var letterValue = document.getElementById('decryptG').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputG").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputG").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'H') {
    var letterValue = document.getElementById('decryptH').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputH").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputH").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'I') {
    var letterValue = document.getElementById('decryptI').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputI").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputI").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'J') {
    var letterValue = document.getElementById('decryptJ').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputJ").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputJ").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'K') {
    var letterValue = document.getElementById('decryptK').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputK").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputK").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'L') {
    var letterValue = document.getElementById('decryptL').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputL").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputL").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'M') {
    var letterValue = document.getElementById('decryptM').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputM").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputM").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'N') {
    var letterValue = document.getElementById('decryptN').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputN").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputN").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'O') {
    var letterValue = document.getElementById('decryptO').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputO").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputO").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'P') {
    var letterValue = document.getElementById('decryptP').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputP").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputP").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'Q') {
    var letterValue = document.getElementById('decryptQ').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputQ").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputQ").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'R') {
    var letterValue = document.getElementById('decryptR').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputR").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputR").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'S') {
    var letterValue = document.getElementById('decryptS').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputS").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputS").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'T') {
    var letterValue = document.getElementById('decryptT').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputT").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputT").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'U') {
    var letterValue = document.getElementById('decryptU').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputU").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputU").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'V') {
    var letterValue = document.getElementById('decryptV').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputV").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputV").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'W') {
    var letterValue = document.getElementById('decryptW').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputW").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputW").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'X') {
    var letterValue = document.getElementById('decryptX').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputX").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputX").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'Y') {
    var letterValue = document.getElementById('decryptY').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputY").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputY").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else if (letter === 'Z') {
    var letterValue = document.getElementById('decryptZ').value.toUpperCase();
    if (letterValue !== '') { $("#decryptInputZ").addClass('has-success').removeClass('has-error'); }
    else { $("#decryptInputZ").addClass('has-error').removeClass('has-success'); }
    return letterValue;
  }
  else { return ''; }
}


// Main function which runs when new input is added to the text box
function runProg() {
  $("#hide-extras").show("slow");
  letterFrequenciesCount();
  letterFrequenciesReset();
  letterOccurrences();
  decryptMessage();
}
