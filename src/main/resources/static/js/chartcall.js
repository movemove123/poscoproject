const user = $("#user").text()

let mainDashBoardData = {}
let maxSalesInMonthData = {}


/** 순서대로 topTen, month, mostPopulPro 값이 담긴 배열이 넘어온다 **/
function mainDashBoard() {

    console.log("불러와!!")
    // console.log(mainDashBoardData)

    $.ajax({
        url: "http://localhost:5000/maindashboard/" + user,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        async: false,
        success: function (data) {
            // console.log("data : "+data.month)
            // console.log("data : "+data.mostPopulPro)
            // console.log("data : "+data.topten)
            mainDashBoardData = data
        }
    })
    // console.log("dashboard : "+mainDashBoardData)


}


/** 지점의 최고 매출액 **/
function maxSalesInMonth() {

    console.log("불러와!!")

    $.ajax({
        url: "http://localhost:5000/monthMaxChart",
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        async: false,
        success: function (data) {
            // console.log("data : "+data.month)
            // console.log("data : "+data.mostPopulPro)
            // console.log("data : "+data.topten)
            // console.dir(data);
            maxSalesInMonthData = data
        }
    })


}

maxSalesInMonth()
mainDashBoard()