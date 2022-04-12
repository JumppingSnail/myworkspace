const showTopChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	data.sort((a,b)=>a.value-b.value);
	let dataAxis=[], value=[];
	for(let obj of data){
		dataAxis.push(obj.name);
		value.push((obj.value/10000).toFixed(2));
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
            y: 20,
        },
        xAxis: {
        	name: '万元',
            type: 'value',
            boundaryGap: [0, 0.01],
            axisTick: {
	            show:false //y轴坐标点消失
            },
            axisLine: {
	            lineStyle: {
		            type: 'solid',
		            color: '#F0F4FF',//坐标线的颜色
		            width: '1' //坐标线的宽度
	            }
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
            axisLine: {
	            lineStyle: {
		            type: 'solid',
		            color: '#F0F4FF',//坐标线的颜色
		            width: '1' //坐标线的宽度
	            }
            },
            axisLabel: {
	            textStyle: {
		            show: true,//不显示坐标轴的数字
		            color: '#333',//坐标值得具体的颜色
	            }
            }
        },
        series: [
            {
                label: {
                    show: true,
                    position: 'right'
                },
                name: '药品分类',
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

const showTopChart02 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	data.sort((a,b)=>a.value-b.value);
	let dataAxis=[], value=[];
	for(let obj of data){
		dataAxis.push(obj.name);
		value.push((obj.value/10000).toFixed(2));
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
            y: 20,
        },
        xAxis: {
        	name: '万元',
            type: 'value',
            boundaryGap: [0, 0.01],
            axisTick: {
	            show:false //y轴坐标点消失
            },
            axisLine: {
	            lineStyle: {
		            type: 'solid',
		            color: '#F0F4FF',//坐标线的颜色
		            width: '1' //坐标线的宽度
	            }
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
            axisLine: {
	            lineStyle: {
		            type: 'solid',
		            color: '#F0F4FF',//坐标线的颜色
		            width: '1' //坐标线的宽度
	            }
            },
            axisLabel: {
	            textStyle: {
		            show: true,//不显示坐标轴的数字
		            color: '#333',//坐标值得具体的颜色
	            }
            }
        },
        series: [
            {
                label: {
                    show: true,
                    position: 'right'
                },
                name: '药品分类',
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


const showTopChart03 = function(id, data) {
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
            trigger: 'item'
        },
        legend: {
            show: false,
            //top: '5%',
            //left: '60%'
        },
        color:['#4266EE', '#5BCED6'],
        series: [{
            name: '采购额（万元）',
            type: 'pie',
            radius: '70%',
            data: arrData,
            emphasis: {
                label: {
                    show: true,
                    // fontSize: '40',
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
                        position: 'outsize',
                        formatter: '{b}\n{d}%',
                    }
                }
            },
            label: {
                formatter: '{b}\n{d}%',
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


const showCenterChart01 = function(id, data) {
	if(!data){
		return false;
	}
	
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(id));

	let dataArr = [];
    for (let val of data.drugValueArr) {
        dataArr.push((val / 10000).toFixed(2));
    }

    let nameArr = [];
    for(let name of data.drugNameArr){
    	nameArr.push(name.length>4?name.substring(0,4)+'..':name);
    }

    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '3%',
            bottom: '5%',
            show: false,
            containLabel: true,
        },
        xAxis: [{
            type: 'category',
            //data: nameArr,
            data: data.drugNameArr,
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#F0F4FF',//坐标线的颜色
                    width: '1' //坐标线的宽度
                },
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
        }],
        yAxis: [{
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
        }],
        series: [{
            name: '交易额',
            type: 'bar',
            barWidth: '50%',
            data: dataArr,
            label: {
                show: true,
                position: 'top'
            },
            itemStyle: {
                color: '#4266EE'
            }
        }]

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

	let arrData = [];
	for(let dd of data){
		arrData.push({"name":dd.name, "value":(dd.value/10000).toFixed(2)});
	}
	
    // 2. 指定配置和数据
    let option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            show: false,
            //top: '5%',
            //left: '60%'
        },
        color:['#2040AC','#5E78EE','#7FB1F5', '#5BCED6'],
        series: [{
            name: '采购额（万元）',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            emphasis: {
                label: {
                    show: true,
                    // fontSize: '40',
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
                        position: 'outsize',
                        formatter: '{b}\n{d}%',
                    }
                }
            },
            label: {
                formatter: '{b}\n{d}%',

            },
            data: arrData
        }]

    };

    // 3. 把配置和数据给实例对象
    myChart.setOption(option);

    // 重新把配置好的新数据给实例对象
    window.addEventListener("resize", function() {
        myChart.resize();
    });
};




