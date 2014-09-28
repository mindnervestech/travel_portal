$(function () {
    //BEGIN JQUERY JVECTORMAP
    $('.widget-weather').css('height','322px');
    $('#world-map').css('width',$('.col-lg-8').width());
    $('#world-map').css('height','342px');
    $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: 'transparent',
        series: {
            regions: [{
                values: gdpData,
                scale: ['#B8312F', '#fc6e51'],
                normalizeFunction: 'polynomial'
            }]
        },
        hoverOpacity: 0.7,
        hoverColor: false
    });
    $( window ).resize(function() {
        $('#world-map').css('width',$('.col-lg-8').width());
        $('#world-map').css('height','342px');
    });
    //END JQUERY JVECTORMAP
	//BEGIN SKYCON
    var icons = new Skycons({"color": "white"});

    icons.set("clear-day", Skycons.CLEAR_DAY);
    icons.set("clear-night", Skycons.CLEAR_NIGHT);
    icons.set("partly-cloudy-day", Skycons.PARTLY_CLOUDY_DAY);
    icons.set("partly-cloudy-night", Skycons.PARTLY_CLOUDY_NIGHT);
    icons.set("cloudy", Skycons.CLOUDY);
    icons.set("rain", Skycons.RAIN);
    icons.set("sleet", Skycons.SLEET);
    icons.set("snow", Skycons.SNOW);
    icons.set("wind", Skycons.WIND);
    icons.set("fog", Skycons.FOG);

    icons.play();
    //END SKYCON

    //BEGIN AREA CHART SPLINE
    var d6_1 = [["Jan", 67],["Feb", 91],["Mar", 36],["Apr", 150],["May", 28],["Jun", 123],["Jul", 38]];
    var d6_2 = [["Jan", 59],["Feb", 49],["Mar", 45],["Apr", 94],["May", 76],["Jun", 22],["Jul", 31]];
    $.plot("#area-chart-spline", [{
        data: d6_1,
        label: "Upload",
        color: "#ffce54"
    },{
        data: d6_2,
        label: "Download",
        color: "#01b6ad"
    }], {
        series: {
            lines: {
                show: !1
            },
            splines: {
                show: !0,
                tension: .4,
                lineWidth: 2,
                fill: .8
            },
            points: {
                show: !0,
                radius: 4
            }
        },
        grid: {
            borderColor: "#fafafa",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: true
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END AREA CHART SPLINE

    //BEGIN LINE CHART SPLINE
    var d2_1 = [["Jan", 181],["Feb", 184],["Mar", 189],["Apr", 180],["May", 208],["Jun", 183],["Jul", 185],["Aug", 188],["Sep", 202]];
    var d2_2 = [["Jan", 170],["Feb", 152],["Mar", 133],["Apr", 146],["May", 164],["Jun", 151],["Jul", 120],["Aug", 127],["Sep", 161]];
    var d2_3 = [["Jan", 102],["Feb", 91],["Mar", 106],["Apr", 89],["May", 90],["Jun", 94],["Jul", 86],["Aug", 105],["Sep", 88]];
    $.plot("#line-chart-spline", [{
        data: d2_1,
        label: "Chrome",
        color: "#ffce54"
    },{
        data: d2_2,
        label: "Firefox",
        color: "#3DB9D3"
    },{
        data: d2_3,
        label: "Safari",
        color: "#df4782"
    }], {
        series: {
            lines: {
                show: !1
            },
            splines: {
                show: !0,
                tension: 0.4,
                lineWidth: 2,
                fill: 0
            },
            points: {
                show: !0,
                radius: 4
            }
        },
        grid: {
            borderColor: "#ffffff",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: false
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END LINE CHART SPLINE

    /*//BEGIN BAR CHART STACK
    var d4_1 = [["Jan", 126],["Feb", 73],["Mar", 94],["Apr", 54],["May", 92],["Jun", 141],["Jul", 29],["Aug", 44],["Sep", 30],["Oct", 40],["Nov", 67],["Dec", 92]];
    var d4_2 = [["Jan", 58],["Feb", 61],["Mar", 46],["Apr", 35],["May", 55],["Jun", 46],["Jul", 57],["Aug", 80],["Sep", 100],["Oct", 91],["Nov", 35],["Dec", 57]];
    $.plot("#bar-chart-stack", [{
        data: d4_1,
        label: "New Visitor",
        color: "#3DB9D3"
    },{
        data: d4_2,
        label: "Returning Visitor",
        color: "#ffce54"
    }], {
        series: {
            stack: !0,
            bars: {
                align: "center",
                lineWidth: 0,
                show: !0,
                barWidth: .6,
                fill: .9
            }
        },
        grid: {
            borderColor: "#fafafa",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: false
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END BAR CHART STACK*/

    //BEGIN CHART EARNING
    var d2 = [["Jan", 200],["Feb", 80],["Mar", 199],["Apr", 147],["May", 293],["Jun", 192]];
    $.plot("#earning-chart", [{
        data: d2,
        color: "#ffce54"
    }], {
        series: {
            lines: {
                show: !0,
                fill: .01
            },
            points: {
                show: !0,
                radius: 4
            }
        },
        grid: {
            borderColor: "#fafafa",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: false
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END CHART EARNING

    //BEGIN CHART TRAFFIC SOURCES
    var d6_1 = [47];
    var d6_2 = [33];
    var d6_3 = [20];
    $.plot('#traffice-sources-chart', [{
        data: d6_1,
        label: "Search Engines",
        color: "#3DB9D3"
    },
    {
        data: d6_2,
        label: "Referrals",
        color: "#ffce54"
    },
    {
        data: d6_3,
        label: "Direct",
        color: "#fc6e51"
    }], {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true,
            clickable: true
        }
    });
    //END CHART TRAFFIC SOURCES

    //BEGIN CHART NEW CUSTOMER
    var d7 = [["Jan", 93],["Feb", 78],["Mar", 47],["Apr", 35],["May", 48],["Jun", 26]];
    $.plot("#new-customer-chart", [{
        data: d7,
        color: "#01b6ad"
    }], {
        series: {
            bars: {
                align: "center",
                lineWidth: 0,
                show: !0,
                barWidth: .6,
                fill: .9
            }
        },
        grid: {
            borderColor: "#fafafa",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: false
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END CHART NEW CUSTOMER

    //BEGIN CHART DOWNLOAD UPLOAD
    var d8_1 = [["Jan", 67],["Feb", 91],["Mar", 36],["Apr", 150],["May", 28],["Jun", 123],["Jul", 38]];
    var d8_2 = [["Jan", 59],["Feb", 49],["Mar", 45],["Apr", 94],["May", 76],["Jun", 22],["Jul", 31]];
    $.plot("#internet-speed-chart", [{
        data: d8_1,
        label: "Download",
        color: "#a01518"
    },{
        data: d8_2,
        label: "Upload",
        color: "#c1ca01"
    }], {
        series: {
            lines: {
                show: !1
            },
            splines: {
                show: !0,
                tension: .4,
                lineWidth: 2,
                fill: .8
            },
            points: {
                show: !0,
                radius: 4
            }
        },
        grid: {
            borderColor: "#fafafa",
            borderWidth: 1,
            hoverable: !0
        },
        tooltip: !0,
        tooltipOpts: {
            content: "%x : %y",
            defaultTheme: false
        },
        xaxis: {
            tickColor: "#fafafa",
            mode: "categories"
        },
        yaxis: {
            tickColor: "#fafafa"
        },
        shadowSize: 0
    });
    //END CHART DOWNLOAD UPLOAD
   
   $('#earning-number').animateNumber({
        number: 50645,
        numberStep: comma_separator_number_step
    }, 5000);
    $('#new-customer-number').animateNumber({
        number: 3420,
        numberStep: comma_separator_number_step
    }, 5000); 
   


});