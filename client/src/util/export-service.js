export function downloadCsv(csvString, filename) {
    const blob = new Blob([csvString], {type: 'text/csv;charset=utf-8;'});
    const link = document.createElement('a');
    link.setAttribute('href', URL.createObjectURL(blob));
    link.setAttribute('download', filename ? filename : "data.csv");
    document.body.appendChild(link); // Required for FF
    link.click();
    document.body.removeChild(link); // Required for FF
}
