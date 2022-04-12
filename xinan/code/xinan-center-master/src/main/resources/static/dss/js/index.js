// 中间最大饼图
const showCenterContent = function(id, data) {

    echarts.registerMap('MZ', meiZhouJson);
    // 1. 实例化对象
    let myChart = echarts.init(document.getElementById(id));
    // 2. 指定配置项和数据

    let mapData = [];

    for (let val of data) {
        let obj = {
            name: val.name,
            value: val.value,
            label: {
                normal: {
                    show: true,
                    position: '',
                    formatter: function(params) {
                        return val.name + "\n" + '医院数量：' + val.count + '\n成交额：' + val.value; //地图上展示文字 + 数值
                    },
                }
            }
        }
        mapData.push(obj);
    }

    let option = {
        toolbox: {
            show: false
        },
        visualMap: {
            show: false,
        },
        dataRange: {
            show: false,
            splitList: [
                { start: 0, end: 10000000000000, color: '#2240AC' }
            ]
        },
        series: [{
            name: '梅州中医院',
            type: 'map',
            mapType: 'MZ', // 自定义扩展图表类型
            zoom: 1.2,
            label: {
                show: true,
                normal: {
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    },
                    borderColor: '#fff'
                },
            },
            itemStyle: {
                normal: {
                    borderWidth: 1, //边际线大小
                    borderColor: '#81B1F5', //边界线颜色
                },
                emphasis: {
                    areaColor: '#4266EE' //鼠标滑过地图颜色
                }
            },
            data: mapData
        }]
    };

    // 3. 配置项和数据给我们的实例化对象
    myChart.setOption(option);

    // 4. 当我们浏览器缩放的时候，图表也等比例缩放
    window.addEventListener("resize", function() {
        // 让我们的图表调用 resize这个方法
        myChart.resize();
    });
    // });
};

