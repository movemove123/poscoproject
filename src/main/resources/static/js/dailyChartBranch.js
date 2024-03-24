Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
    // *     example: number_format(1234.56, 2, ',', ' ');
    // *     return: '1 234,56'
    number = (number + '').replace(',', '').replace(' ', '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

dailyChart()

// let daily = dailyData
let dailyLabel = []
let dailyVal = []


for (let i = 0; i < Object.keys(dailyData.sum).length; i++) {
    dailyLabel.push(dailyData.order_date[i]+"일")
    dailyVal.push(dailyData.sum[i])
}


// console.log(dailyLabel)


var ctx = document.getElementById("dailyChart");
var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: dailyLabel,
        datasets: [{
            label: "매출액",
            lineTension: 0.3,
            backgroundColor: "rgba(78, 115, 223, 0.05)",
            borderColor: "rgba(78, 115, 223, 1)",
            pointRadius: 3,
            pointBackgroundColor: "rgba(78, 115, 223, 1)",
            pointBorderColor: "rgba(78, 115, 223, 1)",
            pointHoverRadius: 3,
            pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
            pointHoverBorderColor: "rgba(78, 115, 223, 1)",
            pointHitRadius: 10,
            pointBorderWidth: 2,
            data: dailyVal,
        }],
    },
    options: {
        responsive: false,
        maintainAspectRatio: false,
        title:{
            display:true,
            text:"일별 매출 통계"
        },
        layout: {
            padding: {
                left: 10,
                right: 25,
                top: 25,
                bottom: 0
            }
        },
        scales: {
            xAxes: [{
                time: {
                    unit: 'date'
                },
                gridLines: {
                    display: false,
                    drawBorder: false
                },
                // ticks: {
                //     maxTicksLimit: 7
                // }
            }],
            yAxes: [{
                ticks: {
                    maxTicksLimit: 5,
                    padding: 10,
                    // Include a dollar sign in the ticks
                    callback: function(value, index, values) {
                        return number_format(value)+ ' 원';
                    }
                },
                gridLines: {
                    color: "rgb(234, 236, 244)",
                    zeroLineColor: "rgb(234, 236, 244)",
                    drawBorder: false,
                    borderDash: [2],
                    zeroLineBorderDash: [2]
                }
            }],
        },
        legend: {
            display: false
        },
        tooltips: {
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            titleMarginBottom: 10,
            titleFontColor: '#6e707e',
            titleFontSize: 14,
            borderColor: '#dddfeb',
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            intersect: false,
            mode: 'index',
            caretPadding: 10,
            callbacks: {
                label: function(tooltipItem, chart) {
                    var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                    return datasetLabel + ': '+ number_format(tooltipItem.yLabel)+' 원';
                }
            }
        }
    }
});

let $scatterDaily = $("#scatterDaily")
let $boxPlotDaily = $("#boxPlotDaily")

$.ajax({
    type: "GET",
    url: "http://localhost:5000/dailyAnlyByScatter/"+user,
    async: false,
    success: function (result, textStatus, jqXHR) {

        // console.log("result : "+result)
        // $scatter.attr("src", "data:image/png;base64,"+result);
        $scatterDaily.attr("src", result);

        $scatterDaily.css({
            width:"100%",
            height:"50%"
        })
    }
});

$.ajax({
    type: "GET",
    url: "http://localhost:5000/dailyAnlyByBoxplot/"+user,
    async: false,
    success: function (result, textStatus, jqXHR) {

        // console.log("result : "+result)
        // $boxPlot.attr("src", "data:image/png;base64,"+result);
        $boxPlotDaily.attr("src", result);

        $boxPlotDaily.css({
            width:"100%",
            height:"50%"
        })
    }
});