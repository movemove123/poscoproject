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

//////* 매달 매출 합계 *///////////
let monthLabel = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
let monLabel = []
let sumValAllsales = []

let mon = JSON.parse(mainDashBoardData.month)

let maxData = 0
let minData = -1

//// 각 월별 전체 지점에서 최대 매출 ////
// let maxValInMonth = []
// let maxValInMonthData = maxSalesInMonth();

// console.dir(maxSalesInMonth())

for (let i = 0; i < Object.keys(mon.sum).length; i++) {
    maxData = Math.max(maxData, mon.sum[i])
    minData = Math.max(minData, mon.sum[i])

    monLabel.push(monthLabel[i]);
    sumValAllsales.push(mon.sum[i]);
    // maxValInMonth.push(maxValInMonthData.sales[i]);
}

// Bar Chart Example
var monChart = document.getElementById("monthChart");

window.chartColors = {
    red: 	'rgba(255, 99, 132, 0.5)',
    orange: 'rgba(255, 159, 64, 0.5)',
    yellow: 'rgba(255, 205, 86, 0.5)',
    green: 	'rgba(75, 192, 192, 0.5)',
    blue: 	'rgba(54, 162, 235, 0.5)',
    purple: 'rgba(153, 102, 255, 0.5)',
    grey: 	'rgba(231, 233, 237, 0.5)'
};

var barChartData = {
    labels: monLabel,
    datasets: [
        {
            label: '월별 매출 합계(지점 통합)',
            backgroundColor: [
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.purple,
                window.chartColors.red
            ],
            yAxisID: "y-axis-1",
            data: sumValAllsales
        },
        {
            label: '월별 매출 추이',
            yAxisID: "y-axis-2",
            data: sumValAllsales,
            type: 'line',
            fill: false
        }
    ]

};
window.onload = function() {
    var ctx = document.getElementById("monthChart").getContext("2d");
    window.myBar = new Chart(ctx, {
        type: 'bar',
        data: barChartData,
        options: {
            responsive: false,
            stacked: false,
            title:{
                display:true,
                text:"국내 월별 매출 통계"
            },
            tooltips: {
                mode: 'index',
                intersect: true,
                callbacks: {
                    label: function(tooltipItem, chart) {
                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                        return datasetLabel + ':'+number_format(tooltipItem.yLabel)+" 원";
                    }
                }
            },
            scales: {
                xAxes: [{
                }],
                yAxes: [{
                    type: "linear", // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                    display: true,
                    position: "left",
                    id: "y-axis-1",
                    ticks: {
                        beginAtZero: true,
                        suggestedMin: 0,
                        suggestedMax: maxData,
                        callback: function(value, index, values) {
                            return number_format(value)+" 원";
                        }
                    }
                }, {
                    type: "linear", // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                    display: false,
                    id: "y-axis-2",
                    position:"right",
                    ticks: {
                        beginAtZero: true,
                        suggestedMin: 0,
                        suggestedMax: maxData,
                        callback: function(value, index, values) {
                            return number_format(value)+" 원";
                        }
                    }
                }],
            }
        }
    });
};


// <block:utils:2>
function average(ctx) {
    return ctx.reduce((a, b) => a + b, 0) / sumValAllsales.length;
}

let $scatter = $("#scatterMonth")
let $boxPlot = $("#boxPlotMonth")

$.ajax({
    type: "GET",
    url: "http://localhost:5000/monthAnlyAllByScatter/"+user,
    async: false,
    success: function (result, textStatus, jqXHR) {

        // console.log("result : "+result)
        // $scatter.attr("src", "data:image/png;base64,"+result);
        $scatter.attr("src", result);

        $scatter.css({
            width:"100%",
            height:"50%"
        })
    }
});

$.ajax({
    type: "GET",
    url: "http://localhost:5000/monthAnlyAllByBoxplot/"+user,
    async: false,
    success: function (result, textStatus, jqXHR) {

        // console.log("result : "+result)
        // $boxPlot.attr("src", "data:image/png;base64,"+result);
        $boxPlot.attr("src", result);

        $boxPlot.css({
            width:"100%",
            height:"50%"
        })
    }
});