// 左边第一幅图
const showLeftChart01 = function(id, data) {
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        color: ['#2240AC', '#6078EE'],
        legend: {
            data: ['中药饮片', '配方颗粒'],
            textStyle: {
                color: "#333"
            }
        },
        grid: {
            left: '2%',
            right: '2%',
            bottom: '1%',
            show: false,
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: data.hospitalNameArr,
            // 去除刻度
            axisTick: {
                show: false
            },
            // 修饰刻度标签的颜色
            axisLabel: {
                color: "#333",
                interval: 0,
                rotate: 15,
                align: 'center',
                verticalAlign: 'top'
            },
            // 去除x坐标轴的颜色
            axisLine: {
                show: false
            }
        }],
        yAxis: [{
            type: 'value',
            name: '万元',
            nameTextStyle: {
                color: "#333",
                align: 'left'
            },
            axisTick: {
                show: false
            },
            axisLine: { //y轴
                show: false
            },
            // 修饰刻度标签的颜色
            axisLabel: {
                color: "#333"
            },
            // 修改y轴分割线的颜色
            splitLine: {
                show: false
            }
        }],
        series: [{
                name: '中药饮片',
                barWidth: 30,
                type: 'bar',
                stack: 'one-column',
                data: data.hospitalDataArr[0]
            },
            {
                name: '配方颗粒',
                type: 'bar',
                stack: 'one-column',
                data: data.hospitalDataArr[1]
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    myChart.setOption(option);
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

// 左边第二幅图******************
const showLeftChart02 = function(id, data) {
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        color: ['#2240AC', '#6078EE', 'rgb(250,200,88)', 'rgb(238,102,102)'],
        legend: {
            data: ['中药饮片', '配方颗粒', '饮片环比', '颗粒环比'],
            textStyle: {
                color: "#333"
            }
        },
        grid: {
            left: '2%',
            right: '2%',
            bottom: '1%',
            show: false,
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: data.xAxisDataArr,
            axisPointer: {
                type: 'shadow'
            },
            axisTick: {
                show: false
            },
            // 修饰刻度标签的颜色
            axisLabel: {
                color: "#333",
                align: 'center',
                verticalAlign: 'top'
            },
            // 去除x坐标轴的颜色
            axisLine: {
                show: false
            }
        }],
        yAxis: [{
                type: 'value',
                name: '万元',
                nameTextStyle: {
                    color: "#333",
                    align: 'left'
                },
                axisTick: {
                    show: false
                },
                axisLine: { //y轴
                    show: false
                },
                // 修饰刻度标签的颜色
                axisLabel: {
                    color: "#333"
                },
                // 修改y轴分割线的颜色
                splitLine: {
                    show: false
                }
            },
            {
                type: 'value',
                name: '百分比',
                nameTextStyle: {
                    color: "#333",
                    align: 'right'
                },
                max: 100,
                axisTick: {
                    show: false
                },
                axisLine: { //y轴
                    show: false
                },
                // 修饰刻度标签的颜色
                axisLabel: {
                    color: "#333",
                    formatter: '{value} %'
                },
                // 修改y轴分割线的颜色
                splitLine: {
                    show: false
                }
            }
        ],
        series: [{
                name: '中药饮片',
                type: 'bar',
                data: data.indexDataArr[0]
            },
            {
                name: '配方颗粒',
                type: 'bar',
                data: data.indexDataArr[1]
            },
            {
                name: '饮片环比',
                type: 'line',
                yAxisIndex: 1,
                symbol: "circle",
                data: data.indexDataArr[2]
            },
            {
                name: '颗粒环比',
                type: 'line',
                yAxisIndex: 1,
                symbol: "circle",
                data: data.indexDataArr[3]
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    myChart.setOption(option);
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

// 左边第三幅图
const showLeftChart03 = function(id, data) {
    let myChart = echarts.init(document.getElementById(id));
    let drugNameArr = [];
    for (var val of data) {
        drugNameArr.push(val.name);
    }

    let option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        color: ['#2240AC', '#6078EE', '#81B1F5', '#4C66E6', '#5DCED3'],
        legend: {
            orient: 'vertical',
            left: 'left',
            textStyle: {
                color: "#333"
            },
            data: drugNameArr
        },
        series: [{
            name: '中药饮片',
            type: 'pie',
            radius: '85%',
            center: ['60%', '50%'],
            data: data,
            label: {
                formatter: ' {per|{d}%} ',
                borderColor: '#fff',
                borderWidth: 1,
                borderRadius: 4,
                rich: {
                    per: {
                        color: '#5DCED3',
                        lineHeight: 18
                    }
                }
            },
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };

    myChart.setOption(option);
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

// 右侧第一幅图
const showRightChart01 = function(id, data) {
    let myChart = echarts.init(document.getElementById(id));

    let option = {
        tooltip: {
            trigger: 'axis'
        },
        color: ['#4C66E6', '#81B1F5', '#5DCED3', '#2240AC'],
        legend: {
            data: ['网上交易率', '订单响应率', '医院到货率', '产品退货率'],
            textStyle: {
                color: "#333"
            }
        },
        grid: {
            left: '2%',
            right: '2%',
            bottom: '1%',
            show: false,
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.xAxisDataArr,

            axisTick: {
                show: false
            },
            // 修饰刻度标签的颜色
            axisLabel: {
                color: "#333",
                align: 'center',
                verticalAlign: 'top'
            },
            // 去除x坐标轴的颜色
            axisLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            name: '百分比',
            max: 100,
            nameTextStyle: {
                color: "#333",
                align: 'left'
            },
            axisTick: {
                show: false
            },
            axisLine: { //y轴
                show: false
            },
            // 修饰刻度标签的颜色
            axisLabel: {
                color: "#333",
                formatter: '{value} %'
            },
            // 修改y轴分割线的颜色
            splitLine: {
                show: false
            }

        },
        series: [{
                name: '网上交易率',
                type: 'line',
                smooth: true,
                symbol: "circle",
                symbolSize: 7,
                data: data.indexDataArr[0]
            },
            {
                name: '订单响应率',
                type: 'line',
                smooth: true,
                symbol: "circle",
                symbolSize: 7,
                data: data.indexDataArr[1]
            },
            {
                name: '医院到货率',
                type: 'line',
                smooth: true,
                symbol: "circle",
                symbolSize: 7,
                data: data.indexDataArr[2]
            },
            {
                name: '产品退货率',
                type: 'line',
                smooth: true,
                symbol: "circle",
                symbolSize: 7,
                data: data.indexDataArr[3]
            }
        ]
    };

    myChart.setOption(option);
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

// 右侧第二幅图
const showRightChart02 = function(id, data) {
    let myChart = echarts.init(document.getElementById(id));

    let showData = [
        { value: data[0], name: '本地产品销售额' },
        { value: data[1], name: '外地产品销售额' }
    ];

    let option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        color: ['#81B1F5', '#2240AC'],
        legend: {
            orient: 'vertical',
            left: 'left',
            textStyle: {
                color: "#333"
            },
            data: ['本地产品销售额', '外地产品销售额']
        },
        series: [{
            name: '中药饮片',
            type: 'pie',
            radius: '85%',
            center: ['55%', '50%'],
            data: showData,
            label: {
                formatter: ' {per|{d}%} ',
                borderColor: '#fff',
                borderWidth: 1,
                borderRadius: 4,
                rich: {
                    per: {
                        color: '#5DCED3',
                        lineHeight: 18
                    }
                }
            },
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };

    myChart.setOption(option);
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};



const showRightTable03 = function(id, data) {
    let tableHeader = `
        <tr>
            <th style="color: #6078EE;">指标</th>
            <th style="color: #81E7EC;">本月</th>
            <th style="color: #6078EE;">本年</th>
        </tr>`;

    let tableValue = '';
    for (let val of data) {
        tableValue = tableValue + `
            <tr>
                <td>${val.name}</td>
                <td>${val.month}</td>
                <td>${val.year}</td>
            </tr>`;
    }

    $('#' + id).html(tableHeader + tableValue);
};