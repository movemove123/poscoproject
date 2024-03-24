// Set new default font family and font color to mimic Bootstrap's default styling
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

/* 전체 매출 변수 */
let month = JSON.parse(mainDashBoardData.month)
let monthLabel = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
let monthVal = []

/* topTen 차트 변수 */
let topTen = JSON.parse(mainDashBoardData.topten);
let topTenLabel = []
let topTenVal = []
let max = 0
let min = -1

/* 가장 많이 팔린 상품 파이 차트 변수 */
let mostPro = JSON.parse(mainDashBoardData.mostPopulPro)
let mostProLabel = []
let mostProVal = []

for(let i=0; i<Object.keys(mostPro.name).length; i++){
    mostProLabel.push(mostPro.name[i])
    mostProVal.push(mostPro.count[i])
}

/* 메인 파이 차트 */
// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var myPieChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: mostProLabel,
        datasets: [{
            data: mostProVal,
            backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
            hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
            hoverBorderColor: "rgba(234, 236, 244, 1)",
        }],
    },
    options: {
        maintainAspectRatio: false,
        tooltips: {
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: '#dddfeb',
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
        },
        legend: {
            display: false
        },
        cutoutPercentage: 80,
    },
});


/* 월별 전체 매출 차트 */
for (let i = 0; i < Object.keys(month.sum).length; i++) {

    monthVal.push(month.sum[i])
}


// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: monthLabel,
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
            data: monthVal,
        }],
    },
    options: {
        maintainAspectRatio: false,
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
                ticks: {
                    maxTicksLimit: 7
                }
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

/* topTen 차트 */

// console.log("top : "+topTen)

for (let i = 0; i < Object.keys(topTen.name).length; i++) {
    max = Math.max(max, topTen.payment[i]);
    min = Math.min(min, topTen.payment[i])

    topTenLabel.push(topTen.name[i]);
    topTenVal.push(topTen.payment[i]);

}


// Bar Chart Example
var ctx = document.getElementById("myBarChart");

const labels = topTenLabel
const data = {
    labels: labels,
    datasets: [{
        type : 'bar',
        label: '매출액',
        data: topTenVal,
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 205, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(201, 203, 207, 0.2)'
        ],
        borderColor: [
            'rgb(255, 99, 132)',
            'rgb(255, 159, 64)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
            'rgb(54, 162, 235)',
            'rgb(153, 102, 255)',
            'rgb(201, 203, 207)'
        ],
        borderWidth: 1
    },{
        type : 'line',
        label: '매출 추이',
        data : topTenVal,
        fill: false,
        borderColor: 'rgb(54, 162, 235)' // 조금더 어두운 파랑 => 남색에 가까운(코키티와 컬러톤 매칭)
    }]
};

const annotation = {
    type: 'line',
    borderColor: 'black',
    borderDash: [6, 6],
    borderDashOffset: 0,
    borderWidth: 3,
    label: {
        display: true,
        content: 'Average: ' + average(topTenVal),
        position: 'start'
    },
    scaleID: 'y',
    value: average(topTenVal)
};

const config = {
    type: 'line',
    data,
    options: {
        plugins: {
            annotation: {
                annotations: {
                    annotation
                }
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    maxTicksLimit: 5,
                    padding: 10,
                    // Include a dollar sign in the ticks
                    callback: function(value, index, values) {
                        return number_format(value)+" 원";
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
                    return datasetLabel + ':'+number_format(tooltipItem.yLabel)+" 원";
                }
            }
        }
    }
};

// <block:utils:2>
function average(ctx) {
    return ctx.reduce((a, b) => a + b, 0) / ctx.length;
}
// </block:utils>

/* chart 넣기 */
$(function(){
    const myChart = new Chart(
        ctx, config, data
    )
})

/* 메인 지도 차트 */
google.load('visualization', '1', {'packages': ['geochart']});
google.setOnLoadCallback(drawVisualization);

function drawVisualization() {
    var data = new google.visualization.DataTable();

    data.addColumn('string', 'Country');
    data.addColumn('number', 'Value');
    data.addColumn({type: 'string', role: 'tooltip'});
    var ivalue = new Array();

    data.addRows([[{v: 'KR-11', f: '서울 특별시'}, 0, '7 점포 \n 57,030,000 원']]);

    data.addRows([[{v: 'KR-26', f: '부산 광역시'}, 1, '5 점포 \n 49,100,000 원']]);

    data.addRows([[{v: 'KR-27', f: '대구 광역시'}, 2, '3 점포 \n 10,000,000 원']]);

    data.addRows([[{v: 'KR-30', f: '대전 광역시'}, 3, '3 점포 \n 9,100,000 원']]);

    data.addRows([[{v: 'KR-29', f: '광주 광역시'}, 4, '2 점포 \n 7,120,000 원']]);

    data.addRows([[{v: 'KR-28', f: '인천 광역시'}, 5, '3 점포 \n 8,800,000 원']]);

    data.addRows([[{v: 'KR-31', f: '울산 광역시'}, 6, '2 점포 \n 6,700,000']]);

    data.addRows([[{v: 'KR-43', f: '충청북도'}, 7, '0 점포']]);

    data.addRows([[{v: 'KR-44', f: '충청남도'}, 8, '2 점포 \n 6,000,000']]);

    data.addRows([[{v: 'KR-42', f: '강원도'}, 9, '2 점포 \n 6,300,000']]);

    data.addRows([[{v: 'KR-41', f: '경기도'}, 10, '4 점포 \n 38,010,000']]);

    data.addRows([[{v: 'KR-47', f: '경상북도'}, 11, '2 점포 \n 4,900,000']]);

    data.addRows([[{v: 'KR-48', f: '경상남도'}, 12, '1 점포 \n 3.800,000']]);

    data.addRows([[{v: 'KR-49', f: '제주도'}, 13, '1 점포 \n 4.200,000']]);

    data.addRows([[{v: 'KR-45', f: '전라북도'}, 14, '2 점포 \n 4,720,000']]);

    data.addRows([[{v: 'KR-46', f: '전라남도'}, 15, '2 점포 \n 39,050,000']]);


    var options = {
        backgroundColor: {fill: '#FFFFFF', stroke: '#FFFFFF', strokeWidth: 0},
        colorAxis: {
            minValue: 0,
            maxValue: 21,
            colors: ['#3182BD', '#3182BD', '#3182BD', '#3182BD', '#3182BD', '#3182BD', '#3182BD', '#3182BD', '#3182BD', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#9ECAE1', '#DEEBF7', '#DEEBF7', '#DEEBF7', '#DEEBF7', '#DEEBF7',]
        },
        legend: 'none',
        backgroundColor: {fill: '#FFFFFF', stroke: '#FFFFFF', strokeWidth: 0},
        displayMode: 'regions',
        enableRegionInteractivity: 'true',
        resolution: 'provinces',
        sizeAxis: {minValue: 1, maxValue: 1, minSize: 10, maxSize: 10},
        region: 'KR', //country code
        keepAspectRatio: true,
        width: 470,
        height: 300,
        tooltip: {textStyle: {color: '#444444'}, trigger: 'focus'}
    };

    var chart = new google.visualization.GeoChart(document.getElementById('visualization'));
    google.visualization.events.addListener(chart, 'select', function () {
        var selection = chart.getSelection();
        if (selection.length == 1) {
            var selectedRow = selection[0].row;
            var selectedRegion = data.getValue(selectedRow, 0);
            if (ivalue[selectedRegion] != '') {
                document.getElementsByTagName('body')[0].style.background = ivalue[selectedRegion];
            }
        }
    });
    chart.draw(data, options);
}