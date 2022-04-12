
const showTopChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	let arrData = [];
	for(let dd of data){
		arrData.push({"name":dd.name, "value":(dd.value/10000).toFixed(2)});
	}

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'item',
            //formatter: '{b}\n销售额 : {c}'
        },
        legend: {
            //top: '5%',
            //left: '5%',
            show:false
        },
        color:['#2040AC','#5E78EE','#7FB1F5', '#5BCED6'],
        series: [
            {
                name: '销售额（万元）',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        //fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: true
                },
                itemStyle:{
                    normal:{
                        label:{
                            color: 'black',
                                show:true,
                                position: 'outside',
                                //formatter: '{c}({d}%)'
                                formatter: '{b}\n{d}%',
                        }
                    }
                },
                data: arrData
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};


const showTopChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var buySumArr = [];
    for (let val of data.buySumArr) {
    	buySumArr.push((val / 10000).toFixed(2));
    }

    var saleSumArr = [];
    for (let val of data.saleSumArr) {
    	saleSumArr.push((val / 10000).toFixed(2));
    }

    var nameArr = data.dossageArr;

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
        	data: ['采购金额', '销售金额'],
        	//left: 'center'
        	top: '5%',
        	right: '5%'
        },
        xAxis: [
            {
                type: 'category',
                data: nameArr,
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#F0F4FF',//坐标线的颜色
                        width: '1' //坐标线的宽度
                    }
                },
                axisTick: {
                    show:false //x轴坐标点消失
                },
                axisLabel: {
                    textStyle: {
                        show: true,//不显示坐标轴的数字
                        color: '#333',//坐标值得具体的颜色
                    }
                }
            }
        ],
        yAxis: [
            {
            	name: '万元',
                type: 'value',
                axisLine:{
                    show:false //y轴线消失
                },
                axisTick: {
                    show:false //y轴坐标点消失
                },
                splitLine: {
                    show: true,
                    lineStyle:{
                        color: ['#F0F4FF'],
                        width: 1,
                        type: 'solid'
                    }
                }
            }
        ],
        series: [
            {
                name: '采购金额',
                type: 'bar',
                data: buySumArr,
                label: {
	                show: true,
	                position: 'top'
	            },
                barWidth: '35',
                itemStyle: {
                    color: '#4266EE'
                }
            },
            {
                name: '销售金额',
                type: 'bar',
                data: saleSumArr,
                label: {
	                show: true,
	                position: 'top'
	            },
                barWidth: '35',
                itemStyle: {
                    color: '#5BCED6'
                }
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showTopChart03 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;

    var value = data.data;
    var value=[];
    for(let val of data.data){
    	value.push((val/10000).toFixed(2));
    }

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '7%',
            bottom: '3%',
            containLabel: true,
            y: 20
        },
        xAxis: {
        	name: '万元',
            type: 'value',
            boundaryGap: [0, 0.01],
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                }
            },
            nameTextStyle: {
                color: '#333'
            }
        },
        yAxis: {
            type: 'category',
            data: dataAxis,
            axisTick: {
                show:false //y轴坐标点消失
            },
            axisLine:{
                show:false //y轴线消失
            },

            // splitLine: {
            //     show: true,
            //     lineStyle:{
            //         color: ['#F0F4FF'],
            //         width: 1,
            //         type: 'solid'
            //     }
            // }
        },
        series: [
            {
                label: {
                    show: true,
                    position: 'right'
                },
                name: '销售额',
                type: 'bar',
                data: value,
                itemStyle: {
                    color: '#4266EE'
                }
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};


const showCenterChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
    var insureSumData = [] ;
    var normalSumData = [];
    var totalSumData = [];
    for (let val of data.data.insureSumData) {
        insureSumData.push((val / 10000).toFixed(2));
    }
    for (let val of data.data.normalSumData) {
        normalSumData.push((val / 10000).toFixed(2));
    }
    for (let val of data.data.totalSumData) {
        totalSumData.push((val / 10000).toFixed(2));
    }
    var emphasisStyle = {
        itemStyle: {
            shadowBlur: 10,
            shadowColor: 'rgba(0,0,0,0.3)'
        }
    };
    // 2. 指定配置和数据
    let option = {
        legend: {
            top: '5%',
            right: '5%',
            data: ['医保采购金额', '非医保采购金额', '采购总金额'],
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            data: dataAxis,
            //name: '医疗机构',
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                }
            },
            nameTextStyle: {
			 color: '#333'
			}
        },
        yAxis: {
            name: '万元',
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [
        	{
                name: '采购总金额',
                type: 'bar',
                //stack: 'two',
                emphasis: emphasisStyle,
                data: totalSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#7FB1F5'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },
            {
                name: '医保采购金额',
                type: 'bar',
                //stack: 'one',
                emphasis: emphasisStyle,
                data: insureSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#4266EE'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },
            {
                name: '非医保采购金额',
                type: 'bar',
                //stack: 'one',
                emphasis: emphasisStyle,
                data: normalSumData,
                barWidth: '35',
                itemStyle: {
                    color: '#5E78EE'
                },
                label: {
                    show: true,
                    position: 'top'
                }
            },

        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showCenterChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	//var dataAxis = data.dataAxis;
	// let dataAxis = [];
    // for (let name of data.dataAxis) {
    //     dataAxis.push(name.substring(0, 5)+'...');
    // }

    var responseRatioData = data.data.responseRatioData;
    var stockupRetioData = data.data.stockupRetioData;
    var returnRatioData = data.data.returnRatioData;

    // 2. 指定配置和数据
    let option = {
    	tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['订单响应率(%)', '订单备货率(%)', '退货率(%)'],
            top: '5%',
            right: '5%',
        },
        grid: {
            left: '3%',
            right: '8%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: data.dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                },
                interval: 0,
                rotate: 15,
                align: 'center',
                verticalAlign: 'top'
            }
        },
        yAxis: {
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [
            {
                name: '订单响应率(%)',
                type: 'line',
                stack: '百分比1',
                data: responseRatioData,
                itemStyle: {
                    color: '#5BCED6'
                },
            },
            {
                name: '订单备货率(%)',
                type: 'line',
                stack: '百分比2',
                data: stockupRetioData,
                itemStyle: {
                    color: '#7FB1F5'
                },
            },
            {
                name: '退货率(%)',
                type: 'line',
                stack: '百分比3',
                data: returnRatioData,
                itemStyle: {
                    color: '#5E78EE'
                },
            }
        ]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showBottomChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;
    var value = data.data;

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
    	xAxis: {
            type: 'category',
            data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                }
            }
        },
        yAxis: {
        	name: '百分比%',
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [{
            label: {
                show: true,
                position: 'top'
            },
            data: value,
            type: 'bar',
            barWidth: '35',
            itemStyle: {
                color: '#4266EE'
            },
        }]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};

const showBottomChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	var dataAxis = data.dataAxis;


    var value = data.data;

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
    	xAxis: {
            type: 'category',
            data: dataAxis,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                }
            },
            axisTick: {
                show:false //x轴坐标点消失
            },
            axisLabel: {
                textStyle: {
                    show: true,//不显示坐标轴的数字
                    color: '#333',//坐标值得具体的颜色
                }
            }
        },
        yAxis: {
        	name: '百分比%',
            type: 'value',
            axisLine:{
                show:false //y轴线消失
            },
            axisTick: {
                show:false //y轴坐标点消失
            },
            splitLine: {
                show: true,
                lineStyle:{
                    color: ['#F0F4FF'],
                    width: 1,
                    type: 'solid'
                }
            }
        },
        series: [{
            label: {
                show: true,
                position: 'top'
            },
            data: value,
            type: 'bar',
            barWidth: '35',
            itemStyle: {
                color: '#4266EE'
            },
        }]
    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};



